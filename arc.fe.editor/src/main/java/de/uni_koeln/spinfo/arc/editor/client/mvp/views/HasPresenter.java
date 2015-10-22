package de.uni_koeln.spinfo.arc.editor.client.mvp.views;

import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.Presenter;

public interface HasPresenter<T extends Presenter> {
	
	void setPresenter(T presenter);

}
