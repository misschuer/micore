package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.coder.AbstractCoder;

/**
 * 通知网关服启动完成
 **/
public class ServerStartFinishMsg extends AbstractCoder  {

	public ServerStartFinishMsg() {
		super(12);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
	}

	@Override
	public void decode(ByteBuf buffer) {
	}
	

	public AbstractCoder newInstance() {
		return new ServerStartFinishMsg();
	}
}