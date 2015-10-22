package de.uni_koeln.spinfo.arc.editor.client.mvp.events;

import com.google.gwt.event.shared.GwtEvent;


/**
 * Implementing classes are Publisher of a event. This interface should be implemented 
 * to denote that instances can send events typically on the GWT event bus
 * 
 * @author D. Rival
 *
 * @param <T> The event to be published on the GWT event bus
 */
public interface IsPublisher<E extends GwtEvent<?>> {
	/**
	 * This Method should redirect the event to the eventbus by calling 
	 * something like EventBus.fireEvent(event);
	 * 
	 * @param event the GWT to be published
	 */
	public void publishEvent(E event);

}
