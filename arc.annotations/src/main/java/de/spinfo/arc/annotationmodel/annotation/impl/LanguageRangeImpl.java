package de.spinfo.arc.annotationmodel.annotation.impl;

import de.spinfo.arc.annotationmodel.annotation.FormAnnotation;
import de.spinfo.arc.annotationmodel.annotation.LanguageRange;

import java.io.Serializable;
import java.util.Date;

public class LanguageRangeImpl extends RangeAnnotationImpl implements LanguageRange, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	
	/**
	 * empty Constructor for GWT - don't use otherwise!
	 */
	public LanguageRangeImpl() {}
	
	public LanguageRangeImpl(Date date, int score, String userId, int start,
			int end, String title) {
		super(date, score, userId,  start, end);
		this.title = title;
	}
//	public LanguageRangeImpl(Date date, int score, String userId, int start,
//			int end) {
//		super(date, score, userId, start, end);
//
//	}
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
		if (obj instanceof FormAnnotation) {
			LanguageRange other = (LanguageRange) obj;
			boolean isTitle = getTitle().equals(other.getTitle());
			if (!isTitle) 
				return false;
			return super.equals(obj);
		} 
		else 
			return false;
	}
}
