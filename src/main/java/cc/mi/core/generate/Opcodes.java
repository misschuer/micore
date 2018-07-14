package cc.mi.core.generate;

import cc.mi.core.packet.Packet;
import cc.mi.core.packet.PacketImpl;

import cc.mi.core.generate.msg.NullAction;
import cc.mi.core.generate.msg.PingPong;
import cc.mi.core.generate.msg.ServerRegOpcode;
import cc.mi.core.generate.msg.CheckSession;
import cc.mi.core.generate.msg.CreateConnection;
import cc.mi.core.generate.msg.OperationResult;
import cc.mi.core.generate.msg.DestroyConnection;
import cc.mi.core.generate.msg.CreateChar;
import cc.mi.core.generate.msg.IdentityServerMsg;
import cc.mi.core.generate.msg.ServerStartFinishMsg;
import cc.mi.core.generate.msg.CloseSession;
import cc.mi.core.generate.msg.BinlogDataModify;
import cc.mi.core.generate.msg.UnitBinlogDataModify;
import cc.mi.core.generate.msg.AddWatchAndCall;
import cc.mi.core.generate.msg.AddTagWatchAndCall;
import cc.mi.core.generate.msg.AddWatch;
import cc.mi.core.generate.msg.AddTagWatch;
import cc.mi.core.generate.msg.InstanceResult;

public final class Opcodes  {
	private static final PacketImpl[] coders = new PacketImpl[376];

	public static final int MSG_NULLACTION = 0; //无效动作
	public static final int MSG_PINGPONG = 1; //测试连接状态
	public static final int MSG_SERVERREGOPCODE = 2; //服务器注册消息号
	public static final int MSG_CHECKSESSION = 4; //验证Session
	public static final int MSG_CREATECONNECTION = 5; //客户端连接进来了
	public static final int MSG_OPERATIONRESULT = 6; //操作结果
	public static final int MSG_DESTROYCONNECTION = 7; //销毁fd
	public static final int MSG_CREATECHAR = 10; //创建角色
	public static final int MSG_IDENTITYSERVERMSG = 11; //通知服务器身份
	public static final int MSG_SERVERSTARTFINISHMSG = 12; //通知网关服启动完成
	public static final int MSG_CLOSESESSION = 13; //通知网关服关闭客户端
	public static final int MSG_BINLOGDATAMODIFY = 15; //对象更新
	public static final int MSG_UNITBINLOGDATAMODIFY = 16; //场景元素对象更新
	public static final int MSG_ADDWATCHANDCALL = 17; //添加binlog并发送
	public static final int MSG_ADDTAGWATCHANDCALL = 18; //添加整组binlog并发送
	public static final int MSG_ADDWATCH = 19; //添加binlog
	public static final int MSG_ADDTAGWATCH = 20; //添加整组binlog
	public static final int MSG_INSTANCERESULT = 375; //副本结果
	
	static {
		coders[MSG_NULLACTION] = new NullAction();
		coders[MSG_PINGPONG] = new PingPong();
		coders[MSG_SERVERREGOPCODE] = new ServerRegOpcode();
		coders[MSG_CHECKSESSION] = new CheckSession();
		coders[MSG_CREATECONNECTION] = new CreateConnection();
		coders[MSG_OPERATIONRESULT] = new OperationResult();
		coders[MSG_DESTROYCONNECTION] = new DestroyConnection();
		coders[MSG_CREATECHAR] = new CreateChar();
		coders[MSG_IDENTITYSERVERMSG] = new IdentityServerMsg();
		coders[MSG_SERVERSTARTFINISHMSG] = new ServerStartFinishMsg();
		coders[MSG_CLOSESESSION] = new CloseSession();
		coders[MSG_BINLOGDATAMODIFY] = new BinlogDataModify();
		coders[MSG_UNITBINLOGDATAMODIFY] = new UnitBinlogDataModify();
		coders[MSG_ADDWATCHANDCALL] = new AddWatchAndCall();
		coders[MSG_ADDTAGWATCHANDCALL] = new AddTagWatchAndCall();
		coders[MSG_ADDWATCH] = new AddWatch();
		coders[MSG_ADDTAGWATCH] = new AddTagWatch();
		coders[MSG_INSTANCERESULT] = new InstanceResult();
	}
	
	private Opcodes(){}
	
	public static Packet newInstance(int opcode) {
		return coders[opcode].newInstance();
	}
	
	public static boolean contains(int opcode) {
		return coders[opcode] != null;
	}
}