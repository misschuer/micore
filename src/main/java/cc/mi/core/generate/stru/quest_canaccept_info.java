package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;
import cc.mi.core.coder.StringCoder;

/**
 * 可接任务信息
 **/
public class quest_canaccept_info  {
	//任务ID
	private int quest_id;
	//任务类别
	private byte quest_type;
	//标题
	private String title;
	//接受任务NPC模板id
	private int npc_id;
	//任务等级
	private int quest_level;

	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.quest_id); 
		buffer.writeByte(this.quest_type); 
		StringCoder.writeString(buffer, this.title);
		buffer.writeInt(this.npc_id); 
		buffer.writeInt(this.quest_level); 
	}

	public void decode(ByteBuf buffer) {
		this.quest_id = buffer.readInt(); 
		this.quest_type = buffer.readByte(); 
		this.title = StringCoder.readString(buffer);
		this.npc_id = buffer.readInt(); 
		this.quest_level = buffer.readInt(); 
	}
	
	public int getQuest_id() {
		return this.quest_id;
	}
	
	public void setQuest_id(int quest_id) {
		this.quest_id = quest_id;
	}
	
	public byte getQuest_type() {
		return this.quest_type;
	}
	
	public void setQuest_type(byte quest_type) {
		this.quest_type = quest_type;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getNpc_id() {
		return this.npc_id;
	}
	
	public void setNpc_id(int npc_id) {
		this.npc_id = npc_id;
	}
	
	public int getQuest_level() {
		return this.quest_level;
	}
	
	public void setQuest_level(int quest_level) {
		this.quest_level = quest_level;
	}
	
}