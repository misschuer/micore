package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;

/**
 * 坐标结构体
 **/
public class point  {
	//坐标X
	private float pos_x;
	//坐标Y
	private float pos_y;

	public void encode(ByteBuf buffer) {
		buffer.writeFloat(this.pos_x); 
		buffer.writeFloat(this.pos_y); 
	}

	public void decode(ByteBuf buffer) {
		this.pos_x = buffer.readFloat(); 
		this.pos_y = buffer.readFloat(); 
	}
	
	public float getPos_x() {
		return this.pos_x;
	}
	
	public void setPos_x(float pos_x) {
		this.pos_x = pos_x;
	}
	
	public float getPos_y() {
		return this.pos_y;
	}
	
	public void setPos_y(float pos_y) {
		this.pos_y = pos_y;
	}
	
}