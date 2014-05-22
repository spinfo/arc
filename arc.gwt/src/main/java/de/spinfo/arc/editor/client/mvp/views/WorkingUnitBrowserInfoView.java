package de.spinfo.arc.editor.client.mvp.views;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import de.spinfo.arc.editor.client.mvp.presenter.WorkingUnitBrowserFramePresenter;
import de.spinfo.arc.editor.client.mvp.presenter.WorkingUnitBrowserInfoViewPresenter;

/**
 * Views implementing this embody a general info node of some kind
 * The view consists of a header with dedicated Label to display the header text
 * Convenient Methods allow to quickly change the text or to add widgets to the header panel.
 * The content area is formated/structured alike.
 * 
 * @author drival
 *
 */
public interface WorkingUnitBrowserInfoView extends IsWidget, HasPresenter<WorkingUnitBrowserInfoViewPresenter> {
	/**
	 * Adds a widget to the header panel. This could be a f.i. a button with some kind
	 * of functionality like 'edit'. It can also be a another panel with other widgets in it.
	 * 
	 * @param widget the widget to be added to the header panel
	 */
	void addToHeaderPanel(Widget widget);
	/**
	 * Adds a widget to the content panel. This could be a f.i. a button with some kind
	 * of functionality like 'edit'. It can also be a another panel with other widgets in it.
	 * 
	 * @param widget the widget to be added to the header panel
	 */
	void addToContentPanel(Widget widget);
	
	/**
	 * Sets the text of the Label displaying the Header text
	 * 
	 * @param headerText the text of the Label displaying the Header text
	 */
	void setHeaderText(String headerText);
	
	/**
	 * Sets the text of the Label displaying the content text
	 * 
	 * @param contentText the text of the Label displaying the content text
	 */
	void setContentText(String contentText); 

	/**
	 * Clears/removes all child widgets of the Header panel except the Label
	 * which can hold the title text
	 */
	void clearHeaderPanelWidgets();
	/**
	 * Clears/removes all child widgets of the content panel except the Label
	 * which can hold the content text
	 */
	void clearContentPanelWidgets();

	/**
	 * Get the Panel of the header area
	 * @return the Panel of the header area
	 */
	HasWidgets getHeaderPanel();

	/**
	 * Get the Panel of the content area
	 * @return the Panel of the content area
	 */
	HasWidgets getContentPanel();
	
	/**
	 * Get the widget which is responsible for displaying the header text (usually a Label)
	 * 
	 * @return the widget which is responsible for displaying the header text (usually a Label)
	 */
	HasText getHeaderTextHolder();
	
	/**
	 *  Get the widget which is responsible for displaying the header text (usually a Label)
	 * 
	 * @return the widget which is responsible for displaying the header text (usually a Label)
	 */
	HasText getContentTextHolder();
	
	/**
	 * Clears all content except the initial panels and headers and labels.
	 * All textual content is cleared as well
	 */
	void clearAllContent();
	
}
