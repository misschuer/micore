package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;
import cc.mi.core.packet.StringCoder;
import java.util.List;
import java.util.ArrayList;

/**
 * 对象删除
 **/
public class ReleaseBinlogData extends PacketImpl  {
	//删除的binlogid
	private List<String> binlogIdList;

	public ReleaseBinlogData() {
		super(27);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeShort(this.binlogIdList.size());
		for (String element : this.binlogIdList) {
			StringCoder.writeString(buffer, element);
		}
	}

	@Override
	public void decode(ByteBuf buffer) {
		int binlogIdListSize = buffer.readUnsignedShort();
		this.binlogIdList = new ArrayList<>(binlogIdListSize);
		for (int i = 0; i < binlogIdListSize; ++ i) {
			String element = StringCoder.readString(buffer);
			this.binlogIdList.add(element);
		}
	}
	
	public List<String> getBinlogIdList() {
		return this.binlogIdList;
	}
	
	public void setBinlogIdList(List<String> binlogIdList) {
		this.binlogIdList = binlogIdList;
	}
	

	public PacketImpl newInstance() {
		return new ReleaseBinlogData();
	}
}