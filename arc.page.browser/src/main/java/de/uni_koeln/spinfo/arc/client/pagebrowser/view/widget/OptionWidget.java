package de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;

public interface OptionWidget extends IsWidget, HasClickHandlers {

	public abstract void setQuestionToUserText(String text);

	public abstract void setTypeText(String text);

	public abstract String getQuestionToUserText();

	public abstract String getTypeText();

}