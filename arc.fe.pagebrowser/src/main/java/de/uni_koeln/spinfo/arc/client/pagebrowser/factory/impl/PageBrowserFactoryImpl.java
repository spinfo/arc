package de.uni_koeln.spinfo.arc.client.pagebrowser.factory.impl;

import org.cafesip.gwtcomp.client.ui.PopupMenu;
import org.cafesip.gwtcomp.client.ui.SuperTree;
import org.cafesip.gwtcomp.client.ui.SuperTreeItem;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;

import de.uni_koeln.spinfo.arc.client.pagebrowser.factory.PageBrowserFactory;
import de.uni_koeln.spinfo.arc.client.pagebrowser.view.dialog.PageEditorDialogViewImpl;
import de.uni_koeln.spinfo.arc.client.pagebrowser.view.util.SuperTreeConstants;
import de.uni_koeln.spinfo.arc.client.pagebrowser.view.util.SuperTreeConstants.PATH;
import de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget.OptionWidget;
import de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget.PageLeafItemWidget;
import de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget.TreePageDividerItemWidget;
import de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget.TreeRootItemWidget;
import de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget.impl.NewRangeOptionWidgetImpl;
import de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget.impl.PageLeafItemWidgetImpl;
import de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget.impl.TreePageDividerItemWidgetImpl;
import de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget.impl.TreeRootItemWidgetImpl;


public class PageBrowserFactoryImpl implements PageBrowserFactory {

	private PageBrowserFactoryImpl() {};
	
	public static PageBrowserFactory INSTANCE = new PageBrowserFactoryImpl();
	
	@Override
	public SuperTree createSuperTree() {
		SuperTree superTree = new SuperTree();
		superTree.setMultiSelectionEnabled(true);
		superTree.setAnimationEnabled(true);
		return superTree;
	}
	
	@Override
	public SuperTreeItem createRootItem() {
		return new SuperTreeItem(PATH.ROOT);
	}
	
	@Override
	public TreeRootItemWidget createRootItemWidget(String title) {
		TreeRootItemWidget rootWidget = new TreeRootItemWidgetImpl(title);
		return rootWidget;
	}
	
	@Override
	public SuperTreeItem createPageDeviderItem() {
		return new SuperTreeItem(PATH.DEVISION);
	}
	
	@Override
	public TreePageDividerItemWidget createPageDividerItemWidget(int start, int end) {
		TreePageDividerItemWidget pageDividerWidget = new TreePageDividerItemWidgetImpl(start, end);
		return pageDividerWidget;
	}
	
	@Override
	public SuperTreeItem createPageLeafItem() {
		return new SuperTreeItem(PATH.LEAF);
	}
	
	@Override
	public PageLeafItemWidget createPageLeafItemWidget(int ordinal, String title) {
		PageLeafItemWidget pageDividerWidget = new PageLeafItemWidgetImpl(ordinal, title);
		return pageDividerWidget;
	}
	
	
	@Override
	public PopupMenu createLeavePopupMenu(SuperTree superTree) {
		PopupMenu menu = new PopupMenu();
		superTree.setContextMenu(SuperTreeConstants.PATH.PATH_TO_LEAVES, menu);
		menu.setWidget(new PageEditorDialogViewImpl().asWidget());
		return menu;
	}
	
	@Override
	public Widget createAddToRangeOption(String annotationType, ClickHandler handler) {
//		HTML addToCatOption = new HTML(HTMLHelper.imageWithText(
//				"gwtcomp-icons/edit_add.png", "Add selection to new [" + annotationType + "]"));
//		return addToCatOption;
		OptionWidget optionWidget = new NewRangeOptionWidgetImpl();
		optionWidget.setQuestionToUserText("Add selection to new ");
		optionWidget.setTypeText(annotationType);
		optionWidget.addClickHandler(handler);
		return optionWidget.asWidget();
	} 
	
	@Override
	public Widget createViewPageOption (ClickHandler handler) {
//		HTML viewPageOption = new HTML(HTMLHelper.imageWithText(
//				"gwtcomp-icons/player_play.png", "edit words of this page"));
//		viewPageOption.addStyleName("gwt-DialogBox");
//		return viewPageOption;
		OptionWidget optionWidget = new NewRangeOptionWidgetImpl();
		optionWidget.setQuestionToUserText("Edit Page");
		optionWidget.setTypeText("");
		optionWidget.addClickHandler(handler);
		return optionWidget.asWidget();
	}
	
	@Override	
	public PopupMenu createAndSetPageLeavesPopUpMenu(SuperTree browserTree) {
		PopupMenu popMenu = new PopupMenu();
		browserTree.setContextMenu(PATH.PATH_TO_LEAVES, popMenu);
		return popMenu;
	}
}
