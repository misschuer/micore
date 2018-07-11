package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;
import java.util.List;
import java.util.ArrayList;
import cc.mi.core.generate.stru.BinlogInfo;

/**
 * 对象更新
 **/
public class BinlogDataModify extends PacketImpl  {
	//改变量对象
	private List<BinlogInfo> binlogDataList;

	public BinlogDataModify() {
		super(15);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeShort(this.binlogDataList.size());
		for (BinlogInfo element : this.binlogDataList) {
			element.encode(buffer);
		}
	}

	@Override
	public void decode(ByteBuf buffer) {
		int binlogDataListSize = buffer.readUnsignedShort();
		this.binlogDataList = new ArrayList<>(binlogDataListSize);
		for (int i = 0; i < binlogDataListSize; ++ i) {
			BinlogInfo element = new BinlogInfo();
			element.decode(buffer);
			this.binlogDataList.add(element);
		}
	}
	
	public List<BinlogInfo> getBinlogDataList() {
		return this.binlogDataList;
	}
	
	public void setBinlogDataList(List<BinlogInfo> binlogDataList) {
		this.binlogDataList = binlogDataList;
	}
	

	public PacketImpl newInstance() {
		return new BinlogDataModify();
	}
}