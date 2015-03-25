package de.spinfo.arc.data;


import de.spinfo.arc.persistance.service.query.WordQueries;
import de.spinfo.arc.persistance.service.query.WorkingUnitQueries;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.WorkingUnit;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.impl.WordImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.LanguageRange;
import de.uni_koeln.spinfo.arc.utils.FileUtils;

import java.io.*;
import java.util.*;

/**
 * Created by franciscomondaca on 24/3/15.
 */
public class IOMongo {

    static WorkingUnitQueries wuQueries = new WorkingUnitQueries();
    static WordQueries wordQueries = new WordQueries();


    public List<Entry> getSursilvanGoldenEntries() {

        WorkingUnit workingUnit = wuQueries.getWorkingUnit("Band II");

        List<LanguageRange> languageRange = workingUnit.getLanguages();
        Set<Long> germanWords = germanWordsInRange(languageRange);
        List<WordImpl> sursilvanTokens = sursilvanTokensInRange(languageRange);

        List<Entry> entries = new ArrayList<>();

        for (WordImpl word : sursilvanTokens) {

            if (germanWords.contains(word.getIndex())) {
                continue;
            }

            if (word.getIndex() > 366350) {
                break;
            }

            //printNotTagged(word);

            Entry entry = new Entry();
            entry.setIndex(word.getIndex());
            entry.setForm(word.getLastFormAnnotation().getForm());
            if (word.getLastPosAnnotation() != null) {
                entry.setPos(word.getLastPosAnnotation().getPos().toString());
                entry.setAutor(word.getLastPosAnnotation().getUserId());


            }

            entries.add(entry);
        }


        return entries;
    }


    public Map<Integer, String> getTokens(String fileName) throws Exception {

        List<Entry> getListOfTokens = readEntries(fileName);
        Map<Integer, String> tokens = new HashMap<>();
        int i = 0;


        for (Entry e : getListOfTokens) {

            String form = e.getForm();

            if (form.equals("…")) {
                continue;
            }

            if (form.endsWith(".") || form.endsWith("?") || form.endsWith("!")) {
                String last = form.substring(form.length() - 1);
                form = form.substring(0, form.length() - 1);
                tokens.put(i, form);
                i++;

                tokens.put(i, last);
                i++;

            } else if (form.endsWith(",")) {

                tokens.put(i, form.substring(form.length() - 1));
                i++;


            } else if (form.endsWith("\".") || form.endsWith("\"?") || form.endsWith("\"!")) {

                int p = form.length() - 2;
                String penultimate = String.valueOf(form.charAt(p));

                form = form.substring(0, form.length() - 2);
                tokens.put(i, form);
                i++;


                tokens.put(i, penultimate);
                i++;


            } else {

                tokens.put(i, form);
                i++;
            }


        }


        return tokens;

    }


    public Set<Long> germanWordsInRange(List<LanguageRange> languageRange) {
        Set<Long> germanWords = new HashSet<>();


        for (LanguageRange lr : languageRange) {
            if (lr.getTitle().equals("Deutsch/Tudesg")) {
                List<WordImpl> wordsOfLang = wordQueries.getWordsByRange(lr);
                for (WordImpl german : wordsOfLang) {

                    germanWords.add(german.getIndex());
                }

            }

        }

        return germanWords;
    }


    List<WordImpl> sursilvanTokensInRange(List<LanguageRange> languageRange) {

        List<WordImpl> sursilvanTokens = new ArrayList<>();

        for (LanguageRange lr : languageRange) {

            if (lr.getTitle().equals("Sursilvan")) {

                List<WordImpl> wordsOfLang = wordQueries.getWordsByRange(lr);
                for (WordImpl sur : wordsOfLang) {

                    sursilvanTokens.add(sur);
                }

            }
        }


        return sursilvanTokens;
    }


    public void getModel(Map<Integer, String> goldenMap) throws IOException {

        File file = new File(FileUtils.outputPath + "model" + FileUtils.getISO8601StringForCurrentDate() + ".txt");
        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), "UTF8"));

        Map<Integer, String> treeMap = new TreeMap<>(goldenMap);


        for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {

            Integer index = entry.getKey();
            String form = entry.getValue();

            if (form.equals("!") || form.equals(".") || form.equals("?") || form.equals(",")) {
                out.append(form);
                out.append("_");
                out.append(form);
                out.append("\t");
            }

        }


    }

    //Temporary solution in order top avoid mutual dependence in maven
    private static List<Entry> readEntries(String fileName) throws Exception {

        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FileUtils.outputPath + fileName));

        List<Entry> tokens = (List<Entry>) inputStream.readObject();

        inputStream.close();

        return tokens;


    }


}