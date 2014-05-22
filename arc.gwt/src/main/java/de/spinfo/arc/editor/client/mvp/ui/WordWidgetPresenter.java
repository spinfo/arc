package de.spinfo.arc.editor.client.mvp.ui;

import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;

import de.spinfo.arc.editor.client.mvp.presenter.Presenter;

public interface WordWidgetPresenter extends Presenter {
	void onWordClicked(int index);

	void onWordRightClicked(int index, ContextMenuHandler contextMenuHandler,
			ContextMenuEvent event);
}
