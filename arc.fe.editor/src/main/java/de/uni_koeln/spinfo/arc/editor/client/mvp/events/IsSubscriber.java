package de.uni_koeln.spinfo.arc.editor.client.mvp.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent.Type;


/**
 * Implementing classes are subscriber of an event. This interface should be implemented 
 * to denote that instances add handler to this specific event from the GWT event bus.
 * This Interface should make clear who is listening for which events and to not get lost in loosely bound instances
 * 
 * @author D. Rival
 *
 * @param <T> The event to handle from the GWT eventbus (specially the type is needed)
 * @param <H> the handler of the specified event type
 */
public interface IsSubscriber<T extends Type<?> , H extends EventHandler> {
	/**
	 * This method should be called in order to set up the eventhandler for the specified GWT EventType
	 * 
	 * @param eventType
	 * @param handler
	 */
	public void bindEventTypeWithHandler(T eventType  , H handler);
}
