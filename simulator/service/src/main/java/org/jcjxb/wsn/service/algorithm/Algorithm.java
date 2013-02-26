package org.jcjxb.wsn.service.algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jcjxb.wsn.service.proto.BasicDataType.Event;

public abstract class Algorithm {

	private Map<String, EventHandler> handlerList = new HashMap<String, EventHandler>();

	public List<Event> generate(Event event) {
		EventHandler handler = handlerList.get(event.getType());
		if (handler != null) {
			return handler.handle(event);
		}
		return null;
	}

	public abstract void initHandlers();
	
	public abstract List<Event> start();

	public interface EventHandler {
		public List<Event> handle(Event event);
	}
}
