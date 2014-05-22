package de.spinfo.arc.editor.client;

import java.util.ArrayList;
import java.util.List;

//import org.cafesip.gwtcomp.client.ui.PopupMenu;
//import org.cafesip.gwtcomp.client.ui.SuperTree;
//import org.cafesip.gwtcomp.client.ui.SuperTreeItem;
//import org.cafesip.gwtcomp.client.utils.HTMLHelper;




import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

import de.spinfo.arc.editor.client.cactus.Cactus;
import de.spinfo.arc.editor.client.cactus.CactusHtmlHelper;
import de.spinfo.arc.editor.client.cactus.CactusItem;
import de.spinfo.arc.editor.client.cactus.CactusListener;
import de.spinfo.arc.editor.client.cactus.CactusPopupMenu;

public class MyCactus extends Composite {

	private static MyCactusUiBinder uiBinder = GWT
			.create(MyCactusUiBinder.class);

	interface MyCactusUiBinder extends UiBinder<Widget, MyCactus> {
	}

	
	private static final int WORKING_UNIT = 1;
	private static final int CHAPTER = 2;
	private static final int PAGE = 3;
	
	private Cactus tree;
	
	private CactusPopupMenu pagesPopUp;
	private HTML addChapter;
	
	private MyPopup confirmPopup;

	  private static class MyPopup extends PopupPanel {

	    public MyPopup() {
	      // PopupPanel's constructor takes 'auto-hide' as its boolean parameter.
	      // If this is set, the panel closes itself automatically when the user
	      // clicks outside of it.
	      super(true);

	      // PopupPanel is a SimplePanel, so you have to set it's widget property to
	      // whatever you want its contents to be.
	      setWidget(new Label("Here should be a better widget to edit chapter specs"));
	    }
	  }
	
	public MyCactus() {
		initWidget(uiBinder.createAndBindUi(this));
		setUpTree();
	
        initPopupMenu();
		initListener();
		
		confirmPopup = new MyPopup();
		
		
	}
	

	CactusItem wuRoot;
	CactusItem remainingUnsorted;
	@UiField
	SimpleLayoutPanel chapterPanel;
	
	
	private void setUpTree() {
        tree = new Cactus();
        tree.setMultiSelectionEnabled(true);
        tree.setAnimationEnabled(true);
        chapterPanel.setWidget(tree);
        
        wuRoot = new CactusItem("working_unit", WORKING_UNIT);
        tree.addItem(wuRoot);
        
//        CactusItem newChapter = new CactusItem("< create new chapter >", CHAPTER);
//        wuRoot.addItem(newChapter);
        
        remainingUnsorted = new CactusItem("< all pages >", CHAPTER);
        wuRoot.addItem(remainingUnsorted);
        
        for (int i = 0; i < 21; i++) {
//        	TreeUserObject uo = new TreeUserObject();
//        	uo.number = i+"";
//        	uo.state = "unsorted";
        	
        	Label l = new Label("page nr." + i );
        	
        	CactusItem item = new CactusItem(l, PAGE);
        	// set the user object in order
//        	item.setUserObject(uo);
        	remainingUnsorted.addItem(item);
		}
	}

//	List<CactusItem> shiftSelectedFirst = new ArrayList<CactusItem>();
//	CactusItem shiftSelectedLast = null;
	
	private void initListener() {
	     tree.addTreeListener(new CactusListener()
	        {
	            public void onPopup(String level, CactusPopupMenu menu,
	                    TreeItem[] treeItems)
	            {
	            	GWT.log("OPENED POPUP" );
	            	GWT.log("level: " + level );
	            	GWT.log("Selected Items: " + treeItems.length );
	            }

	            public void onSelect(String level, TreeItem[] treeItems)
	            {
	                GWT.log(treeItems.length + " item(s) selected at level "
	                        + level);
	                
	            }

	            public void onCollapse(TreeItem item)
	            {
	                GWT.log("collapsed item: " + item.getText() );
	            }

	            public void onExpand(TreeItem item)
	            {
	            	 GWT.log("expanded item: " + item.getText() );
	            }
	        });
	}

	private void initPopupMenu() {
		pagesPopUp = new CactusPopupMenu();
		
		// adding a Chapter
		addChapter = new HTML(CactusHtmlHelper.imageWithText(
                "gwtcomp-icons/edit_add.png", "Add To New Chapter"));
		addChapter.addClickHandler(new ClickHandler(){

            public void onClick(ClickEvent event)
            {
            	pagesPopUp.hideAll();
            	
//            	if (currentSelectedItems != null) {
            	if (tree.getSelectedItems() != null) {
            		StringBuilder sb = new StringBuilder();
            		
            		for (int i = 0; i < tree.getSelectedItems().length; i++) {
            			sb.append(tree.getSelectedItems()[i].getText());
						sb.append("\n");
					}
            		
//            		if (Window
//            				.confirm("Are you sure you want to add the following listed selected items to a new chapter? \n"
//            						+ sb.toString()));
//            		{
            			int dHeight = (int) (Window.getClientHeight() * 0.45);
            			int dWidth = (int) (Window.getClientWidth() * 0.45);
            			confirmPopup.setPopupPosition(dWidth, dHeight);
            				confirmPopup.show();
            				
            				CactusItem newChapter = new CactusItem("fresh chapter created by you!", CHAPTER);
            				
            				wuRoot.addItem(newChapter);
            				
            				CactusItem lastParent = (CactusItem) tree.getSelectedItems()[0].getParentItem();
            				
            				TreeItem[] selectedItems = tree.getSelectedItems();
            				
            				sortTreeItemsArrayByIdx(selectedItems);
            				
            				for (int j = 0; j < selectedItems.length; j++) {
//            					remainingUnsorted.removeItem(tree.getSelectedItems()[j]);
            					
            					String content = ((Label) selectedItems[j].getWidget()).getText();
//            					TreeUserObject uo = (TreeUserObject) selectedItems[j].getUserObject();
//            					uo.state = "chapter";
            					
//            					TreeUserObject uo = new TreeUserObject();
//            					uo.state = "chapter";
            					CactusItem copy = new CactusItem(new Label(content), PAGE);
//            					copy.setUserObject(uo);
            					newChapter.addItem(copy);
            					
            					
//            					newChapter.addItem(tree.getSelectedItems()[j]);
							}
            				if (lastParent.getChildCount() == 0) lastParent.remove();
            				else if (lastParent.getState()) lastParent.setState(false, true);
//            		}
            		}
            }});
		
		HTML moveToChapter = new HTML(CactusHtmlHelper.imageWithText(
                "gwtcomp-icons/next.png", "Move To Chapter"));
		
		
		pagesPopUp.addMenuItem(addChapter);
		tree.setContextMenu(WORKING_UNIT + "." + CHAPTER + "." + PAGE, pagesPopUp);
	}	
	

	
	/**
	 * Bubble Sorting a tree items array
	 * @param array
	 */
	public static void sortTreeItemsArrayByIdx(TreeItem[] array) {
		   boolean swapped = true;
		    int j = 0;
		    TreeItem tmp;
		    while (swapped) {
		        swapped = false;
		        j++;
		        for (int i = 0; i < array.length - j; i++) {
		        	int currentIdx = array[i].getParentItem().getChildIndex(array[i]);
		        	int nextIdx = array[i + 1].getParentItem().getChildIndex(array[i + 1]);
		            if (currentIdx > nextIdx) {
		                tmp = array[i];
		                array[i] = array[i + 1];
		                array[i + 1] = tmp;
		                swapped = true;
		            }
		        }
		    }
	}
}
