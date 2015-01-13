package de.uni_koeln.spinfo.arc.dto.comparator;

import java.util.Comparator;

import de.uni_koeln.spinfo.arc.dto.annotation.RangeAnnotationDto;

public class RangeAnnotationComparator implements Comparator<RangeAnnotationDto>{
	
	private RangeAnnotationComparator() {}; 
	
	public static RangeAnnotationComparator INSTANCE = new RangeAnnotationComparator();
	
	public int compare(RangeAnnotationDto o1, RangeAnnotationDto o2) {
		if (o1 == o2) {
			return 0;
		}

			long resStart = o1.getStart() - o2.getStart();
			if (resStart != 0) return (int) resStart;
			
			long resEnd = o1.getEnd() - o2.getEnd();
			if (resEnd != 0) return (int) resEnd;

			return 0;
	}
}
