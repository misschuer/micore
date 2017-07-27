package cc.mi.core.binlog.stru;

public abstract class BinlogStru {
	//操作类型 BinlogOptType
	private final byte optType;
	// 数据类型 BinlogDataType
	private final byte dataType;
	// 对象下标
	private final int indx;
	
	public BinlogStru(byte optType, byte dataType, int indx) {
		this.optType  =  optType;
		this.dataType = dataType;
		this.indx 	  = 	indx;
	}

	public byte getOptType() {
		return optType;
	}

	public byte getDataType() {
		return dataType;
	}

	public int getIndx() {
		return indx;
	}
}
