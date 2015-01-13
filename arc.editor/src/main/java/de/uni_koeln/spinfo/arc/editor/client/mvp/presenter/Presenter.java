package de.uni_koeln.spinfo.arc.editor.client.mvp.presenter;

import com.google.gwt.user.client.ui.HasWidgets;

/**
 * Base Interface all used MVP-Presenters are implementing to get bound to their views
 * 
 * @author David Rival
 *
 */
public interface Presenter {
		/**
		 * Tells the view this presenter is supporting to display itself on the given container
		 * 
		 * @param container the Panel or HasWidget implementation the associated view will display itself
		 */
	   public void go(final HasWidgets container);
	   /**
	    * Gets called after construction of this presenter to bind the corresponding view to this presenter
	    */
	   public void bind();
}
