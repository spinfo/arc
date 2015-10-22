package de.uni_koeln.spinfo.arc.annotationmodel.annotation.impl;

import de.uni_koeln.spinfo.arc.annotationmodel.annotation.FormAnnotation;

import java.io.Serializable;
import java.util.Date;

public class FormAnnotationImpl extends AnnotationImpl implements FormAnnotation, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String form; 
	
	/**
	 * empty Constructor for GWT - don't use otherwise!
	 */
	public FormAnnotationImpl() {}

	public FormAnnotationImpl(Date date, int score, String userId, String form) {
		super(date, score, userId);
		this.form = form;
	}

	@Override
	public String getForm() {
		return form;
	}

	@Override
	public void setForm(String form) {
		this.form = form;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + getForm().hashCode();
		result = 31 * super.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof FormAnnotation) {
			FormAnnotation other = (FormAnnotation) obj;
			boolean isForm = getForm().equals(other.getForm());
			if (!isForm) 
				return false;
			return super.equals(obj);
		} 
		else 
			return false;
	}
	
	@Override
	public String toString() {
		return super.toString() + "\nFormAnnotationImpl [form=" + form + "]";
	}   

}
