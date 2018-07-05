package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;
import cc.mi.core.packet.StringCoder;

/**
 * 验证Session
 **/
public class CheckSession extends PacketImpl  {
	//客户端连接网关服的fd
	private int fd;
	//会话密钥
	private String sessionkey;

	public CheckSession() {
		super(4);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.fd);
		StringCoder.writeString(buffer, this.sessionkey);
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.fd = buffer.readInt(); 
		this.sessionkey = StringCoder.readString(buffer);
	}
	
	public int getFd() {
		return this.fd;
	}
	
	public void setFd(int fd) {
		this.fd = fd;
	}
		
	public String getSessionkey() {
		return this.sessionkey;
	}
	
	public void setSessionkey(String sessionkey) {
		this.sessionkey = sessionkey;
	}
		

	public PacketImpl newInstance() {
		return new CheckSession();
	}
}