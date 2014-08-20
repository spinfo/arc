package de.uni_koeln.spinfo.arc.generator;

import com.mongodb.*;
import de.uni_koeln.spinfo.arc.common.DictUtils;
import de.uni_koeln.spinfo.arc.tagger.ARCTagger;
import de.uni_koeln.spinfo.arc.tagger.SursilvanTagger;
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
    @Test
    public void testVFGenerator() throws UnknownHostException {

        Sursilvan_VFGenerator vfg = new Sursilvan_VFGenerator();

        MongoClient mongoClient = new MongoClient("localhost", 27017);

        DB db = mongoClient.getDB("arc");

        DBCollection collection = db.getCollection("nvs_cleaned");

        Map<String, TreeSet<String>> VFs = vfg.generateVollForms(collection);

        System.out.println(vfg.getNumberOfDBEntries());
        System.out.println(vfg.getNumberOfVFEntries());
    }

    /**
     * testet den SursilvanTagger
     * - erzeugt die Textdatei recallTest(config).txt
     *
     * @throws IOException
     */
    @Test
    public void testTagger() throws IOException {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("arc");
        DBCollection collection = db.getCollection("nvs_20130806_merged");
        // Surselvian tokens to list

        String pathToFile = "../arc.data/testdata/XII_types-20140804_nc.txt";
        File sursFile = new File(pathToFile);
        BufferedReader in = new BufferedReader(new FileReader(sursFile));
        String tokenLine = in.readLine();
        List<String> sursTokens = new LinkedList<String>();
        while (tokenLine != null) {
            sursTokens.add(tokenLine);
            tokenLine = in.readLine();
        }

        // Get Fullforms from Sursilvan-Generator
        Sursilvan_VFGenerator gen = new Sursilvan_VFGenerator();
        Map<String, TreeSet<String>> generatedVollForms = gen
                .generateVollForms(collection);
        //create Tagger
        ARCTagger tagger = new SursilvanTagger(generatedVollForms, collection.getFullName());
        tagger.configure(new Boolean[]{true, true, true, true});
        tagger.testRecall(sursFile.getName(), sursTokens, 1000);
        Map<String, Set<String>> taggings = tagger.tag(sursTokens);

        DictUtils.printMap(taggings, "../arc.data/testdata/", "tags");


    }


    @Test
    public void testDate() {

        Date date = new Date();

        System.out.println(date.toString());

    }


    @Test
    public void testGetTokensfromXII() throws IOException {

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("arc");
        DBCollection collection = db.getCollection("word");

        DBObject rangeXII = new BasicDBObject("word_index", new BasicDBObject("$gt", 2531717).append("$lt", 2629575));

        DBCursor cursor = collection.find(rangeXII);
        List<String> last_forms_tokens = new ArrayList<>();
        Set<String> last_forms_types = new TreeSet<>();


        for (DBObject obj : cursor) {

            ArrayList<BasicDBObject> mods = (ArrayList<BasicDBObject>) obj
                    .get("modifications");

            BasicDBObject lastMod = mods.get(0);

            String lastForm = (String) lastMod.get("form");


            //lastForm = lastForm.replaceAll("[^\\p{L}]", "");

            last_forms_types.add(lastForm);
            //last_forms_tokens.add(lastForm);

        }

        DictUtils.printSet(last_forms_types, "../arc.data/testdata/", "XII_types-20140804_nc");
        //DictUtils.printList(last_forms_tokens, "../arc.data/","XII_tokens");


    }


}
