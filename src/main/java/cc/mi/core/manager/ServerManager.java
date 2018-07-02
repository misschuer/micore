package cc.mi.core.manager;

import java.util.List;

import cc.mi.core.generate.msg.IdentityServerMsg;
import cc.mi.core.generate.msg.ServerRegOpcode;
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
	}
	
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
}
