package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.coder.AbstractCoder;

/**
 * 无效动作
 **/
public class NullAction extends AbstractCoder  {

	public NullAction() {
		super(0);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
	}

	@Override
	public void decode(ByteBuf buffer) {
	}
	

	public AbstractCoder newInstance() {
		return new NullAction();
	}
}