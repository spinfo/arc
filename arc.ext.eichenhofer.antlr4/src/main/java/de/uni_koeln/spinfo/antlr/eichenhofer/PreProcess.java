package de.uni_koeln.spinfo.antlr.eichenhofer;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by franciscomondaca on 6/11/14.
 */
public class PreProcess {


    public static List<String> addTags(String filePath) throws IOException {

        List<String> list = new ArrayList<String>();

        FileInputStream fis = new FileInputStream(filePath);
        InputStreamReader isr = new InputStreamReader(fis, "UTF8");
        LineNumberReader reader = new LineNumberReader(isr);


        String currentLine;

        while ((currentLine = reader.readLine()) != null) {

            if (!currentLine.startsWith(" ") && currentLine.contains(" ")  && currentLine.length() > 4) {
                StringBuilder builder = new StringBuilder();
                builder.append("<E>");
                builder.append(currentLine);
                builder.append("</E>");

                list.add(builder.toString());
            }


        }


        return list;

    }


}
