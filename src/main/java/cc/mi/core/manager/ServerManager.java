package cc.mi.core.manager;

import cc.mi.core.generate.msg.IdentityServerMsg;
import cc.mi.core.log.CustomLogger;
import io.netty.channel.Channel;

public abstract class ServerManager {
	static final CustomLogger logger = CustomLogger.getLogger(ServerManager.class);
	
	private Channel centerChannel = null;
	// 连接网关服的通道
	private Channel gateChannel = null;
	
	private final int serverType;
	
	public ServerManager(int serverType) {
		this.serverType = serverType;
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
}
