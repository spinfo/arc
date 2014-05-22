package de.spinfo.arc.editor.client.cactus;

//import org.cafesip.gwtcomp.client.ui.PopupMenu;
//import org.cafesip.gwtcomp.client.ui.SuperTree;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class CactusItem extends TreeItem {
	
	   private CactusPopupMenu contextMenu;

	    private int type;

	    private Object userObject;

	    /**
	     *  A constructor for this class.
	     * 
	     *  @param type type of the object. <b>The object type must be greater than zero as zero is
	     *  a reserved type.</b>
	     */
	    public CactusItem(int type)
	    {
	        super();
	        this.type = type;
	    }

	    /**
	     * A constructor for this class.
	     * 
	     * @param html tree item rendition using HTML.
	     * @param type type of the object. <b>The object type must be greater than zero as zero is
	     *  a reserved type.</b>
	     */
	    public CactusItem(String html, int type)
	    {
	        super(new HTML(html));
	        this.type = type;
	    }

	    /**
	     * A constructor for this class.
	     * 
	     * @param widget widget rendition of the tree item.
	     * @param type type of the object. <b>The object type must be greater than zero as zero is
	     *  a reserved type.</b>
	     */
	    public CactusItem(Widget widget, int type)
	    {
	        super(widget);
	        this.type = type;
	    }

	    /**
	     * @see com.google.gwt.user.client.ui.TreeItem#addItem(java.lang.String).
	     * <p>
	     * Note that all the items in the tree are converted to GWT HTML object
	     *      before adding to the tree. Therefore, the GWT Tree.getText() method
	     *      will return a null.
	     */
	    public TreeItem addItem(String itemText)
	    {
	        HTML html = new HTML(itemText);
	        Cactus.preventBrowserContextMenu(html.getElement());
	        return super.addItem(html);
	    }

	    /**
	     * Add a SuperTree object as a child of this tree element.
	     * 
	     * @param item
	     *            item to add.
	     */
	    public void addItem(CactusItem item)
	    {
	        super.addItem(item);
	        Cactus.preventBrowserContextMenu(item.getElement());
	    }

	    /**
	     * @see com.google.gwt.user.client.ui.TreeItem#addItem(com.google.gwt.user.client.ui.TreeItem)
	     */
	    public void addItem(TreeItem item)
	    {
	    	Cactus.preventBrowserContextMenu(item.getElement());
	        super.addItem(item);
	    }

	    /**
	     * @see com.google.gwt.user.client.ui.TreeItem#addItem(com.google.gwt.user.client.ui.Widget)
	     */
	    public TreeItem addItem(Widget widget)
	    {
	    	Cactus.preventBrowserContextMenu(widget.getElement());
	        return super.addItem(widget);
	    }

	    /**
	     * Gets the context menu for this element. If an element-specific context menu is not
	     * set, a null is returned.
	     * 
	     * @return Returns the contextMenu.
	     */
	    public CactusPopupMenu getContextMenu()
	    {
	        return contextMenu;
	    }

	    /**
	     * Returns the type of the object.
	     * 
	     * @return Returns the type.
	     */
	    public int getType()
	    {
	        return type;
	    }

	    /**
	     * @see com.google.gwt.user.client.ui.TreeItem#getUserObject()
	     */
	    public Object getUserObject()
	    {
	        return userObject;
	    }

	    /**
	     * Sets an item-specific context menu. This will override the level-specific
	     * context menu.
	     * 
	     * @param contextMenu
	     *            The contextMenu to set.
	     */
	    public void setContextMenu(CactusPopupMenu contextMenu)
	    {
	        this.contextMenu = contextMenu;
	    }

	    /**
	     * @see com.google.gwt.user.client.ui.TreeItem#setHTML(java.lang.String)
	     * <p>
	     * Note that all the items in the tree are converted to GWT HTML object
	     *      before adding to the tree. Therefore, the GWT Tree.getHTML() method
	     *      will return a null.
	     */
	    public void setHTML(String html)
	    {
	        HTML h = new HTML(html);
	        super.setWidget(h);
	        Cactus.preventBrowserContextMenu(h.getElement());
	    }

	    /**
	     * @see com.google.gwt.user.client.ui.TreeItem#setText(java.lang.String)
	     * <p>
	     * Note that all the items in the tree are converted to GWT HTML object
	     *      before adding to the tree. Therefore, the GWT Tree.getText() method
	     *      will return a null.
	     */
	    public void setText(String text)
	    {
	        HTML html = new HTML(text);
	        super.setWidget(html);
	        Cactus.preventBrowserContextMenu(html.getElement());
	    }

	    /**
	     * Sets the type of this object.
	     * 
	     * @param type
	     *            The type to set.
	     */
	    public void setType(int type)
	    {
	        this.type = type;
	    }

	    /**
	     * @see com.google.gwt.user.client.ui.TreeItem#setUserObject(java.lang.Object)
	     */
	    public void setUserObject(Object userObject)
	    {
	        this.userObject = userObject;
	    }

	    /**
	     * @see com.google.gwt.user.client.ui.TreeItem#setWidget(com.google.gwt.user.client.ui.Widget)
	     */
	    public void setWidget(Widget newWidget)
	    {
	    	Cactus.preventBrowserContextMenu(newWidget.getElement());
	        super.setWidget(newWidget);
	    }
}
