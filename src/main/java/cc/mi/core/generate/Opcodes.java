package cc.mi.core.generate;

import cc.mi.core.coder.Coder;
import cc.mi.core.coder.AbstractCoder;

import cc.mi.core.generate.msg.null_action;
import cc.mi.core.generate.msg.ping_pong;
import cc.mi.core.generate.msg.forced_into;
import cc.mi.core.generate.msg.get_session;
import cc.mi.core.generate.msg.send_instance_result;

public final class Opcodes  {
	private static final AbstractCoder[] coders = new AbstractCoder[376];

	public static final int MSG_NULL_ACTION = 0; //无效动作
	public static final int MSG_PING_PONG = 1; //测试连接状态
	public static final int MSG_FORCED_INTO = 2; //踢掉在线的准备强制登陆
	public static final int MSG_GET_SESSION = 3; //获得Session对象
	public static final int MSG_SEND_INSTANCE_RESULT = 375; //副本结果
	
	static {
		coders[MSG_NULL_ACTION] = new null_action();
		coders[MSG_PING_PONG] = new ping_pong();
		coders[MSG_FORCED_INTO] = new forced_into();
		coders[MSG_GET_SESSION] = new get_session();
		coders[MSG_SEND_INSTANCE_RESULT] = new send_instance_result();
	}
	
	private Opcodes(){}
	
	public static Coder newInstance(int opcode) {
		return coders[opcode].newInstance();
	}
	
	public static boolean contains(int opcode) {
		return coders[opcode] != null;
	}
}