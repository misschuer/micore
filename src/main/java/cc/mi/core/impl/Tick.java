package cc.mi.core.impl;

public interface Tick {
	/**
	 * 进行心跳
	 * @param diff
	 */
	public boolean update(int diff);
}
