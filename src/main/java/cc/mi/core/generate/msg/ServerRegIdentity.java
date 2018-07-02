package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;

/**
 * 服务器注册身份信息
 **/
public class ServerRegIdentity extends PacketImpl  {
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
		

	public PacketImpl newInstance() {
		return new ServerRegIdentity();
	}
}