package cc.mi.core.utils;

public class TimerTimestamp {
	private int timestamp;
	
	public TimerTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
	
	public boolean isSuccess() {
		return TimestampUtils.now() >= this.timestamp;
	}
	
	/**
	 * 从现在开始的seconds秒后执行
	 * @param seconds
	 */
	public void doNextAfterSeconds(int seconds) {
		this.timestamp = TimestampUtils.now() + seconds;
	}
	
	/**
	 * 从timestamp开始的seconds秒后执行
	 * @param seconds
	 */
	public void doNextByAddSeconds(int seconds) {
		this.timestamp += seconds;
	}
}
