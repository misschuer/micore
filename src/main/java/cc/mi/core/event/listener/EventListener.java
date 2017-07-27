package cc.mi.core.event.listener;

import cc.mi.core.event.events.Event;

public interface EventListener<T extends Event> {
	 public void handleEvent(T event);
}
