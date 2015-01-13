package de.uni_koeln.spinfo.arc.editor.client.mvp.views.impl;

import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.uni_koeln.spinfo.arc.dto.annotation.PosAnnotationDto;
import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.PageEditorDialogPresenter;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.PageEditorDialogView;

//import java.util.

public class PageEditorDialogViewImpl extends DialogBox implements
		PageEditorDialogView, ChangeHandler {
	/**
	 * the maximum allowed charecters to enter in the input text box for the
	 * form
	 */
	private static final int FORM_INPUT_MAX_CARACTERS_ALLOWED = 35;
	private static PageEditorDialogViewImplUiBinder uiBinder = GWT
			.create(PageEditorDialogViewImplUiBinder.class);

	interface PageEditorDialogViewImplUiBinder extends
			UiBinder<Widget, PageEditorDialogViewImpl> {
	}

	private PageEditorDialogPresenter presenter;

	@Override
	public void setUpWithConstants() {
		posListBox.clear();
		for (int i = 0; i < PosAnnotationDto.PosTags.values().length; i++)
			setNewPosListBoxItem(PosAnnotationDto.PosTags.values()[i]
					.toString());

		hasChanged = false;
	}

	/*
	 * The delimiter which denotes the seperation between the acutal pos options
	 * available for this word and all possible pos tags
	 */
	public static final String POS_DELIM = "______________________";

	@Override
	public void setUpWithPosOptions(Set<String> posOptions) {
		if (posOptions == null)
			throw new IllegalArgumentException("the set must not be null");
		if (posOptions.isEmpty())
			throw new IllegalArgumentException("the set must not be empty");
		posListBox.clear();
		for (String s : posOptions)
			setNewPosListBoxItem(s);

		setNewPosListBoxItem(POS_DELIM);
		for (int i = 0; i < PosAnnotationDto.PosTags.values().length; i++)
			setNewPosListBoxItem(PosAnnotationDto.PosTags.values()[i]
					.toString());

		hasChanged = false;
	}

	@Override
	public void setHasChanged(boolean b) {
		this.hasChanged = b;
	}

	public PageEditorDialogViewImpl() {
		super(false, false);
		setWidget(uiBinder.createAndBindUi(this));
		setAutoHideOnHistoryEventsEnabled(true);
		setAnimationEnabled(true);
		center();
		setText("Edit a word");
		getSaveCoordinatesBtn().setEnabled(false);
		getSaveFormBtn().setEnabled(false);

		/*
		 * register this view as changeHandler in order to update the flag
		 * hasChanged to true if the selection has changed This is used by the
		 * page Editor view if one uses the next and prev buttons. only if
		 * hasChanged is true the selection will be saved
		 */
		setSelectedPosChangeHandler(this);

		for (int i = 0; i < PosAnnotationDto.PosTags.values().length; i++)
			setNewPosListBoxItem(PosAnnotationDto.PosTags.values()[i]
					.toString());

		formInputTextBox.setMaxLength(FORM_INPUT_MAX_CARACTERS_ALLOWED);
		;

		/*
		 * set all click and change handlers in order to inform the presenter
		 */

		/*
		 * for the form
		 */
		ClickHandler saveFormHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.onSaveFormClicked(formInputTextBox.getText());
			}
		};
		this.setSaveFormClickHandler(saveFormHandler);

		KeyUpHandler formKeyUpHandler = new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				presenter.onSomethingTypedIntoFormInputBox(formInputTextBox
						.getText());
			}
		};

		this.setFormInputKeyUpHandler(formKeyUpHandler);

		/*
		 * for the POS-Selection
		 */
		ClickHandler savePosHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				int selectedIdx = posListBox.getSelectedIndex();
				String selectedItemText = posListBox.getItemText(selectedIdx);
				/*
				 * Do nothing if the selection is either the delimiter bar or
				 * the NOT_TAGGED_Default value
				 */
				if (selectedItemText.equals(POS_DELIM)
						|| selectedItemText
								.equals(PosAnnotationDto.PosTags.NOT_TAGGED
										.toString()))
					return;

				presenter.onSavePosClicked(posListBox.getItemText(selectedIdx));
			}
		};
		this.setSavePosClickHandler(savePosHandler);
		/*
		 * for the coordinates
		 */
		ClickHandler editCoordinatesHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.onEditCoordinatesClicked();
			}
		};
		ClickHandler saveCoordinatesHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.onSaveCoordinatesClicked();
			}
		};
		this.setEditCoordinatesClickHandler(editCoordinatesHandler);
		this.setSaveCoordinatesClickHandler(saveCoordinatesHandler);
		/*
		 * For the close Button of the whole dialog
		 */
		ClickHandler cancelHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.onCancelEditWordClicked();
			}
		};
		this.setCancelButtonClickHandler(cancelHandler);
		/*
		 * Below is for handling keypreses if this view is focused - PROBLEM: It
		 * confilcts with ListBox Keyhandlers
		 */
		// KeyUpHandler leftRightKeyHandler = new KeyUpHandler() {
		// @Override
		// public void onKeyUp(KeyUpEvent event) {
		// if (KeyUpEvent.isArrow(event.getNativeKeyCode())) {
		// if (event.getNativeKeyCode() == KeyCodes.KEY_LEFT) {
		// presenter.onLeftArrowKey();
		// event.getNativeEvent().stopPropagation();
		// event.getNativeEvent().preventDefault();
		// }
		// else if (event.getNativeKeyCode() == KeyCodes.KEY_RIGHT) {
		// presenter.onRightArrowKey();
		// event.getNativeEvent().stopPropagation();
		// event.getNativeEvent().preventDefault();
		// }
		// }
		//
		// }
		// };
		// allFocusWrapperPanel.addKeyUpHandler(leftRightKeyHandler);

	}

	@Override
	public void setPresenter(PageEditorDialogPresenter presenter) {
		this.presenter = presenter;

	}

	@UiField
	FocusPanel allFocusWrapperPanel;
	@UiField
	Button prevWordBtn;
	@UiField
	Button nextWordBtn;

	@UiHandler("nextWordBtn")
	public void onNextWordBtnClicked(ClickEvent e) {
		presenter.onRightArrowKey();
	}

	@UiHandler("prevWordBtn")
	public void onPrevWordBtnClicked(ClickEvent e) {
		presenter.onLeftArrowKey();
	}

	@Override
	public Button getPrevWordBtn() {
		return prevWordBtn;
	}

	@Override
	public Button getNextWordBtn() {
		return nextWordBtn;
	}

	@UiField
	Label selectedWordLabel;
	@UiField
	Label selectedPosLabel;

	@UiField
	Label newFormLabel;
	@UiField
	TextBox formInputTextBox;
	@UiField
	Button saveFormBtn;

	@UiField
	Label newPosLabel;
	@UiField
	ListBox posListBox;
	@UiField
	Button savePosBtn;

	@UiField
	Label editCoordinatesLabel;
	@UiField
	Button editCoordinatesBtn;
	@UiField
	Button saveCoordinatesBtn;

	@UiField
	Button cancelBtn;

	@Override
	public void setHeaderText(String text) {
		setText(text);
	}

	@Override
	public void setSelectedWordText(String text) {
		selectedWordLabel.setText(text);
	}

	@Override
	public void setSelectedWordPosText(String text) {
		selectedPosLabel.setText(text);
	}

	/*
	 * FORM specific
	 */
	@Override
	public void setNewformLabelText(String text) {
		newFormLabel.setText(text);
	}

	@Override
	public void setNewformTextBoxText(String text) {
		formInputTextBox.setText(text);
	}

	@Override
	public String getNewformTextBoxText() {
		return formInputTextBox.getText();
	}

	@Override
	public TextBox getFormInputTextBox() {
		return formInputTextBox;
	}

	@Override
	public void setSaveFormClickHandler(ClickHandler handler) {
		saveFormBtn.addClickHandler(handler);
	}

	private void setFormInputKeyUpHandler(KeyUpHandler formKeyUpHandler) {
		formInputTextBox.addKeyUpHandler(formKeyUpHandler);

	}

	@Override
	public Button getSaveFormBtn() {
		return saveFormBtn;
	}

	/*
	 * POS specific
	 */
	@Override
	public void setNewPosLabelText(String text) {
		newPosLabel.setText(text);
	}

	@Override
	public void setNewPosListBoxItem(String newItemsText) {
		posListBox.addItem(newItemsText);
	}

	@Override
	public void setSelectedPosChangeHandler(ChangeHandler changeHandler) {
		posListBox.addChangeHandler(changeHandler);
	}

	private boolean hasChanged = false;

	@Override
	public void onChange(ChangeEvent event) {
		System.out.println("OnChange ");
		hasChanged = true;
	}

	@Override
	public boolean hasChanged() {
		return hasChanged;
	}

	@Override
	public ListBox getPosListBox() {
		return posListBox;
	}

	@Override
	public void setSavePosClickHandler(ClickHandler handler) {
		savePosBtn.addClickHandler(handler);
	}

	/*
	 * Coordinates specific
	 */
	@Override
	public void setCoordinatesLabelText(String text) {
		editCoordinatesLabel.setText(text);
	}

	@Override
	public void setEditCoordinatesClickHandler(ClickHandler handler) {
		editCoordinatesBtn.addClickHandler(handler);
	}

	@Override
	public void setSaveCoordinatesClickHandler(ClickHandler handler) {
		saveCoordinatesBtn.addClickHandler(handler);
	}

	@Override
	public Button getEditCoordinatesBtn() {
		return editCoordinatesBtn;
	}

	@Override
	public Button getSaveCoordinatesBtn() {
		return saveCoordinatesBtn;
	}

	@Override
	public void setCancelButtonClickHandler(ClickHandler handler) {
		cancelBtn.addClickHandler(handler);
	}

	@Override
	public DialogBox asDialogBox() {
		return this;
	}



}
