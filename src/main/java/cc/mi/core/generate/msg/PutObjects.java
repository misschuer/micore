package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;
import cc.mi.core.packet.StringCoder;
import java.util.List;
import java.util.ArrayList;
import cc.mi.core.generate.stru.BinlogInfo;

/**
 * 对象提交
 **/
public class PutObjects extends PacketImpl  {
	//所属主人
	private String ownerId;
	//binlog对象
	private List<BinlogInfo> binlogDataList;

	public PutObjects() {
		super(21);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		StringCoder.writeString(buffer, this.ownerId);
		buffer.writeShort(this.binlogDataList.size());
		for (BinlogInfo element : this.binlogDataList) {
			element.encode(buffer);
		}
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.ownerId = StringCoder.readString(buffer);
		int binlogDataListSize = buffer.readUnsignedShort();
		this.binlogDataList = new ArrayList<>(binlogDataListSize);
		for (int i = 0; i < binlogDataListSize; ++ i) {
			BinlogInfo element = new BinlogInfo();
			element.decode(buffer);
			this.binlogDataList.add(element);
		}
	}
	
	public String getOwnerId() {
		return this.ownerId;
	}
	
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
		
	public List<BinlogInfo> getBinlogDataList() {
		return this.binlogDataList;
	}
	
	public void setBinlogDataList(List<BinlogInfo> binlogDataList) {
		this.binlogDataList = binlogDataList;
	}
	

	public PacketImpl newInstance() {
		return new PutObjects();
	}
}