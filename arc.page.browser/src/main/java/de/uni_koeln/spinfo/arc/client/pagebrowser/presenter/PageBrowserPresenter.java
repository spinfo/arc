package de.uni_koeln.spinfo.arc.client.pagebrowser.presenter;

import org.cafesip.gwtcomp.client.ui.SuperTreeListener;

import de.uni_koeln.spinfo.arc.dto.annotation.AnnotationDto.AnnotationTypes;


public interface PageBrowserPresenter extends SuperTreeListener{

	void onAddAnnotation(AnnotationTypes type, int start, int end);

	void onEditPage(int pageNum);

}
