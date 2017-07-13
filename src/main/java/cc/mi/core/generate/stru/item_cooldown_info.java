package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;

/**
 * 物品冷却
 **/
public class item_cooldown_info  {
	//物品摸版
	private int item;
	//冷却时间
	private int cooldown;

	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.item); 
		buffer.writeInt(this.cooldown); 
	}

	public void decode(ByteBuf buffer) {
		this.item = buffer.readInt(); 
		this.cooldown = buffer.readInt(); 
	}
	
	public int getItem() {
		return this.item;
	}
	
	public void setItem(int item) {
		this.item = item;
	}
	
	public int getCooldown() {
		return this.cooldown;
	}
	
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
	
}