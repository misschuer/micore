package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;
import cc.mi.core.packet.StringCoder;

/**
 * 操作结果
 **/
public class OperationResult extends PacketImpl  {
	//操作类型(填表)
	private int type;
	//参数(如果需要填充的话)
	private String data;

	public OperationResult() {
		super(6);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.type);
		StringCoder.writeString(buffer, this.data);
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.type = buffer.readInt(); 
		this.data = StringCoder.readString(buffer);
	}
	
	public int getType() {
		return this.type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
		
	public String getData() {
		return this.data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
		

	public PacketImpl newInstance() {
		return new OperationResult();
	}
}