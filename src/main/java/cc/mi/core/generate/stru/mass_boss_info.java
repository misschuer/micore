package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;

/**
 * 全民boss信息
 **/
public class mass_boss_info  {
	//全民boss编号
	private byte id;
	//全民boss状态
	private byte state;
	//全民boss刷新时间
	private int time;
	//boss血量
	private byte percent;
	//挑战boss人数
	private short count;

	public void encode(ByteBuf buffer) {
		buffer.writeByte(this.id); 
		buffer.writeByte(this.state); 
		buffer.writeInt(this.time); 
		buffer.writeByte(this.percent); 
		buffer.writeShort(this.count); 
	}

	public void decode(ByteBuf buffer) {
		this.id = buffer.readByte(); 
		this.state = buffer.readByte(); 
		this.time = buffer.readInt(); 
		this.percent = buffer.readByte(); 
		this.count = buffer.readShort(); 
	}
	
	public byte getId() {
		return this.id;
	}
	
	public void setId(byte id) {
		this.id = id;
	}
	
	public byte getState() {
		return this.state;
	}
	
	public void setState(byte state) {
		this.state = state;
	}
	
	public int getTime() {
		return this.time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public byte getPercent() {
		return this.percent;
	}
	
	public void setPercent(byte percent) {
		this.percent = percent;
	}
	
	public short getCount() {
		return this.count;
	}
	
	public void setCount(short count) {
		this.count = count;
	}
	
}