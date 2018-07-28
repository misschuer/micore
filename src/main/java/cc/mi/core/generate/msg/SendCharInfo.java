package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;
import java.util.List;
import java.util.ArrayList;
import cc.mi.core.generate.stru.CharInfo;

/**
 * 发送角色信息
 **/
public class SendCharInfo extends PacketImpl  {
	//角色信息
	private List<CharInfo> chars;

	public SendCharInfo() {
		super(24);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeShort(this.chars.size());
		for (CharInfo element : this.chars) {
			element.encode(buffer);
		}
	}

	@Override
	public void decode(ByteBuf buffer) {
		int charsSize = buffer.readUnsignedShort();
		this.chars = new ArrayList<>(charsSize);
		for (int i = 0; i < charsSize; ++ i) {
			CharInfo element = new CharInfo();
			element.decode(buffer);
			this.chars.add(element);
		}
	}
	
	public List<CharInfo> getChars() {
		return this.chars;
	}
	
	public void setChars(List<CharInfo> chars) {
		this.chars = chars;
	}
	

	public PacketImpl newInstance() {
		return new SendCharInfo();
	}
}