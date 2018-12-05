package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;
import cc.mi.core.generate.stru.MapInfo;

/**
 * 创建地图
 **/
public class MapCreateMsg extends PacketImpl  {
	//地图信息
	private MapInfo mapInfo;

	public MapCreateMsg() {
		super(37);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		this.mapInfo.encode(buffer);
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.mapInfo = new MapInfo();
		this.mapInfo.decode(buffer);
	}
	
	public MapInfo getMapInfo() {
		return this.mapInfo;
	}
	
	public void setMapInfo(MapInfo mapInfo) {
		this.mapInfo = mapInfo;
	}
		

	public PacketImpl newInstance() {
		return new MapCreateMsg();
	}
}