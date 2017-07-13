package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;
import cc.mi.core.coder.StringCoder;

/**
 * 全民boss排名
 **/
public class mass_boss_rank_info  {
	//名称
	private String name;
	//伤害
	private double dam;

	public void encode(ByteBuf buffer) {
		StringCoder.writeString(buffer, this.name);
		buffer.writeDouble(this.dam); 
	}

	public void decode(ByteBuf buffer) {
		this.name = StringCoder.readString(buffer);
		this.dam = buffer.readDouble(); 
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getDam() {
		return this.dam;
	}
	
	public void setDam(double dam) {
		this.dam = dam;
	}
	
}