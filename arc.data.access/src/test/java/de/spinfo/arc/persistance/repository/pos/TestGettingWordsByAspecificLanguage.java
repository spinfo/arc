package de.spinfo.arc.persistance.repository.pos;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import de.spinfo.arc.persistance.service.query.WordQueries;
import de.spinfo.arc.persistance.service.query.WorkingUnitQueries;
import de.spinfo.arc.persistance.service.update.WordUpdater;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.WorkingUnit;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.impl.WordImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.LanguageRange;

public class TestGettingWordsByAspecificLanguage {
	static WorkingUnitQueries wuQueries = new WorkingUnitQueries();
	static WordQueries wordQueries = new WordQueries();
	static WordUpdater wordUpdater = new WordUpdater();
	@Test
	public void test() {
		/*
		 * First Demo is using iteration over all languages
		 */
		
		List<WorkingUnit> allWorkingUnits = wuQueries.getAllWorkingUnits();
		
		assertEquals("Are all 14 Working units retrieved?", 14, allWorkingUnits.size());
		
		WorkingUnit retrievedWu = wuQueries.getWorkingUnit("Band II");
		assertEquals("Is the demanded Working Unit retrievable by its title",
				"Band II",
				retrievedWu.getTitle());
		
		List<LanguageRange> languageRanges = retrievedWu.getLanguages();
		

		/* Below some dummy demo strings*/
		String LANGUAGE_TO_LOOK_FOR = "Sursilvan";
		String WORD_TO_LOOK_FOR_IN_LANGUAGE_IN_ORDER_TO_ADD_POS_OPTION = "svaneus";
		int wordSize = 0;
		for (LanguageRange languageRange : languageRanges) {
			if (languageRange.getTitle().equals(LANGUAGE_TO_LOOK_FOR)) {
				System.err.println("found range with title: " + languageRange.getTitle() );
				List<WordImpl> wordsOfLang = wordQueries.getWordsByRange(languageRange);
				/*adding to var in order to make a test a the end*/
				wordSize += wordsOfLang.size();
				System.err.println("With num words in this range: " + wordsOfLang.size() );
				for (WordImpl word : wordsOfLang) {
					if (word.getLastFormAnnotation().
							getForm().equals(WORD_TO_LOOK_FOR_IN_LANGUAGE_IN_ORDER_TO_ADD_POS_OPTION )) {
					/*
					 * HERE you can write the new POs-Options to the specific word in the MongoDB
					 */
					Set<String> posSet = new HashSet<>();
					/*
					 * Below is a commented demo. 
					 * 1. fill the set with relevant posOptions
					 * 2. use the wordUpdater to update the current word (determined by word.getIndex())
					 * with the set of availble pos options
					 */
//					posSet.add("NN");
//					posSet.add("CONJ_S");
//					for (String string : posSet) { PosChecker.checkIfValid(string); }
//					wordUpdater.pushPosTaggerOptionsAsStrings(word.getIndex(), posSet);
					}
				}
				
				
			}
		}
		

		assertEquals("Is the amount of words retrieved by the 2 ways equal", 
				wordSize,getWordsByLanguage(LANGUAGE_TO_LOOK_FOR).size() );
	}
	
	/**
	 * Returns a list of all words containing in ALL working units if they match the given lanuage param
	 * 
	 * @param language the language to lok for among all workingUnits
	 * @return the words of ALL working units which have this kind of Language as range defined
	 */
	private static List<WordImpl> getWordsByLanguage(String language){
		List<WordImpl> toReturn = new ArrayList<>();
		List<WorkingUnit> workingUnitsWhichHaveAtLeastOneRangeOfSearchedLanguage 
					=  wuQueries.getWorkingUnitsWithLanguage(language);
		for (WorkingUnit workingUnit : workingUnitsWhichHaveAtLeastOneRangeOfSearchedLanguage) {
			List<LanguageRange> languageRanges = workingUnit.getLanguages();
			for (LanguageRange languageRange : languageRanges) {
				if(language.equals(languageRange.getTitle())) {
					System.err.println("found range with title: " + languageRange.getTitle() );
					List<WordImpl> wordsOfLang = wordQueries.getWordsByRange(languageRange);
					System.err.println("With num words in this range: " + wordsOfLang.size() );
					toReturn.addAll(wordsOfLang);
				}
			}
		}
		return toReturn;
	}

}
