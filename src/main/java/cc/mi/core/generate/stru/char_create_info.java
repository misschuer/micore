package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;
import cc.mi.core.coder.StringCoder;

/**
 * 玩家角色创建选择信息
 **/
public class char_create_info  {
	//名称
	private String name;
	//阵营
	private byte faction;
	//性别
	private byte gender;
	//等级
	private short level;
	//guid
	private String guid;
	//头像
	private int head_id;
	//发型ID
	private int hair_id;
	//种族，猛男美女萝莉那些
	private byte race;
	//邀请的帮派id
	private String inviteGuid;
	//创建的帮派名称
	private String faction_name;
	//创建的帮派标志
	private byte icon;

	public void encode(ByteBuf buffer) {
		StringCoder.writeString(buffer, this.name);
		buffer.writeByte(this.faction); 
		buffer.writeByte(this.gender); 
		buffer.writeShort(this.level); 
		StringCoder.writeString(buffer, this.guid);
		buffer.writeInt(this.head_id); 
		buffer.writeInt(this.hair_id); 
		buffer.writeByte(this.race); 
		StringCoder.writeString(buffer, this.inviteGuid);
		StringCoder.writeString(buffer, this.faction_name);
		buffer.writeByte(this.icon); 
	}

	public void decode(ByteBuf buffer) {
		this.name = StringCoder.readString(buffer);
		this.faction = buffer.readByte(); 
		this.gender = buffer.readByte(); 
		this.level = buffer.readShort(); 
		this.guid = StringCoder.readString(buffer);
		this.head_id = buffer.readInt(); 
		this.hair_id = buffer.readInt(); 
		this.race = buffer.readByte(); 
		this.inviteGuid = StringCoder.readString(buffer);
		this.faction_name = StringCoder.readString(buffer);
		this.icon = buffer.readByte(); 
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public byte getFaction() {
		return this.faction;
	}
	
	public void setFaction(byte faction) {
		this.faction = faction;
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
	
	public int getHead_id() {
		return this.head_id;
	}
	
	public void setHead_id(int head_id) {
		this.head_id = head_id;
	}
	
	public int getHair_id() {
		return this.hair_id;
	}
	
	public void setHair_id(int hair_id) {
		this.hair_id = hair_id;
	}
	
	public byte getRace() {
		return this.race;
	}
	
	public void setRace(byte race) {
		this.race = race;
	}
	
	public String getInviteGuid() {
		return this.inviteGuid;
	}
	
	public void setInviteGuid(String inviteGuid) {
		this.inviteGuid = inviteGuid;
	}
	
	public String getFaction_name() {
		return this.faction_name;
	}
	
	public void setFaction_name(String faction_name) {
		this.faction_name = faction_name;
	}
	
	public byte getIcon() {
		return this.icon;
	}
	
	public void setIcon(byte icon) {
		this.icon = icon;
	}
	
}