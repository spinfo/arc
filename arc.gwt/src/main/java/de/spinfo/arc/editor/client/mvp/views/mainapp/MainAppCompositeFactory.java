package de.spinfo.arc.editor.client.mvp.views.mainapp;

import de.spinfo.arc.editor.client.mvp.views.mainapp.composite.WorkingUnitBrowserWidget;
import de.spinfo.arc.editor.client.mvp.views.mainapp.composite.impl.WorkingUnitBrowserWidgetImpl;
import de.spinfo.arc.editor.shared.model.PseudoDataSource;

public class MainAppCompositeFactory {
	
	static WorkingUnitBrowserWidget westWorkingUnitbrowserWidget = null;

	
	private static MainAppCompositeFactory instance;
	
	
	public static WorkingUnitBrowserWidget getWestWorkingUnitBrowserWidget() {
		if (instance == null)
			instance = new MainAppCompositeFactory();
		
		if (westWorkingUnitbrowserWidget == null) 
			westWorkingUnitbrowserWidget = new WorkingUnitBrowserWidgetImpl();
		return westWorkingUnitbrowserWidget;
	}
	
	
	
	
}
