package cc.mi.core.loginAction;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import cc.mi.core.constance.LoginActionEnum;
import cc.mi.core.impl.Tick;

public abstract class ContextLoginManager implements Tick {
	protected Map<String, Queue<LoginActionBase>> actionHash = new HashMap<>();
	
	public boolean update(int diff) {
		List<String> removedGuid = new LinkedList<>();
		for (Queue<LoginActionBase> queue: actionHash.values()) {
			LoginActionBase action = queue.peek();
			// false 表示操作完成了
			if (!action.update(diff)) {
				queue.poll();
				if (queue.isEmpty()) {
					removedGuid.add(action.getGuid());
				}
			}
		}
		//删除值没了的选项
		for (String guid : removedGuid) {
			this.actionHash.remove(guid);
		}
		
		return true;
	}
	
	public abstract void pushAction(String guid, int fd, LoginActionEnum actionType);
}
