package de.uni_koeln.spinfo.arc.editor.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface WelcomeServiceAsync {
	
	public void welcome(AsyncCallback<String> callback);

}
