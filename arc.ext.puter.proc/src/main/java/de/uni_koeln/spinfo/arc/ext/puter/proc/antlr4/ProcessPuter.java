package de.uni_koeln.spinfo.arc.ext.puter.proc.antlr4;

import de.uni_koeln.spinfo.arc.ext.vallader.proc.pdftextstream.PdfXStreamExtractor;
import de.uni_koeln.spinfo.arc.ext.puter.gramm.PuterBaseListener;
import de.uni_koeln.spinfo.arc.ext.puter.gramm.PuterLexer;
import de.uni_koeln.spinfo.arc.ext.puter.gramm.PuterParser;
import de.uni_koeln.spinfo.arc.utils.DictUtils;
import de.uni_koeln.spinfo.arc.utils.FileUtils;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.*;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Andreas on 01.07.2015.
 */
public class ProcessPuter {

    public static String output_data_path = "../arc.data/puter/output/";
    public static String input_data_path = "../arc.data/puter/input/";
    public static String puter_input = ProcessPuter.input_data_path + "tscharner-20150114_20150318_20150326.pdf";

    public void extractionWorkflow(String fileNamePrefix) throws IOException {

        // PDF Extraction
        PdfXStreamExtractor pdfExt = new PdfXStreamExtractor();
        pdfExt.extractWithPDFTextStream(puter_input,this.output_data_path, fileNamePrefix+"_PdfExtraction");

        String inputFilePath = this.output_data_path + fileNamePrefix +"_PdfExtraction.txt";

        List<String> workflowList = new ArrayList<>();

        // Preprocessing
        workflowList = DictUtils.bracketCorrection(inputFilePath, ProcessPuter.output_data_path, fileNamePrefix+"_BracketsCorrected");
        /* Check */ System.out.println("bracketCorrection durchgeführt.");
        workflowList = DictUtils.addTags(workflowList);
          /* Check */ System.out.println("Einträge mit Tags versehen.");
        DictUtils.printList(workflowList,ProcessPuter.output_data_path,fileNamePrefix+"_taggedEntries");

        statistics(inputFilePath);

        // Parsing
        ParsedToLists parsedToLists = parsePuter(output_data_path+fileNamePrefix+"_taggedEntries.txt", output_data_path, fileNamePrefix+"_parsed");
        List<String> entries = parsedToLists.getEntries();
        List<String> errors = parsedToLists.getErrors();
        List<String> complexEntries = parsedToLists.getComplexEntries();

        System.out.println("Entries enthält " + complexEntries.size() + " Enträge mit mehreren Genusformen");

        // Umgang mit Einträgen mit mehreren Genus
        List<String> processedComplexEntries = processComplexEntries(complexEntries);
        System.out.println(processedComplexEntries.size() +" Einträge wurden zusätzlich aus komplexen Einträgen gewonnen");
        entries.addAll(processedComplexEntries);

        // Umgang mit Parsingfehlern: Korrekturen und erneut parsen
        cleanErrors(errors, this.output_data_path, fileNamePrefix+"_correctedErrors");
        ParsedToLists secondIterationParsing = parsePuter(this.output_data_path + fileNamePrefix +"_correctedErrors.txt", this.output_data_path, fileNamePrefix + "_parsed2ndIteration");
        System.out.println("Der zweite Durchgang erbrachte " + secondIterationParsing.getEntries().size() + " weitere korrekt geparste Einträge");
        entries.addAll(secondIterationParsing.getEntries());
        DictUtils.printList(secondIterationParsing.getEntries(),this.output_data_path, fileNamePrefix + "_savedEntries");
        DictUtils.printList(secondIterationParsing.getErrors(),this.output_data_path, fileNamePrefix + "_remainingErrors");

        System.out.println("Es wurden nach 2 Durchgängen " + entries.size()+ " Einträge korrekt geparst");
        DictUtils.printList(entries,this.output_data_path, fileNamePrefix + "_finalResult");

    }

