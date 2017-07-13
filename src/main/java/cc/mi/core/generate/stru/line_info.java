package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;

/**
 * 坐标结构体
 **/
public class line_info  {
	//分线号
	private short lineNo;
	//玩家比率
	private byte rate;

	public void encode(ByteBuf buffer) {
		buffer.writeShort(this.lineNo); 
		buffer.writeByte(this.rate); 
	}

	public void decode(ByteBuf buffer) {
		this.lineNo = buffer.readShort(); 
		this.rate = buffer.readByte(); 
	}
	
	public short getLineNo() {
		return this.lineNo;
	}
	
	public void setLineNo(short lineNo) {
		this.lineNo = lineNo;
	}
	
	public byte getRate() {
		return this.rate;
	}
	
	public void setRate(byte rate) {
		this.rate = rate;
	}
	
}