package de.spinfo.arc.editor.client.cactus.presenter;

import com.google.gwt.event.dom.client.ClickEvent;

public interface ModificationDialogViewPresenter extends Presenter {
	public void onDialogOkBtnClicked(ClickEvent e);
	public void onDialogCancelBtnClicked(ClickEvent e);
}
