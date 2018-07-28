package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;
import cc.mi.core.packet.StringCoder;
import java.util.List;
import java.util.ArrayList;

/**
 * 对象删除
 **/
public class RemoveBinlog extends PacketImpl  {
	//binlogId列表
	private List<String> binlogList;

	public RemoveBinlog() {
		super(23);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeShort(this.binlogList.size());
		for (String element : this.binlogList) {
			StringCoder.writeString(buffer, element);
		}
	}

	@Override
	public void decode(ByteBuf buffer) {
		int binlogListSize = buffer.readUnsignedShort();
		this.binlogList = new ArrayList<>(binlogListSize);
		for (int i = 0; i < binlogListSize; ++ i) {
			String element = StringCoder.readString(buffer);
			this.binlogList.add(element);
		}
	}
	
	public List<String> getBinlogList() {
		return this.binlogList;
	}
	
	public void setBinlogList(List<String> binlogList) {
		this.binlogList = binlogList;
	}
	

	public PacketImpl newInstance() {
		return new RemoveBinlog();
	}
}