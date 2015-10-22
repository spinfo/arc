package de.uni_koeln.spinfo.arc.annotationmodel.comparator;

import de.uni_koeln.spinfo.arc.annotationmodel.annotation.HasDetails;

import java.util.Comparator;

public class HasDetailsDateComparator implements Comparator<HasDetails>{
	
	private HasDetailsDateComparator() {}; 
	
	public static HasDetailsDateComparator INSTANCE = new HasDetailsDateComparator();
	
	public int compare(HasDetails o1, HasDetails o2) {
		if (o1 == o2) {
			return 0;
		}

		// Compare the id columns.
		if (o1 != null) {
			return (o2 != null) ? o1.getDate().compareTo(
					o2.getDate()) : 1;
		}
		return -1;
	}
}
