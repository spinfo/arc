package de.spinfo.arc.annotationmodel.annotatable;

import de.spinfo.arc.annotationmodel.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

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
