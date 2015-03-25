import de.uni_koeln.spinfo.antlr.nvs.common.DictUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by franciscomondaca on 7/16/14.
 */
public class TarantinoTest {


    public static String eich_input = "../eichenhofer/eichenhofer.data/20140715_pdftextstream.txt";
    public static String eich_dir = "../eichenhofer/eichenhofer.data/";


    private static Tarantino parser;

    @BeforeClass
    public static void init() {
        parser = new Tarantino();

    }


    @Test
    public void getLemmataTest() throws IOException {

        List<String> lemmata = parser.getLemmata(eich_dir + "20140715_eichenhofer_tags.txt");
        List<String> cleanedLemmata = parser.cleanLemmata(lemmata);

        DictUtils.printList(cleanedLemmata, eich_dir, "20140716_cleaned_lemmata_2");

    }

}
