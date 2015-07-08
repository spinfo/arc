import de.uni_koeln.spinfo.arc.ext.puter.proc.antlr4.ProcessPuter;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;


/**
 * Created by Andreas on 21.04.2015.
 */
public class ProcessPuter_Test {

    static ProcessPuter processor;

    @BeforeClass
    public static void initialize() {

        processor = new ProcessPuter();

    }


    @Test
    public void testExtractWF() throws ParseException, IOException {

        processor.extractionWorkflow();


    }


}
