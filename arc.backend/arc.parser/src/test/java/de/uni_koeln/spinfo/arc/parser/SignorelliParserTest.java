package de.uni_koeln.spinfo.arc.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class SignorelliParserTest {

	private static SignorelliParser parser;
	private static BufferedReader br;
	private static MongoClient mongoClient;

	@BeforeClass
	public static void initialize() throws UnsupportedEncodingException,
			FileNotFoundException, UnknownHostException {
		parser = new SignorelliParser();
		mongoClient = new MongoClient("localhost", 27017);
	}

	@Test
	public void testTxtMongo() throws IOException {

		DB db = mongoClient.getDB("arc");
		DBCollection collection = db.getCollection("signorelli");

		parser.txtToMongo(SignorelliParser.signorelli, collection);

	}

}
