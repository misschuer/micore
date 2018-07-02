package cc.mi.core.net;

import cc.mi.core.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NetEnfrager extends MessageToByteEncoder<Packet> {
	@Override
	protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
		// 这样才能避免跨线程创建ByteBuf和销毁ByteBuf(全部在IO线程中完成)
		msg.onEncode(out);
	}
}