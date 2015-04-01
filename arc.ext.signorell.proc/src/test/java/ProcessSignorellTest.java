import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import de.uni_koeln.spinfo.arc.antlr.signorell.ProcessSignorell;
import de.uni_koeln.spinfo.arc.utils.DictUtils;

/**
 * Created by franciscomondaca on 6/12/14.
 */
public class ProcessSignorellTest {

    private static ProcessSignorell processSignorell;


    @BeforeClass
    public static void init() {

        processSignorell = new ProcessSignorell();

    }


    @Test
    public void testParseSignorelli() throws IOException {


        processSignorell.parsePeer(ProcessSignorell.signorelli_data + "20140616_signorelli_withtags.txt", ProcessSignorell.signorelli_data, "20140616_signorelli_antlr4_b");


    }




    @Test
    public void testRemoveSlashes() throws IOException {

        List<String> list = DictUtils.cleanTextFile(ProcessSignorell.signorelli_complete);

        DictUtils.printList(list, ProcessSignorell.signorelli_data, "signorelli_cleaned");

    }

    @Test
    public void testEquals() {

        Set<String> names = new HashSet<String>();
        names.add("m/f");
        names.add("rn/f");
        names.add("peter");

        if (!names.contains("m/f") | !names.contains("rn/f")) {
            System.out.println("boi b");
        }


    }

    @Test
    public void testAddTags() throws IOException{


        List<String> list = DictUtils.addTags(ProcessSignorell.signorelli_data+"/signorelli_cleaned.txt");

        DictUtils.printList(list, ProcessSignorell.signorelli_data, "20140616_signorelli_withtags");


    }


}
