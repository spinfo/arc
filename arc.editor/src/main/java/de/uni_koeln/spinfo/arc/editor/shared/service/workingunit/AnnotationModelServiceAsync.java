package de.uni_koeln.spinfo.arc.editor.shared.service.workingunit;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.uni_koeln.spinfo.arc.dto.annotatable.WorkingUnitDto;
import de.uni_koeln.spinfo.arc.dto.annotatable.impl.WordDtoImpl;
import de.uni_koeln.spinfo.arc.dto.annotatable.impl.WorkingUnitDtoImpl;
import de.uni_koeln.spinfo.arc.dto.annotation.ChapterRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.FormAnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.LanguageRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.PosAnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.RectangleAnnotationDto;

public interface AnnotationModelServiceAsync {

	void getWorkingUnits(AsyncCallback<List<WorkingUnitDto>> callback);

	void getWorkingUnit(String title, AsyncCallback<WorkingUnitDto> callback);

	void getWorkingUnitImpl(String title,
			AsyncCallback<WorkingUnitDtoImpl> callback);

	void getWords(int start, int end, AsyncCallback<List<WordDtoImpl>> callback);

	void updateRectangle(long index,
			RectangleAnnotationDto rectangleAnnotation,
			AsyncCallback<String> callback);

	void updateForm(long index, FormAnnotationDto formAnnotation,
			AsyncCallback<String> callback);

	void updatePos(long index, PosAnnotationDto posAnnotation,
			AsyncCallback<String> callback);

	void pushChapter(String workingUnitTitle, ChapterRangeDto chapterRange,
			AsyncCallback<String> callback);

	void pushLanguage(String workingUnitTitle, LanguageRangeDto languageRange,
			AsyncCallback<String> callback);

	void pullChapter(String workingUnitTitle, ChapterRangeDto chapterRange,
			AsyncCallback<String> callback);

	void pullLanguage(String workingUnitTitle, LanguageRangeDto languageRange,
			AsyncCallback<String> callback);

}
