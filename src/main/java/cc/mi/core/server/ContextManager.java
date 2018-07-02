package cc.mi.core.server;

import java.util.HashMap;
import java.util.Map;

import cc.mi.core.callback.Callback;
import io.netty.channel.Channel;

public final class ContextManager {
	private static final Map<Integer, ServerContext> fdContextHash
		= new HashMap<>();
	
	private static final Map<String, Integer> sessionHash = new HashMap<>();
	
	public static void putSession(String account, int fd) {
		sessionHash.put(account, fd);
	}
	
	public static Integer getSessionFd(String account) {
		return sessionHash.get(account);
	}
	
	public static void pushContext(ServerContext context) {
		fdContextHash.put(context.getFd(), context);
	}
	
	public static void removeContext(int fd) {
		fdContextHash.remove(fd);
	}
	
	public static void removeContext(ServerContext context) {
		removeContext(context.getFd());
	}
	
	public static ServerContext getContext(int fd) {
		return fdContextHash.get(fd);
	}
	
	public static void closeSession(Channel channel, int fd) {
		closeSession(channel, fd, false);
	}
	
	public static int getLoginPlayers(Callback<ServerContext> callback) {
		int cnt = 0;
		for (ServerContext context : fdContextHash.values()) {
			if (callback.isMatched(context)) {
				cnt ++;
			}
		}
		
		return cnt;
	}
	
	public static void closeSession(Channel channel, int fd, boolean isForced) {
//		DestroyConnection dc = new DestroyConnection();
//		dc.setFd(fd);
//		dc.setInternalDestFD(MsgConst.MSG_TO_GATE);
//		channel.writeAndFlush(dc);
	}
}
