package de.spinfo.arc.editor.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;

import de.spinfo.arc.editor.client.mvp.ArcAppController;
import de.spinfo.arc.editor.client.mvp.views.MainFrameView;
import de.spinfo.arc.editor.client.mvp.views.impl.MainFrameViewImpl;
import de.spinfo.arc.editor.client.mvp.views.impl.PageEditorDialogViewImpl;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ARC_Editor implements EntryPoint {
	
	SimpleLayoutPanel container = new SimpleLayoutPanel();
	
	public void onModuleLoad() {
//		WuCellWithTemplate test = new WuCellWithTemplate();
//		CellWidget<WorkingUnit> testWidget = new CellWidget<WorkingUnit>(test);
//		testWidget.setValue(PseudoDataSource.createData(2, 2, 2).get(0));
//		container.add(testWidget);
		
		
//	    System.out.println(GWT.getModuleBaseURL()+"data/pdf/Signorelli.pdf");
		
		ArcAppController controller = new ArcAppController();
		MainFrameView mainFrame = new MainFrameViewImpl();
		RootLayoutPanel.get().add(mainFrame);
		controller.go(mainFrame.getContentPanel());
	    History.fireCurrentHistoryState();
		
//	    PageEditorDialogViewImpl d = new PageEditorDialogViewImpl();
//		d.show();
	    
//	    final Canvas canvas = Canvas.createIfSupported();
//	    final Context2d context2d = canvas.getContext2d();
//
//	    RootPanel.get().add(canvas);
//	    Image img = new Image("crestoJpg.jpg");
//	    final ImageElement face = ImageElement.as(img.getElement());
//	    img.addLoadHandler(new LoadHandler() {
//
//	        @Override
//	        public void onLoad(LoadEvent event) {
//	        	canvas.setCoordinateSpaceHeight(face.getHeight());
//	        	canvas.setCoordinateSpaceWidth(face.getWidth());
//	            context2d.drawImage(face, 0, 0, face.getWidth(), face.getHeight());
//	        }
//
//
//	    });
//	    img.setVisible(false);
//	    RootLayoutPanel.get().add(img);

	    
	} 
}
