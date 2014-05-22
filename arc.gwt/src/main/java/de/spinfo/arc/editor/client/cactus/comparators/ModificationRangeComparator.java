package de.spinfo.arc.editor.client.cactus.comparators;

import java.util.Comparator;

import de.spinfo.arc.editor.shared.model3.modification.ModificationRange;
import de.spinfo.arc.editor.shared.model3.util.DebugHelper;


public class ModificationRangeComparator implements Comparator<ModificationRange> {
	private static final boolean DEBUG_COMPARATOR = false;

	private ModificationRangeComparator() {};
	
	public static ModificationRangeComparator INSTANCE = new ModificationRangeComparator();
	
	@Override
	public int compare(ModificationRange o1, ModificationRange o2) {
		int resStart = o1.getStart() - o2.getStart();
		if (DEBUG_COMPARATOR) 
			DebugHelper.print(this.getClass(), "COMPARATOR this.start - o.getStart(): " + resStart , true);
		if (resStart != 0) return resStart;
		
		int resEnd = o1.getEnd() - o2.getEnd();
		if (DEBUG_COMPARATOR) 
			DebugHelper.print(this.getClass(), "COMPARATOR this.end - o.getEnd(): " + resEnd , true);
		if (resEnd != 0) return resEnd;
		
		return 0;
	}
}
