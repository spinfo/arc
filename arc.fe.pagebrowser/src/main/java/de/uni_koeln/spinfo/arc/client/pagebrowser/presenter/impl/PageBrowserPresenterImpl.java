package de.uni_koeln.spinfo.arc.client.pagebrowser.presenter.impl;

import org.cafesip.gwtcomp.client.ui.PopupMenu;

import com.google.gwt.user.client.ui.TreeItem;

import de.uni_koeln.spinfo.arc.client.pagebrowser.presenter.PageBrowserPresenter;
import de.uni_koeln.spinfo.arc.client.pagebrowser.view.PageBrowserView;
import de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget.PageLeafItemWidget;
import de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget.TreePageDividerItemWidget;
import de.uni_koeln.spinfo.arc.client.supervisor.Supervisor;
import de.uni_koeln.spinfo.arc.dto.annotatable.WorkingUnitDto;
import de.uni_koeln.spinfo.arc.dto.annotation.AnnotationDto.AnnotationTypes;

public class PageBrowserPresenterImpl implements 
					PageBrowserPresenter {

	private static final boolean DEBUG_SET_UP_TREE = false;
	
	private PageBrowserView pageBrowserView;
	private Supervisor supervisor;
	private WorkingUnitDto workingUnit;

	
	public PageBrowserPresenterImpl(PageBrowserView pageBrowserView, Supervisor supervisor,
			WorkingUnitDto workingUnit) {
		this.pageBrowserView = pageBrowserView;
		this.supervisor = supervisor;
		this.workingUnit = workingUnit;
		this.pageBrowserView.setPresenter(this);
		/*tge browser tree gets this as listener. it listens mainly for on expand -events*/
		this.pageBrowserView.getBrowserTree().addTreeListener(this);
		
		buildTree();
	}
	
	private static final int PAGE_DIVISION = 20;

	private static final boolean DEBUG_FORWARD_TO_SUPERVISOR = false;
	
	private void buildTree() {
		/*set the title of the root node of the tree browser */
		pageBrowserView.setRootTitle(workingUnit.getTitle());
		int numPages = workingUnit.getPages().size();
		/* calculate the amount of devision nodes */
		int amountDivisions = numPages / PAGE_DIVISION;
		/* calculate if there is a remainder left in order to create an adiditional node */
		int remainder = numPages % PAGE_DIVISION;
		boolean hasRemainder = false;
		
		/*if there is some remainder increase the needed amount of division nodes */
        if (remainder > 0) {
        	amountDivisions += 1;
        	hasRemainder = true;
        }
        
        if (DEBUG_SET_UP_TREE) {
	        System.out.println("numPages " + numPages);
	        System.out.println("amountDivisions " + amountDivisions);
	        System.out.println("remainder " + remainder);
	        System.out.println("hasRemainder " + hasRemainder);
        }
        /*vars for setting the text of the page devider nodes */
        int currDivisionStart = 0;
        int currDivisionEnd = 0;
        
        for (int remainderIdx = 0; remainderIdx < amountDivisions; remainderIdx++) {
        	if (remainderIdx == amountDivisions - 1 && hasRemainder)
        		currDivisionEnd = numPages - 1;
        	else {
        		currDivisionEnd = (remainderIdx + 1) * PAGE_DIVISION - 1;
        	}
        	currDivisionStart = remainderIdx * PAGE_DIVISION;
        	
        	/*Append a page division node to the root*/
        	pageBrowserView.appendPageDivision(currDivisionStart, currDivisionEnd);
		} 
        /* Strange order for GWT tree initial */
        // unfold the first page devision node
        pageBrowserView.getRoot().getChild(0).setState(true, true);
        // unfold the root
        pageBrowserView.getRoot().setState(true, true);
        
	}

	@Override
	public void onAddAnnotation(AnnotationTypes type, int start, int end) {
        if (DEBUG_FORWARD_TO_SUPERVISOR) {
			System.out.println("onAddAnnotation " + type);
			System.out.println("start " + start);
			System.out.println("end " + end);
        }
        supervisor.onAddRangeAnnotation(type, start, end);
	}

	@Override
	public void onEditPage(int pageNum) {
		if (DEBUG_FORWARD_TO_SUPERVISOR) 
			System.out.println("onEditPage: " + pageNum);
		
		supervisor.onEditPage(pageNum);
	}

	/*
	 * Below Methods from SuperTreeListener
	 */
	@Override
	public void onPopup(String level, PopupMenu menu, TreeItem[] treeItems) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSelect(String level, TreeItem[] treeItems) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onExpand(TreeItem item) {
		/*Do only something if its on level of the divider nodes*/
		if (item.getWidget() instanceof TreePageDividerItemWidget) {
			/*Do only something if its hasn't been done already:
			 *(thus the page chidlren havn't already been created) -
			 *The view initializes the divider nodes with just one child-item holding a dummy widget
			 *If a collapsed item does not contain a item with the page widget
			 *its safe to delete the dummy item and append the real leaf widgets*/
			if( !(item.getChild(0).getWidget() instanceof PageLeafItemWidget) ) {
				item.removeItems();
				TreePageDividerItemWidget divWidget = (TreePageDividerItemWidget) item.getWidget();
				/*Get the specific properties of the divider widget*/
				int start = divWidget.getStart();
				int end = divWidget.getEnd();
				/*calculate the amount of new needed widgets for the single pages nodes*/
				int amountNeeded = end - start + 1;
				
				for (int i = 0; i < amountNeeded; i++) {
					/*call the view in order to append page-nodes to the expanded divider node */
					pageBrowserView.appendPageToDevision(item, start + i);
				}
				
			
			}
		}
	}

	@Override
	public void onCollapse(TreeItem item) {
		// TODO Auto-generated method stub
		
	}
	
}
