package de.spinfo.arc.editor.client.cactus.comparators;

import java.util.Comparator;

import com.google.gwt.user.client.ui.TreeItem;

import de.spinfo.arc.editor.shared.model3.modification.ModificationRange;
import de.spinfo.arc.editor.shared.model3.util.DebugHelper;


public class TreeItemModificationRangeUserObjectComparator implements Comparator<TreeItem> {
	private static final boolean DEBUG_COMPARATOR = true;

	private TreeItemModificationRangeUserObjectComparator() {};
	
	public static TreeItemModificationRangeUserObjectComparator INSTANCE = new TreeItemModificationRangeUserObjectComparator();

	private static String ISSUE_TEXT = "The treeItem used for this comperator must have a UserObject  of type ModificationRange! But it is: ";
	
	@Override
	public int compare(TreeItem o1, TreeItem o2) {
		if ( !(o1.getUserObject() instanceof ModificationRange)) throw new IllegalArgumentException(ISSUE_TEXT + o1);
		if ( !(o2.getUserObject() instanceof ModificationRange)) throw new IllegalArgumentException(ISSUE_TEXT + o2);
		ModificationRange o1Range = (ModificationRange) o1.getUserObject();
		ModificationRange o2Range = (ModificationRange) o2.getUserObject();
		int resStart = o1Range.getStart() - o2Range.getStart();
		if (DEBUG_COMPARATOR) 
			DebugHelper.print(this.getClass(), "TreeItemModificationRangeUserObjectComparator this.start - o.getStart(): " + resStart , true);
		if (resStart != 0) return resStart;
		
		int resEnd = o1Range.getEnd() - o2Range.getEnd();
		if (DEBUG_COMPARATOR) 
			DebugHelper.print(this.getClass(), "TreeItemModificationRangeUserObjectComparator this.end - o.getEnd(): " + resEnd , true);
		if (resEnd != 0) return resEnd;
		
		return 0;
	}
	
}
