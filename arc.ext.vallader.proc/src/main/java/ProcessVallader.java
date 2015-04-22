import de.uni_koeln.spinfo.arc.utils.DictUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Andreas on 21.04.2015.
 */
public class ProcessVallader {

    public static String output_data_path = "../arc.data/output/";
    public static String input_data_path = "../arc.data/input/";

    public List<String> valladerTXTtoList (String filePath) throws IOException {

        List<String> entryList = new ArrayList<String>();
        entryList = DictUtils.addTags(filePath);

        return entryList;

    }

    public List<String> cleanedValladerTXTtoList (String filePath) throws IOException {

        List<String> entryList = new ArrayList<String>();

        FileInputStream fis = new FileInputStream(filePath);
        InputStreamReader isr = new InputStreamReader(fis, "UTF8");
        LineNumberReader reader = new LineNumberReader(isr);

        String currentLine;

        // Pattern for lines starting with 3 or more blanks
        Pattern pattern = Pattern.compile("^  [ ]+");

        while ((currentLine = reader.readLine()) != null) {

            // skip empty lines
            if (currentLine.equals("")) {
                currentLine = reader.readLine();
            }

            // Create matcher object
            Matcher matcher = pattern.matcher(currentLine);

            // Check if the matcher's prefix match with the matcher's pattern
            if (!matcher.lookingAt()) {
                // add line to list
                entryList.add(addTags(currentLine));
            }
        }

        reader.close();
        return entryList;
    }

    private String addTags(String string) {
        StringBuilder builder = new StringBuilder();
        builder.append("<E>");
        builder.append(string);
        builder.append("</E>");
        return builder.toString();
    }



}
