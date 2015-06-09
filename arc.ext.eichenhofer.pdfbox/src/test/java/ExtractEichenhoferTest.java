import org.junit.BeforeClass;
import org.junit.Test;

import de.uni_koeln.spinfo.arc.eichenhofer.pdfbox.ExtractEichenhofer;

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

    
    @Test
    public void testExtractWithPDTTextStream() throws FileNotFoundException {
    	// FIXME: There are missing libs, e.g. for class "PDFTextStream"
        extractor.extractWithPDFTextStream("20150421_vallader");

    }



}
