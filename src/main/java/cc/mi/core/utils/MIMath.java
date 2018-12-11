package cc.mi.core.utils;

public enum MIMath {
	INSTANCE;
	
	private MIMath() {}
	
	public double getDistance(float x1, float y1, float x2, float y2) {
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}
}
