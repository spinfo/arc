package de.uni_koeln.spinfo.arc.ext.puter.proc.antlr4;

import de.uni_koeln.spinfo.arc.ext.vallader.proc.pdftextstream.PdfXStreamExtractor;
import de.uni_koeln.spinfo.arc.puter.gramm.PuterBaseListener;
import de.uni_koeln.spinfo.arc.puter.gramm.PuterLexer;
import de.uni_koeln.spinfo.arc.puter.gramm.PuterParser;
import de.uni_koeln.spinfo.arc.utils.DictUtils;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Andreas on 21.04.2015.
 */
public class ProcessPuter {

    public static String output_data_path = "../arc.data/output/puter/";
    public static String input_data_path = "../arc.data/input/puter/";

    public void extractionWorkflow(String pdfFilePath) throws IOException, ParseException {

        // Text aus pdf extrahieren
        PdfXStreamExtractor pdfEx = new PdfXStreamExtractor();
        pdfEx.extractWithPDFTextStream("PuterPdfStreamExtraction"+timestamper());
        // valladerTXTtoList optional: cleanVAlladerTXTtoList
        //DictUtils.printList(:::);
        // processPuterListReturn
        // Statistiken
        // Errors and komplex Lemmas
            // delinate Genus
            // processPuterListReturn
            // Statistiken
        // Dict.Utils.PrintList

    }

    public String timestamper() throws ParseException {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        System.out.println(timeStamp);
        return timeStamp;
    }

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

    public void parseVallader(String file, String newFile, String outputpath)
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

    }

    public ParsedToLists parseValladerListReturn(String file, String newFile, String outputpath)
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

        // PostProcs
        ArrayList<String> lemmas = loader.lemmas;
        ArrayList<String> errors = getErrorLines(file, loader.errorLines);
        ArrayList<String> complexLemmas = filterComplexEntries(lemmas);

        // Output

        DictUtils.printList(lemmas, outputpath, newFile);
        System.out.println("lemmas: " + lemmas.size());
        DictUtils.printList(errors, outputpath, newFile + "_errors");
        System.out.println("errors: " + loader.errorLines.size());
        DictUtils.printList(complexLemmas, outputpath, newFile + "_complexLemmas");
        System.out.println("complexLemmas: " + complexLemmas.size());

        return new ParsedToLists(lemmas, errors, complexLemmas);
    }

    private ArrayList<String> filterComplexEntries(ArrayList<String> lemmas) {
        ArrayList<String> complexEntries = new ArrayList<String>();

        Iterator<String> entryIt = lemmas.iterator();

        while (entryIt.hasNext()) {
            String entry = entryIt.next();
            if (entry.contains("-")) {
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
    public void cleanErrors(List<String> errorEntries) throws IOException {
        ArrayList<String> cleanedList = new ArrayList<String>();

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
        Pattern posPattern = Pattern.compile("(<E>\\s{0,3}\\p{L}{1,30})([m,f|adj/adv|adj invar/num|adj invar|adj|adv|invar/num|prep|interj|intr|intr/tr|tr ind|tr|mpl|fpl|cj|pron indef|pron pers|pron pers/refl|pron|refl|fcoll|p sg|m|f])(\\s)");

        // OCR-Fehler refl = rejl
        //Pattern reflPattern = Pattern.compile("(rejl)");



        for(String errorEntry : errorEntries) {
            String corrected = "";

            // Patterns suchen und entsprechend ändern

            // 1. OCR-Fehler rejl = refl

            //Matcher reflMatcher = reflPattern.matcher(errorEntry);
           /* if (reflMatcher.find()) {
                System.out.println("Found refl" + reflMatcher.group(1));
                reflMatcher.replaceAll(" refl ");
            }*/
            errorEntry = errorEntry.replaceAll("rejl","refl");

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
                corrected = posMatcher.replaceFirst(posMatcher.group(1)+" "+posMatcher.group(2)+"");
            }

            if (!corrected.equals("")){
                cleanedList.add(corrected);
            }
        }

        DictUtils.printList(cleanedList, ProcessPuter.output_data_path, "correctedErrors");

    }


}
