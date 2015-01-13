package de.uni_koeln.spinfo.arc.editor.client.mvp.views;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;

public interface MainFrameView extends IsWidget {
	
	/**
	 * Retrieves the Panel where all the other panels/widgets get added to and removed from
	 * across the whole application
	 * 
	 * @return the panel where all the other panels/widgets get added to and removed from
	 */
	public SimpleLayoutPanel getContentPanel();
	
}
