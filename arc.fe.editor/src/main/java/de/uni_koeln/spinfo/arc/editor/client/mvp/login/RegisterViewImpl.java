package de.uni_koeln.spinfo.arc.editor.client.mvp.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

/**
 * A currently unused View which may be used for Registration
 * 
 * @author David Rival
 *
 */
public class RegisterViewImpl extends Composite {

	private static LoginViewImplUiBinder uiBinder = GWT
			.create(LoginViewImplUiBinder.class);

	interface LoginViewImplUiBinder extends UiBinder<Widget, RegisterViewImpl> {
	}

	private WelcomeView welcomeView;

	public RegisterViewImpl(WelcomeView welcomeView) {
		initWidget(uiBinder.createAndBindUi(this));
		this.welcomeView = welcomeView;
	}

	@UiField
	HasText firstName;
	
	@UiField
	HasText lastName;
	
	@UiField
	HasText email;
	
	@UiField
	HasText username;
	
	@UiField
	HasText password;
	
	@UiField
	HasText password2;

	@UiField
	Button backButton;
	
	@UiField
	Button registerButton;

	public void init() {
		firstName.setText("");
		lastName.setText("");
		email.setText("");
		username.setText("");
		password.setText("");
		password2.setText("");
	}

	@UiHandler("registerButton")
	void onloginNow(ClickEvent e) {
		System.out.println("trying to REGISTER in with user: "
				+ username.getText() + " password: " + password.getText());
	}

	@UiHandler("backButton")
	void onBackToWelcome(ClickEvent e) {
		welcomeView.init();
	}

}
