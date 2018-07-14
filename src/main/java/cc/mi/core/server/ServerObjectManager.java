package cc.mi.core.server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.mi.core.binlog.data.BinlogData;
import cc.mi.core.callback.AbstractCallback;

public class ServerObjectManager extends BinlogObjectTable {
	//以binlog的owner_guid为key，保存相关的所有数据
	private final Map<String, OwnerDataSet> allOwnerDataSet;
	
	protected ServerObjectManager() {
		super();
		allOwnerDataSet = new HashMap<>();
	}
	
	public void getDataSetAllObject(final String ownerId, final List<BinlogData> result) {
		if (!allOwnerDataSet.containsKey(ownerId)) {
			return;
		}
		
		final List<String> removeGuidList = new LinkedList<>();
		final ServerObjectManager self = this;
		OwnerDataSet ds = allOwnerDataSet.get(ownerId);
		ds.foreach(new AbstractCallback<String>() {
			@Override
			public void invoke(String binlogId) {
				BinlogData obj = self.get(binlogId);
				if (obj == null) {
					removeGuidList.add(binlogId);
					return;
				}
				result.add(obj);
			}
		});
		
		ds.remove(removeGuidList);
	}
}
