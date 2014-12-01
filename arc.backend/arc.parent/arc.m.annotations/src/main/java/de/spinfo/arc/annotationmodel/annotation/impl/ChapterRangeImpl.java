package de.spinfo.arc.annotationmodel.annotation.impl;

import java.io.Serializable;
import java.util.Date;

import de.spinfo.arc.annotationmodel.annotation.ChapterRange;

public class ChapterRangeImpl extends RangeAnnotationImpl implements ChapterRange, Serializable { 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	
	/**
	 * empty Constructor for GWT - don't use otherwise!
	 */
	public ChapterRangeImpl(){};
	
	public ChapterRangeImpl(Date date, int score, String userId, int start,
			int end, String title) {
		super(date, score, userId,  start, end);
		this.title = title;
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
	public int hashCode() {
		int result = 17;
		result = 31 * result + getTitle().hashCode();
		result = 31 * super.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof ChapterRange) {
			ChapterRange other = (ChapterRange) obj;
			boolean isTitle = getTitle().equals(other.getTitle());
			if (!isTitle) 
				return false;
			return super.equals(obj);
		} 
		else 
			return false;
	}
}
