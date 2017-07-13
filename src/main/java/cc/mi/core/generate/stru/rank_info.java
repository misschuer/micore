package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;
import cc.mi.core.coder.StringCoder;

/**
 * 排名信息
 **/
public class rank_info  {
	//名字
	private String name;
	//伤害百分比
	private float value;

	public void encode(ByteBuf buffer) {
		StringCoder.writeString(buffer, this.name);
		buffer.writeFloat(this.value); 
	}

	public void decode(ByteBuf buffer) {
		this.name = StringCoder.readString(buffer);
		this.value = buffer.readFloat(); 
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public float getValue() {
		return this.value;
	}
	
	public void setValue(float value) {
		this.value = value;
	}
	
}