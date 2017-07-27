package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;

/**
 * 道具奖励信息
 **/
public class ItemRewardInfo  {
	//道具id
	private short itemId;
	//道具数量
	private int num;

	public void encode(ByteBuf buffer) {
		buffer.writeShort(this.itemId); 
		buffer.writeInt(this.num); 
	}

	public void decode(ByteBuf buffer) {
		this.itemId = buffer.readShort(); 
		this.num = buffer.readInt(); 
	}
	
	public short getItemId() {
		return this.itemId;
	}
	
	public void setItemId(short itemId) {
		this.itemId = itemId;
	}
	
	public int getNum() {
		return this.num;
	}
	
	public void setNum(int num) {
		this.num = num;
	}
	
}