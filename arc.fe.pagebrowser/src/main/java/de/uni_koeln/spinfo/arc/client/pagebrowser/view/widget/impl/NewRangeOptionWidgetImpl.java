package de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget.OptionWidget;

public class NewRangeOptionWidgetImpl extends Composite implements OptionWidget {

	private static NewRangeOptionWidgetImplUiBinder uiBinder = GWT
			.create(NewRangeOptionWidgetImplUiBinder.class);

	interface NewRangeOptionWidgetImplUiBinder extends
			UiBinder<Widget, NewRangeOptionWidgetImpl> {
	}

	public NewRangeOptionWidgetImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Label questionToUser;
	@UiField
	Label type;
	@UiField
	FocusPanel panel;

	public NewRangeOptionWidgetImpl(String questionToUser, String type) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("questionToUser")
	void onClick(ClickEvent e) {
	}

	@Override
	public void setQuestionToUserText(String text) {
		questionToUser.setText(text);
	}

	@Override
	public void setTypeText(String text) {
		type.setText(text);
	}

	@Override
	public String getQuestionToUserText() {
		return questionToUser.getText();
	}

	@Override
	public String getTypeText() {
		return type.getText();
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return panel.addClickHandler(handler);
	}

}
