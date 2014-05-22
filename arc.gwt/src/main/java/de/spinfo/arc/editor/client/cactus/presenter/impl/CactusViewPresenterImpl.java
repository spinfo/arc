package de.spinfo.arc.editor.client.cactus.presenter.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.entity.ContentProducer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.spinfo.arc.editor.client.cactus.CactusItem;
import de.spinfo.arc.editor.client.cactus.CactusPopupMenu;
import de.spinfo.arc.editor.client.cactus.comparators.ModificationRangeComparator;
import de.spinfo.arc.editor.client.cactus.comparators.TreeItemModificationRangeUserObjectComparator;
import de.spinfo.arc.editor.client.cactus.presenter.CactusViewPresenter;
import de.spinfo.arc.editor.client.cactus.presenter.ModificationDialogViewPresenter;
import de.spinfo.arc.editor.client.cactus.uitl.CactusConstants;
import de.spinfo.arc.editor.client.cactus.views.CactusView;
import de.spinfo.arc.editor.client.cactus.views.ModificationDialogView;
import de.spinfo.arc.editor.client.cactus.views.factory.CactusFactory;
import de.spinfo.arc.editor.client.cactus.views.factory.impl.CactusFactoryImpl;
import de.spinfo.arc.editor.client.cactus.views.impl.ModificationDialogViewImpl;
import de.spinfo.arc.editor.client.cactus.views.items.CactusCustomLeafItemWidget;
import de.spinfo.arc.editor.client.cactus.views.items.CactusCustomNodeItemWidget;
import de.spinfo.arc.editor.client.cactus.views.items.CactusCustomUnmodifiableNodeWidget;
import de.spinfo.arc.editor.client.mvp.ClientFactoryImpl;
import de.spinfo.arc.editor.client.mvp.Tokens;
import de.spinfo.arc.editor.client.mvp.presenter.WorkingUnitBrowserInfoViewPresenter;
import de.spinfo.arc.editor.client.mvp.views.WorkingUnitBrowserInfoView;
import de.spinfo.arc.editor.client.mvp.views.impl.WorkingUnitBrowserInfoViewImpl;
import de.spinfo.arc.editor.shared.model3.description.BaseDescription;
import de.spinfo.arc.editor.shared.model3.description.impl.BaseDescriptionImpl;
import de.spinfo.arc.editor.shared.model3.modifiable.ModifiableWithParent;
import de.spinfo.arc.editor.shared.model3.modification.Modification;
import de.spinfo.arc.editor.shared.model3.modification.ModificationRange;
import de.spinfo.arc.editor.shared.model3.modification.ModificationString;
import de.spinfo.arc.editor.shared.model3.modification.payload.RangeUnit;
import de.spinfo.arc.editor.shared.model3.modification.payload.impl.RangeUnitImpl;
import de.spinfo.arc.editor.shared.model3.util.DebugHelper;
import de.spinfo.arc.editor.shared.model3.util.WorkingUnitHelper;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;
import de.spinfo.arc.editor.shared.model3.util.factory.impl.WorkingUnitFactoryImpl;
import de.spinfo.arc.editor.shared.model3.workingunit.WorkingUnit;
import de.spinfo.arc.editor.shared.service.WorkingUnitDetails;
import de.spinfo.arc.editor.shared.service.WorkingUnitService;
import de.spinfo.arc.editor.shared.service.WorkingUnitServiceAsync;
import de.spinfo.arc.editor.shared.service.commands.Command;
import de.spinfo.arc.editor.shared.service.commands.impl.CommandHandler;
import de.spinfo.arc.editor.shared.service.commands.impl.CommandImpl;
import de.spinfo.arc.editor.shared.service.commands.impl.WorkingUnitCommandImpl;


public class CactusViewPresenterImpl implements CactusViewPresenter, ModificationDialogViewPresenter, WorkingUnitBrowserInfoViewPresenter {
	private static final boolean DEBUG_RECEIVED_RANGES_FROM_MODEL = true;

	private final CactusView cactusView;
	private final Types modificationRangeType;
	private final WorkingUnitServiceAsync rpcService = GWT.create(WorkingUnitService.class);
	
	private static final CactusFactory FAC = CactusFactoryImpl.INSTANCE;
	
	private ModificationDialogView dialogView;
	private PopupPanel dialogViewPopUp;
	
