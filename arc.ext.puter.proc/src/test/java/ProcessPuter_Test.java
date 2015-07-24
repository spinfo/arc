import de.uni_koeln.spinfo.arc.ext.puter.proc.antlr4.ProcessPuter;
import de.uni_koeln.spinfo.arc.utils.DictUtils;
import de.uni_koeln.spinfo.arc.utils.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Andreas on 21.04.2015.
 */
public class ProcessPuter_Test {

    static ProcessPuter processor;

    @BeforeClass
    public static void initialize() {

        processor = new ProcessPuter();

    }


    @Test
    public void testExtractWF() throws ParseException, IOException {

        processor.extractionWorkflow("Puter2");


    }

    @Test
    public void testParsingWF() throws ParseException, IOException {

        processor.parsingWorkflow(processor.output_data_path + "puter_normalized.txt", "Puter1");


    }




    @Test
    public void normalize() throws IOException {

        ArrayList<String> myList = new ArrayList<>();

        ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(processor.output_data_path+"PuterPdfExtraction.txt"), StandardCharsets.UTF_8);

        for (String s : lines) {

            s = Normalizer.normalize(s, Normalizer.Form.NFC);
            myList.add(s);
        }

        DictUtils.printList(myList, processor.output_data_path, "puter_normalized");


    }

}
