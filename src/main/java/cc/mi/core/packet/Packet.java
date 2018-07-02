package cc.mi.core.packet;


import io.netty.buffer.ByteBuf;

public interface Packet {
	void onEncode(ByteBuf buffer);
	void onDecode(ByteBuf buffer);
	/**
	 * 消息码
	 * @return
	 */
	public int getOpcode();
	
	/**
	 * 当消息发送给网关服的时候 作为客户端的fd
	 * 当消息发送给中心服的时候 
	 * 	内部服发时作为内部服务器的fd
	 * @return
	 */
	public int getFD();
	public void setFD(int fd);
}
