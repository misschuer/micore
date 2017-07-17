package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;

/**
 * 道具奖励信息
 **/
public class ItemRewardInfo  {
	//道具id
	private short item_id;
	//道具数量
	private int num;

	public void encode(ByteBuf buffer) {
		buffer.writeShort(this.item_id); 
		buffer.writeInt(this.num); 
	}

	public void decode(ByteBuf buffer) {
		this.item_id = buffer.readShort(); 
		this.num = buffer.readInt(); 
	}
	
	public short getItem_id() {
		return this.item_id;
	}
	
	public void setItem_id(short item_id) {
		this.item_id = item_id;
	}
	
	public int getNum() {
		return this.num;
	}
	
	public void setNum(int num) {
		this.num = num;
	}
	
}