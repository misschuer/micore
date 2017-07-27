package cc.mi.core.binlog.stru;

import cc.mi.core.constance.BinlogDataType;

public class BinlogStruValueStr extends BinlogStru {
	private final String value;
	
	public BinlogStruValueStr(byte optType, int indx, String value) {
		super(optType, BinlogDataType.TYPE_STRING, indx);
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
