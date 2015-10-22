package de.uni_koeln.spinfo.arc.dto.annotatable;
import java.util.List;
import java.util.Map;

import de.uni_koeln.spinfo.arc.dto.annotation.AnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.AnnotationDto.AnnotationTypes;


public interface HasAnnotationsDto  {

	public Map<AnnotationTypes, List<AnnotationDto>> getAnnotations();
	public List<AnnotationDto> getAnnotationsOfType(AnnotationTypes type);
	
	public void setAnnotationAsType(AnnotationTypes type, AnnotationDto annotation);
	public void setAnnotationsAsType(AnnotationTypes type, List<AnnotationDto> annotations);
	boolean removeAnnotation(AnnotationTypes annotationTypes,
			AnnotationDto annotation);
	
	
}
