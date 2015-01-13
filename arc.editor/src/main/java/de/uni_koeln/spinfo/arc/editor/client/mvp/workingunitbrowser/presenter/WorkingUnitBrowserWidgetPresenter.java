package de.uni_koeln.spinfo.arc.editor.client.mvp.workingunitbrowser.presenter;

import de.uni_koeln.spinfo.arc.dto.annotatable.WorkingUnitDto;
import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.Presenter;

public interface WorkingUnitBrowserWidgetPresenter extends Presenter {

	void onWorkingUnitSelected(WorkingUnitDto selectedWorkingUnit);

}
