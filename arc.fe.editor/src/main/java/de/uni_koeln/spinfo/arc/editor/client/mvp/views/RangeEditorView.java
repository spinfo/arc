package de.uni_koeln.spinfo.arc.editor.client.mvp.views;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TabLayoutPanel;

import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.RangeEditorPresenter;

public interface RangeEditorView extends IsWidget, HasPresenter<RangeEditorPresenter>  {

	HasWidgets getChapterPanel();

	HasWidgets getLanguagePanel();
	
	TabLayoutPanel getRangeTabPanel();
}
