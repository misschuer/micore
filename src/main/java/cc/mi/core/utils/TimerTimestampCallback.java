package cc.mi.core.utils;

import cc.mi.core.callback.Callback;
import cc.mi.core.parameter.CallbackParams;

public class TimerTimestampCallback {
	// 唯一id
	private final int id;
	// 定时器
	private final TimerTimestamp timer;
	
	// 回调的方法
	private final Callback<CallbackParams> callback;
	// 回调的参数
	private final CallbackParams params;
	
	// 是否失效
	private boolean available = true;
	
	public TimerTimestampCallback(int id, int timestamp, Callback<CallbackParams> callback, CallbackParams params) {
		this.id		= 						   id;
		this.timer	= new TimerTimestamp(timestamp);
		this.callback = callback;
		this.params = params;
	}
	
	public boolean invokeIfTimerPassed() {
		if (this.timer.isSuccess()) {
			callback.invoke(params);
			return false;
		}
		
		return true;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getId() {
		return id;
	}
}
