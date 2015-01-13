package de.uni_koeln.spinfo.arc.annotationmodel.annotatable.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.HasAnnotations;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.Annotation;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.Annotation.AnnotationTypes;
import de.uni_koeln.spinfo.arc.annotationmodel.util.DebugHelper;
 

public class HasAnnotationsImpl implements HasAnnotations, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final boolean DEBUG_APPEND_MODIFICATION = false;
	Map<AnnotationTypes, List<Annotation>> annotations = new HashMap<>();
	
//	private int index = -1;
	
//	@Id
//	protected String id;
	
	/**
	 * empty Constructor for GWT - don't use otherwise!
	 */
	public HasAnnotationsImpl() {}

	@Override
	public Map<AnnotationTypes, List<Annotation>> getAnnotations() {
		return annotations;
	}

	@Override 
	public void setAnnotationAsType(AnnotationTypes type, Annotation annotation) {
		if (annotations.get(type) == null) {
			List<Annotation> genericAnnotationList = new ArrayList<Annotation>();
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
			List<Annotation> annotations) {
		if (this.annotations.get(type) == null) {
			List<Annotation> genericAnnotationList = new ArrayList<Annotation>();
			genericAnnotationList.addAll(annotations);
			this.annotations.put(type, genericAnnotationList);
		} else {
			this.annotations.get(type).addAll(annotations);
		}
	}
	 

	@Override
	public List<Annotation> getAnnotationsOfType(AnnotationTypes type) {
		List<Annotation> toReturn = new ArrayList<>();
		if (annotations.get(type) == null)
			return toReturn;
		else
			return annotations.get(type);
	}	
	
	@Override
	public boolean removeAnnotation(AnnotationTypes annotationTypes, Annotation annotation) {
		return getAnnotations().get(annotationTypes).remove(annotation);
	}
	
//	@Override
//	public void setIndex(int index) {
//		this.index = index;
//		
//	}
//
//	@Override
//	public int getIndex() {
//		return index;
//	}

	@Override
	public String toString() {
		return "Annotatable [annotations=" + annotations + "]";
	}


}
