import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import de.uni_koeln.spinfo.arc.utils.DictUtils;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by franciscomondaca on 13/7/15.
 */
public class TXTToMongoTest {

    private static TXTToMongo parser;
    private static BufferedReader br;
    private static MongoClient mongoClient;

    @BeforeClass
    public static void initialize() throws UnsupportedEncodingException,
            FileNotFoundException, UnknownHostException {

        parser = new TXTToMongo();
        mongoClient = new MongoClient("localhost", 27017);
    }


    @Ignore
    @Test
    public void testSursilvanToMongo() throws IOException {

        String txtFile = "";
        String dbName = "";
        String collectionName = "";

        DB db = mongoClient.getDB(dbName);
        DBCollection collection = db.getCollection(collectionName);

        br = new BufferedReader(new InputStreamReader(new FileInputStream(
                txtFile), StandardCharsets.UTF_8));

        parser.txtToMongo(br, collection, "sursilvan");

    }


    //@Ignore
    @Test
    public void testValladerToMongo() throws IOException {

        String txtFile = "../arc.data/input/tscharner-20140715_20140923-20150318-20150818.txt";
        String dbName = "dicts";
        String collectionName = "vallader";

        DB db = mongoClient.getDB(dbName);
        DBCollection collection = db.getCollection(collectionName);

        br = new BufferedReader(new InputStreamReader(new FileInputStream(
                txtFile), StandardCharsets.UTF_8));

        parser.txtToMongo(br, collection, "vallader");

    }

    //@Ignore
    @Test
    public void testSutsilvanToMongo() throws IOException {

        String txtFile = "../arc.data/input/sutsilvan.txt";
        String dbName = "dicts";
        String collectionName = "sutsilvan";

        DB db = mongoClient.getDB(dbName);
        DBCollection collection = db.getCollection(collectionName);

        br = new BufferedReader(new InputStreamReader(new FileInputStream(
                txtFile), StandardCharsets.UTF_8));

        parser.txtToMongo(br, collection, "sutsilvan");

    }


    //@Ignore
    @Test
    public void testPuterToMongo() throws IOException {

        String txtFile = "../arc.data/puter/input/tscharner-20150114_20150318_20150326_20150812.txt";
        String dbName = "dicts";
        String collectionName = "puter";

        DB db = mongoClient.getDB(dbName);
        DBCollection collection = db.getCollection(collectionName);

        br = new BufferedReader(new InputStreamReader(new FileInputStream(
                txtFile), StandardCharsets.UTF_8));

        parser.txtToMongo(br, collection, "puter");

    }


    @Ignore
    @Test
    public void testGetLemmas() throws IOException {


        String dbName = "arc";
        String collectionName = "nvs_20140812";

        DB db = mongoClient.getDB(dbName);
        DBCollection collection = db.getCollection(collectionName);

        Map<String, Set<String>> set = parser.getLemmas(collection);

        //parser.replaceStrings(set);

        String fileName = "nvs-20141124_oca";

        DictUtils.printMap(set, "../" + DictUtils.outputPath, fileName);

    }

    // Transforms a single txt-file into Mongo


    @Ignore
    @Test
    public void testLemmasOneKPS() throws IOException {
        DB db = mongoClient.getDB("arc");
        DBCollection nvs = db.getCollection("nvs_20140812");
        List<String> lemmasOneKPS = parser.getLemmasOnlyWithKPS(nvs);
        DictUtils.printList(lemmasOneKPS, "../" + DictUtils.outputPath, "lemmasKPS");


    }

    @Ignore
    @Test

    public void testAddPOSToKPS() throws Exception {

        DB db = mongoClient.getDB("arc");
        DBCollection nvs = db.getCollection("nvs_20141203");

        String sCurrentLine;

        br = new BufferedReader(new FileReader("../" + DictUtils.outputPath + "lemmasKPS.txt"));

        while ((sCurrentLine = br.readLine()) != null) {

            String[] lineToArray = sCurrentLine.split("\\s+");

            String lemma = lineToArray[0];

            //If there is actually a POS for the lemma
            if (lineToArray.length > 1) {
                for (int i = 1; i < lineToArray.length; i++) {
                    BasicDBObject entry = new BasicDBObject();
                    BasicDBObject pos = new BasicDBObject();

                    entry.put("entry", lemma);
                    pos.put("eagles_pos", lineToArray[i]);
                    pos.put("nvs_pos", "KPS");
                    entry.put("pos", pos);

                    nvs.insert(entry);

                }
            }


        }


    }


    @Ignore
    @Test
    public void testMergeNVSVersions() {

        DB db = mongoClient.getDB("arc");
        DBCollection first_coll = db.getCollection("nvs_slb_web_antlr4b");
        DBCollection second_coll = db.getCollection("nvs_20130806");

        parser.mergeNVSVersions(db, second_coll, first_coll, "nvs_20140812");

        // Later remove dups in Terminal.

        // db.nvs_20140812.ensureIndex({ 'entry': 1, 'pos.nvs_pos': 1 }, {
        // unique: true, dropDups: true});

    }

}