    public void parsingWorkflow(String inputFilePath, String fileNamePrefix) throws IOException {

        List<String> workflowList = new ArrayList<>();

        workflowList = DictUtils.bracketCorrection(inputFilePath, ProcessPuter.output_data_path, fileNamePrefix + "_BracketsCorrected");
        /* Check */ System.out.println("bracketCorrection durchgeführt.");
        workflowList = DictUtils.addTags(workflowList);
          /* Check */ System.out.println("Einträge mit Tags versehen.");
        DictUtils.printList(workflowList,ProcessPuter.output_data_path,fileNamePrefix+"_taggedEntries");

        statistics(inputFilePath);

        ParsedToLists parsedToLists = parsePuter(output_data_path+fileNamePrefix+"_taggedEntries.txt", output_data_path, fileNamePrefix+"_parsed");
        List<String> entries = parsedToLists.getEntries();
        List<String> errors = parsedToLists.getErrors();
        List<String> complexEntries = parsedToLists.getComplexEntries();

        System.out.println("Entries enthält " + complexEntries.size() + " Enträge mit mehreren Genusformen");

        // Umgang mit Einträgen mit mehreren Genus
        List<String> processedComplexEntries = processComplexEntries(complexEntries);
        System.out.println(processedComplexEntries.size() +" Einträge wurden zusätzlich aus komplexen Einträgen gewonnen");
        entries.addAll(processedComplexEntries);

        // Umgang mit Parsingfehlern: Korrekturen und erneut parsen
        cleanErrors(errors, this.output_data_path, fileNamePrefix+"_correctedErrors");
        ParsedToLists secondIterationParsing = parsePuter(this.output_data_path + fileNamePrefix +"_correctedErrors.txt", this.output_data_path, fileNamePrefix + "_parsed2ndIteration");
        System.out.println("Der zweite Durchgang erbrachte " + secondIterationParsing.getEntries().size() + " weitere korrekt geparste Einträge");
        entries.addAll(secondIterationParsing.getEntries());
        DictUtils.printList(secondIterationParsing.getEntries(),this.output_data_path, fileNamePrefix + "_savedEntries");
        DictUtils.printList(secondIterationParsing.getErrors(),this.output_data_path, fileNamePrefix + "_remainingErrors");

        System.out.println("Es wurden nach 2 Durchgängen " + entries.size()+ " Einträge korrekt geparst");
        DictUtils.printList(entries,this.output_data_path, fileNamePrefix + "_finalResult");

    }



    private void statistics(String inputFilePath) throws IOException {
        List<String> originalFileAsList = new ArrayList<String>();
        Path file = Paths.get(inputFilePath);
        DictUtils.getLines(originalFileAsList, file);
        System.out.println("\n" +
                "\nDie eingelesene Datei hatte " + originalFileAsList.size() + " Zeilen");

        List<String> linesWithoutStartingBlanks = new ArrayList<String>();

        // Pattern for lines starting with 3 or more blanks
        Pattern pattern = Pattern.compile("^(<E>)?[ ]+");

        for (String line: originalFileAsList) {

            // Create matcher object
            Matcher matcher = pattern.matcher(line);

            // Check if the matcher's prefix match with the matcher's pattern
            if (!matcher.lookingAt()) {
                // add line to list
                linesWithoutStartingBlanks.add(line);
            }
            /*if(!line.startsWith("<E> ")) {
                linesWithoutStartingBlanks.add(line);
            }*/
        }
        System.out.println("Die eingelesene Datei hatte " + linesWithoutStartingBlanks.size() + " Zeilen, die nicht mit einem Leerzeichen beginnen.");
    }

   /* public List<String> valladerTXTtoList (String filePath) throws IOException {

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
        Pattern pattern = Pattern.compile("^(<E>)?  [ ]+");

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
                entryList.add(currentLine);
            }
        }

        reader.close();
        return entryList;
    }*/

    private String addTags(String string) {
        StringBuilder builder = new StringBuilder();
        builder.append("<E>");
        builder.append(string);
        builder.append("</E>");
        return builder.toString();
    }

 /*   public void parsePuter(String file, String newFile, String outputpath)
            throws IOException {

        ANTLRInputStream input = new ANTLRInputStream(new InputStreamReader(
                new FileInputStream(file), "UTF8"));

        PuterLexer lexer = new PuterLexer(input);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        PuterParser parser = new PuterParser(tokens);

        parser.setBuildParseTree(true);
        ParseTree tree = parser.dict();

        ParseTreeWalker walker = new ParseTreeWalker();

        LemmaLoader loader = new LemmaLoader();
        walker.walk(loader, tree);

        ArrayList<String> errors = getErrorLines(file, loader.errorLines);

        DictUtils.printList(loader.lemmas, outputpath, newFile);
        System.out.println("lemmas: " + loader.lemmas.size());
        DictUtils.printList(errors, outputpath, newFile + "_errors");
        System.out.println("errors: " + errors.size());

    }*/

    ParsedToLists parsePuter(String inputFilePath, String outputpath, String fileName)
            throws IOException {

        ANTLRInputStream input = new ANTLRInputStream(new InputStreamReader(
                new FileInputStream(inputFilePath), "UTF8"));

        PuterLexer lexer = new PuterLexer(input);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        PuterParser parser = new PuterParser(tokens);

        parser.setBuildParseTree(true);
        ParseTree tree = parser.dict();

        ParseTreeWalker walker = new ParseTreeWalker();

        LemmaLoader loader = new LemmaLoader();
        walker.walk(loader, tree);

        // PostProcs
        ArrayList<String> lemmas = loader.lemmas;
        ArrayList<String> errors = getErrorLines(inputFilePath, loader.errorLines);
        ArrayList<String> complexLemmas = filterComplexEntries(lemmas);

        // Output

        DictUtils.printList(lemmas, outputpath, fileName);
        System.out.println("lemmas: " + lemmas.size());
        DictUtils.printList(errors, outputpath, fileName + "_errors");
        System.out.println("errors: " + loader.errorLines.size());
        DictUtils.printList(complexLemmas, outputpath, fileName + "_complexLemmas");
        System.out.println("complexLemmas: " + complexLemmas.size());

        return new ParsedToLists(lemmas, errors, complexLemmas);
    }

