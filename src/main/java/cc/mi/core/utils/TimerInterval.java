package cc.mi.core.utils;

import cc.mi.core.impl.Tick;

public class TimerInterval implements Tick {
	private final int interval;
	private int curr = 0;
	
	public TimerInterval(int interval) {
		this.interval = interval;
	}
	
	
	@Override
	public boolean update(int diff) {
		this.curr += diff;
		return this.curr >= diff;
	}

	public void reset() {
		this.curr = 0;
	}

	public int getInterval() {
		return interval;
	}
}
