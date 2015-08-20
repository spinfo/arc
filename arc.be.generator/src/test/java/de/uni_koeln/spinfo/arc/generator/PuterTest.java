package de.uni_koeln.spinfo.arc.generator;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import de.uni_koeln.spinfo.arc.matcher.POSMatcher;
import de.uni_koeln.spinfo.arc.matcher.PuterMatcher;
import de.uni_koeln.spinfo.arc.matcher.Token;
import de.uni_koeln.spinfo.arc.matcher.ValladerMatcher;
import de.uni_koeln.spinfo.arc.utils.DictUtils;
import de.uni_koeln.spinfo.arc.utils.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
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
    public void showStats() throws IOException {

        Puter_VFGenerator pfg = new Puter_VFGenerator();

        Map<String, TreeSet<String>> VFs = pfg.generateFullForms(dictCollection);

        System.out.println(pfg.getNumberOfDBEntries());
        System.out.println(pfg.getNumberOfVFEntries());

        extendedStats(VFs);
    }

    //@Ignore
    @Test
    public void getFullForms() throws Exception {

        Map<String, TreeSet<String>> fullForms = generatefullForms();
        FileUtils.writeFullforms(fullForms, "puter_");
        FileUtils.printMap(fullForms, "../arc.data/output/", "puter_fullForms_");

    }

    private void extendedStats(Map<String, TreeSet<String>> vFs) throws IOException {

        List<String> nn = new ArrayList<String>();
        List<String> v_gvrb = new ArrayList<String>();
        List<String> adj = new ArrayList<String>();
        List<String> adj_num = new ArrayList<String>();
        List<String> adv = new ArrayList<String>();
        List<String> iNT = new ArrayList<String>();
        List<String> prep = new ArrayList<String>();
        List<String> c_num = new ArrayList<String>();
        List<String> pron_per = new ArrayList<String>();
        List<String> pron_ref = new ArrayList<String>();
        List<String> pron_ies = new ArrayList<String>();
        List<String> andere = new ArrayList<String>();

        for (Map.Entry<String, TreeSet<String>> entry : vFs.entrySet()) {
            for (String pos : entry.getValue()) {
                switch (pos) {
                    case "NN":
                        nn.add(entry.getKey());
                        break;
                    case "V_GVRB":
                        v_gvrb.add(entry.getKey());
                        break;
                    case "ADJ":
                        adj.add(entry.getKey());
                        break;
                    case "ADJ_NUM":
                        adj_num.add(entry.getKey());
                        break;
                    case "ADV":
                        adv.add(entry.getKey());
                        break;
                    case "INT":
                        iNT.add(entry.getKey());
                        break;
                    case "PREP":
                        prep.add(entry.getKey());
                        break;
                    case "C_NUM":
                        c_num.add(entry.getKey());
                        break;
                    case "PRON_PER":
                        pron_per.add(entry.getKey());
                        break;
                    case "PRON_REF":
                        pron_ref.add(entry.getKey());
                        break;
                    case "PRON_IES":
                        pron_ies.add(entry.getKey());
                        break;
                    default:
                        andere.add(entry.getKey());
                        break;
                }
            }
        }

        System.out.println("NN: " + nn.size());
        System.out.println("V_GVRB: " + v_gvrb.size());
        System.out.println("ADJ: " + adj.size());
        System.out.println("ADJ_NUM: " + adj_num.size());
        System.out.println("ADV: " + adv.size());
        System.out.println("INT: " + iNT.size());
        System.out.println("PREP: " + prep.size());
        System.out.println("C_NUM: " + c_num.size());
        System.out.println("PRON_PER: " + pron_per.size());
        System.out.println("PRON_REF: " + pron_ref.size());
        System.out.println("PRON_IES: " + pron_ies.size());
        System.out.println("andere: " + andere.size());

        DictUtils.printList(nn, FileUtils.outputPath + "/generator/", "puter_nn");
        DictUtils.printList(v_gvrb, FileUtils.outputPath+"/generator/", "puter_v_gvrb");
        DictUtils.printList(adj, FileUtils.outputPath+"/generator/", "puter_adj");
        DictUtils.printList(adj_num, FileUtils.outputPath+"/generator/", "puter_adj_num");
        DictUtils.printList(adv, FileUtils.outputPath+"/generator/", "puter_adv");
        DictUtils.printList(iNT, FileUtils.outputPath+"/generator/", "puter_int");
        DictUtils.printList(prep, FileUtils.outputPath+"/generator/", "puter_prep");
        DictUtils.printList(c_num, FileUtils.outputPath+"/generator/", "puter_c_num");
        DictUtils.printList(pron_per, FileUtils.outputPath+"/generator/", "puter_pron_per");
        DictUtils.printList(pron_ref, FileUtils.outputPath+"/generator/", "puter_pron_ref");
        DictUtils.printList(pron_ies, FileUtils.outputPath+"/generator/", "puter_pron_ies");

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
