package de.uni_koeln.spinfo.arc.ext.vallader.proc.pdftextstream;

import com.snowtide.PDF;
import com.snowtide.pdf.Document;
import com.snowtide.pdf.OutputTarget;
import com.snowtide.pdf.PDFTextStream;
import de.uni_koeln.spinfo.arc.ext.vallader.proc.antlr4.ProcessVallader;

import java.io.*;

/**
 * Created by Andreas on 22.04.2015.
 */
public class PdfXStreamExtractor {

    public static String vallader_input = ProcessVallader.input_data_path + "tscharner-20140715_20140923-20150318.pdf";

	public void extractWithPDFTextStream(String outputFile) throws IOException {

		File pdfFile = new File(vallader_input);
		Document pdf = PDF.open(pdfFile);
		StringWriter buffer = new StringWriter();
		pdf.pipe(new OutputTarget(buffer));
		pdf.close();

		System.out.printf("The text extracted from %s is:", vallader_input);
		System.out.println(buffer);

		try {
			File fileDir = new File(ProcessVallader.output_data_path + "/" + outputFile + ".txt");

			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileDir), "UTF8"));
			out.append(buffer.toString());

			out.flush();
			out.close();

		}
		catch (UnsupportedEncodingException e)
		{
			System.out.println(e.getMessage());
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

}


