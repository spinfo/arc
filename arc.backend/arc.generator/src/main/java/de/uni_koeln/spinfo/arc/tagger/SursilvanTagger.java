package de.uni_koeln.spinfo.arc.tagger;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class SursilvanTagger extends ARCTagger {

	
	
	public SursilvanTagger(Map<String, TreeSet<String>> fullForms, String collectionName) {
		super(fullForms, collectionName);
	}

	@Override
	boolean isIndImperfect(String token) {
		if (token.endsWith("avel") || token.endsWith("avas")
				|| token.endsWith("ava") || token.endsWith("avan")
				|| token.endsWith("evel") || token.endsWith("evas")
				|| token.endsWith("eva") || token.endsWith("evan")){
			return true;
		}
		return false;
	}



	@Override
	boolean isAdverb(String token) {
		if(token.endsWith("mein")){
			return true;
		}
		return false;
	}

}
