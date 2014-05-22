package de.spinfo.arc.editor.client.mvp.views.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import de.spinfo.arc.editor.client.mvp.Tokens;
import de.spinfo.arc.editor.client.mvp.views.WelcomeView;

public class WelcomeViewImpl extends Composite implements WelcomeView {

	private static InfoViewImplUiBinder uiBinder = GWT
			.create(InfoViewImplUiBinder.class);

	interface InfoViewImplUiBinder extends UiBinder<Widget, WelcomeViewImpl> {
	}

	public WelcomeViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

@UiField
Button loginButton;

@UiHandler("loginButton")
void onLoginButtonPressed(ClickEvent e) {
	History.newItem(Tokens.WORKING_UNIT_BROWSER);
}


}
