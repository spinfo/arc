package de.uni_koeln.spinfo.arc.common;

import com.mongodb.*;

import java.io.*;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DictUtils {

	public static String outputPath = "../../arc.data/output/";
	public static String inputPath = "../../arc.data/input/";

	public static Map<String, Integer> countPOS(DBCollection collection,
			String dict_pos) {

		Map<String, Integer> occurrences = new HashMap<String, Integer>();

		DBCursor cursor = collection.find();

		List<String> posList = new ArrayList<String>();

		while (cursor.hasNext()) {
			DBObject doc = cursor.next();
			DBObject pos = (DBObject) doc.get("pos");

			String nvs_pos = (String) pos.get(dict_pos);
			posList.add(nvs_pos);

		}

		for (String s : posList) {

			Integer count = occurrences.get(s);
			if (count == null) {
				occurrences.put(s, 1);
			} else {
				occurrences.put(s, count + 1);
			}
		}

		return occurrences;

	}

	public static Map<String, Integer> countEaglesPOSFromMongo(
			String collection_name) throws UnknownHostException {
		Map<String, Integer> occurrences = new HashMap<String, Integer>();
		Set<String> posSet = new HashSet<String>(
				Arrays.asList(IPosTags.POS_TAGS_FINAL));

		MongoClient mongoClient = new MongoClient("localhost", 27017);
		DB db = mongoClient.getDB("arc");
		DBCollection collection = db.getCollection(collection_name);

		DBCursor cursor = collection.find();

		while (cursor.hasNext()) {
			DBObject o = cursor.next();
			DBObject pos = (BasicDBObject) o.get("pos");
			String eagles_pos = (String) pos.get("eagles_pos");

			if (eagles_pos != null) {

				for (String s : posSet) {

					if (eagles_pos.equals(s)) {

						Integer count = occurrences.get(s);
						if (count == null) {
							occurrences.put(s, 1);
						} else {
							occurrences.put(s, count + 1);
						}

					}

				}

			}

		}
		return occurrences;

	}

	public static <K, V> File printMap(Map<K, V> map, String destPath,
			String fileName) throws IOException {

		File file = new File(destPath + fileName + ".txt");
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file), "UTF8"));

		for (Map.Entry<K, V> entry : map.entrySet()) {
			out.append(entry.getKey() + " : " + entry.getValue());
			out.append("\n");
		}

		out.flush();
		out.close();

		return file;
	}

	public static <T> File printSet(Set<T> set, String destPath, String filename)
			throws IOException {

		File file = new File(destPath + filename + ".txt");

		Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file), "UTF8"));

		for (Object o : set) {
			writer.append(o + "\n");
		}

		writer.flush();
		writer.close();

		return file;
	}

	public static File addClosingTags(String filePath, String destPath,
			String newFileName) throws IOException {

		File inFile = new File(filePath);
		File outFile = new File(destPath + newFileName + ".txt");

		Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(outFile), "UTF8"));

		BufferedReader br = new BufferedReader(new FileReader(inFile));

		String sCurrentLine;

		while ((sCurrentLine = br.readLine()) != null) {

			StringBuffer b = new StringBuffer(sCurrentLine);

			Pattern lp = Pattern.compile("\\(");
			Matcher lm = lp.matcher(b);
			int lpcount = 0;
			while (lm.find()) {
				lpcount += 1;
			}

			Pattern rp = Pattern.compile("\\)");
			Matcher rm = rp.matcher(b);
			int rpcount = 0;
			while (rm.find()) {
				rpcount += 1;
			}

			if (lpcount > rpcount) {

				int llp = b.lastIndexOf("(");
				b.replace(llp, llp + 1, "");

			}

			writer.append("</E>" + b + "</E>" + "\n");

		}
		writer.flush();
		writer.close();
		br.close();

		return outFile;
	}

	public static <T> File printList(List<T> list, String destPath,
			String filename) throws IOException {

		File file = new File(destPath + filename + ".txt");

		Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file), "UTF8"));

		for (Object o : list) {
			writer.append(o + "\n");
		}

		writer.flush();
		writer.close();

		return file;
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(
			Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(
				map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}


	public static String getISO8601StringForCurrentDate() {
		Date now = new Date();
		return getISO8601StringForDate(now);
	}

	private static String getISO8601StringForDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.GERMANY);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+1"));
		return dateFormat.format(date);
	}

}
