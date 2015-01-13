package de.uni_koeln.spinfo.arc.dto.annotation.impl;

import java.io.Serializable;
import java.util.Date;

import de.uni_koeln.spinfo.arc.dto.annotation.FormAnnotationDto;

public class FormAnnotationDtoImpl extends AnnotationDtoImpl implements FormAnnotationDto, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String form; 
	
	/**
	 * empty Constructor for GWT - don't use otherwise!
	 */
	public FormAnnotationDtoImpl() {}

	public FormAnnotationDtoImpl(Date date, int score, String userId, String form) {
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
		if (obj instanceof FormAnnotationDto) {
			FormAnnotationDto other = (FormAnnotationDto) obj;
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
