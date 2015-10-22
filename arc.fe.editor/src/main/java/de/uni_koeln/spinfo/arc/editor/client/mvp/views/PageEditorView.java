package de.uni_koeln.spinfo.arc.editor.client.mvp.views;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ScrollPanel;

import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.PageEditorPresenter;
import de.uni_koeln.spinfo.arc.editor.client.mvp.ui.WordWidget;

public interface PageEditorView extends IsWidget, HasPresenter<PageEditorPresenter> {
	
	/**
	 * This Method should be called after the parameterless constructor. The reason why this is not in the constructor is
	 * because one couldn't cache the instance in a factory if it would be page specific.
	 * 
	 * @param pageNum
	 * @param words
	 */
	void setPageNum(int pageNum);
	void setAmountTotalPages(int totalAmountPages);
	
	/**
	 * Gets the Panel which holds the canvas to draw on
	 * 
	 * @return the Panel where the canvas is in
	 */
	HasWidgets getCanvasPanel();
	
	HasWidgets getWordWidgetPanel();

	void clearCanvas();
	
	void drawResizableRect(int x, int y, int width, int height);

//	void onWordRightClicked(WordWidgetImpl wordWidgetImpl,
//			ContextMenuEvent event);


//	void onWordClicked(WordWidgetImpl wordWidgetImpl, ClickEvent e);


//	void onWordRightClicked(WordWidget wordWidget, ContextMenuEvent event);

//	void onWordClicked(WordWidget wordWidget, ClickEvent e);

	void addWord(WordWidget word);

	void drawWord(int x, int y, int w, int h, CssColor wordSelectionFrameColor);

	Canvas getCanvas();

	Context2d getContext();
	ImageElement getPageImageElement();
	void drawPageImage();
	void setPageImageElement(Image img);
	int getPageImageWidth();
	int getPageImageHeight();
	ScrollPanel getWordsScrollPanel();
	Button getNextPageBtn();
	Button getPreviousPageBtn();
	void setModeInfoText(String text);
	void clearWordWidgets();

	 
}
