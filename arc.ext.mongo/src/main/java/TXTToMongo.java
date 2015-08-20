import com.mongodb.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by franciscomondaca on 13/7/15.
 */
public class TXTToMongo {


    public DBCollection txtToMongo(BufferedReader br, DBCollection collection, String dictToSave)
            throws IOException {

        String line;

        while ((line = br.readLine()) != null) {
            if (!line.startsWith("#")) {
                String[] nvs_line = line.split("\\$");
                DBObject mongoEntry;

                switch (dictToSave) {

                    case "puter":
                        mongoEntry = puterEntryToMongoObject(nvs_line);
                        collection.insert(mongoEntry);
                        break;

                    case "surmiran":
                        mongoEntry = surmiranEntryToMongoObject(nvs_line);
                        collection.insert(mongoEntry);
                        break;

                    case "sursilvan":
                        mongoEntry = sursilvanEntryToMongoObject(nvs_line);
                        collection.insert(mongoEntry);
                        break;

                    case "sutsilvan":
                        mongoEntry = sutsilvanEntryToMongoObject(nvs_line);
                        collection.insert(mongoEntry);
                        break;

                    case "vallader":
                        mongoEntry = valladerEntryToMongoObject(nvs_line);
                        collection.insert(mongoEntry);
                        break;


                }


            }
        }

        return collection;
    }


