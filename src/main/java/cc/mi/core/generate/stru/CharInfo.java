package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.StringCoder;

/**
 * 角色精简数据
 **/
public class CharInfo  {
	//账号
	private String accountName;
	//角色guid
	private String guid;
	//角色名称
	private String name;
	//模型id
	private byte modelId;
	//角色等级
	private int level;

	public void encode(ByteBuf buffer) {
		StringCoder.writeString(buffer, this.accountName);
		StringCoder.writeString(buffer, this.guid);
		StringCoder.writeString(buffer, this.name);
		buffer.writeByte(this.modelId);
		buffer.writeInt(this.level);
	}

	public void decode(ByteBuf buffer) {
		this.accountName = StringCoder.readString(buffer);
		this.guid = StringCoder.readString(buffer);
		this.name = StringCoder.readString(buffer);
		this.modelId = buffer.readByte(); 
		this.level = buffer.readInt(); 
	}
	
	public String getAccountName() {
		return this.accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
		
	public String getGuid() {
		return this.guid;
	}
	
	public void setGuid(String guid) {
		this.guid = guid;
	}
		
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
		
	public byte getModelId() {
		return this.modelId;
	}
	
	public void setModelId(byte modelId) {
		this.modelId = modelId;
	}
		
	public int getLevel() {
		return this.level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
		
}