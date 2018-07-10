package cc.mi.core.utils;

public class BinlogWatcher {
	//fd mappin binlogid
	final RelateHash<Integer, String> fdHash = new RelateHash<>();
	//binlogid mappin fd
	final RelateHash<String, Integer> biHash = new RelateHash<>();
	
	public BinlogWatcher() {}

	public RelateHash<Integer, String> getFdHash() {
		return fdHash;
	}

	public RelateHash<String, Integer> getBiHash() {
		return biHash;
	}
}
