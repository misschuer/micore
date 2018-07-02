package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;

/**
 * 测试连接状态
 **/
public class PingPong extends PacketImpl  {

	public PingPong() {
		super(1);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
	}

	@Override
	public void decode(ByteBuf buffer) {
	}
	

	public PacketImpl newInstance() {
		return new PingPong();
	}
}