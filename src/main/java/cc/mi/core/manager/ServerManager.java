package cc.mi.core.manager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cc.mi.core.callback.InvokeCallback;
import cc.mi.core.generate.msg.AddTagWatchAndCall;
import cc.mi.core.generate.msg.AddWatchAndCall;
import cc.mi.core.generate.msg.DelTagWatch;
import cc.mi.core.generate.msg.IdentityServerMsg;
import cc.mi.core.generate.msg.ServerRegOpcode;
import cc.mi.core.generate.msg.StartReady;
import cc.mi.core.handler.Handler;
import cc.mi.core.log.CustomLogger;
import cc.mi.core.packet.Packet;
import cc.mi.core.server.ContextManager;
import cc.mi.core.utils.ServerProcessBlock;
import io.netty.channel.Channel;

public abstract class ServerManager {
	static final CustomLogger logger = CustomLogger.getLogger(ServerManager.class);
	
	protected Channel centerChannel = null;
	// 连接网关服的通道
	protected Channel gateChannel = null;
	
	private final int serverType;
	
	// 消息收到以后的回调
	protected final Map<Integer, Handler> handlers = new HashMap<>();
	// 需要的消息opcode列表
	protected final List<Integer> opcodes = new LinkedList<>();
	// 帧刷新
	protected final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	// 主逻辑线程
	protected final ExecutorService mainLogicalExecutor = Executors.newSingleThreadExecutor();
	// 消息包队列
	protected final Queue<Packet> packetQueue = new LinkedList<>();
	// 当前帧刷新执行的代码逻辑
	protected ServerProcessBlock process;
	// 最后一次执行帧刷新的时间戳
	protected long timestamp = 0;
	
	
	public ServerManager(int serverType) {
		this.serverType = serverType;
		
		this.onOpcodeInit();
		this.onExecutorInit();
		this.onProcessInit();
	}
	
	/**
	 * 消息号处理
	 */
	protected abstract void onOpcodeInit();
	
