package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;

/**
 * 通知网关服启动完成
 **/
public class ServerStartFinishMsg extends PacketImpl  {
	//启动状态
	private boolean bootstrap;

	public ServerStartFinishMsg() {
		super(12);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeBoolean(this.bootstrap);
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.bootstrap = buffer.readBoolean(); 
	}
	
	public boolean getBootstrap() {
		return this.bootstrap;
	}
	
	public void setBootstrap(boolean bootstrap) {
		this.bootstrap = bootstrap;
	}
		

	public PacketImpl newInstance() {
		return new ServerStartFinishMsg();
	}
}