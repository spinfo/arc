import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import de.uni_koeln.spinfo.arc.antlr.nvs.ProcessNVS;
import de.uni_koeln.spinfo.arc.utils.DictUtils;


public class ProcessNVS_Test {


    static ProcessNVS processor;


    @BeforeClass
    public static void initialize() {

        processor = new ProcessNVS();


    }


    @Test
    public void testCleanNVSFromTXT() throws IOException {
        List<String> list = processor.cleanNVSFromTXT("../nvs.singleline/input_data/decurtins-20131201_20140805_20140805.txt");

        DictUtils.printList(list, ProcessNVS.output_data_path, "decurtins-20131201_20140805_20140805_20140806");

        List<String> withTags = DictUtils.addTags("../nvs.singleline/output_data/decurtins-20131201_20140805_20140805_20140806.txt");

        DictUtils.printList(withTags, ProcessNVS.output_data_path, "decurtins-20131201_20140805_20140805_20140806_tags");


    }


    @Test
    public void testParsePeer() throws IOException {


        //processor.parseNVS("../antlr/test/nvs.wholeentry/input_data/raw_cleaned.txt", "nvs_raw_parsed");
        processor.parseNVS("../antlr4.parent/nvs.singleline/input_data/peer_tags.txt", "peer_parsed", processor.output_data_path);

    }

    @Test
    public void testParseNVS2() throws IOException {
        processor.parseNVS(ProcessNVS.output_data_path + "decurtins-20131201_20140805_20140805_20140806_tags.txt", "decurtins-20131201_20140805_20140805_20140806_antlr", ProcessNVS.output_data_path);
    }


    @Test
    public void testParseNVS() throws IOException {


        for (int i = 0; i <= 6; i++) {
            switch (i) {

                case 1:
                    processor.parseNVS(ProcessNVS.nvs_1_200_pp, "nvs_1_200", ProcessNVS.output_data_path);
                    break;
                case 2:
                    processor.parseNVS(ProcessNVS.nvs_201_400_pp, "nvs_201_400", ProcessNVS.output_data_path);
                    break;
                case 3:
                    processor.parseNVS(ProcessNVS.nvs_401_600_pp, "nvs_401_600", ProcessNVS.output_data_path);
                    break;
                case 4:
                    processor.parseNVS(ProcessNVS.nvs_601_800_pp, "nvs_601_800", ProcessNVS.output_data_path);
                    break;
                case 5:
                    processor.parseNVS(ProcessNVS.nvs_801_1000_pp, "nvs_801_1000", ProcessNVS.output_data_path);
                    break;
                case 6:
                    processor.parseNVS(ProcessNVS.nvs_1001_1218_pp, "nvs_1001_1218", ProcessNVS.output_data_path);
                    break;

                default:
                    break;


            }
        }


    }


    @Test
    public void testLemmasFromNVS() throws IOException {


        int i = 6;


        switch (i) {

            case 1:
                DictUtils.printList(DictUtils.lemmasfromNVS(ProcessNVS.nvs_1_200_pp), ProcessNVS.output_data_path, "1_200_lemmas");
                break;
            case 2:
                DictUtils.printList(DictUtils.lemmasfromNVS(ProcessNVS.nvs_201_400_pp), ProcessNVS.output_data_path, "201_400_lemmas");
                break;
            case 3:
                DictUtils.printList(DictUtils.lemmasfromNVS(ProcessNVS.nvs_401_600_pp), ProcessNVS.output_data_path, "401_600_lemmas");
                break;
            case 4:
                DictUtils.printList(DictUtils.lemmasfromNVS(ProcessNVS.nvs_601_800_pp), ProcessNVS.output_data_path, "601_800_lemmas");
                break;
            case 5:
                DictUtils.printList(DictUtils.lemmasfromNVS(ProcessNVS.nvs_801_1000_pp), ProcessNVS.output_data_path, "801_1000_lemmas");
                break;
            case 6:
                DictUtils.printList(DictUtils.lemmasfromNVS(ProcessNVS.nvs_1001_1218_pp), ProcessNVS.output_data_path, "1001_1218_lemmas");
                break;

            default:
                break;


        }


    }


