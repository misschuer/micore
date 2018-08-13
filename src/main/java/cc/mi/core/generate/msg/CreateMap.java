package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;
import cc.mi.core.packet.StringCoder;

/**
 * 创建地图
 **/
public class CreateMap extends PacketImpl  {
	//实例id
	private int instId;
	//地图模板id
	private int mapId;
	//分线号
	private int lineNo;
	//地图参数
	private String ext;

	public CreateMap() {
		super(26);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.instId);
		buffer.writeInt(this.mapId);
		buffer.writeInt(this.lineNo);
		StringCoder.writeString(buffer, this.ext);
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.instId = buffer.readInt(); 
		this.mapId = buffer.readInt(); 
		this.lineNo = buffer.readInt(); 
		this.ext = StringCoder.readString(buffer);
	}
	
	public int getInstId() {
		return this.instId;
	}
	
	public void setInstId(int instId) {
		this.instId = instId;
	}
		
	public int getMapId() {
		return this.mapId;
	}
	
	public void setMapId(int mapId) {
		this.mapId = mapId;
	}
		
	public int getLineNo() {
		return this.lineNo;
	}
	
	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}
		
	public String getExt() {
		return this.ext;
	}
	
	public void setExt(String ext) {
		this.ext = ext;
	}
		

	public PacketImpl newInstance() {
		return new CreateMap();
	}
}