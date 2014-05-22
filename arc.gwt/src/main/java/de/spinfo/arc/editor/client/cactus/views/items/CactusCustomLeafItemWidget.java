package de.spinfo.arc.editor.client.cactus.views.items;
 
import com.google.gwt.user.client.ui.IsWidget;

public interface CactusCustomLeafItemWidget extends IsWidget {

	public abstract int getOrdinal();

	public abstract String getTitle();
	
	public void setTitle(String title);
	
	public void setOrdinal(int ordinal);
	
}