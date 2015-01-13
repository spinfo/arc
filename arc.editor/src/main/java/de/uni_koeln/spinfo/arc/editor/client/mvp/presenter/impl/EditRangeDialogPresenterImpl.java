package de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.impl;

import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.EditRangeDialogPresenter;

public class EditRangeDialogPresenterImpl implements EditRangeDialogPresenter {

	public void onRangeStartSelected() {
			System.out.println(" DUMMY of: " + this.getClass());		
	}

	public void onRangeEndSelected() {
		System.out.println(" DUMMY of: " + this.getClass());		
	}

	public void onSaveRangeClicked() {
		System.out.println(" DUMMY of: " + this.getClass());		
	}

	public void onCancelRangeClicked() {
		System.out.println(" DUMMY of: " + this.getClass());		
	}

}
