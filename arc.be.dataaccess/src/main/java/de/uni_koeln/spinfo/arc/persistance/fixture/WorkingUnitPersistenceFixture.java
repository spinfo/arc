package de.uni_koeln.spinfo.arc.persistance.fixture;

import java.util.Date;

import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.Word;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.impl.WordImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.impl.WorkingUnitImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.FormAnnotation;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.PageRange;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.RectangleAnnotation;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.Annotation.AnnotationTypes;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.impl.FormAnnotationImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.impl.PageRangeImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.impl.RectangleAnnotationImpl;

public class WorkingUnitPersistenceFixture {
	static Date DATE = new Date();
	static int SCORE = 1000;
	static String USERID = "junit";

	public static WorkingUnitImpl standardWorkingUnit() {
		int PAGES = 300;
		int WORDS_PER_PAGE = 500;
		WorkingUnitImpl wu = new WorkingUnitImpl(DATE, SCORE, USERID, "title", 0, PAGES * WORDS_PER_PAGE );
		
		for (int i = 0; i < PAGES; i++) {
			PageRange page = new PageRangeImpl(DATE, SCORE, USERID, 0, 0, "testUrl");
			wu.setAnnotationAsType(AnnotationTypes.PAGE_RANGE, page);
			for (int j = 0; j <= WORDS_PER_PAGE; j++) {
				FormAnnotation initialForm = new FormAnnotationImpl(DATE, SCORE, USERID, i +". page, "+ j + ". word");
				RectangleAnnotation initialRect = new RectangleAnnotationImpl(DATE, SCORE, USERID,i,j,i*2, j*2);
				Word word = new WordImpl(j, initialForm, initialRect);
				wu.appendWord(word);
			}
		}
		System.err.println(wu.getWords().size());
		return wu;
	}
	


}
