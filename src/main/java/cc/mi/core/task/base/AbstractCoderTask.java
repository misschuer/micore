package cc.mi.core.task.base;

import cc.mi.core.coder.Coder;

public abstract class AbstractCoderTask extends AbstractTask {
	protected final Coder coder;
	
	public AbstractCoderTask(Coder coder) {
		this.coder = coder;
	}
}
