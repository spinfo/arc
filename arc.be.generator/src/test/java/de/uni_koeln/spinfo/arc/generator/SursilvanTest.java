package de.uni_koeln.spinfo.arc.generator;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import de.uni_koeln.spinfo.arc.utils.FileUtils;
import de.uni_koeln.spinfo.arc.matcher.POSMatcher;
import de.uni_koeln.spinfo.arc.matcher.SursilvanMatcher;
import de.uni_koeln.spinfo.arc.matcher.Token;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.net.UnknownHostException;
import java.util.*;

/**
 * @author geduldia
 *         <p/>
 *         Diese Klasse enthält 2 Tests: 1. Test des
 *         Sursilvan_VollFormenGenerators 2. Test des Sursilvan_Taggers
 */

public class SursilvanTest {

	/**
	 * Testet den Sursilvan_VFGenerator (angewendet auf das NVS_Lexikon)
	 * Ausgabe: Anzahl Stammformen (in der DB-Collection) Anzahl der generierten
	 * Vollformen
	 *
	 * @throws UnknownHostException
	 */

	String date = FileUtils.getISO8601StringForCurrentDate();
	private static String pathToTokensFromDB = "../../arc.data/output/words_2014-12-06T09:53:17Z";
	private static MongoClient mongoClient;
	private static DBCollection nvs_collection;
	private static DB db;

	@BeforeClass
	public static void init() throws Exception {

		mongoClient = new MongoClient("localhost", 27017);
		db = mongoClient.getDB("arc");
		nvs_collection = db.getCollection("nvs_20150203");

	}

	@Ignore
	@Test
	public void testVFGenerator() throws UnknownHostException {

		Sursilvan_VFGenerator vfg = new Sursilvan_VFGenerator();

		MongoClient mongoClient = new MongoClient("localhost", 27017);

		DB db = mongoClient.getDB("arc");

		DBCollection collection = db.getCollection("nvs_cleaned");

		Map<String, TreeSet<String>> VFs = vfg.generateFullforms(collection);

		System.out.println(vfg.getNumberOfDBEntries());
		System.out.println(vfg.getNumberOfVFEntries());
	}

	@Ignore
	@Test
	public void testMatchTokens() throws IOException {

		MongoClient mongoClient = new MongoClient("localhost", 27017);
		DB db = mongoClient.getDB("arc");
		DBCollection collection = db.getCollection("nvs_20141203");
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
				.generateFullforms(collection);

		POSMatcher matcher = new SursilvanMatcher(generatedVollForms,
				collection.getFullName());
		matcher.configure(new Boolean[] { true, true, true, true });

		Map<String, Set<String>> matches = matcher.match(sursTokens);
		FileUtils.printMap(matches, "../arc.data/output/", "matches_" + date);

		in.close();
	}

	// @Ignore
	@Test
	public void testMatchTokensSerialized() throws Exception {

		Map<String, TreeSet<String>> fullForms = readFullForms("fullforms_2015-02-03T19:54:07Z");

		POSMatcher matcher = new SursilvanMatcher(fullForms,
				nvs_collection.getFullName());
		matcher.configure(new Boolean[] { true, true, true, true });

		ArrayList<Token> matches = matcher
				.matchTokensWithPOS(getListOfTokens(pathToTokensFromDB));
		FileUtils.writeList(matches, "matchedWords_");
		FileUtils.printList(matches, FileUtils.outputPath, "matchedWords_");
	}

	@Ignore
	@Test
	public void getFullForms() throws Exception {

		Map<String, TreeSet<String>> fullForms = generatefullForms();
		writeFullforms(fullForms);
		FileUtils.printMap(fullForms, "../../arc.data/output/", "fullForms"
				+ date);

	}

	// @Ignore
	@Test
	public void testGetPOS() throws Exception {

		Map<String, TreeSet<String>> fullForms = readFullForms("fullforms_2015-01-22T14:41:27Z");

		POSMatcher matcher = new SursilvanMatcher(fullForms,
				nvs_collection.getFullName());
		matcher.configure(new Boolean[] { true, true, true, true });

		String s = "&";

		Set<String> set = matcher.getPOS(s);

		for (String f : set) {

			System.out.println(f);

		}

	}

	;

	@Test
	public void reg() {

		String token = "E2323.";

		token = token.replaceAll("[\\p{Punct}]", "");

		System.out.println(token);

	}

	@Ignore
	@Test
	public void testRegex() {

		String token = "I";

		// token = token.replaceAll("\\P{L}", "");

		// token = token.replaceAll("(?!')(\\P{L})", "");

		if (token
				.matches("^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$"))

			System.out.println(token);

	}

	private static Map<String, TreeSet<String>> generatefullForms()
			throws UnknownHostException {

		// Get Fullforms from Sursilvan-Generator
		Sursilvan_VFGenerator gen = new Sursilvan_VFGenerator();
		Map<String, TreeSet<String>> fullForms = gen
				.generateFullforms(nvs_collection);

		return fullForms;

	}

	private static Map<String, TreeSet<String>> readFullForms(String fileName)
			throws Exception {

		ObjectInputStream inputStream = new ObjectInputStream(
				new FileInputStream(FileUtils.outputPath + fileName));

		Map<String, TreeSet<String>> fullForms = (Map<String, TreeSet<String>>) inputStream
				.readObject();
		inputStream.close();
		return fullForms;
	}

	private static <K, V> void writeFullforms(Map<K, V> fullForms)
			throws IOException {

		ObjectOutputStream outputStream = new ObjectOutputStream(
				new FileOutputStream(FileUtils.outputPath + "fullforms_"
						+ FileUtils.getISO8601StringForCurrentDate()));

		outputStream.writeObject(fullForms);

		outputStream.close();

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
