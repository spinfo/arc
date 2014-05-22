package de.spinfo.arc.editor.client.cactus.views;


import com.google.gwt.user.client.ui.IsWidget;

import de.spinfo.arc.editor.client.cactus.presenter.ModificationDialogViewPresenter;

public interface ModificationDialogView extends IsWidget {
	public void setPresenter(ModificationDialogViewPresenter presenter);
	public void setTitleLabelText(String titleLabelText);
	public void setQuestionToUserLabelText(String questionToUserLabelText);
	String getTitleLabelText();
	String getQuestionToUserLabelText();
	String getCategoryInputTextBoxText();
	public String getCommentInputTextAreaText();
}
