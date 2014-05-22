package de.spinfo.arc.editor.client.mvp.views.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.spinfo.arc.editor.client.mvp.Tokens;
import de.spinfo.arc.editor.client.mvp.presenter.PageEditorPresenter;
import de.spinfo.arc.editor.client.mvp.ui.WordWidget;
import de.spinfo.arc.editor.client.mvp.ui.impl.WordWidgetImpl;
import de.spinfo.arc.editor.client.mvp.views.PageEditorView;
import de.spinfo.arc.editor.shared.model3.modifiable.ModifiableWithParent;
import de.spinfo.arc.editor.shared.model3.modification.ModificationRectangle;
import de.spinfo.arc.editor.shared.model3.modification.impl.ModificationRectangleImpl;
import de.spinfo.arc.editor.shared.model3.modification.payload.UsableGwtRectangle;
import de.spinfo.arc.editor.shared.model3.modification.payload.impl.UsableGwtRectangleImpl;
import de.spinfo.arc.editor.shared.model3.util.DebugHelper;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;

public class PageEditorViewImpl extends Composite implements PageEditorView  {

	private static PageEditorViewImplUiBinder uiBinder = GWT
			.create(PageEditorViewImplUiBinder.class);

	interface PageEditorViewImplUiBinder extends
			UiBinder<Widget, PageEditorViewImpl> {
	}

	
	
private PageEditorPresenter presenter;

//how much pixel next to the edge is tolerance
final int GRAB_TOLERANCE = 20;
//the minimal width and height a Rect can have if resized
final int MIN_SIZE_OF_RECT = 5;

private ImageElement imageElement;
private int imgWidth;
private int imgHeight;


public PageEditorViewImpl() {
	initWidget(uiBinder.createAndBindUi(this));
	
	initCanvas();
}

public void initCanvas() {
	// set up a keyup handler for left/right arrows in order to move
	// the selection of word to left or right
    KeyUpHandler leftRightKeyHandler = new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (KeyUpEvent.isArrow(event.getNativeKeyCode())) {
					event.getNativeEvent().stopPropagation();
					event.getNativeEvent().preventDefault();
					if (event.getNativeKeyCode() == KeyCodes.KEY_LEFT) {
						presenter.onLeftArrowKey();
					}
					else if (event.getNativeKeyCode() == KeyCodes.KEY_RIGHT) {
						presenter.onRightArrowKey();
					}
				}
				
			}
		};
	allFocusWrapperPanel.addKeyUpHandler(leftRightKeyHandler);
	canvas = Canvas.createIfSupported();
	if (canvas == null) {
		pageImageCanvas.add(new Label("Sorry! Your Browser doesn't support the Canvas-Element to draw on. Plz update your browser in order to make this editor work. thank you!"));
	}
	else {
		pageImageCanvas.add(canvas);
		DebugHelper.print(this.getClass(), "setting the canvas", true);
		context = canvas.getContext2d();
	
	canvas.addMouseDownHandler(new MouseDownHandler() {
		
		@Override
		public void onMouseDown(MouseDownEvent event) {
			presenter.onCanvasMouseDown( event);
		}
	});
    
    canvas.addMouseMoveHandler(new MouseMoveHandler() {
    	@Override
    	public void onMouseMove(MouseMoveEvent event) {
    		presenter.onCanvasMouseMove( event);
    	}
    });
    
    canvas.addMouseUpHandler(new MouseUpHandler() {
		@Override
		public void onMouseUp(MouseUpEvent event) {
			presenter.onCanvasMouseUp( event);
		}
	});
    
    canvas.addKeyUpHandler(leftRightKeyHandler);
	}

}
	
@Override
public HasWidgets getCanvasPanel() {
	return pageImageCanvas;
}


@Override
public void setPageNum(int pageNum) {
	pageNumLabel.setText(pageNum+"");
};

@Override
public void setAmountTotalPages(int totalAmountPages) {
	totalPagesLabel.setText(totalAmountPages+"");
}

