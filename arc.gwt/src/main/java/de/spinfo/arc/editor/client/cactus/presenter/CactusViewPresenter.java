package de.spinfo.arc.editor.client.cactus.presenter;

import de.spinfo.arc.editor.client.cactus.CactusListener;
import de.spinfo.arc.editor.client.cactus.views.CactusChapterViewConstants;

public interface CactusViewPresenter extends Presenter, CactusListener, CactusChapterViewConstants{
	void onAddClicked();
	void onRemoveClicked();
	void onShowInfoClicked();
//	void setUpCactusFromModel();
	void onViewPageClicked();
}
