package de.uni_koeln.spinfo.arc.dto.annotation;


public interface FormAnnotationDto extends AnnotationDto, HasDetailsDto {
	public String getForm();
	public void setForm(String form); 
}

