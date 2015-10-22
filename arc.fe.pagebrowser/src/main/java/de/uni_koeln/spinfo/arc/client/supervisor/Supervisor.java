package de.uni_koeln.spinfo.arc.client.supervisor;

import de.uni_koeln.spinfo.arc.dto.annotation.AnnotationDto.AnnotationTypes;

public interface Supervisor {
	/**
	 * Called by the page browser in order to inform the page editor
	 * to switch to selection of start and end words
	 * 
	 * @param type
	 * @param start
	 * @param end
	 */
	void onAddRangeAnnotation(AnnotationTypes type, int pageNumStart, int pageNumEnd);
	/**
	 * Called by the page browser in order to inform the page editor
	 * @param pageNum
	 */
	void onEditPage(int pageNum);
}
