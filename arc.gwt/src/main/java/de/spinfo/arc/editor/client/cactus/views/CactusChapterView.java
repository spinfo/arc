package de.spinfo.arc.editor.client.cactus.views;


import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;

import de.spinfo.arc.editor.client.cactus.Cactus;
import de.spinfo.arc.editor.client.cactus.CactusItem;
import de.spinfo.arc.editor.client.cactus.CactusPopupMenu;
import de.spinfo.arc.editor.client.cactus.presenter.CactusChapterViewPresenter;


public interface CactusChapterView extends IsWidget, CactusChapterViewConstants{
	void setPresenter(CactusChapterViewPresenter presenter);

	Cactus getCactus();

//	void setCactus(Cactus cactus);

	CactusItem getRoot();

//	void setRoot(CactusItem root);

//	CactusItem getRemainingUnsorted();

//	void setRemainingUnsorted(CactusItem remainingUnsorted);

	CactusPopupMenu getPagesPopUp();

	HTML getAddPageToChapterOption();

	HTML getRemovePageFromChapterOption();

	HTML getRemoveChapterFromWorkingUnitOption();

	CactusPopupMenu getChapterPopUp();

//	void setPagesPopUp(CactusPopupMenu pagesPopUp);


}
 