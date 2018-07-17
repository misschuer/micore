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
	private List<BinlogInfo> binlogInfoList;

	public BinlogDataModify() {
		super(15);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeShort(this.binlogInfoList.size());
		for (BinlogInfo element : this.binlogInfoList) {
			element.encode(buffer);
		}
	}

	@Override
	public void decode(ByteBuf buffer) {
		int binlogInfoListSize = buffer.readUnsignedShort();
		this.binlogInfoList = new ArrayList<>(binlogInfoListSize);
		for (int i = 0; i < binlogInfoListSize; ++ i) {
			BinlogInfo element = new BinlogInfo();
			element.decode(buffer);
			this.binlogInfoList.add(element);
		}
	}
	
	public List<BinlogInfo> getBinlogInfoList() {
		return this.binlogInfoList;
	}
	
	public void setBinlogInfoList(List<BinlogInfo> binlogInfoList) {
		this.binlogInfoList = binlogInfoList;
	}
	

	public PacketImpl newInstance() {
		return new BinlogDataModify();
	}
}