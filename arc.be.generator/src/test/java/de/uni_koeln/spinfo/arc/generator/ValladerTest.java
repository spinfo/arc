package de.uni_koeln.spinfo.arc.generator;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import de.uni_koeln.spinfo.arc.matcher.POSMatcher;
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
 * Created by franciscomondaca on 13/7/15.
 */
public class ValladerTest {


    String date = FileUtils.getISO8601StringForCurrentDate();
    private static String pathToTokensFromDB = "../../arc.data/output/Vallader_words_2015-08-14T15:07:48Z";
    private static MongoClient mongoClient;
    private static DBCollection dictCollection;
    private static DB db;


    @BeforeClass
    public static void init() throws Exception {

        mongoClient = new MongoClient("localhost", 27017);
        db = mongoClient.getDB("dicts");
        dictCollection = db.getCollection("vallader");

    }


    @Test
    public void showStats() throws UnknownHostException {

        Vallader_VFGenerator vfg = new Vallader_VFGenerator();

        Map<String, TreeSet<String>> VFs = vfg.generateFullforms(dictCollection);

        System.out.println(vfg.getNumberOfDBEntries());
        System.out.println(vfg.getNumberOfVFEntries());
    }

    //@Ignore
    @Test
    public void getFullForms() throws Exception {

        Map<String, TreeSet<String>> fullForms = generatefullForms();
        FileUtils.writeFullforms(fullForms, "vallader_");
        FileUtils.printMap(fullForms, "../arc.data/output/", "vallader_fullForms_");

    }


    private static Map<String, TreeSet<String>> generatefullForms()
            throws UnknownHostException {

        // Get Fullforms from Vallader_VFGenerator
        Vallader_VFGenerator gen = new Vallader_VFGenerator();
        Map<String, TreeSet<String>> fullForms = gen
                .generateFullforms(dictCollection);

        return fullForms;

    }

    @Test
    public void testMatchTokensSerialized() throws Exception {

        Map<String, TreeSet<String>> fullForms = FileUtils.readFullForms("vallader_fullforms_2015-08-14T17:20:57Z");

        POSMatcher matcher = new ValladerMatcher(fullForms,
                dictCollection.getFullName());
        matcher.configure(new Boolean[]{true, true, true, true});

        List<Token> valladertokens = getListOfTokens(pathToTokensFromDB);

        ArrayList<Token> matches = (ArrayList<Token>) matcher
                .matchTokensWithPOS(valladertokens, "vallader");


        System.out.println("VALLADER_TOKENS: " + valladertokens.size());
        System.out.println("MATCHES: " + matches.size() + "\t - " + matches.size() * 100 / valladertokens.size()+"%");

        FileUtils.writeList(matches, "vallader_matchedWords_");
        FileUtils.printList(matches, FileUtils.outputPath, "vallader_matchedWords_");
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
