package de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget;

import com.google.gwt.user.client.ui.IsWidget;

public interface TreePageDividerItemWidget extends IsWidget{

	TreePageDividerItemWidget setStart(int start);
	
	TreePageDividerItemWidget setEnd(int end);
	
	int getStart();

	int getEnd();
}
