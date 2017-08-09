package cc.mi.core.server;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cc.mi.core.callback.Callback;

public class OwnerDataSet {
	private final String owner;
	private final Set<String> allData;
	
	public OwnerDataSet(String owner) {
		this.owner = owner;
		this.allData = new HashSet<>();
	}
	
	public void foreach(Callback<String> callback) {
		if (callback != null) {
			for (String guid : this.allData) {
				callback.invoke(guid);
			}
		}
	}
	
	public void remove(List<String> guidList) {
		for (String guid : guidList) {
			this.allData.remove(guid);
		}
	}

	public String getOwner() {
		return owner;
	}
}
