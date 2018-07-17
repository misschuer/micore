package cc.mi.core.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.mi.core.binlog.data.BinlogData;
import cc.mi.core.callback.AbstractCallback;
import cc.mi.core.generate.msg.PutObject;
import cc.mi.core.generate.msg.PutObjects;
import cc.mi.core.generate.stru.BinlogInfo;
import io.netty.channel.Channel;

public class ServerObjectManager extends BinlogObjectTable {
	//以binlog的owner_guid为key，保存相关的所有数据
	private final Map<String, OwnerDataSet> allOwnerDataSet;
	protected final int serverType;
	
	protected ServerObjectManager(int serverType) {
		this.serverType = serverType;
		allOwnerDataSet = new HashMap<>();
	}
	
	public void getDataSetAllObject(final String ownerId, final List<BinlogData> result) {
		if (!this.allOwnerDataSet.containsKey(ownerId)) {
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
	
	public void addOwnerDataSet(final String ownerId, String binlogId) {
		if (!this.allOwnerDataSet.containsKey(ownerId)) {
			this.allOwnerDataSet.put(ownerId, new OwnerDataSet(ownerId));
		}
		OwnerDataSet ds = allOwnerDataSet.get(ownerId);
		ds.add(binlogId);
	}
	
	public void putObjects(Channel centerChannel, String ownerId, final List<BinlogData> result) {
		PutObjects po = new PutObjects();
		List<BinlogInfo> binlogDataList = new ArrayList<>(result.size());
		for (BinlogData binlogData : result) {
			binlogDataList.add(binlogData.packNewBinlogInfo());
		}
		po.setBinlogDataList(binlogDataList);
		po.setOwnerId(ownerId);
		centerChannel.writeAndFlush(po);
	}
	
	public void putObject(Channel centerChannel, String ownerId, BinlogData result) {
		PutObject po = new PutObject();
		po.setBinlogData(result.packNewBinlogInfo());
		po.setOwnerId(ownerId);
		centerChannel.writeAndFlush(po);
	}
}
