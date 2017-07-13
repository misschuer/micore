package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;
import cc.mi.core.coder.StringCoder;

/**
 * 等待信息
 **/
public class wait_info  {
	//名字
	private String name;
	//状态
	private byte state;

	public void encode(ByteBuf buffer) {
		StringCoder.writeString(buffer, this.name);
		buffer.writeByte(this.state); 
	}

	public void decode(ByteBuf buffer) {
		this.name = StringCoder.readString(buffer);
		this.state = buffer.readByte(); 
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public byte getState() {
		return this.state;
	}
	
	public void setState(byte state) {
		this.state = state;
	}
	
}