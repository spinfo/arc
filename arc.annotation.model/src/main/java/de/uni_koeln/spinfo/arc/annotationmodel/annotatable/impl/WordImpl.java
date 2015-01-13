package de.uni_koeln.spinfo.arc.annotationmodel.annotatable.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.Word;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.WorkingUnit;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.Annotation;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.FormAnnotation;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.PosAnnotation;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.RectangleAnnotation;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.Annotation.AnnotationTypes;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.PosAnnotation.PosTags;
import de.uni_koeln.spinfo.arc.annotationmodel.comparator.HasDetailsDateComparator;

@Document(collection = "words")
public class WordImpl extends HasAnnotationsImpl implements Word, Serializable {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//	private String id;
//	@Id
//	private String id;
//	@Id
	@Indexed(unique = true, dropDups = true)
	private long index;
	
	WorkingUnit parentUnit;
	Set<String> taggerPosOptions = new HashSet<>();

	
	/**
	 * empty Constructor for GWT - don't use otherwise!
	 */
	public WordImpl() {}
	
	public WordImpl(long index, FormAnnotation initialForm, RectangleAnnotation initialRect) {
		this.index = index;
		super.setAnnotationAsType(AnnotationTypes.FORM, initialForm);
		super.setAnnotationAsType(AnnotationTypes.RECTANGLE, initialRect);
	}
	
	public WordImpl(long index, List<FormAnnotation> initialForms, RectangleAnnotation initialRect) {
		this.index = index;
		for (Iterator<FormAnnotation> iterator = initialForms.iterator(); iterator.hasNext();) {
			FormAnnotation formAnnotation = (FormAnnotation) iterator.next();
			super.setAnnotationAsType(AnnotationTypes.FORM, formAnnotation);
		}
		super.setAnnotationAsType(AnnotationTypes.RECTANGLE, initialRect);
	}


	@Override
	public List<FormAnnotation> getAllFormsAnnotations() {
		List<FormAnnotation> toReturn = new ArrayList<FormAnnotation>();
		List<Annotation> annotations =  getAnnotationsOfType(AnnotationTypes.FORM);
		for (Iterator<Annotation> iterator = annotations.iterator(); iterator.hasNext();) {
			Annotation annotation = iterator.next();
			if (annotation instanceof FormAnnotation) {
				FormAnnotation form = (FormAnnotation) annotation;
				toReturn.add(form); 
			}
		}
		Collections.sort(toReturn, HasDetailsDateComparator.INSTANCE);
		return toReturn;
	}
	
	
	@Override
	public FormAnnotation getLastFormAnnotation() {
		List<FormAnnotation> forms = getAllFormsAnnotations();
		return forms.get(forms.size()-1);
	}
	
	@Override
	public List<RectangleAnnotation> getAllRectangleAnnotations() {
		List<RectangleAnnotation> toReturn = new ArrayList<>();
		List<Annotation> annotations =  getAnnotationsOfType(AnnotationTypes.RECTANGLE);
		for (Iterator<Annotation> iterator = annotations.iterator(); iterator.hasNext();) {
			Annotation annotation = iterator.next();
			if (annotation instanceof RectangleAnnotation) {
				RectangleAnnotation rect = (RectangleAnnotation) annotation;
				toReturn.add(rect); 
			}
		}
		Collections.sort(toReturn, HasDetailsDateComparator.INSTANCE);
		return toReturn;
	}
	
	
	@Override
	public RectangleAnnotation getLastRectangleAnnotation() {
		List<RectangleAnnotation> rects = getAllRectangleAnnotations();
		return rects.get(rects.size()-1);
	}
	
	@Override
	public List<PosAnnotation> getAllPosAnnotations() {
		List<PosAnnotation> toReturn = new ArrayList<>();
		List<Annotation> annotations =  getAnnotationsOfType(AnnotationTypes.POS);
		for (Iterator<Annotation> iterator = annotations.iterator(); iterator.hasNext();) {
			Annotation annotation = iterator.next();
			 if (annotation instanceof PosAnnotation) {
				 PosAnnotation rect = (PosAnnotation) annotation;
				toReturn.add(rect); 
			}
		}
		Collections.sort(toReturn, HasDetailsDateComparator.INSTANCE);
		return toReturn;
	}


	@Override
	public PosAnnotation getLastPosAnnotation() {
		List<PosAnnotation> pos = getAllPosAnnotations();
		return pos.isEmpty() ? null : pos.get(pos.size()-1);
	}
	
	@Override
	public void setAnnotationAsType(AnnotationTypes type, Annotation annotation) {
		if (isTypeValid(type))
			super.setAnnotationAsType(type, annotation);
	}
	
	@Override
	public void setAnnotationsAsType(AnnotationTypes type, List<Annotation> annotations) {
		if (isTypeValid(type)) 
			super.setAnnotationsAsType(type, annotations);
	}
	
	private boolean isTypeValid(AnnotationTypes type) {
		if (!type.equals(AnnotationTypes.FORM) && !type.equals(AnnotationTypes.RECTANGLE) && !type.equals(AnnotationTypes.POS)) 
			throw new IllegalArgumentException("the passed type must be either of " + AnnotationTypes.FORM.toString() + " or of " + AnnotationTypes.RECTANGLE.toString() + " or of " + AnnotationTypes.RECTANGLE.toString());
		else 
			return true;
	}
	
	@Override
	public WorkingUnit getParentUnit() {
		return parentUnit;
	}

	@Override
	public void setParentUnit(WorkingUnit parentUnit) {
		this.parentUnit = parentUnit;
	}

	@Override
	public Set<String> getTaggerPosOptions() {
		return taggerPosOptions;
	}

	@Override 
	public void setTaggerPosOptions(Set<String> posOptions) {
		this.taggerPosOptions = posOptions;
	}

	@Override
	public void appendTaggerPosOption(String posOption) {
		this.taggerPosOptions.add(posOption);
	}

	@Override
	public long getIndex() {
		return index;
	}

}
