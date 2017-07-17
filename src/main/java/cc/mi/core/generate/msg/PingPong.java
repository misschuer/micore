package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.coder.AbstractCoder;

/**
 * 测试连接状态
 **/
public class PingPong extends AbstractCoder  {

	public PingPong() {
		super(1);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
	}

	@Override
	public void decode(ByteBuf buffer) {
	}
	

	public AbstractCoder newInstance() {
		return new PingPong();
	}
}