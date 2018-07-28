package cc.mi.core.generate.stru;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.StringCoder;

/**
 * 玩家角色创建选择信息
 **/
public class CharCreateInfo  {
	//名称
	private String name;
	//模型
	private byte modelId;

	public void encode(ByteBuf buffer) {
		StringCoder.writeString(buffer, this.name);
		buffer.writeByte(this.modelId);
	}

	public void decode(ByteBuf buffer) {
		this.name = StringCoder.readString(buffer);
		this.modelId = buffer.readByte(); 
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
		
	public byte getModelId() {
		return this.modelId;
	}
	
	public void setModelId(byte modelId) {
		this.modelId = modelId;
	}
		
}