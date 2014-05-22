package de.spinfo.arc.editor.client.mvp.presenter.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.aria.client.SelectedValue;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;

import de.spinfo.arc.editor.client.mvp.ClientFactory;
import de.spinfo.arc.editor.client.mvp.Tokens;
import de.spinfo.arc.editor.client.mvp.presenter.PageEditorDialogPresenter;
import de.spinfo.arc.editor.client.mvp.presenter.PageEditorPresenter;
import de.spinfo.arc.editor.client.mvp.ui.WordWidget;
import de.spinfo.arc.editor.client.mvp.ui.WordWidgetPresenter;
import de.spinfo.arc.editor.client.mvp.ui.impl.WordWidgetImpl;
import de.spinfo.arc.editor.client.mvp.views.PageEditorDialogView;
import de.spinfo.arc.editor.client.mvp.views.PageEditorView;
import de.spinfo.arc.editor.client.mvp.views.impl.PageEditorViewImpl;
import de.spinfo.arc.editor.shared.model3.modifiable.ModifiableWithParent;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;
import de.spinfo.arc.editor.shared.model3.util.DebugHelper;
import de.spinfo.arc.editor.shared.model3.util.WorkingUnitHelper;
import de.spinfo.arc.editor.shared.model3.workingunit.WorkingUnit;
import de.spinfo.arc.editor.shared.model3.modification.ModificationRectangle;
import de.spinfo.arc.editor.shared.model3.modification.ModificationString;
import de.spinfo.arc.editor.shared.model3.modification.impl.ModificationRectangleImpl;
import de.spinfo.arc.editor.shared.model3.modification.impl.ModificationStringImpl;
import de.spinfo.arc.editor.shared.model3.modification.payload.UsableGwtRectangle;
import de.spinfo.arc.editor.shared.model3.modification.payload.impl.UsableGwtRectangleImpl;

public class PageEditorPresenterImpl implements PageEditorPresenter, WordWidgetPresenter, PageEditorDialogPresenter {

	private PageEditorView pageEditor;
	private WorkingUnit workingUnit;
	private ClientFactory clientFactory;
	private PageEditorDialogView dialogView;
//	private Canvas canvas;
//	private Context2d context2dToDraw;
	private List<WordWidget> wordWidgets = new LinkedList<>();
	private List<ModifiableWithParent> words = new LinkedList<>();
	private int pageNum = 0;
	private int totalPageNum = 0;
	
	private final CssColor CSS_COLOR_WORD_SELECTION_FRAME = CssColor.make("rgba(" + 0 + ", " + 0 + "," + 255 + ", " + 1+ ")");
	
	private enum EditingStates {
		NONE, CONTEXT_MENU, EDIT_COORDINATES
	}
	
	private EditingStates STATE = EditingStates.NONE; 
	
	public PageEditorPresenterImpl(int pageNum, WorkingUnit workingUnit, ClientFactory clientFactory) {
		pageEditor = clientFactory.getPageEditorView();
		bind();
		// getting and setting up the dialogView
		dialogView = clientFactory.getPageEditorDialogView();
		dialogView.setPresenter(this);
		dialogView.asDialogBox().hide();
		
		this.pageNum = pageNum;
		totalPageNum = WorkingUnitHelper.getAllPageRanges(workingUnit).size();
		pageEditor.setAmountTotalPages(totalPageNum);
		words = WorkingUnitHelper.getWordsOfPage(pageNum, workingUnit);
		pageEditor.setPageNum(pageNum + 1);
		
		setUpCanvas();
		
		wordWidgets = clientFactory.getBlancWordWidgets(words.size());
		int wordIdx = 0;
		for (Iterator<ModifiableWithParent> iterator = words.iterator(); iterator.hasNext();) {
			ModifiableWithParent word = (ModifiableWithParent) iterator
					.next();
			WordWidget ww = wordWidgets.get(wordIdx);
			 
			String wordText = ((ModificationString) word.getLastModificationOf(Types.TEXT_MOD)).getText();
			ww.setIndex(wordIdx);
			ww.setWordText(wordText);
			ww.setPresenter(this);
			pageEditor.addWord(ww);
			wordIdx++;
		}
		
		this.workingUnit = workingUnit;
		this.clientFactory = clientFactory;
		

	}
	

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(pageEditor.asWidget());
	}

	@Override
	public void bind() {
		pageEditor.setPresenter(this);
	}
	
	/*
	 * drawing fields 
	 */
	ImageElement pageImage = null;
	 int imgWidth = 0;
	 int imgHeight = 0;
	 
