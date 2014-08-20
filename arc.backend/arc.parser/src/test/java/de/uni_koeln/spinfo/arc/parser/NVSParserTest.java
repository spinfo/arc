package de.uni_koeln.spinfo.arc.parser;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import de.uni_koeln.spinfo.arc.common.DictUtils;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Collator;
import java.text.ParseException;
import java.text.RuleBasedCollator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NVSParserTest {
    private static NVSParser parser;
    private static BufferedReader br;
    private static MongoClient mongoClient;

    @BeforeClass
    public static void initialize() throws UnsupportedEncodingException,
            FileNotFoundException, UnknownHostException {
        parser = new NVSParser();

        mongoClient = new MongoClient("localhost", 27017);
    }


    @Test
    public void testGetLemmas() throws IOException {
        DB db = mongoClient.getDB("arc");
        DBCollection collection = db.getCollection("nvs_20140812");


        Map<String, Set<String>> set = parser.getLemmas(collection);

        parser.replaceStrings(set);


        DictUtils.printMap(set, "/Users/franciscomondaca/Desktop/", "nvs_lemmas-20140812_d");


    }

    //
    @Test

    public void testTxtToMongo() throws IOException {

        DB db = mongoClient.getDB("arc");
        DBCollection collection = db.getCollection("nvs_noantlrb");

        parser.txtToMongo(NVSParser.noantlr_path, collection);

    }

    // Use this method to transform a single NVS.txt file into Mongo
    @Test
    public void testNVSToMongo() throws IOException {

        String last = "/Users/franciscomondaca/spinfo/repositories/antlr4/nvs/nvs.singleline/output_data/decurtins-20131201_20140805_20140805_20140806_20140806.txt";

        DB db = mongoClient.getDB("arc");
        DBCollection collection = db.getCollection("nvs_20130806");

        br = new BufferedReader(new InputStreamReader(new FileInputStream(last), StandardCharsets.UTF_8));

        parser.nvsToMongo(br, collection);

    }

    @Test
    @Ignore
    public void testaddTags() throws IOException {

    }

    //
    @Test
    @Ignore
    public void testParseTXT() throws IOException {

        parser.parseTXT("../arc.data/lexika/nvs/nvs_2_LinesEachEntry",
                NVSParser.nvs_genfiles_path);
    }

    @Test
    @Ignore
    public void testNVSDiff() throws IOException {

        DB db = mongoClient.getDB("arc");
        DBCollection nvs_noantlr = db.getCollection("nvs_noantlr");
        DBCollection nvs = db.getCollection("nvs");

        parser.NVSDiff(nvs, nvs_noantlr);
        DictUtils.printList(parser.NVSDiff(nvs, nvs_noantlr),
                NVSParser.nvs_genfiles_path, "nvs_unique");

    }

    @Test
    @Ignore
    public void testCountPOS() throws IOException {

        DB db = mongoClient.getDB("arc");

        DBCollection nvs = db.getCollection("nvs_ws");

        Map<String, Integer> occurrences = DictUtils.countPOS(nvs, "nvs_pos");

        DictUtils.printMap(occurrences, NVSParser.nvs_genfiles_path,
                "pos_occurences_nvs_ws");

    }

    @Test

    public void testMergeNVSVersions() {

        DB db = mongoClient.getDB("arc");
        DBCollection first_coll = db.getCollection("nvs_slb_web_antlr4b");
        DBCollection second_coll = db.getCollection("nvs_20130806");

        parser.mergeNVSVersions(db, second_coll, first_coll, "nvs_20140812");

        // Later remove dups in Terminal.

//		 db.nvs_20140812.ensureIndex({ 'entry': 1, 'pos.nvs_pos': 1 }, { unique:	 true, dropDups: true});

    }

    @Test
    @Ignore
    public void testRemoveEmptyLines() throws Exception {

        parser.removeEmptyLines(NVSParser.nvs_komplett2,
                NVSParser.nvs_genfiles_path, "nvs");
    }

    @Test
    @Ignore
    public void testSortStrings() throws Exception {

        Path file = Paths.get(NVSParser.nvs_ng_2);
        parser.sortStrings(file, NVSParser.nvs_genfiles_path);
    }

    @Test
    public void testSortPDF() throws Exception {
        String sursilvan = "<a,A,Á,á,À,à< b,B<c,C< d,D<e,E,è,È,ê,Ê,é,É< f,F< g,G< h,H< i,I< j,J< k,K< l,L< m,M< n,N< o,O< p,P< q,Q< r,R< s,S< t,T< u,U< v,V< w,W< x,X< y,Y< z,Z";

        RuleBasedCollator collator = new RuleBasedCollator(sursilvan);

        collator.setStrength(Collator.SECONDARY);

        Path file = Paths.get("../arc.data/lexika/nvs/raw.txt");


        DictUtils.printList(parser.sortPDFCollator(file, NVSParser.nvs_genfiles_path, collator), NVSParser.nvs_genfiles_path, "raw_");
    }

    @Test
    @Ignore
    public void testSortTXT() throws IOException {

        parser.sortTXT(NVSParser.noantlr_d_rival,
                NVSParser.noantlr_folder_sorted);

    }

    @Test
    public void testSortTXTCollator() throws IOException, ParseException {

        String sursilvan = "<a,A,Á,á,À,à< b,B<c,C< d,D<e,E,è,È,ê,Ê,é,É< f,F< g,G< h,H< i,I< j,J< k,K< l,L< m,M< n,N< o,O< p,P< q,Q< r,R< s,S< t,T< u,U< v,V< w,W< x,X< y,Y< z,Z";

        RuleBasedCollator collator = new RuleBasedCollator(sursilvan);

        collator.setStrength(Collator.SECONDARY);

        parser.sortTXTCollator("../arc.data/lexika/nvs/raw.txt",
                NVSParser.nvs_genfiles_path, collator, "sortPDFCollator");

    }

    @Test
    @Ignore
    public void testCountEaglesPOSFromMongo() throws IOException {

        Map<String, Integer> pos_occ = DictUtils
                .countEaglesPOSFromMongo("nvs_eagles");
        DictUtils.printMap(pos_occ, NVSParser.nvs_genfiles_path,
                "CountEaglesPOSFromMongo");

    }

    @Test

    public void testNvsPOSWithoutEaglesPOSMapping() throws IOException {

        Map<String, Integer> pos_occ = parser
                .nvsPOSWithoutEaglesPOSMapping("nvs_we");

        DictUtils.printMap(pos_occ, NVSParser.nvs_genfiles_path,
                "nvs_we_pos");

    }

    // Deprecated
    @Test
    @Ignore
    public void testAddEaglesPOS() throws IOException {

        DB db = mongoClient.getDB("arc");
        DBCollection collection = db.getCollection("nvs");

        Set<String> nvsTags = new HashSet<>();

        // ADJ: 'adj'
        // nvsTags.add("adj");

        // ADJ_NUM: 'num'
        // nvsTags.add("num");

        // ADV: 'adv'
        // nvsTags.add("adv");

        // INT: 'interj'
        // nvsTags.add("interj");

        // NN: 'm', 'f','n', 'f/coll', 'pl', 'coll'
        // nvsTags.add("m");
        // nvsTags.add("f");
        // nvsTags.add("n");
        // nvsTags.add("f/coll");
        // nvsTags.add("pl");
        // nvsTags.add("coll");

        // PREP: 'prep'
        // nvsTags.add("prep");

        // PRON: 'pron'
        // nvsTags.add("pron");

        // V_GVRB: 'tr', 'intr', 'refl', 'v'
        // nvsTags.add("tr");
        // nvsTags.add("intr");
        // nvsTags.add("refl");
        // nvsTags.add("v");

        parser.addEaglesPOS(collection, nvsTags, "PREP");

    }

    @Ignore
    @Test
    public void testNVStoMap() throws IOException {

        Map<String, String> dictMap = parser.nvsToMap(br);
        DictUtils.printMap(dictMap, NVSParser.nvs_genfiles_path, "nvs_map");

    }

}
