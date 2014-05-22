package de.spinfo.arc.editor.client.mvp.presenter.impl;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.HasWidgets;

import de.spinfo.arc.editor.client.mvp.presenter.MainAppViewPresenter;
import de.spinfo.arc.editor.client.mvp.views.MainAppView;

public class MainAppViewPresenterImpl implements MainAppViewPresenter{

	private MainAppView mainAppview;

	public MainAppViewPresenterImpl(MainAppView mainAppview) {
		this.mainAppview = mainAppview;
		bind();
	}

	@Override
	public void go(HasWidgets container) {
		GWT.log("container!");
		container.clear();
		container.add(mainAppview.asWidget());
		
		
	}

	@Override
	public void bind() {
		mainAppview.setPresenter(this);
	}
	

}
