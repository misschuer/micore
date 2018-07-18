package cc.mi.core.manager;

import java.util.List;

import cc.mi.core.callback.Callback;
import cc.mi.core.generate.msg.AddTagWatchAndCall;
import cc.mi.core.generate.msg.AddWatchAndCall;
import cc.mi.core.generate.msg.IdentityServerMsg;
import cc.mi.core.generate.msg.ServerRegOpcode;
import cc.mi.core.generate.msg.StartReady;
import cc.mi.core.log.CustomLogger;
import io.netty.channel.Channel;

public abstract class ServerManager {
	static final CustomLogger logger = CustomLogger.getLogger(ServerManager.class);
	
	protected Channel centerChannel = null;
	// 连接网关服的通道
	protected Channel gateChannel = null;
	
	private final int serverType;
	
	// 需要的消息opcode列表
	private final List<Integer> opcodes;
	
	public ServerManager(int serverType, List<Integer> opcodes) {
		this.serverType = serverType;
		this.opcodes = opcodes;
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
	public void addWatchAndCall(String guidType) {
		this.addWatchAndCall(0, guidType);
	}
	
	/**
	 * 给客户端注册消息
	 * @param channel
	 * @param fd
	 * @param guidType
	 */
	public void addWatchAndCall(int fd, String guidType) {
		AddWatchAndCall awac = new AddWatchAndCall();
		awac.setFd(fd);
		awac.setGuidType(guidType);
		this.centerChannel.writeAndFlush(awac);
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
	public void addTagWatchAndCall(String ownerTag, Callback<Void> callback) {
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
	public void addTagWatchAndCall(int fd, String ownerTag, Callback<Void> callback) {
		AddTagWatchAndCall atwac = new AddTagWatchAndCall();
		atwac.setFd(fd);
		atwac.setOwnerTag(ownerTag);
		this.centerChannel.writeAndFlush(atwac);
		if (callback != null) {
			this.addTagWatchCallback(ownerTag, callback);
		}
	}
	
	protected void addTagWatchCallback(String ownerTag, Callback<Void> callback) {}
	
	/**
	 * 告诉对方中心服准备完成
	 * @param channel
	 */
	protected void startReady() {
		StartReady sr = new StartReady();
		this.centerChannel.writeAndFlush(sr);
	}
}
