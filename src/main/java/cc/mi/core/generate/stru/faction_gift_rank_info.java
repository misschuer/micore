package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;
import cc.mi.core.coder.StringCoder;

/**
 * 家族魅力排行信息
 **/
public class faction_gift_rank_info  {
	//排行
	private int rank;
	//魅力值
	private int point;
	//女王名称
	private String queen_name;
	//家族名称
	private String faction_name;
	//骑士名称
	private String guard_name;
	//家族旗子
	private int faction_flag;
	//女王vip等级
	private int queen_vip;
	//骑士vip等级
	private int guard_vip;

	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.rank); 
		buffer.writeInt(this.point); 
		StringCoder.writeString(buffer, this.queen_name);
		StringCoder.writeString(buffer, this.faction_name);
		StringCoder.writeString(buffer, this.guard_name);
		buffer.writeInt(this.faction_flag); 
		buffer.writeInt(this.queen_vip); 
		buffer.writeInt(this.guard_vip); 
	}

	public void decode(ByteBuf buffer) {
		this.rank = buffer.readInt(); 
		this.point = buffer.readInt(); 
		this.queen_name = StringCoder.readString(buffer);
		this.faction_name = StringCoder.readString(buffer);
		this.guard_name = StringCoder.readString(buffer);
		this.faction_flag = buffer.readInt(); 
		this.queen_vip = buffer.readInt(); 
		this.guard_vip = buffer.readInt(); 
	}
	
	public int getRank() {
		return this.rank;
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public int getPoint() {
		return this.point;
	}
	
	public void setPoint(int point) {
		this.point = point;
	}
	
	public String getQueen_name() {
		return this.queen_name;
	}
	
	public void setQueen_name(String queen_name) {
		this.queen_name = queen_name;
	}
	
	public String getFaction_name() {
		return this.faction_name;
	}
	
	public void setFaction_name(String faction_name) {
		this.faction_name = faction_name;
	}
	
	public String getGuard_name() {
		return this.guard_name;
	}
	
	public void setGuard_name(String guard_name) {
		this.guard_name = guard_name;
	}
	
	public int getFaction_flag() {
		return this.faction_flag;
	}
	
	public void setFaction_flag(int faction_flag) {
		this.faction_flag = faction_flag;
	}
	
	public int getQueen_vip() {
		return this.queen_vip;
	}
	
	public void setQueen_vip(int queen_vip) {
		this.queen_vip = queen_vip;
	}
	
	public int getGuard_vip() {
		return this.guard_vip;
	}
	
	public void setGuard_vip(int guard_vip) {
		this.guard_vip = guard_vip;
	}
	
}