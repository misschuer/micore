package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;
import java.util.List;
import java.util.ArrayList;
import cc.mi.core.generate.stru.UnitBinlogInfo;

/**
 * 场景元素对象更新
 **/
public class UnitBinlogDataModify extends PacketImpl  {
	//场景元素对象
	private List<UnitBinlogInfo> unitBinlogDataList;

	public UnitBinlogDataModify() {
		super(16);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeShort(this.unitBinlogDataList.size());
		for (UnitBinlogInfo element : this.unitBinlogDataList) {
			element.encode(buffer);
		}
	}

	@Override
	public void decode(ByteBuf buffer) {
		int unitBinlogDataListSize = buffer.readUnsignedShort();
		this.unitBinlogDataList = new ArrayList<>(unitBinlogDataListSize);
		for (int i = 0; i < unitBinlogDataListSize; ++ i) {
			UnitBinlogInfo element = new UnitBinlogInfo();
			element.decode(buffer);
			this.unitBinlogDataList.add(element);
		}
	}
	
	public List<UnitBinlogInfo> getUnitBinlogDataList() {
		return this.unitBinlogDataList;
	}
	
	public void setUnitBinlogDataList(List<UnitBinlogInfo> unitBinlogDataList) {
		this.unitBinlogDataList = unitBinlogDataList;
	}
	

	public PacketImpl newInstance() {
		return new UnitBinlogDataModify();
	}
}