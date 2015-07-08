import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import de.uni_koeln.spinfo.arc.ext.vallader.proc.antlr4.ParsedToLists;
import de.uni_koeln.spinfo.arc.ext.vallader.proc.antlr4.ProcessVallader;
import de.uni_koeln.spinfo.arc.ext.vallader.proc.mongo.MongoIO;
import de.uni_koeln.spinfo.arc.ext.vallader.proc.pdftextstream.PdfXStreamExtractor;
import de.uni_koeln.spinfo.arc.utils.DictUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by Andreas on 21.04.2015.
 */
public class ProcessVallader_Test {

    static ProcessVallader processor;
    static MongoClient mongoClient;
    static MongoIO mongoIO;

    @BeforeClass
    public static void initialize() throws UnknownHostException {

        processor = new ProcessVallader();
        mongoIO = new MongoIO();
        mongoClient = new MongoClient("localhost", 27017);

    }

    @Test
    public void testValladerToMongo() throws IOException {

        String txtFile = processor.output_data_path + "finalParsingResultBrackets.txt";
        String dbName = "arc_test";
        String collectionName = "vallader";

        DB db = mongoClient.getDB(dbName);
        DBCollection collection = db.getCollection(collectionName);

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(
                txtFile), StandardCharsets.UTF_8));

        mongoIO.txtToMongo(br, collection);

    }

    @Ignore
    @Test
    public void TXTtoList_Test() throws IOException {
        List<String> listWithTags = DictUtils.addTags(ProcessVallader.output_data_path +"/pdfTextStreamExtraction2.txt");
        DictUtils.printList(listWithTags, processor.output_data_path,"taggedValladerExtraction");
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

    @Test
    public void reduceErrors_Test() throws IOException {
        List<String> entries = processor.cleanedValladerTXTtoList(ProcessVallader.output_data_path + "remainingErrors.txt");
        DictUtils.printList(entries, ProcessVallader.output_data_path,"reducedRemainingErrors");
        Assert.assertNotNull("In dieser Liste sollten hauptsächlich die ersten Zeilen von Wörterbucheinträgen stehen", entries);
    }

    @Test
    public void reduceNotCorrected_Test() throws IOException {
        List<String> entries = processor.cleanedValladerTXTtoList(ProcessVallader.output_data_path + "notCorrectedErrors.txt");
        DictUtils.printList(entries, ProcessVallader.output_data_path,"reducedNotCorrectedErrors");
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

        ParsedToLists parsedToLists = processor.parseValladerListReturn(inputFilePath, "parsedVallader20150506", ProcessVallader.output_data_path);


        for (String entry : parsedToLists.getEntries()) {
            if (entry.contains("-")) {
                complexEntries.add(entry);
            }
        }
        DictUtils.printList(parsedToLists.getErrors(), ProcessVallader.output_data_path, "errorsBeforeCorrection");
        processor.cleanErrors(parsedToLists.getErrors(),processor.output_data_path,"correctedErrorsSingleIt");

        DictUtils.printList(complexEntries, ProcessVallader.output_data_path, "complexEntries.txt");
        System.out.println("Entries enthält " + complexEntries.size() + " Enträge mit einem \"-\"");

    }

    @Ignore
    @Test
    public void parseValladerTwoIterations_Test() throws IOException {

        System.out.println("\n\n__________________\n Zweifaches Parsen");


        String inputFilePath = ProcessVallader.output_data_path + "taggedValladerExtraction.txt";

        ParsedToLists parsedToLists = processor.parseValladerListReturn(inputFilePath, "parsedVallader20150506", ProcessVallader.output_data_path);
        List<String> entries = parsedToLists.getEntries();
        List<String> errors = parsedToLists.getErrors();
        List<String> complexEntries = parsedToLists.getComplexEntries();

        System.out.println("Entries enthält " + complexEntries.size() + " Enträge mit mehreren Genusformen");
        DictUtils.printList(complexEntries, ProcessVallader.output_data_path, "complexEntries");

        // Fehler vor der Korrektur
        DictUtils.printList(parsedToLists.getErrors(), ProcessVallader.output_data_path, "errorsBeforeCorrection");


        // Umgang mit Einträgen mit mehreren Genus
        List<String> processedComplexEntries = processor.processComplexEntries(complexEntries);
        System.out.println(processedComplexEntries.size() +" Einträge wurden zusätzlich aus komplexen Einträgen gewonnen");
        entries.addAll(processedComplexEntries);

        // Umgang mit Parsingfehlern: Korrekturen und erneut parsen
        processor.cleanErrors(parsedToLists.getErrors(), processor.output_data_path,"correctedErrors");
        ParsedToLists secondIterationParsing = processor.parseValladerListReturn(ProcessVallader.output_data_path+"/correctedErrors.txt","parsedVallader2ndIteration", ProcessVallader.output_data_path);
        System.out.println("Der zweite Durchgang erbrachte " + secondIterationParsing.getEntries().size() + " weitere korrekt geparste Einträge");
        entries.addAll(secondIterationParsing.getEntries());

        System.out.println("Es wurden nach 2 Durchgängen " + entries.size()+ " Einträge korrekt geparst");
        DictUtils.printList(entries,ProcessVallader.output_data_path, "finalParsingResult");

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

        processor.bracketCorrection(inputFilePath, processor.output_data_path, "ValladerBracketsCorrected");
        List<String> taggedEntries = DictUtils.addTags(processor.output_data_path +"/ValladerBracketsCorrected.txt");
        DictUtils.printList(taggedEntries,processor.output_data_path,"taggedValladerEntries");


        ParsedToLists parsedToLists = processor.parseValladerListReturn(processor.output_data_path+"/taggedValladerEntries.txt", "parsedValladerBrackets", ProcessVallader.output_data_path);
        List<String> entries = parsedToLists.getEntries();
        List<String> errors = parsedToLists.getErrors();
        List<String> complexEntries = parsedToLists.getComplexEntries();

        System.out.println("Entries enthält " + complexEntries.size() + " Enträge mit mehreren Genusformen");
        DictUtils.printList(complexEntries, ProcessVallader.output_data_path, "complexEntries");

        // Fehler vor der Korrektur
        DictUtils.printList(parsedToLists.getErrors(), ProcessVallader.output_data_path, "errorsBeforeCorrectionBrackets");

        // Umgang mit Einträgen mit mehreren Genus
        List<String> processedComplexEntries = processor.processComplexEntries(complexEntries);
        System.out.println(processedComplexEntries.size() +" Einträge wurden zusätzlich aus komplexen Einträgen gewonnen");
        entries.addAll(processedComplexEntries);

        // Umgang mit Parsingfehlern: Korrekturen und erneut parsen
        processor.cleanErrors(parsedToLists.getErrors(),processor.output_data_path, "correctedErrorsBrackets");
        ParsedToLists secondIterationParsing = processor.parseValladerListReturn(ProcessVallader.output_data_path+"/correctedErrorsBrackets.txt","parsedVallader2ndIterationBrackets", ProcessVallader.output_data_path);
        System.out.println("Der zweite Durchgang erbrachte " + secondIterationParsing.getEntries().size() + " weitere korrekt geparste Einträge");
        entries.addAll(secondIterationParsing.getEntries());
        DictUtils.printList(secondIterationParsing.getEntries(),processor.output_data_path,"savedEntries");
        DictUtils.printList(secondIterationParsing.getErrors(),processor.output_data_path,"remainingErrors");

        System.out.println("Es wurden nach 2 Durchgängen " + entries.size()+ " Einträge korrekt geparst");
        DictUtils.printList(entries,ProcessVallader.output_data_path, "finalParsingResultBrackets");

    }




}
