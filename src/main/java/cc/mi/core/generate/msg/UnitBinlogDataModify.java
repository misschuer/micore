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
	private List<UnitBinlogInfo> unitBinlogInfoList;

	public UnitBinlogDataModify() {
		super(16);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeShort(this.unitBinlogInfoList.size());
		for (UnitBinlogInfo element : this.unitBinlogInfoList) {
			element.encode(buffer);
		}
	}

	@Override
	public void decode(ByteBuf buffer) {
		int unitBinlogInfoListSize = buffer.readUnsignedShort();
		this.unitBinlogInfoList = new ArrayList<>(unitBinlogInfoListSize);
		for (int i = 0; i < unitBinlogInfoListSize; ++ i) {
			UnitBinlogInfo element = new UnitBinlogInfo();
			element.decode(buffer);
			this.unitBinlogInfoList.add(element);
		}
	}
	
	public List<UnitBinlogInfo> getUnitBinlogInfoList() {
		return this.unitBinlogInfoList;
	}
	
	public void setUnitBinlogInfoList(List<UnitBinlogInfo> unitBinlogInfoList) {
		this.unitBinlogInfoList = unitBinlogInfoList;
	}
	

	public PacketImpl newInstance() {
		return new UnitBinlogDataModify();
	}
}