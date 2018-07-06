package cc.mi.core.server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.mi.core.binlog.data.GuidObject;
import cc.mi.core.callback.AbstractCallback;
import cc.mi.core.constance.BinlogSyncMode;
import io.netty.channel.Channel;

public class ServerObjectManager extends GuidObjectTable {
	//以binlog的owner_guid为key，保存相关的所有数据
	private final Map<String, OwnerDataSet> allOwnerDataSet;
	
	protected ServerObjectManager() {
		super(BinlogSyncMode.SYNC_SLAVE);
		allOwnerDataSet = new HashMap<>();
	}
	
	public void getDataSetAllObject(final String guid, List<GuidObject> result) {
		if (!allOwnerDataSet.containsKey(guid)) {
			return;
		}
		
		List<String> removeGuidList = new LinkedList<>();
		OwnerDataSet ds = allOwnerDataSet.get(guid);
		ds.foreach(new AbstractCallback<String>() {
			@Override
			public void invoke(String value) {
				GuidObject obj = get(value);
				if (obj == null) {
					removeGuidList.add(value);
					return;
				}
				result.add(obj);
			}
		});
		
		ds.remove(removeGuidList);
	}
	
	/**
	 *  给当前服务器注册消息
	 * @param channel
	 * @param guidType
	 */
	protected void addWatchAndCall(Channel channel, String guidType) {
		this.addWatchAndCall(channel, 0, guidType);
	}
	
	/**
	 * 给客户端注册消息
	 * @param channel
	 * @param fd
	 * @param guidType
	 */
	protected void addWatchAndCall(Channel channel, int fd, String guidType) {
		
	}
}
