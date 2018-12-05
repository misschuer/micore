package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;
import cc.mi.core.packet.StringCoder;

/**
 * 删除地图
 **/
public class MapDeleteMsg extends PacketImpl  {
	//地图唯一id
	private String binlogId;

	public MapDeleteMsg() {
		super(38);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		StringCoder.writeString(buffer, this.binlogId);
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.binlogId = StringCoder.readString(buffer);
	}
	
	public String getBinlogId() {
		return this.binlogId;
	}
	
	public void setBinlogId(String binlogId) {
		this.binlogId = binlogId;
	}
		

	public PacketImpl newInstance() {
		return new MapDeleteMsg();
	}
}