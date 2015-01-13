package de.uni_koeln.spinfo.arc.editor.client.mvp.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import de.uni_koeln.spinfo.arc.editor.client.pooling.ClientFactoryImpl;

/**
 * The Implementation of the first view which gets loaded into
 * {@link de.uni_koeln.spinfo.arc.editor.client.mvp.views.MainFrameView#getContentPanel()}
 * This implementation has references to ta LoginView, which will be replaced
 * with this view interchangeable unless the user logs in.
 * 
 * @author David Rival
 *
 */
public class WelcomeViewImpl extends Composite implements WelcomeView {

	@UiField
	Button loginButton;

	// @UiField Button registerButton;

	@UiField
	Panel contentPanel;

	@UiField
	Panel headerPanel;
	// @UiField
	// Panel formPanel;

	@UiField
	HasText headerText;

	@UiField
	Button skipLoginButton;

	@UiHandler("loginButton")
	void onLoginButtonPressed(ClickEvent e) {
		loginView.setVisible(true);
		contentPanel.setVisible(false);
	}

	// RegisterViewImpl registerView;
	LoginViewImpl loginView;

	private static InfoViewImplUiBinder uiBinder = GWT
			.create(InfoViewImplUiBinder.class);

	interface InfoViewImplUiBinder extends UiBinder<Widget, WelcomeViewImpl> {
	}

	public WelcomeViewImpl() {
		// registerView = ClientFactoryImpl.INSTANCE.getRegisterView(this);
		loginView = ClientFactoryImpl.INSTANCE.getLoginView(this);
		initWidget(uiBinder.createAndBindUi(this));

		skipLoginButton.setVisible(SessionState.IS_QUICK_LOGIN_BUTTON_WITHOUT_AUTH_VISIBLE);

		headerPanel.add(loginView);
		loginView.setVisible(false);

		// headerPanel.add(registerView);
		// registerView.setVisible(false);
		// formPanel.ssetVisible(false);
	}

	// @UiHandler("registerButton")
	// void onRegisterButtonPressed(ClickEvent e) {
	// registerView.setVisible(true);
	// contentPanel.setVisible(false);
	// }

	public HasWidgets getContentPanel() {
		return contentPanel;
	}

	public HasText getHeaderLabel() {
		return headerText;
	}

	@UiHandler("skipLoginButton")
	void onSkipLogin(ClickEvent e) {
		loginView.loginAsGuest();
	}

	public void init() {
		loginView.setVisible(false);
		// registerView.setVisible(false);
		contentPanel.setVisible(true);
	}

}
