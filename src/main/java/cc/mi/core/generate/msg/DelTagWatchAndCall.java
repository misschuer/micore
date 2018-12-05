package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;
import cc.mi.core.packet.StringCoder;

/**
 * 删除注册监听
 **/
public class DelTagWatchAndCall extends PacketImpl  {
	//需要注册的fd(0:表示消息发来的服务器
	private int fd;
	//需要注册的binlog拥有者id
	private String ownerTag;

	public DelTagWatchAndCall() {
		super(36);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.fd);
		StringCoder.writeString(buffer, this.ownerTag);
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.fd = buffer.readInt(); 
		this.ownerTag = StringCoder.readString(buffer);
	}
	
	public int getFd() {
		return this.fd;
	}
	
	public void setFd(int fd) {
		this.fd = fd;
	}
		
	public String getOwnerTag() {
		return this.ownerTag;
	}
	
	public void setOwnerTag(String ownerTag) {
		this.ownerTag = ownerTag;
	}
		

	public PacketImpl newInstance() {
		return new DelTagWatchAndCall();
	}
}