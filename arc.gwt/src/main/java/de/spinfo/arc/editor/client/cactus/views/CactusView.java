package de.spinfo.arc.editor.client.cactus.views;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import de.spinfo.arc.editor.client.cactus.Cactus;
import de.spinfo.arc.editor.client.cactus.CactusItem;
import de.spinfo.arc.editor.client.cactus.CactusPopupMenu;
import de.spinfo.arc.editor.client.cactus.presenter.CactusViewPresenter;
import de.spinfo.arc.editor.shared.model3.modification.ModificationRange;


public interface CactusView extends IsWidget {
	
	void setPresenter(CactusViewPresenter presenter);
	
	public void setRootWidget(String title, int ordinal);
	
	public Cactus getCactus();

	public CactusItem getUnmodifiableCategoryItem();

	public CactusItem getRootItem();

	public CactusPopupMenu getLeavesPopUp();

	public Widget getRemoveFromCategoryOption();

	public Widget getAddToCategoryOption();

	void appendToUnodifiableOverviewNode(String title, int ordinal,
			ModificationRange userObject);

	void createNewCategoryAndAppendLeaves(String titleOfCategory, ModificationRange categoryRange,
			List<ModificationRange> pageRanges);

	void removeAllButUncategorized();
}
