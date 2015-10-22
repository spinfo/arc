package de.uni_koeln.spinfo.arc.editor.client.mvp.workingunitbrowser.view;

import com.google.gwt.user.client.ui.IsWidget;

import de.uni_koeln.spinfo.arc.editor.client.mvp.views.HasPresenter;
import de.uni_koeln.spinfo.arc.editor.client.mvp.workingunitbrowser.presenter.WorkingUnitBrowserWidgetPresenter;

//import de.spinfo.arc.editor.client.mvp.presenter

public interface WorkingUnitBrowserWidget extends IsWidget,
		HasPresenter<WorkingUnitBrowserWidgetPresenter> {
	
	@Override
	void setPresenter(WorkingUnitBrowserWidgetPresenter presenter);

}
