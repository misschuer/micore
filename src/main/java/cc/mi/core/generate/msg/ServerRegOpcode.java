package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.coder.AbstractCoder;
import java.util.List;
import java.util.ArrayList;

/**
 * 服务器注册消息号
 **/
public class ServerRegOpcode extends AbstractCoder  {
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
		int size = buffer.readUnsignedShort();
		this.opcodes = new ArrayList<>(size);
		for (int i = 0; i < size; ++ i) {
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
	

	public AbstractCoder newInstance() {
		return new ServerRegOpcode();
	}
}