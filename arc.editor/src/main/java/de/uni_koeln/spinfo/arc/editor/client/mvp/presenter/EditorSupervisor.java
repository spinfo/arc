package de.uni_koeln.spinfo.arc.editor.client.mvp.presenter;

import de.uni_koeln.spinfo.arc.dto.annotation.ChapterRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.LanguageRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.AnnotationDto.AnnotationTypes;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.RangeWidget;

/**
 * The supervisor which manages presenter communication between the various 
 * Editorviews. Current implementation is {@link de.spinfo.arc.editor.client.mvp.presenter.impl.WorkingUnitEditorFramePresenterImpl)
 * 
 * @author David Rival
 *
 */
public interface EditorSupervisor {
	

//	void setNewRange(int selectedIndexInRespectOfTotalWordIndex,
//			String rangeTitle, AnnotationTypes annotationTypes);
	
/**
 * Gets called if one chooses to edit an existing range and the beginning is selected 
 * with the PageEditor by selecting a certain word with  
 * {@link de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.EditRangeDialogPresenter#onRangeStartSelected }
 * 
 * @param absoluteSelectedWordIdx the absolute word index of the word which is selected
 */
	public void onRangeStartSelected(int absoluteSelectedWordIdx);
	
	/**
	 * Gets called if one chooses to edit an existing range and the end is selected 
	 * with the PageEditor by selecting a certain word with  
	 * {@link de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.EditRangeDialogPresenter#onRangeEndSelected() }
	 * 
	 * @param absoluteSelectedWordIdx the absolute word index of the word which is selected
	 */
	public void onRangeEndSelected(int absoluteSelectedWordIdx);
	
	/**
	 * Gets called if the button for saving a annotation is clicked
	 * 
	 * @param title The title of the fresh Annotation
	 * @param annotationType The type of annotation to be saved
	 */
	void onSaveRangeClicked(String title, AnnotationTypes annotationType);
	
	/**
	 * If the cancel button for eding a range is clicked
	 * 
	 */
	public void onCancelRangeClicked();
	
	/**
	 * If a new paged is demanded this method gets invoked
	 * @param pageNum the new page number to be displayed
	 */
	void onNewPageDemanded(int pageNum);
	
	/**
	 * Gets called if a certain existing chapter is to be removed 
	 * @param rangeAnnotation
	 */
	void onRemoveChapter(ChapterRangeDto rangeAnnotation);
	
	/**
	 * Gets called if a certain existing language range is to be removed 
	 * @param languageRange
	 */
	void onRemoveLanguage(LanguageRangeDto languageRange);
	
	/**
	 * Gets called if an existing bounding rectangel of a word is to be updated
	 * 
	 * @param x 
	 * @param y
	 * @param width
	 * @param height
	 * @param absoluteWordIndex the wordindex the bounding rect is enclosing
	 */
	void updateCoordinates(int x, int y, int width, int height,
			int absoluteWordIndex);
	/**
	 * Adds a new FromAnnotation to the stack of available ones
	 * 
	 * @param text the form itself
	 * @param absoluteSelectedWordIdx
	 */
	void updateForm(String text, int absoluteSelectedWordIdx);

	/**
	 * Adds a new PosAnnotatin to the stack of available ones
	 * 
	 * @param itemText the new POS-Tag
	 * @param absoluteSelectedWordIdx
	 */
	void updatePos(String itemText, int absoluteSelectedWordIdx);

	/**
	 * Creates a new range of the specified type
	 * 
	 * @param selectedIndexInRespectOfTotalWordIndex
	 * @param endIndex
	 * @param title title of the new range
	 * @param annotationType the type of RangeAnnotation
	 */
	void setNewRange(int selectedIndexInRespectOfTotalWordIndex, int endIndex,
			String title, AnnotationTypes annotationType);
	
    /**
     * Gets called by tange widget if one clicks on edit
     * 
     * @param rangeWidge
     */
	void onEditRangeAnnotation(RangeWidget rangeWidge);

	/**
	 * Gets called by the rangeWidget if one wants to see the edited range.
	 * The supervisor should issue the page editor view to display the regarding page and
	 * highlight the regarding word
	 * 
	 * @param pageNum
	 * @param wordIdx
	 * @param isStart 
	 */
	void showRange(int pageNum, long selectedWordIdx, boolean isStart);

	/**
	 * If an exitsing range gets updated tis methofd with the changed instance will be calles
	 * 
	 * @param rangeWidget the widget which displays the the data of the updated existing range
	 */
	void onUpdateExistingRange(RangeWidget rangeWidget);

}
