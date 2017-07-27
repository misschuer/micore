package cc.mi.core.binlog.stru;

import cc.mi.core.constance.BinlogDataType;

public class BinlogStruValueInt extends BinlogStru {
	private final int value;
	
	public BinlogStruValueInt(byte optType, int indx, int value) {
		super(optType, BinlogDataType.TYPE_INT, indx);
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
