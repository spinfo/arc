package de.uni_koeln.spinfo.arc.annotationmodel.annotation;


public interface RangeAnnotation extends Annotation {
	public long getStart();
	public long getEnd();
	/**
	 * Checks another RangeAnnotation Type if its intersecting this instance
	 * This comparison is measured just by the start and end bounds. 
	 * @param other the instance to check if its intersecting this range
	 * @return true if the other instances range is intersecting this one
	 */
	public boolean intersectsRange(RangeAnnotation other);
	
	/**
	 * Compares the start and end index amd returns true if both are equal.
	 * This should be used for loose equality (opposed to the the equals Method wich 
	 * uses the data and userId etc. fields as well to determine equality)
	 * 
	 * @param rangeAnnotation
	 * @return true if start and end-indices match
	 */
	public boolean equalsRange(RangeAnnotation rangeAnnotation);
}
