package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.coder.AbstractCoder;
import cc.mi.core.coder.StringCoder;

/**
 * 操作结果
 **/
public class OperationResult extends AbstractCoder  {
	//操作类型
	private short type;
	//造成结果的原因
	private short reason;
	//参数
	private String data;

	public OperationResult() {
		super(6);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeShort(this.type);
		buffer.writeShort(this.reason);
		StringCoder.writeString(buffer, this.data);
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.type = buffer.readShort(); 
		this.reason = buffer.readShort(); 
		this.data = StringCoder.readString(buffer);
	}
	
	public short getType() {
		return this.type;
	}
	
	public void setType(short type) {
		this.type = type;
	}
		
	public short getReason() {
		return this.reason;
	}
	
	public void setReason(short reason) {
		this.reason = reason;
	}
		
	public String getData() {
		return this.data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
		

	public AbstractCoder newInstance() {
		return new OperationResult();
	}
}