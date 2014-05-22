package de.spinfo.arc.editor.client.mvp.views.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.spinfo.arc.editor.client.mvp.presenter.PageEditorDialogPresenter;
import de.spinfo.arc.editor.client.mvp.presenter.PageEditorPresenter;
import de.spinfo.arc.editor.client.mvp.views.PageEditorDialogView;

public class PageEditorDialogViewImpl extends DialogBox  implements PageEditorDialogView {
	/** the maximum allowed charecters to enter in the input text box for the form */
	private static final int FORM_INPUT_MAX_CARACTERS_ALLOWED = 35;
	private static PageEditorDialogViewImplUiBinder uiBinder = GWT
			.create(PageEditorDialogViewImplUiBinder.class);

	interface PageEditorDialogViewImplUiBinder extends
			UiBinder<Widget, PageEditorDialogViewImpl> {
	}

	private PageEditorDialogPresenter presenter;

	public PageEditorDialogViewImpl() {
		super(false, false);
		setWidget(uiBinder.createAndBindUi(this));
		setAutoHideOnHistoryEventsEnabled(true);
		center();
		setText("Edit a word");
		getSaveCoordinatesBtn().setEnabled(false);
		getSaveFormBtn().setEnabled(false);
		setNewPosListBoxItem("V");
		setNewPosListBoxItem("N");
		setNewPosListBoxItem("ADJ");
		setNewPosListBoxItem("ADV");
		formInputTextBox.setMaxLength(FORM_INPUT_MAX_CARACTERS_ALLOWED);
//		prevWordBtn.setText("<<");
//		nextWordBtn.setText(">>");
		
		/*
		 *  set all click and change handlers in order to inform the presenter
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
		
		KeyUpHandler formKeyUpHandler = new KeyUpHandler() {@Override
			public void onKeyUp(KeyUpEvent event) {
				presenter.onSomethingTypedIntoFormInputBox(formInputTextBox.getText());
				
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
				presenter.onCancelClicked();
			}
		};
		this.setCancelButtonClickHandler(cancelHandler);
		
	    KeyUpHandler leftRightKeyHandler = new KeyUpHandler() {
				@Override
				public void onKeyUp(KeyUpEvent event) {
					if (KeyUpEvent.isArrow(event.getNativeKeyCode())) {
						if (event.getNativeKeyCode() == KeyCodes.KEY_LEFT) {
							presenter.onLeftArrowKey();
							event.getNativeEvent().stopPropagation();
							event.getNativeEvent().preventDefault();
						}
						else if (event.getNativeKeyCode() == KeyCodes.KEY_RIGHT) {
							presenter.onRightArrowKey();
							event.getNativeEvent().stopPropagation();
							event.getNativeEvent().preventDefault();
						}
					}
					
				}
			};
		allFocusWrapperPanel.addKeyUpHandler(leftRightKeyHandler);
	}



	@Override 
	public void setPresenter(PageEditorDialogPresenter presenter) {
		this.presenter = presenter;
		
	}
	
	@UiField
	FocusPanel allFocusWrapperPanel;
	@UiField Button prevWordBtn;
	@UiField Button nextWordBtn;
	
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
	
	@UiField Label selectedWordLabel;
	@UiField Label selectedPosLabel;
	
	@UiField Label newFormLabel;
	@UiField TextBox formInputTextBox;
	@UiField Button saveFormBtn;
	
	@UiField Label newPosLabel;
	@UiField ListBox posListBox;
	@UiField Button savePosBtn;
	
	@UiField Label editCoordinatesLabel;
	@UiField Button editCoordinatesBtn;
	@UiField Button saveCoordinatesBtn;
	
	@UiField Button cancelBtn;
	
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
