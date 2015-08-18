package de.spinfo.arc.persistance.repository.pos;

import de.spinfo.arc.annotationmodel.annotatable.WorkingUnit;
import de.spinfo.arc.annotationmodel.annotatable.impl.WordImpl;
import de.spinfo.arc.annotationmodel.annotation.LanguageRange;
import de.spinfo.arc.data.IOMongo;
import de.spinfo.arc.persistance.service.query.WordQueries;
import de.spinfo.arc.persistance.service.query.WorkingUnitQueries;
import de.uni_koeln.spinfo.arc.matcher.Token;
import de.uni_koeln.spinfo.arc.utils.FileUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by franciscomondaca on 20/7/15.
 */
public class getWordsTest {


    static WorkingUnitQueries wuQueries = new WorkingUnitQueries();
    static WordQueries wordQueries = new WordQueries();


    @Test
    public void testWordstoFile() throws Exception {

        String language = "Puter";

        List<WordImpl> gw = getWordsByLanguage(language);

        List<Token> tokens = getListOfTokens(gw);

        FileUtils.writeList(tokens, language +"_words_");
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
            token.setToken(word.getFirstAnnotation().getForm());


            tokens.add(token);


        }


        return tokens;


    }

}
