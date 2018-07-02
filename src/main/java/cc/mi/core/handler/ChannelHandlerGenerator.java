package cc.mi.core.handler;

import io.netty.channel.ChannelHandler;

public interface ChannelHandlerGenerator {
	public ChannelHandler newChannelHandler();
}
