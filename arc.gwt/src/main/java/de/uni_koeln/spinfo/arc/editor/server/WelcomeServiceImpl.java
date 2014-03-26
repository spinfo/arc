package de.uni_koeln.spinfo.arc.editor.server;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("welcomeService")
public class WelcomeServiceImpl {

	private Logger logger = Logger.getLogger(getClass());

	public String welcome() {
		return "Welcome to ARC...";
	}

}
