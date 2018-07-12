package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;
import cc.mi.core.packet.StringCoder;

/**
 * 添加binlog并发送
 **/
public class AddWatchAndCall extends PacketImpl  {
	//需要注册的fd(0:表示消息发来的服务器
	private int fd;
	//需要注册的binlogId
	private String guidType;

	public AddWatchAndCall() {
		super(17);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.fd);
		StringCoder.writeString(buffer, this.guidType);
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.fd = buffer.readInt(); 
		this.guidType = StringCoder.readString(buffer);
	}
	
	public int getFd() {
		return this.fd;
	}
	
	public void setFd(int fd) {
		this.fd = fd;
	}
		
	public String getGuidType() {
		return this.guidType;
	}
	
	public void setGuidType(String guidType) {
		this.guidType = guidType;
	}
		

	public PacketImpl newInstance() {
		return new AddWatchAndCall();
	}
}