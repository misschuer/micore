package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.StringCoder;
import java.util.List;
import java.util.ArrayList;

/**
 * binlog数据
 **/
public class UnitBinlogInfo  {
	//操作类型(新建
	private int state;
	//数据id
	private String binlogId;
	//整形id(更新时比较频繁
	private int uintId;
	//int类型掩码
	private List<Integer> intMask;
	//str类型掩码
	private List<Integer> strMask;
	//int类型数据(确保有序)
	private List<Integer> intValues;
	//str类型数据(确保有序)
	private List<String> strValues;

	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.state);
		StringCoder.writeString(buffer, this.binlogId);
		buffer.writeInt(this.uintId);
		buffer.writeShort(this.intMask.size());
		for (int element : this.intMask) {
			buffer.writeInt(element);
		}
		buffer.writeShort(this.strMask.size());
		for (int element : this.strMask) {
			buffer.writeInt(element);
		}
		buffer.writeShort(this.intValues.size());
		for (int element : this.intValues) {
			buffer.writeInt(element);
		}
		buffer.writeShort(this.strValues.size());
		for (String element : this.strValues) {
			StringCoder.writeString(buffer, element);
		}
	}

	public void decode(ByteBuf buffer) {
		this.state = buffer.readInt(); 
		this.binlogId = StringCoder.readString(buffer);
		this.uintId = buffer.readInt(); 
		int intMaskSize = buffer.readUnsignedShort();
		this.intMask = new ArrayList<>(intMaskSize);
		for (int i = 0; i < intMaskSize; ++ i) {
			int element = buffer.readInt();
			this.intMask.add(element);
		}
		int strMaskSize = buffer.readUnsignedShort();
		this.strMask = new ArrayList<>(strMaskSize);
		for (int i = 0; i < strMaskSize; ++ i) {
			int element = buffer.readInt();
			this.strMask.add(element);
		}
		int intValuesSize = buffer.readUnsignedShort();
		this.intValues = new ArrayList<>(intValuesSize);
		for (int i = 0; i < intValuesSize; ++ i) {
			int element = buffer.readInt();
			this.intValues.add(element);
		}
		int strValuesSize = buffer.readUnsignedShort();
		this.strValues = new ArrayList<>(strValuesSize);
		for (int i = 0; i < strValuesSize; ++ i) {
			String element = StringCoder.readString(buffer);
			this.strValues.add(element);
		}
	}
	
	public int getState() {
		return this.state;
	}
	
	public void setState(int state) {
		this.state = state;
	}
		
	public String getBinlogId() {
		return this.binlogId;
	}
	
	public void setBinlogId(String binlogId) {
		this.binlogId = binlogId;
	}
		
	public int getUintId() {
		return this.uintId;
	}
	
	public void setUintId(int uintId) {
		this.uintId = uintId;
	}
		
	public List<Integer> getIntMask() {
		return this.intMask;
	}
	
	public void setIntMask(List<Integer> intMask) {
		this.intMask = intMask;
	}
	
	public List<Integer> getStrMask() {
		return this.strMask;
	}
	
	public void setStrMask(List<Integer> strMask) {
		this.strMask = strMask;
	}
	
	public List<Integer> getIntValues() {
		return this.intValues;
	}
	
	public void setIntValues(List<Integer> intValues) {
		this.intValues = intValues;
	}
	
	public List<String> getStrValues() {
		return this.strValues;
	}
	
	public void setStrValues(List<String> strValues) {
		this.strValues = strValues;
	}
	
}