import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by franciscomondaca on 7/21/14.
 */
public class ProcessEichenhoferTest {

    static ProcessEichenhofer processor;



    @BeforeClass
    public static void initialize() {

        processor = new ProcessEichenhofer();


    }


    @Test
    public void testParseEichenhofer() throws IOException {

    processor.parseEichenhofer(ProcessEichenhofer.eich_dir+"20140715_eichenhofer_tags.txt","20140721_antlr4_2", ProcessEichenhofer.eich_dir);

    }

}
