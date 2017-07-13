package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;
import cc.mi.core.coder.StringCoder;

/**
 * 家族礼物信息
 **/
public class faction_gift_info  {
	//排行
	private int rank;
	//id
	private int id;
	//魅力值
	private int point;
	//感谢标识
	private int thank;
	//女王回复标识
	private int reply;
	//时间
	private int time;
	//count_id
	private int count_id;
	//赠送者guid
	private String guid;
	//赠送者留言
	private String msg;
	//赠送道具信息
	private String item_list;
	//回复信息
	private String reply_list;

	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.rank); 
		buffer.writeInt(this.id); 
		buffer.writeInt(this.point); 
		buffer.writeInt(this.thank); 
		buffer.writeInt(this.reply); 
		buffer.writeInt(this.time); 
		buffer.writeInt(this.count_id); 
		StringCoder.writeString(buffer, this.guid);
		StringCoder.writeString(buffer, this.msg);
		StringCoder.writeString(buffer, this.item_list);
		StringCoder.writeString(buffer, this.reply_list);
	}

	public void decode(ByteBuf buffer) {
		this.rank = buffer.readInt(); 
		this.id = buffer.readInt(); 
		this.point = buffer.readInt(); 
		this.thank = buffer.readInt(); 
		this.reply = buffer.readInt(); 
		this.time = buffer.readInt(); 
		this.count_id = buffer.readInt(); 
		this.guid = StringCoder.readString(buffer);
		this.msg = StringCoder.readString(buffer);
		this.item_list = StringCoder.readString(buffer);
		this.reply_list = StringCoder.readString(buffer);
	}
	
	public int getRank() {
		return this.rank;
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getPoint() {
		return this.point;
	}
	
	public void setPoint(int point) {
		this.point = point;
	}
	
	public int getThank() {
		return this.thank;
	}
	
	public void setThank(int thank) {
		this.thank = thank;
	}
	
	public int getReply() {
		return this.reply;
	}
	
	public void setReply(int reply) {
		this.reply = reply;
	}
	
	public int getTime() {
		return this.time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public int getCount_id() {
		return this.count_id;
	}
	
	public void setCount_id(int count_id) {
		this.count_id = count_id;
	}
	
	public String getGuid() {
		return this.guid;
	}
	
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public String getMsg() {
		return this.msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getItem_list() {
		return this.item_list;
	}
	
	public void setItem_list(String item_list) {
		this.item_list = item_list;
	}
	
	public String getReply_list() {
		return this.reply_list;
	}
	
	public void setReply_list(String reply_list) {
		this.reply_list = reply_list;
	}
	
}