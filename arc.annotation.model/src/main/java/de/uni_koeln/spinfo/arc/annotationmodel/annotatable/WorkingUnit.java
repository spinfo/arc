package de.uni_koeln.spinfo.arc.annotationmodel.annotatable;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import de.uni_koeln.spinfo.arc.annotationmodel.annotation.ChapterRange;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.HasDetails;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.HasTitle;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.LanguageRange;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.PageRange;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.RangeAnnotation;

@Document(collection = "workingUnits")
public interface WorkingUnit extends HasDetails, HasAnnotations, HasTitle, RangeAnnotation  {
//String getId();

	public void setWords(List<Word> words);
	public void appendWord(Word word);
	public List<Word> getWords ();
	public List<PageRange> getPages();
	public List<ChapterRange> getChapters();
	List<LanguageRange> getLanguages();
	List<PageRange> getPageRangesByRange(RangeAnnotation rangeAnnotation);
	
}
