import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import de.uni_koeln.spinfo.antlr.eichenhofer.PreProcess;
import de.uni_koeln.spinfo.arc.utils.DictUtils;

/**
 * Created by franciscomondaca on 6/11/14.
 */
public class PreProcessTest {

    public static String eich_input = "../eichenhofer/eichenhofer.data/20140715_pdftextstream.txt";
    public static String eich_dir = "../eichenhofer/eichenhofer.data/";


    private static PreProcess preprocessor;

    @BeforeClass
    public static void init() {
        preprocessor = new PreProcess();

    }


    @Test
    public void testAddTags() throws IOException {

        preprocessor.addTags(eich_input);

        DictUtils.printList( preprocessor.addTags(eich_input),eich_dir,"20140721_eichenhofer_tags");

    }

}
