package cc.mi.core.constance;

public class BinlogOptType {
	// 这些修改某些值
	public static final byte OPT_SET   = 0x01;
	
	//说明这是一个新对象
	public static final byte OPT_NEW = 0x10;
	//对象移除;带字符串guid
	public static final byte OPT_DELETE = 0x20;
	//覆盖式更新
	public static final byte OPT_UPDATE = 0x40;
}
