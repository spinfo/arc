import de.uni_koeln.spinfo.arc.ext.puter.proc.antlr4.ProcessPuter;
import de.uni_koeln.spinfo.arc.utils.DictUtils;
import de.uni_koeln.spinfo.arc.utils.FileUtils;
import org.junit.BeforeClass;
import org.junit.Ignore;
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

        processor.parsingWorkflow(processor.output_data_path + "puter_splittedLines.txt", "Puter4");


    }

    @Test
    public void parseIndendedLines() throws ParseException, IOException {

        processor.parsingWorkflow(processor.output_data_path + "Puter4dumpedLines.txt", "Puter4dumped");

        List<String> dumpedEntries = FileUtils.fileToList(processor.output_data_path + "Puter4dumped_finalResult.txt");
        dumpedEntries = DictUtils.removeCapLetterEntries(dumpedEntries);

        DictUtils.printList(dumpedEntries,processor.output_data_path, "Puter4_dumpedEntries");


    }

    @Test
    public void extractLemmas_Test() throws IOException {
        DictUtils.separateLemmasShorterThan(processor.output_data_path + "Puter4_finalResult_extended.txt", processor.output_data_path, 4);

    }

    @Test
    public void findGermanLemmas_Test() throws IOException {
        DictUtils.findGermanLemmas(processor.input_data_path + "german.txt", processor.output_data_path + "final/Puter4_finalResult_cleanedShortLemmas.txt", processor.output_data_path);
    }

    @Test
    public void removeGermanLemmas_Test() throws IOException {
        DictUtils.removeGermanLemmas(processor.output_data_path+"removedGermanWords_manuell.txt", processor.output_data_path+"final/Puter4_finalResult_cleanedShortLemmas.txt", processor.output_data_path);
    }



    @Ignore
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

    @Test
    public void splitTwoColumnLines_Test() throws IOException {

        DictUtils.splitTwoColumnLines(ProcessPuter.output_data_path+"puter_normalized.txt", ProcessPuter.output_data_path,"puter_splittedLines");
    }



}
