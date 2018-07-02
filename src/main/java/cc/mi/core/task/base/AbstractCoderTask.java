package cc.mi.core.task.base;

import cc.mi.core.coder.Packet;

public abstract class AbstractCoderTask extends AbstractTask {
	protected final Packet coder;
	
	public AbstractCoderTask(Packet coder) {
		this.coder = coder;
	}
}
