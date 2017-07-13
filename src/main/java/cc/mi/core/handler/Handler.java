package cc.mi.core.handler;

import cc.mi.core.coder.Coder;
import io.netty.channel.Channel;

public interface Handler<T> {
	public void handle(T player, Channel channel, Coder decoder);
	
	public Coder newCoder();
}
