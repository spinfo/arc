package de.spinfo.arc.editor.client.mvp.views;

import com.google.gwt.user.client.ui.ListBox;

import de.spinfo.arc.editor.client.mvp.presenter.Presenter;


public interface HasPresenter<T extends Presenter> {
	void setPresenter(T presenter);

}
