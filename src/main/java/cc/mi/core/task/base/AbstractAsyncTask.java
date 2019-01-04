package cc.mi.core.task.base;

import cc.mi.core.callback.InvokeCallback;

public abstract class AbstractAsyncTask<T> implements AsyncTask {
	private final InvokeCallback<T> callback;
	
	public AbstractAsyncTask(InvokeCallback<T> callback) {
		this.callback = callback;
	}
	
	// 实际执行的任务
	protected abstract void doTask(InvokeCallback<T> callback);
	
	@Override
	public void run() {
		this.doTask(callback);
	}

}
