package de.spinfo.arc.editor.client.cactus.presenter.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TreeItem;

import de.spinfo.arc.editor.client.cactus.CactusItem;
import de.spinfo.arc.editor.client.cactus.CactusPopupMenu;
import de.spinfo.arc.editor.client.cactus.presenter.CactusChapterViewPresenter;
import de.spinfo.arc.editor.client.cactus.views.CactusChapterView;


public class CactusChapterViewPresenterImpl implements
		CactusChapterViewPresenter {

	private final CactusChapterView cactusChapterView;

	public CactusChapterViewPresenterImpl(CactusChapterView cactusChapterView) {
		super();
		this.cactusChapterView = cactusChapterView;
		bind();
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(cactusChapterView.asWidget());

	}

	@Override
	public void bind() {
		cactusChapterView.setPresenter(this);
	}

	@Override
	public void onPopup(String level, CactusPopupMenu menu, TreeItem[] treeItems) {
		sortTreeItemsArrayByIdx(treeItems);
		GWT.log("OPENED POPUP");
		GWT.log("level: " + level);
		GWT.log("Selected Items: " + treeItems.length);
		
		if (level.equals(PATH_TO_PAGES)) {
			if (getParentOfItems(treeItems).getText().equals("< all pages >")) {
//				cactusChapterView.getPagesPopUp().remove(cactusChapterView.getRemovePageFromChapterOption());
				cactusChapterView.getRemovePageFromChapterOption().setVisible(false);
				cactusChapterView.getAddPageToChapterOption().setVisible(true);
			} 
			else {
				cactusChapterView.getRemovePageFromChapterOption().setVisible(true);
				cactusChapterView.getAddPageToChapterOption().setVisible(false);
			}
		}
		else if (level.equals(PATH_TO_CHAPTERS))
		{
			if (treeItems[0].getText().equals("< all pages >")) {
				cactusChapterView.getRemoveChapterFromWorkingUnitOption().setVisible(false);
			}
			else {
				cactusChapterView.getRemoveChapterFromWorkingUnitOption().setVisible(true);
			}
		}
		
	}

	@Override
	public void onSelect(String level, TreeItem[] treeItems) {
		GWT.log(treeItems.length + " item(s) selected at level " + level);

	}

	@Override
	public void onExpand(TreeItem item) {
		GWT.log("expanded item: " + item.getText());

	}

	@Override
	public void onCollapse(TreeItem item) {
		GWT.log("collapsed item: " + item.getText());

	}

	@Override
	public void onAddClicked() {
		GWT.log("CactusChapterViewPresenterImpl onAddToChapterClicked");

		cactusChapterView.getPagesPopUp().hideAll();

		if (cactusChapterView.getCactus().getSelectedItems() != null) {
			TreeItem[] selectedItems = cactusChapterView.getCactus()
					.getSelectedItems();

			TreeItem lastParent  = getParentOfItems(selectedItems);

			CactusItem newChapter = new CactusItem(
					"fresh chapter created by you!", CHAPTER_LEVEL);
			cactusChapterView.getRoot().addItem(newChapter);

			sortTreeItemsArrayByIdx(selectedItems);

			// Make new instances of the leaves when moving to chapters
			for (int j = 0; j < selectedItems.length; j++) {
				String content = ((Label) selectedItems[j].getWidget())
						.getText();
				CactusItem copy = new CactusItem(new Label(content),
						PAGE_LEVEL);
				newChapter.addItem(copy);
			}
			if (lastParent.getChildCount() == 0)
				lastParent.remove();
			else if (lastParent.getState())
				lastParent.setState(false, true);
			
			cactusChapterView.getCactus().unselectItems();
		}
	}

	@Override
	public void onRemoveClicked() {
		GWT.log("CactusChapterViewPresenterImpl onRemoveFromChapterClicked");
		
		cactusChapterView.getPagesPopUp().hideAll();
		cactusChapterView.getChapterPopUp().hideAll();
		
		if (cactusChapterView.getCactus().getSelectedItems() != null) {
			TreeItem[] selectedItems = cactusChapterView.getCactus()
					.getSelectedItems();
			TreeItem lastParent = getParentOfItems(selectedItems);

//			if (!lastParent.getText().equals("< all pages >")) {

				for (int j = 0; j < selectedItems.length; j++) {
					selectedItems[j].remove();
				}

				if (lastParent.getChildCount() == 0)
					lastParent.remove();
//			}
				
				cactusChapterView.getCactus().unselectItems();
		}
	}
	
	
	@Override
	public void onShowInfoClicked() {
		GWT.log("CactusChapterViewPresenterImpl onShowInfoClicked");
		
	}
	
	
	private static TreeItem getParentOfItems(TreeItem[] items ) {
		return items[0].getParentItem();
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

}
