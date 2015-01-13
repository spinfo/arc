package de.uni_koeln.spinfo.arc.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;

import de.uni_koeln.spinfo.arc.client.pagebrowser.factory.impl.PageBrowserFactoryImpl;
import de.uni_koeln.spinfo.arc.client.pagebrowser.presenter.PageBrowserPresenter;
import de.uni_koeln.spinfo.arc.client.pagebrowser.presenter.impl.PageBrowserPresenterImpl;
import de.uni_koeln.spinfo.arc.client.pagebrowser.view.PageBrowserView;
import de.uni_koeln.spinfo.arc.client.pagebrowser.view.impl.PageBrowserViewImpl;
import de.uni_koeln.spinfo.arc.client.supervisor.impl.SupervisorImpl;
import de.uni_koeln.spinfo.arc.dto.annotatable.WorkingUnitDto;
import de.uni_koeln.spinfo.arc.dto.annotatable.impl.WorkingUnitDtoImpl;
import de.uni_koeln.spinfo.arc.dto.annotation.AnnotationDto.AnnotationTypes;
import de.uni_koeln.spinfo.arc.dto.annotation.PageRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.impl.PageRangeDtoImpl;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ARC_Page_Browser implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	SimpleLayoutPanel slp = new SimpleLayoutPanel();

	public void onModuleLoad() {
		/* uncomment below if you wnt to test the page browser solely */
		// doNotUseService();
	}

	private void doNotUseService() {

		PageBrowserView view = new PageBrowserViewImpl(
				PageBrowserFactoryImpl.INSTANCE);
		PageBrowserPresenter presenter = new PageBrowserPresenterImpl(view,
				new SupervisorImpl(), createDummyWorkingUnit());

		slp.add(view);
		RootLayoutPanel.get().add(slp);
	}

	/*
	 * make a dummy instance without using a service and a DB
	 */
	private WorkingUnitDto createDummyWorkingUnit() {
		Date d = new Date();

		WorkingUnitDto wu = new WorkingUnitDtoImpl(d, -1, "test", "testTitle",
				0, 1000);
		for (int i = 0; i < 40; i++) {
			PageRangeDto page = new PageRangeDtoImpl(d, -1, "test", i, i,
					"test.url");
			wu.setAnnotationAsType(AnnotationTypes.PAGE_RANGE, page);
		}
		return wu;
	}

}
