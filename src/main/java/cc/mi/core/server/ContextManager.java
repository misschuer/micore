package cc.mi.core.server;

import java.util.HashMap;
import java.util.Map;

import cc.mi.core.callback.InvokeCallback;
import cc.mi.core.callback.MatchCallback;
import cc.mi.core.generate.msg.CloseSession;
import io.netty.channel.Channel;

public enum ContextManager {
	INSTANCE;
	private ContextManager() {}
	
	private final Map<Integer, ServerContext> fdContextHash
		= new HashMap<>();
	
	private final Map<String, Integer> sessionHash = new HashMap<>();
	
	public void putSession(String account, int fd) {
		sessionHash.put(account, fd);
	}
	
	public Integer getSessionFd(String account) {
		return sessionHash.get(account);
	}
	
	public void removeSessionFd(String account) {
		sessionHash.remove(account);
	}
	
	public void pushContext(ServerContext context) {
		fdContextHash.put(context.getFd(), context);
	}
	
	public ServerContext removeContext(int fd) {
		return fdContextHash.remove(fd);
	}
	
	public void foreach(InvokeCallback<ServerContext> callback) {
		
	}
	
	public ServerContext removeContext(ServerContext context) {
		return removeContext(context.getFd());
	}
	
	public ServerContext getContext(int fd) {
		return fdContextHash.get(fd);
	}
	
	public int getLoginPlayers(MatchCallback<ServerContext> callback) {
		int cnt = 0;
		for (ServerContext context : fdContextHash.values()) {
			if (callback.isMatched(context)) {
				cnt ++;
			}
		}
		
		return cnt;
	}
	
	public void closeSession(Channel channel, int fd, int reasonType) {
		CloseSession cs = new CloseSession();
		cs.setFd(fd);
		cs.setReasonType(reasonType);
		channel.writeAndFlush(cs);
	}
}
