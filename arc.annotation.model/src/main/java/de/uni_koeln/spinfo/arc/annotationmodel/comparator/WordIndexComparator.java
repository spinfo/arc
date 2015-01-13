package de.uni_koeln.spinfo.arc.annotationmodel.comparator;

import java.util.Comparator;

import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.Word;

public class WordIndexComparator implements Comparator<Word>{
	
	private WordIndexComparator() {}; 
	
	public static WordIndexComparator INSTANCE = new WordIndexComparator();
	
	public int compare(Word o1, Word o2) {
		if (o1 == o2) {
			return 0;
		} 

		// Compare the id columns.
		if (o1 != null) {
			return (int) ((o2 != null) ? o1.getIndex() - o2.getIndex() : 1);
		}
		return -1;
	}
}
