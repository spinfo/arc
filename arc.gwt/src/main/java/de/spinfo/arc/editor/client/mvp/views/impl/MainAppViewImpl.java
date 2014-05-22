package de.spinfo.arc.editor.client.mvp.views.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import de.spinfo.arc.editor.client.cactus.presenter.Presenter;
import de.spinfo.arc.editor.client.mvp.presenter.MainAppViewPresenter;
import de.spinfo.arc.editor.client.mvp.views.MainAppView;
import de.spinfo.arc.editor.client.mvp.views.mainapp.MainAppCompositeFactory;

public class MainAppViewImpl extends Composite implements MainAppView {

	private static MainAppViewImplUiBinder uiBinder = GWT
			.create(MainAppViewImplUiBinder.class);

	interface MainAppViewImplUiBinder extends UiBinder<Widget, MainAppViewImpl> {
	}

 
	@UiField
	FlowPanel headerPanel;
	
	
	@UiField
	SimpleLayoutPanel westPanel;
	
	@UiField
//	FlowPanel centerPanel;
//	SimpleLayoutPanel centerPanel;
	ScrollPanel centerPanel;
//	@UiField
//	SimpleLayoutPanel westPanel;

	private MainAppViewPresenter presenter;


	public MainAppViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		
		// TODO: change below, just for testing
//		MainAppCompositeFactory f = new MainAppCompositeFactory();
		
//		setWestContent(MainAppCompositeFactory.getWestWorkingUnitBrowserWidget());

	}


	@Override
	public void setHeaderContent(IsWidget widget) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSouthContent(IsWidget widget) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWestContent(IsWidget widget) {
		westPanel.clear();
		westPanel.add(widget);
		
	}

	@Override
	public void setCenterContent(IsWidget widget) {
		centerPanel.clear();
		centerPanel.add(widget);
	}

	@Override
	public void setPresenter(MainAppViewPresenter presenter) {
		this.presenter = presenter;
		
	}


	@Override
	public Panel getHeaderPanel() {
		return this.headerPanel;
	}


	@Override
	public Panel getSouthPanel() {
		return null;
	}


	@Override
	public Panel getWestPanel() {
		return this.westPanel;
	} 
 

	@Override
	public Panel getCenterPanel() {
		return this.centerPanel;
	}


}
