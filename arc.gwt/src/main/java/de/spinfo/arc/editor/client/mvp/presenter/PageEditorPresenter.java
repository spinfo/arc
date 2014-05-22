package de.spinfo.arc.editor.client.mvp.presenter;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;

import de.spinfo.arc.editor.client.mvp.ui.WordWidget;

public interface PageEditorPresenter extends Presenter {

	void onCanvasMouseDown(MouseDownEvent event);

	void onCanvasMouseMove(MouseMoveEvent event);

	void onCanvasMouseUp(MouseUpEvent event);

	void onNextPageClicked();

	void onPreviousPageClicked();

	void setIsDoneLoadingPageImage(boolean doneLoadingPageImage);

	void onLeftArrowKey();

	void onRightArrowKey();





}
