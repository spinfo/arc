package de.uni_koeln.spinfo.arc.parser;

import com.mongodb.*;

import java.io.*;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

public class NVSParser {

    static String nvs = "../arc.data/lexika/nvs/nvs.txt";
    static String nvs_test = "../arc.data/lexika/nvs/nvs_test.txt";
    static String nvs_komplett = "../arc.data/lexika/nvs/nvs_komplett.txt";
    static String nvs_komplett2 = "../arc.data/lexika/nvs/nvs_komplett2.txt";
    static String nvs_cleaned = "../arc.data/lexika/nvs/nvs_cleaned.txt";
    static String nvs_ng = "../arc.data/lexika/nvs/nvs_ng.txt";
    static String nvs_ng_2 = "../arc.data/lexika/nvs/nvs_ng_2.txt";
    static String nvs_ng_cleaned = "../arc.data/lexika/nvs/nvs_ng_cleaned.txt";
    static String nvs_ng_2_cleaned = "../arc.data/lexika/nvs/nvs_ng_2_cleaned.txt";

    static String nvs_genfiles_path = "../arc.data/";

    static String nvs_lastround = "../arc.data/lexika/nvs/nvs_lastround";

    static String nvs_completeEntries_NVS_0_200_sorted = "../arc.data/lexika/nvs/nvs_newLineSepperated_entries/processed_antlr/sorted/NVS_0_200.txt";
    static String nvs_completeEntries_NVS_201_400_sorted = "../arc.data/lexika/nvs/nvs_newLineSepperated_entries/processed_antlr/sorted/NVS_201_400.txt";
    static String nvs_completeEntries_NVS_401_600_sorted = "../arc.data/lexika/nvs/nvs_newLineSepperated_entries/processed_antlr/sorted/NVS_401_600.txt";
    static String nvs_completeEntries_NVS_601_800_sorted = "../arc.data/lexika/nvs/nvs_newLineSepperated_entries/processed_antlr/sorted/NVS_601_800.txt";
    static String nvs_completeEntries_NVS_801_1000_sorted = "../arc.data/lexika/nvs/nvs_newLineSepperated_entries/processed_antlr/sorted/NVS_801_1000.txt";

    static String noantlr_d_rival = "../arc.data/lexika/nvs/nvs_noANTLR/d_rival/";
    static String noantlr_folder_sorted = "../arc.data/lexika/nvs/nvs_noANTLR/sorted/";

    static String noantlr_path = "/Users/franciscomondaca/spinfo/repositories/lisa_misc/arc.data/lexika/sursilvan/nvs/extraction/nvs_noANTLR";


    static String wholeEntries_unsorted = "../arc.data/lexika/nvs/nvs_newLineSepperated_entries/processed_antlr/unsorted/";
    static String wholeEntries_sorted = "../arc.data/lexika/nvs/nvs_newLineSepperated_entries/processed_antlr/sorted/";

    static String singleLines_unsorted = "../arc.data/lexika/nvs/nvs_2_LinesEachEntry/processed_antlr/unsorted/";
    static String singleLines_sorted = "../arc.data/lexika/nvs/nvs_2_LinesEachEntry/processed_antlr/sorted/";

    Set<String> avoidEntries = new HashSet<>();


