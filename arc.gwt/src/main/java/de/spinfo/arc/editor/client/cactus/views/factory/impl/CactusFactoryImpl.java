package de.spinfo.arc.editor.client.cactus.views.factory.impl;


import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;

import de.spinfo.arc.editor.client.cactus.Cactus;
import de.spinfo.arc.editor.client.cactus.CactusHtmlHelper;
import de.spinfo.arc.editor.client.cactus.CactusItem;
import de.spinfo.arc.editor.client.cactus.CactusPopupMenu;
import de.spinfo.arc.editor.client.cactus.uitl.CactusConstants.PATH;
import de.spinfo.arc.editor.client.cactus.views.CactusView;
import de.spinfo.arc.editor.client.cactus.views.ModificationDialogView;
import de.spinfo.arc.editor.client.cactus.views.factory.CactusFactory;
import de.spinfo.arc.editor.client.cactus.views.impl.CactusViewImpl;
import de.spinfo.arc.editor.client.cactus.views.impl.ModificationDialogViewImpl;
import de.spinfo.arc.editor.client.cactus.views.items.CactusCustomLeafItemWidget;
import de.spinfo.arc.editor.client.cactus.views.items.CactusCustomNodeItemWidget;
import de.spinfo.arc.editor.client.cactus.views.items.CactusCustomRootItemWidget;
import de.spinfo.arc.editor.client.cactus.views.items.CactusCustomUnmodifiableNodeWidget;
import de.spinfo.arc.editor.client.cactus.views.items.impl.CactusCustomLeafItemWidgetImpl;
import de.spinfo.arc.editor.client.cactus.views.items.impl.CactusCustomNodeItemWidgetImpl;
import de.spinfo.arc.editor.client.cactus.views.items.impl.CactusCustomRootItemWidgetImpl;
import de.spinfo.arc.editor.client.cactus.views.items.impl.CactusCustomUnmodifiableNodeWidgetImpl;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;


public class CactusFactoryImpl implements CactusFactory {
	
	private CactusFactoryImpl () {};
	
	public static CactusFactory INSTANCE = new CactusFactoryImpl();
	
	@Override	
	public CactusView createCactusView(Types modificationRangeType) {
		
		return new CactusViewImpl(modificationRangeType, this);
		
	}	
	
	@Override
	public Cactus createCactus() {
		Cactus cactus = new Cactus();
		cactus.setMultiSelectionEnabled(true);
		cactus.setAnimationEnabled(true);
		
		return cactus;
	}
	
	@Override
	public CactusItem createCactusRootItem() {
		return new CactusItem(PATH.ROOT);
	}
	
	@Override
	public CactusItem createCactusCategoryItem() {
		return new CactusItem(PATH.CATEGORY);
	}
	
	@Override
	public CactusItem createCactusLeafItem() {
		return new CactusItem(PATH.LEAF);
	}
	
	@Override
	public CactusCustomRootItemWidget createCatusRootItemWidget (int ordinal, String title) {
		return new CactusCustomRootItemWidgetImpl(ordinal, title);
	}
	
	@Override
	public CactusCustomLeafItemWidget createCatusLeafItemWidget (int ordinal, String title) {
		return new CactusCustomLeafItemWidgetImpl(ordinal, title);
	}

	@Override
	public CactusCustomNodeItemWidget createCatusNodeItemWidget(int ordinal,
			String title) {
		return new CactusCustomNodeItemWidgetImpl(ordinal, title);
	}
	
	@Override
	public CactusCustomUnmodifiableNodeWidget createCatusUnmodifiableNodeItemWidget(
			String title) {
		 return new CactusCustomUnmodifiableNodeWidgetImpl(title); 
	}
	
	@Override
	public CactusPopupMenu createAndSetRootPopUpMenu(Cactus cactus) {
		CactusPopupMenu popUp = new CactusPopupMenu();
		cactus.setContextMenu(PATH.PATH_TO_ROOT, popUp);
		return popUp;
	}
	@Override
	public CactusPopupMenu createAndSetNodePopUpMenu(Cactus cactus) {
		CactusPopupMenu popUp = new CactusPopupMenu();
		cactus.setContextMenu(PATH.PATH_TO_CATEGORIES, popUp);
		return popUp;
	}
	@Override
	public CactusPopupMenu createAndSetLeavePopUpMenu(Cactus cactus) {
		CactusPopupMenu popUp = new CactusPopupMenu();
		cactus.setContextMenu(PATH.PATH_TO_LEAVES, popUp);
		return popUp;
	}
	@Override
	public CactusPopupMenu createAndSetPopUpMenu(String pathToLevel, Cactus cactus) {
		CactusPopupMenu popUp = new CactusPopupMenu();
		cactus.setContextMenu(pathToLevel, popUp);
		return popUp;
	}
	
	@Override
	public HTML createAddToCategoryOption(Types rangeType, ClickHandler clickHandler) {
		HTML addToCatOption = new HTML(CactusHtmlHelper.imageWithText(
				"gwtcomp-icons/edit_add.png", "Add selection to new " + rangeType.toString()));
		addToCatOption.addClickHandler(clickHandler);
		return addToCatOption;
	} 
	@Override
	public HTML createRemoveFromCategoryOption(Types rangeType, ClickHandler clickHandler) {
		HTML removeFromCatOption = new HTML(CactusHtmlHelper.imageWithText(
				"gwtcomp-icons/cancel.png", "Remove selection from " + rangeType.toString()));
		removeFromCatOption.addClickHandler(clickHandler);
		return removeFromCatOption;
	}
	
	@Override
	public HTML createViewPageOption(ClickHandler clickHandler) {
		HTML viewPageOption = new HTML(CactusHtmlHelper.imageWithText(
				"gwtcomp-icons/player_play.png", "view page"));
		viewPageOption.addClickHandler(clickHandler);
		return viewPageOption;
	}

	@Override
	public ModificationDialogView createModificationDialog(Types modificationRangeType) {
		ModificationDialogView dialog = new ModificationDialogViewImpl();
		dialog.setTitleLabelText("create new " + modificationRangeType.toString());
		dialog.setQuestionToUserLabelText("enter title for " + modificationRangeType.toString());
		return dialog;
	}

	
	
}