private Canvas canvas;
private Context2d context;


@Override
public Canvas getCanvas() {
	return canvas;
}
@Override
public Context2d getContext() {
	return context;
}


@Override
public ImageElement getPageImageElement() {
	return imageElement;
}
@Override
public void setPageImageElement(Image img) {
	
		presenter.setIsDoneLoadingPageImage(false);
		
	  	pageImageCanvas.add(canvas);
//	    Image img = new Image("crestoJpg.jpg");
	    imageElement = ImageElement.as(img.getElement());
	    img.addLoadHandler(new LoadHandler() {

	        @Override
	        public void onLoad(LoadEvent event) {
	           canvas.setCoordinateSpaceHeight(imageElement.getHeight());
	           canvas.setCoordinateSpaceWidth(imageElement.getWidth());
	           context.drawImage(imageElement, 0, 0, imageElement.getWidth(), imageElement.getHeight());
	           imgWidth = imageElement.getWidth();
	           imgHeight = imageElement.getHeight();
	           // inform the presenter 
	          presenter.setIsDoneLoadingPageImage(true);
              System.out.println("WIDTH in GWT: " + imageElement.getWidth());
              System.out.println("HEIGHT in GWT: " + imageElement.getHeight());
	        }
	    });
	    img.setVisible(false);
	    pageImageCanvas.add(img);
	    
}

@Override
public void clearCanvas() {
	System.out.println("clearing canvas!");
	context.clearRect(0 , 0 , canvas.getCoordinateSpaceWidth() , canvas.getCoordinateSpaceHeight() );
}

@Override
public void drawPageImage() {
	if (imageElement != null && canvas != null) {
		context.drawImage(imageElement, 0, 0, imgWidth, imgHeight);
		DebugHelper.print(this.getClass(), "view draws the page image", true);
	}
}

@Override
public void drawResizableRect (int x, int y, int width, int height) {
	context.setLineWidth(1);
	/*
	 * draw the big exclusion rects around selected word
	 */
	context.setFillStyle(CssColor.make("rgba(" + 255 + ", " + 255 + "," + 255 + ", " + 0.8+ ")"));
	// grey left rect
	context.fillRect(0, 0, x, imageElement.getHeight());
	// top of word
	context.fillRect(x, 0, width, y);
	// right from word
	context.fillRect(x + width, 0 , imageElement.getWidth() - (x + width), imageElement.getHeight());
	// bottom of word
	context.fillRect(x, y + height, width, imageElement.getHeight() - ( y + height));
	
	/*
	 *  draw the drag bars
	 */
	context.setFillStyle(CssColor.make("rgba(" + 0 + ", " + 0 + "," + 255 + ", " + 0.1+ ")"));
	context.setStrokeStyle(CssColor.make("rgba(" + 0 + ", " + 0 + "," + 255 + ", " + 0.5+ ")"));
	// left drag bar
//	context2dToDraw.fillRect(x - GRAB_TOLERANCE, y, GRAB_TOLERANCE, height);
	context.strokeRect(x - GRAB_TOLERANCE, y, GRAB_TOLERANCE, height);
	// right drag bar
//	context2dToDraw.fillRect(x + width, y, GRAB_TOLERANCE, height);
	context.strokeRect(x + width, y, GRAB_TOLERANCE, height);
	// top drag bar
//	context2dToDraw.fillRect(x, y - GRAB_TOLERANCE, width, GRAB_TOLERANCE);
	context.strokeRect(x, y - GRAB_TOLERANCE, width, GRAB_TOLERANCE);
	// bottom drag bar
//	context2dToDraw.fillRect(x, y + height, width, GRAB_TOLERANCE);
	context.strokeRect(x, y + height, width, GRAB_TOLERANCE);
	
	/* Draw diagonal scale handle drag bars (upper left, upper right, lower left, lower right  */
	context.setFillStyle(CssColor.make("rgba(" + 0 + ", " + 0 + "," + 0 + ", " + 0.2+ ")"));
	// upper left
	context.fillRect(x - GRAB_TOLERANCE, y - GRAB_TOLERANCE, GRAB_TOLERANCE, GRAB_TOLERANCE);
	// upper right
	context.fillRect(x + width, y - GRAB_TOLERANCE, GRAB_TOLERANCE, GRAB_TOLERANCE);		
	// lower left
	context.fillRect(x - GRAB_TOLERANCE, y + height, GRAB_TOLERANCE, GRAB_TOLERANCE);
	// lower right
	context.fillRect(x + width, y + height, GRAB_TOLERANCE, GRAB_TOLERANCE);
	
	/*
	 *  draw the drag triangles
	 */
	context.setFillStyle(CssColor.make("rgba(" + 0 + ", " + 0 + "," + 255 + ", " + 0.3+ ")"));
	
	// left drag triangle
	context.beginPath();
	// left in the middle of the selected word y-wise
	context.moveTo(x - GRAB_TOLERANCE, y + (height * 0.5));
	// upper left corner of word
	context.lineTo(x , y );
	// lower left
	context.lineTo(x , y + height );
	context.fill();
	context.closePath();
	
	// right drag triangle
	context.beginPath();
	// right in the middle of the selected word y-wise
	context.moveTo(x + width + GRAB_TOLERANCE, y + (height * 0.5));
	// upper right corner of word
	context.lineTo(x + width, y );
	// lower left
	context.lineTo(x + width , y + height );
	context.fill();
	context.closePath();
	
	// top drag triangle
	context.beginPath();
	// top in the middle of the selected word x-wise
	context.moveTo(x + (width * 0.5), y - GRAB_TOLERANCE);
	// upper left corner of word
	context.lineTo(x , y );
	// upper right
	context.lineTo(x + width , y );
	context.fill();
	context.closePath();
	
	// bottom drag triangle
	context.beginPath();
	// bottom in the middle of the selected word x-wise
	context.moveTo(x + (width * 0.5), y + height + GRAB_TOLERANCE);
	// lower left corner of word
	context.lineTo(x , y + height );
	// lower right
	context.lineTo(x + width ,  y + height );
	context.fill();
	context.closePath();

}

