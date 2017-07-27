package cc.mi.core.generate;

import cc.mi.core.coder.Coder;
import cc.mi.core.coder.AbstractCoder;

import cc.mi.core.generate.msg.NullAction;
import cc.mi.core.generate.msg.PingPong;
import cc.mi.core.generate.msg.ServerRegOpcode;
import cc.mi.core.generate.msg.ServerRegIdentity;
import cc.mi.core.generate.msg.GetSession;
import cc.mi.core.generate.msg.CreateConnection;
import cc.mi.core.generate.msg.OperationResult;
import cc.mi.core.generate.msg.DestroyConnection;
import cc.mi.core.generate.msg.InstanceResult;
import cc.mi.core.generate.msg.CreateChar;

public final class Opcodes  {
	private static final AbstractCoder[] coders = new AbstractCoder[376];

	public static final int MSG_NULLACTION = 0; //无效动作
	public static final int MSG_PINGPONG = 1; //测试连接状态
	public static final int MSG_SERVERREGOPCODE = 2; //服务器注册消息号
	public static final int MSG_SERVERREGIDENTITY = 3; //服务器注册身份信息
	public static final int MSG_GETSESSION = 4; //获得Session对象
	public static final int MSG_CREATECONNECTION = 5; //客户端连接进来了
	public static final int MSG_OPERATIONRESULT = 6; //操作结果
	public static final int MSG_DESTROYCONNECTION = 7; //销毁fd
	public static final int MSG_INSTANCERESULT = 375; //副本结果
	public static final int MSG_CREATECHAR = 10; //创建角色
	
	static {
		coders[MSG_NULLACTION] = new NullAction();
		coders[MSG_PINGPONG] = new PingPong();
		coders[MSG_SERVERREGOPCODE] = new ServerRegOpcode();
		coders[MSG_SERVERREGIDENTITY] = new ServerRegIdentity();
		coders[MSG_GETSESSION] = new GetSession();
		coders[MSG_CREATECONNECTION] = new CreateConnection();
		coders[MSG_OPERATIONRESULT] = new OperationResult();
		coders[MSG_DESTROYCONNECTION] = new DestroyConnection();
		coders[MSG_INSTANCERESULT] = new InstanceResult();
		coders[MSG_CREATECHAR] = new CreateChar();
	}
	
	private Opcodes(){}
	
	public static Coder newInstance(int opcode) {
		return coders[opcode].newInstance();
	}
	
	public static boolean contains(int opcode) {
		return coders[opcode] != null;
	}
}