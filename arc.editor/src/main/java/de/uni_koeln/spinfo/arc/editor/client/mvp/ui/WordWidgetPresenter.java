package de.uni_koeln.spinfo.arc.editor.client.mvp.ui;

import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;

import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.Presenter;

/**
 * 
 * The Presenter which corrresponds to a {@link de.uni_koeln.spinfo.arc.editor.client.mvp.ui.WordWidget}
 * @author David Rival
 *
 */
public interface WordWidgetPresenter extends Presenter {
	/**
	 * Gets called if a certain WordWidget is clicked
	 * @param index the index of the clicked Word
	 */
	void onWordClicked(int index);
	
	/**
	 * Gets called if the context menu (e.g. right mouse btn) is clicked ontop
	 * of a certain wird
	 * @param index the wordindex
	 * @param contextMenuHandler the handler
	 * @param event 
	 */
	void onWordRightClicked(int index, ContextMenuHandler contextMenuHandler, ContextMenuEvent event);
}
