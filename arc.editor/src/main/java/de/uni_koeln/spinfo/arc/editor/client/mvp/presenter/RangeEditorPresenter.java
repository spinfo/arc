package de.uni_koeln.spinfo.arc.editor.client.mvp.presenter;

import java.util.List;

import de.uni_koeln.spinfo.arc.dto.annotation.ChapterRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.LanguageRangeDto;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.RangeWidget;

/**
 * The Presenter which interacts with the {@link de.uni_koeln.spinfo.arc.editor.client.mvp.views.RangeEditorView}
 * @author David Rival
 *
 */
public interface RangeEditorPresenter extends Presenter {
	/**
	 * If someone clicks on the remove button of a certain Range widget
	 * 
	 * @param rangeWidget the widget which holds the remove button
	 */
	void onRemoveRangeClicked(RangeWidget rangeWidget);

	/**
	 * If a List of LanguageRanges should be added to the display 
	 * this method get called
	 * 
	 * @param languageRange the list off language Ranges added to the display
	 */
	void addToLanguageRanges(List<LanguageRangeDto> languageRange);

	/**
	 * If one clicks of a certain RangeWidgets button for telling the page editor to display the beginning of the range
	 * @param rangeWidgetImpl
	 */
	void onFromPageBtnClicked(RangeWidget rangeWidgetImpl);
	/**
	 * If one clicks of a certain RangeWidgets button for telling the page editor to display the END of the range
	 * @param rangeWidgetImpl
	 */
	void onToPageBtnClicked(RangeWidget rangeWidgetImpl);

	
	/**
	 * If a List of ChapterRanges should be added to the display 
	 * this method get called
	 * 
	 * @param chapterRanges he list off chapterRanges added to the display
	 */
	void addToChapterRanges(List<ChapterRangeDto> chapterRanges);

	/**
	 * If the button of the RangeWidget for editing a certain range has been clicked
	 * 
	 * @param rangeWidget the widget which displays the data of the certain range to be edited
	 */
	void onEditRangeClicked(RangeWidget rangeWidget);


}
