package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;
import cc.mi.core.coder.StringCoder;

/**
 * 好友信息
 **/
public class social_friend_info  {
	//好友guid
	private String guid;
	//名字
	private String name;
	//帮派
	private String faction;
	//等级
	private short level;
	//头像
	private short icon;
	//头像
	private short vip;

	public void encode(ByteBuf buffer) {
		StringCoder.writeString(buffer, this.guid);
		StringCoder.writeString(buffer, this.name);
		StringCoder.writeString(buffer, this.faction);
		buffer.writeShort(this.level); 
		buffer.writeShort(this.icon); 
		buffer.writeShort(this.vip); 
	}

	public void decode(ByteBuf buffer) {
		this.guid = StringCoder.readString(buffer);
		this.name = StringCoder.readString(buffer);
		this.faction = StringCoder.readString(buffer);
		this.level = buffer.readShort(); 
		this.icon = buffer.readShort(); 
		this.vip = buffer.readShort(); 
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
	
	public String getFaction() {
		return this.faction;
	}
	
	public void setFaction(String faction) {
		this.faction = faction;
	}
	
	public short getLevel() {
		return this.level;
	}
	
	public void setLevel(short level) {
		this.level = level;
	}
	
	public short getIcon() {
		return this.icon;
	}
	
	public void setIcon(short icon) {
		this.icon = icon;
	}
	
	public short getVip() {
		return this.vip;
	}
	
	public void setVip(short vip) {
		this.vip = vip;
	}
	
}