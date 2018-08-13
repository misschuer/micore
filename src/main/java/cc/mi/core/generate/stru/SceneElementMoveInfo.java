package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;
import java.util.List;
import java.util.ArrayList;

/**
 * 元素移动数据
 **/
public class SceneElementMoveInfo  {
	//整形id
	private int uintId;
	//初始位置x
	private short startX;
	//初始位置y
	private short startY;
	//路径
	private List<Byte> path;

	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.uintId);
		buffer.writeShort(this.startX);
		buffer.writeShort(this.startY);
		buffer.writeShort(this.path.size());
		for (byte element : this.path) {
			buffer.writeByte(element);
		}
	}

	public void decode(ByteBuf buffer) {
		this.uintId = buffer.readInt(); 
		this.startX = buffer.readShort(); 
		this.startY = buffer.readShort(); 
		int pathSize = buffer.readUnsignedShort();
		this.path = new ArrayList<>(pathSize);
		for (int i = 0; i < pathSize; ++ i) {
			byte element = buffer.readByte();
			this.path.add(element);
		}
	}
	
	public int getUintId() {
		return this.uintId;
	}
	
	public void setUintId(int uintId) {
		this.uintId = uintId;
	}
		
	public short getStartX() {
		return this.startX;
	}
	
	public void setStartX(short startX) {
		this.startX = startX;
	}
		
	public short getStartY() {
		return this.startY;
	}
	
	public void setStartY(short startY) {
		this.startY = startY;
	}
		
	public List<Byte> getPath() {
		return this.path;
	}
	
	public void setPath(List<Byte> path) {
		this.path = path;
	}
	
}