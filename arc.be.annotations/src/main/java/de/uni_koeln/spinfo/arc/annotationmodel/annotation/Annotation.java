package de.uni_koeln.spinfo.arc.annotationmodel.annotation;

public interface Annotation extends HasDetails {
	
	/**
	 * 
	 * @author drival
	 *
	 */
	public static enum AnnotationTypes {
		RECTANGLE, 
		FORM, 
		POS, 
		PAGE_RANGE, 
		CHAPTER_RANGE, 
		LANGUAGE_RANGE, 
		VOLUME_RANGE, 
	} 
	
}
