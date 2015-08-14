package de.uni_koeln.spinfo.arc.matcher;

import java.util.Map;
import java.util.TreeSet;

public class SutsilvanMatcher extends POSMatcher {


	public SutsilvanMatcher(Map<String, TreeSet<String>> fullForms,
							String collectionName) {
		super(fullForms, collectionName);
	}

	@Override
	boolean isAdverb(String token) {
		if (token.endsWith("maing")) {
			return true;
		}
		return false;
	}

	@Override
	boolean isIndicativeImperfect(String token) {
		if (token.endsWith("ava") || token.endsWith("avas")
				|| token.endsWith("avan") || token.endsWith("eva")
				|| token.endsWith("evas") || token.endsWith("evan")) {
			return true;
		}
		return false;
	}

}
