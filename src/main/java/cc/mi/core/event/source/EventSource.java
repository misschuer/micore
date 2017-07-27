package cc.mi.core.event.source;

import cc.mi.core.event.events.Event;
import cc.mi.core.event.listener.EventListener;

public interface EventSource {
	
    public void addEventListener(EventListener<? extends Event> listener);
    
    public void removeEventListener(EventListener<? extends Event> listener);
    
    public void dispatchEvent(Event event);
}
