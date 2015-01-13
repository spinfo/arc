package de.uni_koeln.spinfo.arc.editor.shared.service.commands;


import java.util.Date;


public interface Command {

	String getName();

	Date getDate();

	boolean isExecuted();
	void setIsExecuted(boolean isExecuted);

//	void execute(AsyncCallback<T> asyncCallback);
	
	
}
