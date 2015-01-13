package de.uni_koeln.spinfo.arc.editor.client.mvp.ui;

import com.google.gwt.user.client.ui.IsWidget;

import de.uni_koeln.spinfo.arc.editor.client.mvp.views.HasPresenter;

/**
 * A WordWidget is the view of a Word of a WorkingUnit
 * @author David Rival
 *
 */
public interface WordWidget extends IsWidget, HasPresenter<WordWidgetPresenter> {
	/**
	 * Sets the index which is retrieved later for determining the words order in context of the global absolute wordindex
	 * @param index
	 */
	void setIndex(int index);
	/**
	 * get the Index of the word this display is for
	 * @return
	 */
	int getIndex();
	/**
	 * Sets the text of the widget
	 * @param text
	 */
	void setWordText(String text);
	/**
	 * Retrieves the text this widget is showing
	 * @return
	 */
	String getWordText();
	
	/**
	 * If this word is selected it should apply some CSS to make it visible
	 * @param isSelected true if the word is selected
	 */
	void setSelected(boolean isSelected);

	boolean getIsSelected();
}
