package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;
import cc.mi.core.coder.StringCoder;

/**
 * 传送地点结构体
 **/
public class taxi_menu_info  {
	//id
	private int id;
	//传送地点名称
	private String taxi_text;
	//地图ID
	private int map_id;
	//坐标X
	private short pos_x;
	//坐标Y
	private short pos_y;

	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.id); 
		StringCoder.writeString(buffer, this.taxi_text);
		buffer.writeInt(this.map_id); 
		buffer.writeShort(this.pos_x); 
		buffer.writeShort(this.pos_y); 
	}

	public void decode(ByteBuf buffer) {
		this.id = buffer.readInt(); 
		this.taxi_text = StringCoder.readString(buffer);
		this.map_id = buffer.readInt(); 
		this.pos_x = buffer.readShort(); 
		this.pos_y = buffer.readShort(); 
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTaxi_text() {
		return this.taxi_text;
	}
	
	public void setTaxi_text(String taxi_text) {
		this.taxi_text = taxi_text;
	}
	
	public int getMap_id() {
		return this.map_id;
	}
	
	public void setMap_id(int map_id) {
		this.map_id = map_id;
	}
	
	public short getPos_x() {
		return this.pos_x;
	}
	
	public void setPos_x(short pos_x) {
		this.pos_x = pos_x;
	}
	
	public short getPos_y() {
		return this.pos_y;
	}
	
	public void setPos_y(short pos_y) {
		this.pos_y = pos_y;
	}
	
}