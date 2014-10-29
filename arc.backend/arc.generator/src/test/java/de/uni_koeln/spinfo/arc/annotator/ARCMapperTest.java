package de.uni_koeln.spinfo.arc.annotator;

import com.mongodb.*;

import de.uni_koeln.spinfo.arc.common.DictUtils;
import de.uni_koeln.spinfo.arc.generator.Sursilvan_VFGenerator;

import org.bson.types.ObjectId;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.net.UnknownHostException;
import java.util.*;

public class ARCMapperTest {

	static ARCMapper mapper;
	static MongoClient mongoClient;
	static DB db;

	@BeforeClass
	public static void initialize() throws UnknownHostException {
		mapper = new ARCMapper();
		mongoClient = new MongoClient("localhost", 27017);
		db = mongoClient.getDB("arc");

	}


	// To use with SursilvanTest.java
	@Test
	public void testGetTokensfromXII() throws IOException {

		MongoClient mongoClient = new MongoClient("localhost", 27017);
		DB db = mongoClient.getDB("arc");
		DBCollection collection = db.getCollection("word");

		DBObject rangeXII = new BasicDBObject("word_index", new BasicDBObject(
				"$gt", 2531717).append("$lt", 2629575));

		DBCursor cursor = collection.find(rangeXII);
		List<String> last_forms_tokens = new ArrayList<>();
		Set<String> last_forms_types = new TreeSet<>();

		for (DBObject obj : cursor) {

			ArrayList<BasicDBObject> mods = (ArrayList<BasicDBObject>) obj
					.get("modifications");

			BasicDBObject lastMod = mods.get(0);

			String lastForm = (String) lastMod.get("form");

			// lastForm = lastForm.replaceAll("[^\\p{L}]", "");

			last_forms_types.add(lastForm);
			// last_forms_tokens.add(lastForm);

		}

		DictUtils.printSet(last_forms_types, "../arc.data/testdata/",
				"XII_types-20140804_nc");
		// DictUtils.printList(last_forms_tokens, "../arc.data/","XII_tokens");

	}

	@Test
	public void testGetlastModifications() throws IOException {
		DBCollection arcCollection = db.getCollection("surtest_pure");

		System.out.println(arcCollection.getStats());

		Map<ObjectId, String> mods = mapper.getlastModifications(arcCollection,
				"all");

		System.out.println(mods.size());

		List<String> listmods = new LinkedList<>();

		for (String s : mods.values()) {

			listmods.add(s);

		}

		DictUtils.printList(listmods, "../arc.data/testdata/", "sursilvan_XII");

	}

	@Ignore
	@Test
	public void testAddPos() throws UnknownHostException {

		Sursilvan_VFGenerator vfg = new Sursilvan_VFGenerator();

		DBCollection arc_collection = db.getCollection("sur_test");
		DBCollection nvs_collection = db.getCollection("nvs_antlr4");

		Map<String, TreeSet<String>> vf = vfg.generateVollForms(nvs_collection);

		mapper.addPOS(arc_collection, "sursilvan", vf);

	}

	@Ignore
	@Test
	public void testAddMetadata() throws UnknownHostException {

		DBCollection metadataCollection = db.getCollection("meta");
		DBCollection arcCollection = db.getCollection("arc_copy");

		mapper.addMetadata(metadataCollection, arcCollection);

	}

	@Ignore
	@Test
	public void testMatchPOS() throws IOException {

		DBCollection arc_collection = db.getCollection("surtest_metadata_copy");
		DBCollection nvs_collection = db.getCollection("nvs_antlr4");

		// Generate vollformen
		Sursilvan_VFGenerator vfg = new Sursilvan_VFGenerator();

		Map<String, TreeSet<String>> vf = vfg.generateVollForms(nvs_collection);

		// Get last modifications
		Map<ObjectId, String> lastMods = mapper.getlastModifications(
				arc_collection, "");
		// Get the POS associated with the entry
		Map<ObjectId, TreeSet<String>> matchPOS = mapper.matchPOS(lastMods, vf);

		// to TXT
		DictUtils.printMap(matchPOS, "./arc.data/testdata/",
				"matchPOS_NVS_VFGenerator");

	}

	@Ignore
	@Test
	public void testInsertPOS() throws IOException {

		DBCollection collection = db.getCollection("surtest_metadata_copy");

		Map<ObjectId, TreeSet<String>> posMap = mapper
				.getPOSfromTXT("./arc.data/testdata/matchPOS.txt");

		// insert POS_Info to the collection
		mapper.insertPOS(collection, posMap);

	}

	@Ignore
	@Test
	public void testGetEntriesWithoutPOS() throws IOException {

		DBCollection collection = db.getCollection("surtest_metadata_copy");

		Map<ObjectId, String> entriesWithoutPOS = mapper
				.getEntriesWithoutPOS(collection);
		DictUtils.printMap(entriesWithoutPOS, "./arc.data/testdata/",
				"entriesWithoutPOS");

	}

	@Ignore
	@Test
	public void countGenForms() throws IOException {

		// DBCollection arc_collection =
		// db.getCollection("surtest_metadata_copy");
		DBCollection nvs_collection = db.getCollection("nvs_antlr4");

		Sursilvan_VFGenerator vfg = new Sursilvan_VFGenerator();
		Map<String, TreeSet<String>> vf = vfg.generateVollForms(nvs_collection);
		System.out.println(vf.size());

		DictUtils.printMap(vf, "./arc.data/testdata/", "vfg");

	}

