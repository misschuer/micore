package cc.mi.core.handler;

import cc.mi.core.coder.Packet;
import cc.mi.core.server.ServerContext;
import io.netty.channel.Channel;

public interface Handler {
	public void handle(ServerContext player, Channel channel, Packet decoder);
}
