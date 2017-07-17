package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.coder.AbstractCoder;

/**
 * 服务器注册身份信息
 **/
public class ServerRegIdentity extends AbstractCoder  {
	//身份信息
	private byte identity;

	public ServerRegIdentity() {
		super(3);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeByte(this.identity);
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.identity = buffer.readByte(); 
	}
	
	public byte getIdentity() {
		return this.identity;
	}
	
	public void setIdentity(byte identity) {
		this.identity = identity;
	}
		

	public AbstractCoder newInstance() {
		return new ServerRegIdentity();
	}
}