package de.uni_koeln.spinfo.arc.editor.client.mvp.events.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ErrorIndicator extends DialogBox {
	
	static ErrorIndicator busy;

//	@UiField FlowPanel view;

	@UiField static Label headerLabel;
	@UiField static Label contentLabel;
	@UiField static Label contentLabel2;
	
	static public void busy(String header1, String header2, String content){
//		if (busy == null) busy = new ErrorIndicator();
//		headerLabel.setText(header1);
//		contentLabel.setText(header2);
//		if (content.isEmpty())
//			content = "Click next to this popup in order to close this message";
//		contentLabel2.setText(content);
//		busy.show();
	
		GWT.log("Error window temprarly disabled in " + ErrorIndicator.class);
	}
	
	static public void free(){
//		if (busy == null) busy = new ErrorIndicator();
//		busy.hide();
		GWT.log("Error window temprarly disabled in " + ErrorIndicator.class);
	}
	
	interface  ErrorIndicatorUiBinder extends UiBinder<Widget, ErrorIndicator> {}
	private static ErrorIndicatorUiBinder uiBinder = GWT.create( ErrorIndicatorUiBinder.class);

	
	public ErrorIndicator(){
		super(true, false);
		setWidget(uiBinder.createAndBindUi(this));
		setAutoHideOnHistoryEventsEnabled(false);
		setAnimationEnabled(false);
		center();
		this.hide();
//		RootPanel.get().add(this,Window.getClientWidth()-200,40);
	}
}