//	 boolean isEditCoordinatsMode = false;
	 
	 boolean isWordRightClicked = false;
	 
	 boolean isInRectClicked = false;
	 boolean isLeftEdgeClicked = false;
	 boolean isRightEdgeClicked = false;
	 boolean isTopEdgeClicked = false;
	 boolean isBottomEdgeClicked = false;
	 
	 boolean isUpperLeftClicked = false;
	 boolean isUpperRightClicked = false;
	 boolean isLowerLeftClicked = false;
	 boolean isLowerRightClicked = false;
	 
	 boolean isClickedToDrawANewRect = false;
	 
	// how much pixel next to the edge tolerance is given to drag
	 final int GRAB_TOLERANCE = 20;
	 // the minimal width and height a rect can have if resized
	 final int MIN_SIZE_OF_RECT = 5;
	 
	 int currentX = 0;
	 int currentY = 0;
	 int currentWidth = 0;
	 int currentHeight= 0;
	 
//	 private  ModifiableWithParent lastSelectedWord = null;
	 private boolean isDoneLoadingPageImage = false;
	 
	 
	 private void setUpCanvas() {
		/*
		 * Load the image
		 */
		    final Image img = new Image("crestoJpg.jpg");
		    pageEditor.setPageImageElement(img);

	 }
	/*
	 * WordWidgetPresenter methods
	 */

	@Override
	public void onWordRightClicked(int index,
			ContextMenuHandler contextMenuHandler, ContextMenuEvent event) {
			System.out.println("on Word RIGHT clicked: "+ index + " " + wordWidgets.get(index).getWordText());
			// do at first whats to be done if one clicks left
			onWordClicked(index);
			// set the content of the dialogBox
			updateDialogView(index);
			dialogView.asDialogBox().show();
	}

    int lastSelectedWidgetIndex = -1;
	@Override
	public void onWordClicked(int index) {
		// only do something if the current clicked word is unequal to last on clicked
		if (lastSelectedWidgetIndex != index) {
			WordWidget selectedWidget = wordWidgets.get(index);
			// this is for the initial state where no last selected index has been set
			// thus just unselect if there is a last selected index set
			if (lastSelectedWidgetIndex != -1) {
				WordWidget lastSelectedWidget = wordWidgets.get(lastSelectedWidgetIndex);
				lastSelectedWidget.setSelected(false);
			}
			// applies some css selection bg color if true
			selectedWidget.setSelected(true);
			
			lastSelectedWidgetIndex = index;
			/*
			 *  if the dialogView is opened while someone clicks on a new 
			 *  word, update te dialog box with the new selected word
			 */
//			if (dialogView.asDialogBox().isShowing())
				resetEditCoordinates();
				updateDialogView(index);
			// draws a selection frame on top of the page image in the canvas
			drawWordAt(index);
		}
		else {
			System.out.println("left clicked on the same word again");
		}
	}
	
	private void updateDialogView(int index) {
		// set the text for the subtitle of the dialog view
		ModifiableWithParent currentSelectedWord = words.get(index);
		String wordText = wordWidgets.get(index).getWordText();
		dialogView.setSelectedWordText(wordText);		
		// check first if any pos modification have been added to this word
		if (currentSelectedWord.getLastModificationOf(Types.POS_MOD) != null) {
			ModificationString lastPosMod = (ModificationString) currentSelectedWord.getLastModificationOf(Types.POS_MOD);
			String posTag = lastPosMod.getPayload();
			
		int itemCount = dialogView.getPosListBox().getItemCount();
		/*
		 *  iterate over the drop box elements and compare them with the 
		 *  last pos tag modification done on the selected word. If one is found
		 *  it will be selected in the drop down menu and displayed in the view right hand 
		 *  next to the subtitle with text of the selected word
		 */
		for (int i = 0; i < itemCount; i++) {
			if (posTag.equals(dialogView.getPosListBox().getItemText(i))) {
				dialogView.setSelectedWordPosText("[" + posTag + "]");
				dialogView.getPosListBox().setSelectedIndex(i);
				// if one found skip the rest
				return;
				}
			}
		}
	}

	public void drawWordAt(int index) {
		ModifiableWithParent currentSelectedWord = words.get(index);
		ModificationRectangle rectMod = (ModificationRectangle) currentSelectedWord.getLastModificationOf(Types.RECTANGLE_MOD);
		int x = rectMod.getX();
		int y = rectMod.getY();
		int w = rectMod.getWidth();
		int h = rectMod.getHeight();
		currentX = x;
		currentY =  y;
		currentWidth =  w;
		currentHeight = h;
		if (isDoneLoadingPageImage) {
			// call the view and clear its canvas
			pageEditor.clearCanvas();
			// make the view draw the page again
			pageEditor.drawPageImage();
			// draw the rectangle around the word
			pageEditor.drawWord(x, y, w, h, CSS_COLOR_WORD_SELECTION_FRAME);
			System.out.println("on Word LEFT clicked: "+ index + " " + wordWidgets.get(index).getWordText());
			
		}
	}

	@Override
	public void onCanvasMouseDown( MouseDownEvent event) {
		if (STATE.equals(EditingStates.EDIT_COORDINATES)) {
		System.out.println("Canvas onMouseDown!");	
		int x = event.getX();
		int y = event.getY();
		boolean isX = (x > currentX) && (x < (currentX + currentWidth)); 
		boolean isY = (y > currentY) && (y < (currentY + currentHeight)); 
		System.out.println("is in rect? isX: " + isX + " isY: " + isY);
		System.out.println("currents: " + currentX + " currentY: " + currentY + "currWidth: " + currentWidth + " currH: " + currentHeight);
		isInRectClicked = isX && isY;
		// (x > (currentX-GRAB_TOLERANCE) && x <= currentX) means 10 pixel extra space in ordner to get a better grip
		isLeftEdgeClicked = ((x > (currentX - GRAB_TOLERANCE ) && x <= currentX)  && (y >= currentY && y <= currentY + currentHeight ));
		
		System.out.println("onMouseDown @ x: " + x + " y: " + y + "is in rectClicked? " + isInRectClicked + "\n" 
								+ "isLeftEdgeClicked? " + isLeftEdgeClicked);
		
		isRightEdgeClicked = x < (currentX + currentWidth + GRAB_TOLERANCE) && x >= (currentX + currentWidth) && (y >= currentY && y <= currentY + currentHeight );
		System.out.println("is right edge clicked? " + isRightEdgeClicked);
		
		isTopEdgeClicked = ( y > (currentY - GRAB_TOLERANCE) && y <= currentY) && (x >= currentX && x <= currentX + currentWidth);
		System.out.println("is top edge clicked? " + isTopEdgeClicked);
		
		isBottomEdgeClicked = y < (currentY + currentHeight + GRAB_TOLERANCE) && y >= (currentY + currentHeight) && (x >= currentX && x <= currentX + currentWidth);
		System.out.println("is bottom edge clicked? " + isBottomEdgeClicked);
		
		isUpperLeftClicked = ( y > (currentY - GRAB_TOLERANCE) && y <= currentY) && (x >= currentX - GRAB_TOLERANCE && x <= currentX);
		System.out.println("is isUpperLeftClicked? " + isUpperLeftClicked);
		if (isUpperLeftClicked) {isLeftEdgeClicked = true; isTopEdgeClicked = true;}
		
		isUpperRightClicked = ( y > (currentY - GRAB_TOLERANCE) && y <= currentY) && x >= (currentX + currentWidth) && x < (currentX + currentWidth + GRAB_TOLERANCE);
		System.out.println("is isUpperRightClicked? " + isUpperRightClicked);
		if (isUpperRightClicked) {isRightEdgeClicked = true; isTopEdgeClicked = true;}
		
		isLowerLeftClicked = y < (currentY + currentHeight + GRAB_TOLERANCE) && y >= (currentY + currentHeight) &&  (x >= currentX - GRAB_TOLERANCE && x <= currentX);
		System.out.println("is isLowerLeftClicked? " + isLowerLeftClicked);
		if (isLowerLeftClicked) {isLeftEdgeClicked = true; isBottomEdgeClicked = true;}
		
		isLowerRightClicked = y < (currentY + currentHeight + GRAB_TOLERANCE) && y >= (currentY + currentHeight) && x >= (currentX + currentWidth) && x < (currentX + currentWidth + GRAB_TOLERANCE);
		System.out.println("is isLowerRightClicked? " + isLowerRightClicked);
		if (isLowerRightClicked) {isRightEdgeClicked = true; isBottomEdgeClicked = true;}
		// if the user did't clicked on the word or on the drah handles he draws a fresh rect for the word
		if (!(isInRectClicked || isLeftEdgeClicked
				|| isRightEdgeClicked || isTopEdgeClicked
				|| isBottomEdgeClicked || isUpperLeftClicked
				|| isUpperRightClicked || isLowerLeftClicked
				|| isLowerRightClicked)) {
				isClickedToDrawANewRect = true;
				currentX = x;
				currentY = y;
			}
		}
	}
	// needed for free rect drawing
	@Override
	public void onCanvasMouseMove(MouseMoveEvent event) {
		if (STATE.equals(EditingStates.EDIT_COORDINATES)) {
    		int x = event.getX();
    		int y = event.getY();
    		
    		int calcX = 0;
    		int calcY = 0;
    		int calcW = 0;
    		int calcH = 0;

    		
    		if (isInRectClicked) {
    			double diffX = (currentWidth * 0.5); 
    			double diffY = (currentHeight * 0.5); 
    			if (diffX < 0) diffX *= -1;
    			if (diffY < 0) diffY *= -1;
    			int newX = (int) (x - diffX);
    			int newY = (int) (y - diffY);
    			currentX = newX;
    			currentY = newY;
    			calcX = newX;
    			calcY = newY;
    			calcW = currentWidth;
    			calcH = currentHeight;
    			System.out.println("in rect! : x:" + newX + "y: " + newY  );
//    			drawCurrentRect(newX , newY, currentWidth, currentHeight);
    		} 
//    		else
    		 if (isLeftEdgeClicked) {
    			int newX = x;
    			int newWidth = currentWidth - (x - currentX);
    			if (newWidth <= MIN_SIZE_OF_RECT) {
    				newWidth = MIN_SIZE_OF_RECT;
    				newX = currentX;
    			}
    			calcX = newX;
    			calcY = currentY;
    			calcW = newWidth;
    			calcH = currentHeight;
    			System.out.println("moving left edge. new Rect: x: " + newX + " y: " + y + "width: " + newWidth + "height: " + currentHeight);
    			currentX = newX;
    			currentWidth = newWidth;
    		} 
//    		 else
    		 if (isRightEdgeClicked) {
    			 int distToRightEdge = x -  (currentX + currentWidth);
    			 int newWidth = currentWidth + distToRightEdge;
    			 System.out.println("Dist To Right edge: " + distToRightEdge);
    			 System.out.println("newWidth: " + newWidth);
    			 if (newWidth <= MIN_SIZE_OF_RECT) {
    				 newWidth = MIN_SIZE_OF_RECT;
    			 }
        			calcX = currentX;
        			calcY = currentY;
        			calcW = newWidth;
        			calcH = currentHeight;
    			 currentWidth = newWidth;
    		 } 
//    		 else
    		 if (isTopEdgeClicked) {
    			 int newY = y;
    			 int newHeight = currentHeight - ( y - currentY); 
    			 if (newHeight <= MIN_SIZE_OF_RECT){
    				 newHeight = MIN_SIZE_OF_RECT;
    				 newY = currentY;
    			 }
        			calcX = currentX;
        			calcY = newY;
        			calcW = currentWidth;
        			calcH = newHeight;
    			 currentY = newY;
    			 currentHeight = newHeight;
    		 } 
//    		 else 
    		 if (isBottomEdgeClicked) {
    			 	int DistToBottomEdge = y - (currentY + currentHeight);
    			 	int newHeight = currentHeight + DistToBottomEdge; 
    			 	if (newHeight <= MIN_SIZE_OF_RECT){
    			 		newHeight = MIN_SIZE_OF_RECT;
    			 	}
        			calcX = currentX;
        			calcY = currentY;
        			calcW = currentWidth;
        			calcH = newHeight;
    				currentHeight = newHeight;
    		 } 
    		 if (isClickedToDrawANewRect) {
  				calcW = x - currentX;
  				calcH = y - currentY;
//  				if (calcW <= MIN_SIZE_OF_RECT)
//  					calcW = MIN_SIZE_OF_RECT;
//  				if (calcH <= MIN_SIZE_OF_RECT)
//  					calcH = MIN_SIZE_OF_RECT;
    			currentWidth = calcW;
    			currentHeight = calcH;
     			calcX = currentX;
     			calcY = currentY;

    		 }
    		
			if (isInRectClicked || isLeftEdgeClicked
					|| isRightEdgeClicked || isTopEdgeClicked
					|| isBottomEdgeClicked || isUpperLeftClicked
					|| isUpperRightClicked || isLowerLeftClicked
					|| isLowerRightClicked || isClickedToDrawANewRect) {
				
				pageEditor.clearCanvas();
				pageEditor.drawPageImage();
				pageEditor.drawResizableRect(calcX, calcY, calcW, calcH);
			} 
			
    		System.out.println("MouseMove @ x: " + x + " y: " + y);
    		} // end if isEditCoordinatsMode..
		
	}

	@Override
	public void onCanvasMouseUp(MouseUpEvent event) {
		int x = event.getX();
		int y = event.getY();
		isInRectClicked = false;
		isLeftEdgeClicked = false;
		isRightEdgeClicked = false;
		isTopEdgeClicked = false;
		isBottomEdgeClicked = false;
		isUpperLeftClicked = false;
		isUpperRightClicked = false;
		isLowerLeftClicked = false;
		isLowerRightClicked = false;
		isClickedToDrawANewRect = false;
//		System.out.println("MouseUp@ x: " + x + " y: " + y);
	}


	@Override
	public void onNextPageClicked() {
		if (pageNum + 1 < totalPageNum) {
			pageNum++;
			History.newItem(Tokens.EDIT_PAGE+"&"+0+"&"+pageNum);
		}
	}

	@Override
	public void onPreviousPageClicked() {
		if (pageNum - 1 >= 0) {
			pageNum--;
			History.newItem(Tokens.EDIT_PAGE+"&"+0+"&"+pageNum);
		}
	}
	
	/*
	 * Call backs from the page editor view
	 */
	
	@Override
	public void setIsDoneLoadingPageImage(boolean doneLoadingPageImage) {
		this.isDoneLoadingPageImage = doneLoadingPageImage;
	}

	/*
	 * Call backs from the right click dialog
	 */
	
	@Override
	public void onEditCoordinatesClicked() {
		if (!STATE.equals(EditingStates.EDIT_COORDINATES)) {
			STATE = EditingStates.EDIT_COORDINATES;
			pageEditor.drawResizableRect(currentX, currentY, currentWidth, currentHeight);
			DebugHelper.print(this.getClass(), "inEditCoordinatesClicked", true);
			dialogView.getEditCoordinatesBtn().setEnabled(false);
			dialogView.getSaveCoordinatesBtn().setEnabled(true);
		}
	}


	@Override
	public void onSaveCoordinatesClicked() {
		resetEditCoordinates();
		updateSelectedCoordinates();
	}
	
	private  void updateSelectedCoordinates() {
		UsableGwtRectangle rect = new UsableGwtRectangleImpl(currentX,currentY,currentWidth,currentHeight);
		ModificationRectangle rectMod = new ModificationRectangleImpl(rect, new Date(), -1, Types.RECTANGLE_MOD);
		words.get(lastSelectedWidgetIndex).appendModification(rectMod);
		
	}

	
	@Override
	public void onSaveFormClicked(String text) {
		text = text.trim();
		ModificationString formMod = new ModificationStringImpl(text, new Date(), -1, Types.TEXT_MOD);
		words.get(lastSelectedWidgetIndex).appendModification(formMod);
		wordWidgets.get(lastSelectedWidgetIndex).setWordText(text);
		dialogView.setSelectedWordText(text);
		dialogView.setNewformTextBoxText("");
		dialogView.getSaveFormBtn().setEnabled(false);
	}


	@Override
	public void onSomethingTypedIntoFormInputBox(String formBoxText) {
		// only allow saving if the input is not empy and the content is different
		if (!(formBoxText.trim().isEmpty()) && !(formBoxText.trim().equals(wordWidgets.get(lastSelectedWidgetIndex).getWordText())) ) {
			dialogView.getSaveFormBtn().setEnabled(true);
		} 
		else
			dialogView.getSaveFormBtn().setEnabled(false);
	}


	@Override
	public void onSavePosClicked(String itemText) {
		ModificationString posMod = new ModificationStringImpl(itemText, new Date(), -1, Types.POS_MOD);
		words.get(lastSelectedWidgetIndex).appendModification(posMod);
		dialogView.setSelectedWordPosText(itemText);
	}


	@Override
	public void onCancelClicked() {
		STATE = EditingStates.NONE;
		dialogView.asDialogBox().hide();
		drawWordAt(lastSelectedWidgetIndex);
		
	}
	
	
	@Override
	public void onLeftArrowKey() {
		if (lastSelectedWidgetIndex - 1 >= 0) {
			resetEditCoordinates();
			onWordClicked(lastSelectedWidgetIndex - 1);
		} 
	}

	@Override
	public void onRightArrowKey() {
		if (lastSelectedWidgetIndex + 1 < wordWidgets.size()) {
			onWordClicked(lastSelectedWidgetIndex + 1);
			resetEditCoordinates();
		} 
	}
	
	/**
	 * resets the state of the editor and od the buttons regarding editiing the coordinates
	 */
	private void resetEditCoordinates() {
		if (STATE.equals(EditingStates.EDIT_COORDINATES)) {
			STATE = EditingStates.NONE;
			dialogView.getEditCoordinatesBtn().setEnabled(true);
			dialogView.getSaveCoordinatesBtn().setEnabled(false);
		}
	}
	
}
