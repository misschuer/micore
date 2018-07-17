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
	
	public void remove(int fd) {
		Set<String> set = this.fdHash.removeAll(fd);
		for (String bid : set) {
			this.biHash.remove(bid, fd);
		}
		
		Set<String> set2 = this.fdOwnerHash.removeAll(fd);
		for (String bid : set2) {
			this.biOwnerHash.remove(bid, fd);
		}
	}
}
