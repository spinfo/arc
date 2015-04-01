package de.spinfo.arc.data;

import de.spinfo.arc.annotationmodel.annotatable.WorkingUnit;
import de.spinfo.arc.annotationmodel.annotatable.impl.WordImpl;
import de.spinfo.arc.annotationmodel.annotation.LanguageRange;
import de.spinfo.arc.annotationmodel.annotation.PageRange;
import de.spinfo.arc.persistance.service.query.WordQueries;
import de.spinfo.arc.persistance.service.query.WorkingUnitQueries;
import de.uni_koeln.spinfo.arc.utils.FileUtils;

import java.io.*;
import java.util.*;

/**
 * Created by franciscomondaca on 24/3/15.
 */
public class IOMongo {

    static WorkingUnitQueries wuQueries = new WorkingUnitQueries();
    static WordQueries wordQueries = new WordQueries();


    public long getPageNumberInWU(String Wu, long index) {

        WorkingUnit workingUnit = wuQueries.getWorkingUnit(Wu);

        List<PageRange> pageRange = workingUnit.getPages();

        List<Range> ranges = new ArrayList<>();

        for (PageRange pr : pageRange) {


            Range range = new Range(pr.getStart(), pr.getEnd());
            ranges.add(range);
        }


        for (Range range : ranges) {

            if (index > range.getStart() && index < range.getEnd()) {

                System.out.println(ranges.indexOf(range));
            }


        }

        return 0;
    }


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

            printNotTagged(word);

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


    public List<ForStand> getTokens(String fileName) throws Exception {
        List<ForStand> list = new ArrayList<>();
        Map<Long, Integer> map = new HashMap<>();

        List<Entry> getListOfTokens = readEntries(fileName);
        int i = 0;


        for (Entry e : getListOfTokens) {

            String form = e.getForm();

            if (form.equals("…")) {
                continue;
            }


            if (form.startsWith("„")) {
                String first = form.substring(0, 1);
                form = form.substring(1, form.length());

                ForStand az = new ForStand();
                az.setIndex(i);
                az.setForm(first);
                az.setPOS("P_OTH");
                list.add(az);
                i++;


                ForStand forStand = new ForStand();
                forStand.setIndex(i);
                forStand.setForm(form);
                forStand.setPOS(e.getPos());

                list.add(forStand);
                i++;

            }

            if (form.endsWith(".") || form.endsWith("?") || form.endsWith("!")) {
                String last = form.substring(form.length() - 1);
                form = form.substring(0, form.length() - 1);


                ForStand forStand = new ForStand();
                forStand.setIndex(i);
                forStand.setForm(form);
                forStand.setPOS(e.getPos());


                list.add(forStand);
                map.put(e.getIndex(), i);
                i++;

                ForStand eos = new ForStand();
                eos.setIndex(i);
                eos.setForm(last);
                eos.setPOS("P_EOS");

                list.add(eos);
                i++;

            } else if (form.endsWith(",") || form.endsWith(";") || form.endsWith(":")) {
                String last = form.substring(form.length() - 1);
                form = form.substring(0, form.length() - 1);


                ForStand forStand = new ForStand();
                forStand.setIndex(i);
                forStand.setForm(form);
                forStand.setPOS(e.getPos());
                list.add(forStand);
                map.put(e.getIndex(), i);

                i++;

                ForStand comma = new ForStand();
                comma.setIndex(i);
                comma.setForm(last);
                comma.setPOS("P_OTH");
                list.add(comma);

                i++;


            } else if (form.endsWith("“")) {


                // If we have a punctiation char in the penultimate position
                if (!Character.isAlphabetic(form.length() - 2)) {
                    form = form.substring(0, form.length() - 2);
                    System.out.println(form);
                    String penultimate = String.valueOf(form.length() - 2);
                    String last = form.substring(form.length() - 1);

                    ForStand forStand = new ForStand();
                    forStand.setIndex(i);
                    forStand.setForm(form);
                    forStand.setPOS(e.getPos());
                    list.add(forStand);
                    map.put(e.getIndex(), i);

                    i++;


                    if (penultimate.equals("!") || penultimate.equals("?") || penultimate.equals(".")) {

                        ForStand eos = new ForStand();
                        eos.setIndex(i);
                        eos.setForm(penultimate);
                        eos.setPOS("P_EOS");

                        list.add(eos);

                        i++;


                    } else {

                        ForStand oth = new ForStand();
                        oth.setIndex(i);
                        oth.setForm(penultimate);
                        oth.setPOS("P_OTH");

                        list.add(oth);

                        i++;

                    }


                    //Add '“'
                    ForStand ls = new ForStand();
                    ls.setIndex(i);
                    ls.setForm(last);
                    ls.setPOS("P_OTH");

                    list.add(ls);

                    i++;


                } else {
                    form = form.substring(0, form.length() - 2);
                    String last = form.substring(form.length() - 1);
                    ForStand forStand = new ForStand();
                    forStand.setIndex(i);
                    forStand.setForm(form);
                    forStand.setPOS(e.getPos());
                    list.add(forStand);
                    map.put(e.getIndex(), i);

                    i++;


                    ForStand ls = new ForStand();
                    ls.setIndex(i);
                    ls.setForm(last);
                    ls.setPOS("P_OTH");

                    list.add(ls);

                    i++;

                }


            } else {

                ForStand forStand = new ForStand();
                forStand.setIndex(i);
                forStand.setForm(form);
                forStand.setPOS(e.getPos());
                list.add(forStand);
                map.put(e.getIndex(), i);

                i++;




            }


        }

        FileUtils.writeMap(map, "index_mapping_");
        return list;

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


    public List<WordImpl> sursilvanTokensInRange(List<LanguageRange> languageRange) {

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


    private void printNotTagged(WordImpl word) {

        StringBuffer buffer = new StringBuffer();

        if (word.getLastPosAnnotation() != null) {

            if (word.getLastPosAnnotation().getPos().toString().equals("NULL") || word.getLastPosAnnotation().getPos().toString().equals("NOT_TAGGED")) {

                //if (word.getLastPosAnnotation().getUserId().toString().equals("lutzf") ||word.getLastPosAnnotation().getUserId().toString().equals("rivald")) {

                buffer.append(word.getIndex());
                buffer.append("\t");
                buffer.append(word.getLastFormAnnotation().getForm());
                buffer.append("\t");
                buffer.append(word.getLastPosAnnotation().getPos());
                buffer.append("\t");
                buffer.append(word.getLastPosAnnotation().getUserId());

                System.out.println(buffer.toString());
                // }
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