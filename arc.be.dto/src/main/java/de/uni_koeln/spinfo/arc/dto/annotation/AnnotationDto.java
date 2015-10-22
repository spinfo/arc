package de.uni_koeln.spinfo.arc.dto.annotation;

public interface AnnotationDto extends HasDetailsDto {
	
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
