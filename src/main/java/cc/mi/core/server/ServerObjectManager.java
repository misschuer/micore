package cc.mi.core.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.mi.core.binlog.data.BinlogData;
import cc.mi.core.callback.AbstractCallback;
import cc.mi.core.callback.Callback;
import cc.mi.core.constance.BinlogOptType;
import cc.mi.core.generate.msg.PutObject;
import cc.mi.core.generate.msg.PutObjects;
import cc.mi.core.generate.stru.BinlogInfo;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class ServerObjectManager extends BinlogObjectTable {
	//以binlog的owner_guid为key，保存相关的所有数据
	protected final Map<String, OwnerDataSet> allOwnerDataSet;
	protected final int serverType;
	
	private final Map<String, Callback<Void>> ownerCallbackHash;
	
	protected ServerObjectManager(int serverType) {
		this.serverType = serverType;
		allOwnerDataSet = new HashMap<>();
		this.ownerCallbackHash = new HashMap<>();
	}
	
	public void addCreateCallback(String ownerId, Callback<Void> callback) {
		this.ownerCallbackHash.put(ownerId, callback);
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
			// 有注册回调事件的就回调
			if (ownerCallbackHash.containsKey(ownerId)) {
				ownerCallbackHash.get(ownerId).invoke(null);
			}
		}
		OwnerDataSet ds = allOwnerDataSet.get(ownerId);
		ds.add(binlogId);
	}
	
	public void putObjects(Channel centerChannel, String ownerId, final List<BinlogData> result, AbstractCallback<Boolean> abstractCallback) {
		PutObjects po = new PutObjects();
		List<BinlogInfo> binlogDataList = new ArrayList<>(result.size());
		for (BinlogData binlogData : result) {
			binlogDataList.add(binlogData.packNewBinlogInfo());
		}
		po.setBinlogDataList(binlogDataList);
		po.setOwnerId(ownerId);
		
		ChannelPromise promise = centerChannel.newPromise();
		promise.addListener(new GenericFutureListener<Future<? super Void>>() {
			@Override
			public void operationComplete(Future<? super Void> future) throws Exception {
				if (abstractCallback != null) {
					abstractCallback.invoke(true);
				}
			}
		});
		centerChannel.writeAndFlush(po, promise);
	}
	
	public void putObject(Channel centerChannel, String ownerId, BinlogData result, Callback<Boolean> callback) {
		PutObject po = new PutObject();
		po.setBinlogData(result.packNewBinlogInfo());
		po.setOwnerId(ownerId);
		
		ChannelPromise promise = centerChannel.newPromise();
		promise.addListener(new GenericFutureListener<Future<? super Void>>() {
			@Override
			public void operationComplete(Future<? super Void> future) throws Exception {
				if (callback != null) {
					callback.invoke(true);
				}
			}
		});
		centerChannel.writeAndFlush(po, promise);
	}
	
	public void parseBinlogInfo(BinlogInfo binlogInfo) {
		String guid = binlogInfo.getBinlogId();
		BinlogData binlogData = this.get(guid);
		if (binlogData == null) {
			binlogData = new BinlogData(1 << 6, 1 << 6);
		}
		List<Integer> intMask = binlogInfo.getIntMask();
		List<Integer> intValueChanged = binlogInfo.getIntValues();
		List<Integer> strMask = binlogInfo.getStrMask();
		List<String>  strValueChanged = binlogInfo.getStrValues();
		if (binlogInfo.getState() == BinlogOptType.OPT_NEW) {
			binlogData.setGuid(binlogInfo.getBinlogId());
			binlogData.onCreateEvent(intMask, intValueChanged, strMask, strValueChanged);
			this.attachObject(binlogData);
			this.addOwnerDataSet(binlogData.getOwner(), binlogData.getGuid());
		} else if (binlogInfo.getState() == BinlogOptType.OPT_UPDATE) {
			binlogData.onUpdateEvent(intMask, intValueChanged, strMask, strValueChanged);
		}
	}
}
