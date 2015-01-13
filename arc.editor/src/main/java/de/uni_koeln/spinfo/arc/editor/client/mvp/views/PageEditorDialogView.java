package de.uni_koeln.spinfo.arc.editor.client.mvp.views;

import java.util.Set;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.PageEditorDialogPresenter;

public interface PageEditorDialogView extends HasPresenter<PageEditorDialogPresenter> {
	
	/**
	 * Sets the text of the draggable header of the dialog view
	 * 
	 * @param text
	 */
	void setHeaderText(String text);
	
	/**
	 * Sets the text of the sub title which is straight below the draggable
	 * header
	 * 
	 * @param text
	 */
	void setSelectedWordText(String text);
	
	/**
	 * Sets the text which should tell the beholder
	 * to enter a new form for a word
	 * 
	 * @param text
	 */
	void setNewformLabelText(String text);
	/**
	 * Sets the text which is initially displayed before the user enters a form modification.
	 * This could be the old form thus the user has only to commit small changes
	 * 
	 * @param text
	 */
	void setNewformTextBoxText(String text);
	
	/**
	 * Gets the text of the new form textbox text
	 * 
	 * @return the text in the inputbox
	 */
	String getNewformTextBoxText();
	
	/**
	 * Get the whole widget of the TextBox for entering new forms
	 * 
	 * @return the TextBox widget
	 */
	TextBox getFormInputTextBox();
	/**
	 * Set the clickhandler which reacts on saving the new form
	 * 
	 * @param handler
	 */
	void setSaveFormClickHandler(ClickHandler handler);

	/**
	 * Sets the text which should tell the beholder
	 * to choose a POS-Tag for a word
	 * 
	 * @param text
	 */
	void setNewPosLabelText(String text);
	
	/**
	 * Sets a new item in the drop down box for 
	 * the POS selection
	 * 
	 * @param newItemsText
	 */
	void setNewPosListBoxItem(String newItemsText);
	
	/**
	 * Sets the changehandler which reacts on changes in respect of the
	 * POS-Tag selection of the drop down menu for pos tags
	 * 
	 * @param changeHandler
	 */
	void setSelectedPosChangeHandler(ChangeHandler changeHandler);
	/**
	 * Get the whole widget of the ListBox for selecting POS-Tags
	 * 
	 * @return the ListBox widget
	 */
	ListBox getPosListBox();

	/**
	 * Set the clickhandler which reacts on saving a new POS selection
	 * 
	 * @param handler
	 */
	void setSavePosClickHandler(ClickHandler handler);
	
	
	/**
	 * Sets the text which should tell the beholder
	 * to edit the coordinates of a word in a working unit
	 * 
	 * @param text
	 */
	void setCoordinatesLabelText(String text);

	/**
	 * Set the clickhandler which reacts on clicking 'edit'
	 * for moving/drawing a new rectangle around a word
	 * 
	 * @param handler
	 */
	void setEditCoordinatesClickHandler(ClickHandler handler);
	
	/**
	 * Set the clickhandler which reacts on saving  new coordinates for a bounding rectangle
	 * around a word
	 * 
	 * @param handler
	 */
	void setSaveCoordinatesClickHandler(ClickHandler handler);

	/**
	 *  Set the clickhandler which reacts on clicking cancel.
	 *  thus f.i. discarding any changes and/or hiding this view
	 *  
	 * @param handler
	 */
	void setCancelButtonClickHandler(ClickHandler handler);

	/**
	 * 
	 * @return the underlying DialogBox Widget to f.i. call the hide() and show()
	 * method on it
	 */
	DialogBox asDialogBox();

	Button getEditCoordinatesBtn();

	Button getSaveCoordinatesBtn();

	void setSelectedWordPosText(String text);

	Button getSaveFormBtn();

	Button getPrevWordBtn();

	Button getNextWordBtn();

	void setUpWithConstants();

	void setUpWithPosOptions(Set<String> posOptions);

	public boolean hasChanged();

	void setHasChanged(boolean b);


	



}
