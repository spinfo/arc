package de.spinfo.arc.editor.client.mvp;

import java.util.List;

import com.google.gwt.event.shared.EventBus;

import de.spinfo.arc.editor.client.mvp.ui.WordWidget;
import de.spinfo.arc.editor.client.mvp.views.PageEditorDialogView;
import de.spinfo.arc.editor.client.mvp.views.PageEditorView;
import de.spinfo.arc.editor.client.mvp.views.WelcomeView;
import de.spinfo.arc.editor.client.mvp.views.MainAppView;
import de.spinfo.arc.editor.client.mvp.views.WorkingUnitBrowserFrame;
import de.spinfo.arc.editor.client.mvp.views.WorkingUnitBrowserInfoView;
import de.spinfo.arc.editor.client.mvp.views.WorkingUnitEditorFrame;



public interface ClientFactory {
	public EventBus getEventBus();
	
	MainAppView getMainAppView();
	
	WorkingUnitBrowserFrame getWorkingUnitBrowser();
	WorkingUnitEditorFrame getWorkingUnitEditor();
	WelcomeView getInfoView();

	WorkingUnitBrowserInfoView getWorkingUnitInfoView();

	PageEditorDialogView getPageEditorDialogView();

	PageEditorView getPageEditorView();
	
	/**
	 * Gets a list of WordWidgets which need to be initialized further.
	 * The idea is like in ObjectPooling needed allocated Widgets can be reused.
	 * The class implementing this factory should hold a static list of already created 
	 * instances of WordWidget implementations. If more are needed this method should take care of 
	 * creating new ones. If less are needed this method should return the appropriate sublist needed.
	 * If none have been instantiated so far new ones should be created and appended to the static list.
	 * Thus first time a page with a lot of WordWidget needed will take longer but later calls to
	 * edit a page should be very fast because the DOM-Elements have been already instantiated
	 * Again: Don't forget to reinitialize the values of each Widget with calls to the Widget: <br>			
	 * setIndex(wordIdx);<br>	
	 * setWordText(wordText);<br>	
	 * setPresenter(this); - if a presenter takes care of the wordwidgets<br>	
	 * 
	 * @param amount of word widgets needed
	 * @return a List of WordWidgets
	 */
	List<WordWidget> getBlancWordWidgets(int amount);
}
