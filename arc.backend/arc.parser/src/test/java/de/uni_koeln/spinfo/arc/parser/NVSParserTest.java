package de.uni_koeln.spinfo.arc.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import de.uni_koeln.spinfo.arc.common.DictUtils;

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

		
		String dbName = "";
		String collectionName = "";

		DB db = mongoClient.getDB(dbName);
		DBCollection collection = db.getCollection(collectionName);

		Map<String, Set<String>> set = parser.getLemmas(collection);

		parser.replaceStrings(set);

		String outputPath = "";
		String fileName = "";

		DictUtils.printMap(set, outputPath, fileName);

	}

	// Transforms a single txt-file into Mongo
	@Test
	public void testNVSToMongo() throws IOException {

		String txtFile = "";
		String dbName = "";
		String collectionName = "";

		DB db = mongoClient.getDB(dbName);
		DBCollection collection = db.getCollection(collectionName);

		br = new BufferedReader(new InputStreamReader(new FileInputStream(
				txtFile), StandardCharsets.UTF_8));

		parser.nvsToMongo(br, collection);

	}

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
