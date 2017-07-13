package cc.mi.core.task;

public abstract class AbstractTask implements Task {
	
	// 实际执行的任务
	protected abstract void doTask();
	
	@Override
	public void run() {
		this.doTask();
	}
}
