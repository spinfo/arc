package de.uni_koeln.spinfo.arc.editor.client.mvp.workingunitbrowser.presenter.impl;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

import de.uni_koeln.spinfo.arc.dto.annotatable.WorkingUnitDto;
import de.uni_koeln.spinfo.arc.editor.client.mvp.Tokens;
import de.uni_koeln.spinfo.arc.editor.client.mvp.workingunitbrowser.presenter.WorkingUnitBrowserWidgetPresenter;
import de.uni_koeln.spinfo.arc.editor.client.mvp.workingunitbrowser.view.WorkingUnitBrowserFrame;
import de.uni_koeln.spinfo.arc.editor.client.mvp.workingunitbrowser.view.WorkingUnitBrowserWidget;
import de.uni_koeln.spinfo.arc.editor.client.pooling.ClientFactory;

public class WorkingUnitBrowserWidgetPresenterImpl implements  WorkingUnitBrowserWidgetPresenter  {

	private WorkingUnitBrowserFrame browserFrame;
	private ClientFactory clientFactory;

	public WorkingUnitBrowserWidgetPresenterImpl(WorkingUnitBrowserFrame browserFrame, ClientFactory clientFactory) {
		this.browserFrame = browserFrame;
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(browserFrame.asWidget()); 
		/*
		 * Doesn't need to be binded because the WorkingUnitBrowserWidget
		 * gets this as Presenter
		 */
//		bind();
		attachBrowser();
//		attachInfoView(); 
		
	} 

	private void attachBrowser() {
		WorkingUnitBrowserWidget browserWidget = clientFactory.getWestWorkingUnitBrowserWidget();
		browserWidget.setPresenter(this);
		browserFrame.getWestPanel().clear();
		browserFrame.getWestPanel().add(browserWidget.asWidget());
	}

	@Override
	public void bind() {
		/*
		 * Doesn't need to be binded because the WorkingUnitBrowserWidget
		 * gets this as Presenter
		 */
//		browserFrame.setPresenter(this);	
	}


	@Override
	public void onWorkingUnitSelected(WorkingUnitDto selectedWorkingUnit) {
		System.out.println("selected Working Unit with ID: " + selectedWorkingUnit.getTitle());
		History.newItem(Tokens.EDITOR+"&" + selectedWorkingUnit.getTitle());
		
		
	}

}
