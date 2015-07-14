package de.uni_koeln.spinfo.arc.generator;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import de.uni_koeln.spinfo.arc.utils.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by franciscomondaca on 13/7/15.
 */
public class ValladerTest {


    String date = FileUtils.getISO8601StringForCurrentDate();
    private static String pathToTokensFromDB = "../../arc.data/output/words_2014-12-06T09:53:17Z";
    private static MongoClient mongoClient;
    private static DBCollection dictCollection;
    private static DB db;


    @BeforeClass
    public static void init() throws Exception {

        mongoClient = new MongoClient("localhost", 27017);
        db = mongoClient.getDB("arc");
        dictCollection = db.getCollection("nvs_20150203");

    }


    @Test
    public void testVFGenerator() throws UnknownHostException {

        Vallader_VFGenerator vfg = new Vallader_VFGenerator();

        MongoClient mongoClient = new MongoClient("localhost", 27017);

        DB db = mongoClient.getDB("arc");

        //DBCollection collection = db.getCollection("nvs_cleaned");

        Map<String, TreeSet<String>> VFs = vfg.generateFullforms(dictCollection);

        System.out.println(vfg.getNumberOfDBEntries());
        System.out.println(vfg.getNumberOfVFEntries());
    }
}