	public CactusViewPresenterImpl (CactusView cactusView, Types modificationRangeType, int workingUnitIdx) {
		this.cactusView = cactusView;
		this.modificationRangeType = modificationRangeType;
		
		dialogView = FAC.createModificationDialog(modificationRangeType);
		dialogViewPopUp = (PopupPanel) dialogView.asWidget();
		dialogViewPopUp.hide();
		
		bind();
		
		setUpCactusFromModel();

	}
	

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(cactusView.asWidget());
	}

	private WorkingUnit workingUnit;

	private HasWidgets contenPanel;
	
	public void setUpCactusFromModel() {
		final Command cmdGetWorkingUnit = new CommandImpl("get working units", new Date());
		CommandHandler.appendCommand(cmdGetWorkingUnit);
		rpcService.getWorkingUnit(0 , new AsyncCallback<WorkingUnit>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onSuccess(WorkingUnit result) {
				workingUnit = result;
				cactusView.setRootWidget(result.getDescription().getTitle(), 1);
				setUpOverview();
				
				setUpCactusCategories();
				
				cmdGetWorkingUnit.setIsExecuted(true);
				System.out.println("success");
			}
		});
	}

	@Override
	public void onViewPageClicked() {
		cactusView.getLeavesPopUp().hide();
		if (cactusView.getCactus().getSelectedItems() != null) {
			TreeItem[] selectedItems = cactusView.getCactus()
					.getSelectedItems();
			
			contenPanel.clear();
			
			
			final ModificationRange lastSelected = (ModificationRange) selectedItems[selectedItems.length -1].getUserObject();
			List<ModifiableWithParent> words = WorkingUnitHelper.getWordsOfPage(lastSelected.getDescription().getOrdinal(), workingUnit);
		
			WorkingUnitBrowserInfoView infoView = clientFactory.getWorkingUnitInfoView();
			infoView.clearAllContent();
			infoView.setPresenter(this);
			infoView.setHeaderText("Overview of: " + lastSelected.getDescription().getTitle() + " "
										+ lastSelected.getDescription().getOrdinal());
			Button editButton = new Button("edit");
			editButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					onInfoEditButtonClicked(workingUnit.getDescription().getOrdinal(),lastSelected.getDescription().getOrdinal());
					
				}
			});
			infoView.addToHeaderPanel(editButton);
			infoView.setContentText("the selected Page contains:");
			
			HorizontalPanel hp = new HorizontalPanel();
			infoView.addToContentPanel(new Label("Number of Words: " + words.size()));
			
			int formModCount = 0;
			int posModcount = 0;
			int rectModcount = 0;
			List<Modification> stringMods = new LinkedList<>();
			List<Modification> posMods = new LinkedList<>();
			List<Modification> rectMods = new LinkedList<>();
			for (Iterator<ModifiableWithParent> iterator = words.iterator(); iterator.hasNext();) {
				ModifiableWithParent modifiableWithParent = (ModifiableWithParent) iterator
						.next();
					stringMods  = (List<Modification>) modifiableWithParent.getModifications(Types.TEXT_MOD);
					posMods  = (List<Modification>) modifiableWithParent.getModifications(Types.POS_MOD);
					rectMods  = (List<Modification>) modifiableWithParent.getModifications(Types.RECTANGLE_MOD);
					// below is counting while ignoring each first modification because this should be the inital one
				if (!stringMods.isEmpty()) 
					formModCount ++;	
//				if (posMods.size() > 1) 
				if (!posMods.isEmpty()) 
					posModcount ++;	
//				if (rectMods.size() > 1) 
				if (!rectMods.isEmpty()) 
					rectModcount ++;	
				}
			
				VerticalPanel vpStatRow = new VerticalPanel();
					vpStatRow.add(new Label(formModCount + " / " + words.size() + " words have form modifications" ));
					vpStatRow.add(new Label(posModcount + " / " + words.size() + " words have part of speech modifications" ));
					vpStatRow.add(new Label(rectModcount + " / " + words.size() + " words have rectangle modifications" ));
				infoView.addToContentPanel(vpStatRow);
			contenPanel.add(infoView.asWidget());
			
			
			
			
			