	/**
	 * 定时任务处理
	 */
	protected void onExecutorInit() {
		final ServerManager instance = this;
		executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				instance.doFrameLogical();
			}
		}, 1000, 50, TimeUnit.MILLISECONDS);
	}
	
	private void doFrameLogical() {
		final ServerManager instance = this;
		mainLogicalExecutor.submit(new Runnable() {
			@Override
			public void run() {
				long prev = instance.timestamp;
				long now = System.currentTimeMillis();
				int diff = 0;
				if (prev > 0) diff = (int) (now - prev);
				instance.timestamp = now;
				if (diff < 0 || diff > 1000) {
					logger.warnLog("too heavy logical that execute");
				}
				try {
					instance.doWork(diff);
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * update 过程处理
	 */
	protected void onProcessInit() {}
	
	/**
	 * 定时任务处理逻辑
	 * @param diff
	 */
	protected abstract void doWork(int diff);
	
	
	protected void doProcess(int diff) {
		if (this.process != null) {
			this.process.run(diff);
		}
	}
	
	/**
	 * 处理包信息
	 */
	protected void dealPacket() {
		while (!packetQueue.isEmpty()) {
			Packet packet = packetQueue.poll();
			this.invokeHandler(packet);
		}
	}
	
	/**
	 * 调用具体消息包处理方法
	 * @param packet
	 */
	protected void invokeHandler(Packet packet) {
		int opcode = packet.getOpcode();
		Handler handle = handlers.get(opcode);
		if (handle != null) {
			handle.handle(null, this.centerChannel, packet);
		}
	}
	
	/**
	 * 添加信息
	 * @param packet
	 */
	public void pushPacket(Packet packet) {
		final ServerManager instance = this;
		mainLogicalExecutor.submit(new Runnable() {
			@Override
			public void run() {
				// 这里看情况 玩家操作才添加也是可以的 
				instance.packetQueue.add(packet);
			}
		});
	}
	
	/**
	 * 和中心服连接上以后触发的操作
	 * @param centerChannel
	 */
	public void onCenterConnected(Channel centerChannel) {
		if (this.centerChannel != null) 
			throw new RuntimeException("duplicate connected center");
		if (centerChannel == null)
			throw new RuntimeException("set center channel with null");
		this.centerChannel = centerChannel;
		logger.devLog("identity to center");
		this.indentityServer(this.centerChannel);
		// 注册消息
		this.registerOpcode(this.centerChannel, opcodes);
		
		this.afterCenterConnectedInnerServerInit();
	}
	
	protected void afterCenterConnectedInnerServerInit() {}
	
	public void onCenterDisconnected(Channel centerChannel) {
		if (this.centerChannel == centerChannel) {
			this.centerChannel = null;
			return;
		}
		throw new RuntimeException("与中心服断开连接, 但是断开的channel不是this.centerChannel");
	}
	
	/**
	 * 和网关服连接上以后触发的操作
	 * @param gateChannel
	 */
	public void onGateConnected(Channel gateChannel) {
		if (this.gateChannel != null) 
			throw new RuntimeException("duplicate connected gate");
		if (gateChannel == null)
			throw new RuntimeException("set gate channel with null");
		this.gateChannel = gateChannel;
		logger.devLog("identity to gate");
		this.indentityServer(this.gateChannel);
	}
	
	public void onGateDisconnected(Channel gateChannel) {
		if (this.gateChannel == centerChannel) {
			this.centerChannel = null;
			return;
		}
		throw new RuntimeException("与网关服断开连接, 但是断开的channel不是this.gateChannel");
	}
	
	/**
	 * 告诉对方自己的身份
	 * @param channel
	 */
	private void indentityServer(Channel channel) {
		if (!channel.isActive()) {
			logger.devLog("channel is inactive");
			return;
		}
		IdentityServerMsg ism = new IdentityServerMsg();
		ism.setServerType(this.serverType);
		channel.writeAndFlush(ism);
	}
	
	/**
	 * 告诉对方自己需要的消息opcode
	 * @param channel
	 */
	private void registerOpcode(Channel channel, List<Integer> opcodes) {
		if (!channel.isActive()) {
			logger.devLog("channel is inactive");
			return;
		}
		ServerRegOpcode sro = new ServerRegOpcode();
		sro.setOpcodes(opcodes);
		channel.writeAndFlush(sro);
	}
	
	/**
	 *  给当前服务器注册消息
	 * @param channel
	 * @param guidType
	 */
	public void addWatchAndCall(String binlogId) {
		this.addWatchAndCall(0, binlogId, null);
	}
	
	/**
	 * 给客户端注册消息
	 * @param channel
	 * @param fd
	 * @param guidType
	 */
	public void addWatchAndCall(int fd, String binlogId) {
		this.addWatchAndCall(fd, binlogId, null);
	}
	
	
	/**
	 * 先从本地取, 本地没有去远程取
	 * @param binlogId
	 * @param callback
	 */
	public void addWatchAndCall(String binlogId, InvokeCallback<Void> callback) {
		this.addWatchAndCall(0, binlogId, callback);
	}
	
	/**
	 * 给客户端注册消息
	 * @param channel
	 * @param fd
	 * @param guidType
	 */
	public void addWatchAndCall(int fd, String binlogId, InvokeCallback<Void> callback) {
		// 本地的话直接可以取
		if (fd == 0) {
			if (this.isLocalBinlogDataExists(binlogId)) {
				if (callback != null) {
					callback.invoke(null);
				}
				return;
			}
		}
		
		AddWatchAndCall awac = new AddWatchAndCall();
		awac.setFd(fd);
		awac.setGuidType(binlogId);
		this.centerChannel.writeAndFlush(awac);
		if (callback != null) {
			this.addWatchCallback(binlogId, callback);
		}
	}
	
	protected boolean isLocalBinlogDataExists(String binlogId) {
		return false;
	}
	
	/**
	 *  给当前服务器注册消息
	 * @param channel
	 * @param ownerTag
	 */
	public void addTagWatchAndCall(String ownerTag) {
		this.addTagWatchAndCall(ownerTag, null);
	}
	
	/**
	 *  给当前服务器注册消息
	 * @param channel
	 * @param ownerTag
	 */
	public void addTagWatchAndCall(String ownerTag, InvokeCallback<Void> callback) {
		this.addTagWatchAndCall(0, ownerTag, callback);
	}
	
	/**
	 * 给客户端注册消息
	 * @param channel
	 * @param fd
	 * @param guidType
	 */
	public void addTagWatchAndCall(int fd, String ownerTag) {
		this.addTagWatchAndCall(fd, ownerTag, null);
	}
	
	/**
	 * 给客户端注册消息
	 * @param channel
	 * @param fd
	 * @param guidType
	 */
	public void addTagWatchAndCall(int fd, String ownerTag, InvokeCallback<Void> callback) {
		AddTagWatchAndCall atwac = new AddTagWatchAndCall();
		atwac.setFd(fd);
		atwac.setOwnerTag(ownerTag);
		this.centerChannel.writeAndFlush(atwac);
		if (callback != null) {
			this.addTagWatchCallback(ownerTag, callback);
		}
	}
	
	/**
	 *  给当前服务器删除注册
	 * @param ownerTag
	 */
	public void delTagWatch(String ownerTag) {
		this.delTagWatch(0, ownerTag);
	}
	
	/**
	 *  给当前服务器删除注册
	 * @param channel
	 * @param ownerTag
	 */
	public void delTagWatch(int fd, String ownerTag) {
		DelTagWatch packet = new DelTagWatch();
		packet.setFd(fd);
		packet.setOwnerTag(ownerTag);
		this.centerChannel.writeAndFlush(packet);
	}
	
	protected void addTagWatchCallback(String ownerTag, InvokeCallback<Void> callback) {}
	
	protected void addWatchCallback(String binlogId, InvokeCallback<Void> callback) {}
	
	/**
	 * 告诉对方中心服准备完成
	 * @param channel
	 */
	protected void startReady() {
		StartReady sr = new StartReady();
		this.centerChannel.writeAndFlush(sr);
	}
	
	public void sendToGate(Packet packet) {
		this.gateChannel.writeAndFlush(packet);
	}
	
	public void sendToCenter(Packet packet) {
		this.centerChannel.writeAndFlush(packet);
	}
	
	public void closeSession(int fd, int reasonType) {
		ContextManager.INSTANCE.closeSession(this.gateChannel, fd, reasonType);
	}
}
