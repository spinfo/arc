package de.spinfo.arc.editor.client.cactus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

/**
 * Almost the whole code is from gwtcomps SuperTree. This version supports range
 * selection by using shift. The Method for addind key-listeners is in the original version private hence
 * the need for making a new class.
 * @author of modifications drival
 * 
 * An extension of the GWT Tree that allows multiple selection of tree items and
 * includes comprehensive support for context-sensitive popup menu among other
 * things. The main feature of the objects is the ability for the application to
 * define different popup menus at every unique level of the tree. For example,
 * if a tree is displaying countries, its states and cities within a state, it
 * is possible to define a different popup menu at the country-level,
 * state-level and city-level without having to attach a popup menu for
 * individual items although that is possible as well. In order to utilize the
 * feature, the application must use
 * {@link org.cafesip.gwtcomp.client.ui.SuperTreeItem} instead of the GWT
 * TreeItem object to define the tree hierarchy. Each object of the type
 * SuperTreeItem has an integer "type" attribute that must be specified. A level
 * is specified as a string formatted as W.X.Y.Z.... where W, X, Y, Z are types.
 * The dots are used to specify the hierarchy. For example, if the country has
 * the type 1, state has the type 2 and the city has the type 3, the level 1.2.3
 * is used to specify a context menu for the city that is placed in the tree as
 * Country->State->City. The context-menu is popped up by using the right-click.
 * <b>Note that the application must not use the type value of 0. That value is
 * reserved for objects of the type TreeItem. </b>
 * <p>
 * If the tree has multi-selection enabled, the user can select more than one
 * tree item by clicking on the tree item while pressing the CTRL key. The user
 * can select multiple items belonging to the same level only. Therefore, in
 * conjuction with the the context-senstive popup menu, the user can select
 * multiple items of the same level and popup the menu.
 * <p>
 * The application can also define their own tree listener for listening to tree
 * selection, tree expand and collapse and popup events. For more details, see
 * the {@link org.cafesip.gwtcomp.client.ui.SuperTreeListener} class.
 * <p>
 * <u>CSS Style Rules:</u> <ul class="css">
 * <li>.gwtcomp-SuperTree { } - style for the tree itself</li>
 * <li>.gwtcomp-SuperTree-SelectedItem { } - style for a selected tree item</li>
 * </ul>
 * 
 * <p>
 * <u>Screen-Shots:</u><br>
 * <img class='gallery' src='../../../../../doc-files/super_tree.gif'/>
 * <p>
 * 
 * @author Amit Chatterjee
 * 
 */

public class Cactus extends Tree {

	private HashMap<String, CactusPopupMenu> contextMenus = new HashMap<String, CactusPopupMenu>();

	private boolean ctrlKeyPressed = false;
	private boolean shiftKeyPressed = false;

	private ArrayList<CactusListener> listeners = new ArrayList<CactusListener>();

	private boolean multiSelectionEnabled = false;

	private ArrayList<TreeItem> selectedItems = new ArrayList<TreeItem>();

	private String selectedLevel = "";

	public Cactus() {
		super();
		init();
	}

	public Cactus(Resources images) {
		super(images);
		init();
	}

	protected static native void preventBrowserContextMenu(
			JavaScriptObject element)
	/*-{
		element.oncontextmenu = function() {
			return false;
		}
	}-*/;

	/**
	 * @see com.google.gwt.user.client.ui.Tree#addItem(java.lang.String).
	 *      <p>
	 *      Note that all the items in the tree are converted to GWT HTML object
	 *      before adding to the tree. Therefore, the GWT Tree.getText() method
	 *      will return a null.
	 */
	public TreeItem addItem(String itemText) {
		HTML html = new HTML(itemText);
		preventBrowserContextMenu(html.getElement());
		return super.addItem(html);
	}

	/**
	 * Add a SuperTree object to the root of the tree.
	 * 
	 * @param item
	 *            item to add.
	 */
	public void addItem(CactusItem item) {
		super.addItem(item);
		preventBrowserContextMenu(item.getElement());
	}