    private ArrayList<String> filterComplexEntries(ArrayList<String> lemmas) {
        ArrayList<String> complexEntries = new ArrayList<String>();

        Pattern complexPattern = Pattern.compile(",\\s?-");

        Iterator<String> entryIt = lemmas.iterator();

        while (entryIt.hasNext()) {
            String entry = entryIt.next();
            Matcher complexMatcher = complexPattern.matcher(entry);
            if (complexMatcher.find()) {
                complexEntries.add(entry);
                entryIt.remove();
            }
        }
        return complexEntries;
    }

    private ArrayList<String> getErrorLines(String file, SortedSet<Integer> errorLines) throws IOException {

        ArrayList<String> errors = new ArrayList<String>();

        FileInputStream fis = new FileInputStream(new File(file));
        InputStreamReader isr = new InputStreamReader(fis, "UTF8");
        LineNumberReader reader = new LineNumberReader(isr);

        String currentLine;
        // Iterator<Integer> errorLineIterator = errorLines.iterator();
        // int currentErrorLine = errorLineIterator.next();

        // nicht sonderlich performant, besser auch durch das Set iterieren
        while ((currentLine = reader.readLine()) != null) {
            if (errorLines.contains(reader.getLineNumber())) {

                //Kontrollausgabe
                //System.out.println(reader.getLineNumber() + ": " + currentLine);

                errors.add(currentLine);
            }
        }
        return errors;
    }

    public static class LemmaLoader extends PuterBaseListener implements
            ParseTreeListener {
        ArrayList<String> lemmas = new ArrayList<String>();
        //ArrayList<String> errors = new ArrayList<String>();
        SortedSet<Integer> errorLines = new TreeSet<Integer>();

        public void exitLexentry(PuterParser.LexentryContext ctx) {

            List<TerminalNode> tokens = ctx.keyphrase().phrase().COMPLEXWORD();

            if (tokens.size() >= 2) {
                TerminalNode tn = tokens.get(0);
                TerminalNode tn1 = tokens.get(1);

                String lemma = tn.getText();
                String lemma2 = tn1.getText();

                String stl = lemma.substring(0, 1);
                String stl1 = lemma2.substring(0, 1);

                if (stl.equals(stl1)) {

                    if (ctx.grammatical_info().GRAMM() != null) {
                        String pos = ctx.grammatical_info().GRAMM().getText();
                        lemmas.add(lemma + '$' + pos);
                        lemmas.add(lemma2 + '$' + pos);
                    } else
                        lemmas.add(lemma + tokens.size());

                }

            } else {
                for (TerminalNode tn : tokens) {

                    String lemma = tn.getText();

                    if (ctx.grammatical_info().GRAMM() != null) {
                        String pos = ctx.grammatical_info().GRAMM().getText();
                        lemmas.add(lemma + '$' + pos);
                    } else
                        lemmas.add(lemma + tokens.size());

                }
            }

        }

        public void exitError(PuterParser.ErrorContext ctx) {

            // String error = ctx.getText();
            int lineNumber = ctx.getStart().getLine();
            errorLines.add(lineNumber);
            // errors.add(error);
            //System.out.println("error");

        }

        @Override
        public void visitTerminal(TerminalNode terminalNode) {

        }

        @Override
        public void visitErrorNode(ErrorNode errorNode) {

        }

        @Override
        public void enterEveryRule(ParserRuleContext parserRuleContext) {

        }

        @Override
        public void exitEveryRule(ParserRuleContext parserRuleContext) {

        }

    }

    public List<String>  processComplexEntries(List<String> complexEntries) {
        return declinateGenus(complexEntries);
    }

