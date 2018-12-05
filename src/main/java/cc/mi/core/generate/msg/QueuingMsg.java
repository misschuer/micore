package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;

/**
 * 排队名次
 **/
public class QueuingMsg extends PacketImpl  {
	//排队名次
	private int index;

	public QueuingMsg() {
		super(35);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.index);
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.index = buffer.readInt(); 
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
		

	public PacketImpl newInstance() {
		return new QueuingMsg();
	}
}