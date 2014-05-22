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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.spinfo.arc.editor.client.mvp.Tokens;
import de.spinfo.arc.editor.client.mvp.presenter.PageEditorPresenter;
import de.spinfo.arc.editor.client.mvp.ui.impl.WordWidgetImpl;
import de.spinfo.arc.editor.client.mvp.views.PageEditorView;
import de.spinfo.arc.editor.shared.model3.modifiable.ModifiableWithParent;
import de.spinfo.arc.editor.shared.model3.modification.ModificationRectangle;
import de.spinfo.arc.editor.shared.model3.modification.ModificationString;
import de.spinfo.arc.editor.shared.model3.modification.impl.ModificationRectangleImpl;
import de.spinfo.arc.editor.shared.model3.modification.payload.UsableGwtRectangle;
import de.spinfo.arc.editor.shared.model3.modification.payload.impl.UsableGwtRectangleImpl;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;

public class WordsContainerImpl extends Composite {

	private static WordsContainerImplUiBinder uiBinder = GWT
			.create(WordsContainerImplUiBinder.class);

	interface WordsContainerImplUiBinder extends
			UiBinder<Widget, WordsContainerImpl> {
	}
//	    Create the new popup.
	final MyPopup popup = new MyPopup(new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			onEditCoordinates();
			
		}
	});
	
	int editCoorCnt = 0;
	private void onEditCoordinates() {
		if (lastSelectedWord != null) {
//			drawWord(lastSelectedWord);
			updateSelectedCoordinates();
			editCoorCnt++;
			if (editCoorCnt % 2 == 0) {
				dialogBox.setEditCoordinatesButtonText("edit coordinates");
				editCoorCnt = 0;
				isEditCoordinatsMode = false;
			}
			else {
				isEditCoordinatsMode = true;
				dialogBox.setEditCoordinatesButtonText("save coordinates");
				
				
			}
		}
		drawWord(lastSelectedWord);
	}
	
	final MyDialogBox dialogBox = new MyDialogBox(new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			dialogBox.hide();
			dialogBox.setEditCoordinatesButtonText("edit coordinates");
			isEditCoordinatsMode = false;
			isWordRightClicked = false;
			editCoorCnt = 0;
			if (lastSelectedWord != null)
				drawWord(lastSelectedWord);
			
		}
	} , new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			onEditCoordinates();
			
		}
	});
	
	private int pageNum;

	private List<ModifiableWithParent> words = new LinkedList<>();
	
	public WordsContainerImpl(int pageNum, List<ModifiableWithParent> words) {
		initWidget(uiBinder.createAndBindUi(this));
		this.pageNum = pageNum;
		this.words = words;
		
		pageNumLabel.setText("page no. " + pageNum);
		
		canvas = Canvas.createIfSupported();
		context2d = canvas.getContext2d();
		
		canvasToDrawOn = Canvas.createIfSupported();
		context2dToDraw = canvas.getContext2d();
		
		setupimage();
		
		
		
	}
	final Canvas canvas;
	final Context2d context2d;

	final Canvas canvasToDrawOn;
	final Context2d context2dToDraw;
    // how much pixel next to the edge is tolerance
    final int GRAB_TOLERANCE = 20;
    // the minimal width and height a Rect can have if resized
    final int MIN_SIZE_OF_RECT = 5;
