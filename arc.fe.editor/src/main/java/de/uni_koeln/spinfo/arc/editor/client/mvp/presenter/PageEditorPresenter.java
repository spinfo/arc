package de.uni_koeln.spinfo.arc.editor.client.mvp.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;

import de.uni_koeln.spinfo.arc.dto.annotatable.impl.WordDtoImpl;
import de.uni_koeln.spinfo.arc.dto.annotation.AnnotationDto.AnnotationTypes;
import de.uni_koeln.spinfo.arc.editor.client.mvp.Editor.PageStates;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.RangeWidget;
/**
 * The Main presenter for the View where all the words are clickable and editable
 * {@link de.uni_koeln.spinfo.arc.editor.client.mvp.views.PageEditorView}
 * 
 * @author David Rival
 *
 */
public interface PageEditorPresenter extends Presenter {
	
	/** 
	 * If the mouse button has been clicked on the canvas where the image of a page is drawn onto
	 * @param event
	 */
	void onCanvasMouseDown(MouseDownEvent event);

	/**
	 * 
	 * if the mouse is moved along the canvas
	 * @param event
	 */
	void onCanvasMouseMove(MouseMoveEvent event);

	/**
	 * If a mouse button has been released whil being over the canvas for displaying word images
	 * 
	 * @param event
	 */
	void onCanvasMouseUp(MouseUpEvent event);

	/**
	 * If the button for demanding a new page has been clicked
	 */
	void onNextPageClicked();
	
	/**
	 * If the button for demanding the prev page has been clicked
	 */
	void onPreviousPageClicked();

	/**
	 * Gets called by the callback which confirmes that the page image has been downloaded from the server
	 * @param doneLoadingPageImage
	 */
	void setIsDoneLoadingPageImage(boolean doneLoadingPageImage);

	/**
	 * if the assigned key left has been pressed
	 */
	void onLeftArrowKey();
	/**
	 * if the assigned key right has been pressed
	 */
	void onRightArrowKey();
 
	/**
	 * Sets the Page editor in a certain state like eding words or f.i. ranges
	 * Before this method gets called, the method {@link #initByState} gets called
	 * 
	 * @param state the edting state
	 * @param pageNum the page num to be edited
	 * @param type the type of annotation to be processed if relevant (e.g. editing existing ranges on basis of pageEditor)
	 * @param rangeAnnotation may be null if no existing range is involved in the editing state
	 */
	void setState(PageStates state, int pageNum,
			AnnotationTypes type, RangeWidget rangeWidget);
	
	/**
	 * Gets called first before {@link #setState} in order to init the presenter and load all relevant data like the page image
	 * 
	 * @param state
	 * @param pageNum
	 * @param annotationType
	 * @param words
	 * @param imageUrl
	 * @param rangewidget may be null if the editing state is not about editing an existing range
	 */
	void initByState(PageStates state, int pageNum, AnnotationTypes annotationType,
			List<WordDtoImpl> words, String imageUrl, RangeWidget rangewidget);

	/**
	 * This method is used for the selection of a range end
	 * 
	 * @param pageNum
	 * @param annotationType
	 * @param words
	 * @param imageUrl
	 */
	
	void initForRangeEndSelection(int pageNum, List<WordDtoImpl> words,
			String imageUrl);

/**
 * 
 * @param pageNum the page num to display
 * @param selectedWordIdx the absolute wordIdx to highlight
 * @param words the words of this page
 * @param isStart if the selection is starting from the index or ending at it
 * @param url the picture url of the page
 */

void initForDisplayingRange(int pageNum, long selectedWordIdx,
		List<WordDtoImpl> words, boolean isStart, String url);

/**
 * resets the current selection index and removes selection CSS from WordWodget
 */
void unselectAll();





}
