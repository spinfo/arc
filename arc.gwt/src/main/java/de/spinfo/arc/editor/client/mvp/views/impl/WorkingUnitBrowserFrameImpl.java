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
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import de.spinfo.arc.editor.client.mvp.presenter.WorkingUnitBrowserFramePresenter;
import de.spinfo.arc.editor.client.mvp.views.WorkingUnitBrowserFrame;
import de.spinfo.arc.editor.client.mvp.views.mainapp.MainAppCompositeFactory;

public class WorkingUnitBrowserFrameImpl extends Composite implements WorkingUnitBrowserFrame {

	private static WorkingUnitBrowserFrameViewImplUiBinder uiBinder = GWT
			.create(WorkingUnitBrowserFrameViewImplUiBinder.class);

	interface WorkingUnitBrowserFrameViewImplUiBinder extends
			UiBinder<Widget, WorkingUnitBrowserFrameImpl> {
	}

	public WorkingUnitBrowserFrameImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		
	}
	
	@UiField
	SplitLayoutPanel splitPanel;
	
	@UiField
	SimpleLayoutPanel westPanel;
	
	@UiField
	SimpleLayoutPanel centerPanel;

	private WorkingUnitBrowserFramePresenter presenter;

	@Override
	public SimpleLayoutPanel getWestPanel() {
		return westPanel;
	}

	@Override
	public SimpleLayoutPanel getCenterPanel() {
		return centerPanel;
	}

	@Override
	public void setPresenter(WorkingUnitBrowserFramePresenter presenter) {
		this.presenter = presenter;
	}


}
