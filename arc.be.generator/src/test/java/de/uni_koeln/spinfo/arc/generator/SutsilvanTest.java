package de.uni_koeln.spinfo.arc.generator;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import de.uni_koeln.spinfo.arc.matcher.POSMatcher;
import de.uni_koeln.spinfo.arc.matcher.SurmiranMatcher;
import de.uni_koeln.spinfo.arc.matcher.SutsilvanMatcher;
import de.uni_koeln.spinfo.arc.matcher.Token;
import de.uni_koeln.spinfo.arc.utils.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.net.UnknownHostException;
import java.util.*;


/**
 * @author geduldia
 *         <p/>
 *         Diese Klasse enthält 2 Tests:
 *         1. Test des Sutsilvan_VollFormenGenerators
 *         2. Test des Sutsilvan_Taggers
 */
public class SutsilvanTest {

    String date = FileUtils.getISO8601StringForCurrentDate();
    private static String pathToTokensFromDB = "../../arc.data/output/Sutsilvan_words_2015-08-31T14:21:09Z";
    private static MongoClient mongoClient;
    private static DBCollection dictCollection;
    private static DB db;

    @BeforeClass
    public static void init() throws Exception {

        mongoClient = new MongoClient("localhost", 27017);
        db = mongoClient.getDB("dicts");
        dictCollection = db.getCollection("sutsilvan");

    }


    /**
     * Testet den Sutsilvan_VFGenerator (angewendet auf das Einchenhofer-Lexikon)
     * Ausgabe: Anzahl Stammformen (aus der DB-Collection)
     * Anzahl der generierten Vollformen
     *
     * @throws UnknownHostException
     */
    @Test
    public void testVFGenerator() throws UnknownHostException {
        Sutsilvan_VFGenerator vfg = new Sutsilvan_VFGenerator();

        MongoClient mongoClient = new MongoClient("localhost", 27017);

        DB db = mongoClient.getDB("arc");

        DBCollection collection = db.getCollection(/**------> Hier fehlt noch die Angabe des Collectionnames (Eichenhofer)**/"");

        Map<String, TreeSet<String>> VFs = vfg.generateFullforms(collection);

        System.out.println(vfg.getNumberOfDBEntries());
        System.out.println(vfg.getNumberOfVFEntries());
    }

    @Test
    public void showStats() throws IOException {

        Sutsilvan_VFGenerator sfg = new Sutsilvan_VFGenerator();

        Map<String, TreeSet<String>> VFs = sfg.generateFullforms(dictCollection);

        System.out.println(sfg.getNumberOfDBEntries());
        System.out.println(sfg.getNumberOfVFEntries());

       // ValladerTest.extendedStats(VFs);
    }


    //@Ignore
    @Test
    public void getFullForms() throws Exception {

        Map<String, TreeSet<String>> fullForms = generateFullforms();


        FileUtils.writeFullforms(fullForms, "sutsilvan_");
        FileUtils.printMap(fullForms, "../arc.data/output/", "sutsilvan_fullForms_");

    }

    private static Map<String, TreeSet<String>> generateFullforms()
            throws UnknownHostException {

        // Get Fullforms from Generator
        Sutsilvan_VFGenerator gen = new Sutsilvan_VFGenerator();
        Map<String, TreeSet<String>> fullForms = gen
                .generateFullforms(dictCollection);

        fullForms.remove("");


        return fullForms;

    }

    @Test
    public void testMatchTokensSerialized() throws Exception {

        Map<String, TreeSet<String>> fullForms = FileUtils.readFullForms("sutsilvan_fullforms_2015-08-31T17:05:40Z");

        POSMatcher matcher = new SutsilvanMatcher(fullForms,
                dictCollection.getFullName());
        matcher.configure(new Boolean[]{true, true, true, true});

        List<Token> sutsilvanTokens = getListOfTokens(pathToTokensFromDB);

        ArrayList<Token> matches = (ArrayList<Token>) matcher
                .matchTokensWithPOS(sutsilvanTokens, "sutsilvan");


        System.out.println("SUTSILVAN_TOKENS: " + sutsilvanTokens.size());
        System.out.println("MATCHES: " + matches.size() + "\t - " + matches.size() * 100 / sutsilvanTokens.size()+"%");

        FileUtils.writeList(matches, "sutsilvan_matchedWords_");
        FileUtils.printList(matches, FileUtils.outputPath, "sutsilvan_matchedWords_");
    }




    /**
     * testet den SutsilvanTagger
     * erzeugt die Textdatei recallTest(config).txt
     *
     * @throws IOException
     */
    @Test
    public void testTagger() throws IOException {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("arc");
        DBCollection collection = db.getCollection(/**------> Hier fehlt noch die Angabe des Collectionnames (Eichenhofer)**/"");
        // Sutsilvan tokens to list

        String pathToFile = "../arc.data/testdata/surmiran_tokens.txt";
        File sursFile = new File(pathToFile);
        BufferedReader in = new BufferedReader(new FileReader(sursFile));
        String tokenLine = in.readLine();
        List<String> sutsTokens = new LinkedList<String>();
        while (tokenLine != null) {
            sutsTokens.add(tokenLine);
            tokenLine = in.readLine();
        }

        // Get Fullforms from Sutsilvan-Generator
        Sutsilvan_VFGenerator gen = new Sutsilvan_VFGenerator();
        Map<String, TreeSet<String>> generatedVollForms = gen
                .generateFullforms(collection);
        //create Tagger
        POSMatcher tagger = new SutsilvanMatcher(generatedVollForms, collection.getFullName());
        tagger.configure(new Boolean[]{true, true, true, true});
        tagger.testRecall(pathToFile, sutsTokens, 50);
        Map<String, Set<String>> taggings = tagger.match(sutsTokens);
        in.close();
    }

    private static List<Token> getListOfTokens(String fileName)
            throws Exception {

        ObjectInputStream inputStream = new ObjectInputStream(
                new FileInputStream(FileUtils.outputPath + fileName));

        List<Token> tokens = (List<Token>) inputStream.readObject();

        inputStream.close();

        return tokens;

    }

}

