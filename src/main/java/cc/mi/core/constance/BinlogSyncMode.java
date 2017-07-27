package cc.mi.core.constance;

public class BinlogSyncMode {
	//不同步;即不产生同步事件
	public static final int SYNC_NONE		= 0;
	//对象为主模式，所有的操作产生成 覆盖模式
	public static final int SYNC_MASTER		= 0x01;
	//对象为从模式，操作产生 为binlog模式
	public static final int SYNC_SLAVE		= 0x02;
	//镜像模式，暂时没用
	public static final int SYNC_MIRROR		= 0x04;
	//对象更新模式
	public static final int SYNC_UPDATEDATA = 0x08;
	//本地做主模式，不接受远程同步，为了解决
	public static final int SYNC_LOCAL		= 0x10;
}