    @Test
    public void getDiffList() throws IOException {

        ArrayList<String> diffList;

        int i = 1;


        switch (i) {

            case 1:
                diffList = (ArrayList<String>) DictUtils.diffLemmasNVS(DictUtils.lemmasfromNVS(ProcessNVS.nvs_1_200_pp), DictUtils.lemmasfromAntlrList(ProcessNVS.nvs_1_200_antlr));
                DictUtils.printList(diffList, ProcessNVS.output_data_path, "diffList_1_200");
                break;
            case 2:
                diffList = (ArrayList<String>) DictUtils.diffLemmasNVS(DictUtils.lemmasfromNVS(ProcessNVS.nvs_201_400_pp), DictUtils.lemmasfromAntlrList(ProcessNVS.nvs_201_400_antlr));
                DictUtils.printList(diffList, ProcessNVS.output_data_path, "diffList_201_400");

                break;
            case 3:
                diffList = (ArrayList<String>) DictUtils.diffLemmasNVS(DictUtils.lemmasfromNVS(ProcessNVS.nvs_401_600_pp), DictUtils.lemmasfromAntlrList(ProcessNVS.nvs_401_600_antlr));
                DictUtils.printList(diffList, ProcessNVS.output_data_path, "diffList_401_600");

                break;
            case 4:
                diffList = (ArrayList<String>) DictUtils.diffLemmasNVS(DictUtils.lemmasfromNVS(ProcessNVS.nvs_601_800_pp), DictUtils.lemmasfromAntlrList(ProcessNVS.nvs_601_800_antlr));
                DictUtils.printList(diffList, ProcessNVS.output_data_path, "diffList_601_800");

                break;
            case 5:
                diffList = (ArrayList<String>) DictUtils.diffLemmasNVS(DictUtils.lemmasfromNVS(ProcessNVS.nvs_801_1000_pp), DictUtils.lemmasfromAntlrList(ProcessNVS.nvs_801_1000_antlr));
                DictUtils.printList(diffList, ProcessNVS.output_data_path, "diffList_801_1000");

                break;
            case 6:
                diffList = (ArrayList<String>) DictUtils.diffLemmasNVS(DictUtils.lemmasfromNVS(ProcessNVS.nvs_1001_1218_pp), DictUtils.lemmasfromAntlrList(ProcessNVS.nvs_1001_1218_antlr));
                DictUtils.printList(diffList, ProcessNVS.output_data_path, "diffList_1001_1218");
                ;
                break;

            default:
                break;


        }


    }


    @Test
    public void testRemoveUnclosedParenthesis() throws IOException {

        DictUtils.removeUnclosedParentheses(ProcessNVS.nvs_1_200, ProcessNVS.output_data_path, "NVS_2_Lines_1_200_PP");


        int i = 6;


        switch (i) {

            case 1:
                DictUtils.removeUnclosedParentheses(ProcessNVS.nvs_1_200, ProcessNVS.output_data_path, "NVS_2_Lines_1_200_PP");
                break;
            case 2:
                DictUtils.removeUnclosedParentheses(ProcessNVS.nvs_201_400, ProcessNVS.output_data_path, "NVS_2_Lines_201_400_PP");

                break;
            case 3:
                DictUtils.removeUnclosedParentheses(ProcessNVS.nvs_401_600, ProcessNVS.output_data_path, "NVS_2_Lines_401_600_PP");
                break;
            case 4:
                DictUtils.removeUnclosedParentheses(ProcessNVS.nvs_601_800, ProcessNVS.output_data_path, "NVS_2_Lines_601_800_PP");
                break;
            case 5:
                DictUtils.removeUnclosedParentheses(ProcessNVS.nvs_801_1000, ProcessNVS.output_data_path, "NVS_2_Lines_801_1000_PP");
                break;
            case 6:
                DictUtils.removeUnclosedParentheses(ProcessNVS.nvs_1001_1218, ProcessNVS.output_data_path, "NVS_2_Lines_1001_1218_PP");
                break;

            default:
                break;


        }


    }


    @Test
    public void joinLines() throws IOException {


        ArrayList<String> lines = new ArrayList<String>();

        lines = (ArrayList<String>) DictUtils.joinLines("../antlr/test/nvs.singleline/output_data/chopped_lists", lines);


        Collections.sort(lines);

        DictUtils.printList(lines, processor.output_data_path, "nvs_singleline_2");

    }


}
