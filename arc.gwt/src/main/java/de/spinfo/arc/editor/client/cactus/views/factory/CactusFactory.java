package de.spinfo.arc.editor.client.cactus.views.factory;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

import de.spinfo.arc.editor.client.cactus.Cactus;
import de.spinfo.arc.editor.client.cactus.CactusItem;
import de.spinfo.arc.editor.client.cactus.CactusPopupMenu;
import de.spinfo.arc.editor.client.cactus.views.CactusView;
import de.spinfo.arc.editor.client.cactus.views.ModificationDialogView;
import de.spinfo.arc.editor.client.cactus.views.items.CactusCustomLeafItemWidget;
import de.spinfo.arc.editor.client.cactus.views.items.CactusCustomNodeItemWidget;
import de.spinfo.arc.editor.client.cactus.views.items.CactusCustomRootItemWidget;
import de.spinfo.arc.editor.client.cactus.views.items.CactusCustomUnmodifiableNodeWidget;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;


public interface CactusFactory {
	public CactusCustomLeafItemWidget createCatusLeafItemWidget (int ordinal, String title);
	public CactusCustomNodeItemWidget createCatusNodeItemWidget (int ordinal, String title);
	public CactusCustomRootItemWidget createCatusRootItemWidget (int ordinal, String title);

	public CactusPopupMenu createAndSetPopUpMenu(String pathToLevel, Cactus cactus);
	public Widget createAddToCategoryOption(Types rangeType,  ClickHandler clickHandler);
	public Widget createRemoveFromCategoryOption(Types rangeType, ClickHandler clickHandler);
	
	public Cactus createCactus();
	public CactusView createCactusView(Types modificationRangeType);
	public CactusItem createCactusRootItem();
	CactusItem createCactusCategoryItem();
	CactusItem createCactusLeafItem();
	CactusCustomUnmodifiableNodeWidget createCatusUnmodifiableNodeItemWidget(
			String title);
	public CactusPopupMenu createAndSetRootPopUpMenu(Cactus cactus);
	CactusPopupMenu createAndSetNodePopUpMenu(Cactus cactus);
	CactusPopupMenu createAndSetLeavePopUpMenu(Cactus cactus);
	
	public ModificationDialogView createModificationDialog(Types modificationRangeType);
	HTML createViewPageOption(ClickHandler clickHandler);



}