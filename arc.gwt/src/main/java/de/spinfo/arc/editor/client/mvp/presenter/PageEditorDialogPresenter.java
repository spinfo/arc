package de.spinfo.arc.editor.client.mvp.presenter;

public interface PageEditorDialogPresenter extends Presenter{

	void onEditCoordinatesClicked();

	void onSaveCoordinatesClicked();
	/**
	 * 'The presenter gets informed by the DialogView that the save button for
	 * a new entered form has been pressed. the presenter gets the input text or an empty string
	 * if nothing has been entered in the form.
	 * 
	 * @param text  of the input field of the dialog view
	 */
	void onSaveFormClicked(String text);

	/**
	 * 'The presenter gets informed by the DialogView that the save button for
	 * a new selected POS-Tag  has been pressed. the presenter gets the text of the selected item
	 * 
	 * @param itemText the text of the selected item in the dropdown menu
	 */
	void onSavePosClicked(String itemText);

	void onCancelClicked();

	void onLeftArrowKey();

	void onRightArrowKey();
	
	/**
	 * if the inital content of the form box is changed this Method should be called
	 * Its meant to activate/deactivate a save button to not save smthg which hasn'T been edited
	 */

	void onSomethingTypedIntoFormInputBox(String formBoxText);

}
