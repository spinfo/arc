package de.uni_koeln.spinfo.arc.dto.comparator;

import java.util.Comparator;

import de.uni_koeln.spinfo.arc.dto.annotation.HasDetailsDto;

public class HasDetailsDateComparator implements Comparator<HasDetailsDto>{
	
	private HasDetailsDateComparator() {}; 
	
	public static HasDetailsDateComparator INSTANCE = new HasDetailsDateComparator();
	
	public int compare(HasDetailsDto o1, HasDetailsDto o2) {
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
