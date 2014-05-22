package de.spinfo.arc.editor.client.mvp.views.mainapp.composite.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CenterWidgetImpl extends Composite implements HasText {

	private static CenterWidgetImplUiBinder uiBinder = GWT
			.create(CenterWidgetImplUiBinder.class);

	interface CenterWidgetImplUiBinder extends
			UiBinder<Widget, CenterWidgetImpl> {
	}

	public CenterWidgetImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

//	@UiField
//	FlowPanel header;
//	@UiField
//	FlowPanel content; 
	
//	@UiField
//	Label headerText;
	@UiField
	Label contentText; 

	@Override
	public String getText() {
		return contentText.getText();
	}

	@Override
	public void setText(String text) {
		contentText.setText(text);
	}
 

	public void setHeaderText(String text) {
//		headerText.setText(text);
	}

	public String getHeaderText() {
//		return headerText.getText();
		return null;
	}

}
