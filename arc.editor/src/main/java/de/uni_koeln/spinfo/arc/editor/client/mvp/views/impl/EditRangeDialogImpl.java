package de.uni_koeln.spinfo.arc.editor.client.mvp.views.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.uni_koeln.spinfo.arc.dto.annotation.AnnotationDto.AnnotationTypes;
import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.EditRangeDialogPresenter;

public class EditRangeDialogImpl extends DialogBox {

	private static EditRangeDialogImplUiBinder uiBinder = GWT
			.create(EditRangeDialogImplUiBinder.class);

	interface EditRangeDialogImplUiBinder extends
			UiBinder<Widget, EditRangeDialogImpl> {
	}

	private Button cancelBtn = new Button("cancel");
	private Button proceedToEndBtn = new Button("proceed and edit end");
	private Button proceedToSaveBtn = new Button("proceed and edit Title");
	private Button saveRangeBtn = new Button("save range");

	private final EditRangeDialogPresenter presenter;

	public EditRangeDialogImpl(final EditRangeDialogPresenter presenter) {
		super(false, false);
		this.presenter = presenter;
		setWidget(uiBinder.createAndBindUi(this));
		setAutoHideOnHistoryEventsEnabled(true);
		setAnimationEnabled(true);
		center();
		init();

		/* Set the clickhandlers in order to change state */
		ClickHandler proceedToEndHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.onRangeStartSelected();
				proceedToEnd();
			}
		};
		proceedToEndBtn.addClickHandler(proceedToEndHandler);

		ClickHandler proceedToSaveHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.onRangeEndSelected();
				proceedToSave();
			}
		};
		proceedToSaveBtn.addClickHandler(proceedToSaveHandler);

		ClickHandler saveRangeHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.onSaveRangeClicked();
			}
		};
		saveRangeBtn.addClickHandler(saveRangeHandler);

		ClickHandler cancelHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.onCancelRangeClicked();
			}
		};
		cancelBtn.addClickHandler(cancelHandler);
	}

	public void init() {
		setText("Steps left to create new range: 3");

		rangeTitleTextBox.setVisible(false);
		rangeTitleListBox.setVisible(false);

		questionToUserLabel.setVisible(false);
		buttonContainer.clear();
		proceedToEndBtn.setText("proceed and edit end");
		buttonContainer.add(proceedToEndBtn);
		buttonContainer.add(cancelBtn);
		newStartOfRangeLabel.setText("select a word as start of range type");
		selectedWordLabel.setText("-please select a start word-");
		upperLine.setVisible(true);
	}

	public void proceedToEnd() {
		setText("Steps left to create new range: 2");
		rangeTitleTextBox.setVisible(false);
		rangeTitleListBox.setVisible(false);

		questionToUserLabel.setVisible(false);
		rangeTitleListBox.setVisible(false);
		buttonContainer.clear();
		buttonContainer.add(proceedToSaveBtn);
		buttonContainer.add(cancelBtn);
		newStartOfRangeLabel.setText("select a word as end of range type");
		selectedWordLabel.setText("-please select an end word-");
		upperLine.setVisible(true);
	}

	public void proceedToSave() {
		setText("Steps left to create new range: 1");
		questionToUserLabel.setVisible(true);
		if (this.getAnnotationType().equals(AnnotationTypes.LANGUAGE_RANGE)) {
			rangeTitleListBox.setVisible(true);
			typeOfRangeLabel.setText("please select a language and click save");

		} else {
			rangeTitleTextBox.setVisible(true);
			typeOfRangeLabel.setText("please choose a title and click save");
		}

		buttonContainer.clear();
		buttonContainer.add(saveRangeBtn);
		buttonContainer.add(cancelBtn);
		newStartOfRangeLabel.setText("");
		selectedWordLabel.setText("");
		upperLine.setVisible(false);
	}

	@UiField
	HasWidgets buttonContainer;

	@UiField
	Label questionToUserLabel;

	@UiField
	TextBox rangeTitleTextBox;

	@UiField
	ListBox rangeTitleListBox;

	@UiField
	HasText newStartOfRangeLabel;
	@UiField
	HasText typeOfRangeLabel;
	@UiField
	HasText selectedWordLabel;

	@UiField
	Panel upperLine;
	// @UiField
	// Button saveRangeBtn;
	// @UiField
	// Button cancelBtn;
	private AnnotationTypes annotationType;

	// @Override
	public TextBox getCategoryInputTextBox() {
		return rangeTitleTextBox;
	}

	// @Override
	public String getCategoryText() {
		if (this.getAnnotationType().equals(AnnotationTypes.LANGUAGE_RANGE)) {
			int selectedIdx = rangeTitleListBox.getSelectedIndex();
			if (selectedIdx != -1)
				return rangeTitleListBox.getItemText(selectedIdx);
			else
				return "Auters";
		} else {
			if (!rangeTitleTextBox.getText().isEmpty())
				return rangeTitleTextBox.getText();
			else
				return "Unnamed";
		}
	}

	// @Override
	// public String getCategoryInputTextBoxText() {
	// return categoryInputTextBox.getText();
	// }

	// @Override
	public void setSelectedWordText(String text) {
		selectedWordLabel.setText(text);
	}

	public Button getProceedToEndBtn() {
		return proceedToEndBtn;
	}

	public Button getProceedToSaveBtn() {
		return proceedToSaveBtn;
	}

	public Button getSaveRangeBtn() {
		return saveRangeBtn;
	}

	// @Override
	// public Button getSaveRangeBtn() {
	// return saveRangeBtn;
	// }

	public void setTypeOfRange(AnnotationTypes type) {
		typeOfRangeLabel.setText(type.toString());
		this.annotationType = type;
	}

	public AnnotationTypes getAnnotationType() {
		return annotationType;
	}
}
