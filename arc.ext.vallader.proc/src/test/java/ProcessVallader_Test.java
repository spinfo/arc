import com.mongodb.MongoClient;
import de.uni_koeln.spinfo.arc.ext.vallader.proc.antlr4.ParsedToLists;
import de.uni_koeln.spinfo.arc.ext.vallader.proc.antlr4.ProcessVallader;
import de.uni_koeln.spinfo.arc.ext.vallader.proc.pdftextstream.PdfXStreamExtractor;
import de.uni_koeln.spinfo.arc.utils.DictUtils;
import de.uni_koeln.spinfo.arc.utils.FileUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andreas on 21.04.2015.
 */
public class ProcessVallader_Test {

    static ProcessVallader processor;
    static MongoClient mongoClient;


    @BeforeClass
    public static void initialize() throws UnknownHostException {

        processor = new ProcessVallader();
    }




    @Ignore
    @Test
    public void TXTtoList_Test() throws IOException {
        List<String> listWithTags = DictUtils.addTags(ProcessVallader.output_data_path + "/pdfTextStreamExtraction2.txt");
        DictUtils.printList(listWithTags, processor.output_data_path, "taggedValladerExtraction");
    }

    @Ignore
    @Test
    public void valladerTXTtoList_Test() throws IOException {
        DictUtils.printList(processor.valladerTXTtoList(ProcessVallader.input_data_path + "tscharner-20140715_20140923_20150319.txt"), ProcessVallader.output_data_path, "ValladerTXTtoList-Test.txt");
    }

    @Ignore
    @Test
    public void cleanedValladerTXTtoList_dummyData_Test() throws IOException {
        List<String> entries = processor.cleanedValladerTXTtoList(ProcessVallader.input_data_path + "testinput.txt");
        DictUtils.printList(entries, ProcessVallader.output_data_path, "cleanedDummyEntries");
        Assert.assertTrue("Es sollten eigentlich 6 akzeptable Einträge gefunden werden", entries.size() == 6);
    }

    @Ignore
    @Test
    public void cleanValladerTXTtoList_realData_Test() throws IOException {
        List<String> entries = processor.cleanedValladerTXTtoList(ProcessVallader.input_data_path + "20150421_vallader.txt");
        DictUtils.printList(entries, ProcessVallader.output_data_path, "cleanedrealEntries");
        Assert.assertNotNull("In dieser Liste sollten hauptsächlich die ersten Zeilen von Wörterbucheinträgen stehen", entries);
    }

    @Test
    public void reduceErrors_Test() throws IOException {
        List<String> entries = processor.cleanedValladerTXTtoList(ProcessVallader.output_data_path + "remainingErrors.txt");
        DictUtils.printList(entries, ProcessVallader.output_data_path, "reducedRemainingErrors");
        Assert.assertNotNull("In dieser Liste sollten hauptsächlich die ersten Zeilen von Wörterbucheinträgen stehen", entries);
    }

    @Test
    public void reduceNotCorrected_Test() throws IOException {
        List<String> entries = processor.cleanedValladerTXTtoList(ProcessVallader.output_data_path + "notCorrectedErrors.txt");
        DictUtils.printList(entries, ProcessVallader.output_data_path, "reducedNotCorrectedErrors");
        Assert.assertNotNull("In dieser Liste sollten hauptsächlich die ersten Zeilen von Wörterbucheinträgen stehen", entries);
    }

    @Ignore
    @Test
    public void pdfXStreamExtractor_Test() throws IOException {
        PdfXStreamExtractor pdfEx = new PdfXStreamExtractor();
        pdfEx.extractWithPDFTextStream(processor.vallader_input, ProcessVallader.output_data_path, "ValladerPdfExtraction");
    }

    @Ignore
    @Test
    public void parseVallader_Test() throws IOException {

        String inputFilePath = ProcessVallader.output_data_path + "cleanedrealEntries.txt";

        processor.parseVallader(inputFilePath, "parsedValladerOLD", ProcessVallader.output_data_path);


    }

