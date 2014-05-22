package de.spinfo.arc.editor.client.mvp.views.mainapp.composite.presenter;

import de.spinfo.arc.editor.shared.model.WorkingUnit;

public interface WorkingUnitBrowserPresenter extends WidgetPresenter{

	void onWorkingUnitSelected(WorkingUnit selectedWorkingUnit);
	
}
