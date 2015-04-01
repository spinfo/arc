package de.uni_koeln.spinfo.arc.eichenhofer.pdfbox;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.*;
import java.util.List;

/**
 * Created by franciscomondaca on 6/11/14.
 */
public class ExtractEichenhofer {

	public static String eich_input = "../eichenhofer/eichenhofer.data/20140715_eichenhofer_ocr.pdf";

	public void extractWithPDFBOX(String outputFile) throws IOException {

		PDDocument pd;
		BufferedWriter wr;
		try {
			File input = new File(eich_input);
			File output = new File("../antlr4.parent/eichenhofer.data/"
					+ outputFile + "txt");
			pd = PDDocument.load(input);
			System.out.println(pd.getNumberOfPages());
			System.out.println(pd.isEncrypted());

			PDFTextStripper stripper = new PDFTextStripper();

			wr = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(output)));

			stripper.writeText(pd, wr);
			if (pd != null) {
				pd.close();
			}

			wr.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void extractWithIText(String outputFile) throws IOException {
		PdfReader reader = new PdfReader(eich_input);
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		PrintWriter out = new PrintWriter(new FileOutputStream(
				"../antlr4.parent/eichenhofer.data/" + outputFile + "txt"));
		TextExtractionStrategy strategy;

		RenderFilter filter;

		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
			// strategy = parser.processContent(i, new
			// SimpleTextExtractionStrategy());
			strategy = parser.processContent(i,
					new LocationTextExtractionStrategy());

			out.println(strategy.getResultantText());
		}
		out.flush();
		out.close();
		reader.close();

	}

//	public void extractWithPDFTextStream(String outputFile) throws FileNotFoundException {
//
//		String pdfFilePath = eich_input;
//		PDFTextStream pdfts = new PDFTextStream(pdfFilePath);
//		StringBuilder text = new StringBuilder(1024);
//		pdfts.pipe(new OutputTarget(text));
//		pdfts.close();
//
//		System.out.printf("The text extracted from %s is:", pdfFilePath);
//		System.out.println(text);
//		PrintWriter out = new PrintWriter(new FileOutputStream(
//				"../eichenhofer/eichenhofer.data/" + outputFile + ".txt"));
//
//		out.print(text);
//
//		out.flush();
//		out.close();
//
//	}

	// public void extractBookmarks() throws IOException {
	//
	//
	// PDFTextStream pdfts = new PDFTextStream(eich_input);
	// Bookmark root = pdfts.getBookmarks();
	// if (root == null) {
	// System.out.printf("%s does not contain any bookmarks.", eich_input);
	// } else {
	// for (Bookmark b : (List<Bookmark>) root.getAllDescendants()) {
	// System.out.printf("Bookmark '%s' points at page %s, bounds %s, %s, %s, %s",
	// b.getTitle(), b.getPageNumber(),
	// b.getLeftBound(), b.getBottomBound(),
	// b.getRightBound(), b.getTopBound());
	// System.out.println();
	// }
	// }
	//
	// pdfts.close();
	//
	//
	// }

}
