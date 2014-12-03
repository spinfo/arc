package de.uni_koeln.spinfo.arc.generator;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import de.uni_koeln.spinfo.arc.common.DictUtils;
import de.uni_koeln.spinfo.arc.common.FileUtils;
import de.uni_koeln.spinfo.arc.tagger.POSMatcher;
import de.uni_koeln.spinfo.arc.tagger.SursilvanTagger;
import de.uni_koeln.spinfo.arc.tagger.Token;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.net.UnknownHostException;
import java.util.*;

/**
 * @author geduldia
 *         <p/>
 *         Diese Klasse enthält 2 Tests:
 *         1. Test des Sursilvan_VollFormenGenerators
 *         2. Test des Sursilvan_Taggers
 */


public class SursilvanTest {


    /**
     * Testet den Sursilvan_VFGenerator (angewendet auf das NVS_Lexikon)
     * Ausgabe: Anzahl Stammformen (in der DB-Collection)
     * Anzahl der generierten Vollformen
     *
     * @throws UnknownHostException
     */


    String date = DictUtils.getISO8601StringForCurrentDate();
    private static String pathToTokensFromDB = "../../arc.data/output/words_2014-12-02T14:42:04Z";
    private static MongoClient mongoClient;
    private static DBCollection nvs_collection;
    private static DB db;
    private static String outputPath = "../../arc.data/output/";
    private static String inputPath = "../../arc.data/input/";


    @BeforeClass
    public static void init() throws Exception {

        mongoClient = new MongoClient("localhost", 27017);
        db = mongoClient.getDB("arc");
        nvs_collection = db.getCollection("nvs_20141203");

    }


    @Ignore
    @Test
    public void testVFGenerator() throws UnknownHostException {

        Sursilvan_VFGenerator vfg = new Sursilvan_VFGenerator();

        MongoClient mongoClient = new MongoClient("localhost", 27017);

        DB db = mongoClient.getDB("arc");

        DBCollection collection = db.getCollection("nvs_cleaned");

        Map<String, TreeSet<String>> VFs = vfg.generateFullforms(collection);

        System.out.println(vfg.getNumberOfDBEntries());
        System.out.println(vfg.getNumberOfVFEntries());
    }


    @Ignore
    @Test
    public void testMatchTokens() throws IOException {


        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("arc");
        DBCollection collection = db.getCollection("nvs_20141203");
        // Surselvian tokens to list

        String pathToFile = "../arc.data/input/20141127_sursilvan_tokens_list.txt";
        File sursFile = new File(pathToFile);
        BufferedReader in = new BufferedReader(new FileReader(sursFile));
        String tokenLine = in.readLine();
        List<String> sursTokens = new LinkedList<String>();

        while (tokenLine != null) {
            sursTokens.add(tokenLine);
            tokenLine = in.readLine();
        }
        System.out.println(sursTokens.get(0));
        // Get Fullforms from Sursilvan-Generator
        Sursilvan_VFGenerator gen = new Sursilvan_VFGenerator();
        Map<String, TreeSet<String>> generatedVollForms = gen
                .generateFullforms(collection);


        POSMatcher matcher = new SursilvanTagger(generatedVollForms, collection.getFullName());
        matcher.configure(new Boolean[]{true, true, true, true});


        Map<String, Set<String>> matches = matcher.match(sursTokens);
        DictUtils.printMap(matches, "../arc.data/output/", "matches_" + date);


    }


    //@Ignore
    @Test
    public void testMatchTokensSerialized() throws Exception {

        Map<String, TreeSet<String>> fullForms = readFullForms(outputPath + "fullforms_2014-12-03T15:17:03Z");


        POSMatcher matcher = new SursilvanTagger(fullForms, nvs_collection.getFullName());
        matcher.configure(new Boolean[]{true, true, true, true});

        ArrayList<Token> matches = matcher.matchTokensWithPOS(FileUtils.getListOfTokens(pathToTokensFromDB));
        DictUtils.printList(matches, "../../arc.data/output/", "wordsWithPOS_" + date);
        //FileUtils.writeList(matches, "matchedWords_");
    }


    @Ignore
    @Test
    public void getFullForms() throws Exception {

        Map<String, TreeSet<String>> fullForms = generatefullForms();
        writeFullforms(fullForms);


    }

    private static Map<String, TreeSet<String>> generatefullForms() throws UnknownHostException {


        // Get Fullforms from Sursilvan-Generator
        Sursilvan_VFGenerator gen = new Sursilvan_VFGenerator();
        Map<String, TreeSet<String>> fullForms = gen
                .generateFullforms(nvs_collection);


        return fullForms;

    }


    private static Map<String, TreeSet<String>> readFullForms(String pathToFile) throws Exception {

        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(outputPath + pathToFile));

        Map<String, TreeSet<String>> fullForms = (Map<String, TreeSet<String>>) inputStream.readObject();

        return fullForms;
    }


    private static <K, V> void writeFullforms(Map<K, V> fullForms) throws IOException {

        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(outputPath + "fullforms_" + DictUtils.getISO8601StringForCurrentDate()));

        outputStream.writeObject(fullForms);

        outputStream.close();

    }


//    /**
//     * testet den SursilvanTagger
//     * - erzeugt die Textdatei recallTest(config).txt
//     *
//     * @throws IOException
//     */
//    @Ignore
//    @Test
//    public void testMatcherAndRecall() throws IOException {
//
//        MongoClient mongoClient = new MongoClient("localhost", 27017);
//        DB db = mongoClient.getDB("arc");
//        DBCollection collection = db.getCollection("nvs_20140812");
//        // Surselvian tokens to list
//
//        String pathToFile = "../arc.data/input/20141124_sursilvan_tokens_list.txt";
//        File sursFile = new File(pathToFile);
//        BufferedReader in = new BufferedReader(new FileReader(sursFile));
//        String tokenLine = in.readLine();
//        List<String> sursTokens = new LinkedList<String>();
//        while (tokenLine != null) {
//            sursTokens.add(tokenLine);
//            tokenLine = in.readLine();
//        }
//
//        // Get Fullforms from Sursilvan-Generator
//        Sursilvan_VFGenerator gen = new Sursilvan_VFGenerator();
//        Map<String, TreeSet<String>> generatedVollForms = gen
//                .generateFullforms(collection);
//        //create Tagger
//        POSMatcher matcher = new SursilvanTagger(generatedVollForms, collection.getFullName());
//        matcher.configure(new Boolean[]{true, true, true, true});
//        matcher.testRecall(sursFile.getName(), sursTokens, 1000);
//        Map<String, Set<String>> taggings = matcher.match(sursTokens);
//
//
//        DictUtils.printMap(taggings, "../arc.data/output/", "matches_" + date);
//
//
//    }


}
