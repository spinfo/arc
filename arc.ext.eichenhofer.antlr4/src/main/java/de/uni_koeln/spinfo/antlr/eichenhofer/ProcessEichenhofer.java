package de.uni_koeln.spinfo.antlr.eichenhofer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import de.uni_koeln.spinfo.arc.utils.DictUtils;

/**
 * Created by franciscomondaca on 7/21/14.
 */
public class ProcessEichenhofer {

    public static String eich_input = "../eichenhofer/eichenhofer.data/20140715_pdftextstream.txt";
    public static String eich_dir = "../eichenhofer/eichenhofer.data/";


    public void parseEichenhofer(String file, String newFile, String outputpath) throws IOException {

        ANTLRInputStream input = new ANTLRInputStream(new InputStreamReader(
                new FileInputStream(file), "UTF-8"));


        EichenhoferLexer lexer = new EichenhoferLexer(input);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        EichenhoferParser parser = new EichenhoferParser(tokens);

        parser.setBuildParseTree(true);
        ParseTree tree = parser.startSymb();

        ParseTreeWalker walker = new ParseTreeWalker();

        LemmaLoader loader = new LemmaLoader();
        walker.walk(loader, tree);


        DictUtils.printList(loader.lemmas, outputpath, newFile);
        System.out.println("lemmas: " + loader.lemmas.size());
        DictUtils.printList(loader.errors, outputpath, newFile + "_errors");
        System.out.println("errors: " + loader.errors.size());


    }


    public static class LemmaLoader extends EichenhoferBaseListener {
        ArrayList<String> lemmas = new ArrayList<String>();
        ArrayList<String> errors = new ArrayList<String>();


        public void exitLemma(EichenhoferParser.LemmaContext ctx) {

            TerminalNode complexTokens = ctx.COMPLEXTOKEN();


            if (complexTokens != null) {

                String lemma = complexTokens.getText();

                String pos = ctx.POS().getText();
                lemmas.add(lemma + '$' + pos);
            }

        }


        public void exitError(EichenhoferParser.ErrorContext ctx) {

            String error = ctx.getText();

            errors.add(error);
            //System.out.println("error");


        }


    }
}
