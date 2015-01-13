package de.uni_koeln.spinfo.arc.editor.client.mvp.events;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;


public interface HasPageEditorModeChangeEventHandlers extends HasHandlers {
	public HandlerRegistration addHasPageEditorModeChangeEventHandlers(PageEditorModeChangeEventHandler handler);
}
