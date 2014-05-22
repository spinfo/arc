package de.spinfo.arc.editor.client.cactus.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.TreeItem;

import de.spinfo.arc.editor.client.cactus.CactusListener;
import de.spinfo.arc.editor.client.cactus.CactusPopupMenu;
import de.spinfo.arc.editor.client.cactus.views.CactusChapterViewConstants;




public interface CactusChapterViewPresenter extends Presenter, CactusListener, CactusChapterViewConstants {
	
	
//	void onPopup(String level, CactusPopupMenu menu, TreeItem[] treeItems);
//	void onSelect(String level, TreeItem[] treeItems);
//	void onExpand(TreeItem item);
//	void onCollapse(TreeItem item);
	
	void onAddClicked();
	void onRemoveClicked();
	void onShowInfoClicked();
	
}