	@Ignore
	@Test
	public void countPOSFromAntlr4() throws IOException {

		DBCollection collection = db.getCollection("nvs_antlr4");

		Map<String, Integer> countPOS = DictUtils.countPOS(collection,
				"nvs_pos");

		DictUtils.printMap(countPOS, "./arc.data/testdata/", "antlr4_pos");

	}
	
	@Ignore
	@Test
	public void testTagIdiomWithChapterInfo() {

		DBCollection chapter = db.getCollection("chapter");
		DBCollection language = db.getCollection("language");

		Map<Long, Long> intervals = new HashMap<>();

		intervals.put(61L, 0L);

		mapper.tagIdiomWithChapterInfo(chapter, language, intervals,
				"sursilvan");

	}

	@Ignore
	@Test
	public void getDiffWordDiffs() throws IOException {

		DBCollection arc_sursilvan = db.getCollection("arc_sursilvan");
		DBCollection word = db.getCollection("word");

		File file = new File("./arc.data/testdata/toCompare.txt");
		FileReader in = new FileReader(file);
		BufferedReader br = new BufferedReader(in);

		FileWriter writer = new FileWriter(
				"./arc.data/testdata/sursilvan_diff.txt");
		BufferedWriter out = new BufferedWriter(writer);

		String l;

		while ((l = br.readLine()) != null) {

			l = l.replaceAll("\"", "").replaceAll(",", "")
					.replaceAll("\\t", "");

			System.out.println(l);

			BasicDBObject arc_o = new BasicDBObject("token_info.xml_id", l);

			BasicDBObject word_o = new BasicDBObject("page_id", l);

			long word_q = arc_sursilvan.count(arc_o);

			long arc_q = word.count(word_o);

			StringBuilder builder = new StringBuilder();
			builder.append(l);
			builder.append("\t");
			builder.append(word_q);
			builder.append("\t");
			builder.append(arc_q);
			builder.append("\n");

			out.append(builder.toString());

		}
		out.close();

	}

	@Test
	public void testMongoCommandsArrays() {

		DBCollection arc = db.getCollection("arc_sursilvan");

		DBObject query = QueryBuilder.start("lang_info")
				.elemMatch(new BasicDBObject("idiom", "sursilvan")).get();
		DBCursor arcCursor = arc.find(query);
		System.out.println(arcCursor.size());

		for (int i = 0; i < arcCursor.size(); i++) {
			DBObject object = arcCursor.next();
			String os = object.toString();
			System.out.println(os);

			break;
		}

	}

	@Test
	public void testMongoCommandsArrays_S() {

		DBCollection arc = db.getCollection("arc_sursilvan");

		DBObject criteria = new QueryBuilder().put("phys_info.oct_vol")
				.is("XII").and("phys_info.oct_page").greaterThan("1")
				.lessThanEquals("11").get();

		DBCursor arcCursor = arc.find(criteria);
		System.out.println(arcCursor.size());

		DBCollection surtest = db.getCollection("surtest_metadata");

		DBCursor surtestCursor = surtest.find();
		System.out.println(surtestCursor.size());

	}

	@Test
	public void addMetadataToSpecificFiles() {

		DBCollection arc = db.getCollection("arc_metadata");
		DBCollection metadata = db.getCollection("meta");

		DBCursor arcCursor = arc.find(new BasicDBObject("token_info.xml_id",
				"PPN345572629_0038-0366.xml"));

		for (int i = 0; i < arcCursor.size(); i++) {

			DBObject doc = arcCursor.next();

			DBObject token_info = (DBObject) doc.get("token_info");

			// Get the page_id from the Entry
			String page_id_fromARC = (String) token_info.get("xml_id");
			System.out.println(page_id_fromARC);

			String token = (String) token_info.get("original");
			System.out.println(token);

			// Get the metadata related to the Entry
			BasicDBObject md = new BasicDBObject();
			md.put("xml_id", page_id_fromARC);

			DBCursor metaCursor = metadata.find(md);

			if (metaCursor != null) {

				BasicDBObject metadata_obj = (BasicDBObject) metaCursor.next();

				BasicDBObject phys_info = (BasicDBObject) metadata_obj
						.get("phys_info");

				doc.put("phys_info", phys_info);

				arc.save(doc);

			}

		}

	}

	@Test
	public void compareCollectionsEntries() throws IOException {

		DBCollection coll1 = db.getCollection("arc_metadata");
		DBCollection coll2 = db.getCollection("surtest_metadata");

		BasicDBObject toSearch = new BasicDBObject("phys_info.oct_vol", "XII");

		DBCursor coll1Cursor = coll1.find(toSearch);
		DBCursor coll2Cursor = coll2.find(toSearch);

		List<String> coll1List = new ArrayList<>();
		List<String> coll2List = new ArrayList<>();

		System.out.println("coll1Cursor: " + coll1Cursor.size());
		System.out.println("coll2Cursor: " + coll2Cursor.size());

		for (DBObject coll1Obj : coll1Cursor) {

			BasicDBObject t1 = (BasicDBObject) coll1Obj.get("token_info");
			String coll1String = (String) t1.get("original");
			coll1List.add(coll1String);

		}

		for (DBObject coll2Obj : coll2Cursor) {

			BasicDBObject t2 = (BasicDBObject) coll2Obj.get("token_info");
			String coll2String = (String) t2.get("original");
			coll2List.add(coll2String);

		}

		Collections.sort(coll1List);
		Collections.sort(coll2List);

		DictUtils.printList(coll1List, "./arc.data/testdata/",
				"coll1List_sorted");

		DictUtils.printList(coll2List, "./arc.data/testdata/",
				"coll2List_sorted");

		// coll1List.removeAll(coll2List);
		// DictUtils.printList(coll1List, "./arc.data/testdata/",
		// "diffcoll");

	}

}
