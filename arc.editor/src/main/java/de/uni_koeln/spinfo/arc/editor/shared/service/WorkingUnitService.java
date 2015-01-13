package de.uni_koeln.spinfo.arc.editor.shared.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("service")
public interface WorkingUnitService extends RemoteService {

	void writeToCommandLog(String text);

	boolean isPasswordCorrect(String password);

}
