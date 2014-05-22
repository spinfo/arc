package de.spinfo.arc.editor.client.cactus.views.items;

import com.google.gwt.user.client.ui.IsWidget;

public interface CactusCustomNodeItemWidget extends IsWidget{
	
	public  int getOrdinal();

	public  String getTitle();
	
	public void setTitle(String title);
	
	public void setOrdinal(int ordinal);
}
