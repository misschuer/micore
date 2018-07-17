package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;

/**
 * 通知中心服服务器启动完毕
 **/
public class StartReady extends PacketImpl  {

	public StartReady() {
		super(14);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
	}

	@Override
	public void decode(ByteBuf buffer) {
	}
	

	public PacketImpl newInstance() {
		return new StartReady();
	}
}