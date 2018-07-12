package cc.mi.core.server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.mi.core.binlog.data.BinlogModifier;
import cc.mi.core.callback.AbstractCallback;
import io.netty.channel.Channel;

public class ServerObjectManager extends GuidObjectTable {
	//以binlog的owner_guid为key，保存相关的所有数据
	private final Map<String, OwnerDataSet> allOwnerDataSet;
	
	protected ServerObjectManager() {
		super();
		allOwnerDataSet = new HashMap<>();
	}
	
	public void getDataSetAllObject(final String guid, List<BinlogModifier> result) {
		if (!allOwnerDataSet.containsKey(guid)) {
			return;
		}
		
		List<String> removeGuidList = new LinkedList<>();
		OwnerDataSet ds = allOwnerDataSet.get(guid);
		ds.foreach(new AbstractCallback<String>() {
			@Override
			public void invoke(String value) {
				BinlogModifier obj = get(value);
				if (obj == null) {
					removeGuidList.add(value);
					return;
				}
				result.add(obj);
			}
		});
		
		ds.remove(removeGuidList);
	}
}
