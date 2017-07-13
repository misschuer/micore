package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;

/**
 * 任务状态
 **/
public class quest_status  {
	//任务ID
	private short quest_id;
	//任务状态
	private byte status;

	public void encode(ByteBuf buffer) {
		buffer.writeShort(this.quest_id); 
		buffer.writeByte(this.status); 
	}

	public void decode(ByteBuf buffer) {
		this.quest_id = buffer.readShort(); 
		this.status = buffer.readByte(); 
	}
	
	public short getQuest_id() {
		return this.quest_id;
	}
	
	public void setQuest_id(short quest_id) {
		this.quest_id = quest_id;
	}
	
	public byte getStatus() {
		return this.status;
	}
	
	public void setStatus(byte status) {
		this.status = status;
	}
	
}