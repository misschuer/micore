package cc.mi.core.loginAction;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import cc.mi.core.constance.LoginActionEnum;

public abstract class ContextLoginManager {
	protected Map<String, Queue<LoginActionBase>> actionHash = new HashMap<>();
	
	public void update(int diff) {
		
	}
	
	public abstract void pushAction(String guid, int fd, LoginActionEnum actionType);
}
