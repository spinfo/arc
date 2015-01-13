package de.uni_koeln.spinfo.arc.editor.client.mvp.events;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasAppBusyHandlers extends HasHandlers {
  public HandlerRegistration addHasAppBusyHandler(AppBusyHandler handler); 
}
