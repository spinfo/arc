package de.uni_koeln.spinfo.arc.generator;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import de.uni_koeln.spinfo.arc.matcher.POSMatcher;
import de.uni_koeln.spinfo.arc.matcher.SutsilvanMatcher;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

        Map<String, TreeSet<String>> VFs = vfg.generateVollForms(collection);

        System.out.println(vfg.getNumberOfDBEntries());
        System.out.println(vfg.getNumberOfVFEntries());
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
                .generateVollForms(collection);
        //create Tagger
        POSMatcher tagger = new SutsilvanMatcher(generatedVollForms, collection.getFullName());
        tagger.configure(new Boolean[]{true, true, true, true});
        tagger.testRecall(pathToFile, sutsTokens, 50);
        Map<String, Set<String>> taggings = tagger.match(sutsTokens);
        in.close();
    }

}

