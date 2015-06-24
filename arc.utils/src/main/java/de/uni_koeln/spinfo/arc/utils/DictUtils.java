package de.uni_koeln.spinfo.arc.utils;

import com.mongodb.*;

import java.io.*;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DictUtils {

	public static String outputPath = "../arc.data/output/";
	public static String inputPath = "../arc.data/input/";

	private DictUtils() {
		throw new AssertionError();
	}

	public static List<String> handleEntriesWithParenthesis(String filePath)
			throws IOException {

		List<String> list = new ArrayList<String>();

		FileInputStream fis = new FileInputStream(filePath);
		InputStreamReader isr = new InputStreamReader(fis, "UTF8");
		LineNumberReader reader = new LineNumberReader(isr);

		String currentLine;

		while ((currentLine = reader.readLine()) != null) {

			String[] cA = currentLine.split("((?<=\\$)|(?=\\$))");

			for (String s : cA) {

				if (s.contains("(")) {

					String[] parA = s.split("\\(");

				}

			}

		}
		reader.close();

		return list;
	}

	public static List<String> getLinesWithFinalDots(String filePath)
			throws IOException {

		List<String> list = new ArrayList<String>();

		FileInputStream fis = new FileInputStream(filePath);
		InputStreamReader isr = new InputStreamReader(fis, "UTF8");
		LineNumberReader reader = new LineNumberReader(isr);

		String currentLine;

		while ((currentLine = reader.readLine()) != null) {

			String[] cA = currentLine.split(" ");

			if (cA.length > 1) {

				if (cA[cA.length - 1].endsWith(".")
						&& !Character.isUpperCase(cA[0].codePointAt(0))) {

					list.add(currentLine);

					String next = reader.readLine();

					if (next != null
							&& !Character.isUpperCase(next.codePointAt(0))) {
						list.add(next);
					}

				}

			}

		}

		reader.close();
		return list;

	}

	public static List<String> addTags(String filePath) throws IOException {

		List<String> list = new ArrayList<String>();

		FileInputStream fis = new FileInputStream(filePath);
		InputStreamReader isr = new InputStreamReader(fis, "UTF8");
		LineNumberReader reader = new LineNumberReader(isr);

		String currentLine;

		while ((currentLine = reader.readLine()) != null) {

			StringBuilder builder = new StringBuilder();
			builder.append("<E>");
			builder.append(currentLine);
			builder.append("</E>");

			list.add(builder.toString());

		}
		System.out.println("Kandidaten gesamt: " +list.size());
		reader.close();
		return list;

	}

	public static List<String> txtToList(String filePath) throws IOException {

		List<String> list = new ArrayList<String>();

		FileInputStream fis = new FileInputStream(filePath);
		InputStreamReader isr = new InputStreamReader(fis, "UTF8");
		LineNumberReader reader = new LineNumberReader(isr);

		String currentLine;

		while ((currentLine = reader.readLine()) != null) {
			list.add(currentLine);
		}

		reader.close();
		return list;

	}

	public static List<String> cleanTextFile(String filePath)
			throws IOException {

		List<String> list = new ArrayList<String>();

		FileInputStream fis = new FileInputStream(filePath);
		InputStreamReader isr = new InputStreamReader(fis, "UTF8");
		LineNumberReader reader = new LineNumberReader(isr);

		Set<String> toAvoid = new HashSet<String>();
		toAvoid.add("m/f");
		toAvoid.add("rn/f");
		toAvoid.add("m/f,");
		toAvoid.add("rn/f,");
		toAvoid.add("m/f;");
		toAvoid.add("rn/f;");
		toAvoid.add("m/f-");
		toAvoid.add("rn/f-");

		String currentLine;

		while ((currentLine = reader.readLine()) != null) {

			String[] clAsA = currentLine.split(" ");

			StringBuilder builder = new StringBuilder();

			for (String s : clAsA) {

				// change false tokens

				s = s.replace("rn", "m");
				s = s.replace("ü.", "v.");
				s = s.replace("u.", "v.");
				s = s.replace("o.", "v.");

				// remove slashes
				if (!toAvoid.contains(s)) {

					String[] sAsA = s.split("((?<=\\/)|(?=\\/))");

					for (String t : sAsA) {

						if (t.equals("/")) {

							t = t.replace("/", "f");

						}

						builder.append(t);
						builder.append(" ");

					}

				} else {

					s = s.replace(s, "m/f");

					builder.append(s);
					builder.append(" ");

				}

			}

			list.add(builder.toString());

			// list.add(builder.toString());

		}

		reader.close();
		return list;

	}

	//
	public static File removeUnclosedParentheses(String filePath,
			String destPath, String newFileName) throws IOException {

		File inFile = new File(filePath);
		File outFile = new File(destPath + newFileName + ".txt");

		Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(outFile), "UTF-8"));

		BufferedReader br = null;

		String sCurrentLine;

		br = new BufferedReader(new FileReader(inFile));

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

			if (b.length() > 3) {
				if (b.subSequence(0, 2).equals("<E>")) {

					writer.append(b + "</E>" + "\n");

				} else {

					writer.append("<E>" + b + "</E>" + "\n");

				}
			}

		}
		br.close();
		writer.flush();
		writer.close();

		return outFile;
	}

	public static File addClosingTag(String filePath, String destPath,
			String newFileName) throws IOException {

		File inFile = new File(filePath);
		File outFile = new File(destPath + newFileName + ".txt");

		Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(outFile), "UTF-8"));

		BufferedReader br = null;

		String sCurrentLine;

		br = new BufferedReader(new FileReader(inFile));

		while ((sCurrentLine = br.readLine()) != null) {

			if (sCurrentLine.endsWith("")) {

				String nl = sCurrentLine + "</E>\n";
				writer.append(nl);

			} else

			{
				writer.append(sCurrentLine);

			}

			;

		}
		br.close();
		writer.flush();
		writer.close();

		return outFile;

	}

	public static File sortText(String filePath, String destPath,
			String newFileName) throws IOException {

		File outFile = new File(destPath + newFileName + ".txt");

		Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(outFile), "UTF-8"));

		Path f = Paths.get(filePath);

		BufferedReader br = Files.newBufferedReader(f, StandardCharsets.UTF_8);

		String line = br.readLine();

		List<String> lines = new ArrayList<String>();

		while (line != null) {
			lines.add(line);
			line = br.readLine();

		}

		String first = null;

		for (int i = 0; i < lines.size(); i++) {

			first = lines.get(i);

			String fft = first.substring(0, 1);

			if (first.endsWith("")) {

				if (i + 1 >= lines.size()) {
					break;
				}

				String second = lines.get(i + 1);

				fft = first.substring(0, 1);
				String sst = second.substring(0, 1);

				if (fft.equals(sst)) {

					writer.append(first + "</E>\n");

					continue;

				} else {

					writer.append(first);
					continue;
				}

			} else {

				writer.append(first);

			}

		}
		writer.close();
		return outFile;

	}

	public static List lemmasfromNVS(String file) throws IOException {

		List<String> lemmas = new ArrayList<String>();

		BufferedReader br = null;

		String sCurrentLine;

		br = new BufferedReader(new FileReader(file));

		while ((sCurrentLine = br.readLine()) != null) {

			String[] line = sCurrentLine.split("\\s");

			String lemma = line[0].replace("*", "").replace("<E>", "");
			lemmas.add(lemma);

		}
		br.close();
		return lemmas;

	}

	public static List lemmasfromAntlrList(String antlrFilePath)
			throws IOException {

		List<String> antlrLemmas = new ArrayList<String>();

		BufferedReader br = null;

		String sCurrentLine;

		br = new BufferedReader(new FileReader(antlrFilePath));

		while ((sCurrentLine = br.readLine()) != null) {

			String[] line = sCurrentLine.split("\\$");

			String lemma = line[0].replace("*", "");
			antlrLemmas.add(lemma);

		}
		br.close();

		return antlrLemmas;

	}

	public static List<String> diffLemmasNVS(List<String> nvsList,
			List<String> antlrList) {

		List<String> cleaned_nvsList = new ArrayList<String>();

		for (String s : nvsList) {

			s = s.replace("*", "").replace(",", "");
			cleaned_nvsList.add(s);
		}

		cleaned_nvsList.removeAll(antlrList);

		return cleaned_nvsList;

	}

	public static List<String> diffLemmasAntlr(List<String> nvsList,
			List<String> antlrList) {

		List<String> cleaned_nvsList = new ArrayList<String>();

		for (String s : nvsList) {

			s = s.replace("*", "").replace(",", "");
			cleaned_nvsList.add(s);
		}

		antlrList.removeAll(cleaned_nvsList);

		return antlrList;

	}

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

	// Visit all TXT files in Path
	public static <T> List joinLines(String foldersPath, final List<T> list)
			throws IOException {

		Path start = FileSystems.getDefault().getPath(foldersPath);
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file,
					BasicFileAttributes attrs) throws IOException {
				if (file.toString().endsWith(".txt")) {
					System.out.println(file.toString());

					try {
						getLines(list, file);
					} catch (IOException e) {

						e.printStackTrace();
					}

				}

				return FileVisitResult.CONTINUE;
			}

		});

		return list;
	}

	public static <T> List getLines(List<T> list, Path file) throws IOException {

		BufferedReader br = Files.newBufferedReader(file,
				StandardCharsets.UTF_8);

		String line;

		while ((line = br.readLine()) != null) {

			list.add((T) line);

		}

		return list;
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

	public static void removeUnclosedParenthesis(String nvs_0_200,
			String output_path, String nvs_0_200_pp) {
	}

}
