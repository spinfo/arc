package de.spinfo.arc.editor.client.mvp.views;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;

import de.spinfo.arc.editor.client.mvp.presenter.WorkingUnitBrowserFramePresenter;

/**
 * Implementing Views embody the frame of the working unit browser view
 * It consists of a splitpanel which has a west-side for the browser and 
 * a center area for content.
 * 
 * @author drival
 *
 */
public interface WorkingUnitBrowserFrame extends IsWidget, HasPresenter<WorkingUnitBrowserFramePresenter> {

	/**
	 * Retrieves the Panel which should hold the Browser for the
	 * working units in order to let the user choose one to work with
	 * 
	 * @return the panel which holds the content left hand
	 */
	public SimpleLayoutPanel getWestPanel();
	
	/**
	 * Retrieves the Panel which should hold some kind of informationt regarding the
	 * working unit which is currently selected on the corresponding west panel (where a browser for the units should reside)
	 * 
	 * @return the panel which holds the content left hand
	 */
	public SimpleLayoutPanel getCenterPanel();
}
