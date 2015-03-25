package de.uni_koeln.spinfo.arc.client.pagebrowser.view.dialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;


public class PageEditorDialogViewImpl extends DialogBox {
    /**
     * the maximum allowed charecters to enter in the input text box for the form
     */
    private static final int FORM_INPUT_MAX_CARACTERS_ALLOWED = 35;
    private static PageEditorDialogViewImplUiBinder uiBinder = GWT
            .create(PageEditorDialogViewImplUiBinder.class);

    interface PageEditorDialogViewImplUiBinder extends
            UiBinder<Widget, PageEditorDialogViewImpl> {
    }

//	private PageEditorDialogPresenter presenter;

    private static String[] TAG_SET = {
            // Verbs
            "V_AVAIR", "V_ESSER", "V_MOD", "V_PP", "V_GVRB", "V_CLIT",
            // Nouns
            "NN", "NN_P",
            // Articles
            "ART",
            // Articles fusioned with Nouns
            "ART_NN",
            // Articles fusioned with Pronouns
            "ART_ADJ",
            // Articles with Adjectives
            "ART_PRON",
            // Prepositions
            "PREP",
            // Prepositions fusioned with Articles
            "PREP_ART",
            // Prepositions fusioned with Pronouns
            "PREP_PRON",
            // Prepositions with Adverbs
            "PREP_ADV",
            // Adjectives
            "ADJ", "ADJ_DEM", "ADJ_IND", "ADJ_IES", "ADJ_POS", "ADJ_NUM",
            // Adjectives fusioned with Nouns
            "ADJ_NN",
            // Conjunctions
            "CONJ_C", "CONJ_S",
            // Conjunctions fusioned with Pronouns
            "CONJ_PRON",
            // Conjunctions with Art
            "CONJ_ART",
            // Conjunctions with Verbs
            "CONJ_GVRB",
            // Adverbs
            "ADV",
            // Interjections
            "INT",
            // Numbers
            "C_NUM",
            // Pronouns
            "PRON_PER", "PRON_REL", "PRON_DEM", "PRON_IND", "PRON_IES",
            "PRON_POS", "PRON_REF",
            // Particles
            "PART",
            // Symbols
            "NULL",
            // PunctuationMarks
            "P_EOS", "P_APO", "P_OTH",
            // NOT TAGGED
            "NOT_TAGGED"

    };

