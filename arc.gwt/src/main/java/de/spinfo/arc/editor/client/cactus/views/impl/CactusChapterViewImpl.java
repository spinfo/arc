package de.spinfo.arc.editor.client.cactus.views.impl;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.widget.client.TextButton;

import de.spinfo.arc.editor.client.cactus.Cactus;
import de.spinfo.arc.editor.client.cactus.CactusHtmlHelper;
import de.spinfo.arc.editor.client.cactus.CactusItem;
import de.spinfo.arc.editor.client.cactus.CactusListener;
import de.spinfo.arc.editor.client.cactus.CactusPopupMenu;
import de.spinfo.arc.editor.client.cactus.presenter.CactusChapterViewPresenter;
import de.spinfo.arc.editor.client.cactus.views.CactusChapterView;


public class CactusChapterViewImpl extends Composite implements
		CactusChapterView {

	private CactusChapterViewPresenter presenter;

	@Override
	public void setPresenter(CactusChapterViewPresenter presenter) {
		this.presenter = presenter;

	}

	private Cactus cactus;
	private CactusItem root;

	private CactusItem uncategorizePages;
	

	/**
	 * The PopUpMenu which is associated with the Page leafs of each working
	 * unit
	 */
	private CactusPopupMenu pagesPopUp;
	
	/**
	 * The addChapter-Element which is placed in the Popup-Menu of the Page
	 * leafs of each working unit
	 */
	private HTML addPageToChapterOption;
	
	private HTML showPageInfoOption;
	/**
	 * The addChapter-Element which is placed in the Popup-Menu of the Page
	 * leafs of each working unit
	 */
	private HTML removePageFromChapterOption;
	
	/**
	 * The PopUpMenu which is associated with the user defined chapter nodes of each working
	 * unit
	 */
	private CactusPopupMenu chapterPopUp;
	
	
	
	/**
	 * The addChapter-Element which is placed in the Popup-Menu of the Page
	 * leafs of each working unit
	 */
	private HTML removeChapterFromWorkingUnitOption;

	private HTML showChapterInfoOption;
	
	private CactusPopupMenu workingUnitPopUp;
	
	private HTML showWorkingUnitInfoOption;
	
	/**
	 * Each Popup of each level of the cactus has an info option
	 */

	

	public CactusChapterViewImpl() {
		super();
		setUpTree();

		initPopupMenu();

		initListener();
		initWidget(cactus);
	}

	private void initListener() {
		cactus.addTreeListener(new CactusListener() {
			public void onPopup(String level, CactusPopupMenu menu,
					TreeItem[] treeItems) {
				presenter.onPopup(level, menu, treeItems);
			}

			public void onSelect(String level, TreeItem[] treeItems) {
				presenter.onSelect(level, treeItems);

			}

			public void onCollapse(TreeItem item) {
				presenter.onCollapse(item);
			}

			public void onExpand(TreeItem item) {
				presenter.onExpand(item);
			}
		});

	}
	


	
	private void initPopupMenu() {
		/*
		 * PopUp for the pages
		 */
		pagesPopUp = new CactusPopupMenu();
		// adding a Chapter
		addPageToChapterOption = new HTML(CactusHtmlHelper.imageWithText(
				"gwtcomp-icons/edit_add.png", "Add selection to new chapter"));
		
		addPageToChapterOption.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				presenter.onAddClicked();
			}
		});
		
		// removing a Chapter
		removePageFromChapterOption = new HTML(CactusHtmlHelper.imageWithText(
                "gwtcomp-icons/cancel.png", "remove selection from chapter"));
		removePageFromChapterOption.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				presenter.onRemoveClicked();
			}
		});
		
		showPageInfoOption = new HTML(CactusHtmlHelper.imageWithText(
				"gwtcomp-icons/info.png", "show info"));
		
		showPageInfoOption.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				presenter.onShowInfoClicked(); 
			}
		});
		
		pagesPopUp.addMenuItem(addPageToChapterOption);
//		pagesPopUp.addSeparator();
		pagesPopUp.addMenuItem(removePageFromChapterOption);
		pagesPopUp.addMenuItem(showPageInfoOption);
		
		cactus.setContextMenu(PATH_TO_PAGES, pagesPopUp);
		
		/*
		 * PopUp for the Chapters
		 */
		chapterPopUp = new CactusPopupMenu();
		
		
		removeChapterFromWorkingUnitOption = new HTML(CactusHtmlHelper.imageWithText(
                "gwtcomp-icons/cancel.png", "remove chapter from working unit"));
		
		removeChapterFromWorkingUnitOption.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				presenter.onRemoveClicked();
			}
		});
		
		showChapterInfoOption = new HTML(CactusHtmlHelper.imageWithText(
				"gwtcomp-icons/info.png", "show info"));
		
		showChapterInfoOption.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				presenter.onShowInfoClicked(); 
			}
		});
		
		chapterPopUp.addMenuItem(removeChapterFromWorkingUnitOption);
		
		chapterPopUp.addMenuItem(showChapterInfoOption);
		
		CactusPopupMenu subUp = new CactusPopupMenu();
		subUp.addMenuItem(new Label("YO"));
		TextButton btn = new TextButton("SUB");
		chapterPopUp.addMenuItem(btn, subUp);
		
		cactus.setContextMenu(PATH_TO_CHAPTERS, chapterPopUp);
		
	}

	private void setUpTree() {
		cactus = new Cactus();
		cactus.setMultiSelectionEnabled(true);
		cactus.setAnimationEnabled(true);

		root = new CactusItem("working_unit", WORKING_UNIT_LEVEL);
		cactus.addItem(root);

		// CactusItem newChapter = new
		// CactusItem("< create new chapter >", CHAPTER);
		// wuRoot.addItem(newChapter);

		uncategorizePages = new CactusItem("< all pages >", CHAPTER_LEVEL);
		root.addItem(uncategorizePages);

		for (int i = 0; i < 21; i++) {
			Label l = new Label("page nr." + i);
			CactusItem item = new CactusItem(l, PAGE_LEVEL);
			
			// test with custom widgets as items
//			CactusCustomItemWidget cw = new CactusCustomItemWidget();
//			CactusItem item = new CactusItem(cw, PAGE_LEVEL);
			
			uncategorizePages.addItem(item);
		}
	}

	@Override
	public Cactus getCactus() {
		return cactus;
	}

	@Override
	public CactusItem getRoot() {
		return root;
	}

//	@Override
//	public CactusItem getRemainingUnsorted() {
//		return remainingUnsorted;
//	}

	@Override
	public CactusPopupMenu getPagesPopUp() {
		return pagesPopUp;
	}
	
	@Override
	public CactusPopupMenu getChapterPopUp() { 
		return chapterPopUp;
	}

	@Override
	public HTML getAddPageToChapterOption() {
		return addPageToChapterOption;
	}
	
	@Override
	public HTML getRemovePageFromChapterOption() {
		return removePageFromChapterOption;
	}
	
	@Override
	public HTML getRemoveChapterFromWorkingUnitOption() {
		return removeChapterFromWorkingUnitOption;
	}

}
