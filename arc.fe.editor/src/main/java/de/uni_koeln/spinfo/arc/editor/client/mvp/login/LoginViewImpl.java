package de.uni_koeln.spinfo.arc.editor.client.mvp.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import de.uni_koeln.spinfo.arc.editor.client.mvp.ArcAppController;
import de.uni_koeln.spinfo.arc.editor.client.mvp.ClientSession;
import de.uni_koeln.spinfo.arc.editor.client.mvp.ClientSessionImpl;
import de.uni_koeln.spinfo.arc.editor.client.mvp.Tokens;
import de.uni_koeln.spinfo.arc.editor.client.mvp.events.ui.BusyIndicator;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.MainFrameView;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.impl.MainFrameViewImpl;
import de.uni_koeln.spinfo.arc.editor.client.pooling.ClientFactoryImpl;
import de.uni_koeln.spinfo.arc.editor.shared.service.WorkingUnitService;
import de.uni_koeln.spinfo.arc.editor.shared.service.WorkingUnitServiceAsync;

/**
 * this view is attached to welcome view if the user clicks the logIn-button on the welcome view.
 * This view takes controller functionality by using a RPC-Call to validate the entered password on the server
 * If the passphrase is confirmed a new Controller is instantiated ArcAppControllerfor dispatching further views of this editor
 * 
 * @author David Rival
 *
 */
public class LoginViewImpl extends Composite {

	private static LoginViewImplUiBinder uiBinder = GWT
			.create(LoginViewImplUiBinder.class);

	interface LoginViewImplUiBinder extends UiBinder<Widget, LoginViewImpl> {
	}

	private WelcomeView welcomeView;
	private ClientSession clientSession;
	
	public LoginViewImpl(WelcomeView welcomeView) {
		initWidget(uiBinder.createAndBindUi(this));
		this.welcomeView = welcomeView;
		int namesSize = UserNameConstants.NAMES.values().length;
		int count = 0;
		for (UserNameConstants.NAMES name : UserNameConstants.NAMES.values()) {
			userListBox.insertItem(name.toString(), count);
			count++;
		};
		
		init();
	}
	
//	@UiField	HasText userName;
//	@UiField	HasText password;
	@UiField
	Button loginButton;
	
	@UiField
	PasswordTextBox passwordTextBox;
	
	@UiField
	Button backButton;
	
	@UiField ListBox userListBox;
	
	public void init() {
		SessionState.IS_LOGGED_IN = false;
		passwordTextBox.setText("");
	}
	private final WorkingUnitServiceAsync rpcService = GWT.create(WorkingUnitService.class);
	
	@UiHandler("loginButton")
	void onloginNow(ClickEvent e) {
		int selectedIdx = userListBox.getSelectedIndex();
		String passString = passwordTextBox.getText();
		final String userName = userListBox.getItemText(selectedIdx);
		if (passString.length() > 0) {
				
				rpcService.isPasswordCorrect(passString, new AsyncCallback<Boolean>() {
					@Override
					public void onSuccess(Boolean result) {
						if (result) {
							SessionState.IS_LOGGED_IN = result;
							goAheadAfterLogin(userName);
						} 
						else 
							BusyIndicator.busy("the password for user " + userName + " doesn't match!", "", "");
					}
					
					@Override
					public void onFailure(Throwable caught) {
						BusyIndicator.busy("Sorry! An error occoured while connecting to DB! Please try again or contact the admin.", "", "");
					}
				});		
		}
		else {
			BusyIndicator.busy("please enter a password for user:", userName, "");
		}
	}
	
	private void goAheadAfterLogin(String userName) {
		if (SessionState.IS_LOGGED_IN) {
//			System.out.println("goAheadAfterLogin IS_LOGGED_IN");
			RootLayoutPanel.get().clear();
			// System.out.println("trying to log in with user: " +
			// userName.getText() + " password: " + password.getText() );
			clientSession = new ClientSessionImpl(userName,
					ClientFactoryImpl.INSTANCE, new SimpleEventBus());
			/* without setting the flag nothing happens */
	//				SessionState.IS_LOGGED_IN = true;
			SessionState.USER_NAME = userName;
	
			ArcAppController controller = new ArcAppController(
					clientSession);
			MainFrameView mainFrame = new MainFrameViewImpl();
			RootLayoutPanel.get().add(mainFrame);
			controller.go(mainFrame.getContentPanel());
			History.newItem(Tokens.WORKING_UNIT_BROWSER, true);
			BusyIndicator.busy("loading the working unit browser",
					"this may take a while.. ", "");
			 History.fireCurrentHistoryState();
		}
	}
	
	@UiHandler("backButton")
	void onBackToWelcome(ClickEvent e) {
		welcomeView.init();
	}
	
	public void loginAsGuest() {
		RootLayoutPanel.get().clear();
//		System.out.println("trying to log in with user: " + userName.getText() + " password: " + password.getText() );
		clientSession = new ClientSessionImpl(UserNameConstants.NAMES.guest.toString(), ClientFactoryImpl.INSTANCE, new SimpleEventBus());
		/* without setting the flag nothing happens */
		SessionState.IS_LOGGED_IN = true;
		SessionState.USER_NAME = UserNameConstants.NAMES.guest.toString();
		
		ArcAppController controller = new ArcAppController(clientSession);
		MainFrameView mainFrame = new MainFrameViewImpl();
		RootLayoutPanel.get().add(mainFrame);
		controller.go(mainFrame.getContentPanel());
		History.newItem(Tokens.WORKING_UNIT_BROWSER, true);
	}
}
