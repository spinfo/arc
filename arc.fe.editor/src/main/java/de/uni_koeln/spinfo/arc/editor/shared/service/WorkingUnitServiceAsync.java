package de.uni_koeln.spinfo.arc.editor.shared.service;

import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface WorkingUnitServiceAsync {

	Request writeToCommandLog(String commandToLog, AsyncCallback<Void> callback);

	Request isPasswordCorrect(String password, AsyncCallback<Boolean> callback);
}
