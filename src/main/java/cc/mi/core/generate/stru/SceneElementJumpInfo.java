package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;

/**
 * 元素跳跃数据
 **/
public class SceneElementJumpInfo  {
	//整形id
	private int uintId;
	//目标位置x
	private short destX;
	//目标位置y
	private short destY;

	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.uintId);
		buffer.writeShort(this.destX);
		buffer.writeShort(this.destY);
	}

	public void decode(ByteBuf buffer) {
		this.uintId = buffer.readInt(); 
		this.destX = buffer.readShort(); 
		this.destY = buffer.readShort(); 
	}
	
	public int getUintId() {
		return this.uintId;
	}
	
	public void setUintId(int uintId) {
		this.uintId = uintId;
	}
		
	public short getDestX() {
		return this.destX;
	}
	
	public void setDestX(short destX) {
		this.destX = destX;
	}
		
	public short getDestY() {
		return this.destY;
	}
	
	public void setDestY(short destY) {
		this.destY = destY;
	}
		
}