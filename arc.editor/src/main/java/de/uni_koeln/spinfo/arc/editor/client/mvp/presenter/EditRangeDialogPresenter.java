package de.uni_koeln.spinfo.arc.editor.client.mvp.presenter;

/**
 * The Presenter for Dialog-View for editing Ranges.
 * The Rageediting consists of 3 steps:
 * 1. selecting the beginning
 * 2. selecting end
 * 3. setting title and confiming 
 * 
 * @author David Rival
 *
 */
public interface EditRangeDialogPresenter {

	/**
	 * If the start word (index) has been selected
	 */
	void onRangeStartSelected();

	/**
	 * If the end(-word) index has been selected
	 */
	void onRangeEndSelected();

	/**
	 * If the save button for eding this range has been pressed
	 */
	void onSaveRangeClicked();
	
	/**
	 * if the cancel button for editing this range has been pressed
	 */
	void onCancelRangeClicked();

}
