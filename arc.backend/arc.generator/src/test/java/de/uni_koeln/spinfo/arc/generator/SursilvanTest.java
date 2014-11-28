package de.uni_koeln.spinfo.arc.generator;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import de.uni_koeln.spinfo.arc.common.DictUtils;
import de.uni_koeln.spinfo.arc.tagger.POSMatcher;
import de.uni_koeln.spinfo.arc.tagger.SursilvanTagger;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Ignore
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
    @Ignore
    @Test
    public void testMatcherAndRecall() throws IOException {

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("arc");
        DBCollection collection = db.getCollection("nvs_20140812");
        // Surselvian tokens to list

        String pathToFile = "../arc.data/input/20141124_sursilvan_tokens_list.txt";
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
        POSMatcher matcher = new SursilvanTagger(generatedVollForms, collection.getFullName());
        matcher.configure(new Boolean[]{true, true, true, true});
        matcher.testRecall(sursFile.getName(), sursTokens, 1000);
        Map<String, Set<String>> taggings = matcher.match(sursTokens);


        DictUtils.printMap(taggings, "../arc.data/output/", "matches_" + date);


    }

    //@Ignore
    @Test
    public void testMatchTokens() throws IOException {


        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("arc");
        DBCollection collection = db.getCollection("nvs_20140812");
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
                .generateVollForms(collection);


        POSMatcher matcher = new SursilvanTagger(generatedVollForms, collection.getFullName());
        matcher.configure(new Boolean[]{true, true, true, true});


        Map<String, Set<String>> matches = matcher.match(sursTokens);
        DictUtils.printMap(matches, "../arc.data/output/", "matches_" + date);


    }


    @Ignore
    @Test
    public void testRegex() {

        String s = "XI[üüücscdsd";


        s = s.replaceAll("[IVXLCDM]", "");


        System.out.println(s);

    }

    @Ignore
    @Test
    public void testStrings() {


        String s = "DANİEL";
        String k = "DANİEL";

        String regex_rn = "^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";
        String regex_fp = "[\\p{Punct}]";


        Pattern p = Pattern.compile("[.!?]$");
        Matcher m = p.matcher(k);


        if (m.find()) {


            //k = k.replaceAll("\\p{P}", "");

            System.out.println("String: " + k);


        }


    }


    @Ignore
    @Test
    public void testRemoveEverythingButChars() {


        String token = "sfklvjdf v-csdcs.";

        Pattern p = Pattern.compile("\\p{P}$");
        Matcher m = p.matcher(token);


//        if (m.find()) {
//            startOfSentence = true;
//
//        }

        //Remove everything but letters
        token = token.replaceAll("\\P{L}", "");

        // Remove whitespaces on the borders
        token = token.replaceAll("^\\s+|\\s+$", "");


       // if (token.equals("")) {

//            System.out.println("yep");
//            System.out.println(token.length());
        //}
        System.out.println(token);


    }


}
