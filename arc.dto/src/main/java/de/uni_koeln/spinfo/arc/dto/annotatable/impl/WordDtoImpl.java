package de.uni_koeln.spinfo.arc.dto.annotatable.impl;

import java.io.Serializable;
import java.util.ArrayList;
//import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.uni_koeln.spinfo.arc.dto.annotatable.WordDto;
import de.uni_koeln.spinfo.arc.dto.annotatable.WorkingUnitDto;
import de.uni_koeln.spinfo.arc.dto.annotation.AnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.AnnotationDto.AnnotationTypes;
import de.uni_koeln.spinfo.arc.dto.annotation.FormAnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.PosAnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.RectangleAnnotationDto;
import de.uni_koeln.spinfo.arc.dto.comparator.HasDetailsDateComparator;


public class WordDtoImpl extends HasAnnotationsDtoImpl implements WordDto, Serializable {
	
	private static final long serialVersionUID = 1L;

	private long index;
	
	private WorkingUnitDto parentUnit;
	private Set<String> taggerPosOptions = new HashSet<>();
	
	/**
	 * empty Constructor for GWT - don't use otherwise!
	 */
	public WordDtoImpl() {}
	
	public WordDtoImpl(long index, FormAnnotationDto initialForm, RectangleAnnotationDto initialRect) {
		this.index = index;
		super.setAnnotationAsType(AnnotationTypes.FORM, initialForm);
		super.setAnnotationAsType(AnnotationTypes.RECTANGLE, initialRect);
	}
	
	public WordDtoImpl(long index, List<FormAnnotationDto> initialForms, RectangleAnnotationDto initialRect) {
		this.index = index;
		for (Iterator<FormAnnotationDto> iterator = initialForms.iterator(); iterator.hasNext();) {
			FormAnnotationDto formAnnotation = (FormAnnotationDto) iterator.next();
			super.setAnnotationAsType(AnnotationTypes.FORM, formAnnotation);
		}
		super.setAnnotationAsType(AnnotationTypes.RECTANGLE, initialRect);
	}


	@Override
	public List<FormAnnotationDto> getAllFormsAnnotations() {
		List<FormAnnotationDto> toReturn = new ArrayList<FormAnnotationDto>();
		List<AnnotationDto> annotations =  getAnnotationsOfType(AnnotationTypes.FORM);
		for (Iterator<AnnotationDto> iterator = annotations.iterator(); iterator.hasNext();) {
			AnnotationDto annotation = iterator.next();
			if (annotation instanceof FormAnnotationDto) {
				FormAnnotationDto form = (FormAnnotationDto) annotation;
				toReturn.add(form); 
			}
		}
		Collections.sort(toReturn, HasDetailsDateComparator.INSTANCE);
		return toReturn;
	}
	
	
	@Override
	public FormAnnotationDto getLastFormAnnotation() {
		List<FormAnnotationDto> forms = getAllFormsAnnotations();
		return forms.get(forms.size()-1);
	}
	
	@Override
	public List<RectangleAnnotationDto> getAllRectangleAnnotations() {
		List<RectangleAnnotationDto> toReturn = new ArrayList<>();
		List<AnnotationDto> annotations =  getAnnotationsOfType(AnnotationTypes.RECTANGLE);
		for (Iterator<AnnotationDto> iterator = annotations.iterator(); iterator.hasNext();) {
			AnnotationDto annotation = iterator.next();
			if (annotation instanceof RectangleAnnotationDto) {
				RectangleAnnotationDto rect = (RectangleAnnotationDto) annotation;
				toReturn.add(rect); 
			}
		}
		Collections.sort(toReturn, HasDetailsDateComparator.INSTANCE);
		return toReturn;
	}
	
	
	@Override
	public RectangleAnnotationDto getLastRectangleAnnotation() {
		List<RectangleAnnotationDto> rects = getAllRectangleAnnotations();
		return rects.get(rects.size()-1);
	}
	
	@Override
	public List<PosAnnotationDto> getAllPosAnnotations() {
		List<PosAnnotationDto> toReturn = new ArrayList<>();
		List<AnnotationDto> annotations =  getAnnotationsOfType(AnnotationTypes.POS);
		for (Iterator<AnnotationDto> iterator = annotations.iterator(); iterator.hasNext();) {
			AnnotationDto annotation = iterator.next();
			 if (annotation instanceof PosAnnotationDto) {
				 PosAnnotationDto rect = (PosAnnotationDto) annotation;
				toReturn.add(rect); 
			}
		}
		Collections.sort(toReturn, HasDetailsDateComparator.INSTANCE);
		return toReturn;
	}


	@Override
	public PosAnnotationDto getLastPosAnnotation() {
		List<PosAnnotationDto> pos = getAllPosAnnotations();
		return pos.isEmpty() ? null : pos.get(pos.size()-1);
	}
	
	@Override
	public void setAnnotationAsType(AnnotationTypes type, AnnotationDto annotation) {
		if (isTypeValid(type))
			super.setAnnotationAsType(type, annotation);
	}
	
	@Override
	public void setAnnotationsAsType(AnnotationTypes type, List<AnnotationDto> annotations) {
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
	public WorkingUnitDto getParentUnit() {
		return parentUnit;
	}

	@Override
	public void setParentUnit(WorkingUnitDto parentUnit) {
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
