package de.spinfo.arc.persistance.repository.pos;


import de.spinfo.arc.data.Entry;
import de.spinfo.arc.data.IOMongo;
import de.spinfo.arc.persistance.service.query.WordQueries;
import de.spinfo.arc.persistance.service.query.WorkingUnitQueries;
import de.spinfo.arc.persistance.service.update.WordUpdater;
import de.spinfo.arc.persistance.util.PosChecker;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.WorkingUnit;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.impl.WordImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.LanguageRange;
import de.uni_koeln.spinfo.arc.matcher.Token;
import de.uni_koeln.spinfo.arc.utils.FileUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class TestGettingWordsByAspecificLanguage {

    static IOMongo ioMongo = new IOMongo();

    static WorkingUnitQueries wuQueries = new WorkingUnitQueries();
    static WordQueries wordQueries = new WordQueries();
    static WordUpdater wordUpdater = new WordUpdater();


    //@Ignore
    @Test
    public void getGoldenTokens() throws IOException {


        List<Entry> entries = ioMongo.getSursilvanGoldenEntries();

        FileUtils.writeList(entries, "golden");
        FileUtils.printList(entries, FileUtils.outputPath, "golden");

    }

    @Test
    public void testGetTokens() throws Exception {

        Map<Integer, String> getTokens = ioMongo.getTokens("golden2015-03-24T14:41:09Z");

        FileUtils.printMap(getTokens, FileUtils.outputPath, "testmap");


    }

    @Test
    public void testGetPageNumberInWU() {

        ioMongo.getPageNumberInWU("Band II", 359930);


    }


    @Ignore
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
                System.err.println("found range with title: " + languageRange.getTitle());
                List<WordImpl> wordsOfLang = wordQueries.getWordsByRange(languageRange);
                /*adding to var in order to make a test a the end*/
                wordSize += wordsOfLang.size();
                System.err.println("With num words in this range: " + wordsOfLang.size());
                for (WordImpl word : wordsOfLang) {
                    if (word.getLastFormAnnotation().
                            getForm().equals(WORD_TO_LOOK_FOR_IN_LANGUAGE_IN_ORDER_TO_ADD_POS_OPTION)) {
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
                        for (String string : posSet) {
                            PosChecker.checkIfValid(string);
                        }
                        wordUpdater.pushPosTaggerOptionsAsStrings(word.getIndex(), posSet);
                    }
                }


            }
        }


        assertEquals("Is the amount of words retrieved by the 2 ways equal",
                wordSize, getWordsByLanguage(LANGUAGE_TO_LOOK_FOR).size());
    }

    @Ignore
    @Test
    public void testWordstoFile() throws Exception {


        String sursilvan = "Sursilvan";

        List<WordImpl> gw = getWordsByLanguage(sursilvan);

        List<Token> tokens = getListOfTokens(gw);


        //DictUtils.printList(gw, outputPath, "words_sursilvan_20141203");

        FileUtils.writeList(tokens, "words_");
    }


    /**
     * Returns a list of all words containing in ALL working units if they match the given lanuage param
     *
     * @param language the language to lok for among all workingUnits
     * @return the words of ALL working units which have this kind of Language as range defined
     */
    private static List<WordImpl> getWordsByLanguage(String language) {
        List<WordImpl> toReturn = new ArrayList<>();
        List<WorkingUnit> workingUnitsWhichHaveAtLeastOneRangeOfSearchedLanguage
                = wuQueries.getWorkingUnitsWithLanguage(language);
        for (WorkingUnit workingUnit : workingUnitsWhichHaveAtLeastOneRangeOfSearchedLanguage) {
            List<LanguageRange> languageRanges = workingUnit.getLanguages();
            for (LanguageRange languageRange : languageRanges) {
                if (language.equals(languageRange.getTitle())) {

                    System.out.println(workingUnit.getTitle() + "\t" + languageRange.getStart() + "\t" + languageRange.getEnd() + "\t" + languageRange.getTitle());
                    List<WordImpl> wordsOfLang = wordQueries.getWordsByRange(languageRange);
                    toReturn.addAll(wordsOfLang);
                }
            }
        }
        return toReturn;
    }


    private static List<Token> getListOfTokens(List<WordImpl> words) throws Exception {

        List<Token> tokens = new ArrayList<>();

        for (WordImpl word : words) {

            Token token = new Token();
            token.setIndex(word.getIndex());
            token.setToken(word.getLastFormAnnotation().getForm());


            tokens.add(token);


        }


        return tokens;


    }


}
