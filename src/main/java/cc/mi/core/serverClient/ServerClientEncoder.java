package cc.mi.core.serverClient;

import cc.mi.core.coder.Coder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ServerClientEncoder extends MessageToByteEncoder<Coder> {
	@Override
	protected void encode(ChannelHandlerContext ctx, Coder msg, ByteBuf out) throws Exception {
		msg.onEncode(out);
	}

}
