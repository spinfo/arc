package de.uni_koeln.spinfo.arc.editor.client.pooling;

import java.util.List;

import com.google.gwt.event.shared.EventBus;

import de.uni_koeln.spinfo.arc.editor.client.mvp.login.LoginViewImpl;
import de.uni_koeln.spinfo.arc.editor.client.mvp.login.RegisterViewImpl;
import de.uni_koeln.spinfo.arc.editor.client.mvp.login.WelcomeView;
import de.uni_koeln.spinfo.arc.editor.client.mvp.ui.WordWidget;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.PageEditorDialogView;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.PageEditorView;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.RangeEditorView;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.RangeWidget;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.WorkingUnitEditorFrame;
import de.uni_koeln.spinfo.arc.editor.client.mvp.workingunitbrowser.view.WorkingUnitBrowserFrame;
import de.uni_koeln.spinfo.arc.editor.client.mvp.workingunitbrowser.view.WorkingUnitBrowserWidget;
import de.uni_koeln.spinfo.arc.editor.shared.service.WorkingUnitServiceAsync;

public interface ClientFactory {

	public EventBus getEventBus();

	WorkingUnitBrowserFrame getWorkingUnitBrowserFrame();

	WorkingUnitEditorFrame getWorkingUnitEditorFrame();

	WelcomeView getWelcomeView();

	// WorkingUnitBrowserInfoView getWorkingUnitInfoView();

	PageEditorDialogView getPageEditorDialogView();

	PageEditorView getPageEditorView();

	/**
	 * Gets a list of WordWidgets which need to be initialized further. The idea
	 * is like in ObjectPooling needed allocated Widgets can be reused. The
	 * class implementing this factory should hold a static list of already
	 * created instances of WordWidget implementations. If more are needed this
	 * method should take care of creating new ones. If less are needed this
	 * method should return the appropriate sublist needed. If none have been
	 * instantiated so far new ones should be created and appended to the static
	 * list. Thus first time a page with a lot of WordWidget needed will take
	 * longer but later calls to edit a page should be very fast because the
	 * DOM-Elements have been already instantiated Again: Don't forget to
	 * reinitialize the values of each Widget with calls to the Widget: <br>
	 * setIndex(wordIdx);<br>
	 * setWordText(wordText);<br>
	 * setPresenter(this); - if a presenter takes care of the wordwidgets<br>
	 * 
	 * @param amount
	 *            of word widgets needed
	 * @return a List of WordWidgets
	 */
	List<WordWidget> getBlancWordWidgets(int amount);

	WorkingUnitBrowserWidget getWestWorkingUnitBrowserWidget();

	WorkingUnitServiceAsync getWorkingUnitRpcService();

	List<RangeWidget> getBlancRangeWidgets(int amount);

	RangeEditorView getRangeEditorView();

	RegisterViewImpl getRegisterView(WelcomeView welcomeView);

	LoginViewImpl getLoginView(WelcomeView welcomeView);
}
