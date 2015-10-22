package de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

import de.uni_koeln.spinfo.arc.dto.annotatable.WorkingUnitDto;
import de.uni_koeln.spinfo.arc.dto.annotatable.impl.WordDtoImpl;
import de.uni_koeln.spinfo.arc.dto.annotation.AnnotationDto.AnnotationTypes;
import de.uni_koeln.spinfo.arc.dto.annotation.ChapterRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.LanguageRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.PosAnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.RectangleAnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.impl.PosAnnotationDtoImpl;
import de.uni_koeln.spinfo.arc.dto.annotation.impl.RectangleAnnotationDtoImpl;
import de.uni_koeln.spinfo.arc.editor.client.mvp.ClientSession;
import de.uni_koeln.spinfo.arc.editor.client.mvp.Editor;
import de.uni_koeln.spinfo.arc.editor.client.mvp.Editor.PageStates;
import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.EditRangeDialogPresenter;
import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.EditorSupervisor;
import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.PageEditorDialogPresenter;
import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.PageEditorPresenter;
import de.uni_koeln.spinfo.arc.editor.client.mvp.ui.WordWidget;
import de.uni_koeln.spinfo.arc.editor.client.mvp.ui.WordWidgetPresenter;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.PageEditorDialogView;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.PageEditorView;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.RangeWidget;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.impl.EditRangeDialogImpl;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.impl.PageEditorDialogViewImpl;
import de.uni_koeln.spinfo.arc.editor.shared.until.DebugHelper;
//import de.spinfo.arc.editor.shared.model3.modification.payload.UsableGwtRectangle;
//import de.spinfo.arc.editor.shared.model3.modification.payload.impl.UsableGwtRectangleImpl;

