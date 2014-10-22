package de.uni_koeln.spinfo.arc.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArraySet;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class NVSParser {

	public Map<String, Set<String>> getLemmas(DBCollection nvs) {

		Map<String, Set<String>> lemmas = new TreeMap<>();

		DBCursor lemmasFromMongo = nvs.find();

		for (DBObject o : lemmasFromMongo) {

			String lemma = (String) o.get("entry");
			BasicDBObject pos = (BasicDBObject) o.get("pos");
			String nvs_pos = (String) pos.get("nvs_pos");

			Set<String> pos_set;

			if (lemmas.get(lemma) == null) {
				pos_set = new CopyOnWriteArraySet<String>();
				pos_set.add(nvs_pos);
				lemmas.put(lemma, pos_set);

			} else {
				pos_set = lemmas.get(lemma);
				pos_set.add(nvs_pos);
				lemmas.put(lemma, pos_set);

			}

		}

		return lemmas;
	}

	public Map<String, Set<String>> replaceStrings(Map<String, Set<String>> map) {

		Iterator<String> it1 = map.keySet().iterator();

		while (it1.hasNext()) {

			String entry = it1.next();
			Set<String> pos_set = map.get(entry);
			Iterator<String> it = pos_set.iterator();

			while (it.hasNext()) {
				String value = it.next();
				System.out.println("Set Value:" + value);
				if (value.equals("adj")) {
					pos_set.remove(value);
					pos_set.add("ADJ");
				}
				if (value.equals("m")) {
					pos_set.remove(value);
					pos_set.add("NN");
				}

			}

			map.put(entry, pos_set);

		}

		return map;
	}

	public DBCollection nvsToMongo(BufferedReader br, DBCollection collection)
			throws IOException {

		String line;

		while ((line = br.readLine()) != null) {
			if (!line.startsWith("#")) {
				String[] nvs_line = line.split("\\$");
				DBObject mongoEntry = nvsEntryToMongoObject(nvs_line);
				collection.insert(mongoEntry);
			}
		}

		return collection;
	}

	public DBObject nvsEntryToMongoObject(String[] nvs_line) {

		BasicDBObject entry = new BasicDBObject();

		BasicDBObject pos = new BasicDBObject();

		String lemma = nvs_line[0];

		lemma = lemma.replace("*", "");

		entry.put("entry", lemma);

		System.out.println(nvs_line[0]);

		String nvs_pos = nvs_line[1];

		String eagles_pos = null;

		switch (nvs_pos) {

		case "KPS":
			eagles_pos = "KPS";
			break;

		// ART
		case "art":
		case "art.":
		case "art.def":
		case "art.def.":
		case "art.indef":
		case "art.indef.":
			eagles_pos = "ART";
			break;

		// ADJ
		case "adj":
		case "adj.":
		case "adj.invar.":
		case "adj.invar":
		case "adj.attrib":
		case "adj.attrib.":
			eagles_pos = "ADJ";
			break;

		// ADJ_INDEF:
		case "adj.indef.":
		case "adj.indef":

			eagles_pos = "ADJ_IND";
			break;

		// ADJ_NUM:
		case "num.ord":
		case "num.ord.":
			eagles_pos = "ADJ_NUM";
			break;
		// ADV
		case "adv":
			eagles_pos = "ADV";
			break;
		// INT
		case "interj":
			eagles_pos = "INT";
			break;

		// CONJ
		case "conj.":
		case "conj":
			eagles_pos = "CONJ";
			break;

		// NN
		case "m":
		case "f":
		case "n":
		case "f/coll":
		case "pl":
		case "coll":
		case "sm":
		case "sm/sf":
		case "m/pl":
		case "f/pl":
		case "m/f":
			eagles_pos = "NN";
			break;
		// NN_P
		case "ON":
		case "PN":
			eagles_pos = "NN_P";
			break;
		// PREP
		case "prep":
			eagles_pos = "PREP";
			break;

		// CARDINAL_NUMBERS
		case "num.":
		case "num":
			eagles_pos = "C_NUM";
			break;

		// PRON
		case "pron":
			eagles_pos = "PRON";
			break;
		case "pron.pers.":
		case "pron.pers":
		case "pron.pers.obj":
			eagles_pos = "PRON_PER";
			break;
		case "pron.impers.":
		case "pron.impers":
			eagles_pos = "PRON";
			break;
		case "pron.indef.":
		case "pron.indef":
			eagles_pos = "PRON_IES";
			break;
		case "pron.rel.":
		case "pron.rel":
			eagles_pos = "PRON_REL";
			break;
		case "pron.interrog.":
		case "pron.interrog":
			eagles_pos = "PRON_IES";
			break;
		case "pron.ord.":
		case "pron.ord":
			eagles_pos = "PRON";
			break;
		case "pron.refl.":
		case "pron.refl":
			eagles_pos = "PRON";
			break;

		// V_GVRB
		case "tr":
		case "tr.":
		case "intr":
		case "intr.":
		case "refl":
		case "v":
			eagles_pos = "V_GVRB";
			break;
		default:
			break;
		}

		// Add nvs_pos info
		pos.put("nvs_pos", nvs_line[1]);
		// Add eagles_pos info
		if (eagles_pos != null) {
			pos.put("eagles_pos", eagles_pos);
		}

		entry.put("pos", pos);

		return entry;

	}

	public DBCollection mergeNVSVersions(DB db, DBCollection firstToMerge,
			DBCollection secondToMerge, String newCollection) {

		DBCollection mergedCollection = db.getCollection(newCollection);

		DBCursor nvsCursor = firstToMerge.find();
		DBCursor noAntlrCursor = secondToMerge.find();

		List<DBObject> nvsList = nvsCursor.toArray();
		List<DBObject> noAntlrList = noAntlrCursor.toArray();

		nvsList.addAll(noAntlrList);

		BasicDBObject keys = new BasicDBObject();
		keys.put("entry", 1);
		keys.put("pos", 1);

		// mergedCollection.ensureIndex(keys, "le_index", true);

		// ensureIndex(keys, "le_index", true);

		mergedCollection.insert(nvsList);

		// Delete duplicate elements

		// DBCursor mergedCursor = mergedCollection.find();

		// System.out.println(count);

		return mergedCollection;

	}

}
