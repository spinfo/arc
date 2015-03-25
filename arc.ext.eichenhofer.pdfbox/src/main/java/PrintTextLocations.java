

import org.apache.pdfbox.exceptions.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrintTextLocations extends PDFTextStripper {

    public static List wordList = new ArrayList();
    public static int pageNo = 1;


    public PrintTextLocations()
            throws IOException {
        super.setSortByPosition(true);
    }

    public static void main(String[] args)
            throws Exception {
        PDDocument document = null;
        File input = new File(ExtractEichenhofer.eich_input);
        try {

            document = PDDocument.load(input);
            if (document.isEncrypted()) {
                try {
                    document.decrypt("");
                } catch (InvalidPasswordException e) {
                    System.err.println("Error: Document is encrypted with a password.");
                    System.exit(1);
                }
            }


            PrintTextLocations printer = new PrintTextLocations();
            List allPages = document.getDocumentCatalog().getAllPages();

            for (int i = 0; i < 1; i++) {
                PDPage page = (PDPage) allPages.get(i);
                PDStream contents = page.getContents();

                if (contents != null) {
                    printer.processStream(page, page.findResources(), page.getContents().getStream());
                }
                pageNo += 1;
            }
        } finally {
            if (document != null) {
                System.out.println(wordList);
                document.close();
            }
        }
    }

    @Override
    protected void processTextPosition(TextPosition text) {
        System.out.println("String[" + text.getXDirAdj() + ","
                + text.getYDirAdj() + " fs=" + text.getFontSize() + " xscale="
                + text.getXScale() + " height=" + text.getHeightDir() + " space="
                + text.getWidthOfSpace() + " width="
                + text.getWidthDirAdj() + "]" + text.getCharacter()
                + " Y:" + text.getY() + " X: " + text.getX());


    }

}