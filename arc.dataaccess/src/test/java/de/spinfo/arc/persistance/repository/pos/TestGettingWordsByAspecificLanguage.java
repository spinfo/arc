package de.spinfo.arc.persistance.repository.pos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import de.spinfo.arc.annotationmodel.annotatable.WorkingUnit;
import de.spinfo.arc.annotationmodel.annotatable.impl.WordImpl;
import de.spinfo.arc.annotationmodel.annotation.LanguageRange;
import de.spinfo.arc.annotationmodel.annotation.PageRange;
import de.spinfo.arc.data.Entry;
import de.spinfo.arc.data.ForStan;
import de.spinfo.arc.data.IOMongo;
import de.spinfo.arc.data.LangRange;
import de.spinfo.arc.data.MongoWord;
import de.spinfo.arc.persistance.service.query.WordQueries;
import de.spinfo.arc.persistance.service.query.WorkingUnitQueries;
import de.uni_koeln.spinfo.arc.matcher.Token;
import de.uni_koeln.spinfo.arc.utils.CrossvalidationGroupBuilder;
import de.uni_koeln.spinfo.arc.utils.FileUtils;
import de.uni_koeln.spinfo.arc.utils.TrainingTestSets;

public class TestGettingWordsByAspecificLanguage {
    static WorkingUnitQueries wuQueries = new WorkingUnitQueries();
    static WordQueries wordQueries = new WordQueries();
    static IOMongo ioMongo = new IOMongo();


    private static MongoClient mongoClient;

    @BeforeClass
    public static void initialize() throws UnsupportedEncodingException,
            FileNotFoundException, UnknownHostException {

        //mongoClient = new MongoClient("localhost", 27017);
    }


    @Test
    public void testGetLastTokenInPage() {

        System.out.println(ioMongo.getLastTokenInPage("Band II", 19));

    }


    @Test
    public void getGoldenTokens() throws IOException {


        List<Entry> entries = ioMongo.getSursilvanGoldenData("Band II", 19);

        FileUtils.writeList(entries, "golden");
        FileUtils.printList(entries, FileUtils.outputPath, "golden");

    }




    @Test
    public void testregex() throws IOException {


        String s = "per|PREP";

        String[] array = s.split("\\|");
        System.out.println(s);
        System.out.println(array[0]);


    }


    @Test
    public void getNOTTAGGED() throws Exception {

        List<ForStan> sursilvantokens = ioMongo.getTokensForStand("golden2015-05-12T10:21:44Z");
        WorkingUnit workingUnit = wuQueries.getWorkingUnit("Band II");

        System.out.println(workingUnit.getStart());
        System.out.println(workingUnit.getEnd());
        List<PageRange> pages = workingUnit.getPages();
        List<String> not_tagged = new ArrayList<>();


        for (ForStan s : sursilvantokens) {

            String pos = s.getPOS();

            if (pos.equals("NOT_TAGGED")) {

                StringBuffer buffer = new StringBuffer();
                buffer.append(s.getForm());
                buffer.append("\t");
                buffer.append(s.getPOS());
                buffer.append("\t");
                //buffer.append(s.getWord_index());
                //buffer.append("\t");
                //System.out.println(s.getIndex());

                for (PageRange r : pages) {

                    //System.out.println(r.toString());

                    long start = r.getStart();
                    long end = r.getEnd();


                    if (s.getWord_index() >= start && s.getWord_index() <= end) {
                        buffer.append("Seite N°: ");
                        buffer.append(pages.indexOf(r));

                    }

                }

                not_tagged.add(buffer.toString());


            }

        }


        FileUtils.printList(not_tagged, FileUtils.outputPath, "NOT_TAGGED_");

    }


    @Test
    public void testProcessNOTTagged() throws Exception {

        List<ForStan> sursilvantokens = ioMongo.getTokensForStand("golden2015-06-22T10:28:04Z");
        List<ForStan> toReturn = new ArrayList<>();

        int index = 0;
        for (ForStan fs : sursilvantokens) {

            if (fs.getPOS().equals("NOT_TAGGED")) {


                String form = fs.getForm();

                String[] array = form.split("\\s+");

                for (String s : array) {

                    ForStan n = new ForStan();
                    n.setWord_index(fs.getWord_index());
                    //n.setIndex(index);
                    n.setForm(s);
                    n.setPOS(fs.getPOS());

                    toReturn.add(n);

                    index++;
                }


            } else {


                ForStan n = new ForStan();
                n.setWord_index(fs.getWord_index());
                //n.setIndex(index);
                n.setForm(fs.getForm());
                n.setPOS(fs.getPOS());

                toReturn.add(fs);
                index++;
            }


        }


        FileUtils.printList(toReturn, FileUtils.outputPath, "forstand_");


    }