    public Map<String, Set<String>> getLemmas(DBCollection nvs) {
        Collator collator = Collator.getInstance(Locale.GERMAN);

        Map<String, Set<String>> lemmas = new TreeMap<>();

        DBCursor lemmasFromMongo = nvs.find();

        for (DBObject o : lemmasFromMongo) {

            String lemma = (String) o.get("entry");
            BasicDBObject pos = (BasicDBObject) o.get("pos");
            String nvs_pos = (String) pos.get("nvs_pos");


            Set<String> pos_set;

            if (lemmas.get(lemma) == null) {
                pos_set = new CopyOnWriteArraySet();
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
                        noAntlrToMongo(file, collection);
                    } catch (IOException e) {

                        e.printStackTrace();
                    }

                }

                return FileVisitResult.CONTINUE;
            }

        });

        return collection;
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

            //ART
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

            //CONJ
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
            //NN_P
            case "ON":
            case "PN":
                eagles_pos = "NN_P";
                break;
            // PREP
            case "prep":
                eagles_pos = "PREP";
                break;

            //CARDINAL_NUMBERS
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

    public List<String> NVSDiff(DBCollection nvs, DBCollection nvs_noantlr) {

        // List<String> diff = new ArrayList<>();
        List<String> nvsList = new ArrayList<>();
        List<String> nvs_noantlrList = new ArrayList<>();

        DBCursor nvsCursor = nvs.find();
        DBCursor noAntlrCursor = nvs_noantlr.find();

        while (nvsCursor.hasNext()) {
            DBObject doc = nvsCursor.next();
            String entry = (String) doc.get("entry");
            nvsList.add(entry);

        }

        while (noAntlrCursor.hasNext()) {
            DBObject doc = noAntlrCursor.next();
            String entry = (String) doc.get("entry");
            nvs_noantlrList.add(entry);

        }

        nvsList.removeAll(nvs_noantlrList);

        Collections.sort(nvsList);

        return nvsList;

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

    public DBCollection noAntlrToMongo(Path file, DBCollection collection)
            throws IOException {

        String line;

        BufferedReader br = Files.newBufferedReader(file,
                StandardCharsets.UTF_8);

        while ((line = br.readLine()) != null) {
            if (!line.startsWith("#")) {

                String[] nvs_line = line.split("\\$");
                DBObject mongoEntry = nvsEntryToMongoObject(nvs_line);
                collection.insert(mongoEntry);
            }
        }

        return collection;
    }

    // Extract only the entries from the generated text file.
    public File removeEmptyLines(String fileName, String destPath,
                                 String newFileName) throws Exception, FileNotFoundException {
        File file = new File(destPath + newFileName + ".txt");
        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), "UTF8"));

        FileInputStream in = new FileInputStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(in,
                "UTF-8"));
        String line;

        while ((line = br.readLine()) != null) {

            if (!line.startsWith("#")) {
                out.append(line + '\n');

            }

        }

        br.close();
        out.flush();
        out.close();

        return file;
    }

    // Visit all TXT files in Path
    public void parseTXT(String foldersPath, final String destPath)
            throws IOException {

        Path start = FileSystems.getDefault().getPath(foldersPath);
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file,
                                             BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".txt")) {
                    System.out.println(file.toString());

                    try {
                        removeSecondLines(file, destPath);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                }

                return FileVisitResult.CONTINUE;
            }

        });

    }

    // Extract only the entries from the generated text file.
    public File removeSecondLines(Path fileName, String destPath)
            throws Exception, FileNotFoundException {

        String[] fileStrings = fileName.toString().split("/");

        String newFileName = fileStrings[5];
        System.out.println(newFileName);

        File file = new File(destPath + newFileName + "_fl");

        System.out.println("creating " + file.toString());

        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), "UTF8"));

        BufferedReader br = Files.newBufferedReader(fileName,
                StandardCharsets.UTF_8);
        String line;

        while ((line = br.readLine()) != null) {

            if (line.startsWith("<E>")) {
                out.append(line + '\n');
                System.out.println(line);
            }

        }

        br.close();
        out.flush();
        out.close();

        return file;
    }

    // Visit all TXT files in Path
    public void sortTXT(String foldersPath, final String destPath)
            throws IOException {

        Path start = FileSystems.getDefault().getPath(foldersPath);
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file,
                                             BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".txt")) {
                    System.out.println(file.toString());

                    try {
                        sortStrings(file, destPath);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                }

                return FileVisitResult.CONTINUE;
            }

        });

    }

    // 'Cleans' the text file generated after parsing the NVS.
    public File sortStrings(Path fileName, String destPath) throws Exception,
            FileNotFoundException {

        String[] fileStrings = fileName.toString().split("/");

        String newFileName = fileStrings[6];
        System.out.println(newFileName);

        File file = new File(destPath + newFileName + "_cleaned.txt");
        File errors = new File(destPath + newFileName + "_errors" + ".txt");

        System.out.println("creating " + file.toString());

        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), "UTF8"));

        Writer err = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(errors), "UTF8"));

        BufferedReader br = Files.newBufferedReader(fileName,
                StandardCharsets.UTF_8);

        String line = br.readLine();

        List<String> lines = new ArrayList<String>();

        while (line != null) {
            lines.add(line);
            line = br.readLine();

        }

        String first = null;
        String second = null;
        String third = null;
        String fourth = null;

        for (int i = 0; i < lines.size(); i++) {

            first = lines.get(i);

            if (i + 1 >= lines.size()) {
                break;
            }

            second = lines.get(i + 1);

            String f = first.substring(0, 1);
            String s = second.substring(0, 1);

            // if there is a new letter
            if (!s.equalsIgnoreCase(f)) {

                if (i + 2 >= lines.size()) {
                    break;
                }

                third = lines.get(i + 2);
                String t = third.substring(0, 1);

                if (i + 3 >= lines.size()) {
                    break;
                }

                fourth = lines.get(i + 3);
                String ft = fourth.substring(0, 1);

                // Change of letter, do nothing
                if (s.equalsIgnoreCase(t) && s.equalsIgnoreCase(ft)) {
                    continue;
                }
                // Delete entry between two with the same letter
                else if (f.equalsIgnoreCase(t)) {

                    // 1
                    err.append(second + "\n");
                    lines.remove(second);
                    i = 0;
                    continue;

                }
                // Delete two misplaced entries together, same letter
                else if (s.equalsIgnoreCase(t) && !s.equalsIgnoreCase(ft)) {

                    // 2
                    err.append(second + " 2" + "\n");
                    err.append(third + " 2" + "\n");
                    lines.remove(second);
                    lines.remove(third);
                    i = 0;
                    continue;
                    // Delete two misplaced entries together, different letter
                } else if (f.equalsIgnoreCase(ft) && !s.equalsIgnoreCase(t)) {

                    // 3
                    err.append(second + " 3" + "\n");
                    err.append(third + " 3" + "\n");
                    lines.remove(second);
                    lines.remove(third);
                    i = 0;
                    continue;
                }

            }

        }

        lines = orderEntries(lines, err);

        for (String s : lines) {

            out.append(s + "\n");

        }

        out.flush();
        out.close();

        return file;

    }

    // This Method is used in order to do a second sorting to the NVS' entries
    List<String> orderEntries(List<String> entries, Writer writer)
            throws IOException {

        String first = null;
        String second = null;
        String third = null;
        writer.append("++++" + "\n");
        for (int i = 0; i <= entries.size(); i++) {

            first = entries.get(i);

            if (i + 1 >= entries.size()) {
                break;
            }

            second = entries.get(i + 1);

            String f = first.substring(0, 2);
            String s = second.substring(0, 2);

            if (i + 2 >= entries.size()) {
                break;
            }

            third = entries.get(i + 2);
            String t = third.substring(0, 2);

            if (!s.equalsIgnoreCase(f)) {

                if (f.equalsIgnoreCase(t)) {
                    // 4
                    writer.append(second + " 4" + "\n");
                    entries.remove(second);
                    i = 0;
                    continue;

                }

            }

        }

        writer.flush();
        writer.close();

        return entries;

    }

    // Visit all TXT files in Path
    public void sortTXTCollator(String foldersPath, final String destPath,
                                final RuleBasedCollator collator, final String method)
            throws IOException {

        Path start = FileSystems.getDefault().getPath(foldersPath);
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file,
                                             BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".txt")) {
                    System.out.println(file.toString());

                    try {

                        switch (method) {

                            case "sortStringsCollator":
                                sortStringsCollator(file, destPath, collator);
                                break;

                            default:
                                break;
                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                }

                return FileVisitResult.CONTINUE;
            }

        });

    }

    // 'Cleans' the text file generated after parsing the NVS.
    public File sortStringsCollator(Path fileName, String destPath,
                                    RuleBasedCollator collator) throws Exception, FileNotFoundException {

        String[] fileStrings = fileName.toString().split("/");

        String newFileName = fileStrings[fileStrings.length - 1];
        newFileName = newFileName.replace(".txt", "");
        System.out.println(newFileName);

        File file = new File(destPath + newFileName + ".txt");
        File errors = new File(destPath + newFileName + "_errors_collator"
                + ".txt");

        System.out.println("newfile: " + file.toString());
        System.out.println("newErrorsfile: " + errors.toString());

        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), "UTF8"));

        Writer err = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(errors), "UTF8"));

        BufferedReader br = Files.newBufferedReader(fileName,
                StandardCharsets.UTF_8);

        String line = br.readLine();

        List<String> lines = new ArrayList<String>();

        while (line != null) {
            lines.add(line);
            line = br.readLine();

        }

        String first = null;
        String second = null;
        String third = null;
        String fourth = null;
        String fifth = null;

        for (int i = 0; i < lines.size(); i++) {

            first = lines.get(i);

            if (i + 1 >= lines.size()) {
                break;
            }

            second = lines.get(i + 1);

            String f = first.substring(0, 1);
            String s = second.substring(0, 1);

            // if there is a new letter
            if (collator.compare(f, s) != 0) {

                if (i + 2 >= lines.size()) {
                    break;
                }

                third = lines.get(i + 2);
                String t = third.substring(0, 1);

                if (i + 4 >= lines.size()) {
                    break;
                }

                fourth = lines.get(i + 3);
                String fo = fourth.substring(0, 1);

                fifth = lines.get(i + 4);
                String fi = fifth.substring(0, 1);

                // Change of letter, continue
                if (collator.compare(s, t) == 0 && collator.compare(s, fo) == 0) {
                    continue;
                }

                // Delete entry between two with the same letter

                else if (collator.compare(f, t) == 0) {
                    err.append(second + " 1" + "\n");
                    lines.remove(second);
                    i = 0;
                    continue;

                }

                // Delete two misplaced entries together, same letter
                else if (collator.compare(s, t) == 0
                        && collator.compare(s, fi) != 0) {
                    err.append(second + " 2" + "\n");
                    err.append(third + " 2" + "\n");
                    lines.remove(second);
                    lines.remove(third);
                    i = 0;
                    continue;

                }

                // Delete two misplaced entries together, different letter

                else if (collator.compare(f, fo) == 0
                        && collator.compare(s, t) != 0) {
                    err.append(second + " 3" + "\n");
                    err.append(third + " 3" + "\n");
                    lines.remove(second);
                    lines.remove(third);
                    i = 0;
                    continue;
                }

            }

        }

        lines = orderEntriesCollator(collator, lines, err);

        for (String s : lines) {

            out.append(s + "\n");

        }

        out.flush();
        out.close();

        return file;

    }

    // This Method is used in order to do a second sorting of the NVS' entries
    List<String> orderEntriesCollator(RuleBasedCollator collator,
                                      List<String> entries, Writer writer) throws IOException {

        String first = null;
        String second = null;
        String third = null;
        writer.append("++++" + "\n");
        for (int i = 0; i <= entries.size(); i++) {

            first = entries.get(i);

            if (i + 1 >= entries.size()) {
                break;
            }

            second = entries.get(i + 1);

            String f = first.substring(0, 2);
            String s = second.substring(0, 2);

            if (i + 2 >= entries.size()) {
                break;
            }

            third = entries.get(i + 2);
            String t = third.substring(0, 2);

            if (collator.compare(s, f) != 0) {

                if (collator.compare(f, t) == 0) {

                    writer.append(second + " 4" + "\n");
                    entries.remove(second);
                    i = 0;
                    continue;

                }

            }

        }

        writer.flush();
        writer.close();

        return entries;

    }

    // 'Cleans' the text file generated after parsing the NVS.
    public List<String> sortPDFCollator(Path fileName, String destPath,
                                        RuleBasedCollator collator) throws Exception, FileNotFoundException {

        String[] fileStrings = fileName.toString().split("/");

        String newFileName = fileStrings[fileStrings.length - 1];
        newFileName = newFileName.replace(".txt", "");
        System.out.println(newFileName);

        File file = new File(destPath + newFileName + ".txt");
        File errors = new File(destPath + newFileName + "_errors_collator"
                + ".txt");

        System.out.println("newfile: " + file.toString());
        System.out.println("newErrorsfile: " + errors.toString());

        Writer err = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(errors), "UTF8"));

        BufferedReader br = Files.newBufferedReader(fileName,
                StandardCharsets.UTF_8);

        String line = br.readLine();

        List<String> lines = new ArrayList<String>();

        // List<String> newLines = new ArrayList<String>();

        while (line != null) {

            line = line.replace("</E>", "");

            lines.add(line);
            line = br.readLine();

        }

        String first = null;
        String second = null;
        String third = null;
        String fourth = null;
        String fifth = null;
        String sixth = null;

        for (int i = 0; i < lines.size(); i++) {

            first = lines.get(i);

            if (i + 1 >= lines.size()) {
                break;
            }

            second = lines.get(i + 1);

            String f = first.substring(0, 1);
            String s = second.substring(0, 1);

            // if there is a new letter
            if (collator.compare(f, s) != 0) {

                if (i + 2 >= lines.size()) {
                    break;
                }

                third = lines.get(i + 2);
                String t = third.substring(0, 1);

                if (i + 4 >= lines.size()) {
                    break;
                }

                fourth = lines.get(i + 3);
                String fo = fourth.substring(0, 1);

                fifth = lines.get(i + 4);
                String fi = fifth.substring(0, 1);

                // Change of letter, continue
                if (collator.compare(s, t) == 0 && collator.compare(s, fo) == 0) {
                    continue;
                }

                // Delete entry between two with the same letter

                else if (collator.compare(f, t) == 0) {
                    err.append(second + " 1" + "\n");
                    lines.remove(second);
                    i = 0;
                    continue;

                }

                // Delete two misplaced entries together, same letter
                else if (collator.compare(s, t) == 0
                        && collator.compare(s, fi) != 0) {
                    err.append(second + " 2" + "\n");
                    err.append(third + " 2" + "\n");
                    lines.remove(second);
                    lines.remove(third);
                    i = 0;
                    continue;

                }

                // Delete two misplaced entries together, different letter

                else if (collator.compare(f, fo) == 0
                        && collator.compare(s, t) != 0) {
                    err.append(second + " 3" + "\n");
                    err.append(third + " 3" + "\n");
                    lines.remove(second);
                    lines.remove(third);
                    i = 0;
                    continue;
                }

            }

        }

        lines = orderPDFCollator(collator, lines, err);

        // for (String s : lines) {
        //
        // out.append(s + "\n");
        //
        // }

        // out.close();

        return lines;

    }

    // This Method is used in order to do a second sorting to the NVS' entries
    List<String> orderPDFCollator(RuleBasedCollator collator,
                                  List<String> entries, Writer writer) throws IOException {

        String first = null;
        String second = null;
        String third = null;
        writer.append("++++" + "\n");
        for (int i = 0; i <= entries.size(); i++) {

            first = entries.get(i);

            if (i + 1 >= entries.size()) {
                break;
            }

            second = entries.get(i + 1);

            String f = first.substring(0, 2);
            String s = second.substring(0, 2);

            if (i + 2 >= entries.size()) {
                break;
            }

            third = entries.get(i + 2);
            String t = third.substring(0, 2);

            if (collator.compare(s, f) != 0) {

                if (collator.compare(f, t) == 0) {

                    writer.append(second + " 4" + "\n");
                    entries.remove(second);
                    i = 0;
                    continue;

                }

            }

        }

        writer.close();

        return entries;

    }

    // Deprecated
    public void addEaglesPOS(DBCollection nvs, Set<String> nvsPos,
                             String eaglePos) {

        String[] nvs_pos_array = nvsPos.toArray(new String[nvsPos.size()]);

        BasicDBObject updateQuery = new BasicDBObject();
        updateQuery.append("$set",
                new BasicDBObject().append("pos.eagles_pos", eaglePos));

        BasicDBObject searchQuery = (BasicDBObject) QueryBuilder
                .start("pos.nvs_pos").in(nvs_pos_array).get();

        nvs.updateMulti(searchQuery, updateQuery);

    }

    public Map<String, Integer> nvsPOSWithoutEaglesPOSMapping(
            String collection_name) throws UnknownHostException {

        Map<String, Integer> occurrences = new HashMap<String, Integer>();
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("arc");
        DBCollection collection = db.getCollection(collection_name);

        DBCursor cursor = collection.find();

        while (cursor.hasNext()) {
            DBObject o = cursor.next();
            DBObject pos = (BasicDBObject) o.get("pos");
            String eagles_pos = (String) pos.get("eagles_pos");
            String nvs_pos = (String) pos.get("nvs_pos");

            if (eagles_pos == null) {
                Integer count = occurrences.get(nvs_pos);
                // System.out.println(pos);
                if (count == null) {
                    occurrences.put(nvs_pos, 1);
                } else {
                    occurrences.put(nvs_pos, count + 1);
                }
            }

        }

        return occurrences;
    }


    // Does not work properly, because some entries are repeated
    public Map<String, String> nvsToMap(BufferedReader br) throws IOException {

        Map<String, String> entries = new HashMap<String, String>();

        String line;
        while ((line = br.readLine()) != null) {
            if (!line.startsWith("#")) {
                String[] nvs_line = line.split("\\$");
                entries.put(nvs_line[0], nvs_line[1]);
            }
        }

        return entries;
    }

}
