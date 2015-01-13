package de.uni_koeln.spinfo.arc.client.pagebrowser.view.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.cafesip.gwtcomp.client.ui.PopupMenu;
import org.cafesip.gwtcomp.client.ui.SuperTree;
import org.cafesip.gwtcomp.client.ui.SuperTreeItem;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

import de.uni_koeln.spinfo.arc.client.pagebrowser.comparator.PageLeafItemWidgetOrdinalComparator;
import de.uni_koeln.spinfo.arc.client.pagebrowser.factory.PageBrowserFactory;
import de.uni_koeln.spinfo.arc.client.pagebrowser.presenter.PageBrowserPresenter;
import de.uni_koeln.spinfo.arc.client.pagebrowser.view.PageBrowserView;
import de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget.PageLeafItemWidget;
import de.uni_koeln.spinfo.arc.dto.annotation.AnnotationDto;

//import de.spinfo.arc.client.pagebrowser.view.util.SuperTreeConstants;

public class PageBrowserViewImpl extends Composite implements PageBrowserView {

	private SuperTree browserTree;
	private SuperTreeItem root;
	private Widget rootWidget;
	private PageBrowserFactory pageBrowserFactory;
	private PageBrowserPresenter presenter;

	public PageBrowserViewImpl(PageBrowserFactory pageBrowserFactory) {
		this.pageBrowserFactory = pageBrowserFactory;
		this.browserTree = pageBrowserFactory.createSuperTree();
		this.root = pageBrowserFactory.createRootItem();
		initWidget(browserTree);
		/* The actual text of the root widget is set by the presenter */
		rootWidget = pageBrowserFactory.createRootItemWidget("").asWidget();
		root.setWidget(rootWidget);
		browserTree.addItem(root);

		/* comment to make one click page editing unavailable - from here .. */
		browserTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
			@Override
			public void onSelection(SelectionEvent event) {
				if (browserTree.getSelectedItem().getWidget() instanceof PageLeafItemWidget) {
					System.out.println("selected items length: "
							+ browserTree.getSelectedItems().length);
					if (browserTree.getSelectedItems().length > 0) {
						TreeItem item = (TreeItem) event.getSelectedItem();
						PageLeafItemWidget widget = (PageLeafItemWidget) browserTree
								.getSelectedItem().getWidget();
						presenter.onEditPage(widget.getOrdinal());
					}
				}
			}
		});
		/* .. to here */

		setUpPopup();
	}

	private void setUpPopup() {

		final PopupMenu popMenu = pageBrowserFactory
				.createAndSetPageLeavesPopUpMenu(browserTree);

		ClickHandler viewPageOptionHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (browserTree.getSelectedItem().getWidget() instanceof PageLeafItemWidget) {
					int selectedPageNum = ((PageLeafItemWidget) browserTree
							.getSelectedItem().getWidget()).getOrdinal();
					presenter.onEditPage(selectedPageNum);
					popMenu.hideAll();
				}
			}
		};
		ClickHandler addChapterOptionHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (browserTree.getSelectedItem().getWidget() instanceof PageLeafItemWidget) {

					sortSelectedAndAddAnnotation(
							AnnotationDto.AnnotationTypes.CHAPTER_RANGE,
							browserTree.getSelectedItems());
					popMenu.hideAll();
				}
			}
		};
		ClickHandler addLanguageOptionHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (browserTree.getSelectedItem().getWidget() instanceof PageLeafItemWidget) {
					sortSelectedAndAddAnnotation(
							AnnotationDto.AnnotationTypes.LANGUAGE_RANGE,
							browserTree.getSelectedItems());
					popMenu.hideAll();
				}
			}
		};
		Widget viewPageOption = pageBrowserFactory
				.createViewPageOption(viewPageOptionHandler);
		Widget createChapterOption = pageBrowserFactory.createAddToRangeOption(
				AnnotationDto.AnnotationTypes.CHAPTER_RANGE.toString(),
				addChapterOptionHandler);
		Widget createLanguageOption = pageBrowserFactory
				.createAddToRangeOption(
						AnnotationDto.AnnotationTypes.LANGUAGE_RANGE.toString(),
						addLanguageOptionHandler);

		popMenu.addMenuItem(viewPageOption);
		popMenu.addMenuItem(createChapterOption);
		popMenu.addMenuItem(createLanguageOption);

	}

	/**
	 * Sorts the selected tree items and forwards the the first and last
	 * selected as Pages to be specified for range stard and end respectivly
	 * 
	 * @param annotationType
	 *            the kind of annotation to be specified
	 * @param items
	 *            the selected items to be sorted
	 */
	private void sortSelectedAndAddAnnotation(
			AnnotationDto.AnnotationTypes annotationType, TreeItem[] items) {
		/*
		 * Create a list of the widgets in order to sort them (necessary because
		 * the array of selected items is not ordered
		 */
		List<PageLeafItemWidget> leafwidgets = new ArrayList<>(items.length);
		for (int i = 0; i < items.length; i++) {
			leafwidgets.add((PageLeafItemWidget) items[i].getWidget());
		}
		Collections.sort(leafwidgets,
				PageLeafItemWidgetOrdinalComparator.INSTANCE);
		int selectedPageNumStart = leafwidgets.get(0).getOrdinal();
		int selectedPageNumEnd = leafwidgets.get(leafwidgets.size() - 1)
				.getOrdinal();

		presenter.onAddAnnotation(annotationType, selectedPageNumStart,
				selectedPageNumEnd);
	}

	/**
	 * Bubble Sorting a tree items array
	 * 
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
				int currentIdx = array[i].getParentItem().getChildIndex(
						array[i]);
				int nextIdx = array[i + 1].getParentItem().getChildIndex(
						array[i + 1]);
				if (currentIdx > nextIdx) {
					tmp = array[i];
					array[i] = array[i + 1];
					array[i + 1] = tmp;
					swapped = true;
				}
			}
		}
	}

	@Override
	public SuperTreeItem getRoot() {
		return root;
	}

	@Override
	public void appendPageDivision(int currDivisionStart, int currDivisionEnd) {
		Widget deviderWidget = pageBrowserFactory.createPageDividerItemWidget(
				currDivisionStart, currDivisionEnd).asWidget();
		SuperTreeItem devider = pageBrowserFactory.createPageDeviderItem();
		devider.setWidget(deviderWidget);

		/*
		 * Set up dummy items for first time because the actual items for pages
		 * are loaded lazily in response of clicking on a node (then the dummy
		 * child get deleted)
		 */
		TreeItem dummy = new TreeItem();
		dummy.setWidget(new Label("d"));
		devider.addItem(dummy);

		root.addItem(devider);

	}

	@Override
	public void appendPageToDevision(TreeItem divTobeAppendedTo, int pageNum) {
		Widget pageWidget = pageBrowserFactory.createPageLeafItemWidget(
				pageNum, "page").asWidget();
		SuperTreeItem pageItem = pageBrowserFactory.createPageLeafItem();
		pageItem.setWidget(pageWidget);

		divTobeAppendedTo.addItem(pageItem);
	}

	@Override
	public void setRootTitle(String title) {
		rootWidget.setTitle(title);
	}

	@Override
	public SuperTree getBrowserTree() {
		return browserTree;
	}

	@Override
	public void setPresenter(PageBrowserPresenter pageBrowserPresenter) {
		this.presenter = pageBrowserPresenter;
	}

	@Override
	public Widget asWidget() {
		return super.asWidget();
	}

}
