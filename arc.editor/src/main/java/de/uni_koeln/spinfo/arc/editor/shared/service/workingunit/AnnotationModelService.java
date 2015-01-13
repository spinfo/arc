package de.uni_koeln.spinfo.arc.editor.shared.service.workingunit;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.uni_koeln.spinfo.arc.dto.annotatable.WorkingUnitDto;
import de.uni_koeln.spinfo.arc.dto.annotatable.impl.WordDtoImpl;
import de.uni_koeln.spinfo.arc.dto.annotatable.impl.WorkingUnitDtoImpl;
import de.uni_koeln.spinfo.arc.dto.annotation.ChapterRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.FormAnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.LanguageRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.PosAnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.RectangleAnnotationDto;

@RemoteServiceRelativePath("model")
public interface AnnotationModelService extends RemoteService {

	List<WorkingUnitDto> getWorkingUnits();

	WorkingUnitDto getWorkingUnit(String title);

	WorkingUnitDtoImpl getWorkingUnitImpl(String title);

	List<WordDtoImpl> getWords(int start, int end);

	String updateRectangle(long index,
			RectangleAnnotationDto rectangleAnnotation);

	String updateForm(long index, FormAnnotationDto formAnnotation);

	String updatePos(long index, PosAnnotationDto posAnnotation);

	String pushChapter(String workingUnitTitle, ChapterRangeDto chapterRange);

	String pushLanguage(String workingUnitTitle, LanguageRangeDto languageRange);

	String pullChapter(String workingUnitTitle, ChapterRangeDto chapterRange);

	String pullLanguage(String workingUnitTitle, LanguageRangeDto languageRange);

}
