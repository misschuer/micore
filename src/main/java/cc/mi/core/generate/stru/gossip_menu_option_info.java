package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;
import cc.mi.core.coder.StringCoder;

/**
 * 闲聊选项结构体
 **/
public class gossip_menu_option_info  {
	//id
	private int id;
	//选项icon图标
	private int option_icon;
	//选项文本
	private String option_title;

	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.id); 
		buffer.writeInt(this.option_icon); 
		StringCoder.writeString(buffer, this.option_title);
	}

	public void decode(ByteBuf buffer) {
		this.id = buffer.readInt(); 
		this.option_icon = buffer.readInt(); 
		this.option_title = StringCoder.readString(buffer);
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getOption_icon() {
		return this.option_icon;
	}
	
	public void setOption_icon(int option_icon) {
		this.option_icon = option_icon;
	}
	
	public String getOption_title() {
		return this.option_title;
	}
	
	public void setOption_title(String option_title) {
		this.option_title = option_title;
	}
	
}