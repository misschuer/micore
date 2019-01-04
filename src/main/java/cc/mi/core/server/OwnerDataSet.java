package cc.mi.core.server;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cc.mi.core.callback.InvokeCallback;
import cc.mi.core.log.CustomLogger;

public class OwnerDataSet {
	static final CustomLogger logger = CustomLogger.getLogger(OwnerDataSet.class);
	private final String owner;
	private final Set<String> allData;
	
	public OwnerDataSet(String owner) {
		this.owner = owner;
		this.allData = new HashSet<>();
	}
	
	public void foreach(InvokeCallback<String> callback) {
		if (callback != null) {
			for (String guid : this.allData) {
				callback.invoke(guid);
			}
		}
	}
	
	public void remove(List<String> guidList) {
		for (String guid : guidList) {
			this.allData.remove(guid);
			logger.devLog("OwnerDataSet remove guid = {}", guid);
		}
	}
	
	public void remove(String binlogId) {
		this.allData.remove(binlogId);
		logger.devLog("OwnerDataSet remove guid = {}", binlogId);
	}
	
	public void add(String guid) {
		this.allData.add(guid);
		logger.devLog("OwnerDataSet add guid = {}", guid);
	}

	public int size() {
		return this.allData.size();
	}
	
	public String getOwner() {
		return owner;
	}
}
