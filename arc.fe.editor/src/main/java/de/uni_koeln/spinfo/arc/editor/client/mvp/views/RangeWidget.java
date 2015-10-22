package de.uni_koeln.spinfo.arc.editor.client.mvp.views;

import com.google.gwt.user.client.ui.IsWidget;

import de.uni_koeln.spinfo.arc.dto.annotation.AnnotationDto.AnnotationTypes;
import de.uni_koeln.spinfo.arc.dto.annotation.RangeAnnotationDto;
import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.RangeEditorPresenter;

public interface RangeWidget extends IsWidget, HasPresenter<RangeEditorPresenter>{

	void setOrdinal(String ordinal);

	String getOrdinal();

	void setRangeTitle(String title);

	String getRangeTitle();

	void setFromPage(String fromPage);

	String getFromPage();

	void setToPage(String toPage);

	String getToPage();
	
	RangeAnnotationDto getRangeAnnotation();

	void setFromIndex(long toIndex);

	String getFromIndex();

	void setToIndex(long toIndex);

	String getToIndex();

	AnnotationTypes getAnnotationType();

}
