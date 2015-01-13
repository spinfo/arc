package de.uni_koeln.spinfo.arc.editor.client.mvp.views.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import de.uni_koeln.spinfo.arc.dto.annotation.AnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.AnnotationDto.AnnotationTypes;
import de.uni_koeln.spinfo.arc.dto.annotation.RangeAnnotationDto;
import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.RangeEditorPresenter;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.RangeWidget;

public class RangeWidgetImpl extends Composite implements RangeWidget {

	private static RangeWidgetImplUiBinder uiBinder = GWT
			.create(RangeWidgetImplUiBinder.class);

	interface RangeWidgetImplUiBinder extends UiBinder<Widget, RangeWidgetImpl> {
	}

	public RangeWidgetImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public RangeWidgetImpl(RangeAnnotationDto rangeAnnotation,
			AnnotationDto.AnnotationTypes annotationType) {
		initWidget(uiBinder.createAndBindUi(this));
		this.rangeAnnotation = rangeAnnotation;
		this.annotationType = annotationType;
	}

	@UiField
	Button removeBtn;

	@UiField
	Button editBtn;

	@UiField
	HasText ordinalLabel;

	@UiField
	HasText titleLabel;

	@UiField
	HasText fromIndexLabel;

	@UiField
	HasText toIndexLabel;

	@UiField
	Button fromPageBtn;

	@UiField
	Button toPageBtn;

	private AnnotationTypes annotationType;

	private RangeEditorPresenter presenter;

	private RangeAnnotationDto rangeAnnotation;

	@UiHandler("fromPageBtn")
	void onFromPageBtnClicked(ClickEvent e) {
		presenter.onFromPageBtnClicked(this);
	}

	@UiHandler("toPageBtn")
	void onToPageBtnClicked(ClickEvent e) {
		presenter.onToPageBtnClicked(this);
	}

	@UiHandler("removeBtn")
	void onClick(ClickEvent e) {
		presenter.onRemoveRangeClicked(this);
	}

	@UiHandler("editBtn")
	void onEditRangeClicked(ClickEvent e) {
		presenter.onEditRangeClicked(this);
	}

	@Override
	public void setOrdinal(String ordinal) {
		ordinalLabel.setText(ordinal);
	}

	@Override
	public String getOrdinal() {
		return ordinalLabel.getText();
	}

	@Override
	public void setRangeTitle(String title) {
		titleLabel.setText(title);
	}

	@Override
	public String getRangeTitle() {
		return titleLabel.getText();
	}

	@Override
	public void setFromPage(String fromPage) {
		fromPageBtn.setText(fromPage);
	}

	@Override
	public String getFromPage() {
		return fromPageBtn.getText();
	}

	@Override
	public void setToPage(String toPage) {
		toPageBtn.setText(toPage);
	}

	@Override
	public String getToPage() {
		return toPageBtn.getText();
	}

	@Override
	public void setFromIndex(long fromIndex) {
		fromIndexLabel.setText(fromIndex + "");
	}

	@Override
	public String getFromIndex() {
		return fromIndexLabel.getText();
	}

	@Override
	public void setToIndex(long toIndex) {
		toIndexLabel.setText(toIndex + "");
	}

	@Override
	public String getToIndex() {
		return toIndexLabel.getText();
	}

	@Override
	public void setPresenter(RangeEditorPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public RangeAnnotationDto getRangeAnnotation() {
		return rangeAnnotation;
	}

	@Override
	public AnnotationDto.AnnotationTypes getAnnotationType() {
		return annotationType;
	}
}
