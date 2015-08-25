package de.uni_koeln.spinfo.arc.generator;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import de.uni_koeln.spinfo.arc.matcher.POSMatcher;
import de.uni_koeln.spinfo.arc.matcher.SurmiranMatcher;
import de.uni_koeln.spinfo.arc.utils.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;

import static de.uni_koeln.spinfo.arc.generator.ValladerTest.*;

/**
 * @author geduldia
 *         <p/>
 *         Diese Klasse enthält 2 Tests:
 *         1. Test des Surmiran_VollFormenGenerators
 *         2. Test des Surmiran_Taggers
 */

public class SurmiranTest {

    String date = FileUtils.getISO8601StringForCurrentDate();
    private static String pathToTokensFromDB = "";
    private static MongoClient mongoClient;
    private static DBCollection dictCollection;
    private static DB db;

    @BeforeClass
    public static void init() throws Exception {

        mongoClient = new MongoClient("localhost", 27017);
        db = mongoClient.getDB("dicts");
        dictCollection = db.getCollection("surmiran");

    }

    /**
     * Testet den Surmiran_VFGenerator (angewendet auf das Signorelli_Lexikon)
     * Ausgabe: Anzahl Stammformen (in der DB-Collection)
     * Anzahl der generierten Vollformen
     *
     * @throws UnknownHostException
     */
    @Test
    public void testVFGenerator() throws UnknownHostException {
        Surmiran_VFGenerator vfg = new Surmiran_VFGenerator();

        MongoClient mongoClient = new MongoClient("localhost", 27017);

        DB db = mongoClient.getDB("arc");

        DBCollection collection = db.getCollection(    /**------> Hier fehlt noch die Angabe des Collectionnames (Signorelli)**/"");
        Map<String, TreeSet<String>> VFs = vfg.generateVollForms(collection);
        for (String entry : VFs.keySet()) {
            System.out.println(entry + ":  ");
            for (String Pos : VFs.get(entry)) {
                System.out.println(Pos);
            }
        }
        System.out.println(vfg.getNumberOfDBEntries());
        System.out.println(vfg.getNumberOfVFEntries());
    }

    @Test
    public void showStats() throws IOException {

        Surmiran_VFGenerator sfg = new Surmiran_VFGenerator();

        Map<String, TreeSet<String>> VFs = sfg.generateVollForms(dictCollection);

        System.out.println(sfg.getNumberOfDBEntries());
        System.out.println(sfg.getNumberOfVFEntries());

        ValladerTest.extendedStats(VFs);
    }

    /**
     * testet den SurmiranTagger
     * - erzeugt die Textdatei recallTest(config).txt
     *
     * @throws IOException
     */
    @Test
    public void testTagger() throws IOException {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("arc");
        DBCollection collection = db.getCollection(/**------> Hier fehlt noch die Angabe des Collectionnames (Signorelli)**/"");
        // Surmiran tokens to list

        String pathToFile = "../arc.data/testdata/surmiran_tokens.txt";
        File sursFile = new File(pathToFile);
        BufferedReader in = new BufferedReader(new FileReader(sursFile));
        String tokenLine = in.readLine();
        List<String> surmTokens = new LinkedList<String>();
        while (tokenLine != null) {
            surmTokens.add(tokenLine);
            tokenLine = in.readLine();
        }

        // Get Fullforms from Surmiran-Generator
        Surmiran_VFGenerator gen = new Surmiran_VFGenerator();
        Map<String, TreeSet<String>> generatedVollForms = gen
                .generateVollForms(collection);
        //create Tagger
        POSMatcher tagger = new SurmiranMatcher(generatedVollForms, collection.getFullName());
        tagger.configure(new Boolean[]{true, true, true, true});
        tagger.testRecall(pathToFile, surmTokens, 50);
        Map<String, Set<String>> taggings = tagger.match(surmTokens);
        in.close();

    }
}
