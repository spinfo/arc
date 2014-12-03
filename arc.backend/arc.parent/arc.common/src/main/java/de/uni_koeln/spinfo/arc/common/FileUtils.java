package de.uni_koeln.spinfo.arc.common;

import de.spinfo.arc.annotationmodel.annotatable.impl.WordImpl;
import de.uni_koeln.spinfo.arc.tagger.Token;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by franciscomondaca on 3/12/14.
 */
public class FileUtils {


    private static String outputPath = "../../arc.data/output/";
    private static String inputPath = "../../arc.data/input/";

    public static <T> void writeList(List<T> matchedWords, String fileName) throws IOException {

        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(outputPath + fileName + DictUtils.getISO8601StringForCurrentDate()));

        outputStream.writeObject(matchedWords);

        outputStream.close();

    }


    public static List<Token> getListOfTokens(String fileName) throws Exception {

        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(outputPath+fileName));

        List<Token> tokens = (List<Token>) inputStream.readObject();

        inputStream.close();

        return tokens;


    }




}
