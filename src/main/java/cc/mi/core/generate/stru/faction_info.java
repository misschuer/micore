package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;
import cc.mi.core.coder.StringCoder;

/**
 * 帮派信息
 **/
public class faction_info  {
	//帮派guid
	private String faction_guid;
	//名字
	private String faction_name;
	//帮主名字
	private String faction_bz;
	//公告
	private String faction_gg;
	//等级
	private short level;
	//头像
	private byte icon;
	//帮派人数
	private short player_count;
	//等级限制
	private short minlev;

	public void encode(ByteBuf buffer) {
		StringCoder.writeString(buffer, this.faction_guid);
		StringCoder.writeString(buffer, this.faction_name);
		StringCoder.writeString(buffer, this.faction_bz);
		StringCoder.writeString(buffer, this.faction_gg);
		buffer.writeShort(this.level); 
		buffer.writeByte(this.icon); 
		buffer.writeShort(this.player_count); 
		buffer.writeShort(this.minlev); 
	}

	public void decode(ByteBuf buffer) {
		this.faction_guid = StringCoder.readString(buffer);
		this.faction_name = StringCoder.readString(buffer);
		this.faction_bz = StringCoder.readString(buffer);
		this.faction_gg = StringCoder.readString(buffer);
		this.level = buffer.readShort(); 
		this.icon = buffer.readByte(); 
		this.player_count = buffer.readShort(); 
		this.minlev = buffer.readShort(); 
	}
	
	public String getFaction_guid() {
		return this.faction_guid;
	}
	
	public void setFaction_guid(String faction_guid) {
		this.faction_guid = faction_guid;
	}
	
	public String getFaction_name() {
		return this.faction_name;
	}
	
	public void setFaction_name(String faction_name) {
		this.faction_name = faction_name;
	}
	
	public String getFaction_bz() {
		return this.faction_bz;
	}
	
	public void setFaction_bz(String faction_bz) {
		this.faction_bz = faction_bz;
	}
	
	public String getFaction_gg() {
		return this.faction_gg;
	}
	
	public void setFaction_gg(String faction_gg) {
		this.faction_gg = faction_gg;
	}
	
	public short getLevel() {
		return this.level;
	}
	
	public void setLevel(short level) {
		this.level = level;
	}
	
	public byte getIcon() {
		return this.icon;
	}
	
	public void setIcon(byte icon) {
		this.icon = icon;
	}
	
	public short getPlayer_count() {
		return this.player_count;
	}
	
	public void setPlayer_count(short player_count) {
		this.player_count = player_count;
	}
	
	public short getMinlev() {
		return this.minlev;
	}
	
	public void setMinlev(short minlev) {
		this.minlev = minlev;
	}
	
}