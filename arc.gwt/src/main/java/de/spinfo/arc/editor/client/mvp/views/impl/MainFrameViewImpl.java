package de.spinfo.arc.editor.client.mvp.views.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import de.spinfo.arc.editor.client.mvp.views.MainFrameView;

public class MainFrameViewImpl extends Composite implements MainFrameView  {

	private static MainFrameViewImplUiBinder uiBinder = GWT
			.create(MainFrameViewImplUiBinder.class);

	interface MainFrameViewImplUiBinder extends
			UiBinder<Widget, MainFrameViewImpl> {
	}

	public MainFrameViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	SimpleLayoutPanel contentPanel;
	
	
	@Override
	public SimpleLayoutPanel getContentPanel() {
		return contentPanel;
	}

	

}