    public PageEditorDialogViewImpl() {
        super(false, false);
        setWidget(uiBinder.createAndBindUi(this));
        setAutoHideOnHistoryEventsEnabled(true);
        setAnimationEnabled(true);
        center();
        setText("Edit a word");
//		getSaveCoordinatesBtn().setEnabled(false);
//		getSaveFormBtn().setEnabled(false);

//		for (int i = 0; i < TAG_SET.length; i++)
//			setNewPosListBoxItem(TAG_SET[i]);
//		setNewPosListBoxItem("N");
//		setNewPosListBoxItem("ADJ");
//		setNewPosListBoxItem("ADV");
//		formInputTextBox.setMaxLength(FORM_INPUT_MAX_CARACTERS_ALLOWED);
//		prevWordBtn.setText("<<");
//		nextWordBtn.setText(">>");

		/*
		 *  set all click and change handlers in order to inform the presenter
		 */

		/*
		 * for the form 
		 */
//		ClickHandler saveFormHandler = new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				presenter.onSaveFormClicked(formInputTextBox.getText());
//			}
//		};
//		this.setSaveFormClickHandler(saveFormHandler);
//		
//		KeyUpHandler formKeyUpHandler = new KeyUpHandler() {@Override
//			public void onKeyUp(KeyUpEvent event) {
//				presenter.onSomethingTypedIntoFormInputBox(formInputTextBox.getText());
//			}
//		};
//		
//		this.setFormInputKeyUpHandler(formKeyUpHandler);
//		
//		/*
//		 * for the POS-Selection 
//		 */
//		ClickHandler savePosHandler = new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				int selectedIdx = posListBox.getSelectedIndex();
//				presenter.onSavePosClicked(posListBox.getItemText(selectedIdx));
//			}
//		};
//		this.setSavePosClickHandler(savePosHandler);
//		/*
//		 * for the coordinates
//		 */
//		ClickHandler editCoordinatesHandler = new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				presenter.onEditCoordinatesClicked();
//			}
//		};
//		ClickHandler saveCoordinatesHandler = new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				presenter.onSaveCoordinatesClicked();
//			}
//		};
//		this.setEditCoordinatesClickHandler(editCoordinatesHandler);
//		this.setSaveCoordinatesClickHandler(saveCoordinatesHandler);
//		/*
//		 * For the close Button of the whole dialog
//		 */
//		ClickHandler cancelHandler = new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				presenter.onCancelEditWordClicked();
//			}
//		};
//		this.setCancelButtonClickHandler(cancelHandler);
//		
//	    KeyUpHandler leftRightKeyHandler = new KeyUpHandler() {
//				@Override
//				public void onKeyUp(KeyUpEvent event) {
//					if (KeyUpEvent.isArrow(event.getNativeKeyCode())) {
//						if (event.getNativeKeyCode() == KeyCodes.KEY_LEFT) {
//							presenter.onLeftArrowKey();
//							event.getNativeEvent().stopPropagation();
//							event.getNativeEvent().preventDefault();
//						}
//						else if (event.getNativeKeyCode() == KeyCodes.KEY_RIGHT) {
//							presenter.onRightArrowKey();
//							event.getNativeEvent().stopPropagation();
//							event.getNativeEvent().preventDefault();
//						}
//					}
//					
//				}
//			};
//		allFocusWrapperPanel.addKeyUpHandler(leftRightKeyHandler);
//	}
//
//
//
//	@Override 
//	public void setPresenter(PageEditorDialogPresenter presenter) {
//		this.presenter = presenter;
//		
//	}
//	
//	@UiField
//	FocusPanel allFocusWrapperPanel;
//	@UiField Button prevWordBtn;
//	@UiField Button nextWordBtn;
//	
//	@UiHandler("nextWordBtn")
//	public void onNextWordBtnClicked(ClickEvent e) {
//		presenter.onRightArrowKey();
//	}
//	@UiHandler("prevWordBtn")
//	public void onPrevWordBtnClicked(ClickEvent e) {
//		presenter.onLeftArrowKey();
//	}
//	
//	@Override
//	public Button getPrevWordBtn() {
//		return prevWordBtn;
//	}
//	@Override
//	public Button getNextWordBtn() {
//		return nextWordBtn;
//	}
//	
//	@UiField Label selectedWordLabel;
//	@UiField Label selectedPosLabel;
//	
//	@UiField Label newFormLabel;
//	@UiField TextBox formInputTextBox;
//	@UiField Button saveFormBtn;
//	
//	@UiField Label newPosLabel;
//	@UiField ListBox posListBox;
//	@UiField Button savePosBtn;
//	
//	@UiField Label editCoordinatesLabel;
//	@UiField Button editCoordinatesBtn;
//	@UiField Button saveCoordinatesBtn;
//	
//	@UiField Button cancelBtn;
//	
//	@Override 
//	public void setHeaderText(String text) {
//		 setText(text);
//	}
//	
//	@Override 
//	public void setSelectedWordText(String text) {
//		selectedWordLabel.setText(text);
//	}
//	@Override 
//	public void setSelectedWordPosText(String text) {
//		selectedPosLabel.setText(text);
//	}
//	/*
//	 * FORM specific
//	 */
//	@Override 
//	public void setNewformLabelText(String text) {
//		newFormLabel.setText(text);
//	}
//	@Override 
//	public void setNewformTextBoxText(String text) {
//		formInputTextBox.setText(text);
//	}
//	@Override 
//	public String getNewformTextBoxText() {
//		return formInputTextBox.getText();
//	}
//	
//	@Override 
//	public TextBox getFormInputTextBox() {
//		return formInputTextBox;
//	}
//	
//	@Override 
//	public void setSaveFormClickHandler(ClickHandler handler) {
//		saveFormBtn.addClickHandler(handler);
//	}
//	
//	private void setFormInputKeyUpHandler(KeyUpHandler formKeyUpHandler) {
//		formInputTextBox.addKeyUpHandler(formKeyUpHandler);
//		
//	}
//	
//	@Override 
//	public Button getSaveFormBtn() {
//		return saveFormBtn;
//	}
//	/*
//	 * POS specific
//	 */
//	@Override 
//	public void setNewPosLabelText(String text) {
//		newPosLabel.setText(text);
//	}
//	@Override 
//	public void setNewPosListBoxItem(String newItemsText) {
//		posListBox.addItem(newItemsText);
//	}
//	@Override 
//	public void setSelectedPosChangeHandler(ChangeHandler changeHandler) {
//		posListBox.addChangeHandler(changeHandler);
//	}
//	
//	@Override 
//	public ListBox getPosListBox() {
//		return posListBox;
//	}
//	
//	@Override 
//	public void setSavePosClickHandler(ClickHandler handler) {
//		savePosBtn.addClickHandler(handler);
//	}
//	/*
//	 * Coordinates specific
//	 */
//	@Override 
//	public void setCoordinatesLabelText(String text) {
//		editCoordinatesLabel.setText(text);
//	}
//	@Override 
//	public void setEditCoordinatesClickHandler(ClickHandler handler) {
//		editCoordinatesBtn.addClickHandler(handler);
//	}
//	@Override 
//	public void setSaveCoordinatesClickHandler(ClickHandler handler) {
//		saveCoordinatesBtn.addClickHandler(handler);
//	}
//	@Override 
//	public Button getEditCoordinatesBtn() {
//		return editCoordinatesBtn;
//	}
//	@Override 
//	public Button getSaveCoordinatesBtn() {
//		return saveCoordinatesBtn;
//	}
//	
//	@Override 
//	public void setCancelButtonClickHandler(ClickHandler handler) {
//		cancelBtn.addClickHandler(handler);
//	}
//
//	@Override 
//	public DialogBox asDialogBox() {
//		return this;
//	}
    }

}
