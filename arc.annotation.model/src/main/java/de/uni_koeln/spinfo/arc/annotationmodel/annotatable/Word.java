package de.uni_koeln.spinfo.arc.annotationmodel.annotatable;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import de.uni_koeln.spinfo.arc.annotationmodel.annotation.FormAnnotation;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.PosAnnotation;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.RectangleAnnotation;

@Document(collection = "words")
public interface Word extends HasAnnotations, HasParentUnit<WorkingUnit>, HasTaggerPosOptions, HasIndex {
	/**
	 * Convenience Method
	 * Get the Last Form Annotation of this word sorted by date
	 * @return  the Last Form Annotation of this word
	 */
	public FormAnnotation getLastFormAnnotation();
	
	/**
	 * Convenience Method
	 * Get all Form Annotation of this word sorted by date
	 * @return All Form-Annotations of this word
	 */
	public List<FormAnnotation> getAllFormsAnnotations();

	/**
	 * Convenience Method
	 * Get all Rect Annotation of this word sorted by date
	 * @return All Rect-Annotations of this word
	 */
	List<RectangleAnnotation> getAllRectangleAnnotations();
	
	/**
	 * Convenience Method
	 * Get the Last Rect Annotation of this word sorted by date
	 * @return  the Last Rect Annotation of this word
	 */
	public RectangleAnnotation getLastRectangleAnnotation();

	List<PosAnnotation> getAllPosAnnotations();

	PosAnnotation getLastPosAnnotation();
	
}
