package de.spinfo.arc.editor.client.cactus.comparators;

import java.util.Comparator;
import java.util.List;

import de.spinfo.arc.editor.shared.model3.modification.ModificationRange;
import de.spinfo.arc.editor.shared.model3.util.DebugHelper;


public class ModificationRangeListComparator implements Comparator<List<ModificationRange>> {
	private static final boolean DEBUG_COMPARATOR = false;

	private ModificationRangeListComparator() {};
	
	public static ModificationRangeListComparator INSTANCE = new ModificationRangeListComparator();

	@Override
	public int compare(List<ModificationRange> o1, List<ModificationRange> o2) {
		
		int minSize = Math.min(o1.size(), o2.size());
		int resStart = 0;
		
		for (int i = 0; i < minSize; i++) {
			resStart += (o1.get(i).getStart() - o2.get(i).getStart());
			
		}

		if (DEBUG_COMPARATOR) 
			DebugHelper.print(this.getClass(), "ModificationRangeListComparator o1.get(i).getStart() - o2.get(i).getStart(): " + resStart , true);
		if (resStart != 0) return resStart;
		
		
		int resEnd = 0;
		for (int i = 0; i < minSize; i++) {
			resEnd += (o1.get(i).getEnd() - o2.get(i).getEnd());
			
		}
		if (DEBUG_COMPARATOR) 
			DebugHelper.print(this.getClass(), "ModificationRangeListComparator - (o1.get(i).getEnd() - o2.get(i).getEnd()): " + resEnd , true);
		if (resEnd != 0) return resEnd;
		
		return 0;
	}
	

}
