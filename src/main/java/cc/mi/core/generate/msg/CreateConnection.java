package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;
import cc.mi.core.packet.StringCoder;

/**
 * 客户端连接进来了
 **/
public class CreateConnection extends PacketImpl  {
	//客户端连接网关服的fd
	private int fd;
	//客户端ip
	private String remoteIp;
	//客户端网关
	private int remotePort;

	public CreateConnection() {
		super(5);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.fd);
		StringCoder.writeString(buffer, this.remoteIp);
		buffer.writeInt(this.remotePort);
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.fd = buffer.readInt(); 
		this.remoteIp = StringCoder.readString(buffer);
		this.remotePort = buffer.readInt(); 
	}
	
	public int getFd() {
		return this.fd;
	}
	
	public void setFd(int fd) {
		this.fd = fd;
	}
		
	public String getRemoteIp() {
		return this.remoteIp;
	}
	
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
		
	public int getRemotePort() {
		return this.remotePort;
	}
	
	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}
		

	public PacketImpl newInstance() {
		return new CreateConnection();
	}
}