package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;
import cc.mi.core.packet.StringCoder;
import cc.mi.core.generate.stru.BinlogInfo;

/**
 * 对象提交
 **/
public class PutObject extends PacketImpl  {
	//所属主人
	private String ownerId;
	//binlog对象
	private BinlogInfo binlogData;

	public PutObject() {
		super(22);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		StringCoder.writeString(buffer, this.ownerId);
		this.binlogData.encode(buffer);
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.ownerId = StringCoder.readString(buffer);
		this.binlogData = new BinlogInfo();
		this.binlogData.decode(buffer);
	}
	
	public String getOwnerId() {
		return this.ownerId;
	}
	
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
		
	public BinlogInfo getBinlogData() {
		return this.binlogData;
	}
	
	public void setBinlogData(BinlogInfo binlogData) {
		this.binlogData = binlogData;
	}
		

	public PacketImpl newInstance() {
		return new PutObject();
	}
}