package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;

/**
 * 通知网关服关闭客户端
 **/
public class CloseSession extends PacketImpl  {
	//客户端fd
	private int fd;
	//关闭类型
	private int reasonType;

	public CloseSession() {
		super(13);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.fd);
		buffer.writeInt(this.reasonType);
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.fd = buffer.readInt(); 
		this.reasonType = buffer.readInt(); 
	}
	
	public int getFd() {
		return this.fd;
	}
	
	public void setFd(int fd) {
		this.fd = fd;
	}
		
	public int getReasonType() {
		return this.reasonType;
	}
	
	public void setReasonType(int reasonType) {
		this.reasonType = reasonType;
	}
		

	public PacketImpl newInstance() {
		return new CloseSession();
	}
}