package de.uni_koeln.spinfo.arc.editor.client.mvp;

import com.google.gwt.event.shared.SimpleEventBus;

import de.uni_koeln.spinfo.arc.editor.client.pooling.ClientFactory;

public class ClientSessionImpl implements ClientSession {

	private String userId;
	private ClientFactory clientFactory;
	private SimpleEventBus eventBus;
	
	public ClientSessionImpl(String userId, ClientFactory clientFactory, SimpleEventBus eventBus) {
		this.userId = userId;
		this.clientFactory = clientFactory;
		this.eventBus = eventBus;
	}
	
	@Override
	public String getUserId() {
		return userId;
	}
	
	@Override
	public ClientFactory getClientFactory() {
		return clientFactory;
	}

	@Override
	public SimpleEventBus getEventBus() {
		return this.eventBus;
	}



	
}
