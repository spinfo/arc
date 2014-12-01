package de.uni_koeln.spinfo.arc.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class SignorelliParser {
	
	
	static String signorelli = "../arc.data/lexika/signorelli/Signorelli";

	// Visit all TXT files in Path
	public DBCollection txtToMongo(String foldersPath,
			final DBCollection collection) throws IOException {

		Path start = FileSystems.getDefault().getPath(foldersPath);
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file,
					BasicFileAttributes attrs) throws IOException {
				if (file.toString().endsWith(".txt")) {
					System.out.println(file.toString());

					try {
						SignorelliToMongo(file, collection);
					} catch (IOException e) {

						e.printStackTrace();
					}

				}

				return FileVisitResult.CONTINUE;
			}

		});

		return collection;
	}

	public DBCollection SignorelliToMongo(Path file, DBCollection collection)
			throws IOException {

		String line;

		BufferedReader br = Files.newBufferedReader(file,
				StandardCharsets.UTF_8);

		while ((line = br.readLine()) != null) {
			if (!line.startsWith("#")) {

				if (line.contains(">")) {

					String[] nvs_line = line.split("\\>");

					for (String s : nvs_line) {

						BasicDBObject entry = new BasicDBObject();

						BasicDBObject pos = new BasicDBObject();

						entry.put("entry", s);
						// Add nvs_pos info
						pos.put("signorelli_pos", "NONE");
						entry.put("pos", pos);
						collection.insert(entry);

					}

				} else {
					String[] nvs_line = line.split("\\$");
					DBObject mongoEntry = nvsEntryToMongoObject(nvs_line);
					collection.insert(mongoEntry);
				}

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

		String nvs_pos = nvs_line[1];

		String eagles_pos = null;

		switch (nvs_pos) {

		// ADJ
		case "adj":
			eagles_pos = "ADJ";
			break;
		// ADJ_NUM:
		case "num":
			eagles_pos = "ADJ_NUM";
			break;
		// ADV
		case "adv":
			eagles_pos = "ADV";
			break;
		// CONJ
		case "cj":
			eagles_pos = "CONJ";
			break;
		// INT
		case "interj":
			eagles_pos = "INT";
			break;
		// NN
		case "m":
		case "f":
		case "n":
		case "f/coll":
		case "pl":
		case "coll":
		case "sg":
		case "subst":
			eagles_pos = "NN";
			break;

		case "nom":
			eagles_pos = "NN_P";
			break;

		// PREP
		case "prep":
			eagles_pos = "PREP";
			break;
		// PRON
		case "pron":
			eagles_pos = "PRON";
			break;
		// PRON
		case "pron dem":
			eagles_pos = "PRON_DIM";
			break;// PRON
		case "pron indef":
			eagles_pos = "PRON_IND";
			break;// PRON
		case "pron interr":
			eagles_pos = "PRON_IES";
			break;// PRON
		case "pron pers":
			eagles_pos = "PRON_PER";
			break;
		case "pron poss":
			eagles_pos = "PRON_POSS";
			break;
		case "pron rel":
			eagles_pos = "PRON_REL";
			break;

		// V_GVRB
		case "tr":
		case "intr":
		case "refl":
		case "v":
			eagles_pos = "V_GVRB";
			break;
		default:
			break;
		}

		// Add nvs_pos info
		pos.put("signorelli_pos", nvs_line[1]);
		// Add eagles_pos info
		if (eagles_pos != null) {
			pos.put("eagles_pos", eagles_pos);
		}

		entry.put("pos", pos);

		return entry;

	}

}
