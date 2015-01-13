package de.uni_koeln.spinfo.arc.editor.client.mvp.views.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.RangeEditorPresenter;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.RangeEditorView;

public class RangeEditorViewImpl extends Composite implements RangeEditorView {

	private static RangeEditorViewImplUiBinder uiBinder = GWT
			.create(RangeEditorViewImplUiBinder.class);

	interface RangeEditorViewImplUiBinder extends
			UiBinder<Widget, RangeEditorViewImpl> {
	}

	public RangeEditorViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		rangeTabPanel.setAnimationVertical(false);

	}

	@UiField
	TabLayoutPanel rangeTabPanel;

	@UiField
	HasWidgets chapterPanel;

	@UiField
	HasWidgets languagePanel;

	private RangeEditorPresenter presenter;

	@Override
	public TabLayoutPanel getRangeTabPanel() {
		return rangeTabPanel;
	}

	@Override
	public void setPresenter(RangeEditorPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public HasWidgets getChapterPanel() {
		return chapterPanel;
	}

	@Override
	public HasWidgets getLanguagePanel() {
		return languagePanel;
	}

}
