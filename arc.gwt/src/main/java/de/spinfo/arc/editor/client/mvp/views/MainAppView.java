package de.spinfo.arc.editor.client.mvp.views;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Panel;

import de.spinfo.arc.editor.client.mvp.presenter.MainAppViewPresenter;

/**
 * This view is the composite which wraps the 4 Mainpanels of the app 
 * 
 * @author drival
 *
 */
public interface MainAppView extends IsWidget, HasPresenter<MainAppViewPresenter> {
	
//	void setPresenter(MainAppViewPresenter presenter);
	
	void setHeaderContent(IsWidget widget);
	void setSouthContent(IsWidget widget);
	void setWestContent(IsWidget widget);
	void setCenterContent(IsWidget widget);
	
	Panel getHeaderPanel();
	Panel getSouthPanel();
	Panel getWestPanel();
	Panel getCenterPanel();
	
	
}
