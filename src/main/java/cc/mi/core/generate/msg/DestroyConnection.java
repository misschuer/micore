package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;

/**
 * 销毁fd
 **/
public class DestroyConnection extends PacketImpl  {
	//客户端连接网关服的fd
	private int fd;

	public DestroyConnection() {
		super(7);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.fd);
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.fd = buffer.readInt(); 
	}
	
	public int getFd() {
		return this.fd;
	}
	
	public void setFd(int fd) {
		this.fd = fd;
	}
		

	public PacketImpl newInstance() {
		return new DestroyConnection();
	}
}