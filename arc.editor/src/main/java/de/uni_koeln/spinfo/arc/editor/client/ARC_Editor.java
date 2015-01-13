package de.uni_koeln.spinfo.arc.editor.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;

import de.uni_koeln.spinfo.arc.editor.client.mvp.ArcAppController;
import de.uni_koeln.spinfo.arc.editor.client.mvp.ClientSession;
import de.uni_koeln.spinfo.arc.editor.client.mvp.ClientSessionImpl;
import de.uni_koeln.spinfo.arc.editor.client.mvp.Tokens;
import de.uni_koeln.spinfo.arc.editor.client.mvp.login.LoginController;
import de.uni_koeln.spinfo.arc.editor.client.mvp.login.SessionState;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.MainFrameView;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.impl.MainFrameViewImpl;
import de.uni_koeln.spinfo.arc.editor.client.pooling.ClientFactoryImpl;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class ARC_Editor implements EntryPoint {

	SimpleLayoutPanel container = new SimpleLayoutPanel();

	public void onModuleLoad() {

		// ClientSession clientSession = new ClientSessionImpl("testUserId",
		// ClientFactoryImpl.INSTANCE, new SimpleEventBus());
		// ArcAppController controller = new ArcAppController(clientSession);
		// MainFrameView mainFrame = new MainFrameViewImpl();
		// RootLayoutPanel.get().add(mainFrame);
		// controller.go(mainFrame.getContentPanel());
		// History.fireCurrentHistoryState();
		//
		/*
		 * The loginController below is just for handling the login screen in
		 * the welcome area. Afterwards the appcontroller is started after login
		 * currentlz from the LoginView / this must be changed
		 */
		// MainFrameView mainFrame = new MainFrameViewImpl();
		// RootLayoutPanel.get().add(mainFrame);
		// LoginController loginController = new LoginController();
		// loginController.go(mainFrame.getContentPanel());
		// History.fireCurrentHistoryState();

		/*
		 * Uncomment to use pseudo authentification in order to choose a
		 * username which gets saved along with the annotations
		 */
		doLoginWithAuthentification();

		/*
		 * Debug login which automatically logs the user in as guest
		 */
		// doFastLoginForTests();

	}

	private void doLoginWithAuthentification() {
		/*
		 * The loginController below is just for handling the login screen in
		 * the welcome area. Afterwards the appcontroller is started after login
		 * currentlz from the LoginView / this must be changed
		 */
		MainFrameView mainFrame = new MainFrameViewImpl();
		RootLayoutPanel.get().add(mainFrame);
		LoginController loginController = new LoginController();
		loginController.go(mainFrame.getContentPanel());
		History.fireCurrentHistoryState();
	}

	private void doFastLoginForTests() {
		/*
		 * Below is for bypassing the login for testing purposes
		 */
		// System.out.println("trying to log in with user: " +
		// userName.getText() + " password: " + password.getText() );
		ClientSession clientSession = new ClientSessionImpl(
				SessionState.USER_NAME, ClientFactoryImpl.INSTANCE,
				new SimpleEventBus());
		/* without setting the flag nothing happens */
		SessionState.IS_LOGGED_IN = true;

		ArcAppController controller = new ArcAppController(clientSession);
		MainFrameView mainFrame = new MainFrameViewImpl();
		RootLayoutPanel.get().add(mainFrame);
		controller.go(mainFrame.getContentPanel());
		History.newItem(Tokens.WORKING_UNIT_BROWSER, true);
	}

}
