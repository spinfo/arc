package de.uni_koeln.spinfo.arc.editor.client.mvp.views;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;

import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.WorkingUnitEditorFramePresenter;

/**
 * Implementing Views embody the frame of the working unit Editor view
 * It consists of a splitpanel which has a west-side for editing range modifications
 * like Chapter-Ranges in page measures. The center area is split vertically in a north and a south area.
 * the north one should display plain words which are clickable and editable in respect of modifications
 * like form and part-of-speech tagging. The south-area displays the underlying image which comes usually from 
 * a PDF file where the textual content is OCR'ed and the image is embedded in the document.
 * 
 * @author David Rival
 *
 */
public interface WorkingUnitEditorFrame extends IsWidget, HasPresenter<WorkingUnitEditorFramePresenter> {
	
	/**
	 * Retrieves the upper left paenl for holding the tree view of pages
	 * @return
	 */
	HasWidgets getChapterPanel();
	/**
	 * retrieves the panel which holds the two fold views where the upper one is filled with word widgets 
	 * and the lower one holds the images of thee pages
	 * @return
	 */
	HasWidgets getCenterPanel();
	/**
	 * retrieves the lower left panel which hold the widgets for the ranges
	 * @return
	 */
	HasWidgets getRangesPanel();

}
