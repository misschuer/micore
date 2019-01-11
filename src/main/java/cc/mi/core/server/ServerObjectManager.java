package cc.mi.core.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cc.mi.core.binlog.data.BinlogData;
import cc.mi.core.callback.InvokeCallback;
import cc.mi.core.constance.BinlogChangeInfo;
import cc.mi.core.constance.BinlogOptType;
import cc.mi.core.generate.msg.BinlogDataModify;
import cc.mi.core.generate.msg.PutObject;
import cc.mi.core.generate.msg.PutObjects;
import cc.mi.core.generate.stru.BinlogInfo;
import cc.mi.core.impl.Tick;
import cc.mi.core.packet.Packet;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public abstract class ServerObjectManager extends BinlogObjectTable implements Tick {
	//以binlog的owner_guid为key，保存相关的所有数据
	protected final Map<String, OwnerDataSet> allOwnerDataSet;
	protected final int serverType;
	
	private final Map<String, InvokeCallback<Void>> createCallbackHash;
	private final Map<String, InvokeCallback<Void>> ownerCreateCallbackHash;
	
	private final Map<String, InvokeCallback<String>> releaseCallbackHash;
	
	protected ServerObjectManager(int serverType) {
		this.serverType = serverType;
		allOwnerDataSet = new HashMap<>();
		this.createCallbackHash = new HashMap<>();
		this.ownerCreateCallbackHash = new HashMap<>();
		this.releaseCallbackHash = new HashMap<>();
	}
	
	public void registerReleaseEvent(String binlogId, InvokeCallback<String> callback) {
		this.releaseCallbackHash.put(binlogId, callback);
	}
	
	public void unregisterReleaseEvent(String binlogId) {
		this.releaseCallbackHash.remove(binlogId);
	}
	
	public void addOwnerCreateCallback(String ownerId, InvokeCallback<Void> callback) {
		this.ownerCreateCallbackHash.put(ownerId, callback);
	}
	
	public void addCreateCallback(String binlogId, InvokeCallback<Void> callback) {
		this.createCallbackHash.put(binlogId, callback);
	}
	
	public void getDataSetAllObject(final String ownerId, final List<BinlogData> result) {
		if (!this.allOwnerDataSet.containsKey(ownerId)) {
			return;
		}
		
		final List<String> removeGuidList = new LinkedList<>();
		final ServerObjectManager self = this;
		OwnerDataSet ds = allOwnerDataSet.get(ownerId);
		ds.foreach(new InvokeCallback<String>() {
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
			// owner回调
			if (ownerCreateCallbackHash.containsKey(ownerId)) {
				ownerCreateCallbackHash.get(ownerId).invoke(null);
			}
		}
		OwnerDataSet ds = allOwnerDataSet.get(ownerId);
		ds.add(binlogId);
		
		// 某个binlog回调
		if (createCallbackHash.containsKey(binlogId)) {
			createCallbackHash.get(binlogId).invoke(null);
		}
	}
	
	public void removeOwnerDataSet(final String ownerId, String binlogId) {
		if (!this.allOwnerDataSet.containsKey(ownerId)) {
			return;
		}
		this.allOwnerDataSet.get(ownerId).remove(binlogId);
	}
	
	public void putObjects(Channel centerChannel, String ownerId, final List<BinlogData> result, InvokeCallback<Boolean> abstractCallback) {
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
	
	public void putObject(Channel centerChannel, String ownerId, BinlogData result, InvokeCallback<Boolean> callback) {
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
	
	protected abstract BinlogData createBinlogData(String guid);
	
	public void parseBinlogInfo(BinlogInfo binlogInfo) {
		String guid = binlogInfo.getBinlogId();
		BinlogData binlogData = this.get(guid);
		if (binlogData == null) {
			binlogData = this.createBinlogData(guid);
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
		} else if (binlogInfo.getState() == BinlogOptType.OPT_DELETE) {
			this.detachObject(binlogData);
			this.removeOwnerDataSet(binlogData.getOwner(), binlogData.getGuid());
		}
	}
	
	public boolean update(int diff) {
		return false;
	}
	
	public Packet getUpdatePacket() {
		final BinlogObjectTable self = this;
		List<BinlogInfo> binlogInfoList = new ArrayList<>();
		BinlogChangeInfo.INSTANCE.foreach(new InvokeCallback<String>() {
			@Override
			public void invoke(String value) {
				BinlogData data = self.get(value);
				if (data != null) {
					BinlogInfo info = data.packUpdateBinlogInfo();
					if (info != null) {
						binlogInfoList.add(info);
					}
				}
			}
		});
		
		if (binlogInfoList.size() == 0) {
			return null;
		}
		
		BinlogDataModify packet = new BinlogDataModify();
		packet.setBinlogInfoList(binlogInfoList);
		
		return packet;
	}
	
	public void removeCentreObject(String binlogId, InvokeCallback<String> callback) {
		
		//TODO:
		this.registerReleaseEvent(binlogId, callback);
		
//		ByteArray& pkt = MallocByteArray(opt_control_);
//		pkt.writeByte(CMD_DEL_OBJ);
//		pkt.writeUTF(key);
//		pkt.writeInt(0);
//		table_->RegisterReleaseEvent(key,cb);
//		send_pkt_array_.push_back(std::make_pair(connection_id_,&pkt));
		
	}
}
