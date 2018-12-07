package cc.mi.core.utils;

import cc.mi.core.callback.ReturnedCallback;
import cc.mi.core.parameter.CallbackParams;

public class TimerIntervalCallback {
	// 唯一id
	private final int id;
	// 定时器
	private final TimerInterval timer;
	
	// 回调的方法
	private final ReturnedCallback<CallbackParams, Boolean> callback;
	// 回调的参数
	private final CallbackParams params;
	
	// 是否可用
	private boolean available = true;
	// 是否新增
	private boolean newable = true;
	
	public TimerIntervalCallback(int id, int interval, ReturnedCallback<CallbackParams, Boolean> callback, CallbackParams params) {
		this.id		= 						   id;
		this.timer	= new TimerInterval(interval);
		this.callback = callback;
		this.params = params;
	}
	
	public boolean invokeIfTimerPassed(int diff) {
		if (this.timer.update(diff)) {
			this.timer.reset();
			return callback.returnedInvoke(params);
		}
		
		return false;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isNew() {
		return newable;
	}

	public void setNew(boolean newable) {
		this.newable = newable;
	}

	public int getId() {
		return id;
	}
}
