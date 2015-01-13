package de.uni_koeln.spinfo.arc.annotationmodel.annotatable.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.Word;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.WorkingUnit;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.Annotation;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.ChapterRange;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.LanguageRange;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.PageRange;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.RangeAnnotation;
import de.uni_koeln.spinfo.arc.annotationmodel.comparator.RangeAnnotationComparator;
import de.uni_koeln.spinfo.arc.annotationmodel.util.DebugHelper;

@Document(collection = "workingUnits")
public class WorkingUnitImpl extends HasAnnotationsImpl implements WorkingUnit, Serializable {
	
//	@Id
//	private String id;
	
	/**
	 * Default id
	 */
	private static final long serialVersionUID = 1L;
	
	private static final boolean DEBUG_CONTAINS_RANGE = true;
	private Date date;
	private int score;
	private String userId;
	@Indexed(unique = true, dropDups = true)
	private String title;
	
//	@DBRef (lazy = true) 
//	WordContainer wordContainer = new WordContainer();	
	@Transient
	private List<Word> words = new ArrayList<>();
	private long start;
	private long end;
	
	/**
	 * empty Constructor for GWT - don't use otherwise!
	 */
	public WorkingUnitImpl() {}
	
//	public WorkingUnitImpl(Date date, int score, String userId, String title) {
//		super();
////		words = new ArrayList<>();
//		this.date = date;
//		this.score = score;
//		this.userId = userId;
//		this.title = title;
//	}
	public WorkingUnitImpl(Date date, int score, String userId, String title, long wordStartIndex, long wordEndIndex) {
		super();
//		words = new ArrayList<>();
		this.date = date;
		this.score = score;
		this.userId = userId;
		this.title = title;
		this.start = wordStartIndex;
		this.end = wordEndIndex;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String getUserId() {
		return userId;
	}

 
	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getTitle() {
		return title;
	}
	
	@Override
	public void setAnnotationAsType(AnnotationTypes type, Annotation annotation) {
		if (isTypeValid(type))
			super.setAnnotationAsType(type, annotation);
	}
	
	@Override
	public void setAnnotationsAsType(AnnotationTypes type, List<Annotation> annotations) {
		if (isTypeValid(type)) 
			super.setAnnotationsAsType(type, annotations);
	}
	
	private boolean isTypeValid(AnnotationTypes type) {
		if (!type.equals(AnnotationTypes.PAGE_RANGE) && !type.equals(AnnotationTypes.LANGUAGE_RANGE) && !type.equals(AnnotationTypes.CHAPTER_RANGE) && !type.equals(AnnotationTypes.VOLUME_RANGE)) 
			throw new IllegalArgumentException("the passed type must be either of " + 
						AnnotationTypes.PAGE_RANGE.toString() + " or of " +
							AnnotationTypes.LANGUAGE_RANGE.toString() + " or of " + 
								AnnotationTypes.CHAPTER_RANGE.toString() + " or of " +
									AnnotationTypes.VOLUME_RANGE.toString());
		else 
			return true;
	}
	

	/**
	 * Get list of page ranges from this working unit
	 * @return
	 */
	@Override
	public List<PageRange> getPages() {
		List<PageRange> pagesToReturn =  new ArrayList<>();
		List<Annotation> retrieved =  getAnnotationsOfType(AnnotationTypes.PAGE_RANGE);
		for (Iterator<Annotation> iterator = retrieved.iterator(); iterator.hasNext();) {
			Annotation annotation =  iterator.next();
			if (annotation instanceof PageRange) {
				PageRange pageRange = (PageRange) annotation;
				pagesToReturn.add(pageRange);
			}
			
		}
		Collections.sort(pagesToReturn, RangeAnnotationComparator.INSTANCE);
		return pagesToReturn;
	}
	/**
	 * Get list of chapter ranges from this working unit
	 * @return
	 */
	@Override
	public List<ChapterRange> getChapters() {
		List<ChapterRange> chaptersToReturn =  new ArrayList<>();
		List<Annotation> retrieved =  getAnnotationsOfType(AnnotationTypes.CHAPTER_RANGE);
		for (Iterator<Annotation> iterator = retrieved.iterator(); iterator.hasNext();) {
			Annotation annotation =  iterator.next();
			if (annotation instanceof ChapterRange) {
				ChapterRange range = (ChapterRange) annotation;
				chaptersToReturn.add(range);
			}
			
		}
		Collections.sort(chaptersToReturn, RangeAnnotationComparator.INSTANCE);
		return chaptersToReturn;
	}
	
	@Override
	public List<LanguageRange> getLanguages() {
		List<LanguageRange> languagesToReturn =  new ArrayList<>();
		List<Annotation> retrieved =  getAnnotationsOfType(AnnotationTypes.LANGUAGE_RANGE);
		for (Iterator<Annotation> iterator = retrieved.iterator(); iterator.hasNext();) {
			Annotation annotation =  iterator.next();
			if (annotation instanceof LanguageRange) {
				LanguageRange range = (LanguageRange) annotation;
				languagesToReturn.add(range);
			}
		}
		Collections.sort(languagesToReturn, RangeAnnotationComparator.INSTANCE);
		return languagesToReturn;
	}
	

	@Override
	public String toString() {
		return "WorkingUnitImpl [date=" + date + ", score="
				+ score + ", userId=" + userId + ", title=" + title
				+ ", annotations=" + getAnnotations()
				+ ", words=" + words + "]";
	}

	@Override
	public void setWords(List<Word> words) {
		this.words.addAll(words);
		
	}

	@Override
	public void appendWord(Word word) {
		this.words.add(word);
		
	}

	@Override
	public List<Word> getWords() {
		return words;
	}

	@Override
	public long getStart() {
		return start;
	}

	@Override
	public long getEnd() {
		return end;
	}
	
	@Override
	public List<PageRange> getPageRangesByRange(RangeAnnotation rangeAnnotation) {
		List<PageRange> toReturn = new ArrayList<PageRange>();
		for (Iterator<PageRange> iterator = getPages().iterator(); iterator.hasNext();) {
			PageRange pageRange = iterator.next();
			if(rangeAnnotation.intersectsRange(pageRange)) 
				toReturn.add(pageRange);
		}
		Collections.sort(toReturn, RangeAnnotationComparator.INSTANCE );
		return toReturn;
	}
	
	@Override
	public boolean intersectsRange(RangeAnnotation other) {
		System.out.println("CALLED!");
		long otherStart = other.getStart();
		long otherEnd = other.getEnd();
		long thisStart = getStart();
		long thisEnd = getEnd();
		boolean isOthersStartSmaller = otherStart <= thisStart;
		boolean isOthersStartGreater = otherStart >= thisStart;
		boolean isContaining = (isOthersStartSmaller && (otherEnd >= thisStart)) || (isOthersStartGreater && (otherStart <= thisEnd));
		if (DEBUG_CONTAINS_RANGE)
			DebugHelper.print(getClass(), 
					"--->" 
					+"(isOthersStartSmaller && (otherEnd >= thisStart)) || (isOthersStartGreater && (otherStart <= thisEnd))" + isContaining + "\n" 
					+"isOthersStartGreater: " + isOthersStartGreater + "\n" +
					" | thisStart "	+ thisStart+ " thisEnd: " + thisEnd + "\n" 
							+ " | otherStart: " + otherStart + " otherEnd: " + otherEnd, isContaining);
		return isContaining;
	}

	@Override
	public boolean equalsRange(RangeAnnotation rangeAnnotation) {
		return (getStart() == rangeAnnotation.getStart()) && (getEnd() == rangeAnnotation.getEnd());
	}

}
