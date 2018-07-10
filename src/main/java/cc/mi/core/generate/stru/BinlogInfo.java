package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.StringCoder;
import java.util.List;
import java.util.ArrayList;

/**
 * binlog数据
 **/
public class BinlogInfo  {
	//名称
	private List<String> ddd;
	//房东
	private List<Integer> ff;
	//这样
	private String gg;

	public void encode(ByteBuf buffer) {
		buffer.writeShort(this.ddd.size());
		for (String element : this.ddd) {
			StringCoder.writeString(buffer, element);
		}
		buffer.writeShort(this.ff.size());
		for (int element : this.ff) {
			buffer.writeInt(element);
		}
		StringCoder.writeString(buffer, this.gg);
	}

	public void decode(ByteBuf buffer) {
		int dddSize = buffer.readUnsignedShort();
		this.ddd = new ArrayList<>(dddSize);
		for (int i = 0; i < dddSize; ++ i) {
			String element = StringCoder.readString(buffer);
			this.ddd.add(element);
		}
		int ffSize = buffer.readUnsignedShort();
		this.ff = new ArrayList<>(ffSize);
		for (int i = 0; i < ffSize; ++ i) {
			int element = buffer.readInt();
			this.ff.add(element);
		}
		this.gg = StringCoder.readString(buffer);
	}
	
	public List<String> getDdd() {
		return this.ddd;
	}
	
	public void setDdd(List<String> ddd) {
		this.ddd = ddd;
	}
	
	public List<Integer> getFf() {
		return this.ff;
	}
	
	public void setFf(List<Integer> ff) {
		this.ff = ff;
	}
	
	public String getGg() {
		return this.gg;
	}
	
	public void setGg(String gg) {
		this.gg = gg;
	}
		
}