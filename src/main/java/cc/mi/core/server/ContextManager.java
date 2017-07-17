package cc.mi.core.server;

import java.util.HashMap;
import java.util.Map;

public final class ContextManager {
	private static final Map<Integer, ServerContext> fdContextHash
		= new HashMap<>();
	
	public static void pushContext(int fd, ServerContext context) {
		fdContextHash.put(fd, context);
	}
	
	public static void removeContext(int fd) {
		fdContextHash.remove(fd);
	}
	
	public static void removeContext(ServerContext context) {
		removeContext(context.getFd());
	}
}
