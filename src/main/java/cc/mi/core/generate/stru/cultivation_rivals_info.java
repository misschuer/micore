package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;
import cc.mi.core.coder.StringCoder;

/**
 * 修炼场对手信息
 **/
public class cultivation_rivals_info  {
	//序号
	private int index;
	//名字
	private String name;
	//等级
	private int level;
	//武器
	private int weapon;
	//外观
	private int avatar;
	//神兵
	private int divine;
	//战力
	private int force;
	//宝箱
	private int chest;
	//性别
	private int gender;

	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.index); 
		StringCoder.writeString(buffer, this.name);
		buffer.writeInt(this.level); 
		buffer.writeInt(this.weapon); 
		buffer.writeInt(this.avatar); 
		buffer.writeInt(this.divine); 
		buffer.writeInt(this.force); 
		buffer.writeInt(this.chest); 
		buffer.writeInt(this.gender); 
	}

	public void decode(ByteBuf buffer) {
		this.index = buffer.readInt(); 
		this.name = StringCoder.readString(buffer);
		this.level = buffer.readInt(); 
		this.weapon = buffer.readInt(); 
		this.avatar = buffer.readInt(); 
		this.divine = buffer.readInt(); 
		this.force = buffer.readInt(); 
		this.chest = buffer.readInt(); 
		this.gender = buffer.readInt(); 
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getWeapon() {
		return this.weapon;
	}
	
	public void setWeapon(int weapon) {
		this.weapon = weapon;
	}
	
	public int getAvatar() {
		return this.avatar;
	}
	
	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}
	
	public int getDivine() {
		return this.divine;
	}
	
	public void setDivine(int divine) {
		this.divine = divine;
	}
	
	public int getForce() {
		return this.force;
	}
	
	public void setForce(int force) {
		this.force = force;
	}
	
	public int getChest() {
		return this.chest;
	}
	
	public void setChest(int chest) {
		this.chest = chest;
	}
	
	public int getGender() {
		return this.gender;
	}
	
	public void setGender(int gender) {
		this.gender = gender;
	}
	
}