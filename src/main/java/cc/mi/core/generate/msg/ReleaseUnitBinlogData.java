package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;
import java.util.List;
import java.util.ArrayList;

/**
 * 场景元素对象删除
 **/
public class ReleaseUnitBinlogData extends PacketImpl  {
	//删除的uintid
	private List<Integer> unitUintIdList;

	public ReleaseUnitBinlogData() {
		super(28);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeShort(this.unitUintIdList.size());
		for (int element : this.unitUintIdList) {
			buffer.writeInt(element);
		}
	}

	@Override
	public void decode(ByteBuf buffer) {
		int unitUintIdListSize = buffer.readUnsignedShort();
		this.unitUintIdList = new ArrayList<>(unitUintIdListSize);
		for (int i = 0; i < unitUintIdListSize; ++ i) {
			int element = buffer.readInt();
			this.unitUintIdList.add(element);
		}
	}
	
	public List<Integer> getUnitUintIdList() {
		return this.unitUintIdList;
	}
	
	public void setUnitUintIdList(List<Integer> unitUintIdList) {
		this.unitUintIdList = unitUintIdList;
	}
	

	public PacketImpl newInstance() {
		return new ReleaseUnitBinlogData();
	}
}