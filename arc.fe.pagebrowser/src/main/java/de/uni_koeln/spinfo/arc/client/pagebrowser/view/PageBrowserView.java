package de.uni_koeln.spinfo.arc.client.pagebrowser.view;

import org.cafesip.gwtcomp.client.ui.SuperTree;
import org.cafesip.gwtcomp.client.ui.SuperTreeItem;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TreeItem;

import de.uni_koeln.spinfo.arc.client.pagebrowser.presenter.PageBrowserPresenter;


public interface PageBrowserView extends IsWidget {

	void setPresenter(PageBrowserPresenter pageBrowserPresenter);
	
	public void setRootTitle(String title);
	
	/** 
	 * Append a page devision node to the root item of the browser tree
	 * @param currDivisionStart the start page num for a division
	 * @param currDivisionEnd the end page num for a division
	 */
	void appendPageDivision(int currDivisionStart, int currDivisionEnd);

	SuperTree getBrowserTree();

	void appendPageToDevision(TreeItem divTobeAppendedTo, int pageNum);

	SuperTreeItem getRoot();
	

}
