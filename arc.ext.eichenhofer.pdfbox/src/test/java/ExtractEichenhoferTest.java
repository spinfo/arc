import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by franciscomondaca on 6/11/14.
 */
public class ExtractEichenhoferTest {

    private static ExtractEichenhofer extractor;

    @BeforeClass
    public static void init() {
        extractor = new ExtractEichenhofer();

    }

    @Test
    public void testPDFBOX() {
    }


    @Test
    public void testIText() throws IOException {

    extractor.extractWithIText("20140611_itext_2");

    }

    @Test public void testExtractWithPDTTextStream() throws FileNotFoundException {

        extractor.extractWithPDFTextStream("20140715_pdftextstream");

    }



}
