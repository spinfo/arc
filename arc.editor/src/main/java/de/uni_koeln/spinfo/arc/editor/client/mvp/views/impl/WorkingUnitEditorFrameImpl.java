package de.uni_koeln.spinfo.arc.editor.client.mvp.views.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.uni_koeln.spinfo.arc.editor.client.mvp.Tokens;
import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.WorkingUnitEditorFramePresenter;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.WorkingUnitEditorFrame;

public class WorkingUnitEditorFrameImpl extends Composite implements
		WorkingUnitEditorFrame {

	private static WorkingUnitEditorFrameImplUiBinder uiBinder = GWT
			.create(WorkingUnitEditorFrameImplUiBinder.class);

	interface WorkingUnitEditorFrameImplUiBinder extends
			UiBinder<Widget, WorkingUnitEditorFrameImpl> {
	}

	public WorkingUnitEditorFrameImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	HasWidgets cactusPanel;

	@UiField
	HasWidgets centerPanel;

	@UiField
	HasWidgets mainRangesTabPanel;

	@UiField
	Button backToBrowserBtn;

	private WorkingUnitEditorFramePresenter presenter;

	@Override
	public void setPresenter(WorkingUnitEditorFramePresenter presenter) {
		this.presenter = presenter;
	}

	@UiHandler("backToBrowserBtn")
	public void onGoBackToBrowser(ClickEvent e) {
		History.newItem(Tokens.WORKING_UNIT_BROWSER, true);
	}

	@Override
	public HasWidgets getChapterPanel() {
		return cactusPanel;
	}

	@Override
	public HasWidgets getRangesPanel() {
		return mainRangesTabPanel;
	}

	@Override
	public HasWidgets getCenterPanel() {
		return centerPanel;
	}

}
