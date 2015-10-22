package de.uni_koeln.spinfo.arc.dto.annotation.impl;

import java.io.Serializable;
import java.util.Date;

import de.uni_koeln.spinfo.arc.dto.annotation.PageRangeDto;

public class PageRangeDtoImpl extends RangeAnnotationDtoImpl implements PageRangeDto, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;

	/**
	 * empty Constructor for GWT - don't use otherwise!
	 */
	public PageRangeDtoImpl() {};
	
	public PageRangeDtoImpl(Date date, int score, String userId, int start,
			int end, String url) {
		super(date, score, userId,  start, end);
		this.url = url;
	}


	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String getUrl() {
		return url;
	}


	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + getUrl().hashCode();
		result = 31 * super.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof PageRangeDto) {
			PageRangeDto other = (PageRangeDto) obj;
			boolean isUrl = getUrl().equals(other.getUrl());
			if (!isUrl) 
				return false;
			return super.equals(obj);
		} 
		else 
			return false;
	}
}
