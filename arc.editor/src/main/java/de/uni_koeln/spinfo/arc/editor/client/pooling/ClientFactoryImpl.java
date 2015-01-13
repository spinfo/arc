package de.uni_koeln.spinfo.arc.editor.client.pooling;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;

import de.uni_koeln.spinfo.arc.editor.client.mvp.login.LoginViewImpl;
import de.uni_koeln.spinfo.arc.editor.client.mvp.login.RegisterViewImpl;
import de.uni_koeln.spinfo.arc.editor.client.mvp.login.WelcomeView;
import de.uni_koeln.spinfo.arc.editor.client.mvp.login.WelcomeViewImpl;
import de.uni_koeln.spinfo.arc.editor.client.mvp.ui.WordWidget;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.PageEditorDialogView;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.PageEditorView;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.RangeEditorView;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.RangeWidget;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.WorkingUnitEditorFrame;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.impl.PageEditorDialogViewImpl;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.impl.PageEditorViewImpl;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.impl.RangeEditorViewImpl;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.impl.WorkingUnitEditorFrameImpl;
import de.uni_koeln.spinfo.arc.editor.client.mvp.workingunitbrowser.view.WorkingUnitBrowserFrame;
import de.uni_koeln.spinfo.arc.editor.client.mvp.workingunitbrowser.view.WorkingUnitBrowserWidget;
import de.uni_koeln.spinfo.arc.editor.client.mvp.workingunitbrowser.view.impl.WorkingUnitBrowserFrameImpl;
import de.uni_koeln.spinfo.arc.editor.client.mvp.workingunitbrowser.view.impl.WorkingUnitBrowserWidgetImpl;
import de.uni_koeln.spinfo.arc.editor.shared.service.WorkingUnitService;
import de.uni_koeln.spinfo.arc.editor.shared.service.WorkingUnitServiceAsync;

public class ClientFactoryImpl implements ClientFactory {

	private ClientFactoryImpl() {
	};

	public static ClientFactoryImpl INSTANCE = new ClientFactoryImpl();

	// private static MainAppView mainAppView = null;

	/**
	 * the overall frame with the splitpanel where on the left side the range
	 * editor is located and on the right is the view where one can edit single
	 * word properties or select the beginning of a range
	 */
	private static WorkingUnitBrowserFrame workingUnitBrowserFrame = null;
	/**
	 * The widget with the cell data grid where one can select the working unit
	 * to work with
	 */
	static WorkingUnitBrowserWidget westWorkingUnitbrowserWidget = null;
	/**
	 * the overall frame where one can select the range modifications
	 */
	private static WorkingUnitEditorFrame workingUnitEditorFrame = null;
	private static PageEditorView pageEditorView = null;

	private static PageEditorDialogView pageEditorDialogView;

	private static List<WordWidget> wordWidgets = new ArrayList<WordWidget>();

	private static RangeEditorView rangeEditorView = null;

	private WorkingUnitServiceAsync rpcService = null;

	@Override
	public EventBus getEventBus() {
		return null;
	}

	private static WelcomeView welcomeView = null;
	private static LoginViewImpl loginView = null;
	private static RegisterViewImpl registerView = null;

	@Override
	public WelcomeView getWelcomeView() {
		if (welcomeView == null)
			welcomeView = new WelcomeViewImpl();
		else
			welcomeView.init();
		return welcomeView;
	}

	@Override
	public LoginViewImpl getLoginView(WelcomeView welcomeView) {
		if (loginView == null)
			loginView = new LoginViewImpl(welcomeView);
		else
			loginView.init();
		return loginView;
	}

	@Override
	public RegisterViewImpl getRegisterView(WelcomeView welcomeView) {
		if (registerView == null)
			registerView = new RegisterViewImpl(welcomeView);
		else
			registerView.init();

		return registerView;
	}

	@Override
	public WorkingUnitServiceAsync getWorkingUnitRpcService() {
		if (rpcService == null)
			rpcService = GWT.create(WorkingUnitService.class);
		return rpcService;
	}

	@Override
	public WorkingUnitBrowserFrame getWorkingUnitBrowserFrame() {
		if (workingUnitBrowserFrame == null)
			workingUnitBrowserFrame = new WorkingUnitBrowserFrameImpl();
		return workingUnitBrowserFrame;
	}

	@Override
	public WorkingUnitBrowserWidget getWestWorkingUnitBrowserWidget() {

		if (westWorkingUnitbrowserWidget == null)
			westWorkingUnitbrowserWidget = new WorkingUnitBrowserWidgetImpl();
		return westWorkingUnitbrowserWidget;
	}

	@Override
	public WorkingUnitEditorFrame getWorkingUnitEditorFrame() {
		if (workingUnitEditorFrame == null)
			workingUnitEditorFrame = new WorkingUnitEditorFrameImpl();
		return workingUnitEditorFrame;
	}

	@Override
	public PageEditorView getPageEditorView() {
		if (pageEditorView == null)
			pageEditorView = new PageEditorViewImpl();
		return pageEditorView;
	}

	@Override
	public PageEditorDialogView getPageEditorDialogView() {
		if (pageEditorDialogView == null)
			pageEditorDialogView = new PageEditorDialogViewImpl();
		return pageEditorDialogView;
	}

	private static WordWidgetPool poolWordWidgets = new WordWidgetPool();

	@Override
	public List<WordWidget> getBlancWordWidgets(int amount) {
		return poolWordWidgets.obtainMulti(amount);
	}

	@Override
	public RangeEditorView getRangeEditorView() {
		if (rangeEditorView == null) {
			rangeEditorView = new RangeEditorViewImpl();
		}
		return rangeEditorView;
	}

	private static RangeWidgetPool poolRangeWidgets = new RangeWidgetPool();

	@Override
	public List<RangeWidget> getBlancRangeWidgets(int amount) {
		return poolRangeWidgets.obtainMulti(amount);
	}
}