//	final int width;
//	final int height;
	ImageElement pageImage = null;
	private void setupimage() {
	    
	    pageImageCanvas.add(canvas);
	    Image img = new Image("crestoJpg.jpg");
	    final ImageElement face = ImageElement.as(img.getElement());
	    img.addLoadHandler(new LoadHandler() {

	        @Override
	        public void onLoad(LoadEvent event) {
	        	canvas.setCoordinateSpaceHeight(face.getHeight());
	        	canvas.setCoordinateSpaceWidth(face.getWidth());
	        	
	        	pageImage = face;
	            context2d.drawImage(pageImage, 0, 0, face.getWidth(), face.getHeight());
                System.out.println("WIDTH in GWT: " + pageImage.getWidth());
                System.out.println("HEIGHT in GWT: " + pageImage.getHeight());

//	        	context2d.clearRect(0, 0, 100, 100);
//	            context2d.beginPath();
//	            
//	            context2d.rect(0, 0, 20, 20);
//	            context2d.closePath();
                KeyUpHandler enterHandler = new KeyUpHandler() {
					
					@Override
					public void onKeyUp(KeyUpEvent event) {
//						if (event.getNativeKeyCode() == KeyUpEvent.)
						if (lastSelectedWord != null ) {
							System.out.println("keyup");
							scaleX = Double.parseDouble(xScaleInput.getText());
							scaleY = Double.parseDouble(yScaleInput.getText());
							scaleWidth = Double.parseDouble(widthScaleInput.getText());
							scaleHeight = Double.parseDouble(heightScaleInput.getText());
							drawWord(lastSelectedWord);
						}
						
					}
				};
                xScaleInput.setText(scaleX+"");
                xScaleInput.addKeyUpHandler(enterHandler);
                yScaleInput.setText(scaleY+"");
                yScaleInput.addKeyUpHandler(enterHandler);
                widthScaleInput.setText(scaleWidth+"");
                widthScaleInput.addKeyUpHandler(enterHandler);
                heightScaleInput.setText(scaleHeight+"");
                heightScaleInput.addKeyUpHandler(enterHandler);
                

                
	            canvas.addMouseDownHandler(new MouseDownHandler() {
					

					@Override
					public void onMouseDown(MouseDownEvent event) {
						if (isEditCoordinatsMode) {
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
						}
					}
				});
	            
	            canvas.addMouseMoveHandler(new MouseMoveHandler() {
	            	@Override
	            	public void onMouseMove(MouseMoveEvent event) {
	            		if (isEditCoordinatsMode) {
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
//	            			drawCurrentRect(newX , newY, currentWidth, currentHeight);
	            		} 
//	            		else
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
//	            			drawCurrentRect(newX , currentY, newWidth, currentHeight);
	            			System.out.println("moving left edge. new Rect: x: " + newX + " y: " + y + "width: " + newWidth + "height: " + currentHeight);
	            			currentX = newX;
	            			currentWidth = newWidth;
	            		} 
//	            		 else
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
//	            			 drawCurrentRect(currentX , currentY, newWidth, currentHeight);
//	            				System.out.println("moving right edge. new Rect: x: " + x + " y: " + y + "width: " + newWidth + "height: " + currentHeight);
	            			 currentWidth = newWidth;
	            		 } 
//	            		 else
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
//	            			 drawCurrentRect(currentX , newY, currentWidth, newHeight);
//	            				System.out.println("moving right edge. new Rect: x: " + x + " y: " + y + "width: " + newWidth + "height: " + currentHeight);
	            			 currentY = newY;
	            			 currentHeight = newHeight;
	            		 } 
//	            		 else 
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
//	            				drawCurrentRect(currentX , currentY, currentWidth, newHeight);
//	            				System.out.println("moving right edge. new Rect: x: " + x + " y: " + y + "width: " + newWidth + "height: " + currentHeight);
	            				currentHeight = newHeight;
	            		 } 

	            		
						if (isInRectClicked || isLeftEdgeClicked
								|| isRightEdgeClicked || isTopEdgeClicked
								|| isBottomEdgeClicked || isUpperLeftClicked
								|| isUpperRightClicked || isLowerLeftClicked
								|| isLowerRightClicked)
									drawCurrentRect(calcX, calcY, calcW, calcH);

	            		
	            		System.out.println("MouseMove @ x: " + x + " y: " + y);
	            		} // end if isEditCoordinatsMode..
	            	}
	            });
	            
	            canvas.addMouseUpHandler(new MouseUpHandler() {
					@Override
					public void onMouseUp(MouseUpEvent event) {
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
						System.out.println("MouseUp@ x: " + x + " y: " + y);
					}
				});
	        }
	    });
	    img.setVisible(false);
	    pageImageCanvas.add(img);
		
	}
	
	 boolean isEditCoordinatsMode = false;
	
	 boolean isInRectClicked = false;
	 boolean isLeftEdgeClicked = false;
	 boolean isRightEdgeClicked = false;
	 boolean isTopEdgeClicked = false;
	 boolean isBottomEdgeClicked = false;
	 
	 boolean isUpperLeftClicked = false;
	 boolean isUpperRightClicked = false;
	 boolean isLowerLeftClicked = false;
	 boolean isLowerRightClicked = false;
	 
	 int currentX = 0;
	 int currentY = 0;
	 int currentWidth = 0;
	 int currentHeight= 0;
	
	double scaleX = 2.79;
	double scaleY = 2.5;
	double scaleWidth = 12;
	double scaleHeight = 20;
	
	@UiField
	TextBox xScaleInput;
	@UiField
	TextBox yScaleInput;
	
	@UiField
	TextBox widthScaleInput;
	
	@UiField 
	TextBox heightScaleInput;
	
	@UiField
	Button setScaleBtn;

	private  ModifiableWithParent lastSelectedWord = null;
	
	@UiHandler("setScaleBtn")
	public void onSetXClicked(ClickEvent e) {
		scaleX = Double.parseDouble(xScaleInput.getText());
		scaleY = Double.parseDouble(yScaleInput.getText());
		scaleWidth = Double.parseDouble(widthScaleInput.getText());
		scaleHeight = Double.parseDouble(heightScaleInput.getText());
		System.out.println("setting scales:\n x: " + scaleX + " y: " + scaleY + "width: "+ scaleWidth + "height: " + scaleHeight);
		if (lastSelectedWord != null )
			drawWord(lastSelectedWord);
	}
	
	
	@UiField
	ScrollPanel imageScrollPanel;
	
	public void onWordClicked(WordWidgetImpl wordWidgetImpl, ClickEvent e) {
		ModifiableWithParent word = words.get(wordWidgetImpl.getIndex());
		this.lastSelectedWord = word;
		if (!isWordRightClicked) {
			System.out
					.println("left click on idx:" + wordWidgetImpl.getIndex());
			int wordY = ((ModificationRectangle) word.getLastModificationOf(Types.RECTANGLE_MOD)).getY();
			System.out.println("current scroll Pos: "
					+ imageScrollPanel.getVerticalScrollPosition());
			if (wordY - 50 >= 0)
				imageScrollPanel.setVerticalScrollPosition(wordY - 50);
			else
				imageScrollPanel.setVerticalScrollPosition(0);
			drawWord(word);
		}
	}

	
	private void drawCurrentRect (int x, int y, int width, int height) {
		context2dToDraw.clearRect(0 , 0 , canvasToDrawOn.getCoordinateSpaceWidth() , canvasToDrawOn.getCoordinateSpaceHeight() );
		if (pageImage != null)
			context2d.drawImage(pageImage, 0, 0, pageImage.getWidth(), pageImage.getHeight());
		if (isEditCoordinatsMode) {
		context2dToDraw.setLineWidth(1);
		/*
		 * draw the big exclusion rects around selected word
		 */
		context2dToDraw.setFillStyle(CssColor.make("rgba(" + 255 + ", " + 255 + "," + 255 + ", " + 0.8+ ")"));
		// grey left rect
		context2dToDraw.fillRect(0, 0, x, pageImage.getHeight());
		// top of word
		context2dToDraw.fillRect(x, 0, width, y);
		// right from word
		context2dToDraw.fillRect(x + width, 0 , pageImage.getWidth() - (x + width), pageImage.getHeight());
		// bottom of word
		context2dToDraw.fillRect(x, y + height, width, pageImage.getHeight() - ( y + height));
		
		
		/*
		 *  draw the drag bars
		 */
		context2dToDraw.setFillStyle(CssColor.make("rgba(" + 0 + ", " + 0 + "," + 255 + ", " + 0.1+ ")"));
		context2dToDraw.setStrokeStyle(CssColor.make("rgba(" + 0 + ", " + 0 + "," + 255 + ", " + 0.5+ ")"));
		// left drag bar
//		context2dToDraw.fillRect(x - GRAB_TOLERANCE, y, GRAB_TOLERANCE, height);
		context2dToDraw.strokeRect(x - GRAB_TOLERANCE, y, GRAB_TOLERANCE, height);
		// right drag bar
//		context2dToDraw.fillRect(x + width, y, GRAB_TOLERANCE, height);
		context2dToDraw.strokeRect(x + width, y, GRAB_TOLERANCE, height);
		// top drag bar
//		context2dToDraw.fillRect(x, y - GRAB_TOLERANCE, width, GRAB_TOLERANCE);
		context2dToDraw.strokeRect(x, y - GRAB_TOLERANCE, width, GRAB_TOLERANCE);
		// bottom drag bar
//		context2dToDraw.fillRect(x, y + height, width, GRAB_TOLERANCE);
		context2dToDraw.strokeRect(x, y + height, width, GRAB_TOLERANCE);
		
		/* Draw diagonal scale handle drag bars (upper left, upper right, lower left, lower right  */
		context2dToDraw.setFillStyle(CssColor.make("rgba(" + 0 + ", " + 0 + "," + 0 + ", " + 0.2+ ")"));
		// upper left
		context2dToDraw.fillRect(x - GRAB_TOLERANCE, y - GRAB_TOLERANCE, GRAB_TOLERANCE, GRAB_TOLERANCE);
		// upper right
		context2dToDraw.fillRect(x + width, y - GRAB_TOLERANCE, GRAB_TOLERANCE, GRAB_TOLERANCE);		
		// lower left
		context2dToDraw.fillRect(x - GRAB_TOLERANCE, y + height, GRAB_TOLERANCE, GRAB_TOLERANCE);
		// lower right
		context2dToDraw.fillRect(x + width, y + height, GRAB_TOLERANCE, GRAB_TOLERANCE);
		
		/*
		 *  draw the drag triangles
		 */
		context2dToDraw.setFillStyle(CssColor.make("rgba(" + 0 + ", " + 0 + "," + 255 + ", " + 0.3+ ")"));
		
		// left drag triangle
		context2dToDraw.beginPath();
		// left in the middle of the selected word y-wise
		context2dToDraw.moveTo(x - GRAB_TOLERANCE, y + (height * 0.5));
		// upper left corner of word
		context2dToDraw.lineTo(x , y );
		// lower left
		context2dToDraw.lineTo(x , y + height );
		context2dToDraw.fill();
		context2dToDraw.closePath();
		
		// right drag triangle
		context2dToDraw.beginPath();
		// right in the middle of the selected word y-wise
		context2dToDraw.moveTo(x + width + GRAB_TOLERANCE, y + (height * 0.5));
		// upper right corner of word
		context2dToDraw.lineTo(x + width, y );
		// lower left
		context2dToDraw.lineTo(x + width , y + height );
		context2dToDraw.fill();
		context2dToDraw.closePath();
		
		// top drag triangle
		context2dToDraw.beginPath();
		// top in the middle of the selected word x-wise
		context2dToDraw.moveTo(x + (width * 0.5), y - GRAB_TOLERANCE);
		// upper left corner of word
		context2dToDraw.lineTo(x , y );
		// upper right
		context2dToDraw.lineTo(x + width , y );
		context2dToDraw.fill();
		context2dToDraw.closePath();
		
		// bottom drag triangle
		context2dToDraw.beginPath();
		// bottom in the middle of the selected word x-wise
		context2dToDraw.moveTo(x + (width * 0.5), y + height + GRAB_TOLERANCE);
		// lower left corner of word
		context2dToDraw.lineTo(x , y + height );
		// lower right
		context2dToDraw.lineTo(x + width ,  y + height );
		context2dToDraw.fill();
		context2dToDraw.closePath();
		}	
		/*
		 * Draw the word enclosing Rect on top of everything
		 */
		context2dToDraw.setLineWidth(2);
		context2dToDraw.setStrokeStyle(CssColor.make(0,0,255));
		context2dToDraw.strokeRect(x, y, width, height);
		
//		
	}
	
	private  void updateSelectedCoordinates() {
		UsableGwtRectangle rect = new UsableGwtRectangleImpl(currentX,currentY,currentWidth,currentHeight);
		ModificationRectangle rectMod = new ModificationRectangleImpl(rect, new Date(), -1, Types.RECTANGLE_MOD);
		lastSelectedWord.appendModification(rectMod);
		
	}


	private void drawWord(ModifiableWithParent word ) {
		if (pageImage != null) {
		ModificationRectangle rect = (ModificationRectangle) word.getLastModificationOf(Types.RECTANGLE_MOD);
//		context2dToDraw.clearRect(0 , 0 , canvasToDrawOn.getCoordinateSpaceWidth() , canvasToDrawOn.getCoordinateSpaceHeight() );
//		double x = (double) (rect.getX() * scaleX);
//		double y = (double) (rect.getY() * scaleY);
//		double w = rect.getWidth() * scaleWidth;
//		double h = rect.getHeight() * scaleHeight;
		double x = (double) (rect.getX());
		double y = (double) (rect.getY() );
		double w = rect.getWidth();
		double h = rect.getHeight();
		
		currentX = (int) x;
		currentY = (int) y;
		currentWidth = (int) w;
		currentHeight = (int) h;
		
		System.out.println("setting RECT:\n x: " + x + " y: " + x + " width: "+ w + " height: " + h);
			context2d.drawImage(pageImage, 0, 0, pageImage.getWidth(), pageImage.getHeight());
//		context2dToDraw.fillRect(0, 0, pageImage.getWidth(), pageImage.getHeight());
		context2dToDraw.strokeRect(x, y, w, h);
		drawCurrentRect(currentX, currentY, currentWidth, currentHeight);
//		drawWordBasedOnAverageFontSize();
		}
	}
	
	private void drawWordBasedOnAverageFontSize() {
		if (lastSelectedWord != null) {
			
			ModificationRectangle rect = (ModificationRectangle) lastSelectedWord.getLastModificationOf(Types.RECTANGLE_MOD);
			ModificationString str = (ModificationString) lastSelectedWord.getLastModificationOf(Types.TEXT_MOD);
			
			context2dToDraw.clearRect(0 , 0 , canvasToDrawOn.getCoordinateSpaceWidth() , canvasToDrawOn.getCoordinateSpaceHeight() );
			double x = (double) (rect.getX() * scaleX);
			double y = (double) (rect.getY() * scaleY);
			
			double w= (str.getText().length() * scaleWidth);
			double h = scaleHeight;
			
			currentX = (int) x;
			currentY = (int) y;
			currentWidth = (int) w;
			currentHeight = (int) h;
//			double w = rect.getWidth() * scaleWidth;
//			double h = rect.getHeight() * scaleHeight;
			System.out.println("setting RECT:\n x: " + x + " y: " + x + " width: "+ w + " height: " + h);
			drawCurrentRect(currentX, currentY, currentWidth, currentHeight);
//			if (pageImage != null)
//				context2d.drawImage(pageImage, 0, 0, pageImage.getWidth(), pageImage.getHeight());
////		context2dToDraw.fillRect(0, 0, pageImage.getWidth(), pageImage.getHeight());
//			context2dToDraw.strokeRect(x, y, w, h);
		}
	}


	@UiField
	FlowPanel wordsContainer;
	@UiField
	VerticalPanel pageImageCanvas;
	
	@UiField
	Button previousPageBtn;
	@UiField
	Button nextPageBtn;
	@UiField
	Label pageNumLabel;

	@UiHandler("nextPageBtn")
	public void onNextPageClicked(ClickEvent e) {
		pageNum++;
		History.newItem(Tokens.EDIT_PAGE+"&"+0+"&"+pageNum);
	}
	@UiHandler("previousPageBtn")
	public void onPreviousPageClicked(ClickEvent e) {
		pageNum--;
	History.newItem(Tokens.EDIT_PAGE+"&"+0+"&"+pageNum);
	}
	
	public void addWord(final WordWidgetImpl word){
		
		word.addDomHandler(new ContextMenuHandler() {
			public void onContextMenu(ContextMenuEvent event) {
				System.out.println("On contextEvent");
//				event.getNativeEvent().stopPropagation();
//				event.getNativeEvent().preventDefault();
				 
				onWordRightClicked(word, event);
				}
			}
		, ContextMenuEvent.getType());
		wordsContainer.add(word.asWidget());
	}
	
	private static class MyPopup extends PopupPanel {
		public final Label wordIndexLabel = new Label();
		
		public MyPopup(ClickHandler handler) {
			// PopupPanel's constructor takes 'auto-hide' as its boolean parameter.
			// If this is set, the panel closes itself automatically when the user
			// clicks outside of it.
			super(false);
			
			VerticalPanel vp = new VerticalPanel();
			
			Button editPosBtn = new Button("Edit Part Of Speech");
			Button editFormBtn = new Button("Edit Form");
			Button editLanguageBtn = new Button("Edit Language");
			Button editCoordinatesBtn = new Button("Edit Coordinates");
			vp.add(wordIndexLabel);
			vp.add(editPosBtn);
			vp.add(editFormBtn);
			vp.add(editLanguageBtn);
			vp.add(editCoordinatesBtn);
			editCoordinatesBtn.addClickHandler(handler);
			
			// PopupPanel is a SimplePanel, so you have to set it's widget property to
			// whatever you want its contents to be.
			setWidget(vp);
		}
	}
	  private static class MyDialogBox extends DialogBox {
		  public final Label wordIndexLabel = new Label();
		  
		  private final Button editCoordinatesBtn = new Button("edit coordinates");
		  
		    public MyDialogBox(ClickHandler closeHandler, ClickHandler editCoordinatesHandler) {
		      // PopupPanel's constructor takes 'auto-hide' as its boolean parameter.
		      // If this is set, the panel closes itself automatically when the user
		      // clicks outside of it.
		      super(false, false);
		      setText("Edit word");
		      VerticalPanel vp = new VerticalPanel();
		      this.setAutoHideOnHistoryEventsEnabled(true);
//		      this.setGlassEnabled(true);
		      Button editPosBtn = new Button("Edit Part Of Speech");
		      Button editFormBtn = new Button("Edit Form");
		      Button editLanguageBtn = new Button("Edit Language");
		      
//		      Button editCoordinatesBtn = new Button(editCoordinatesBtnText);
		      editCoordinatesBtn.addClickHandler(editCoordinatesHandler);
		      
		      Button closeBtn = new Button("close");
		      closeBtn.addClickHandler(closeHandler);		      
		     
		      
		      vp.add(wordIndexLabel);
		      vp.add(editPosBtn);
		      vp.add(editFormBtn);
		      vp.add(editLanguageBtn);
		      vp.add(editCoordinatesBtn);
		      vp.add(closeBtn);

		      // PopupPanel is a SimplePanel, so you have to set it's widget property to
		      // whatever you want its contents to be.
		      setWidget(vp);
		      
		      
		    }
			public void setEditCoordinatesButtonText(String text) {
				editCoordinatesBtn.setText(text);
				
			}
		  }
	  
	
	boolean isWordRightClicked = false;

	public void onWordRightClicked(WordWidgetImpl wordWidgetImpl,
			ContextMenuEvent event) {
		
		if (!isWordRightClicked) {
//		popup.wordIndexLabel.setText("SELECTED: <" + wordWidgetImpl.getText() + "> index: [" + wordWidgetImpl.getIndex() + "]");
//		popup.setPopupPosition(event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY());
//		popup.show();
		dialogBox.wordIndexLabel.setText("SELECTED: <" + wordWidgetImpl.getText() + "> index: [" + wordWidgetImpl.getIndex() + "]");
		dialogBox.setPopupPosition(event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY());
		dialogBox.show();
		isWordRightClicked = true;
		onWordClicked(wordWidgetImpl, null);
		}
//		drawWord(words.get(wordWidgetImpl.getIndex()));
		event.getNativeEvent().stopPropagation();
		event.getNativeEvent().preventDefault();
	}


}
