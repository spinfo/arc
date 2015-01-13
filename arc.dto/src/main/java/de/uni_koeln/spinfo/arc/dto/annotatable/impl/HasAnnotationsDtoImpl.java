package de.uni_koeln.spinfo.arc.dto.annotatable.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.uni_koeln.spinfo.arc.dto.annotatable.HasAnnotationsDto;
import de.uni_koeln.spinfo.arc.dto.annotation.AnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.AnnotationDto.AnnotationTypes;
import de.uni_koeln.spinfo.arc.dto.util.DebugHelper;



public class HasAnnotationsDtoImpl implements HasAnnotationsDto, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final boolean DEBUG_APPEND_MODIFICATION = false;
	Map<AnnotationTypes, List<AnnotationDto>> annotations = new HashMap<>();
	
	/**
	 * empty Constructor for GWT - don't use otherwise!
	 */
	public HasAnnotationsDtoImpl() {}

	@Override
	public Map<AnnotationTypes, List<AnnotationDto>> getAnnotations() {
		return annotations;
	}

	@Override 
	public void setAnnotationAsType(AnnotationTypes type, AnnotationDto annotation) {
		if (annotations.get(type) == null) {
			List<AnnotationDto> genericAnnotationList = new ArrayList<AnnotationDto>();
			genericAnnotationList.add(annotation);
			annotations.put(type, genericAnnotationList);
		} else {
			annotations.get(type).add(annotation);
		}
		
		if (DEBUG_APPEND_MODIFICATION)
			DebugHelper.print(getClass(), "appended annotation: " + annotation.toString(), true);
	}
	
	
	@Override
	public void setAnnotationsAsType(AnnotationTypes type,
			List<AnnotationDto> annotations) {
		if (this.annotations.get(type) == null) {
			List<AnnotationDto> genericAnnotationList = new ArrayList<AnnotationDto>();
			genericAnnotationList.addAll(annotations);
			this.annotations.put(type, genericAnnotationList);
		} else {
			this.annotations.get(type).addAll(annotations);
		}
	}
	  

	@Override
	public List<AnnotationDto> getAnnotationsOfType(AnnotationTypes type) {
		List<AnnotationDto> toReturn = new ArrayList<>();
		if (annotations.get(type) == null)
			return toReturn;
		else
			return annotations.get(type);
	}	
	
	@Override
	public boolean removeAnnotation(AnnotationTypes annotationTypes, AnnotationDto annotation) {
		return getAnnotations().get(annotationTypes).remove(annotation);
	}
	
	@Override
	public String toString() {
		return "Annotatable [annotations=" + annotations + "]";
	}


}