	/**
	 * @see com.google.gwt.user.client.ui.Tree#addItem(com.google.gwt.user.client.ui.TreeItem)
	 */
	public void addItem(TreeItem item) {
		super.addItem(item);
		preventBrowserContextMenu(item.getElement());
	}

	/**
	 * @see com.google.gwt.user.client.ui.Tree#addItem(com.google.gwt.user.client.ui.Widget)
	 */
	public TreeItem addItem(Widget widget) {
		preventBrowserContextMenu(widget.getElement());
		return super.addItem(widget);
	}

	/**
	 * Add a tree listener.
	 * 
	 * @param listener
	 *            the listener object.
	 */
	public void addTreeListener(CactusListener listener) {
		listeners.add(listener);
	}

	/**
	 * Convenience method for finding the level of any given tree items. Note
	 * that although an object of the type TreeItem is passed as a parameter,
	 * normally, an object of the type SuperTreeItem must be used. An object of
	 * the type TreeItem is assumed to have the tye 0.
	 * 
	 * @param item
	 *            a tree item.
	 * @return the level
	 */
	public static String getLevel(TreeItem item) {
		ArrayList<Integer> items = new ArrayList<Integer>();
		do {
			if (item instanceof CactusItem) {
				items.add(new Integer(((CactusItem) item).getType()));
			} else {
				items.add(new Integer(0));
			}

			item = item.getParentItem();
		} while (item != null);

		StringBuffer buffer = new StringBuffer();
		Collections.reverse(items);

		for (int i = 0; i < items.size(); i++) {
			if (i > 0) {
				buffer.append('.');
			}

			buffer.append(((Integer) items.get(i)).toString());
		}
		return buffer.toString();
	}

	/**
	 * @see com.google.gwt.user.client.ui.Tree#getSelectedItem().
	 * 
	 *      <p>
	 *      Note that if multi-selection is enabled, this method returns the
	 *      item selected first. For trees with multi-selection, the
	 *      getSelectedItems() method must be used.
	 * 
	 */
	public TreeItem getSelectedItem() {
		if (selectedItems.size() == 0) {
			return null;
		}

		return selectedItems.get(0);
	}

	/**
	 * Returns the selected items.
	 * 
	 * @return selected items.
	 */
	public TreeItem[] getSelectedItems() {
		TreeItem[] items = convertSelectedToArray();
		return items;
	}

	/**
	 * Returns if multi-selection is enabled.
	 * 
	 * @return Returns the multiSelectionEnabled.
	 */
	public boolean isMultiSelectionEnabled() {
		return multiSelectionEnabled;
	}

	/**
	 * Remove the context menu at a given level.
	 * 
	 * @param level
	 */
	public void removeContextMenu(String level) {
		contextMenus.remove(level);
	}