    @Test
    public void testGetTokensForStand() throws Exception {


        List<String> lines = Files.readAllLines(Paths.get(FileUtils.outputPath + "forstand_2015-05-13T11:23:02Z.txt"),
                Charset.defaultCharset());

        List<ForStan> withoutNulls = new ArrayList<>();

        //List<ForStand> sursilvantokens = ioMongo.getTokensForStand("golden2015-05-12T11:55:35Z");

        List<ForStan> sursilvantokens = new ArrayList<>();

        for (String s : lines) {

            String[] array = s.split("\\t");

            ForStan fs = new ForStan();
            fs.setWord_index(Long.parseLong(array[0]));
            fs.setForm(array[1]);
            fs.setPOS(array[2]);


            sursilvantokens.add(fs);


        }

//
//        for (ForStand stand : sursilvantokens) {
//
//            if (stand.getForm().equals("“") || stand.getForm().equals("„") || stand.getForm().equals("\"")
//                    // || stand.getForm().equals(",") || stand.getForm().equals(";") || stand.getForm().equals(":")
//                    || stand.getPOS().equals("NULL")) {
//
//                continue;
//
//            } else {
//
//                withoutNulls.add(stand);
//            }
//
//        }
//
//        withoutNulls.remove(0);


        List<List<String>> sentences = new ArrayList<>();
        List<String> words = new ArrayList<>();


        for (ForStan stand : sursilvantokens) {
            words.add(stand.getForm().replaceAll("\\s+", "") + "|" + stand.getPOS());


            if (stand.getPOS().endsWith("EOS")) {

                sentences.add(words);
                System.out.println(words);

                System.out.println("\n");
                words = new ArrayList<>();
            }


        }


        CrossvalidationGroupBuilder<List<String>> cvgb = new CrossvalidationGroupBuilder<List<String>>(sentences, 10);

        Iterator<TrainingTestSets<List<String>>> iterator = cvgb.iterator();

        int index = 1;

        while (iterator.hasNext()) {

            TrainingTestSets<List<String>> test = iterator.next();

            List<List<String>> trainingSet = test.getTrainingSet();
            List<List<String>> testSet = test.getTestSet();


            FileUtils.printForStand(trainingSet, "training", index);
            FileUtils.printForStand(testSet, "test", index);


            index++;
        }


    }


    @Test
    public void testGetPageNumberInWU() {

        ioMongo.getPageNumberInWU("Band II", 367843);


    }

    //use for getting all the words from the RC
    @Test
    public void testGetWords() throws IOException {

        String dbName = "arc_working_units";
        String collectionName = "workingUnits";

        DB db = mongoClient.getDB(dbName);
        DBCollection collection = db.getCollection(collectionName);

        List<LangRange> list = ioMongo.getLanguageRanges(collection);


        for (LangRange lr : list) {

            System.out.println(lr);
        }

        DBCollection wordsCollection = db.getCollection("words");

        Map<String, List<MongoWord>> getWordsInRange = ioMongo.getWords(list, wordsCollection);

        FileUtils.writeMap(getWordsInRange, "wordsInRange");


    }


    @Test
    public void testGetTokens() throws IOException {

        String dbName = "arc_working_units";
        String collectionName = "workingUnits";

        DB db = mongoClient.getDB(dbName);
        DBCollection collection = db.getCollection(collectionName);

        List<LangRange> list = ioMongo.getLanguageRanges(collection);


        for (LangRange lr : list) {

            System.out.println(lr);
        }

        DBCollection wordsCollection = db.getCollection("words");

        Map<String, List<String>> getWordsInRange = ioMongo.getTokens(list, wordsCollection);

        FileUtils.writeMap(getWordsInRange, "tokens_");


    }


