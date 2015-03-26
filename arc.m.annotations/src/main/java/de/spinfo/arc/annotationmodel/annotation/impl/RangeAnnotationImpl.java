package de.spinfo.arc.annotationmodel.annotation.impl;

import de.spinfo.arc.annotationmodel.annotation.RangeAnnotation;
import de.spinfo.arc.annotationmodel.util.DebugHelper;

import java.io.Serializable;
import java.util.Date;

public class RangeAnnotationImpl extends AnnotationImpl implements RangeAnnotation, Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final boolean DEBUG_CONTAINS_RANGE = false;
	private int start;
	private int end;
	
	/**
	 * empty Constructor for GWT - don't use otherwise!
	 */
	public RangeAnnotationImpl(){};
	
	public RangeAnnotationImpl(Date date, int score, String userId, int start,
			int end) {
		super(date, score, userId);
		this.start = start;
		this.end = end;
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
	public int hashCode() {
		int result = 17;
		result = (int) (31 * result + getStart());
		result = (int) (31 * result + getEnd());
		result = 31 * result + super.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof RangeAnnotation) {
			RangeAnnotation other = (RangeAnnotation) obj;
			
			boolean isStartAndEnd = (
					getStart() == other.getStart()
					&& getEnd() == other.getEnd()
					);
			if (!isStartAndEnd)	
				return false;
			
			return super.equals(obj);
		} 
		else 
			return false;
		
	}
	
	@Override
	public boolean equalsRange(RangeAnnotation rangeAnnotation) {
		return (getStart() == rangeAnnotation.getStart()) 
				&& (getEnd() == rangeAnnotation.getEnd());
	}
	
	@Override
	public boolean intersectsRange(RangeAnnotation other) {
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
	public String toString() {
		return super.toString() + "\n RangeAnnotationImpl [start=" + start + ", end=" + end + "]";
	}


}
