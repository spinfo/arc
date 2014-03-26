package de.uni_koeln.spinfo.arc.editor.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc/welcome")
public interface WelcomeService extends RemoteService {
	
	public String welcome();

}
