package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;

/**
 * 删除地图
 **/
public class DeleteMap extends PacketImpl  {
	//实例id
	private int instId;
	//地图模板id
	private int mapId;

	public DeleteMap() {
		super(34);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.instId);
		buffer.writeInt(this.mapId);
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.instId = buffer.readInt(); 
		this.mapId = buffer.readInt(); 
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
		

	public PacketImpl newInstance() {
		return new DeleteMap();
	}
}