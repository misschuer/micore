package cc.mi.core.task.base;

import cc.mi.core.callback.Callback;

public abstract class AbstractAsyncTask<T> implements AsyncTask {
	private final Callback<T> callback;
	
	public AbstractAsyncTask(Callback<T> callback) {
		this.callback = callback;
	}
	
	// 实际执行的任务
	protected abstract void doTask(Callback<T> callback);
	
	@Override
	public void run() {
		this.doTask(callback);
	}

}
