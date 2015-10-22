package de.uni_koeln.spinfo.arc.editor.client.mvp.views.impl;

import org.gwtbootstrap3.client.ui.Label;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import de.uni_koeln.spinfo.arc.editor.client.mvp.Tokens;
import de.uni_koeln.spinfo.arc.editor.client.mvp.login.LoginController;
import de.uni_koeln.spinfo.arc.editor.client.mvp.login.MenuDialogViewImpl;
import de.uni_koeln.spinfo.arc.editor.client.mvp.login.SessionState;
import de.uni_koeln.spinfo.arc.editor.client.mvp.login.UserNameConstants;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.MainFrameView;

public class MainFrameViewImpl extends Composite implements MainFrameView {

	private static MainFrameViewImplUiBinder uiBinder = GWT
			.create(MainFrameViewImplUiBinder.class);

	interface MainFrameViewImplUiBinder extends
			UiBinder<Widget, MainFrameViewImpl> {
	}

	/** Resets all session related fields in the SessionState class */
	Command logOutCmd;

	public MainFrameViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));

		versionLabel.setText("v" + SessionState.VERSION);

		logOutCmd = new Command() {
			@Override
			public void execute() {
				SessionState.IS_LOGGED_IN = false;
				SessionState.USER_NAME = UserNameConstants.NAMES.guest
						.toString();
				History.newItem(Tokens.HOME);
				RootLayoutPanel.get().clear();
				RootLayoutPanel.get().add(asWidget());
				LoginController loginController = new LoginController();
				loginController.go(getContentPanel());
				History.fireCurrentHistoryState();

			}
		};

		impressumBox = new MenuDialogViewImpl();
		impressumBox.setText("Über");

		impressumItem.setScheduledCommand(new Command() {
			@Override
			public void execute() {
				impressumBox.show();
			}
		});

		logout.setScheduledCommand(logOutCmd);
		impressumBox.hide();
	}

	@UiField
	SimpleLayoutPanel contentPanel;

	@UiField
	MenuItem impressumItem;

	@UiField
	MenuItem logout;

	@UiField
	Label versionLabel;

	// @UiField infoItem;

	MenuDialogViewImpl impressumBox;

	@Override
	public SimpleLayoutPanel getContentPanel() {
		return contentPanel;
	}

}
