package de.spinfo.arc.editor.server.pdfparser;

import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;

/**
 * Classes which implement this interface's methods will be called by
 *<br>{@link SuperPDFStripper SuperPDFStripper} after registering.
 * 
 * @author D. Rival
 *
 */
public interface IPDFParserListener {
	
	public void onStartDocument(PDDocument pdf);
	
	public void onEndDocument(PDDocument pdf);
	
	public void onStartPage(PDPage page, int pageNum);
	
	public void onEndPage(PDPage page, int pageNum);
	
	public void onStartArticle(boolean isltr, int numArticles);
	
	public void onEndArticle();
	
	public void onLineWritten(List<TextPosition> line);
}