public class PageEditorPresenterImpl implements PageEditorPresenter,
		WordWidgetPresenter, PageEditorDialogPresenter,
		EditRangeDialogPresenter {

	private ClientSession clientSession;

	private PageEditorView pageEditorView;
	private WorkingUnitDto workingUnit;
	/** the dialog view for editing on a word basis */
	private PageEditorDialogView pageEditorDialogView;
	/** the new dialog view for editing range start AND end */
	private EditRangeDialogImpl editRangeDialog;

	private List<WordWidget> wordWidgets = new LinkedList<>();
	private List<WordDtoImpl> words = new LinkedList<>();
	private int pageNum = 0;
	private int totalPageNum = 0;

	/**
	 * The actual beginning index of a page in respect of the underlying word
	 * index Thus the index in respect of the whole word index of all words in
	 * the DB
	 */
	private int currentPageOffSet = 0;

	private final CssColor CSS_COLOR_WORD_SELECTION_FRAME = CssColor
			.make("rgba(" + 0 + ", " + 0 + "," + 255 + ", " + 1 + ")");
	private Editor.PageStates state = Editor.PageStates.EDIT_WORD;

	private EditorSupervisor supervisor;

	@Override
	public void initByState(Editor.PageStates state, int pageNum,
			AnnotationTypes annotationType, List<WordDtoImpl> words,
			String imageUrl, RangeWidget rangeWidget) {
		// if (this.pageNum != pageNum || isLoadingFirstTime){
		// isLoadingFirstTime = false;
		this.pageNum = pageNum;
		this.state = state;
		this.currentPageOffSet = (int) workingUnit.getPages().get(pageNum)
				.getStart();
		pageEditorView.setPageNum(pageNum);
		this.words = words;
		// System.out.println(" \n --- first Word --- \n" +
		// words.get(0).getLastModificationOf(Types.TEXT_MOD).toString());
		// System.out.println(" \n --- last Word --- \n" +
		// words.get(words.size()-1).getLastModificationOf(Types.TEXT_MOD).toString());
		// System.out.println(" \n --- total Num of Words in this page --- \n" +
		// words.size());
		pageEditorView.clearWordWidgets();
		this.wordWidgets = clientSession.getClientFactory()
				.getBlancWordWidgets(words.size());
		int wordIdx = 0;
		for (Iterator<WordDtoImpl> iterator = words.iterator(); iterator
				.hasNext();) {
			WordDtoImpl word = iterator.next();
			WordWidget ww = wordWidgets.get(wordIdx);
			String wordText = word.getLastFormAnnotation().getForm();
			// System.out.println("setting Text: " + wordText);
			ww.setIndex(wordIdx);
			ww.setWordText(wordText);
			ww.setSelected(false);
			ww.setPresenter(this);
			pageEditorView.addWord(ww);
			wordIdx++;
		}

		setUpCanvas(imageUrl);

		setState(state, pageNum, annotationType, rangeWidget);

	}

	public void setWordHighlightedAndSelected(int index) {
		// set this to make an initial selection to be drawn
		onWordClicked(index);
		// set this flag in order to draw rect around image if image loaded
		// callback has returned
		isWaitingForImageBeingLoadedInOrderToSetSelection = true;
	}

	public PageEditorPresenterImpl(WorkingUnitDto workingUnit,
			ClientSession clientSession, EditorSupervisor supervisor) {
		this.workingUnit = workingUnit;
		this.clientSession = clientSession;
		this.pageEditorView = clientSession.getClientFactory()
				.getPageEditorView();

		this.totalPageNum = workingUnit.getPages().size() - 1;
		this.currentPageOffSet = (int) workingUnit.getPages().get(pageNum)
				.getStart();
		this.supervisor = supervisor;

		bind();
		// getting and setting up the dialogView
		pageEditorDialogView = clientSession.getClientFactory()
				.getPageEditorDialogView();
		pageEditorDialogView.setPresenter(this);
		pageEditorDialogView.asDialogBox().hide();

		editRangeDialog = new EditRangeDialogImpl(this);
		editRangeDialog.hide();

		pageEditorView.setAmountTotalPages(totalPageNum);
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(pageEditorView.asWidget());
	}

	@Override
	public void bind() {
		pageEditorView.setPresenter(this);
	}

	/*
	 * drawing fields
	 */
	ImageElement pageImage = null;
	int imgWidth = 0;
	int imgHeight = 0;

	// boolean isEditCoordinatsMode = false;

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
	int currentHeight = 0;

	// private ModifiableWithParent lastSelectedWord = null;
	private boolean isDoneLoadingPageImage = false;

	// private static final String PREFIX = "images/PPN345572629_0004-0008.png";
	private static final String PREFIX = "images/";
	private static final String SUFFIX = ".png";

	private void setUpCanvas(String imageUrl) {
		/*
		 * Load the image
		 */
		String formatedUrl[] = imageUrl.split("\\.");

		// System.out.println(PREFIX + formatedUrl[0] + SUFFIX);
		final Image img = new Image(PREFIX + formatedUrl[0] + SUFFIX);
		pageEditorView.setPageImageElement(img);

	}

	/*
	 * WordWidgetPresenter methods
	 */

	@Override
	public void onWordRightClicked(int index,
			ContextMenuHandler contextMenuHandler, ContextMenuEvent event) {
		switch (state) {
		case EDIT_WORD:
			// System.out.println("on Word RIGHT clicked: "+ index + " " +
			// wordWidgets.get(index).getWordText());
			// do at first whats to be done if one clicks left
			onWordClicked(index);
			// set the content of the dialogBox
			updateDialogView(index);
			pageEditorDialogView.asDialogBox().show();
			// System.err.println("is dialogBox visible: " +
			// pageEditorDialogView.asDialogBox().isShowing());
			break;
		case EDIT_COORDINATES:
			break;
		case SET_RANGE_START_AND_END:
			break;
		default:
			break;
		}
	}

	int lastSelectedWidgetIndex = -1;

	@Override
	public void onWordClicked(int index) {
		// only do something if the current clicked word is unequal to last on
		// clicked
		if (lastSelectedWidgetIndex != index) {
			// System.out.println("clicked on word with index: " + index);
			// System.err.println("current state: " + state.toString());
			// System.err.println("currentPageOffSet " + currentPageOffSet);
			// do at first whats to be done if one clicks left
			// if (currentPageOffSet >= words.size())
			// index = currentPageOffSet - index;
			WordWidget selectedWidget = wordWidgets.get(index);
			// this is for the initial state where no last selected index has
			// been set
			// thus just unselect if there is a last selected index set
			if (lastSelectedWidgetIndex != -1) {
				WordWidget lastSelectedWidget = wordWidgets
						.get(lastSelectedWidgetIndex);
				lastSelectedWidget.setSelected(false);
			}
			// applies some css selection bg color if true
			selectedWidget.setSelected(true);

			lastSelectedWidgetIndex = index;
			/*
			 * if the dialogView is opened while someone clicks on a new word,
			 * update te dialog box with the new selected word
			 */
			// if (dialogView.asDialogBox().isShowing())
			resetEditCoordinates();
			updateDialogView(index);
			// draws a selection frame on top of the page image in the canvas
			drawWordAt(index);
		}
		// else {
		// System.out.println("left clicked on the same word again");
		// }
	}

	private void updateDialogView(int index) {
		// System.out.println("current state: " + state.toString());
		// set the text for the subtitle of the dialog view
		WordDtoImpl currentSelectedWord = words.get(index);
		String wordText = wordWidgets.get(index).getWordText();
		/*
		 * switching on the current state. there is the state where the user can
		 * edit word tags and where the user can select the range starts
		 */
		switch (state) {
		/* if the editor is in state of editing specific word tags: */
		case EDIT_WORD:
			pageEditorDialogView.setSelectedWordText(wordText);

			// check first if any pos modification have been added to this word
			String posTag = "NOT_TAGGED";
			if (currentSelectedWord.getLastPosAnnotation() != null) {
				PosAnnotationDto lastPosMod = currentSelectedWord
						.getLastPosAnnotation();
				posTag = lastPosMod.getPos().toString();
			}

			if (!currentSelectedWord.getTaggerPosOptions().isEmpty())
				pageEditorDialogView.setUpWithPosOptions(currentSelectedWord
						.getTaggerPosOptions());
			else
				pageEditorDialogView.setUpWithConstants();

			int itemCount = pageEditorDialogView.getPosListBox().getItemCount();
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			sb.append(posTag);
			sb.append("]");
			pageEditorDialogView.setSelectedWordPosText(sb.toString());

			/*
			 * iterate over the drop box elements and compare them with the last
			 * pos tag modification done on the selected word. If one is found
			 * it will be selected in the drop down menu and displayed in the
			 * view right hand next to the subtitle with text of the selected
			 * word
			 */
			for (int i = 0; i < itemCount; i++) {
				if (posTag.equals(pageEditorDialogView.getPosListBox()
						.getItemText(i))) {
					// sb.append("[");
					// sb.append(posTag);
					// sb.append("]");
					// dialogView.setSelectedWordPosText( sb.toString() );
					pageEditorDialogView.getPosListBox().setSelectedIndex(i);
					// if one found skip the rest
					return;
				}
			}

			pageEditorDialogView.setHasChanged(false);
			// }
			// if no pos tag has been found
			// else {
			// dialogView.setSelectedWordPosText("[not tagged]");
			// }
			break;
		/* if the editor is in state of editing specific range starts: */
		case SET_RANGE_START_AND_END:
			editRangeDialog.setSelectedWordText(wordText);
			break;
		case EDIT_EXISTING_RANGE:
			editRangeDialog.setSelectedWordText(wordText);
			break;
		case EDIT_COORDINATES:
			break;
		default:
			break;
		}

	}

	public void drawWordAt(int index) {
		DebugHelper.print(getClass(), "drawWordAt " + index, true);
		if (index <= words.size() - 1 && index != -1) {
			WordDtoImpl currentSelectedWord = words.get(index);
			RectangleAnnotationDto rectMod = currentSelectedWord
					.getLastRectangleAnnotation();
			int x = rectMod.getX();
			int y = rectMod.getY();
			int w = rectMod.getWidth();
			int h = rectMod.getHeight();
			currentX = x;
			currentY = y;
			currentWidth = w;
			currentHeight = h;
			DebugHelper.print(getClass(), "isDoneLoadingPageImage "
					+ isDoneLoadingPageImage, true);
			if (isDoneLoadingPageImage) {
				// call the view and clear its canvas
				pageEditorView.clearCanvas();
				// make the view draw the page again
				pageEditorView.drawPageImage();
				// draw the rectangle around the word
				pageEditorView.drawWord(x, y, w, h,
						CSS_COLOR_WORD_SELECTION_FRAME);
				// System.out.println("on Word LEFT clicked: "+ index + " " +
				// wordWidgets.get(index).getWordText());

			}
		}
	}

	@Override
	public void onCanvasMouseDown(MouseDownEvent event) {
		if (state.equals(Editor.PageStates.EDIT_COORDINATES)) {
			// System.out.println("Canvas onMouseDown!");
			int x = event.getX();
			int y = event.getY();
			boolean isX = (x > currentX) && (x < (currentX + currentWidth));
			boolean isY = (y > currentY) && (y < (currentY + currentHeight));
			isInRectClicked = isX && isY;
			// (x > (currentX-GRAB_TOLERANCE) && x <= currentX) means 10 pixel
			// extra space in ordner to get a better grip
			isLeftEdgeClicked = ((x > (currentX - GRAB_TOLERANCE) && x <= currentX) && (y >= currentY && y <= currentY
					+ currentHeight));

			isRightEdgeClicked = x < (currentX + currentWidth + GRAB_TOLERANCE)
					&& x >= (currentX + currentWidth)
					&& (y >= currentY && y <= currentY + currentHeight);

			isTopEdgeClicked = (y > (currentY - GRAB_TOLERANCE) && y <= currentY)
					&& (x >= currentX && x <= currentX + currentWidth);

			isBottomEdgeClicked = y < (currentY + currentHeight + GRAB_TOLERANCE)
					&& y >= (currentY + currentHeight)
					&& (x >= currentX && x <= currentX + currentWidth);

			isUpperLeftClicked = (y > (currentY - GRAB_TOLERANCE) && y <= currentY)
					&& (x >= currentX - GRAB_TOLERANCE && x <= currentX);
			if (isUpperLeftClicked) {
				isLeftEdgeClicked = true;
				isTopEdgeClicked = true;
			}

			isUpperRightClicked = (y > (currentY - GRAB_TOLERANCE) && y <= currentY)
					&& x >= (currentX + currentWidth)
					&& x < (currentX + currentWidth + GRAB_TOLERANCE);
			if (isUpperRightClicked) {
				isRightEdgeClicked = true;
				isTopEdgeClicked = true;
			}

			isLowerLeftClicked = y < (currentY + currentHeight + GRAB_TOLERANCE)
					&& y >= (currentY + currentHeight)
					&& (x >= currentX - GRAB_TOLERANCE && x <= currentX);
			if (isLowerLeftClicked) {
				isLeftEdgeClicked = true;
				isBottomEdgeClicked = true;
			}

			isLowerRightClicked = y < (currentY + currentHeight + GRAB_TOLERANCE)
					&& y >= (currentY + currentHeight)
					&& x >= (currentX + currentWidth)
					&& x < (currentX + currentWidth + GRAB_TOLERANCE);
			if (isLowerRightClicked) {
				isRightEdgeClicked = true;
				isBottomEdgeClicked = true;
			}
			if (!(isInRectClicked || isLeftEdgeClicked || isRightEdgeClicked
					|| isTopEdgeClicked || isBottomEdgeClicked
					|| isUpperLeftClicked || isUpperRightClicked
					|| isLowerLeftClicked || isLowerRightClicked)) {
				isClickedToDrawANewRect = true;
				currentX = x;
				currentY = y;
			}
		} else {
			int x = event.getX();
			int y = event.getY();
			selectWordWidgetByCoordinates(x, y);
		}
	}

	/**
	 * Selects a WordWidget in the upper pageEditor scrollpanel and sets the
	 * scrollposition accordingly All word rectangles are checked if the
	 * coordinates are containing in them. If one is found it gets selected and
	 * set to be the current selected word.
	 * 
	 * @param x
	 *            the mousedown click x coordinate on the page image
	 * @param y
	 *            the mousedown click y coordinate on the page image
	 */
	private void selectWordWidgetByCoordinates(int x, int y) {
		int index = 0;
		for (Iterator<WordDtoImpl> iterator = words.iterator(); iterator
				.hasNext();) {
			WordDtoImpl word = iterator.next();
			RectangleAnnotationDto rectMod = word.getLastRectangleAnnotation();
			if (rectMod.contains(x, y)) {
				onWordClicked(index);
				int absTop = wordWidgets.get(lastSelectedWidgetIndex)
						.asWidget().getAbsoluteTop();
				// int absLeft =
				// wordWidgets.get(lastSelectedWidgetIndex).asWidget().getAbsoluteLeft();
				int scrollAbsTop = pageEditorView.getWordsScrollPanel()
						.getAbsoluteTop();
				// int scrollAbsLeft =
				// pageEditor.getWordsScrollPanel().getAbsoluteLeft();
				int posTop = (absTop - scrollAbsTop + 50) >= 0 ? (absTop
						- scrollAbsTop + 50)
						- scrollAbsTop : 0;
				// int posLeft = absLeft - scrollAbsLeft >= 0 ? absLeft -
				// scrollAbsLeft : 0;
				pageEditorView.getWordsScrollPanel().setVerticalScrollPosition(
						posTop);
				// pageEditor.getWordsScrollPanel().setHorizontalScrollPosition(posLeft);
				break;
			}
			index++;
		}
	}

	@Override
	public void onCanvasMouseMove(MouseMoveEvent event) {
		if (state.equals(Editor.PageStates.EDIT_COORDINATES)) {
			int x = event.getX();
			int y = event.getY();

			int calcX = 0;
			int calcY = 0;
			int calcW = 0;
			int calcH = 0;

			if (isInRectClicked) {
				double diffX = (currentWidth * 0.5);
				double diffY = (currentHeight * 0.5);
				if (diffX < 0)
					diffX *= -1;
				if (diffY < 0)
					diffY *= -1;
				int newX = (int) (x - diffX);
				int newY = (int) (y - diffY);
				currentX = newX;
				currentY = newY;
				calcX = newX;
				calcY = newY;
				calcW = currentWidth;
				calcH = currentHeight;
				// System.out.println("in rect! : x:" + newX + "y: " + newY );
				// drawCurrentRect(newX , newY, currentWidth, currentHeight);
			}
			// else
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
				// System.out.println("moving left edge. new Rect: x: " + newX +
				// " y: " + y + "width: " + newWidth + "height: " +
				// currentHeight);
				currentX = newX;
				currentWidth = newWidth;
			}
			// else
			if (isRightEdgeClicked) {
				int distToRightEdge = x - (currentX + currentWidth);
				int newWidth = currentWidth + distToRightEdge;
				// System.out.println("Dist To Right edge: " + distToRightEdge);
				// System.out.println("newWidth: " + newWidth);
				if (newWidth <= MIN_SIZE_OF_RECT) {
					newWidth = MIN_SIZE_OF_RECT;
				}
				calcX = currentX;
				calcY = currentY;
				calcW = newWidth;
				calcH = currentHeight;
				currentWidth = newWidth;
			}
			// else
			if (isTopEdgeClicked) {
				int newY = y;
				int newHeight = currentHeight - (y - currentY);
				if (newHeight <= MIN_SIZE_OF_RECT) {
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
			// else
			if (isBottomEdgeClicked) {
				int DistToBottomEdge = y - (currentY + currentHeight);
				int newHeight = currentHeight + DistToBottomEdge;
				if (newHeight <= MIN_SIZE_OF_RECT) {
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
				// if (calcW <= MIN_SIZE_OF_RECT)
				// calcW = MIN_SIZE_OF_RECT;
				// if (calcH <= MIN_SIZE_OF_RECT)
				// calcH = MIN_SIZE_OF_RECT;
				currentWidth = calcW;
				currentHeight = calcH;
				calcX = currentX;
				calcY = currentY;

			}

			if (isInRectClicked || isLeftEdgeClicked || isRightEdgeClicked
					|| isTopEdgeClicked || isBottomEdgeClicked
					|| isUpperLeftClicked || isUpperRightClicked
					|| isLowerLeftClicked || isLowerRightClicked
					|| isClickedToDrawANewRect) {

				pageEditorView.clearCanvas();
				pageEditorView.drawPageImage();
				pageEditorView.drawResizableRect(calcX, calcY, calcW, calcH);
			}

			// System.out.println("MouseMove @ x: " + x + " y: " + y);
		} // end if isEditCoordinatsMode..

	}

	@Override
	public void onCanvasMouseUp(MouseUpEvent event) {
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
	}

	@Override
	public void onNextPageClicked() {
		if (pageNum + 1 < totalPageNum) {
			pageNum++;
			supervisor.onNewPageDemanded(pageNum);
			// History.newItem(Tokens.EDIT_PAGE+"&"+0+"&"+pageNum);
		}
	}

	@Override
	public void onPreviousPageClicked() {
		if (pageNum - 1 >= 0) {
			pageNum--;
			supervisor.onNewPageDemanded(pageNum);
			// History.newItem(Tokens.EDIT_PAGE+"&"+0+"&"+pageNum);
		}
	}

	/*
	 * Call backs from the page editor view
	 */
	@Override
	public void setIsDoneLoadingPageImage(boolean doneLoadingPageImage) {
		this.isDoneLoadingPageImage = doneLoadingPageImage;
		if (isWaitingForImageBeingLoadedInOrderToSetSelection) {
			drawWordAt(lastSelectedWidgetIndex);
			isWaitingForImageBeingLoadedInOrderToSetSelection = false;
		}
	}

	/*
	 * Call backs from the right click dialog
	 */

	@Override
	public void onEditCoordinatesClicked() {
		if (!state.equals(Editor.PageStates.EDIT_COORDINATES)) {
			state = Editor.PageStates.EDIT_COORDINATES;
			pageEditorView.drawResizableRect(currentX, currentY, currentWidth,
					currentHeight);
			DebugHelper
					.print(this.getClass(), "inEditCoordinatesClicked", true);
			pageEditorDialogView.getEditCoordinatesBtn().setEnabled(false);
			pageEditorDialogView.getSaveCoordinatesBtn().setEnabled(true);
		}
	}

	private int getAbsoluteSelectedWordIdx() {
		return currentPageOffSet + lastSelectedWidgetIndex;
	}

	@Override
	public void onSaveCoordinatesClicked() {
		resetEditCoordinates();
		state = Editor.PageStates.EDIT_WORD;
		updateSelectedCoordinates();
		drawWordAt(lastSelectedWidgetIndex);
	}

	private void updateSelectedCoordinates() {
		// UsableGwtRectangle rect = new
		// UsableGwtRectangleImpl(currentX,currentY,currentWidth,currentHeight);
		// @Todo this is for updating
		// ModificationRectangle rectMod = new ModificationRectangleImpl(rect,
		// date, clientSession.getUserId(), Types.RECTANGLE_MOD);
		// words.get(lastSelectedWidgetIndex).appendModification(rectMod);
		// WorkingUnitFactoryImpl.INSTANCE.createAndAppendRectangleModification(rect,
		// words.get(lastSelectedWidgetIndex), date, clientSession.getUserId());
		RectangleAnnotationDto rectAnno = new RectangleAnnotationDtoImpl(
				new Date(), 0, clientSession.getUserId(), currentX, currentY,
				currentWidth, currentHeight);
		words.get(lastSelectedWidgetIndex).setAnnotationAsType(
				AnnotationTypes.RECTANGLE, rectAnno);
		supervisor.updateCoordinates(currentX, currentY, currentWidth,
				currentHeight, getAbsoluteSelectedWordIdx());
		// CrudHandler.saveWorkingUnit(clientSession, workingUnit);
	}

	//

	@Override
	public void onSaveFormClicked(String text) {
		text = text.trim();
		wordWidgets.get(lastSelectedWidgetIndex).setWordText(text);
		pageEditorDialogView.setSelectedWordText(text);
		pageEditorDialogView.setNewformTextBoxText("");
		pageEditorDialogView.getSaveFormBtn().setEnabled(false);
		DebugHelper.print(getClass(), "onSaveFormClicked", true);
		supervisor.updateForm(text, getAbsoluteSelectedWordIdx());
	}

	@Override
	public void onSomethingTypedIntoFormInputBox(String formBoxText) {
		// only allow saving if the input is not empy and the content is
		// different
		if (!(formBoxText.trim().isEmpty())
				&& !(formBoxText.trim().equals(wordWidgets.get(
						lastSelectedWidgetIndex).getWordText()))) {
			pageEditorDialogView.getSaveFormBtn().setEnabled(true);
		} else
			pageEditorDialogView.getSaveFormBtn().setEnabled(false);
	}

	@Override
	public void onSavePosClicked(String itemText) {
		if (pageEditorDialogView.hasChanged()) {
			pageEditorDialogView.setSelectedWordPosText(itemText);
			PosAnnotationDto.PosTags tagToSave = PosAnnotationDto.PosTags.NOT_TAGGED;
			for (PosAnnotationDto.PosTags pt : PosAnnotationDto.PosTags
					.values()) {
				if (pt.toString().equals(itemText))
					tagToSave = pt;
			}
			PosAnnotationDto posAnno = new PosAnnotationDtoImpl(new Date(), -1,
					clientSession.getUserId(), tagToSave);
			words.get(lastSelectedWidgetIndex).setAnnotationAsType(
					AnnotationTypes.POS, posAnno);
			DebugHelper.print(getClass(), "onSavePosClicked " + itemText, true);
			supervisor.updatePos(itemText, getAbsoluteSelectedWordIdx());

		}
		// else System.out.println("HAS NOT CHANGED - NOTHING WILL bE SAVED!");
	}

	@Override
	public void onCancelEditWordClicked() {
		pageEditorDialogView.asDialogBox().hide();
		drawWordAt(lastSelectedWidgetIndex);
	}

	@Override
	public void onLeftArrowKey() {
		if (lastSelectedWidgetIndex - 1 >= 0) {
			saveCurrentPosSelection();
			resetEditCoordinates();
			onWordClicked(lastSelectedWidgetIndex - 1);
		}
	}

	@Override
	public void onRightArrowKey() {
		if (lastSelectedWidgetIndex + 1 < wordWidgets.size()) {
			saveCurrentPosSelection();
			onWordClicked(lastSelectedWidgetIndex + 1);
			resetEditCoordinates();
		}
	}

	private void saveCurrentPosSelection() {
		/* when moving to */
		ListBox lb = pageEditorDialogView.getPosListBox();
		String itemText = lb.getItemText(lb.getSelectedIndex());
		/* Do nothing if the selection is the delimiter */
		// System.err.println("saveCurrentPosSelection");
		// System.err.println("lb.getSelectedIndex() " + lb.getSelectedIndex());
		// System.err.println("lb.getItemText(lb.getSelectedIndex()) " +
		// lb.getItemText(lb.getSelectedIndex()));
		if (itemText.equals(PageEditorDialogViewImpl.POS_DELIM))
			return;

		onSavePosClicked(itemText);

	}

	/**
	 * resets the state of the editor and od the buttons regarding editing the
	 * coordinates
	 */
	private void resetEditCoordinates() {
		pageEditorDialogView.getEditCoordinatesBtn().setEnabled(true);
		pageEditorDialogView.getSaveCoordinatesBtn().setEnabled(false);
	}

	RangeWidget currentRangeWidget = null;

	public void setState(Editor.PageStates state, int pageNum,
			AnnotationTypes type, RangeWidget rangeWidget) {
		// System.out.println("CURRENT STATE: " + state.toString());

		currentRangeWidget = rangeWidget;
		this.state = state;
		this.pageNum = pageNum;
		if (state.equals(Editor.PageStates.EDIT_WORD)) {
			pageEditorView.setModeInfoText("word editing mode");
			initForEditingWords();
			setWordHighlightedAndSelected(0);
		} else if (state.equals(Editor.PageStates.SET_RANGE_START_AND_END)) {
			initForEditingRanges(type);
			pageEditorView.setModeInfoText("range editing mode");
			setWordHighlightedAndSelected(0);
		} else if (state.equals(Editor.PageStates.EDIT_EXISTING_RANGE)) {
			initForEditingRanges(type);
			pageEditorView.setModeInfoText("editing existing range mode");
			setWordHighlightedAndSelected(Integer.parseInt(currentRangeWidget
					.getFromIndex()) - currentPageOffSet);
		}
	}

	private void initForEditingWords() {
		pageEditorView.getNextPageBtn().setEnabled(true);
		pageEditorView.getPreviousPageBtn().setEnabled(true);
		pageEditorDialogView.asDialogBox().hide();
		unselectAll();
		editRangeDialog.hide();
	}

	private void initForEditingRanges(AnnotationTypes type) {
		pageEditorView.getNextPageBtn().setEnabled(false);
		pageEditorView.getPreviousPageBtn().setEnabled(false);
		pageEditorDialogView.asDialogBox().hide();
		pageEditorDialogView.setSelectedWordText("");
		editRangeDialog.setTypeOfRange(type);
		TextBox tb = editRangeDialog.getCategoryInputTextBox();
		if (type.equals(AnnotationTypes.CHAPTER_RANGE)) {
			tb.setText(currentRangeWidget.getRangeTitle());
		} else
			tb.setText("");
		editRangeDialog.init();
		unselectAll();
		editRangeDialog.show();
	}

	@Override
	public void unselectAll() {
		// this.wordWidgets.get(0);
		for (Iterator<WordWidget> iterator = wordWidgets.iterator(); iterator
				.hasNext();) {
			WordWidget w = iterator.next();
			w.setSelected(false);
		}
		lastSelectedWidgetIndex = -1;
	}

	/*
	 * Methods called from the NEW
	 */
	boolean isWaitingForImageBeingLoadedInOrderToSetSelection = false;

	@Override
	public void initForDisplayingRange(int pageNum, long selectedWordIdx,
			List<WordDtoImpl> words, boolean isStart, String url) {
		this.state = PageStates.EDIT_WORD;
		this.pageNum = pageNum;
		this.currentPageOffSet = (int) workingUnit.getPages().get(pageNum)
				.getStart();
		pageEditorView.setPageNum(pageNum);
		pageEditorView.getNextPageBtn().setEnabled(true);
		pageEditorView.getPreviousPageBtn().setEnabled(true);
		pageEditorView.setModeInfoText("word editing mode");
		pageEditorDialogView.asDialogBox().hide();
		editRangeDialog.hide();
		this.words = words;
		pageEditorView.clearWordWidgets();
		this.wordWidgets = clientSession.getClientFactory()
				.getBlancWordWidgets(words.size());
		int wordIdx = 0;
		for (Iterator<WordDtoImpl> iterator = words.iterator(); iterator
				.hasNext();) {
			WordDtoImpl word = iterator.next();
			WordWidget ww = wordWidgets.get(wordIdx);
			String wordText = word.getLastFormAnnotation().getForm();
			ww.setIndex(wordIdx);
			ww.setWordText(wordText);
			ww.setSelected(false);
			ww.setPresenter(this);
			pageEditorView.addWord(ww);
			wordIdx++;
		}
		setUpCanvas(url);
		unselectAll();
		// onWordClicked assigns the current clicked index back to
		// lastSelectedWidgetIndex
		onWordClicked((int) selectedWordIdx - currentPageOffSet);
		isWaitingForImageBeingLoadedInOrderToSetSelection = true;
		// if (isStart) {
		// int startAtIdx = (int) ( selectedWordIdx - currentPageOffSet);
		// for (int i = startAtIdx; i < wordWidgets.size(); i++) {
		// wordWidgets.get(i).setSelected(true);
		// }
		// }
		// else {
		// int limit = (int) ( selectedWordIdx - currentPageOffSet);
		// for (int i = 0; i < limit; i++) {
		// wordWidgets.get(i).setSelected(true);
		// }
		// }

	}

	@Override
	public void initForRangeEndSelection(int pageNum, List<WordDtoImpl> words,
			String imageUrl) {
		this.pageNum = pageNum;
		this.currentPageOffSet = (int) workingUnit.getPages().get(pageNum)
				.getStart();
		pageEditorView.setPageNum(pageNum);
		this.words = words;
		pageEditorView.clearWordWidgets();
		this.wordWidgets = clientSession.getClientFactory()
				.getBlancWordWidgets(words.size());
		int wordIdx = 0;
		for (Iterator<WordDtoImpl> iterator = words.iterator(); iterator
				.hasNext();) {
			WordDtoImpl word = iterator.next();
			WordWidget ww = wordWidgets.get(wordIdx);
			String wordText = word.getLastFormAnnotation().getForm();
			ww.setIndex(wordIdx);
			ww.setWordText(wordText);
			ww.setSelected(false);
			ww.setPresenter(this);
			pageEditorView.addWord(ww);
			wordIdx++;
		}

		setUpCanvas(imageUrl);
		unselectAll();
		if (state.equals(Editor.PageStates.EDIT_EXISTING_RANGE))
			setWordHighlightedAndSelected(Integer.parseInt(currentRangeWidget
					.getToIndex()) - currentPageOffSet);
		editRangeDialog.show();
	}

	@Override
	public void onRangeStartSelected() {
		int selectedIndexInRespectOfTotalWordIndex = currentPageOffSet;
		if (lastSelectedWidgetIndex != -1)
			selectedIndexInRespectOfTotalWordIndex = currentPageOffSet
					+ lastSelectedWidgetIndex;

		supervisor.onRangeStartSelected(selectedIndexInRespectOfTotalWordIndex);
	}

	@Override
	public void onRangeEndSelected() {
		int selectedIndexInRespectOfTotalWordIndex = currentPageOffSet;
		if (lastSelectedWidgetIndex != -1)
			selectedIndexInRespectOfTotalWordIndex = currentPageOffSet
					+ lastSelectedWidgetIndex;

		supervisor.onRangeEndSelected(selectedIndexInRespectOfTotalWordIndex);
	}

	@Override
	public void onSaveRangeClicked() {
		resetEditCoordinates();
		editRangeDialog.hide();
		// String text = editRangeDialog.getCategoryInputTextBox().getText();
		String text = editRangeDialog.getCategoryText();

		DebugHelper.print(
				getClass(),
				"onSaveRangeClicked()  + Editor.PageStates: "
						+ state.toString(), false);
		if (state.equals(Editor.PageStates.EDIT_EXISTING_RANGE)) {

			DebugHelper.print(getClass(),
					"onSaveRangeClicked()  + editRangeDialog.getAnnotationType()"
							+ editRangeDialog.getAnnotationType(), false);
			// currentRangeWidget.setRangeTitle(text);
			if (editRangeDialog.getAnnotationType().equals(
					AnnotationTypes.LANGUAGE_RANGE)) {
				DebugHelper.print(getClass(), "onSaveRangeClicked() "
						+ editRangeDialog.getAnnotationType(), false);

				supervisor
						.onRemoveLanguage((LanguageRangeDto) currentRangeWidget
								.getRangeAnnotation());
			} else if (editRangeDialog.getAnnotationType().equals(
					AnnotationTypes.CHAPTER_RANGE)) {
				DebugHelper.print(getClass(), "onSaveRangeClicked() "
						+ editRangeDialog.getAnnotationType(), false);

				supervisor.onRemoveChapter((ChapterRangeDto) currentRangeWidget
						.getRangeAnnotation());
			}

		}
		// supervisor.onEditRangeAnnotation(currentRangeWidget);

		supervisor
				.onSaveRangeClicked(text, editRangeDialog.getAnnotationType());
		/*
		 * Set back to default editing
		 */
		unselectAll();
		setState(Editor.PageStates.EDIT_WORD, pageNum,
				AnnotationTypes.CHAPTER_RANGE, null);
	}

	@Override
	public void onCancelRangeClicked() {
		editRangeDialog.hide();
		setState(Editor.PageStates.EDIT_WORD, pageNum,
				AnnotationTypes.CHAPTER_RANGE, null);
		resetEditCoordinates();
		supervisor.onCancelRangeClicked();
		unselectAll();
		editRangeDialog.hide();
	}

}
