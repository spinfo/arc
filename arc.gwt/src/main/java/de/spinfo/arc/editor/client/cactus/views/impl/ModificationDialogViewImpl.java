package de.spinfo.arc.editor.client.cactus.views.impl;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.spinfo.arc.editor.client.cactus.presenter.ModificationDialogViewPresenter;
import de.spinfo.arc.editor.client.cactus.views.ModificationDialogView;
 
public class ModificationDialogViewImpl extends PopupPanel implements ModificationDialogView {
    private static final Binder binder = GWT.create(Binder.class);
    interface Binder extends UiBinder<Widget, ModificationDialogViewImpl> {
    } 
    
    @UiField
    HTMLPanel panel;
     
    @UiField
    Label titleLabel;
    @UiField
    
    Label questionToUserLabel;
    
    @UiField
    TextBox categoryInputTextBox;
    
    @UiField
    TextArea categoryCommentTextArea;
    
    @UiField
    Button  cancelBtn;
//    @UiField
//    Button  okDefaultBtn;
    @UiField
    Button  okBtn;

	private ModificationDialogViewPresenter presenter;
    
    public ModificationDialogViewImpl() {
//    	setSize("100%", "100%");
    	setWidget(binder.createAndBindUi(this));
//    	categoryCommentTextArea.setValue("enter comment for your modification here");
//    	categoryCommentTextArea.setSize("100%", "100%");
    }
    public ModificationDialogViewImpl(String title, String questiontoUser) {
        setWidget(binder.createAndBindUi(this));
        this.titleLabel.setText(title);
        this.questionToUserLabel.setText(questiontoUser);
        initPupUp();
    }
    
    private void initPupUp() {
        setWidget(binder.createAndBindUi(this));
        setSize("100%", "100%");
        setAutoHideEnabled(true);
        setGlassEnabled(true);
        center();
    }
    
    @Override
    public void setTitleLabelText(String titleLabelText) {
    	this.titleLabel.setText(titleLabelText);
    }
    
    @Override   
    public void setQuestionToUserLabelText(String questionToUserLabelText) {
    	this.questionToUserLabel.setText(questionToUserLabelText);
    }
    @Override
    public String getTitleLabelText() {
    	return titleLabel.getText();
    }
    
    @Override   
    public String getQuestionToUserLabelText() {
    	return questionToUserLabel.getText();
    }
    
    @Override   
    public String getCategoryInputTextBoxText() {
    	return categoryInputTextBox.getText();
    }
    
	@Override
	public String getCommentInputTextAreaText() {
		return categoryCommentTextArea.getText();
	}
    
    
	@UiHandler("okBtn")
    void onOkBtnClicked(ClickEvent e) {
		presenter.onDialogOkBtnClicked(e);
    }
    @UiHandler("cancelBtn")
    void onCancelBtnClicked(ClickEvent e) {
    	presenter.onDialogCancelBtnClicked(e);
    }
	@Override
	public void setPresenter(ModificationDialogViewPresenter presenter) {
		this.presenter = presenter;
		
	}

}
