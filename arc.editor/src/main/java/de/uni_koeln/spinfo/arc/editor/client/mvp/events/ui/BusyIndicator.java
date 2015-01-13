package de.uni_koeln.spinfo.arc.editor.client.mvp.events.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;



public class BusyIndicator extends DialogBox {
	
	static BusyIndicator busy;

//	@UiField FlowPanel view;

	@UiField static Label headerLabel;
	@UiField static Label contentLabel;
	@UiField static Label contentLabel2;
	@UiField static Button closeBtn;
	
	static public void busy(String header1, String header2, String content){
		if (busy == null) busy = new BusyIndicator();
		headerLabel.setText(header1);
		contentLabel.setText(header2);
		contentLabel2.setText(content);
		busy.show();
	}
	
	static public void free(){
		if (busy == null) busy = new BusyIndicator();
		busy.hide();
	}
	
	interface BusyIndicatorUiBinder extends UiBinder<Widget, BusyIndicator> {}
	private static BusyIndicatorUiBinder uiBinder = GWT.create(BusyIndicatorUiBinder.class);

	
	public BusyIndicator(){
		super(true, false);
		setWidget(uiBinder.createAndBindUi(this));
		setAutoHideOnHistoryEventsEnabled(true);
		setAnimationEnabled(false);
		center();
		this.hide();
//		RootPanel.get().add(this,Window.getClientWidth()-200,40);
	}
	
	@UiHandler("closeBtn")
	void onClose(ClickEvent e) {
		this.hide();
	}
}
