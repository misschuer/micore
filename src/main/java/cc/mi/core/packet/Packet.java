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
	 *  默认是网关服和客户端连接的fd
	 * 当消息只发给内部服务器时的是中心服和内部服务器连接的fd
	 * @return
	 */
	public int getBaseFd();
	public void setBaseFd(int fd);
}
