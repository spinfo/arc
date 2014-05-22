package de.spinfo.arc.editor.client.cactus.views.impl;

import java.util.Iterator;
import java.util.List;




import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

import de.spinfo.arc.editor.client.cactus.Cactus;
import de.spinfo.arc.editor.client.cactus.CactusItem;
import de.spinfo.arc.editor.client.cactus.CactusListener;
import de.spinfo.arc.editor.client.cactus.CactusPopupMenu;
import de.spinfo.arc.editor.client.cactus.presenter.CactusViewPresenter;
import de.spinfo.arc.editor.client.cactus.views.CactusView;
import de.spinfo.arc.editor.client.cactus.views.ModificationDialogView;
import de.spinfo.arc.editor.client.cactus.views.factory.CactusFactory;
import de.spinfo.arc.editor.client.cactus.views.items.CactusCustomUnmodifiableNodeWidget;
import de.spinfo.arc.editor.shared.model3.modification.ModificationRange;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;

public class CactusViewImpl extends Composite implements CactusView {

//	private static MyCactusWithModelUiBinder uiBinder = GWT
//			.create(MyCactusWithModelUiBinder.class);
//
//	interface MyCactusWithModelUiBinder extends
//			UiBinder<Widget, MyCactusWithModel> {
//	}

	
//	@UiField
//	SimpleLayoutPanel chapterPanel;
	private Cactus cactus;
	private CactusFactory cactusFactory;
	private String nameOfModification;
	

	
	
	public CactusViewImpl(Types modificationRangeType, CactusFactory cactusFactory) {
//		initWidget(uiBinder.createAndBindUi(this));
		this.cactusFactory = cactusFactory;
		this.nameOfModification = modificationRangeType.toString();

		setUpTree();
		
		initWidget(cactus);
		
	}
	
	private CactusItem root;
	private CactusItem unmodifiableOverviewNode;
	
	private CactusViewPresenter presenter;

	private void setUpTree() {
		cactus = cactusFactory.createCactus();
		
        root = cactusFactory.createCactusRootItem();
        unmodifiableOverviewNode = cactusFactory.createCactusCategoryItem();
        
        unmodifiableOverviewNode.setWidget(cactusFactory.createCatusUnmodifiableNodeItemWidget("uncategorized").asWidget());
        
        root.addItem(unmodifiableOverviewNode);
        
        
        cactus.addItem(root);
        
        initListener();
        initPopupMenu();
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
	
	private CactusPopupMenu leavesPopUp;
	private Widget addToCategoryOption;
	private Widget removeFromCategoryOption;
	private Widget createViewPageOption;
	
	private void initPopupMenu() {
		leavesPopUp = cactusFactory.createAndSetLeavePopUpMenu(cactus);
		ClickHandler addCategoryClickHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.onAddClicked();
			}
		};
		ClickHandler removeCategoryClickHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.onRemoveClicked();
			}
		};
		
		ClickHandler viewPageClickHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.onViewPageClicked();
			}
		};
		
		addToCategoryOption = cactusFactory.createAddToCategoryOption(Types.WORKING_UNIT_CHAPTER_RANGE, addCategoryClickHandler);
		removeFromCategoryOption = cactusFactory.createRemoveFromCategoryOption(Types.WORKING_UNIT_CHAPTER_RANGE, removeCategoryClickHandler);
		createViewPageOption = cactusFactory.createViewPageOption(viewPageClickHandler);
		leavesPopUp.addMenuItem(addToCategoryOption);
		leavesPopUp.addMenuItem(removeFromCategoryOption);
		leavesPopUp.addMenuItem(createViewPageOption);
	}
	
	@Override
	public void setRootWidget(String title, int ordinal) {
		root.setWidget(cactusFactory.createCatusRootItemWidget(ordinal, title).asWidget());
	}
	
	@Override
	public void appendToUnodifiableOverviewNode(String title, int ordinal, ModificationRange userObject) {
		CactusItem item = cactusFactory.createCactusLeafItem();
		item.setWidget(cactusFactory.createCatusLeafItemWidget(ordinal, title).asWidget());
		item.setUserObject(userObject);
		unmodifiableOverviewNode.addItem(item);
	}
	
	@Override 
	public void createNewCategoryAndAppendLeaves(String titleOfCategory,ModificationRange categoryRange, List<ModificationRange> pageRanges) {
		// for each category range create a new cactus item and widget
		CactusItem newCategory = cactusFactory.createCactusCategoryItem();
		// set this modificationRange as userObject of the category cactusitem which encompasses the word ranges for all the the chapter ranges
		newCategory.setUserObject(categoryRange);
		newCategory.setWidget(cactusFactory.createCatusNodeItemWidget(root.getChildCount(), titleOfCategory).asWidget());
		for (Iterator<ModificationRange> pageIterator = pageRanges.iterator(); pageIterator.hasNext();) {
			ModificationRange current = (ModificationRange) pageIterator.next();
			// create the page range leafs for the categorized page ranges
			CactusItem copy = cactusFactory.createCactusLeafItem();
			// set this modificationRange as userObject of the category cactusitem which encompasses the word ranges for the the Pages ranges this leaf points to
			copy.setUserObject(current);
			String title = current.getDescription().getTitle();
			int ordinal = current.getDescription().getOrdinal();
			copy.setWidget(cactusFactory.createCatusLeafItemWidget(ordinal, title).asWidget());
			newCategory.addItem(copy);
			root.addItem(newCategory);
		}
	} 
	
	@Override
	public void removeAllButUncategorized() {
		for (Iterator<TreeItem> iterator = cactus.treeItemIterator(); iterator
				.hasNext();) {
			TreeItem treeItem = (TreeItem) iterator.next();
//			if (count > 1) {
				if(! (treeItem.getWidget() instanceof CactusCustomUnmodifiableNodeWidget))
						if (treeItem.getParentItem() != null )
							if (!(treeItem.getParentItem().getWidget() instanceof CactusCustomUnmodifiableNodeWidget)) {
									treeItem.remove();
										System.out.println(treeItem);
			}
		}
	}
	
	@Override
	public void setPresenter(CactusViewPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public Cactus getCactus() {
		 return cactus;
	}

	@Override
	public CactusItem getRootItem() {
		return root;
	}
	
	@Override
	public CactusItem getUnmodifiableCategoryItem() {
		return unmodifiableOverviewNode;
	}
	@Override
	public CactusPopupMenu getLeavesPopUp() {
		return leavesPopUp;
	}
	@Override
	public Widget getRemoveFromCategoryOption() {
		return removeFromCategoryOption;
	}
	
	@Override
	public Widget getAddToCategoryOption() {
		return addToCategoryOption;
	}
}
