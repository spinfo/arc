package de.uni_koeln.spinfo.arc.utils;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by franciscomondaca on 3/12/14.
 */
public class FileUtils {

    private FileUtils() {
        throw new AssertionError();
    }

    public static String outputPath = "../arc.data/output/";
    public static String inputPath = "../arc.data/input/";

    public static <K, V> void writeMap(Map<K, V> matchedWords, String fileName) throws IOException {

        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(outputPath + fileName + getISO8601StringForCurrentDate()));

        outputStream.writeObject(matchedWords);

        outputStream.close();

    }


    private static Map<Long, Integer> getMap(String fileName) throws Exception {

        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FileUtils.outputPath + fileName));

        Map<Long, Integer> tokens = (Map<Long, Integer>) inputStream.readObject();

        inputStream.close();

        return tokens;


    }

    public static <T> void writeList(List<T> matchedWords, String fileName) throws IOException {

        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(outputPath + fileName));

        outputStream.writeObject(matchedWords);

        outputStream.close();

    }


    public static <K, V> File printMap(Map<K, V> map, String destPath,
                                       String fileName) throws IOException {

        File file = new File(destPath + fileName + getISO8601StringForCurrentDate() + ".txt");
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

        File file = new File(destPath + filename + getISO8601StringForCurrentDate() + ".txt");

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

        File file = new File(destPath + filename + getISO8601StringForCurrentDate() + ".txt");

        Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), "UTF8"));

        for (Object o : list) {
            writer.append(o + "\n");
        }

        writer.flush();
        writer.close();

        return file;
    }


    public static void printForStand(List<List<String>> sentences,
                                     String filename, int index) throws IOException {

        File file = new File(outputPath + "/forstand/" + filename + "_" + index + ".txt");

        Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), "UTF8"));


//        if (filename.startsWith("test")) {
//            for (List<String> list : sentences) {
//
//                for (String s : list) {
//
//                    String[] array = s.split("\\|");
//                    System.out.println(s);
//                    System.out.println(array[0]);
//                    writer.append(array[0]);
//                    writer.append("\n");
//                }
//                writer.append("\n");
//
//            }
//
//        } else {
//
//            for (List<String> list : sentences) {
//
//                for (String s : list) {
//
//                    writer.append(s);
//                    writer.append("\n");
//                }
//                writer.append("\n");
//
//            }
//        }


        for (List<String> list : sentences) {

            for (String s : list) {

                writer.append(s);
                writer.append(" ");
            }
            writer.append("\n");

        }
        writer.flush();
        writer.close();


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

    /**
     * Reads a File with UTF-8 Charsets, normalizes it according to NFC and returns it as a List of lines
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static List<String> fileToList(String filePath) throws IOException {

        ArrayList<String> normalized = new ArrayList<String>();

        ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);

        for (String s : lines) {

            s = Normalizer.normalize(s, Normalizer.Form.NFC);
            normalized.add(s);
        }
        return normalized;
    }


    public static Map<String, TreeSet<String>> readFullForms(String fileName)
            throws Exception {

        ObjectInputStream inputStream = new ObjectInputStream(
                new FileInputStream(FileUtils.outputPath + fileName));

        Map<String, TreeSet<String>> fullForms = (Map<String, TreeSet<String>>) inputStream
                .readObject();
        inputStream.close();
        return fullForms;
    }

    public static <K, V> void writeFullforms(Map<K, V> fullForms, String idiom)
            throws IOException {

        ObjectOutputStream outputStream = new ObjectOutputStream(
                new FileOutputStream(FileUtils.outputPath + idiom + "fullforms_"
                        + FileUtils.getISO8601StringForCurrentDate()));

        outputStream.writeObject(fullForms);

        outputStream.close();

    }


    public static void writeFullformsTxT(Map<String, TreeSet<String>> fullForms, String fileName) throws IOException {

        List<String> txtOutput = new ArrayList<String>();

        for (Map.Entry<String, TreeSet<String>> mapEntry : fullForms.entrySet()) {
            String lemma = mapEntry.getKey();
            for (String pos : mapEntry.getValue()) {
                txtOutput.add(lemma + "$" + pos);
            }
        }

        printList(txtOutput, outputPath, fileName);
    }






}
