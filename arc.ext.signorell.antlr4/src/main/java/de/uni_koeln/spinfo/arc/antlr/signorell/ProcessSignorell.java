import de.uni_koeln.spinfo.antlr.nvs.common.DictUtils;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by franciscomondaca on 6/12/14.
 */
public class ProcessSignorell {

    public static String signorelli_complete = "../antlr4.parent/signorelli.data/signorelli_full.txt";
    public static String signorelli_data = "../antlr4.parent/signorelli.data/";
    public static String signorelli_t = "../antlr4.parent/signorelli.data/t.txt";


    public void parsePeer(String file, String outputpath, String newFile) throws IOException {

        ANTLRInputStream input = new ANTLRInputStream(new InputStreamReader(
                new FileInputStream(file), "UTF-8"));

        SignorellLexer lexer = new SignorellLexer(input);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        SignorellParser parser = new SignorellParser(tokens);

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


    public static class LemmaLoader extends SignorellParserBaseListener implements ParseTreeListener {
        ArrayList<String> lemmas = new ArrayList<String>();
        ArrayList<String> errors = new ArrayList<String>();


        public void exitLemma(SignorellParser.LemmaContext ctx) {


            List<TerminalNode> tokens = ctx.TOKEN();


            if (tokens.size() >= 2) {
                TerminalNode tn = tokens.get(0);
                TerminalNode tn1 = tokens.get(1);


                String lemma = tn.getText();
                String lemma2 = tn1.getText();

                String stl = lemma.substring(0, 1);
                String stl1 = lemma2.substring(0, 1);


                if (stl.equals(stl1)) {

                    if (ctx.POS() != null) {
                        String pos = ctx.POS().getText();
                        lemmas.add(lemma + '$' + pos);
                        lemmas.add(lemma2 + '$' + pos);
                    } else
                        lemmas.add(lemma + tokens.size());

                }


            } else {
                for (TerminalNode tn : tokens) {


                    String lemma = tn.getText();


                    if (ctx.POS() != null) {
                        String pos = ctx.POS().getText();
                        lemmas.add(lemma + '$' + pos);
                    } else
                        lemmas.add(lemma + tokens.size());

                }
            }


        }


        public void exitError(SignorellParser.ErrorContext ctx) {

            String error = ctx.getText();

            errors.add(error);
            System.out.println("error");


        }


    }



}
