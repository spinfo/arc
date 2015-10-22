package de.uni_koeln.spinfo.arc.annotationmodel.annotation.impl;

import de.uni_koeln.spinfo.arc.annotationmodel.annotation.PageRange;

import java.io.Serializable;
import java.util.Date;

public class PageRangeImpl extends RangeAnnotationImpl implements PageRange, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	
	/*@Todo implement for serialization*/
	private String printedPageNuber = "";

	/**
	 * empty Constructor for GWT - don't use otherwise!
	 */
	public PageRangeImpl() {};
	
	public PageRangeImpl(Date date, int score, String userId, int start,
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
		if (obj instanceof PageRange) {
			PageRange other = (PageRange) obj;
			boolean isUrl = getUrl().equals(other.getUrl());
			if (!isUrl) 
				return false;
			return super.equals(obj);
		} 
		else 
			return false;
	}
}
