package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.coder.AbstractCoder;
import cc.mi.core.coder.StringCoder;

/**
 * 获得Session对象
 **/
public class GetSession extends AbstractCoder  {
	//
	private String sessionkey;
	//玩家id
	private String account;
	//版本
	private String version;

	public GetSession() {
		super(4);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		StringCoder.writeString(buffer, this.sessionkey);
		StringCoder.writeString(buffer, this.account);
		StringCoder.writeString(buffer, this.version);
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.sessionkey = StringCoder.readString(buffer);
		this.account = StringCoder.readString(buffer);
		this.version = StringCoder.readString(buffer);
	}
	
	public String getSessionkey() {
		return this.sessionkey;
	}
	
	public void setSessionkey(String sessionkey) {
		this.sessionkey = sessionkey;
	}
		
	public String getAccount() {
		return this.account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
		
	public String getVersion() {
		return this.version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
		

	public AbstractCoder newInstance() {
		return new GetSession();
	}
}