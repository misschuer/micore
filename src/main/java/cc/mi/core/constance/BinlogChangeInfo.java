package cc.mi.core.constance;

import java.util.HashSet;
import java.util.Set;

import cc.mi.core.callback.Callback;

public enum BinlogChangeInfo {
	INSTANCE;
	
	private final Set<String> set = new HashSet<>();
	private BinlogChangeInfo() {}
	
	public void foreach(Callback<String> callback) {
		for (String binlogId : this.set) {
			callback.invoke(binlogId);
		}
		this.set.clear();
	}
	
	public void add(String binlogId) {
		this.set.add(binlogId);
	}
}
