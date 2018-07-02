package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;

/**
 * 无效动作
 **/
public class NullAction extends PacketImpl  {

	public NullAction() {
		super(0);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
	}

	@Override
	public void decode(ByteBuf buffer) {
	}
	

	public PacketImpl newInstance() {
		return new NullAction();
	}
}