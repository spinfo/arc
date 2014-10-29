package de.uni_koeln.spinfo.arc.annotator;

import com.mongodb.*;
import org.bson.types.ObjectId;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

public class ARCMapper {

    public Map<ObjectId, TreeSet<String>> matchPOS(
            Map<ObjectId, String> lastMods,
            Map<String, TreeSet<String>> generatedForms) {

        Map<ObjectId, TreeSet<String>> pos = new HashMap<>();

        // Iterate through the last modified entries
        for (Map.Entry<ObjectId, String> lm : lastMods.entrySet()) {

            ObjectId _id = lm.getKey();
            String lastMod = lm.getValue();

            // Remove punctuation from lastMod
            lastMod = lastMod.replaceAll("[^\\p{L}\\p{Nd}]", "");

            // Iterate through the generated forms
            for (Map.Entry<String, TreeSet<String>> gf : generatedForms
                    .entrySet()) {

                String f = gf.getKey();
                TreeSet<String> gfs = gf.getValue();

                // Compare each generated form with the last modification and if
                // they match, add their respective POS to the map
                if (lastMod.equalsIgnoreCase(f)) {

                    pos.put(_id, gfs);

                }

            }

        }

        return pos;
    }


    public void tagIdiomWithChapterInfo(DBCollection chapter, DBCollection language, Map<Long, Long> intervals, String idiom) {

        DBObject first_chapter = null;
        Long first_word_in_chapter = null;
        Long last_word_in_chapter = null;

        BasicDBObject new_lang_info = null;
        BasicDBList interval_array = null;
        BasicDBObject chap_interval = null;

        DBObject lang_info = language.findOne(new BasicDBObject("obj_info", idiom));
        System.out.println(lang_info);


        for (Map.Entry<Long, Long> interval : intervals.entrySet()) {

            Long key = interval.getKey();
            Long value = interval.getValue();


            //If there is only one volume in the interval
            if (value == 0L) {

                first_chapter = chapter.findOne(new BasicDBObject("obj_index", key));
                first_word_in_chapter = (Long) first_chapter.get("obj_start");
                System.out.println(first_word_in_chapter);
                last_word_in_chapter = (Long) first_chapter.get("obj_end");
                System.out.println(last_word_in_chapter);


                if (lang_info == null) {

                    new_lang_info = new BasicDBObject();
                    new_lang_info.put("obj_info", idiom);

                    interval_array = new BasicDBList();
                    chap_interval = new BasicDBObject();
                    chap_interval.put("obj_start", first_word_in_chapter);
                    chap_interval.put("obj_end", last_word_in_chapter);
                    interval_array.add(interval);

                    new_lang_info.put("words", interval_array);

                    chapter.save(new_lang_info);

                } else {
                    BasicDBList l_list = (BasicDBList) lang_info.get("words");
                    chap_interval = new BasicDBObject();
                    chap_interval.put("obj_start", first_word_in_chapter);
                    chap_interval.put("obj_end", last_word_in_chapter);
                    l_list.add(chap_interval);

                    chapter.save(l_list);

                }


            }


        }


    }


    public void insertPOS(DBCollection collection,
                          Map<ObjectId, TreeSet<String>> posMap) {

        for (Map.Entry<ObjectId, TreeSet<String>> e : posMap.entrySet()) {

            ObjectId _id = e.getKey();
            TreeSet<String> gfpos = e.getValue();

            BasicDBObject id = new BasicDBObject("_id", _id);

            DBCursor cursor = collection.find(id);

            DBObject dbo = cursor.next();

            BasicDBList posinfo = (BasicDBList) dbo.get("pos_info");

            if (posinfo == null) {

                BasicDBList pos_info_list = new BasicDBList();

                BasicDBObject pos_info_obj = new BasicDBObject();

                BasicDBList posList = new BasicDBList();

                for (String s : gfpos) {

                    posList.add(s);

                }

                pos_info_obj.put("pos", posList);
                pos_info_obj.put("score", 0);
                pos_info_obj.put("date", System.currentTimeMillis());
                pos_info_obj.put("autor", "lex");

                pos_info_list.add(pos_info_obj);

                dbo.put("pos_info", pos_info_list);

                collection.save(dbo);

            } else {

                // // to be tested
                // BasicDBObject pos_info_obj = new BasicDBObject();
                // BasicDBList posList = new BasicDBList();
                //
                // for (String s : gfpos) {
                //
                // posList.add(s);
                //
                // }
                //
                // pos_info_obj.put("pos", posList);
                // pos_info_obj.put("score", 0);
                // pos_info_obj.put("date", System.currentTimeMillis());
                // pos_info_obj.put("autor", "lex");
                //
                // collection.update(id, new BasicDBObject("$addToSet",
                // new BasicDBObject("pos_info", pos_info_obj)));

            }

        }

    }

