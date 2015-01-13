package de.uni_koeln.spinfo.arc.dto.annotation.impl;

import java.io.Serializable;
import java.util.Date;

import de.uni_koeln.spinfo.arc.dto.annotation.ChapterRangeDto;

public class ChapterRangeDtoImpl extends RangeAnnotationDtoImpl implements ChapterRangeDto, Serializable { 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	
	/**
	 * empty Constructor for GWT - don't use otherwise!
	 */
	public ChapterRangeDtoImpl(){};
	
	public ChapterRangeDtoImpl(Date date, int score, String userId, int start,
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
		if (obj instanceof ChapterRangeDto) {
			ChapterRangeDto other = (ChapterRangeDto) obj;
			boolean isTitle = getTitle().equals(other.getTitle());
			if (!isTitle) 
				return false;
			return super.equals(obj);
		} 
		else 
			return false;
	}
}
