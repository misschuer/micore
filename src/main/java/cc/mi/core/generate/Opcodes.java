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
import cc.mi.core.generate.msg.InstanceResult;
import cc.mi.core.generate.msg.CreateChar;
import cc.mi.core.generate.msg.IdentityServerMsg;
import cc.mi.core.generate.msg.ServerStartFinishMsg;
import cc.mi.core.generate.msg.CloseSession;

public final class Opcodes  {
	private static final PacketImpl[] coders = new PacketImpl[376];

	public static final int MSG_NULLACTION = 0; //无效动作
	public static final int MSG_PINGPONG = 1; //测试连接状态
	public static final int MSG_SERVERREGOPCODE = 2; //服务器注册消息号
	public static final int MSG_CHECKSESSION = 4; //验证Session
	public static final int MSG_CREATECONNECTION = 5; //客户端连接进来了
	public static final int MSG_OPERATIONRESULT = 6; //操作结果
	public static final int MSG_DESTROYCONNECTION = 7; //销毁fd
	public static final int MSG_INSTANCERESULT = 375; //副本结果
	public static final int MSG_CREATECHAR = 10; //创建角色
	public static final int MSG_IDENTITYSERVERMSG = 11; //通知服务器身份
	public static final int MSG_SERVERSTARTFINISHMSG = 12; //通知网关服启动完成
	public static final int MSG_CLOSESESSION = 13; //通知网关服关闭客户端
	
	static {
		coders[MSG_NULLACTION] = new NullAction();
		coders[MSG_PINGPONG] = new PingPong();
		coders[MSG_SERVERREGOPCODE] = new ServerRegOpcode();
		coders[MSG_CHECKSESSION] = new CheckSession();
		coders[MSG_CREATECONNECTION] = new CreateConnection();
		coders[MSG_OPERATIONRESULT] = new OperationResult();
		coders[MSG_DESTROYCONNECTION] = new DestroyConnection();
		coders[MSG_INSTANCERESULT] = new InstanceResult();
		coders[MSG_CREATECHAR] = new CreateChar();
		coders[MSG_IDENTITYSERVERMSG] = new IdentityServerMsg();
		coders[MSG_SERVERSTARTFINISHMSG] = new ServerStartFinishMsg();
		coders[MSG_CLOSESESSION] = new CloseSession();
	}
	
	private Opcodes(){}
	
	public static Packet newInstance(int opcode) {
		return coders[opcode].newInstance();
	}
	
	public static boolean contains(int opcode) {
		return coders[opcode] != null;
	}
}