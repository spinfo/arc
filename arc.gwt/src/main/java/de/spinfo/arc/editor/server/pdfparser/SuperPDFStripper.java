package de.spinfo.arc.editor.server.pdfparser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;

import de.spinfo.arc.editor.server.pdfparser.temp.PDFTextStripper2;


/**
 * Class for extracting content of a PDF-File. This implementation is a subclass
 * <br>of the the Apache {@link PDFTextStripper PDFTextStripper} and supports 
 * <br> registered listeners of type {@link IPDFParserListener IPDFParserListener}
 * <br> in order to get access to the extracted information.
 * <br> After registering a listener with {@link  #addPdfParserListener} the extraction
 * <br> can be started with {@link  #startExtracting(PDDocument doc, int startPageNum, int endPageNum )}
 * 
 * @author D. Rival
 */
public class SuperPDFStripper extends PDFTextStripper2 {
	
	
	public SuperPDFStripper() throws IOException {
		super();
	}

	public SuperPDFStripper(Properties props) throws IOException {
		super(props);
	}

	public SuperPDFStripper(String encoding) throws IOException {
		super(encoding);
	}
	
	/**
	 * This method starts the extraction/parsing of the PDF
	 * <br>To obtain the results of this process you should pass 
	 * <br>an instance of {@link IPDFParserListener IPDFParserListener}
	 * <br>to an Instance of {@link SuperPDFStripper SuperPDFStripper} 
	 * <br>via {@link #addPdfParserListener}
	 * 
	 * @TODO fix this method to accept encrypted docs as well
	 * 
	 * @param doc the document to parse/extract
	 * @param startPageNum the page number where parsing starts
	 * @param endPageNum the page number where parsing ends
	 */
	public void startExtracting(PDDocument doc, int startPageNum, int endPageNum){
		try {
			setStartPage(startPageNum );
			setEndPage(endPageNum);
			setShouldSeparateByBeads(true);
//			setSortByPosition(true);
			setLineSeparator("\n");
			// start processing
//			System.out.println(getText(doc));
			getText(doc);
		} catch (IOException e) {
			System.err.println("The doc is encrypted or it'S state is not valid!");
//			e.printStackTrace();
		}
	}
	
	// Attach, remove listeners
	private List<IPDFParserListener> pdfParserListeners;
	
	public  <T extends IPDFParserListener> void addPdfParserListener(T listener) {
		if (pdfParserListeners != null)
			this.pdfParserListeners.add(listener);
		else {
			this.pdfParserListeners = new ArrayList<>(1);
			this.pdfParserListeners.add(listener);
		}
	}
	
	public  <T extends IPDFParserListener> void removeWriteLineListener(T listener) {
		if (this.pdfParserListeners != null && this.pdfParserListeners.contains(listener)) {
			pdfParserListeners.remove(listener);
		if (pdfParserListeners.isEmpty()) {
				pdfParserListeners = null;
			}
		}
	}
	
	/*
	 * Here the listeners get called. Note that the redundant NULL chacks are in place
	 * becaus I plan to supports specific listeners. In this implementation on listener 
	 * for all cases is uses.
	 * 
	 * @TODO Make specific listeners
	 * 
	 * (non-Javadoc)
	 * @see org.apache.pdfbox.util.PDFTextStripper#startDocument(org.apache.pdfbox.pdmodel.PDDocument)
	 */
	@Override
	protected void startDocument(PDDocument pdf) throws IOException {
		super.startDocument(pdf);
		if (pdfParserListeners != null ) {
			for (IPDFParserListener listener : pdfParserListeners)
				listener.onStartDocument(pdf);
		}
	}
	
	
	@Override
	protected void endDocument(PDDocument pdf) throws IOException {
//		super.endDocument(pdf);
		if (pdfParserListeners != null ) {
			for (IPDFParserListener listener : pdfParserListeners)
				listener.onEndDocument(pdf);
		}
	}

	
	@Override
	protected void startPage(PDPage page) throws IOException {
		super.startPage(page);
		if (pdfParserListeners != null ) {
			for (IPDFParserListener listener : pdfParserListeners)
				listener.onStartPage(page, getCurrentPageNo());
		}
//		System.out.println("processing page num " + getCurrentPageNo());
	}
	
	
	@Override
	protected void endPage(PDPage page) throws IOException {
		super.endPage(page);
		if (pdfParserListeners != null ) {
			for (IPDFParserListener listener : pdfParserListeners)
				listener.onEndPage(page, getCurrentPageNo());
		}
	}

	
	@Override
	protected void startArticle(boolean isltr) throws IOException {
		super.startArticle(isltr);
		if (pdfParserListeners != null ) {
			for (IPDFParserListener listener : pdfParserListeners)
				listener.onStartArticle(isltr, charactersByArticle.size());
		}
	}
	
	@Override
	protected void endArticle() throws IOException {
		super.endArticle();
		if (pdfParserListeners != null ) {
			for (IPDFParserListener listener : pdfParserListeners)
				listener.onEndArticle();
		}
	}
	
	@Override
	protected void writeLine(List<TextPosition> line) throws IOException {
		super.writeLine(line);
		if (pdfParserListeners != null ) {
			for (IPDFParserListener listener : pdfParserListeners)
				listener.onLineWritten(line);
		}
	}

}