    public Map<String, Set<String>> getLemmas(DBCollection nvs) {

        Map<String, Set<String>> lemmas = new TreeMap<>();

        DBCursor lemmasFromMongo = nvs.find();

        for (DBObject o : lemmasFromMongo) {

            String lemma = (String) o.get("entry");
            BasicDBObject pos = (BasicDBObject) o.get("pos");
            String required_pos = (String) pos.get("eagles_pos");

            Set<String> pos_set;

            if (lemmas.get(lemma) == null) {
                pos_set = new CopyOnWriteArraySet<String>();
                //pos_set = new HashSet<>();
                pos_set.add(required_pos);
                lemmas.put(lemma, pos_set);

            } else {
                pos_set = lemmas.get(lemma);
                pos_set.add(required_pos);
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


    public void writeNewEntry(DBCollection nvs, String lemma, String eagles_pos) {

        BasicDBObject entry = new BasicDBObject();
        BasicDBObject pos = new BasicDBObject();

        entry.put("entry", lemma);

        pos.put("eagles_pos", eagles_pos);
        pos.put("nvs_pos", "KPS");


        entry.put("pos", pos);


        nvs.insert(entry);

    }


    public List<String> getLemmasOnlyWithKPS(DBCollection nvs) {

        BasicDBObject pos = new BasicDBObject();

        pos.put("pos.eagles_pos", "KPS");


        List<String> lemmas = new ArrayList<>();
        List<String> lemmasOneKPS = new ArrayList<>();

        //Get all the entries with KPS
        DBCursor cursor = nvs.find(pos);

        for (DBObject o : cursor) {

            String en = (String) o.get("entry");
            lemmas.add(en);

        }


        //Get only entries with one KPS
        for (String s : lemmas) {

            BasicDBObject o = new BasicDBObject("entry", s);

            DBCursor c = nvs.find(o);

            if (c.size() == 1) {

                lemmasOneKPS.add(s);
            }


        }


        return lemmasOneKPS;

    }


    private DBObject puterEntryToMongoObject(String[] nvs_line) {

        BasicDBObject entry = new BasicDBObject();

        BasicDBObject pos = new BasicDBObject();

        String lemma = nvs_line[0];

        lemma = lemma.replace("*", "");

        entry.put("entry", lemma);

        String nvs_pos = nvs_line[1];

        System.out.println(nvs_line[0] + ", " + nvs_line[1]);

        String eagles_pos = null;

        switch (nvs_pos) {

            // ADJ
            case "adj":
            case "adj invar":
            case "adj/adv":
                eagles_pos = "ADJ";
                break;
            case "adj invar/num":
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

            // NN
            case "m":
            case "f":
            case "fcoll":
            case "fpl":
            case "coll":
            case "mpl":
            case "m f":
            case "mf":
            case "/":
            case "m,f":
            case "p sg":
                eagles_pos = "NN";
                break;

            // PREP
            case "prep":
                eagles_pos = "PREP";
                break;

            // CARDINAL_NUMBERS
            case "invar/num":
                eagles_pos = "C_NUM";
                break;

//            // PRON
//            case "pron":
//                eagles_pos = "PRON";
//                break;
            case "pron pers":
                eagles_pos = "PRON_PER";
                break;
            case "pron pers/refl":
                eagles_pos = "PRON_REF";
                break;

            case "pron indef.":
                eagles_pos = "PRON_IES";
                break;


            // V_GVRB
            case "tr":
            case "tr ind":
            case "intr":
            case "mtr":
            case "intr/tr":
            case "intr(tr)":
                eagles_pos = "V_GVRB";
                break;
            default:
                break;
        }

        // Add nvs_pos info
        pos.put("puter_pos", nvs_line[1]);
        // Add eagles_pos info
        if (eagles_pos != null) {
            pos.put("eagles_pos", eagles_pos);
        }

        entry.put("pos", pos);

        return entry;
    }


    private DBObject surmiranEntryToMongoObject(String[] nvs_line) {

        BasicDBObject entry = new BasicDBObject();

        BasicDBObject pos = new BasicDBObject();

        String lemma = nvs_line[0];

        lemma = lemma.replace("*", "");

        entry.put("entry", lemma);

        String nvs_pos = nvs_line[1];

        System.out.println(nvs_line[0] + ", " + nvs_line[1]);

        String eagles_pos = null;

        switch (nvs_pos) {

            // ADJ
            case "adj":
            case "adi":
            case "adj/adverb":
            case "adj/pronom":
                eagles_pos = "ADJ";
                break;
            case "adj/numeral":
            case "adj numeral":
                eagles_pos = "ADJ_NUM";
                break;
            case "adj indefinit":
            case "adj/pronom indefinit":
                eagles_pos = "ADJ_IND";
                break;
            case "adj interrogativ":
            case "adj/pronom interrogativ":
                eagles_pos = "ADJ_IES";
                break;
            case "adj demonstrativ":
                eagles_pos = "ADJ_DEM";
                break;
            case "adj possessiv":
                eagles_pos = "ADJ_POS";
                break;


            // ADV
            case "adv":
            case "adverb":
            case "adverb/conjunczium":
            case "adverb/prep":
            case "adverb pronom":
            case "adverb interrogativ":
            case "adverb/pronom indefinit":
            case "adv relativ":
            case "adv interrog":
                eagles_pos = "ADV";
                break;
            case "adv pron":
                eagles_pos = "ADV_PRON";
                break;

            // INT
            case "interjecziun":
                eagles_pos = "INT";
                break;

            // NN
            case "m":
            case "f":
            case "m ,f":
            case "m,f":
            case "m,f.pl":
            case "m,n":
            case "m.pl":
            case "n":
            case "n,m":
            case "f.pl":
            case "fcoll":
            case "pl":
                eagles_pos = "NN";
                break;

            // PREP
            case "prep":
            case "prep/conjuncziun":
            case "prep/adverb":
                eagles_pos = "PREP";
                break;
            case "prep+artetgel":
                eagles_pos = "PREP_ART";
                break;

          // CARDINAL_NUMBERS
            case "num":
            case "num ord":
            case "numeral":
            case "numeral/pronom":
                eagles_pos = "C_NUM";
                break;

            // PRON
            case "pronom persuna / persunal":
            case "pronom ":
            case "pronom persuna / persunal 3a pl cumplemaint indirect":
            case "pronom persuna / persunal indefinit":
            case "pronom persuna / persunal pl ":
                eagles_pos = "PRON_PER";
                break;
            case "pronom reflexiv":
                eagles_pos = "PRON_REF";
                break;
            case "pronom interrogativ":
                eagles_pos = "PRON_IES";
                break;

            case "pron indefinit":
                eagles_pos = "PRON_IND";
                break;
            case "pronom possessiv":
                eagles_pos = "PRON_POS";
                break;
            case "pronom rel":
                eagles_pos = "PRON_REL";
                break;
            case "pronom demonstrativ":
            case "pronom demonstrativ impersunal":
            case "pronom demonstrativ neutr":
                eagles_pos = "PRON_DEM";
                break;


            // V_GVRB
            case "tr":
            case "tr indirect":
            case "tr indirect/int":
            case "tr/impersunal":
            case "tr/tr indirect":
            case "tr/modal":
            case "int":
            case "int/impersunal":
            case "int/tr":
            case "int/tr indirect":
            case "refl":
            case "reflexiv":
                eagles_pos = "V_GVRB";
                break;
            default:
                break;
        }

        // Add nvs_pos info
        pos.put("puter_pos", nvs_line[1]);
        // Add eagles_pos info
        if (eagles_pos != null) {
            pos.put("eagles_pos", eagles_pos);
        }

        entry.put("pos", pos);

        return entry;
    }


    public DBObject sursilvanEntryToMongoObject(String[] nvs_line) {

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

//            // PRON
//            case "pron":
//                eagles_pos = "PRON";
//                break;
            case "pron.pers.":
            case "pron.pers":
            case "pron.pers.obj":
                eagles_pos = "PRON_PER";
                break;
//            case "pron.impers.":
//            case "pron.impers":
//                eagles_pos = "PRON";
//                break;
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
//            case "pron.ord.":
//            case "pron.ord":
//                eagles_pos = "PRON";
//                break;
            case "pron.refl.":
            case "pron.refl":
                eagles_pos = "PRON_REFL";
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

    private DBObject sutsilvanEntryToMongoObject(String[] nvs_line) {

        BasicDBObject entry = new BasicDBObject();

        BasicDBObject pos = new BasicDBObject();

        String lemma = nvs_line[0];

        lemma = lemma.replace("*", "");

        entry.put("entry", lemma);

        String nvs_pos = nvs_line[1];

        System.out.println(nvs_line[0] + ", " + nvs_line[1]);

        String eagles_pos = null;

        switch (nvs_pos) {

            // ADJ
            case "adj":
            case "adj invar":
            case "adj adv":
            case "adj prep":
            case "adj pron":
                eagles_pos = "ADJ";
                break;
            case "adj invar/num":
                eagles_pos = "ADJ_NUM";
                break;
            case "adj indef":
                eagles_pos = "ADJ_IND";
                break;
            case "adj interrog":
                eagles_pos = "ADJ_IES";
                break;

            // ADV
            case "adv":
            case "adv interrog":
                eagles_pos = "ADV";
                break;
            case "adv pron":
                eagles_pos = "ADV_PRON";
                break;
            // INT
            case "interj":
                eagles_pos = "INT";
                break;

            // NN
            case "m":
            case "f":
            case "f m":
            case "m(f)":
            case "m,f":
            case "n":
            case "pl":
                eagles_pos = "NN";
                break;

            // PREP
            case "prep":
            case "prep adj":
            case "prep adv":
                eagles_pos = "PREP";
                break;

          /*  // CARDINAL_NUMBERS
            case "invar/num":
                eagles_pos = "C_NUM";
                break;
*/
//            // PRON
            case "pron pers":
            case "pron ":
            case "pron pl":
            case "pron pl pl":
                eagles_pos = "PRON_PER";
                break;
            case "pron pers/refl":
                eagles_pos = "PRON_REF";
                break;
            case "pron interrog":
            case "pron interrog pl":
                eagles_pos = "PRON_IES";
                break;

            case "pron indef":
                eagles_pos = "PRON_IND";
                break;
            case "pron poss":
            case "pron poss pl":
            case "pron poss pl pl":
                eagles_pos = "PRON_POS";
                break;
            case "pron refl":
                eagles_pos = "PRON_REF";
                break;


            // V_GVRB
            case "tr":
            case "tr adv":
            case "tr prep":
            case "tr/int":
            case "int":
            case "int prep":
            case "refl":
                eagles_pos = "V_GVRB";
                break;
            default:
                break;
        }

        // Add nvs_pos info
        pos.put("puter_pos", nvs_line[1]);
        // Add eagles_pos info
        if (eagles_pos != null) {
            pos.put("eagles_pos", eagles_pos);
        }

        entry.put("pos", pos);

        return entry;
    }


    public DBObject valladerEntryToMongoObject(String[] nvs_line) {

        BasicDBObject entry = new BasicDBObject();

        BasicDBObject pos = new BasicDBObject();

        String lemma = nvs_line[0];

        lemma = lemma.replace("*", "");

        entry.put("entry", lemma);

        String nvs_pos = nvs_line[1];

        System.out.println(nvs_line[0] + ", " + nvs_line[1]);

        String eagles_pos = null;

        switch (nvs_pos) {

            // ADJ
            case "adj":
            case "adj invar":
                eagles_pos = "ADJ";
                break;
            case "adj invar/num":
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

            // NN
            case "m":
            case "f":
            case "fcoll":
            case "fpl":
            case "coll":
            case "mpl":
            case "m f":
            case "mf":
            case "/":
                eagles_pos = "NN";
                break;

            // PREP
            case "prep":
                eagles_pos = "PREP";
                break;

            // CARDINAL_NUMBERS
            case "invar/num":
                eagles_pos = "C_NUM";
                break;

//            // PRON
//            case "pron":
//                eagles_pos = "PRON";
//                break;
            case "pron pers":
                eagles_pos = "PRON_PER";
                break;
            case "pron pers/refl":
                eagles_pos = "PRON_REF";
                break;

            case "pron indef.":
                eagles_pos = "PRON_IES";
                break;


            // V_GVRB
            case "tr":
            case "tr ind":
            case "intr":
            case "mtr":
            case "intr/tr":
            case "intr(tr)":
            case "n/a":
                eagles_pos = "V_GVRB";
                break;
            default:
                break;
        }

        // Add nvs_pos info
        pos.put("vallader_pos", nvs_line[1]);
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