    public void addPOS(DBCollection collection, String idiom,
                       Map<String, TreeSet<String>> generatedForms)
            throws UnknownHostException {

        long start = System.currentTimeMillis();

        DBCursor cursor = collection.find();
        cursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
        while (cursor.hasNext()) {

            DBObject doc = cursor.next();

            // Get a token from the RC
            String arc_token = (String) doc.get("token_info.original");

            // System.out.println(arc_token);

            // Remove punctuation from it
            String cleaned_token = arc_token.replaceAll("[^\\p{L}\\p{Nd}]", "");

            if (cleaned_token == null) {
                continue;
            }

            // Iterate through the generated forms
            for (Entry<String, TreeSet<String>> forms : generatedForms
                    .entrySet()) {
                String vf_entry = forms.getKey();
                TreeSet<String> posSet = forms.getValue();

                BasicDBObject pos = new BasicDBObject();

                // If both, token and generated form, match
                if (cleaned_token.equalsIgnoreCase(vf_entry)) {

                    // String id = doc.get("_id").toString();

                    // Search the POS Tags associated with the generated form
                    for (String s : posSet) {
                        pos.put(idiom, s);

                    }

                    // And add the Tags to RC's token -
                    doc.put("pos", pos);

                    // BasicDBObject updateDocument = new BasicDBObject();
                    // updateDocument.append("$set",
                    // new BasicDBObject().append("pos", pos));
                    //
                    // collection.update(doc, updateDocument, true, false);

                    collection.save(doc);

                }
            }

        }

        long end = System.currentTimeMillis();
        System.err.println("Tagging took: " + (end - start) / 1000
                + " seconds.");

    }

    public void addMetadata(DBCollection metadataCollection,
                            DBCollection arcCollection) {
        long start = System.currentTimeMillis();
        DBCursor arcCursor = arcCollection.find();
        arcCursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
        System.out.println("orig_size: " + arcCursor.size());

        for (int i = 0; i < arcCursor.size(); i++) {

            DBObject doc = arcCursor.next();

            DBObject token_info = (DBObject) doc.get("token_info");

            // Get the page_id from the Entry
            String page_id_fromARC = (String) token_info.get("xml_id");
            System.out.println(page_id_fromARC);

            String token = (String) token_info.get("original");
            System.out.println(token);

            // Get the metadata related to the Entry
            BasicDBObject md = new BasicDBObject();
            md.put("xml_id", page_id_fromARC);

            DBCursor metaCursor = metadataCollection.find(md);

            if (metaCursor != null) {

                BasicDBObject metadata = (BasicDBObject) metaCursor.next();

                BasicDBObject phys_info = (BasicDBObject) metadata
                        .get("phys_info");

                doc.put("phys_info", phys_info);

                arcCollection.save(doc);

            }

        }

        long end = System.currentTimeMillis();
        System.err.println("Adding metadata info took: " + (end - start) / 1000
                + " seconds.");

    }

    public void tagIdiomPerWord(DBCollection arc, String idiom, String vol,
                                Map<Integer, Integer> vol_map) {

        for (Map.Entry<Integer, Integer> interval : vol_map.entrySet()) {

            Integer start = interval.getKey();
            Integer ende = interval.getValue();

            for (Integer i = start; i <= ende; i++) {
                long st = System.currentTimeMillis();
                String s = i + "";

                BasicDBObject oct = new BasicDBObject();

                oct.put("phys_info.oct_vol", vol);
                oct.put("phys_info.oct_page", s);

                DBCursor cursor = arc.find(oct);

                for (DBObject dbo : cursor) {

                    BasicDBList lang_info_array = new BasicDBList();
                    BasicDBObject idiom_entry = new BasicDBObject();

                    idiom_entry.put("idiom", idiom);
                    idiom_entry.put("score", 0);
                    idiom_entry.put("date", System.currentTimeMillis());
                    idiom_entry.put("autor", "f_lutz");

                    lang_info_array.add(idiom_entry);

                    dbo.put("lang_info", lang_info_array);
                    arc.save(dbo);

                }

                long end = System.currentTimeMillis();
                System.err.println("Tagging idiom info  to" + i + " took: "
                        + (end - st) + ".");

            }

        }

    }

