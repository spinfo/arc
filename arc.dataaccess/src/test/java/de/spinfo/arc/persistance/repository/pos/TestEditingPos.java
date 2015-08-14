package de.spinfo.arc.persistance.repository.pos;

import de.spinfo.arc.annotationmodel.annotatable.Word;
import de.spinfo.arc.annotationmodel.annotatable.WorkingUnit;
import de.spinfo.arc.annotationmodel.annotatable.impl.WordImpl;
import de.spinfo.arc.annotationmodel.annotation.Annotation.AnnotationTypes;
import de.spinfo.arc.annotationmodel.annotation.ChapterRange;
import de.spinfo.arc.annotationmodel.annotation.PageRange;
import de.spinfo.arc.annotationmodel.annotation.PosAnnotation;
import de.spinfo.arc.annotationmodel.annotation.impl.PosAnnotationImpl;
import de.spinfo.arc.persistance.service.query.WordQueries;
import de.spinfo.arc.persistance.service.query.WorkingUnitQueries;
import de.spinfo.arc.persistance.service.update.WordUpdater;
import de.spinfo.arc.persistance.util.PosChecker;
import de.uni_koeln.spinfo.arc.utils.FileUtils;
import de.uni_koeln.spinfo.arc.matcher.Token;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestEditingPos {

    private static WordUpdater wordUpdater;
    private static WordQueries wordQueries;

    private String vallader = "vallader_matchedWords_2015-08-14T17:22:38Z";
    private String puter = "puter_matchedWords_2015-08-14T17:27:12Z";

    @BeforeClass
    public static void init() {
        wordUpdater = new WordUpdater();
        wordQueries = new WordQueries();
    }


    //@Ignore
    @Test
    public void testAddPOSAlternatives() throws Exception {
        List<Token> tokens = getListOfTokens(puter);
        for (Token token : tokens) {

            long index = token.getIndex();
            Set<String> posSet = token.getPos();

            //Add all the options from the matching
            wordUpdater.pushPosTaggerOptionsAsStrings(index, posSet);
        }
    }


    //@Ignore
    @Test
    public void testWriteOnePOSFromAlternatives() throws Exception {

        List<Token> tokens = getListOfTokens(puter);
        for (Token token : tokens) {

            long index = token.getIndex();
            Set<String> posSet = token.getPos();
            Iterator<String> iterator = posSet.iterator();

            // Avoid 'NOT_TAGGED'

            if (posSet.size() == 1) {
                wordUpdater.pushPos(index, new PosAnnotationImpl(new Date(), 0, "matcher", PosAnnotation.PosTags.valueOf(iterator.next())));

            } else {

                for (String s : posSet) {

                    //If there are more options than NN
                    if (!s.equals("NN")) {
                        wordUpdater.pushPos(index, new PosAnnotationImpl(new Date(), 0, "matcher", PosAnnotation.PosTags.valueOf(s)));
                        break;
                    }
                }
            }
        }

    }



    @Ignore
    @Test
    public void testCommonWuAndRangeRetrieval() {

        /**
         * Querying for Working Units
         *
         */
        WorkingUnitQueries wuQueries = new WorkingUnitQueries();
        List<WorkingUnit> allWorkingUnits = wuQueries.getAllWorkingUnits();

        assertEquals("Are all 14 Working units retrieved?", 14, allWorkingUnits.size());

        WorkingUnit retrievedWu = wuQueries.getWorkingUnit("Band I");
        assertEquals("Is the demanded Working Unit retrievable by its title",
                "Band I",
                retrievedWu.getTitle());
        /* The following two ways of retrieving chapter ranges are equal.
         * the first one is using the convenience method of the working unit type  */
        assertEquals("Is correct amount of Chapters available by convenince access",
                153,
                retrievedWu.getChapters().size());
        /*.. and the second one is unsing the Enum-Key for the annotation map */
        assertEquals("Is correct amount of Chapters available by map access",
                153,
                retrievedWu.getAnnotationsOfType(AnnotationTypes.CHAPTER_RANGE).size());

		/* The following to ways of retrieving language ranges are equal.
         * the first one is using the convenience method of the working unit type
		 * The Language versions are commented because in the original versin they haven't been annotated yet */
//		assertEquals("Is correct amount of Chapters available by convenince access",
//				153,
//				retrievedWu.getLanguages().size());
        /*.. and the second one is unsing the Enum-Key for the annotation map */
//		assertEquals("Is correct amount of Chapters available by map access",
//				153,
//				retrievedWu.getAnnotationsOfType(AnnotationTypes.LANGUAGE_RANGE).size());

        /**
         *  Querying for Ranges
         */
        String TITLE_OF_FIRST_CHAPTER = "Daniel Bonifaci";
        /* Getting the first chapter of a working unit*/
        ChapterRange firstChapter = retrievedWu.getChapters().get(0);

        assertEquals("The first title must be " + TITLE_OF_FIRST_CHAPTER,
                TITLE_OF_FIRST_CHAPTER, firstChapter.getTitle());

		/* Getting a Chapter with a special title*/
        List<ChapterRange> results = new ArrayList<>();

        for (ChapterRange ch : retrievedWu.getChapters())
            if (ch.getTitle().equals(TITLE_OF_FIRST_CHAPTER))
                results.add(ch);

        assertEquals("There mustn't be more than one hit of the first chapters Title",
                1, results.size());
        /**
         * Getting all Words of a Range
         */
        WordQueries wordQueries = new WordQueries();

        WordUpdater wordUpdater = new WordUpdater();
        List<WordImpl> wordsByChapter = wordQueries.getWordsByRange(firstChapter);
        assertEquals("Are the expected amount of Words of the first Chapter retrieved?", 3335, wordsByChapter.size());
        /* Iterating over theFORMS of the words. The 10th word of the first chapter of wu "Band I" is Ludvid..
         * Lets take this for example and add some POS Tagger Options*/
        for (WordImpl word : wordsByChapter) {
            /*
             * IMPORTANT
			 * Get the most current FormAnnotation 
			 * Due to the stack of kinds of Annotations the most currentOne is reachable at the END of the list
			 * by using the word.getLastFormAnnotation()
			 * */
            if (word.getLastFormAnnotation().getForm().equals("Lvdvig")) {
                /* Clear all pos tagger option forehand in order to make this test work*/
                wordUpdater.clearPosTaggerOption(word.getIndex());
                /*2 Example of upsdating the pos options. Note that these options stem from
                 * hardcoded enumerations but are essential Strings */
                // PosChecker.checkIfValid("NN") - This is just a static checker -
                // if this fails the passed Postag String is not conform with the POS-Enums (throws exception)
                // otherwise the valid String will be returned
                String posOptionToAdd_1 = "NN";
                String posOptionToAdd_2 = "PRON_REF";
                String posOptionToAdd_3 = "CONJ_S";
                /*(1) Appending to exisitng pos-options */
                wordUpdater.pushPosTaggerOption(word.getIndex(), PosChecker.checkIfValid(posOptionToAdd_1));
                /*(2) Setting the options with a Set<String>*/
                Set<String> posOptionsSet = new HashSet<>();
                posOptionsSet.add(posOptionToAdd_2);
                posOptionsSet.add(posOptionToAdd_3);
                for (String string : posOptionsSet) {
                    PosChecker.checkIfValid(string);
                }
                wordUpdater.pushPosTaggerOptionsAsStrings(word.getIndex(), posOptionsSet);


                assertTrue("The newly set option must be in the DB",
                        wordQueries.getWordAt(word.getIndex()).getTaggerPosOptions().contains(posOptionToAdd_1));
                assertTrue("The newly set options must be in the DB",
                        wordQueries.getWordAt(word.getIndex()).getTaggerPosOptions().containsAll(posOptionsSet));
//				 Finally delete all posTaggerOptions
                wordUpdater.clearPosTaggerOption(word.getIndex());
            }
        }
        /**
         * some more ways to navigate to certain words
         */
        // gets the first word of the second chapter equals getting the end index of the first plus 1
        Word word = wordQueries.getWordAt(firstChapter.getEnd() + 1);
        // Getting the start of the second Chapter
        ChapterRange secondChapter = retrievedWu.getChapters().get(1);
        assertEquals("The word at the end of the first chapter plus 1 must be equal the first word of the second chapter",
                word.getLastFormAnnotation(), wordQueries.getWordAt(secondChapter.getStart()).getLastFormAnnotation());


    }

    private static List<Token> getListOfTokens(String fileName) throws Exception {

        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FileUtils.outputPath + fileName));

        List<Token> tokens = (List<Token>) inputStream.readObject();

        inputStream.close();

        return tokens;


    }


}
