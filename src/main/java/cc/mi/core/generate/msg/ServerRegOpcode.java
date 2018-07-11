package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;
import java.util.List;
import java.util.ArrayList;

/**
 * 服务器注册消息号
 **/
public class ServerRegOpcode extends PacketImpl  {
	//消息号
	private List<Integer> opcodes;

	public ServerRegOpcode() {
		super(2);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		buffer.writeShort(this.opcodes.size());
		for (int element : this.opcodes) {
			buffer.writeInt(element);
		}
	}

	@Override
	public void decode(ByteBuf buffer) {
		int opcodesSize = buffer.readUnsignedShort();
		this.opcodes = new ArrayList<>(opcodesSize);
		for (int i = 0; i < opcodesSize; ++ i) {
			int element = buffer.readInt();
			this.opcodes.add(element);
		}
	}
	
	public List<Integer> getOpcodes() {
		return this.opcodes;
	}
	
	public void setOpcodes(List<Integer> opcodes) {
		this.opcodes = opcodes;
	}
	

	public PacketImpl newInstance() {
		return new ServerRegOpcode();
	}
}