    @Ignore
    @Test
    public void parseValladerListReturn_Test() throws IOException {

        System.out.println("\n\n__________________\n Einfaches Parsen");

        String inputFilePath = ProcessVallader.output_data_path + "cleanedrealEntries.txt";
        List<String> complexEntries = new ArrayList<String>();

        ParsedToLists parsedToLists = processor.parseVallader(inputFilePath, "parsedVallader20150506", ProcessVallader.output_data_path);


        for (String entry : parsedToLists.getEntries()) {
            if (entry.contains("-")) {
                complexEntries.add(entry);
            }
        }
        DictUtils.printList(parsedToLists.getErrors(), ProcessVallader.output_data_path, "errorsBeforeCorrection");
        processor.cleanErrors(parsedToLists.getErrors(), processor.output_data_path, "correctedErrorsSingleIt");

        DictUtils.printList(complexEntries, ProcessVallader.output_data_path, "complexEntries.txt");
        System.out.println("Entries enthält " + complexEntries.size() + " Enträge mit einem \"-\"");

    }

    @Ignore
    @Test
    public void parseValladerTwoIterations_Test() throws IOException {

        System.out.println("\n\n__________________\n Zweifaches Parsen");


        String inputFilePath = ProcessVallader.output_data_path + "taggedValladerExtraction.txt";

        ParsedToLists parsedToLists = processor.parseVallader(inputFilePath, "parsedVallader20150506", ProcessVallader.output_data_path);
        List<String> entries = parsedToLists.getEntries();
        List<String> errors = parsedToLists.getErrors();
        List<String> complexEntries = parsedToLists.getComplexEntries();

        System.out.println("Entries enthält " + complexEntries.size() + " Enträge mit mehreren Genusformen");
        DictUtils.printList(complexEntries, ProcessVallader.output_data_path, "complexEntries");

        // Fehler vor der Korrektur
        DictUtils.printList(parsedToLists.getErrors(), ProcessVallader.output_data_path, "errorsBeforeCorrection");


        // Umgang mit Einträgen mit mehreren Genus
        List<String> processedComplexEntries = processor.processComplexEntries(complexEntries);
        System.out.println(processedComplexEntries.size() + " Einträge wurden zusätzlich aus komplexen Einträgen gewonnen");
        entries.addAll(processedComplexEntries);

        // Umgang mit Parsingfehlern: Korrekturen und erneut parsen
        processor.cleanErrors(parsedToLists.getErrors(), processor.output_data_path, "correctedErrors");
        ParsedToLists secondIterationParsing = processor.parseVallader(ProcessVallader.output_data_path + "/correctedErrors.txt", "parsedVallader2ndIteration", ProcessVallader.output_data_path);
        System.out.println("Der zweite Durchgang erbrachte " + secondIterationParsing.getEntries().size() + " weitere korrekt geparste Einträge");
        entries.addAll(secondIterationParsing.getEntries());

        System.out.println("Es wurden nach 2 Durchgängen " + entries.size() + " Einträge korrekt geparst");
        DictUtils.printList(entries, ProcessVallader.output_data_path, "finalParsingResult");

    }

    @Test
    public void extractionWorkflow_Test() throws IOException {
        processor.extractionWorkflow();
    }

