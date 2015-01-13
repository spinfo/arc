package de.uni_koeln.spinfo.arc.editor.client.mvp.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;

public class MenuDialogViewImpl extends DialogBox {

	private static MenuDialogViewImplUiBinder uiBinder = GWT
			.create(MenuDialogViewImplUiBinder.class);

	interface MenuDialogViewImplUiBinder extends
			UiBinder<Widget, MenuDialogViewImpl> {
	}

	public MenuDialogViewImpl() {
		super(true, false);
		setWidget(uiBinder.createAndBindUi(this));
		setAutoHideOnHistoryEventsEnabled(true);
		setAnimationEnabled(true);
		center();
	}

	@UiField
	Button cancel;

	// @UiHandler("cancel")
	// public void onCancelClicked() {
	// this.hide();
	// }

	@UiHandler("cancel")
	void onLoginButtonPressed(ClickEvent e) {
		this.hide();
	}

}
