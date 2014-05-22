package de.spinfo.arc.editor.client.mvp.views.mainapp.composite.presenter.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;

import de.spinfo.arc.editor.client.mvp.Tokens;
import de.spinfo.arc.editor.client.mvp.views.mainapp.composite.WorkingUnitBrowserWidget;
import de.spinfo.arc.editor.client.mvp.views.mainapp.composite.presenter.WorkingUnitBrowserPresenter;
import de.spinfo.arc.editor.shared.model.PseudoDataSource;
import de.spinfo.arc.editor.shared.model.WorkingUnit;

public class WorkingUnitBrowserPresenterImpl  implements WorkingUnitBrowserPresenter{

	private WorkingUnitBrowserWidget workingUnitBrowserWidget;
	
	
	public WorkingUnitBrowserPresenterImpl(WorkingUnitBrowserWidget workingUnitBrowserWidget) {
		this.workingUnitBrowserWidget = workingUnitBrowserWidget;
		
		
		
		bind();
	}

	@Override
	public void go(Panel container) {
		container.clear();
		container.add(workingUnitBrowserWidget);
		
	}

	@Override
	public void bind() {
		workingUnitBrowserWidget.setPresenter(this);
		
	}

	@Override
	public void onWorkingUnitSelected(WorkingUnit selectedWorkingUnit) {
		GWT.log("WorkingUnitBrowserPresenterImpl selected item: " + selectedWorkingUnit.getTitle());
		
//		PseudoDataSource.getWorkingUnits();
		String currentLocation = History.getToken();
		String bits[] = currentLocation.split("&");
		History.newItem("", false);
		History.newItem(bits[0] + "&" + selectedWorkingUnit.getId());
	}

}
