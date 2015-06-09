package de.spinfo.arc.persistance.repository.pos;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import de.spinfo.arc.data.*;
import de.uni_koeln.spinfo.arc.utils.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class TestGettingWordsByAspecificLanguage {

    static IOMongo ioMongo = new IOMongo();

//    static WorkingUnitQueries wuQueries = new WorkingUnitQueries();
//    static WordQueries wordQueries = new WordQueries();
//    static WordUpdater wordUpdater = new WordUpdater();


    private static MongoClient mongoClient;

    @BeforeClass
    public static void initialize() throws UnsupportedEncodingException,
            FileNotFoundException, UnknownHostException {

        mongoClient = new MongoClient("localhost", 27017);
    }


    //@Ignore
    @Test
    public void getGoldenTokens() throws IOException {


        List<Entry> entries = ioMongo.getSursilvanGoldenEntries();

        FileUtils.writeList(entries, "golden");
        FileUtils.printList(entries, FileUtils.outputPath, "golden");

    }

    @Test
    public void testGetTokensForStand() throws Exception {

        List<ForStand> getTokens = ioMongo.getTokensForStand("golden2015-04-01T12:44:58Z");

        FileUtils.printList(getTokens, FileUtils.outputPath, "test_list_");


    }


    @Test
    public void testGetPageNumberInWU() {

        ioMongo.getPageNumberInWU("Band II", 365059);


    }


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


}
