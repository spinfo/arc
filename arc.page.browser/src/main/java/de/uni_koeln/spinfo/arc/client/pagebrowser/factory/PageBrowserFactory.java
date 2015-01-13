package de.uni_koeln.spinfo.arc.client.pagebrowser.factory;

import org.cafesip.gwtcomp.client.ui.PopupMenu;
import org.cafesip.gwtcomp.client.ui.SuperTree;
import org.cafesip.gwtcomp.client.ui.SuperTreeItem;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;

import de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget.PageLeafItemWidget;
import de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget.TreePageDividerItemWidget;
import de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget.TreeRootItemWidget;

public interface PageBrowserFactory {

	public abstract SuperTree createSuperTree();

	public abstract SuperTreeItem createRootItem();

	public abstract TreeRootItemWidget createRootItemWidget(String title);

	public abstract SuperTreeItem createPageDeviderItem();

	public abstract TreePageDividerItemWidget createPageDividerItemWidget(
			int start, int end);

	public abstract SuperTreeItem createPageLeafItem();

	public abstract PageLeafItemWidget createPageLeafItemWidget(int ordinal,
			String title);

	public abstract PopupMenu createLeavePopupMenu(SuperTree superTree);

//	Widget createAddToRangeOption(String annotationType);
	
//	Widget createViewPageOption();

	PopupMenu createAndSetPageLeavesPopUpMenu(SuperTree browserTree);

	Widget createAddToRangeOption(String annotationType, ClickHandler handler);

	Widget createViewPageOption(ClickHandler handler);

}