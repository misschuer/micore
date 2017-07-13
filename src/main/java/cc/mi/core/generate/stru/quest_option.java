package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;
import cc.mi.core.coder.StringCoder;

/**
 * 任务菜单
 **/
public class quest_option  {
	//任务id
	private int quest_id;
	//图标
	private int quest_icon;
	//任务等级
	private short quest_level;
	//任务标题
	private String quest_title;
	//标识
	private int flags;

	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.quest_id); 
		buffer.writeInt(this.quest_icon); 
		buffer.writeShort(this.quest_level); 
		StringCoder.writeString(buffer, this.quest_title);
		buffer.writeInt(this.flags); 
	}

	public void decode(ByteBuf buffer) {
		this.quest_id = buffer.readInt(); 
		this.quest_icon = buffer.readInt(); 
		this.quest_level = buffer.readShort(); 
		this.quest_title = StringCoder.readString(buffer);
		this.flags = buffer.readInt(); 
	}
	
	public int getQuest_id() {
		return this.quest_id;
	}
	
	public void setQuest_id(int quest_id) {
		this.quest_id = quest_id;
	}
	
	public int getQuest_icon() {
		return this.quest_icon;
	}
	
	public void setQuest_icon(int quest_icon) {
		this.quest_icon = quest_icon;
	}
	
	public short getQuest_level() {
		return this.quest_level;
	}
	
	public void setQuest_level(short quest_level) {
		this.quest_level = quest_level;
	}
	
	public String getQuest_title() {
		return this.quest_title;
	}
	
	public void setQuest_title(String quest_title) {
		this.quest_title = quest_title;
	}
	
	public int getFlags() {
		return this.flags;
	}
	
	public void setFlags(int flags) {
		this.flags = flags;
	}
	
}