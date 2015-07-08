package de.uni_koeln.spinfo.arc.ext.vallader.proc.mongo;

import com.mongodb.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by Andreas on 08.07.2015.
 */
public class MongoIO {



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

    public DBCollection txtToMongo(BufferedReader br, DBCollection collection)
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

            // PRON
            case "pron":
                eagles_pos = "PRON";
                break;
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
        pos.put("nvs_pos", nvs_line[1]);
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
