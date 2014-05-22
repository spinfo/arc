package de.spinfo.arc.editor.client.mvp.views.mainapp.composite;

import com.google.gwt.user.client.ui.IsWidget;

import de.spinfo.arc.editor.client.mvp.views.HasPresenter;
import de.spinfo.arc.editor.client.mvp.views.mainapp.composite.presenter.MainAppWidgetPresenter;
import de.spinfo.arc.editor.client.mvp.views.mainapp.composite.presenter.WorkingUnitBrowserPresenter;

public interface WorkingUnitBrowserWidget extends IsWidget {
	void setPresenter(WorkingUnitBrowserPresenter presenter);

}
