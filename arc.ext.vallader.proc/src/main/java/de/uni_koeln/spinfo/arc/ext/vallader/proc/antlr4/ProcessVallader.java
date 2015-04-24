package de.uni_koeln.spinfo.arc.ext.vallader.proc.antlr4;

import de.uni_koeln.spinfo.arc.ext.vallader.gramm.ValladerBaseListener;
import de.uni_koeln.spinfo.arc.ext.vallader.gramm.ValladerLexer;
import de.uni_koeln.spinfo.arc.ext.vallader.gramm.ValladerParser;
import de.uni_koeln.spinfo.arc.utils.DictUtils;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
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

    public void parseVallader(String file, String newFile, String outputpath)
            throws IOException {

        ANTLRInputStream input = new ANTLRInputStream(new InputStreamReader(
                new FileInputStream(file), "UTF8"));

        ValladerLexer lexer = new ValladerLexer(input);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        ValladerParser parser = new ValladerParser(tokens);

        parser.setBuildParseTree(true);
        ParseTree tree = parser.dict();

        ParseTreeWalker walker = new ParseTreeWalker();

        LemmaLoader loader = new LemmaLoader();
        walker.walk(loader, tree);

        DictUtils.printList(loader.lemmas, outputpath, newFile);
        System.out.println("lemmas: " + loader.lemmas.size());
        DictUtils.printList(loader.errors, outputpath, newFile + "_errors");
        System.out.println("errors: " + loader.errors.size());

    }

    public static class LemmaLoader extends ValladerBaseListener implements
            ParseTreeListener {
        ArrayList<String> lemmas = new ArrayList<String>();
        ArrayList<String> errors = new ArrayList<String>();

        public void exitLemma(ValladerParser.LexentryContext ctx) {

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

    }

}
