package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.coder.AbstractCoder;
import cc.mi.core.coder.StringCoder;
import java.util.List;
import java.util.ArrayList;
import cc.mi.core.generate.stru.item_reward_info;

/**
 * 副本结果
 **/
public class send_instance_result extends AbstractCoder  {
	//副本状态(249:副本失败 250:副本通关 251:副本未通关)
	private byte state;
	//副本cd
	private byte cd;
	//道具列表
	private List<item_reward_info> list;
	//副本类型
	private byte type;
	//额外数据
	private String data;

	public send_instance_result() {
		super(375);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeByte(this.state);
		buffer.writeByte(this.cd);
		buffer.writeShort(this.list.size());
		for (item_reward_info element : this.list) {
			element.encode(buffer);
		}
		buffer.writeByte(this.type);
		StringCoder.writeString(buffer, this.data);
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.state = buffer.readByte(); 
		this.cd = buffer.readByte(); 
		int size = buffer.readUnsignedShort();
		this.list = new ArrayList<>(size);
		for (int i = 0; i < size; ++ i) {
			item_reward_info element = new item_reward_info();
			element.decode(buffer);
			this.list.add(element);
		}
		this.type = buffer.readByte(); 
		this.data = StringCoder.readString(buffer);
	}
	
	public byte getState() {
		return this.state;
	}
	
	public void setState(byte state) {
		this.state = state;
	}
		
	public byte getCd() {
		return this.cd;
	}
	
	public void setCd(byte cd) {
		this.cd = cd;
	}
		
	public List<item_reward_info> getList() {
		return this.list;
	}
	
	public void setList(List<item_reward_info> list) {
		this.list = list;
	}
	
	public byte getType() {
		return this.type;
	}
	
	public void setType(byte type) {
		this.type = type;
	}
		
	public String getData() {
		return this.data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
		

	public AbstractCoder newInstance() {
		return new send_instance_result();
	}
}