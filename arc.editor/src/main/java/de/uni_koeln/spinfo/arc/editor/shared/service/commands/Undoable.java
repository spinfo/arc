package de.uni_koeln.spinfo.arc.editor.shared.service.commands;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface Undoable<T> {
	
	void undo(AsyncCallback<T> asyncCallback);
}
