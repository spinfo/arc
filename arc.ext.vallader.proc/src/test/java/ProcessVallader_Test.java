import de.uni_koeln.spinfo.arc.ext.vallader.proc.antlr4.ProcessVallader;
import de.uni_koeln.spinfo.arc.ext.vallader.proc.pdftextstream.PdfXStreamExtractor;
import de.uni_koeln.spinfo.arc.utils.DictUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Andreas on 21.04.2015.
 */
public class ProcessVallader_Test {

    static ProcessVallader processor;

    @BeforeClass
    public static void initialize() {

        processor = new ProcessVallader();

    }

    @Ignore
    @Test
    public void valladerTXTtoList_Test() throws IOException {
        DictUtils.printList(processor.valladerTXTtoList(ProcessVallader.input_data_path+"tscharner-20140715_20140923_20150319.txt"), ProcessVallader.output_data_path, "ValladerTXTtoList-Test.txt");
    }
    @Ignore
    @Test
    public void cleanedValladerTXTtoList_dummyData_Test() throws IOException {
        List<String> entries = processor.cleanedValladerTXTtoList(ProcessVallader.input_data_path + "testinput.txt");
        DictUtils.printList(entries, ProcessVallader.output_data_path,"cleanedDummyEntries");
        Assert.assertTrue("Es sollten eigentlich 6 akzeptable Einträge gefunden werden", entries.size() == 6);
    }
    @Ignore
    @Test
    public void cleanValladerTXTtoList_realData_Test() throws IOException {
        List<String> entries = processor.cleanedValladerTXTtoList(ProcessVallader.input_data_path + "20150421_vallader.txt");
        DictUtils.printList(entries, ProcessVallader.output_data_path,"cleanedrealEntries");
        Assert.assertNotNull("In dieser Liste sollten hauptsächlich die ersten Zeilen von Wörterbucheinträgen stehen", entries);
    }
    @Ignore
    @Test
    public void pdfXStreamExtractor_Test() throws IOException {
        PdfXStreamExtractor pdfEx = new PdfXStreamExtractor();
        pdfEx.extractWithPDFTextStream("pdfTextStreamExtraction2");
    }

    @Test
    public void parseVallader_Test() throws IOException {
        processor.parseVallader(ProcessVallader.output_data_path + "cleanedrealEntries.txt", "parsedVallader", ProcessVallader.output_data_path);
    }
}
