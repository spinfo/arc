package de.uni_koeln.spinfo.arc.dto.annotatable.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import de.uni_koeln.spinfo.arc.dto.annotatable.WorkingUnitDto;
import de.uni_koeln.spinfo.arc.dto.annotation.AnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.ChapterRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.LanguageRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.PageRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.RangeAnnotationDto;
import de.uni_koeln.spinfo.arc.dto.comparator.RangeAnnotationComparator;
import de.uni_koeln.spinfo.arc.dto.util.DebugHelper;

public class WorkingUnitDtoImpl extends HasAnnotationsDtoImpl implements WorkingUnitDto, Serializable {
	/**
	 * Default id
	 */
	private static final long serialVersionUID = 1L;
	
	private static final boolean DEBUG_CONTAINS_RANGE = true;
	private Date date;
	private int score;
	private String userId;
	private String title;
	
	private long start;
	private long end;
	
	/**
	 * empty Constructor for GWT - don't use otherwise!
	 */
	public WorkingUnitDtoImpl() {}
	
//	public WorkingUnitImpl(Date date, int score, String userId, String title) {
//		super();
////		words = new ArrayList<>();
//		this.date = date;
//		this.score = score;
//		this.userId = userId;
//		this.title = title;
//	}
	public WorkingUnitDtoImpl(Date date, int score, String userId, String title, long wordStartIndex, long wordEndIndex) {
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
	public void setAnnotationAsType(AnnotationTypes type, AnnotationDto annotation) {
		if (isTypeValid(type))
			super.setAnnotationAsType(type, annotation);
	}
	
	@Override
	public void setAnnotationsAsType(AnnotationTypes type, List<AnnotationDto> annotations) {
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
	public List<PageRangeDto> getPages() {
		List<PageRangeDto> pagesToReturn =  new ArrayList<>();
		List<AnnotationDto> retrieved =  getAnnotationsOfType(AnnotationTypes.PAGE_RANGE);
		for (Iterator<AnnotationDto> iterator = retrieved.iterator(); iterator.hasNext();) {
			AnnotationDto annotation =  iterator.next();
			if (annotation instanceof PageRangeDto) {
				PageRangeDto pageRange = (PageRangeDto) annotation;
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
	public List<ChapterRangeDto> getChapters() {
		List<ChapterRangeDto> chaptersToReturn =  new ArrayList<>();
		List<AnnotationDto> retrieved =  getAnnotationsOfType(AnnotationTypes.CHAPTER_RANGE);
		for (Iterator<AnnotationDto> iterator = retrieved.iterator(); iterator.hasNext();) {
			AnnotationDto annotation =  iterator.next();
			if (annotation instanceof ChapterRangeDto) {
				ChapterRangeDto range = (ChapterRangeDto) annotation;
				chaptersToReturn.add(range);
			}
			
		}
		Collections.sort(chaptersToReturn, RangeAnnotationComparator.INSTANCE);
		return chaptersToReturn;
	}
	
	@Override
	public List<LanguageRangeDto> getLanguages() {
		List<LanguageRangeDto> languagesToReturn =  new ArrayList<>();
		List<AnnotationDto> retrieved =  getAnnotationsOfType(AnnotationTypes.LANGUAGE_RANGE);
		for (Iterator<AnnotationDto> iterator = retrieved.iterator(); iterator.hasNext();) {
			AnnotationDto annotation =  iterator.next();
			if (annotation instanceof LanguageRangeDto) {
				LanguageRangeDto range = (LanguageRangeDto) annotation;
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
				 + "]";
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
	public List<PageRangeDto> getPageRangesByRange(RangeAnnotationDto rangeAnnotation) {
		List<PageRangeDto> toReturn = new ArrayList<PageRangeDto>();
		for (Iterator<PageRangeDto> iterator = getPages().iterator(); iterator.hasNext();) {
			PageRangeDto pageRange = iterator.next();
			if(rangeAnnotation.intersectsRange(pageRange)) 
				toReturn.add(pageRange);
		}
		Collections.sort(toReturn, RangeAnnotationComparator.INSTANCE );
		return toReturn;
	}
	
	@Override
	public boolean intersectsRange(RangeAnnotationDto other) {
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
	public boolean equalsRange(RangeAnnotationDto rangeAnnotation) {
		return (getStart() == rangeAnnotation.getStart()) && (getEnd() == rangeAnnotation.getEnd());
	}

}
