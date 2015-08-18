package de.uni_koeln.spinfo.arc.generator;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import de.uni_koeln.spinfo.arc.matcher.POSMatcher;
import de.uni_koeln.spinfo.arc.matcher.PuterMatcher;
import de.uni_koeln.spinfo.arc.matcher.Token;
import de.uni_koeln.spinfo.arc.matcher.ValladerMatcher;
import de.uni_koeln.spinfo.arc.utils.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by franciscomondaca on 14/8/15.
 */
public class PuterTest {

    String date = FileUtils.getISO8601StringForCurrentDate();
    private static MongoClient mongoClient;
    private static DBCollection dictCollection;
    private static DB db;
    private static String pathToTokensFromDB = "../../arc.data/output/Puter_words_2015-08-14T15:09:05Z";


    @BeforeClass
    public static void init() throws Exception {

        mongoClient = new MongoClient("localhost", 27017);
        db = mongoClient.getDB("dicts");
        dictCollection = db.getCollection("puter");

    }


    @Test
    public void showStats() throws UnknownHostException {

        Puter_VFGenerator pfg = new Puter_VFGenerator();

        Map<String, TreeSet<String>> VFs = pfg.generateFullForms(dictCollection);

        System.out.println(pfg.getNumberOfDBEntries());
        System.out.println(pfg.getNumberOfVFEntries());
    }

    //@Ignore
    @Test
    public void getFullForms() throws Exception {

        Map<String, TreeSet<String>> fullForms = generatefullForms();
        FileUtils.writeFullforms(fullForms, "puter_");
        FileUtils.printMap(fullForms, "../arc.data/output/", "puter_fullForms_");

    }


    private static Map<String, TreeSet<String>> generatefullForms()
            throws UnknownHostException {

        // Get Fullforms from Generator
        Puter_VFGenerator gen = new Puter_VFGenerator();
        Map<String, TreeSet<String>> fullForms = gen
                .generateFullForms(dictCollection);

        return fullForms;

    }


    @Test
    public void testMatchTokensSerialized() throws Exception {

        Map<String, TreeSet<String>> fullForms = FileUtils.readFullForms("puter_fullforms_2015-08-14T17:26:15Z");

        POSMatcher matcher = new PuterMatcher(fullForms,
                dictCollection.getFullName());
        matcher.configure(new Boolean[]{true, true, true, true});

        List<Token> putertokens = getListOfTokens(pathToTokensFromDB);

        ArrayList<Token> matches = (ArrayList<Token>) matcher
                .matchTokensWithPOS(putertokens, "puter");


        System.out.println("PUTER_TOKENS: " + putertokens.size());
        System.out.println("MATCHES: " + matches.size() + "\t - " + matches.size() * 100 / putertokens.size() + "%");

        FileUtils.writeList(matches, "puter_matchedWords_");
        FileUtils.printList(matches, FileUtils.outputPath, "puter_matchedWords_");
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
