package cc.mi.core.generate.msg;

import io.netty.buffer.ByteBuf;
import cc.mi.core.packet.PacketImpl;
import cc.mi.core.generate.stru.BinlogInfo;

/**
 * 只是测试
 **/
public class TestBinlog extends PacketImpl  {
	//数据
	private BinlogInfo info;

	public TestBinlog() {
		super(15);
	}
	
	@Override
	public void encode(ByteBuf buffer) {
		this.info.encode(buffer);
	}

	@Override
	public void decode(ByteBuf buffer) {
		this.info = new BinlogInfo();
		this.info.decode(buffer);
	}
	
	public BinlogInfo getInfo() {
		return this.info;
	}
	
	public void setInfo(BinlogInfo info) {
		this.info = info;
	}
		

	public PacketImpl newInstance() {
		return new TestBinlog();
	}
}