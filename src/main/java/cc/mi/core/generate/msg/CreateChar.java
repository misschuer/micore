package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.coder.AbstractCoder;
import cc.mi.core.generate.stru.CharCreateInfo;

/**
 * 创建角色
 **/
public class CreateChar extends AbstractCoder  {
	//角色信息
	private CharCreateInfo charData;

	public CreateChar() {
		super(10);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		this.charData.encode(buffer);
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.charData = new CharCreateInfo();
		this.charData.decode(buffer);
	}
	
	public CharCreateInfo getCharData() {
		return this.charData;
	}
	
	public void setCharData(CharCreateInfo charData) {
		this.charData = charData;
	}
		

	public AbstractCoder newInstance() {
		return new CreateChar();
	}
}