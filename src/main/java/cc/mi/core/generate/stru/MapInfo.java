package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.StringCoder;

/**
 * 地图数据
 **/
public class MapInfo  {
	//地图唯一id
	private String binlogId;
	//地图id
	private int mapId;
	//实例id
	private int instId;
	//分线号
	private int lineNo;
	//创建时间
	private int createTime;
	//实例类型
	private int instType;
	//参数
	private String ext;

	public void encode(ByteBuf buffer) {
		StringCoder.writeString(buffer, this.binlogId);
		buffer.writeInt(this.mapId);
		buffer.writeInt(this.instId);
		buffer.writeInt(this.lineNo);
		buffer.writeInt(this.createTime);
		buffer.writeInt(this.instType);
		StringCoder.writeString(buffer, this.ext);
	}

	public void decode(ByteBuf buffer) {
		this.binlogId = StringCoder.readString(buffer);
		this.mapId = buffer.readInt(); 
		this.instId = buffer.readInt(); 
		this.lineNo = buffer.readInt(); 
		this.createTime = buffer.readInt(); 
		this.instType = buffer.readInt(); 
		this.ext = StringCoder.readString(buffer);
	}
	
	public String getBinlogId() {
		return this.binlogId;
	}
	
	public void setBinlogId(String binlogId) {
		this.binlogId = binlogId;
	}
		
	public int getMapId() {
		return this.mapId;
	}
	
	public void setMapId(int mapId) {
		this.mapId = mapId;
	}
		
	public int getInstId() {
		return this.instId;
	}
	
	public void setInstId(int instId) {
		this.instId = instId;
	}
		
	public int getLineNo() {
		return this.lineNo;
	}
	
	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}
		
	public int getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}
		
	public int getInstType() {
		return this.instType;
	}
	
	public void setInstType(int instType) {
		this.instType = instType;
	}
		
	public String getExt() {
		return this.ext;
	}
	
	public void setExt(String ext) {
		this.ext = ext;
	}
		
}