package de.uni_koeln.spinfo.arc.utils;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * Created by Andreas on 19.08.2015.
 */
public class TSVReader {

    public static String output_data_path = "../arc.data/output/";
    public static String input_data_path = "../arc.data/input/";

    private List<TSVEntry> entryList;
    private Map<String, TreeSet<String>> entryMap;

    public void readTSV(String filePath) throws IOException {

        entryList = new ArrayList<TSVEntry>();


        List<String> lines = FileUtils.fileToList(filePath);
        lines.remove(0);

        for(String line : lines) {
            String[] fields = line.split("\t");
            if (fields.length > 6) {
                entryList.add(new TSVEntry(fields[4].trim(), fields[5].trim(), fields[6].trim()));
            } else if (fields.length > 5) {
                entryList.add(new TSVEntry(fields[4].trim(), fields[5].trim(), ""));
            } else if (fields.length > 4) {
            entryList.add(new TSVEntry(fields[4].trim(), "", ""));
        } else
            System.out.println(line);
        }
        toEntryMap();
    }

    private void toEntryMap() {

        entryMap = new TreeMap<String, TreeSet<String>>();


        for(TSVEntry entry : entryList) {
            String lemma = entry.reduceStichwort();
            String pos = entry.getPos();

            if(entryMap.containsKey(lemma)) {
                TreeSet<String> posSet = entryMap.get(lemma);
                posSet.add(pos);
                entryMap.put(lemma,posSet);
            } else {
                TreeSet<String> posSet = new TreeSet<>();
                posSet.add(pos);
                entryMap.put(lemma,posSet);
            }
        }

    }

    public void printEntryMap(String outputPath, String fileName) throws IOException {

        List<String> txtOutput = new ArrayList<String>();

        for(Map.Entry<String, TreeSet<String>> mapEntry : entryMap.entrySet()){
            String lemma = mapEntry.getKey();
            for (String pos : mapEntry.getValue()) {
                txtOutput.add(lemma+"$"+pos);
            }
        }

        DictUtils.printList(txtOutput, outputPath,fileName);

    }

    public void toDictTxtFile(String outputPath, String fileName) throws IOException {

        List<String> txtOutput = new ArrayList<String>();

        for (TSVEntry entry : entryList) {
            txtOutput.add(entry.print());
        }

        DictUtils.printList(txtOutput, outputPath,fileName);
    }

    public void posStats(String outputPath, String fileName) throws IOException {

        Set<String> pos = new TreeSet<String>();

       for (TSVEntry entry : entryList) {
           pos.add(entry.getrGenus());
           pos.add(entry.getrGrammatik());
       }

       DictUtils.printSet(pos, outputPath, fileName);
    }
}
