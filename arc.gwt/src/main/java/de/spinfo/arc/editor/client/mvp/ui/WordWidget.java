package de.spinfo.arc.editor.client.mvp.ui;

import com.google.gwt.user.client.ui.IsWidget;

import de.spinfo.arc.editor.client.mvp.views.HasPresenter;

public interface WordWidget extends IsWidget, HasPresenter<WordWidgetPresenter> {

	void setIndex(int index);

	int getIndex();

	void setWordText(String text);

	String getWordText();

	void setSelected(boolean isSelected);

	boolean getIsSelected();
}
