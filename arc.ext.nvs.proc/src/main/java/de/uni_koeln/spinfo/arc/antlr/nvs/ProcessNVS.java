package de.uni_koeln.spinfo.arc.antlr.nvs;

import de.uni_koeln.spinfo.arc.ext.nvs.gramm.NVSBaseListener;
import de.uni_koeln.spinfo.arc.ext.nvs.gramm.NVSLexer;
import de.uni_koeln.spinfo.arc.ext.nvs.gramm.NVSParser;
import de.uni_koeln.spinfo.arc.utils.DictUtils;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: franciscomondaca
 * Date: 2/28/14
 * Time: 6:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcessNVS {


    public static String output_data_path = "../nvs/nvs.singleline/output_data/";
    public static String input_data_path = "../nvs/nvs.singleline/input_data/";

    //FROM DAVID, CLEANED
    public static String nvs_1_200 = "../nvs.singleline/input_data/NVS_2_Lines_1_200.txt";
    public static String nvs_201_400 = "../nvs.singleline/input_data/NVS_2_Lines_201_400.txt";
    public static String nvs_401_600 = "../nvs.singleline/input_data/NVS_2_Lines_401_600.txt";
    public static String nvs_601_800 = "../nvs.singleline/input_data/NVS_2_Lines_601_800.txt";
    public static String nvs_801_1000 = "../nvs.singleline/input_data/NVS_2_Lines_801_1000.txt";
    public static String nvs_1001_1218 = "../nvs.singleline/input_data/NVS_2_Lines_1001_1218.txt";


    //FINAL PREPROCESSED
    public static String nvs_1_200_pp = "../nvs.singleline/input_data/NVS_2_Lines_1_200_PP.txt";
    public static String nvs_201_400_pp = "../nvs.singleline/input_data/NVS_2_Lines_201_400_PP.txt";
    public static String nvs_401_600_pp = "../nvs.singleline/input_data/NVS_2_Lines_401_600_PP.txt";
    public static String nvs_601_800_pp = "../nvs.singleline/input_data/NVS_2_Lines_601_800_PP.txt";
    public static String nvs_801_1000_pp = "../nvs.singleline/input_data/NVS_2_Lines_801_1000_PP.txt";
    public static String nvs_1001_1218_pp = "../nvs.singleline/input_data/NVS_2_Lines_1001_1218_PP.txt";

    //ANTLR FILES
    public static String nvs_1_200_antlr = "../nvs.singleline/output_data/nvs_1_200.txt";
    public static String nvs_201_400_antlr = "../nvs.singleline/output_data/nvs_201_400.txt";
    public static String nvs_401_600_antlr = "../nvs.singleline/output_data/nvs_401_600.txt";
    public static String nvs_601_800_antlr = "../nvs.singleline/output_data/nvs_601_800.txt";
    public static String nvs_801_1000_antlr = "../nvs.singleline/output_data/nvs_801_1000.txt";
    public static String nvs_1001_1218_antlr = "../nvs.singleline/output_data/nvs_1001_1218.txt";


    public List<String> cleanNVSFromTXT(String filePath) throws IOException {

        List<String> list = new ArrayList<String>();
        List<String> toDEL = new ArrayList<String>();

        FileInputStream fis = new FileInputStream(filePath);
        InputStreamReader isr = new InputStreamReader(fis, "UTF8");
        LineNumberReader reader = new LineNumberReader(isr);


        Set<String> rz = new HashSet<String>();

        rz.add("I");
        rz.add("V");


        String currentLine = "";

        while ((currentLine = reader.readLine()) != null) {


            if (currentLine.equals("")) {
                currentLine = reader.readLine();

                if (currentLine != null) {

                    //System.out.println(currentLine);
                    String[] currentLineA = currentLine.split(" ");
                    if (currentLineA.length > 2) {

                        if (currentLineA[0].startsWith("-") || currentLineA[0].startsWith("[")
                                || currentLineA[0].startsWith("(") || Character.isDigit(currentLineA[0].charAt(0))
                                || currentLineA[0].startsWith("~")
                                || currentLineA[0].endsWith(";")

                                || currentLineA[0].endsWith(".")
                                || currentLineA[0].endsWith(":")


                                ) {

                            toDEL.add(currentLine);
                            continue;


                        }

                        if (currentLineA[0].endsWith(",")) {

                            if (currentLineA[1].equals("refl")) {

                                addToList(currentLineA, list);
                                continue;
                            } else {
                                toDEL.add(currentLine);
                                continue;
                            }

                        }

                        if (currentLineA[0].length() >= 2) {

                            if (currentLineA[0].charAt(1) == ',' || currentLineA[0].charAt(1) == ')') {
                                toDEL.add(currentLine);
                                continue;

                            }
                        }


                        if (currentLineA[1].endsWith(",")) {


                            if (currentLineA[1].length() >= 2) {


                                if (rz.contains(currentLineA[1].charAt(currentLineA[1].length() - 2))
                                        || currentLineA[1].charAt(0) == '(') {

                                    addToList(currentLineA, list);
                                    continue;
                                } else {
                                    toDEL.add(currentLine);
                                    continue;
                                }
                            }


                        }

                        if (currentLineA[1].endsWith(";")

                                || currentLineA[1].endsWith(".")
                                || currentLineA[1].startsWith("~")
                                || currentLineA[1].endsWith("~")
                                ) {

                            if (currentLineA[1].charAt(0) == '(') {
                                addToList(currentLineA, list);
                                continue;
                            } else {
                                toDEL.add(currentLine);
                                continue;
                            }

                        }


                        addToList(currentLineA, list);
                    }


                }
            }
        }

        DictUtils.printList(toDEL, ProcessNVS.output_data_path, "toDEL");
        return list;

    }


    private void addToList(String[] currentLineA, List<String> list) {

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < currentLineA.length; i++) {


            builder.append(currentLineA[i]);
            builder.append(" ");

        }

        //builder.append("\n");

        list.add(builder.toString());


    }


    public void parseNVS(String file, String newFile, String outputpath) throws IOException {

        ANTLRInputStream input = new ANTLRInputStream(new InputStreamReader(
                new FileInputStream(file), "UTF8"));


        NVSLexer lexer = new NVSLexer(input);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        NVSParser parser = new NVSParser(tokens);

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


    public static class LemmaLoader extends NVSBaseListener implements ParseTreeListener {
        ArrayList<String> lemmas = new ArrayList<String>();
        ArrayList<String> errors = new ArrayList<String>();


        public void exitLemma(NVSParser.LemmaContext ctx) {


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


        public void exitError(NVSParser.ErrorContext ctx) {

            String error = ctx.getText();

            errors.add(error);
            System.out.println("error");


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

}
