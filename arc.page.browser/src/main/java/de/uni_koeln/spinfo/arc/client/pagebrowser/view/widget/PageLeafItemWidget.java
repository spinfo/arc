package de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget;
 
import com.google.gwt.user.client.ui.IsWidget;

public interface PageLeafItemWidget extends IsWidget {

	public abstract int getOrdinal();

	public abstract String getTitle();
	
	public void setTitle(String title);
	
	public void setOrdinal(int ordinal);
	
}