package de.spinfo.arc.oldmodel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import de.uni_koeln.spinfo.arc.utils.FileUtils;

/**
 * Created by franciscomondaca on 7/10/15.
 */
public class IOOldModel {

    private static MongoClient mongoClient;
    private static DBCollection collection;
    private static DB db;


    @BeforeClass
    public static void init() throws Exception {

        mongoClient = new MongoClient("localhost", 27017);
        db = mongoClient.getDB("arc_20141117");
        collection = db.getCollection("words");

    }


    @Test
    public void getAtIds() throws IOException {

        //BasicDBObject query = new BasicDBObject("annotations.FORM.form", "@");
        DBCursor cursor = collection.find();
        System.out.println(cursor.size());

        ArrayList<String> formsToReturn = new ArrayList<String>();

        for (DBObject o : cursor) {

            BasicDBObject object = (BasicDBObject) o.get("annotations");
            BasicDBList forms = (BasicDBList) object.get("FORM");
            BasicDBObject form = (BasicDBObject) forms.get(0);
            String formAsS = form.getString("form");

            if (!formAsS.equals("@")) {
                formsToReturn.add(formAsS);
            }
        }


        FileUtils.writeList(formsToReturn, "arc_20141117_spielwiese");


    }


    @Test
    public void compareLists() throws Exception {

        List<String> arc_20141114_spielwiese = readList("arc_20141114_spielwiese");
        List<String> arc_20141117_spielwiese = readList("arc_20141117_spielwiese");

        List<String> diff = new ArrayList<>();

        System.out.println(arc_20141114_spielwiese.size());
        System.out.println(arc_20141117_spielwiese.size());

//        for (int i = 0; i < arc_20141114_spielwiese.size(); i++) {
//
//            String s1 = arc_20141114_spielwiese.get(i);
//            String s2 = arc_20141117_spielwiese.get(i);
//            if (!s1.equals(s2)) {
//                diff.add(i + "\t" + s1 + "\t" + s2);
//
//            }
//
//        }
//
//        FileUtils.printList(diff, FileUtils.outputPath, "diffsAts");


    }

    private static List<String> readList(String fileName) throws Exception {

        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FileUtils.outputPath + fileName));

        List<String> tokens = (List<String>) inputStream.readObject();

        inputStream.close();

        return tokens;


    }

}