//			VerticalPanel vp = new VerticalPanel();
//			contenPanel.add(vp);
//			int wordCount = 0;
//			HorizontalPanel hp = new HorizontalPanel();
//			for (Iterator iterator = words.iterator(); iterator.hasNext();) {
//				ModifiableWithParent modifiableWithParent = (ModifiableWithParent) iterator
//						.next();
//				ModificationString stringMod  = (ModificationString) modifiableWithParent.getLastModificationOf(Types.TEXT_MOD);
//				hp.add(new Label(stringMod.getText() + "___"));
//				wordCount++;
//				if (wordCount % 10 == 0) {
//					vp.add(hp);
//					 hp = new HorizontalPanel();
//				}
//				
//			}
//			System.out.println("words of page: "+ lastSelected.getDescription().getOrdinal() + "\n" +
//									WorkingUnitHelper.getWordsOfPage(lastSelected.getDescription().getOrdinal(), workingUnit));
//			
		}
	}
	
	@Override
	public void bind() {
		cactusView.setPresenter(this);
		dialogView.setPresenter(this);
	}
	
	/*
	 * Callbacks from Dialog
	 */
	@Override
	public void onDialogOkBtnClicked(ClickEvent e) {
		System.out.println("onDialogOkBtnClicked");
		dialogViewPopUp.hide();
		String resultFromTextInput = dialogView.getCategoryInputTextBoxText();
		String resultFromComment = dialogView.getCommentInputTextAreaText();
		addNewCategoryNode(resultFromTextInput, resultFromComment);
	}

	private void addNewCategoryNode(String resultFromTextInput, String resultFromComment) {
		if (cactusView.getCactus().getSelectedItems() != null) {
			TreeItem[] selectedItems = cactusView.getCactus()
					.getSelectedItems();
			// get the last parent to close this node in favour of the new created category
			TreeItem lastParent  = getParentOfItems(selectedItems);
			List<TreeItem> selectedItemsList = Arrays.asList(selectedItems);
			//sort this list based on the ranges as user object in the items
			Collections.sort(selectedItemsList, TreeItemModificationRangeUserObjectComparator.INSTANCE);
			
			/*
			 * uncomment below to test other method to find distinct selections
			 */
			// CactusViewPresenterHelper.findSucceedingAndDistincSelectedRanges(selectedItems);
			
			int start = ((ModificationRange) selectedItemsList.get(0).getUserObject()).getPayload().getRange().getStart();
			int size = selectedItemsList.size();
			int end = ((ModificationRange) selectedItemsList.get(size-1).getUserObject()).getPayload().getRange().getEnd();
			
			RangeUnit ru = WorkingUnitFactoryImpl.INSTANCE.createRange(start, end);
			
			// @Todo how important is the ordinal to be set here? 
			BaseDescription description= new BaseDescriptionImpl(0, resultFromTextInput);
			WorkingUnitFactoryImpl.INSTANCE.createAndAppendChapterRangeModification(ru, workingUnit, description, new Date(), 0).setComment(resultFromComment);
			
			cactusView.removeAllButUncategorized();
			
			setUpCactusCategories();
			
			if (lastParent.getChildCount() == 0)
				lastParent.remove();
			else if (lastParent.getState())
				lastParent.setState(false, true);
			
			cactusView.getCactus().unselectItems();
		}
	}
	


	private void setUpOverview() {
		// set up page leafs to the uncategorized node -> each pageRange gets a leaf
		List<ModificationRange> pages = workingUnit.getAllPageRanges();
		for (Iterator<ModificationRange> iterator = pages.iterator(); iterator.hasNext();) {
			ModificationRange modificationRange = (ModificationRange) iterator
					.next();
			String title = modificationRange.getDescription().getTitle();
			int ordinal = modificationRange.getDescription().getOrdinal();
			cactusView.appendToUnodifiableOverviewNode(title, ordinal, modificationRange);
		}
	}


	
	private void setUpCactusCategories() {
		StringBuilder sb;
		if (DEBUG_RECEIVED_RANGES_FROM_MODEL) {
			 sb = new StringBuilder();
			sb.append("setUpCactusCategories() DEBUG_RECEIVED_RANGES_FROM_MODEL: " + modificationRangeType +" ( CategoryRanges ) \n");
		}
		/*
		 *  Get all Ranges this editor is respinsible of. In case of Types.WORKING_UNIT_CHAPTER_RANGE below retrieves all Ranges of
		 *  this type
		 */
		List<ModificationRange> categoryRanges = WorkingUnitHelper.getAllRangesOfType(modificationRangeType, workingUnit);
		if (!categoryRanges.isEmpty()) {
			if (DEBUG_RECEIVED_RANGES_FROM_MODEL) sb.append(categoryRanges);
			for (Iterator<ModificationRange> categoryIterator = categoryRanges
					.iterator(); categoryIterator.hasNext();) {
				ModificationRange categoryRange = (ModificationRange) categoryIterator
						.next();
				/*
				 * For each Category range (f.i.
				 * Types.WORKING_UNIT_CHAPTER_RANGE) get the Pages (which are
				 * ranges of themself) which are part of the Category range
				 */
				List<ModificationRange> pageRanges = WorkingUnitHelper
						.getSublistOfModificationRangesByOther(categoryRange,
								Types.WORKING_UNIT_PAGE_RANGE, workingUnit);
				// if there are at least one page range containing in the
				// category range..
				if (!pageRanges.isEmpty()) {
					/*
					 * create a new cactus item as a Category-Node (by passing
					 * in this range as 1. param) and pass in the
					 * List<ModificationRange> which embody the containing page
					 * Ranges in order to build up the leaves for this specific
					 * new Category
					 */
					cactusView.createNewCategoryAndAppendLeaves(categoryRange
							.getDescription().getTitle(), categoryRange,
							pageRanges);
				}

			}
				}
				if (DEBUG_RECEIVED_RANGES_FROM_MODEL) {
					DebugHelper.print(this.getClass(), sb.toString(), true);
				}
		}
		

	@Override
	public void onDialogCancelBtnClicked(ClickEvent e) {
		System.out.println("onDialogCancelBtnClicked");
		dialogViewPopUp.hide();
	}

	
	/*
	 * Callbacks from CactusView 
	 * 
	 */
	
	/*
	 * PopUp from leaves
	 */
	
	@Override
	public void onAddClicked() {
		dialogViewPopUp.show();
		GWT.log("CactusViewPresenterImpl onAddToChapterClicked");
		cactusView.getLeavesPopUp().hideAll();
	}
	
	@Override
	public void onRemoveClicked() {
		if (cactusView.getCactus().getSelectedItems() != null) {
			TreeItem[] selectedItems = cactusView.getCactus()
					.getSelectedItems();
			int foundAndRemoved = 0;
			// get the last parent to close this node in favour of the new created category
			TreeItem lastParent  = getParentOfItems(selectedItems);
			// get the ModificationRange of the parent node - the category
			ModificationRange rangeOfCategory = (ModificationRange) lastParent.getUserObject();
			System.out.println("rangeOfCategory\n" + rangeOfCategory);
			
			WorkingUnitHelper.removeModificationRangeFromWorkingUnit(rangeOfCategory, workingUnit);
			
			System.out.println("workingUnit chapterRanges\n" +  WorkingUnitHelper.getAllRangesOfType(modificationRangeType, workingUnit));
			cactusView.getCactus().unselectItems();
			cactusView.removeAllButUncategorized();
			setUpCactusCategories();
		}
		
	}
	
	@Override
	public void onShowInfoClicked() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPopup(String level, CactusPopupMenu menu, TreeItem[] treeItems) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSelect(String level, TreeItem[] treeItems) {
		System.out.println("ON SELECT level: " + level + " items: " + treeItems);
		
	}

	@Override
	public void onExpand(TreeItem item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCollapse(TreeItem item) {
		// TODO Auto-generated method stub
		
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
	
	public static int getIndexOfItem(TreeItem item) {
		return ((CactusCustomLeafItemWidget) item.getWidget()).getOrdinal();
	}

	ClientFactoryImpl clientFactory = new ClientFactoryImpl();
	
	public void go(HasWidgets chapterPanel, HasWidgets contentPanel) {
		chapterPanel.clear();
		chapterPanel.add(cactusView.asWidget());
//		contentPanel.clear();
		this.contenPanel = contentPanel;
		
		WorkingUnitBrowserInfoView infoView = clientFactory.getWorkingUnitInfoView();
		infoView.setPresenter(this);
		infoView.setHeaderText("Edit a working unit");
		infoView.setContentText("On the left side is a brwoser where you can define ranges of chapters, languages and much more. " +
								"Use the richt-click context menu on pages to create specifiic ranges");
		contenPanel.add(infoView.asWidget());
	}

	/*
	 * Callback from the info view if view page is clicked
	 */

	public void onInfoEditButtonClicked(int i, int j) {
		System.out.println(this.getClass() + " this class acting as presenter for the " + clientFactory.getWorkingUnitInfoView().getClass() +"\n"
																+ "gets called with onInfoEditButtonClicked()");
		History.newItem(Tokens.EDIT_PAGE+"&"+i+"&"+j);
	}



}
