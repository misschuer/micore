package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.coder.AbstractCoder;

/**
 * 通知服务器身份
 **/
public class IdentityServerMsg extends AbstractCoder  {
	//服务器身份
	private int serverType;

	public IdentityServerMsg() {
		super(11);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeInt(this.serverType);
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.serverType = buffer.readInt(); 
	}
	
	public int getServerType() {
		return this.serverType;
	}
	
	public void setServerType(int serverType) {
		this.serverType = serverType;
	}
		

	public AbstractCoder newInstance() {
		return new IdentityServerMsg();
	}
}