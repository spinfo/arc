package de.uni_koeln.spinfo.arc.dto.annotatable;

import java.util.List;



import de.uni_koeln.spinfo.arc.dto.annotation.FormAnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.PosAnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.RectangleAnnotationDto;

public interface WordDto extends HasAnnotationsDto, HasParentUnitDto<WorkingUnitDto>, HasTaggerPosOptionsDto, HasIndexDto {
	/**
	 * Convenience Method
	 * Get the Last Form Annotation of this word sorted by date
	 * @return  the Last Form Annotation of this word
	 */
	public FormAnnotationDto getLastFormAnnotation();
	
	/**
	 * Convenience Method
	 * Get all Form Annotation of this word sorted by date
	 * @return All Form-Annotations of this word
	 */
	public List<FormAnnotationDto> getAllFormsAnnotations();

	/**
	 * Convenience Method
	 * Get all Rect Annotation of this word sorted by date
	 * @return All Rect-Annotations of this word
	 */
	List<RectangleAnnotationDto> getAllRectangleAnnotations();
	
	/**
	 * Convenience Method
	 * Get the Last Rect Annotation of this word sorted by date
	 * @return  the Last Rect Annotation of this word
	 */
	public RectangleAnnotationDto getLastRectangleAnnotation();

	List<PosAnnotationDto> getAllPosAnnotations();

	PosAnnotationDto getLastPosAnnotation();
	
}
