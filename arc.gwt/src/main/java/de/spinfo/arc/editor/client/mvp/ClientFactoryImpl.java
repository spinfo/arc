package de.spinfo.arc.editor.client.mvp;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.EventBus;

import de.spinfo.arc.editor.client.mvp.ui.WordWidget;
import de.spinfo.arc.editor.client.mvp.ui.impl.WordWidgetImpl;
import de.spinfo.arc.editor.client.mvp.views.PageEditorDialogView;
import de.spinfo.arc.editor.client.mvp.views.PageEditorView;
import de.spinfo.arc.editor.client.mvp.views.WelcomeView;
import de.spinfo.arc.editor.client.mvp.views.MainAppView;
import de.spinfo.arc.editor.client.mvp.views.WorkingUnitBrowserFrame;
import de.spinfo.arc.editor.client.mvp.views.WorkingUnitBrowserInfoView;
import de.spinfo.arc.editor.client.mvp.views.WorkingUnitEditorFrame;
import de.spinfo.arc.editor.client.mvp.views.impl.PageEditorDialogViewImpl;
import de.spinfo.arc.editor.client.mvp.views.impl.PageEditorViewImpl;
import de.spinfo.arc.editor.client.mvp.views.impl.WelcomeViewImpl;
import de.spinfo.arc.editor.client.mvp.views.impl.MainAppViewImpl;
import de.spinfo.arc.editor.client.mvp.views.impl.WorkingUnitBrowserFrameImpl;
import de.spinfo.arc.editor.client.mvp.views.impl.WorkingUnitBrowserInfoViewImpl;
import de.spinfo.arc.editor.client.mvp.views.impl.WorkingUnitEditorFrameImpl;
import de.spinfo.arc.editor.shared.model3.util.DebugHelper;

public class ClientFactoryImpl implements ClientFactory {

	private static MainAppView mainAppView = null;
	
	private static WorkingUnitBrowserFrame wuBrowser = null;
	private static WorkingUnitEditorFrame wuEditor = null;
	private static WelcomeView welcomeView = null;
	private static WorkingUnitBrowserInfoView workingUnitIntoView = null;
	private static PageEditorView pageEditorView = null;

	private static PageEditorDialogView pageEditorDialogView;
	
	private static List<WordWidget> wordWidgets = new ArrayList<WordWidget>();
	
	
	@Override
	public EventBus getEventBus() {
		return null;
	}


	@Override
	public MainAppView getMainAppView() {
		if (mainAppView == null) mainAppView = new MainAppViewImpl();
		return mainAppView;
	}

	@Override
	public WelcomeView getInfoView() {
		if (welcomeView == null) welcomeView = new WelcomeViewImpl();
		return welcomeView;
	}
	
	@Override
	public WorkingUnitBrowserFrame getWorkingUnitBrowser() {
		if (wuBrowser == null) wuBrowser = new WorkingUnitBrowserFrameImpl();
		return wuBrowser;
	}

	@Override
	public WorkingUnitEditorFrame getWorkingUnitEditor() {
		if (wuEditor == null) wuEditor = new WorkingUnitEditorFrameImpl(); 
		return wuEditor;
	}
	
	@Override
	public WorkingUnitBrowserInfoView getWorkingUnitInfoView() {
		if (workingUnitIntoView == null) workingUnitIntoView = new WorkingUnitBrowserInfoViewImpl(); 
		return workingUnitIntoView;
	}
	
	@Override
	public PageEditorView getPageEditorView() {
		if (pageEditorView  == null) pageEditorView = new PageEditorViewImpl(); 
		return pageEditorView;
	}
	
	@Override
	public PageEditorDialogView getPageEditorDialogView() {
		if (pageEditorDialogView  == null) pageEditorDialogView = new PageEditorDialogViewImpl(); 
		return pageEditorDialogView;
	}
	
	@Override
	public List<WordWidget> getBlancWordWidgets(int amount) {
		if (!wordWidgets.isEmpty()) {
			if (wordWidgets.size() >= amount ) {
				DebugHelper.print(this.getClass(), "widgetList is not Empty and grater or equal to needed amount\n returning a subset of existing ones\n returned widgets:  " + wordWidgets.subList(0, amount).size() + " of total Widgets: " + wordWidgets.size() , true);
				return wordWidgets.subList(0, amount);
			}
			else {
				int difference = amount - wordWidgets.size();
				for (int i = 0; i < difference; i++) {
					wordWidgets.add(new WordWidgetImpl());
				} 
				return wordWidgets;
			}
		}
		else {
			for (int i = 0; i < amount; i++) {
				wordWidgets.add(new WordWidgetImpl());
			} 
			DebugHelper.print(this.getClass(), "widgetList is Empty\n created new ones: " + wordWidgets.size() , true);
			return  wordWidgets;
		}
	}
	

}
