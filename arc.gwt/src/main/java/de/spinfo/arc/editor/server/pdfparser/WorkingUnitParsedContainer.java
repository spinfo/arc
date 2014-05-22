package de.spinfo.arc.editor.server.pdfparser;


import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.google.gwt.core.client.GWT;

import de.spinfo.arc.editor.shared.model3.util.DebugHelper;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;
import de.spinfo.arc.editor.shared.model3.workingunit.WorkingUnit;

public class WorkingUnitParsedContainer {

	private WorkingUnitParsedContainer() {};
	
	private static WorkingUnit workingUnit = null;
	
	public static WorkingUnit getData() {
		if (workingUnit != null) {
			DebugHelper.print(WorkingUnitParsedContainer.class, "returning already parsed one!", true);
			return workingUnit;
		}
		
		DebugHelper.print(WorkingUnitParsedContainer.class, "parsing a new Working Unit!", true);
		SuperPDFStripper stripper = null;
		PDDocument  doc = null;
		URL url = null;
//		GWT.log(GWT.getModuleBaseURL()+"data/pdf/Signorelli.pdf");
		try {
			
			/** The Page number where PDF parsing starts */
			int START_PAGE_NUM = 1;
			/** The Page number where PDF parsing ends */
			int END_PAGE_NUM = 1;
			
			int pgDiff = END_PAGE_NUM - START_PAGE_NUM;
//			if (!(pgDiff <= 100)) {
//				
//				
//			}
			/** The path to the source PDF */
//			String PDF_FILE_NAME = "Peer.pdf";
//			String PDF_FILE_NAME = "Signorelli.pdf";
			String PDF_FILE_NAME = "chresto.pdf";
			
			url = ClassLoader.getSystemResource(PDF_FILE_NAME);
			
//			url = ClassLoader.getSystemResource("chresto.pdf");
			doc = PDDocument.load(url.toURI().getPath());
//			 doc = PDDocument.load(PDF_FILE_NAME);
			 stripper = new SuperPDFStripper("UTF-8");
			 
			 PDFParserListener4GWTImpl parserListener = new PDFParserListener4GWTImpl();
			 stripper.addPdfParserListener(parserListener);
			 
			 stripper.startExtracting(doc, START_PAGE_NUM, END_PAGE_NUM);
			 
			 System.out.println(parserListener.getWorkingUnit().getModifications(Types.WORKING_UNIT_PAGE_RANGE));
			 
			 workingUnit = parserListener.getWorkingUnit();
			 

		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		catch ( URISyntaxException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (doc != null)
					doc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		
		return workingUnit;
		
		
	}
}
