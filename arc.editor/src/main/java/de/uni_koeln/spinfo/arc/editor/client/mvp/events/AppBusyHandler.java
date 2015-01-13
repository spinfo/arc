package de.uni_koeln.spinfo.arc.editor.client.mvp.events;

import com.google.gwt.event.shared.EventHandler;


public interface AppBusyHandler extends EventHandler {
	  public void onAppBusyEvent(AppBusyEvent event);
}
