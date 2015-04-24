package de.spinfo.arc.data;

import com.mongodb.*;
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


    public static final String sursilvan = "Sursilvan";
    public static final String sutsilvan = "Sutsilvan";
    public static final String surmiran = "Surmiran";
    public static final String puter = "Puter";
    public static final String vallader = "Vallader";
    public static final String sutsettisch = "Sutsettisch";
    public static final String bivio = "Bivio";
    public static final String bergagliot = "Bergagliot";
    public static final String val_mustair = "Val Müstair";
    public static final String buehlers_koine = "Bühlers Koine";
    public static final String jauer = "Jauer";
    public static final String deutsch = "Deutsch/Tudesg";
    public static final String latein = "Latein";
    public static final String italiano = "Italiano";
    public static final String auters = "Auters";


    public static Set<String> chrestLanguages() {

        Set<String> languages = new HashSet<>();

        languages.add(sursilvan);
        languages.add(sutsilvan);
        languages.add(surmiran);
        languages.add(puter);
        languages.add(vallader);
        languages.add(sutsettisch);
        languages.add(bivio);
        languages.add(bergagliot);
        languages.add(val_mustair);
        languages.add(buehlers_koine);
        languages.add(jauer);
        languages.add(deutsch);
        languages.add(latein);
        languages.add(italiano);
        languages.add(auters);


        return languages;
    }


    public List<LangRange> getLanguageRanges(DBCollection collection) {


        DBCursor cursor = collection.find();

        List<LangRange> ranges = new ArrayList<>();

        int i = 1;
        while (cursor.hasNext()) {

            BasicDBObject annotations = (BasicDBObject) cursor.next().get("annotations");

            BasicDBList langRange = (BasicDBList) annotations.get("LANGUAGE_RANGE");
            ArrayList<BasicDBObject> langRangeArray = (ArrayList) langRange;

            System.out.println("Band: " + i + "   " + langRange.size());

            for (DBObject object : langRangeArray) {

                String title = (String) object.get("title");
                Integer start = (Integer) object.get("start");
                Integer end = (Integer) object.get("end");

                LangRange lr = new LangRange();
                lr.setBand(i);
                lr.setLanguage(title);
                lr.setStart(start);
                lr.setEnd(end);
                ranges.add(lr);
            }

            i++;
        }


        return ranges;
    }


    public Map<String, List<MongoWord>> getWords(List<LangRange> langRanges, DBCollection words) {

        Map<String, List<MongoWord>> allTokens = new HashMap<>();

        List<MongoWord> sursilvanTokens = new ArrayList<>();
        List<MongoWord> sutsilvanTokens = new ArrayList<>();
        List<MongoWord> surmiranTokens = new ArrayList<>();
        List<MongoWord> puterTokens = new ArrayList<>();
        List<MongoWord> valladerTokens = new ArrayList<>();
        List<MongoWord> sutsettischTokens = new ArrayList<>();
        List<MongoWord> bivioTokens = new ArrayList<>();
        List<MongoWord> bergagliotTokens = new ArrayList<>();
        List<MongoWord> valmustairTokens = new ArrayList<>();
        List<MongoWord> buehlerskoineTokens = new ArrayList<>();
        List<MongoWord> jauerTokens = new ArrayList<>();
        List<MongoWord> deutschTokens = new ArrayList<>();
        List<MongoWord> lateinTokens = new ArrayList<>();
        List<MongoWord> italianoTokens = new ArrayList<>();
        List<MongoWord> autersTokens = new ArrayList<>();


        for (LangRange lr : langRanges) {

            String s = lr.getLanguage();
            Long start = lr.getStart();
            Long end = lr.getEnd();
            System.out.println(lr.toString());
            for (long i = start; i <= end; i++) {

                BasicDBObject w = new BasicDBObject("index", i);

                DBObject o = words.findOne(w);

                BasicDBObject annotations = (BasicDBObject) o.get("annotations");
                BasicDBList forms = (BasicDBList) annotations.get("FORM");
                //ArrayList<BasicDBObject> formsArray = (ArrayList) forms;
                BasicDBObject lastForm = (BasicDBObject) forms.get(forms.size() - 1);
                String lf = lastForm.getString("form");


                MongoWord mongoWord = new MongoWord();
                mongoWord.setIndex(i);
                mongoWord.setLanguage(s);
                mongoWord.setWord(lf);


                switch (s) {

                    case sursilvan:
                        sursilvanTokens.add(mongoWord);
                        break;
                    case sutsilvan:
                        sutsilvanTokens.add(mongoWord);
                        break;
                    case surmiran:
                        surmiranTokens.add(mongoWord);
                        break;
                    case puter:
                        puterTokens.add(mongoWord);
                        break;
                    case vallader:
                        valladerTokens.add(mongoWord);
                        break;
                    case sutsettisch:
                        sutsettischTokens.add(mongoWord);
                        break;
                    case bivio:
                        bivioTokens.add(mongoWord);
                        break;
                    case bergagliot:
                        bergagliotTokens.add(mongoWord);
                        break;
                    case val_mustair:
                        valmustairTokens.add(mongoWord);
                        break;
                    case buehlers_koine:
                        buehlerskoineTokens.add(mongoWord);
                        break;
                    case jauer:
                        jauerTokens.add(mongoWord);
                        break;
                    case deutsch:
                        deutschTokens.add(mongoWord);
                        break;
                    case italiano:
                        italianoTokens.add(mongoWord);
                        break;
                    case auters:
                        autersTokens.add(mongoWord);
                        break;
                    case latein:
                        lateinTokens.add(mongoWord);
                        break;
                    default:
                        break;

                }


            }


        }


        allTokens.put(sursilvan, sursilvanTokens);
        allTokens.put(sutsilvan, sutsilvanTokens);
        allTokens.put(surmiran, surmiranTokens);
        allTokens.put(puter, puterTokens);
        allTokens.put(vallader, valladerTokens);
        allTokens.put(sutsettisch, sutsettischTokens);
        allTokens.put(bivio, bivioTokens);
        allTokens.put(bergagliot, bergagliotTokens);
        allTokens.put(val_mustair, valmustairTokens);
        allTokens.put(buehlers_koine, buehlerskoineTokens);
        allTokens.put(jauer, jauerTokens);
        allTokens.put(deutsch, deutschTokens);
        allTokens.put(italiano, italianoTokens);
        allTokens.put(auters, autersTokens);
        allTokens.put(latein, lateinTokens);


        return allTokens;
    }


    public Map<String, List<String>> getTokens(List<LangRange> langRanges, DBCollection words) {

        Map<String, List<String>> allTokens = new HashMap<>();

        List<String> sursilvanTokens = new ArrayList<>();
        List<String> sutsilvanTokens = new ArrayList<>();
        List<String> surmiranTokens = new ArrayList<>();
        List<String> puterTokens = new ArrayList<>();
        List<String> valladerTokens = new ArrayList<>();
        List<String> sutsettischTokens = new ArrayList<>();
        List<String> bivioTokens = new ArrayList<>();
        List<String> bergagliotTokens = new ArrayList<>();
        List<String> valmustairTokens = new ArrayList<>();
        List<String> buehlerskoineTokens = new ArrayList<>();
        List<String> jauerTokens = new ArrayList<>();
        List<String> deutschTokens = new ArrayList<>();
        List<String> lateinTokens = new ArrayList<>();
        List<String> italianoTokens = new ArrayList<>();
        List<String> autersTokens = new ArrayList<>();


        for (LangRange lr : langRanges) {

            String s = lr.getLanguage();
            Long start = lr.getStart();
            Long end = lr.getEnd();
            System.out.println(lr.toString());
            for (long i = start; i <= end; i++) {

                BasicDBObject w = new BasicDBObject("index", i);

                DBObject o = words.findOne(w);

                BasicDBObject annotations = (BasicDBObject) o.get("annotations");
                BasicDBList forms = (BasicDBList) annotations.get("FORM");
                //ArrayList<BasicDBObject> formsArray = (ArrayList) forms;
                BasicDBObject lastForm = (BasicDBObject) forms.get(forms.size() - 1);
                String lf = lastForm.getString("form");


                switch (s) {

                    case sursilvan:
                        sursilvanTokens.add(lf);
                        break;
                    case sutsilvan:
                        sutsilvanTokens.add(lf);
                        break;
                    case surmiran:
                        surmiranTokens.add(lf);
                        break;
                    case puter:
                        puterTokens.add(lf);
                        break;
                    case vallader:
                        valladerTokens.add(lf);
                        break;
                    case sutsettisch:
                        sutsettischTokens.add(lf);
                        break;
                    case bivio:
                        bivioTokens.add(lf);
                        break;
                    case bergagliot:
                        bergagliotTokens.add(lf);
                        break;
                    case val_mustair:
                        valmustairTokens.add(lf);
                        break;
                    case buehlers_koine:
                        buehlerskoineTokens.add(lf);
                        break;
                    case jauer:
                        jauerTokens.add(lf);
                        break;
                    case deutsch:
                        deutschTokens.add(lf);
                        break;
                    case italiano:
                        italianoTokens.add(lf);
                        break;
                    case auters:
                        autersTokens.add(lf);
                        break;
                    case latein:
                        lateinTokens.add(lf);
                        break;
                    default:
                        break;

                }


            }


        }


        allTokens.put(sursilvan, sursilvanTokens);
        allTokens.put(sutsilvan, sutsilvanTokens);
        allTokens.put(surmiran, surmiranTokens);
        allTokens.put(puter, puterTokens);
        allTokens.put(vallader, valladerTokens);
        allTokens.put(sutsettisch, sutsettischTokens);
        allTokens.put(bivio, bivioTokens);
        allTokens.put(bergagliot, bergagliotTokens);
        allTokens.put(val_mustair, valmustairTokens);
        allTokens.put(buehlers_koine, buehlerskoineTokens);
        allTokens.put(jauer, jauerTokens);
        allTokens.put(deutsch, deutschTokens);
        allTokens.put(italiano, italianoTokens);
        allTokens.put(auters, autersTokens);
        allTokens.put(latein, lateinTokens);


        return allTokens;
    }


    public List getAllTokensInChrestomathie(DBCollection collection) {

        List<String> chrestTokens = new ArrayList<>();

        DBCursor cursor = collection.find();

        for (DBObject o : cursor) {

            BasicDBObject annotations = (BasicDBObject) o.get("annotations");
            BasicDBList forms = (BasicDBList) annotations.get("FORM");
            //ArrayList<BasicDBObject> formsArray = (ArrayList) forms;
            BasicDBObject lastForm = (BasicDBObject) forms.get(forms.size() - 1);
            String lf = lastForm.getString("form");

            chrestTokens.add(lf);

        }

        return chrestTokens;
    }


    public Map<String, Set<String>> getTypes(Map<String, List<String>> tokens) {
        Map<String, Set<String>> allTypes = new HashMap<>();


        for (Map.Entry<String, List<String>> entry : tokens.entrySet()) {

            String s = entry.getKey();
            List<String> list = entry.getValue();

            switch (s) {

                case sursilvan:
                    Set<String> sursilvanTokens = new TreeSet<>(list);
                    allTypes.put(s, sursilvanTokens);
                    break;
                case sutsilvan:
                    Set<String> sutsilvanTokens = new TreeSet<>(list);
                    allTypes.put(s, sutsilvanTokens);
                    break;
                case surmiran:
                    Set<String> surmiranTokens = new TreeSet<>(list);
                    allTypes.put(s, surmiranTokens);
                    break;
                case puter:
                    Set<String> puterTokens = new TreeSet<>(list);
                    allTypes.put(s, puterTokens);
                    break;
                case vallader:
                    Set<String> valladerTokens = new TreeSet<>(list);
                    allTypes.put(s, valladerTokens);
                    break;
                case sutsettisch:
                    Set<String> sutsettischTokens = new TreeSet<>(list);
                    allTypes.put(s, sutsettischTokens);
                    break;
                case bivio:
                    Set<String> bivioTokens = new TreeSet<>(list);
                    allTypes.put(s, bivioTokens);
                    break;
                case bergagliot:
                    Set<String> bergagliotTokens = new TreeSet<>(list);
                    allTypes.put(s, bergagliotTokens);
                    break;
                case val_mustair:
                    Set<String> valmustairTokens = new TreeSet<>(list);
                    allTypes.put(s, valmustairTokens);
                    break;
                case buehlers_koine:
                    Set<String> buehlerskoineTokens = new TreeSet<>(list);
                    allTypes.put(s, buehlerskoineTokens);
                    break;
                case jauer:
                    Set<String> jauerTokens = new TreeSet<>(list);
                    allTypes.put(s, jauerTokens);
                    break;
                case deutsch:
                    Set<String> deutschTokens = new TreeSet<>(list);
                    allTypes.put(s, deutschTokens);
                    break;
                case italiano:
                    Set<String> italianoTokens = new TreeSet<>(list);
                    allTypes.put(s, italianoTokens);
                    break;
                case auters:
                    Set<String> autersTokens = new TreeSet<>(list);
                    allTypes.put(s, autersTokens);
                    break;
                case latein:
                    Set<String> lateinTokens = new TreeSet<>(list);
                    allTypes.put(s, lateinTokens);
                    break;
                default:
                    break;

            }


        }


        return allTypes;

    }


    public Set<String> getCombinedQuantity(Set<String> a, Set<String> b) {
        Set<String> cq = new TreeSet<>();
        cq.addAll(a);
        cq.addAll(b);
        return cq;
    }


    public Set<String> getIntersection(Set<String> a, Set<String> b) {

        Set<String> cq = new TreeSet<>();
        cq.addAll(a);
        cq.retainAll(b);


        return cq;
    }


    public Map<String, Set<String>> getIntersections(Map<String, Set<String>> types) {

        Map<String, Set<String>> intersections = new HashMap<>();

        for (Map.Entry<String, Set<String>> entry : types.entrySet()) {

            String lang = entry.getKey();
            Set<String> tps = entry.getValue();


            for (Map.Entry<String, Set<String>> entry_2 : types.entrySet()) {

                String lang_2 = entry_2.getKey();

                Set<String> tps_2 = entry_2.getValue();

                if (!lang.equals(lang_2)) {

                    StringBuffer buffer = new StringBuffer();
                    buffer.append(lang);
                    buffer.append("_");
                    buffer.append(lang_2);

                    Set<String> inter = getIntersection(tps, tps_2);

                    intersections.put(buffer.toString(), inter);
                }


            }


        }

        return intersections;
    }


    public Map<String, Set<String>> getCombinedQuantities(Map<String, Set<String>> types) {

        Map<String, Set<String>> cqs = new HashMap<>();

        for (Map.Entry<String, Set<String>> entry : types.entrySet()) {

            String lang = entry.getKey();
            Set<String> tps = entry.getValue();


            for (Map.Entry<String, Set<String>> entry_2 : types.entrySet()) {

                String lang_2 = entry_2.getKey();

                Set<String> tps_2 = entry_2.getValue();

                if (lang_2.equals(jauer)) {

                    continue;
                }

                if (!lang.equals(lang_2)) {

                    StringBuffer buffer = new StringBuffer();
                    buffer.append(lang);
                    buffer.append("_");
                    buffer.append(lang_2);

                    Set<String> cq = getCombinedQuantity(tps, tps_2);

                    cqs.put(buffer.toString(), cq);
                }


            }


        }

        return cqs;
    }


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

    public List<ForStand> getTokensForStand(String fileName) throws Exception {
        List<ForStand> list = new ArrayList<>();
        Map<Long, Integer> map = new HashMap<>();

        List<Entry> getListOfTokens = readEntries(fileName);
        int i = 0;

        for (Entry e : getListOfTokens) {

            List<Punct> p_list = new ArrayList<>();

            String form = e.getForm();
            StringBuffer buffer = new StringBuffer();

            for (int j = 0; j < form.length(); j++) {

                if (!Character.isLetterOrDigit(form.charAt(j))
                        && !String.valueOf(form.charAt(j)).equals(" ")
                        && !String.valueOf(form.charAt(j)).equals("'")) {

                    Punct p = new Punct();
                    p.setForm(String.valueOf(form.charAt(j)));
                    p.setIndex(j);
                    p_list.add(p);

                } else {

                    buffer.append(form.charAt(j));

                }

            }

            if (p_list.size() == 0) {
                ForStand entry = new ForStand();
                entry.setIndex(i);
                entry.setForm(buffer.toString());
                entry.setPOS(e.getPos());
                list.add(entry);
                i++;
                map.put(e.getIndex(), i);

            } else {

                if (p_list.size() == 1 && buffer.length() == 0) {
                    ForStand p = new ForStand();
                    p.setIndex(i);
                    p.setForm(form);
                    p.setPOS(getPOS(p_list.get(0).getForm()));
                    list.add(p);
                    i++;
                    continue;

                }

                int firstChar = form.indexOf(buffer.charAt(0));
                int lastChar = form
                        .lastIndexOf(buffer.charAt(buffer.length() - 1));

                for (int j = 0; j < p_list.size(); j++) {

                    if (p_list.get(j).getIndex() < firstChar) {
                        ForStand p = new ForStand();
                        p.setIndex(i);
                        p.setForm(p_list.get(j).getForm());
                        p.setPOS(getPOS(p_list.get(j).getForm()));
                        list.add(p);
                        i++;

                    }

                }

                ForStand entry = new ForStand();
                entry.setIndex(i);
                entry.setForm(buffer.toString());
                entry.setPOS(e.getPos());
                list.add(entry);
                i++;
                map.put(e.getIndex(), i);

                for (int j = 0; j < p_list.size(); j++) {

                    if (p_list.get(j).getIndex() > lastChar) {
                        ForStand p = new ForStand();
                        p.setIndex(i);
                        p.setForm(p_list.get(j).getForm());
                        p.setPOS(getPOS(p_list.get(j).getForm()));
                        list.add(p);
                        i++;

                    }

                }

            }

        }
        FileUtils.writeMap(map, "index_mapping_");
        return list;

    }

    private String getPOS(String s) {
        String pos = null;

        switch (s) {

            case "!":
            case "?":
            case ".":
                pos = "P_EOS";
                break;

            case ",":
            case ";":
            case ":":
            case "“":
            case "„":

                pos = "P_OTH";
                break;

            case "…":
            case "-":
                pos = "NULL";
                break;

        }

        return pos;

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

    public List<WordImpl> sursilvanTokensInRange(
            List<LanguageRange> languageRange) {

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

        File file = new File(FileUtils.outputPath + "model"
                + FileUtils.getISO8601StringForCurrentDate() + ".txt");
        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), "UTF8"));

        Map<Integer, String> treeMap = new TreeMap<>(goldenMap);

        for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {

            Integer index = entry.getKey();
            String form = entry.getValue();

            if (form.equals("!") || form.equals(".") || form.equals("?")
                    || form.equals(",")) {
                out.append(form);
                out.append("_");
                out.append(form);
                out.append("\t");
            }

        }

        out.close();
    }

    private void printNotTagged(WordImpl word) {

        StringBuffer buffer = new StringBuffer();

        if (word.getLastPosAnnotation() != null) {

            if (word.getLastPosAnnotation().getPos().toString().equals("NULL")
                    || word.getLastPosAnnotation().getPos().toString()
                    .equals("NOT_TAGGED")) {

                // if
                // (word.getLastPosAnnotation().getUserId().toString().equals("lutzf")
                // ||word.getLastPosAnnotation().getUserId().toString().equals("rivald"))
                // {

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

    // Temporary solution in order top avoid mutual dependence in maven
    private static List<Entry> readEntries(String fileName) throws Exception {

        ObjectInputStream inputStream = new ObjectInputStream(
                new FileInputStream(FileUtils.outputPath + fileName));

        List<Entry> tokens = (List<Entry>) inputStream.readObject();

        inputStream.close();

        return tokens;

    }


    public static List<String> readAllTokens(String fileName) throws Exception {

        ObjectInputStream inputStream = new ObjectInputStream(
                new FileInputStream(FileUtils.outputPath + fileName));

        List<String> tokens = (List<String>) inputStream.readObject();

        inputStream.close();

        return tokens;

    }


    // Temporary solution in order top avoid mutual dependence in maven
    public static Map<String, List<MongoWord>> readMap(String fileName) throws Exception {

        ObjectInputStream inputStream = new ObjectInputStream(
                new FileInputStream(FileUtils.outputPath + fileName));

        Map<String, List<MongoWord>> tokens = (Map<String, List<MongoWord>>) inputStream.readObject();

        inputStream.close();

        return tokens;

    }


    // Temporary solution in order top avoid mutual dependence in maven
    public static Map<String, List<String>> readTokens(String fileName) throws Exception {

        ObjectInputStream inputStream = new ObjectInputStream(
                new FileInputStream(FileUtils.outputPath + fileName));

        Map<String, List<String>> tokens = (Map<String, List<String>>) inputStream.readObject();

        inputStream.close();

        return tokens;

    }

}