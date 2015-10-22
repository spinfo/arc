package de.uni_koeln.spinfo.arc.dto.comparator;

import java.util.Comparator;

import de.uni_koeln.spinfo.arc.dto.annotatable.WordDto;

public class WordIndexComparator implements Comparator<WordDto>{
	
	private WordIndexComparator() {}; 
	
	public static WordIndexComparator INSTANCE = new WordIndexComparator();
	
	public int compare(WordDto o1, WordDto o2) {
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