    /**
     * Bekommt Liste mit korrekt geparsten Einträgen, die noch ein weibliches Suffix angeschlossen haben
     * und transformiert diese Einträge in einen männlichen und einen weiblichen Eintrag.
     * @param multiGenusEntries
     * @return
     */
    private List<String> declinateGenus(List<String> multiGenusEntries) {
        List<String> declinatedEntries = new ArrayList<String>();

        // TODO: Einträge aufspalten, einmal als m, einmal als f einfügen.
        for (String entry : multiGenusEntries) {
            //System.out.println(entry);
            String[] lemPos = entry.split("\\$");
            String[] maskFem = lemPos[0].split(",\\s?-");
            //System.out.println("lemPos: " + lemPos[0] + ", maskFem: " +  maskFem[0]);
            // maskuline Form
            if (maskFem.length == 2 && lemPos.length == 2) {
                declinatedEntries.add(maskFem[0] + "$" + lemPos[1]);
                // feminine Form
                //declinatedEntries.add(generateFemininForm(maskFem)+"$"+lemPos[1]);
            }
        }
        System.out.println("declinatedEntries enthält " + declinatedEntries.size() + " Einträge");
        return declinatedEntries;
    }

    private String generateFemininForm(String[] split) {
        String femininEntry = "";

        // feminine Endung "a"
        if (split[1].equals("a")) {
            // nur bei e oder bei allen weiteren Vokalen auch?
            if (split[0].endsWith("e")) {
                femininEntry = split[0].substring(0,split[0].length()-1)+split[1];
            } else {
                femininEntry = split[0] + split[1];
            }
        }
        // "bla" bel -> bla
        // "vla" vel -> vla
        // "ada" a -> ada
        // "stra" ster -> stra
        // "ta" +ta
        // "fa" +fa
        // "ca" + ca
        // "ta" + ta
        // "da" + da
        // "gla" g -> gla
        // "schem" schem -> schma


        return femininEntry;
    }

    /**
     * Gets List of Lines with parser-errors and tries to fix some of the common OCR-Errors on them.
     * @param
     */
    public void cleanErrors(List<String> errorEntries, String filepath, String fileName) throws IOException {
        ArrayList<String> cleanedList = new ArrayList<String>();
        ArrayList<String> qmList = new ArrayList<String>();
        ArrayList<String> notCorrectedList = new ArrayList<String>();

        /*TODO: mf -> " m f " bzw wie mit 2 POS umgehen;
        f am Ende zu " f"
        m am Ende zu " m"
        adj
        adv
        ,-.{1,3}mf
         */
        // mf bei genusspezifischen Endungen
        Pattern mfPattern = Pattern.compile("(?<=,\\s?-\\w{1,3})(mf)");
        // POS ist direkt an Lemma angschlossen

        //Pattern posPattern = Pattern.compile("(?<=^<E>\\s{0,3}\\p{L}{1,25})[m|f|m,f|adj/adv|adj invar/num|adj invar|adj|adv|invar/num|prep|interj|intr|intr/tr|tr ind|tr|mpl|fpl|cj|pron indef|pron pers|pron pers/refl|pron|refl|fcoll|p sg]");
        Pattern posPattern = Pattern.compile("(<E>\\s{0,3}\\p{L}{1,30})(m,f|adj/adv|adj invar/num|adj invar|adj|adv|invar/num|prep|interj|intr|intr/tr|tr ind|tr|mpl|fpl|cj|pron indef|pron pers|pron pers/refl|pron|refl|fcoll|p sg|m|f)(\\s|\\()");

        // OCR-Fehler refl = rejl
        Pattern reflPattern = Pattern.compile("(rejl)");



        for(String errorEntry : errorEntries) {
            String corrected = "";

            // Patterns suchen und entsprechend ändern

            // 1. OCR-Fehler rejl = refl

            Matcher reflMatcher = reflPattern.matcher(errorEntry);
            if (reflMatcher.find()) {
                corrected = reflMatcher.replaceAll(" refl ");
            }
            //errorEntry = errorEntry.replaceAll("rejl","refl");

            // 2. angeschlossenes mf an feminines Suffix
            Matcher mfMatcher = mfPattern.matcher(errorEntry);
            if(mfMatcher.find()) {
                //System.out.println("Found mf" + mfMatcher.group(1));
                corrected = mfMatcher.replaceFirst(" mf ");
            }


            // 3. direkt angeschlossenes POS
            Matcher posMatcher = posPattern.matcher(errorEntry);
            if(posMatcher.find()){
                //System.out.println("found pos" + posMatcher.group(1)+posMatcher.group(2));
                corrected = posMatcher.replaceFirst(posMatcher.group(1)+" "+posMatcher.group(2)+" ");
            }

            if (!corrected.equals("")){
                cleanedList.add(corrected);
                // Kontrolle
                qmList.add(errorEntry);
                qmList.add(corrected);
            } else {
                // Kontrolle
                notCorrectedList.add(errorEntry);
            }
        }
        System.out.println(cleanedList.size() +" Error-Einträge wurden für einen erneuten Parsingversuch bearbeitet");

        DictUtils.printList(cleanedList, filepath, fileName);
        DictUtils.printList(qmList, this.output_data_path, "correctionsBeforeAndAfter");
        DictUtils.printList(notCorrectedList, this.output_data_path, "notCorrectedErrors");

    }





}