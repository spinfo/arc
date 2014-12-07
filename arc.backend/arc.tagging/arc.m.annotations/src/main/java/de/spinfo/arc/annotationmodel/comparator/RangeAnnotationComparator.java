package de.spinfo.arc.annotationmodel.comparator;

import de.spinfo.arc.annotationmodel.annotation.RangeAnnotation;

import java.util.Comparator;

public class RangeAnnotationComparator implements Comparator<RangeAnnotation>{
	
	private RangeAnnotationComparator() {}; 
	
	public static RangeAnnotationComparator INSTANCE = new RangeAnnotationComparator();
	
	public int compare(RangeAnnotation o1, RangeAnnotation o2) {
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