    public void tagIdiomAllEntries(DBCollection arc, String idiom,
                                   Map<String, Map<Integer, Integer>> vol_map) {

        for (Map.Entry<String, Map<Integer, Integer>> map : vol_map.entrySet()) {

            String vol = map.getKey();
            Map<Integer, Integer> m = map.getValue();

            tagIdiomPerWord(arc, idiom, vol, m);

        }

    }

    public Map<ObjectId, String> getlastModifications(DBCollection arc,
                                                      String langScope) {
        long start = System.currentTimeMillis();

        DBCursor arcCursor;
        Map<ObjectId, String> oldest_mod = new HashMap<ObjectId, String>();

        if (langScope.equals("all")) {

            arcCursor = arc.find();

        } else {

            // Get all the elements of the specified idiom
            DBObject queryForElem = QueryBuilder.start("lang_info")
                    .elemMatch(new BasicDBObject("idiom", langScope)).get();
            arcCursor = arc.find(queryForElem);
            System.out.println(arcCursor.size());
        }

        arcCursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
        System.out.println("orig_size: " + arcCursor.size());

        for (DBObject o : arcCursor) {

            ObjectId _id = (ObjectId) o.get("_id");

            ArrayList<BasicDBObject> mods = (ArrayList<BasicDBObject>) o
                    .get("modifications");

            // Get the last modification (the last modification is the first
            // element in
            // the array)
            BasicDBObject lastMod = mods.get(0);

            String lastForm = (String) lastMod.get("form");

            oldest_mod.put(_id, lastForm);

        }

        long end = System.currentTimeMillis();
        System.err.println("getlastModifications took: " + (end - start) / 1000
                + " seconds.");

        return oldest_mod;

    }

    public Map<ObjectId, TreeSet<String>> getPOSfromTXT(String pathToTxtFile)
            throws IOException {

        Map<ObjectId, TreeSet<String>> posMatches = new HashMap<>();

        Path file = Paths.get(pathToTxtFile);
        BufferedReader br = Files.newBufferedReader(file,
                StandardCharsets.UTF_8);

        String line = null;

        while ((line = br.readLine()) != null) {

            TreeSet<String> pos_set = new TreeSet<>();

            String[] entry = line.split(" : ");

            ObjectId object_id = new ObjectId(entry[0]);
            String pos = entry[1].replace("[", "").replace("]", "");
            String[] pos_array = pos.split(",");

            for (String s : pos_array) {

                pos_set.add(s);

            }

            posMatches.put(object_id, pos_set);

        }

        return posMatches;

    }

    public Map<ObjectId, String> getEntriesWithoutPOS(DBCollection arc) {
        long start = System.currentTimeMillis();

        Map<ObjectId, String> entriesWithoutPOS = new HashMap<ObjectId, String>();

        DBCursor arcCursor = arc.find();
        arcCursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
        System.out.println("orig_size: " + arcCursor.size());

        for (DBObject o : arcCursor) {

            // If the POS field is empty

            if (o.get("pos_info") == null) {
                ObjectId _id = (ObjectId) o.get("_id");

                ArrayList<BasicDBObject> mods = (ArrayList<BasicDBObject>) o
                        .get("modifications");

                // Get the last modification (the last modification is the first
                // in
                // the array)
                BasicDBObject lastMod = mods.get(0);

                String lastForm = (String) lastMod.get("form");

                entriesWithoutPOS.put(_id, lastForm);
            }

        }

        long end = System.currentTimeMillis();
        System.err.println("getEntriesWithoutPOS took: " + (end - start) / 1000
                + " seconds.");

        return entriesWithoutPOS;

    }

}
