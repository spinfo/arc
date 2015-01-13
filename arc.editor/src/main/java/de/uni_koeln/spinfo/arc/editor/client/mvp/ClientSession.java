package de.uni_koeln.spinfo.arc.editor.client.mvp;

import com.google.gwt.event.shared.SimpleEventBus;

import de.uni_koeln.spinfo.arc.editor.client.pooling.ClientFactory;

public interface ClientSession {

	String getUserId();
	ClientFactory getClientFactory();
	SimpleEventBus getEventBus();
//	void setIsLoggedIn(boolean b);
//	boolean getIsLoggedIn();
}
