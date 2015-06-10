import de.uni_koeln.spinfo.arc.ext.puter.proc.antlr4.ParsedToLists;
import de.uni_koeln.spinfo.arc.ext.puter.proc.antlr4.ProcessPuter;
import de.uni_koeln.spinfo.arc.ext.vallader.proc.pdftextstream.PdfXStreamExtractor;
import de.uni_koeln.spinfo.arc.utils.DictUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Ignore
    @Test
    public void valladerTXTtoList_Test() throws IOException {
        DictUtils.printList(processor.TXTtoList(ProcessPuter.input_data_path + "tscharner-20140715_20140923_20150319.txt"), ProcessPuter.output_data_path, "ValladerTXTtoList-Test.txt");
    }
   /* @Ignore
    @Test
    public void cleanedValladerTXTtoList_dummyData_Test() throws IOException {
        List<String> entries = processor.cleanedTXTtoList(ProcessPuter.input_data_path + "testinput.txt");
        DictUtils.printList(entries, ProcessPuter.output_data_path,"cleanedDummyEntries");
        Assert.assertTrue("Es sollten eigentlich 6 akzeptable Einträge gefunden werden", entries.size() == 6);
    }
    @Ignore
    @Test
    public void cleanValladerTXTtoList_realData_Test() throws IOException {
        List<String> entries = processor.cleanedTXTtoList(ProcessPuter.input_data_path + "20150421_vallader.txt");
        DictUtils.printList(entries, ProcessPuter.output_data_path,"cleanedrealEntries");
        Assert.assertNotNull("In dieser Liste sollten hauptsächlich die ersten Zeilen von Wörterbucheinträgen stehen", entries);
    }*/
    @Ignore
    @Test
    public void pdfXStreamExtractor_Test() throws IOException {
        PdfXStreamExtractor pdfEx = new PdfXStreamExtractor();
        pdfEx.extractWithPDFTextStream("pdfTextStreamExtraction2");
    }

    @Ignore
    @Test
    public void parseVallader_Test() throws IOException {

        String inputFilePath = ProcessPuter.output_data_path + "cleanedrealEntries.txt";

        processor.parsePuter(inputFilePath, "parsedValladerOLD", ProcessPuter.output_data_path);

        statistics(inputFilePath);

    }

    @Ignore
    @Test
    public void parseValladerListReturn_Test() throws IOException {

        System.out.println("\n\n__________________\n Einfaches Parsen");

        String inputFilePath = ProcessPuter.output_data_path + "cleanedrealEntries.txt";
        List<String> complexEntries = new ArrayList<String>();

        ParsedToLists parsedToLists = processor.parsePuterListReturn(inputFilePath, "parsedVallader20150506", ProcessPuter.output_data_path);

        statistics(inputFilePath);

        for (String entry : parsedToLists.getEntries()) {
            if (entry.contains("-")) {
                complexEntries.add(entry);
            }
        }
        DictUtils.printList(parsedToLists.getErrors(), ProcessPuter.output_data_path, "errorsBeforeCorrection");
        processor.cleanErrors(parsedToLists.getErrors());

        DictUtils.printList(complexEntries, ProcessPuter.output_data_path, "complexEntries.txt");
        System.out.println("Entries enthält " + complexEntries.size() + " Enträge mit einem \"-\"");

    }

    @Ignore
    @Test
    public void parseValladerTwoIterations_Test() throws IOException {

        System.out.println("\n\n__________________\n Zweifaches Parsen");


        String inputFilePath = ProcessPuter.output_data_path + "cleanedrealEntries.txt";

        ParsedToLists parsedToLists = processor.parsePuterListReturn(inputFilePath, "parsedVallader20150506", ProcessPuter.output_data_path);
        List<String> entries = parsedToLists.getEntries();
        List<String> errors = parsedToLists.getErrors();
        List<String> complexEntries = parsedToLists.getComplexEntries();

        System.out.println("Entries enthält " + complexEntries.size() + " Enträge mit mehreren Genusformen");
        DictUtils.printList(complexEntries, ProcessPuter.output_data_path, "complexEntries");

        // Fehler vor der Korrektur
        DictUtils.printList(parsedToLists.getErrors(), ProcessPuter.output_data_path, "errorsBeforeCorrection");

        statistics(inputFilePath);

        // Umgang mit Einträgen mit mehreren Genus
        List<String> processedComplexEntries = processor.processComplexEntries(complexEntries);
        System.out.println(processedComplexEntries.size() +" Einträge wurden zusätzlich aus komplexen Einträgen gewonnen");
        entries.addAll(processedComplexEntries);

        // Umgang mit Parsingfehlern: Korrekturen und erneut parsen
        processor.cleanErrors(parsedToLists.getErrors());
        ParsedToLists secondIterationParsing = processor.parsePuterListReturn(ProcessPuter.output_data_path + "/correctedErrors.txt", "parsedVallader2ndIteration", ProcessPuter.output_data_path);
        System.out.println("Der zweite Durchgang erbrachte " + secondIterationParsing.getEntries().size() + " weitere korrekt geparste Einträge");
        entries.addAll(secondIterationParsing.getEntries());

        System.out.println("Es wurden nach 2 Durchgängen " + entries.size()+ " Einträge korrekt geparst");
        DictUtils.printList(entries,ProcessPuter.output_data_path, "finalParsingResult");

    }

    private void statistics(String inputFilePath) throws IOException {
        List<String> originalFileAsList = new ArrayList<String>();
        Path file = Paths.get(ProcessPuter.output_data_path + "cleanedrealEntries.txt");
        DictUtils.getLines(originalFileAsList, file);
        System.out.println("Die eingelesene Datei hatte " + originalFileAsList.size() + " Zeilen");

        List<String> linesWithoutStartingBlanks;
        linesWithoutStartingBlanks = new ArrayList<String>();

        for (String line: originalFileAsList) {
            if(!line.startsWith("<E> ")) {
                linesWithoutStartingBlanks.add(line);
            }
        }
        System.out.println("Die eingelesene Datei hatte " + linesWithoutStartingBlanks.size() + " Zeilen, die nicht mit einem Leerzeichen beginnen.");
    }

}