    @Ignore
    @Test
    public void parseValladerBracketCorrection_Test() throws IOException {

        System.out.println("\n\n__________________\n Parsen mit Bracket Correction");


        String inputFilePath = ProcessVallader.output_data_path + "ValladerPdfExtraction.txt";

        DictUtils.bracketCorrection(inputFilePath, processor.output_data_path, "ValladerBracketsCorrected");
        List<String> taggedEntries = DictUtils.addTags(processor.output_data_path + "/ValladerBracketsCorrected.txt");
        DictUtils.printList(taggedEntries, processor.output_data_path, "taggedValladerEntries");


        ParsedToLists parsedToLists = processor.parseVallader(processor.output_data_path + "/taggedValladerEntries.txt", "parsedValladerBrackets", ProcessVallader.output_data_path);
        List<String> entries = parsedToLists.getEntries();
        List<String> errors = parsedToLists.getErrors();
        List<String> complexEntries = parsedToLists.getComplexEntries();

        System.out.println("Entries enthält " + complexEntries.size() + " Enträge mit mehreren Genusformen");
        DictUtils.printList(complexEntries, ProcessVallader.output_data_path, "complexEntries");

        // Fehler vor der Korrektur
        DictUtils.printList(parsedToLists.getErrors(), ProcessVallader.output_data_path, "errorsBeforeCorrectionBrackets");

        // Umgang mit Einträgen mit mehreren Genus
        List<String> processedComplexEntries = processor.processComplexEntries(complexEntries);
        System.out.println(processedComplexEntries.size() + " Einträge wurden zusätzlich aus komplexen Einträgen gewonnen");
        entries.addAll(processedComplexEntries);

        // Umgang mit Parsingfehlern: Korrekturen und erneut parsen
        processor.cleanErrors(parsedToLists.getErrors(), processor.output_data_path, "correctedErrorsBrackets");
        ParsedToLists secondIterationParsing = processor.parseVallader(ProcessVallader.output_data_path + "/correctedErrorsBrackets.txt", "parsedVallader2ndIterationBrackets", ProcessVallader.output_data_path);
        System.out.println("Der zweite Durchgang erbrachte " + secondIterationParsing.getEntries().size() + " weitere korrekt geparste Einträge");
        entries.addAll(secondIterationParsing.getEntries());
        DictUtils.printList(secondIterationParsing.getEntries(), processor.output_data_path, "savedEntries");
        DictUtils.printList(secondIterationParsing.getErrors(), processor.output_data_path, "remainingErrors");

        System.out.println("Es wurden nach 2 Durchgängen " + entries.size() + " Einträge korrekt geparst");
        DictUtils.printList(entries, ProcessVallader.output_data_path, "finalParsingResultBrackets");

    }


    @Test
    public void parsingEvalReduceLines() throws IOException {
        List<String> resultAllLines = DictUtils.txtToList(ProcessVallader.output_data_path+"Extraction2_finalResult.txt");
        List<String> resultLinesRemoved = DictUtils.txtToList(ProcessVallader.output_data_path+"reducedParsing_finalResult.txt");

        resultAllLines.removeAll(resultLinesRemoved);

        DictUtils.printList(resultAllLines,ProcessVallader.output_data_path, "new_additionalParsingResults");
    }

    @Ignore
    @Test
    public void removeCapLetterEntries_Test() throws IOException {
        List<String> additionalParsingResults = FileUtils.fileToList(processor.output_data_path+ "new_additionalParsingResults.txt");

        List<String> removed = DictUtils.removeCapLetterEntries(additionalParsingResults);

        DictUtils.printList(removed,ProcessVallader.output_data_path, "new_withoutCapLetterEntries");
    }


    @Test
    public void addToFinalResult_Test() throws IOException {
        List<String> additionalParsingResults = FileUtils.fileToList(processor.output_data_path+ "new_additionalParsingResults.txt");

        List<String> removed = DictUtils.removeCapLetterEntries(additionalParsingResults);

        List<String> finalResults = FileUtils.fileToList(processor.output_data_path+ "reducedParsing_finalResult.txt");
        finalResults.addAll(removed);

        DictUtils.printList(finalResults,ProcessVallader.output_data_path, "finalParsingResult_20150716-2");
    }

    @Ignore
    @Test
    public void extractionWF_Test2() throws IOException {

        processor.extractionWorkflow("Extraction2");
    }


    @Test
    public void parsingWF_Test() throws IOException {

        processor.parsingWorkflow(processor.output_data_path+"Extraction2_PdfExtraction.txt","reducedParsing");
    }

    @Test
    public void findGermanLemmas_Test() throws IOException {
        DictUtils.findGermanLemmas(processor.input_data_path + "german.txt", processor.output_data_path + "tscharner-20140715_20140923-20150318-20150716.txt", processor.output_data_path);
    }

    @Test
    public void removeGermanLemmas_Test() throws IOException {
        DictUtils.removeGermanLemmas(processor.output_data_path+"removedGermanWords_manuell.txt", processor.output_data_path+"tscharner-20140715_20140923-20150318-20150716.txt", processor.output_data_path);
    }

    @Test
    public void replaceDots_Test() throws IOException {
        DictUtils.replaceDots(processor.input_data_path + "tscharner-20140715_20140923-20150318-20150813.txt", processor.output_data_path,"tscharner-20140715_20140923-20150318-20150818");
    }


}
