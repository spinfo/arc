package de.spinfo.arc.editor.client.mvp.presenter.impl;

import com.google.gwt.user.client.ui.HasWidgets;

import de.spinfo.arc.editor.client.mvp.presenter.WorkingUnitBrowserFramePresenter;
import de.spinfo.arc.editor.client.mvp.views.WorkingUnitBrowserFrame;
import de.spinfo.arc.editor.client.mvp.views.impl.WelcomeViewImpl;
import de.spinfo.arc.editor.client.mvp.views.impl.WorkingUnitBrowserInfoViewImpl;
import de.spinfo.arc.editor.client.mvp.views.mainapp.MainAppCompositeFactory;
import de.spinfo.arc.editor.client.mvp.views.mainapp.composite.impl.CenterWidgetImpl;

public class WorkingUnitBrowserFramePresenterImpl implements WorkingUnitBrowserFramePresenter {

	private WorkingUnitBrowserFrame browserFrame;

	public WorkingUnitBrowserFramePresenterImpl(WorkingUnitBrowserFrame browserFrame) {
		this.browserFrame = browserFrame;
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(browserFrame.asWidget());
		bind();
		attachBrowser();
		attachInfoView();
		
	} 

	private void attachBrowser() {
		browserFrame.getWestPanel().add(MainAppCompositeFactory.getWestWorkingUnitBrowserWidget());
		
	}

	private void attachInfoView() {
		browserFrame.getCenterPanel().add(new WorkingUnitBrowserInfoViewImpl());
		
	}

	@Override
	public void bind() {
		browserFrame.setPresenter(this);	
	}

	@Override
	public void onWorkingUitClicked() {
		
	}

}
