package cc.mi.core.utils;

import java.util.Iterator;
import java.util.Set;

public class BinlogWatcher {
	//fd mappin binlogid
	final RelateHash<Integer, String> fdHash = new RelateHash<>();
	//binlogid mappin fd
	final RelateHash<String, Integer> biHash = new RelateHash<>();
	
	//fd mappin binlogOnwerid
	final RelateHash<Integer, String> fdOwnerHash = new RelateHash<>();
	//binlogOnwerid mappin fd
	final RelateHash<String, Integer> biOwnerHash = new RelateHash<>();
	
	public BinlogWatcher() {}
	
	public Iterator<Integer> binOwnerWatchIterator(String ownerId) {
		return biOwnerHash.relatedList(ownerId);
	}
	
	public Iterator<Integer> binWatchIterator(String ownerId) {
		return biHash.relatedList(ownerId);
	}
	
	public void addWatch(int fd, String binlogId) {
		this.fdHash.addWatch(fd, binlogId);
		this.biHash.addWatch(binlogId, fd);
	}
	
	public void addTagWatch(int fd, String binlogOwnerId) {
		this.fdOwnerHash.addWatch(fd, binlogOwnerId);
		this.biOwnerHash.addWatch(binlogOwnerId, fd);
	}
	
	/**
	 * 删除所有的关系
	 * @param binlogId
	 */
	public void remove(String binlogId) {
		// 删除 hash的数据
		Set<Integer> set = this.biHash.removeAll(binlogId);
		for (Integer fd : set) {
			this.fdHash.remove(fd, binlogId);
		}
		
		// 删除OwnerHash的数据
		set = this.biOwnerHash.removeAll(binlogId);
		for (Integer fd : set) {
			this.fdOwnerHash.remove(fd, binlogId);
		}
	}
}