@Override
public void drawWord(int x, int y, int w, int h, CssColor wordSelectionFrameColor ) {	
	/*
	 * Draw the word enclosing Rect on top of everything
	 */
	context.setLineWidth(2);
	context.setStrokeStyle(wordSelectionFrameColor);
	context.strokeRect(x, y, w, h);
	if (y - 50 >= 0)
		imageScrollPanel.setVerticalScrollPosition(y - 50);
	else
		imageScrollPanel.setVerticalScrollPosition(0);
}

@UiField
FocusPanel allFocusWrapperPanel;
@UiField
FlowPanel wordsContainer;
@UiField
ScrollPanel imageScrollPanel;
@UiField
VerticalPanel pageImageCanvas;

@UiField
Button previousPageBtn;
@UiField
Button nextPageBtn;
@UiField
Label pageNumLabel;
@UiField
Label totalPagesLabel;

@UiHandler("nextPageBtn")
public void onNextPageClicked(ClickEvent e) {
	presenter.onNextPageClicked();
//	pageNum++;
//	History.newItem(Tokens.EDIT_PAGE+"&"+0+"&"+pageNum);
}
@UiHandler("previousPageBtn")
public void onPreviousPageClicked(ClickEvent e) {
	presenter.onPreviousPageClicked();
//	pageNum--;
//History.newItem(Tokens.EDIT_PAGE+"&"+0+"&"+pageNum);
}

@Override
public HasWidgets getWordWidgetPanel() {
	return wordsContainer;
}

@Override
public void addWord(final WordWidget word){
	wordsContainer.add(word.asWidget());
}

@Override
public void setPresenter(PageEditorPresenter presenter) {
	this.presenter = presenter;
	
}

@Override
public int getPageImageWidth() {
	return imgWidth;
}
@Override
public int getPageImageHeight() {
	return imgHeight;
}


}
