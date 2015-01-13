package de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget.impl;

import com.google.gwt.user.client.ui.IsWidget;

public interface NewRangeOptionWidget extends IsWidget {

	void setQuestionToUserText(String text);

	void setTypeText(String text);

	String getQuestionToUserText();

	String getTypeText();

}
