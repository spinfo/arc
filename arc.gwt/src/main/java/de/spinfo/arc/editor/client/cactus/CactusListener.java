package de.spinfo.arc.editor.client.cactus;


import com.google.gwt.user.client.ui.TreeItem;
/**
 * Refactored due to visibility issues. No code Changes. the original is from 
 * package org.cafesip.gwtcomp.client.utils;
 * 
 * This interface must be implemented by applications to register a super tree
 * listener object. See the {@link org.cafesip.gwtcomp.client.ui.SuperTree}
 * class for details.
 * 
 * @author Amit Chatterjee
 * 
 */
public interface CactusListener {
	    /**
	     * This method is invoked just before a popup menu is popped up. The
	     * application can hide menu items, add new menu items, etc. by implementing
	     * this method.
	     * 
	     * @param level
	     *            the selected level.
	     * @param menu
	     *            the menu that will be popped up.
	     * @param treeItems
	     *            selected tree items.
	     */
	    public void onPopup(String level, CactusPopupMenu menu, TreeItem[] treeItems);

	    /**
	     * Invoked when one or more tree items are selected. Note that this method
	     * is only invoked, when there is no popup menu associated with the element
	     * and there is no popup menu associated with the level.
	     * 
	     * @param level
	     *            level of the item
	     * @param treeItems
	     *            selected tree items
	     */
	    public void onSelect(String level, TreeItem[] treeItems);

	    /**
	     * This method is invoked when a tree item is expanded.
	     * 
	     * @param item
	     *            the tree item.
	     */
	    public void onExpand(TreeItem item);

	    /**
	     * This method is invoked when a tree item is collapsed.
	     * 
	     * @param item
	     *            the tree item.
	     */
	    public void onCollapse(TreeItem item);
}