	/**
	 * Removes the tree listener.
	 * 
	 * @param listener
	 *            listener to remove.
	 */
	public void removeTreeListener(CactusListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Sets the context menu for a given level.
	 * 
	 * @param level
	 *            the level to set
	 * @param contextMenu
	 *            the context menu object.
	 */
	public void setContextMenu(String level, CactusPopupMenu contextMenu) {
		contextMenus.put(level, contextMenu);
	}

	/**
	 * Enables or disables multi-selection.
	 * 
	 * @param multiSelectionEnabled
	 *            The multiSelectionEnabled to set.
	 */
	public void setMultiSelectionEnabled(boolean multiSelectionEnabled) {
		this.multiSelectionEnabled = multiSelectionEnabled;
	}

	/**
	 * 
	 * @see com.google.gwt.user.client.ui.Tree#setSelectedItem(com.google.gwt.user.client.ui.TreeItem)
	 */
	public void setSelectedItem(TreeItem item) {
		setSelectedItem(item, false);
	}

	/**
	 * @see com.google.gwt.user.client.ui.Tree#setSelectedItem(com.google.gwt.user.client.ui.TreeItem,
	 *      boolean)
	 */
	public void setSelectedItem(TreeItem item, boolean fireEvents) {
		if (item == null) {
			// let the GWT tree handle de-selection of an item being removed
			super.setSelectedItem(item, fireEvents);
			return;
		}

		if (fireEvents) {
			// let the GWT tree do its work. This is needed because the
			// application
			// may have registered one or more TreeListener.
			super.setSelectedItem(item, fireEvents);
		} else {
			processSelectedItems(item, false);
		}
	}

	private TreeItem[] convertSelectedToArray() {
		TreeItem[] items = new TreeItem[selectedItems.size()];
		for (int i = 0; i < items.length; i++) {
			items[i] = selectedItems.get(i);
		}
		return items;
	}

	private void init() {
		addStyleName("gwtcomp-SuperTree");
		initTreeListeners();
	}

	private void initTreeListeners() {
		addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_CTRL) {
					ctrlKeyPressed = true;
				} 
				
				if (event.getNativeKeyCode() == KeyCodes.KEY_SHIFT) {
					shiftKeyPressed = true;
				} 
				
			}
		});

		addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_CTRL) {
					ctrlKeyPressed = false;
				}
				
				if (event.getNativeKeyCode() == KeyCodes.KEY_SHIFT) {
					shiftKeyPressed = false;
				} 
			}
		});

		addSelectionHandler(new SelectionHandler<TreeItem>() {
			public void onSelection(SelectionEvent<TreeItem> event) {
				processSelectedItems(event.getSelectedItem(), true);
			}
		});

		addOpenHandler(new OpenHandler<TreeItem>() {
			public void onOpen(OpenEvent<TreeItem> event) {
				for (int i = 0; i < listeners.size(); i++) {
					CactusListener listener = listeners.get(i);
					listener.onExpand(event.getTarget());
				}
			}
		});

		addCloseHandler(new CloseHandler<TreeItem>() {
			public void onClose(CloseEvent<TreeItem> event) {
				for (int i = 0; i < listeners.size(); i++) {
					CactusListener listener = listeners.get(i);
					listener.onCollapse(event.getTarget());
				}
			}
		});

		addDomHandler(new ContextMenuHandler() {
			public void onContextMenu(ContextMenuEvent event) {
				processContextMenu(event);
			}
		}, ContextMenuEvent.getType());
	}

	private TreeItem findSelectedTreeItem(TreeItem item, int x, int y) {
		if (!item.getWidget().isVisible()) {
			return null;
		}

		// first, recursively look at child items
		int count = item.getChildCount();
		for (int i = 0; i < count; i++) {
			TreeItem selected = findSelectedTreeItem(item.getChild(i), x, y);
			if (selected != null) {
				return selected;
			}
		}

		// if the item was not not found in the lower level, go for the parent
		// item
		if (x >= item.getWidget().getAbsoluteLeft()
				&& x <= item.getWidget().getAbsoluteLeft()
						+ item.getWidget().getOffsetWidth()
				&& y >= item.getWidget().getAbsoluteTop()
				&& y <= item.getWidget().getAbsoluteTop()
						+ item.getWidget().getOffsetHeight()) {
			return item;
		}

		return null;
	}

	private void popup(TreeItem parent, CactusPopupMenu menu, String level) {
		if (listeners.size() > 0) {
			TreeItem[] items = convertSelectedToArray();
			for (int i = 0; i < listeners.size(); i++) {
				CactusListener listener = listeners.get(i);
				listener.onPopup(level, menu, items);
			}
		}

		int left = parent.getAbsoluteLeft() + 40;
		int top = parent.getAbsoluteTop() + 20;
		menu.setPopupPosition(left, top);
		menu.show();
	}

	private void processSelectedItems(TreeItem item, boolean fireEvents) {
		String level = getLevel(item);
		if (processSelection(item, level) == false) {
			// an items has been de-selected, no need to take any further action
			return;
		}

		// send the browser event
		if (fireEvents && listeners.size() > 0) {
			if (selectedItems.size() == 0) {
				return;
			}

			if (!selectedItems.contains(item)) {
				return;
			}

			// // check for any item-specific popup
			if (item instanceof CactusItem) {
				CactusItem sitem = (CactusItem) item;
				if (sitem.getContextMenu() != null) {
					return;
				}
			}

			// check for any level-specific popup
			CactusPopupMenu menu = contextMenus.get(level);
			if (menu != null) {
				return;
			}

			TreeItem[] items = convertSelectedToArray();
			for (int i = 0; i < listeners.size(); i++) {
				CactusListener listener = listeners.get(i);
				listener.onSelect(level, items);
			}
		}
	}
	
	private boolean processSelection(TreeItem item, String level) {
		if (multiSelectionEnabled) {
			if (ctrlKeyPressed) {
				
				if (isSelectedAlreadyInSelection(item)) 
					return false;

				if (!level.equals(selectedLevel)) 
					unselectItems();
				

				selectItem(item, level);
				return true;
			}
			else if (shiftKeyPressed) {
				if (!level.equals(selectedLevel)) 
					unselectItems();
				
				if (!selectedItems.isEmpty()) {
					
					if (isSelectedAlreadyInSelection(item)) 
						return false;
					
					if (item.getParentItem() != null) {
						TreeItem parent = item.getParentItem();
						// get the child index of the last selected item
						int lastSelectedIdx = selectedItems
								.get(selectedItems.size() - 1)
								.getParentItem()
								.getChildIndex(selectedItems.get(selectedItems.size() - 1));
						// get the current selected
						int currentSelectedIdx = item.getParentItem()
								.getChildIndex(item);
						// if the user starts selection from the bottom its good
						// to check which of the indices is smaller/greater
						// in case the range selection is done top to bottom:
						if (lastSelectedIdx < currentSelectedIdx) {
							for (int i = lastSelectedIdx + 1; i <= currentSelectedIdx; i++) {
								TreeItem currentItem = parent.getChild(i);
								selectItem(currentItem, level);
							}
						}
						// in case the range selection is done bottom to top:
						else {
							for (int i = lastSelectedIdx - 1; i >= currentSelectedIdx; i--) {
								TreeItem currentItem = parent.getChild(i);
								selectItem(currentItem, level);
							}
						}
						return true;
					}
				}
			}
			// if no shift and cntrl is pressed but multiselection enabled
			else {
				unselectItems();
				selectItem(item, level);
				return true;
			}
		} 
		// if no  multiselection enabled do common single selection
		else {
			unselectItems();
			selectItem(item, level);
			return true;
		}
		return false;
	}

	private boolean isSelectedAlreadyInSelection(TreeItem item) {
		if (selectedItems.contains(item)) {
			// de-select the item
			selectedItems.remove(item);
			item.getWidget().removeStyleName(
					"gwtcomp-SuperTree-SelectedItem");
			return true;
		}
		
		return false;
	}
	
	private void selectItem(TreeItem item, String level) {
		selectedItems.add(item);
		selectedLevel = level;
		item.getWidget().addStyleName("gwtcomp-SuperTree-SelectedItem");
	}

	public void unselectItems() {
		for (int i = 0; i < selectedItems.size(); i++) {
			TreeItem el = selectedItems.get(i);
			el.getWidget().removeStyleName("gwtcomp-SuperTree-SelectedItem");
		}
		selectedItems.clear();
		selectedLevel = "";
		// David mod here
		ctrlKeyPressed = false;
		shiftKeyPressed = false;
	}

	private void processContextMenu(ContextMenuEvent event) {
		event.preventDefault();
		event.stopPropagation();

		int x = event.getNativeEvent().getClientX();
		int y = event.getNativeEvent().getClientY();

		TreeItem selectedItem = null;
		int count = getItemCount();
		for (int i = 0; i < count; i++) {
			TreeItem item = getItem(i);
			selectedItem = findSelectedTreeItem(item, x, y);
			if (selectedItem != null) {
				break;
			}
		}

		if (selectedItem == null) {
			return;
		}

		String level = getLevel(selectedItem);
		if (selectedItems.size() == 0) {
			selectItem(selectedItem, level);
		} else if (!selectedItems.contains(selectedItem)) {
			unselectItems();
			selectItem(selectedItem, level);
		}

		if (selectedItem instanceof CactusItem) {
			CactusItem sitem = (CactusItem) selectedItem;
			if (sitem.getContextMenu() != null) {
				popup(selectedItem, sitem.getContextMenu(), level);
				return;
			}
		}

		// check for any level-specific popup
		CactusPopupMenu menu = contextMenus.get(level);
		if (menu != null) {
			popup(selectedItem, menu, level);
		}
	}
}
