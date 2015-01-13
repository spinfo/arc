package de.uni_koeln.spinfo.arc.editor.client.mvp.login;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasWidgets;

import de.uni_koeln.spinfo.arc.editor.client.mvp.Tokens;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.WorkingUnitBrowserInfoView;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.impl.WorkingUnitBrowserInfoViewImpl;
import de.uni_koeln.spinfo.arc.editor.client.pooling.ClientFactoryImpl;

/**
 * First controller which dispatches to the Home-Token in in the Browser URL.
 * Once the user has logged in the controller won't be needed unless the User
 * has logged out / or refreshed the page
 * 
 * @author David Rival
 *
 */
public class LoginController implements ValueChangeHandler<String> {

	private HasWidgets container;

	public LoginController() {
		bind();
	};

	public void go(HasWidgets container) {
		this.container = container;
	}

	private void bind() {
		History.addValueChangeHandler(this);
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		// GWT.log("AppController received ValueChangeEvent from History " +
		// token);

		if (token != null) {
			if (token.equals(Tokens.HOME)) {
				doDisplayWelcomeView();
			} else {
				container.clear();
				WorkingUnitBrowserInfoView infoView = new WorkingUnitBrowserInfoViewImpl();
				infoView.setHeaderText("Error! Currently not logged in!");
				infoView.setContentText("Please click on the button to advance to welcome screen and log in. Thank you!");
				Button backButton = new Button("- back to welcome screen -");
				backButton.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						History.newItem(Tokens.HOME, true);
					}
				});

				infoView.getContentPanel().add(backButton);
				container.add(infoView.asWidget());
			}
		}

	}

	// private final AnnotationModelServiceAsync rpcService =
	// GWT.create(AnnotationModelService.class);

	private void doDisplayWelcomeView() {
		container.clear();
		container.add(ClientFactoryImpl.INSTANCE.getWelcomeView().asWidget());
		// RootLayoutPanel.get().clear();
		// MainFrameView mainFrame = new MainFrameViewImpl();
		// RootLayoutPanel.get().add(mainFrame);
		// LoginController loginController = new LoginController();
		// loginController.go(mainFrame.getContentPanel());

	}

}
