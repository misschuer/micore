package cc.mi.core.task;

import cc.mi.core.coder.Coder;
import cc.mi.core.task.base.AbstractCoderTask;
import io.netty.channel.Channel;

public class SendToCenterTask extends AbstractCoderTask {
	private final Channel centerChannel;
	public SendToCenterTask(Channel centerChannel, Coder coder) {
		super(coder);
		this.centerChannel = centerChannel;
	}
	
	@Override
	protected void doTask() {
		if (this.centerChannel != null && !this.centerChannel.isActive()) {
			this.centerChannel.writeAndFlush(coder);
		}
	}

}
