package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.StringCoder;

/**
 * 玩家角色创建选择信息
 **/
public class CharCreateInfo  {
	//名称
	private String name;
	//角色类别
	private byte gender;
	//等级
	private short level;
	//唯一id
	private String guid;
	//头像
	private int headId;
	//发型ID
	private int hairId;

	public void encode(ByteBuf buffer) {
		StringCoder.writeString(buffer, this.name);
		buffer.writeByte(this.gender); 
		buffer.writeShort(this.level); 
		StringCoder.writeString(buffer, this.guid);
		buffer.writeInt(this.headId); 
		buffer.writeInt(this.hairId); 
	}

	public void decode(ByteBuf buffer) {
		this.name = StringCoder.readString(buffer);
		this.gender = buffer.readByte(); 
		this.level = buffer.readShort(); 
		this.guid = StringCoder.readString(buffer);
		this.headId = buffer.readInt(); 
		this.hairId = buffer.readInt(); 
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public byte getGender() {
		return this.gender;
	}
	
	public void setGender(byte gender) {
		this.gender = gender;
	}
	
	public short getLevel() {
		return this.level;
	}
	
	public void setLevel(short level) {
		this.level = level;
	}
	
	public String getGuid() {
		return this.guid;
	}
	
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public int getHeadId() {
		return this.headId;
	}
	
	public void setHeadId(int headId) {
		this.headId = headId;
	}
	
	public int getHairId() {
		return this.hairId;
	}
	
	public void setHairId(int hairId) {
		this.hairId = hairId;
	}
	
}