package de.spinfo.arc.editor.client.cactus;

//import org.cafesip.gwtcomp.client.ui.PopupMenu;
//import org.cafesip.gwtcomp.client.utils.HTMLHelper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CactusPopupMenu extends PopupPanel {
	   private CactusPopupMenu parent = null;

	    private VerticalPanel panel = new VerticalPanel();

	    private CactusPopupMenu openChildMenu = null;

	    /**
	     * A constructor for this class.
	     * 
	     */
	    public CactusPopupMenu()
	    {
	        super(true);
	        setStyleName("gwtcomp-PopupMenu");

	        panel = new VerticalPanel();
	        panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
	        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
	        setWidget(panel);

	    }

	    /**
	     * Adds a child menu item that is a cascaded menu.
	     * 
	     * @param widget
	     *            normally a HTML or Label object is used to print the menu item
	     *            name.
	     * @param cascadedMenu
	     *            the popup menu that is popped up when the user mouses over the
	     *            widget.
	     * 
	     */
	    public void addMenuItem(Widget widget, CactusPopupMenu cascadedMenu)
	    {
	        class ItemSelectionMouseoverHandler implements MouseOverHandler
	        {
	            private CactusPopupMenu cascadedMenu;
	            private CactusPopupMenu parent;
	            private FocusPanel panel;

	            public ItemSelectionMouseoverHandler(CactusPopupMenu parent, FocusPanel panel,
	                    CactusPopupMenu cascadedMenu)
	            {
	                this.parent = parent;
	                this.panel = panel;
	                this.cascadedMenu = cascadedMenu;
	            }


	            public void onMouseOver(MouseOverEvent event)
	            {
	                Widget sender = (Widget) event.getSource();
	                panel.addStyleName("gwtcomp-PopupMenu-MenuItem-MouseIn");

	                // hide the previous cascaded menu
	                if (parent.openChildMenu != null)
	                {
	                    parent.openChildMenu.hide();
	                    parent.openChildMenu = null;
	                }

	                // open the cascaded menu if one is available
	                if (cascadedMenu != null)
	                {
	                    int top = sender.getAbsoluteTop();
	                    int left = sender.getAbsoluteLeft()
	                            + sender.getOffsetWidth();
	                    cascadedMenu.setPopupPosition(left, top);
	                    cascadedMenu.show();
	                    parent.openChildMenu = cascadedMenu;
	                }
	            }
	        }

	        class ItemSelectionMouseoutHandler implements MouseOutHandler
	        {
	            private FocusPanel panel;
	 
	            public ItemSelectionMouseoutHandler(FocusPanel panel)
	            {
	                this.panel = panel;
	             }

	            public void onMouseOut(MouseOutEvent event)
	            {
	                panel.removeStyleName("gwtcomp-PopupMenu-MenuItem-MouseIn");
	            }
	        }

	        HorizontalPanel w = new HorizontalPanel();
	        w.addStyleName("gwtcomp-PopupMenu-MenuItem");
	        w.setWidth("100%");
	        w.add(widget);
	        w.setCellHorizontalAlignment(widget, HasHorizontalAlignment.ALIGN_LEFT);
	        w.setCellVerticalAlignment(widget, HasVerticalAlignment.ALIGN_MIDDLE);

	        // add spacing
	        w.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));

	        if (cascadedMenu != null)
	        {
	            Image im = new Image(GWT.getModuleBaseURL()
	                    + "gwtcomp-icons/right-arrow.png");
	            w.add(im);
	            w
	                    .setCellHorizontalAlignment(im,
	                            HasHorizontalAlignment.ALIGN_RIGHT);
	            w.setCellVerticalAlignment(im, HasVerticalAlignment.ALIGN_MIDDLE);

	            cascadedMenu.parent = this;
	        }

	        FocusPanel fp = new FocusPanel(w);
	        panel.add(fp);
	        fp.setWidth("100%");
	        fp.addStyleName("gwtcomp-PopupMenu-MenuItem");

	        fp.addMouseOverHandler(new ItemSelectionMouseoverHandler(this, fp, cascadedMenu));
	        fp.addMouseOutHandler(new ItemSelectionMouseoutHandler(fp));
	    }

	    /**
	     * Add a child menu item.
	     * 
	     * @param widget
	     *            normally a HTML segment or a text lable is passed but other
	     *            input widgets can be passed as well.
	     */
	    public void addMenuItem(Widget widget)
	    {
	        addMenuItem(widget, null);
	    }

	    /**
	     * Removes a child menu at the given index.
	     * 
	     * @param index
	     *            index
	     */
	    public void removeMenuItem(int index)
	    {
	        panel.remove(index);
	    }

	    /**
	     * Add a separator item.
	     */
	    public void addSeparator()
	    {
//	    	panel.add(new HTML(HTMLHelper.hr("#AAAAAA")));
	        panel.add(new HTML("<hr  style=\"color: #AAAAAA ;\" />" ));
	    }

	    /**
	     * Hides the popup menu. In case the menu is a cascaded menu and has
	     * parents, the parents are hidden as well.
	     */
	    public void hideAll()
	    {
	        this.hide();
	        CactusPopupMenu parent = this.parent;
	        while (parent != null)
	        {
	            parent.hide();
	            parent = parent.parent;
	        }

	        // Don't leave any menu items selected
	        int numItems = panel.getWidgetCount();
	        for (int i = 0; i < numItems; i++)
	        {
	            panel.getWidget(i).removeStyleName(
	                    "gwtcomp-PopupMenu-MenuItem-MouseIn");
	        }
	    }

	    /**
	     * Gets the root menu for a given popup menu. Cascaded menus have parents
	     * and possibly grand-parents. This method iterates through the chain and
	     * returns the root popup menu.
	     * 
	     * @param menu
	     *            a given popup menu object
	     * @return the root popup menu
	     */
	    public static CactusPopupMenu getRootMenu(CactusPopupMenu menu)
	    {
	        CactusPopupMenu element = menu;
	        CactusPopupMenu parent = element.parent;
	        while (parent != null)
	        {
	            element = parent;
	            parent = element.parent;
	        }

	        return element;
	    }
}