    @Test
    public void testGetTypes() throws Exception {


        Map<String, List<String>> tokens = ioMongo.readTokens("tokens_2015-04-21T14:20:45Z");

        Map<String, Set<String>> types = ioMongo.getTypes(tokens);


        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumFractionDigits(2);

        for (Map.Entry<String, Set<String>> entry : types.entrySet()) {

            String lang = entry.getKey();
            Set<String> tps = entry.getValue();
            String size_format = numberFormat.format(tps.size());

            System.out.println(lang + "\t" + size_format);

        }


    }


    @Test
    public void testGetIntersections() throws Exception {


        Map<String, List<String>> tokens = ioMongo.readTokens("tokens_2015-04-21T14:20:45Z");

        Map<String, Set<String>> types = ioMongo.getTypes(tokens);

        Map<String, Set<String>> intersections = ioMongo.getIntersections(types);


        System.out.println(types.size());
        System.out.println(intersections.size());
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumFractionDigits(2);

        for (Map.Entry<String, Set<String>> entry : intersections.entrySet()) {

            String lang = entry.getKey();
            Set<String> tps = entry.getValue();
            String size_format = numberFormat.format(tps.size());

            System.out.println(lang + "\t" + size_format);

        }


    }


    @Test
    public void testGetCombinedQuantities() throws Exception {


        Map<String, List<String>> tokens = ioMongo.readTokens("tokens_2015-04-21T14:20:45Z");

        Map<String, Set<String>> types = ioMongo.getTypes(tokens);

        Map<String, Set<String>> cqs = ioMongo.getCombinedQuantities(types);


        System.out.println(types.size());
        System.out.println(cqs.size());
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumFractionDigits(2);

        for (Map.Entry<String, Set<String>> entry : cqs.entrySet()) {

            String lang = entry.getKey();
            Set<String> tps = entry.getValue();
            String size_format = numberFormat.format(tps.size());

            System.out.println(lang + "\t" + size_format);

        }


    }


    @Test
    public void printClassTokens() throws Exception {
        String dbName = "arc_working_units";
        DB db = mongoClient.getDB(dbName);
        DBCollection collection = db.getCollection("words");

        Map<String, List<MongoWord>> getWordsInRange = ioMongo.readMap("wordsInRange2015-04-20T15:26:40Z");

        double words = collection.count();

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumFractionDigits(2);

        double classifiedTokens = 0;

        for (Map.Entry<String, List<MongoWord>> entry : getWordsInRange.entrySet()) {

            String language = entry.getKey();

            List<MongoWord> tokens = entry.getValue();

            double percentage = tokens.size() * 100 / words;
            String percentage_format = numberFormat.format(percentage);

            String size_format = numberFormat.format(tokens.size());

            System.out.println(language + "\t" + size_format + "\t" + percentage_format);

            classifiedTokens = classifiedTokens + tokens.size();

        }
        double saldo = words - classifiedTokens;
        double saldo_per = saldo * 100 / words;
        double found_per = classifiedTokens * 100 / words;

        String saldo_format = numberFormat.format(saldo_per);
        String found_format = numberFormat.format(found_per);


        System.out.println("CLASSIFIED: " + "\t" + classifiedTokens + "\t" + found_format);
        System.out.println("TOTAL: " + "\t" + words);


        System.out.println("NOT CLASSIFIED: " + "\t" + saldo + "\t" + saldo_format);

    }


    @Test
    public void testGetAllTokensInChrest() throws Exception {
        String dbName = "arc_working_units";
        DB db = mongoClient.getDB(dbName);
        DBCollection collection = db.getCollection("words");
        List<String> list = ioMongo.getAllTokensInChrestomathie(collection);

        FileUtils.writeList(list, "allTokens_");


    }

    @Test
    public void testGetAllTypes() throws Exception {

        List<String> tokens = ioMongo.readAllTokens("allTokens_2015-04-21T14:52:34Z");
        System.out.println("TOKENS: " + tokens.size());
        Set<String> types = new TreeSet<>(tokens);
        System.out.println("TYPES: " + types.size());

    }




    @Test
    public void testWordstoFile() throws Exception {

        String sursilvan = "Sursilvan";

        List<WordImpl> gw = getWordsByLanguage(sursilvan);

        List<Token> tokens = getListOfTokens(gw);

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
            token.setToken(word.getFirstAnnotation().getForm());


            tokens.add(token);


        }


        return tokens;


    }


}
