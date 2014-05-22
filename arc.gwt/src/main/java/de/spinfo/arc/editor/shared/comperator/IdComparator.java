package de.spinfo.arc.editor.shared.comperator;

import java.util.Comparator;

import de.spinfo.arc.editor.shared.model.WorkingUnit;


public class IdComparator implements Comparator<WorkingUnit>{
	public int compare(WorkingUnit o1, WorkingUnit o2) {
		if (o1 == o2) {
			return 0;
		}

		// Compare the id columns.
		if (o1 != null) {
			return (o2 != null) ? o1.getId().compareTo(
					o2.getId()) : 1;
		}
		return -1;
	}
}
