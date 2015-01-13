package de.uni_koeln.spinfo.arc.editor.server.persistance;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Convenience Class to serialize wortlists to disc.
 * 
 * @author D. Rival
 *
 */
public class FilteredFileAppender {

	private String fn;
	private File file;
	private BufferedWriter writer;
	private static final String NL = "\n";
	private static final String COMMENT = "###";
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private static final Date date = new Date();
	private static final String DATE = COMMENT + " Extraction Date : " + DATE_FORMAT.format(date);
	private static String NAME_OF_EXTRACTOR = null;
	private static String NAME_OF_LEXICON = null ;
	private StringBuilder sBuilder = new StringBuilder();
	
	/**
	 * 
	 * 
	 * @param fn there filename of the target file
	 * @param settings settings object. You can pass null otherwise
	 */
	public FilteredFileAppender(String fn, FilteredFileAppenderSettings settings) {
		super();
		this.fn = fn;
		if (settings != null) {
			NAME_OF_EXTRACTOR = COMMENT +  " name: " +   settings.nameOfExtractor;
			NAME_OF_LEXICON = COMMENT +  " name of workingUnit: " +  settings.nameOfLexicon;
		} else {
			NAME_OF_EXTRACTOR = COMMENT +  " unnamed " ;
			NAME_OF_LEXICON = COMMENT +  "unnamed" ;
		}
	}
	
	public void write(String str) {
		file = new java.io.File(fn);
		try {
			writer = new java.io.BufferedWriter(new java.io.OutputStreamWriter(
					new java.io.FileOutputStream(file, true), "UTF-8"));
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (java.io.FileNotFoundException e) {
			e.printStackTrace();
		} 
		
		try {
			sBuilder.append(NAME_OF_LEXICON);
			sBuilder.append(NL);
			sBuilder.append(DATE);
			sBuilder.append(NL);
			sBuilder.append(NAME_OF_EXTRACTOR);
			sBuilder.append(NL);
			sBuilder.append(COMMENT);
			sBuilder.append(" Extracted lines: " + str.split("\n").length);
			sBuilder.append(NL);
			sBuilder.append(str);
			writer.write(sBuilder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
//	        writer.flush();
	      writer.close();
	  } catch (java.io.IOException e) { 
	 // TODO Auto-generated catch block
	  e.printStackTrace();
	  } 
		
	}

//	public void write(List<List<ILexiconNode>> listOfLines) {
//		file = new java.io.File(fn);
//
//
//		try {
//			writer = new java.io.BufferedWriter(new java.io.OutputStreamWriter(
//					new java.io.FileOutputStream(file, true), "UTF-8"));
//		} catch (java.io.UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (java.io.FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		StringBuilder resbuilder = new StringBuilder();
//		String PAGE = "####################### Page No.: ";
//		int pageCount = 0;
//		for (List<ILexiconNode> nodeList : listOfLines ){
//			pageCount++;
//			resbuilder.append(PAGE);
//			resbuilder.append(pageCount);
//			resbuilder.append("\n");
//			for (ILexiconNode linesOfAPage : nodeList) {
////			resbuilder.append("\n");
////			resbuilder.append("- - - - Page No.: ");
//			if (linesOfAPage.getText().length() > 1) {
//				resbuilder.append(linesOfAPage.getText());
//				resbuilder.append("\n");
//			}
//			}
//		}
//		try {
//			writer.write(resbuilder.toString());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		try {
////	        writer.flush();
//	      writer.close();
//	  } catch (java.io.IOException e) { 
//	 // TODO Auto-generated catch block
//	  e.printStackTrace();
//	  } 
//		
//	}
//	
//	public void write(Map<Integer, List<List<MetaChar>>> pageDict) {
//
//		file = new java.io.File(fn);
//		java.util.Iterator<Entry<Integer, List<List<MetaChar>>>> it = pageDict
//				.entrySet().iterator();
//
//		try {
//			writer = new java.io.BufferedWriter(new java.io.OutputStreamWriter(
//					new java.io.FileOutputStream(file, true), "UTF-8"));
//		} catch (java.io.UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (java.io.FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		StringBuilder resbuilder = new StringBuilder();
//		while (it.hasNext()) {
//			Entry<Integer, List<List<MetaChar>>> entry = it.next();
//			resbuilder.append("\n");
//			resbuilder.append("- - - - Page No.: ");
//			resbuilder.append(entry.getKey());
//			resbuilder.append("\n");
//			resbuilder.append(getFilteredPageContent(entry.getValue()));
//		}
//		try {
//			writer.write(resbuilder.toString());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		try {
////	        writer.flush();
//	      writer.close();
//	  } catch (java.io.IOException e) { 
//	 // TODO Auto-generated catch block
//	  e.printStackTrace();
//	  } 
//	
//
//	}
//
//	private String getFilteredPageContent( List<List<MetaChar>> linesOfAPage) {
//		StringBuilder finalSb = new StringBuilder();
//		
//		for (int i = 0; i < linesOfAPage.size(); i++) {
//			for (int j = 0; j < linesOfAPage.get(i).size(); j++) {
//				if (linesOfAPage.get(i).get(j).getTextRect().getX() < 157) {
////					System.out.println(linesOfAPage.get(i));
//					finalSb.append("\n");
//					finalSb.append(getLine((linesOfAPage.get(i))));
//					if (linesOfAPage.size() - 1 > i+1) {
////						System.out.println(linesOfAPage.get(i).get(j).getTextRect().getX());
//						if (linesOfAPage.get(i).get(j).getTextRect().getX() < 157) 
//						finalSb.append(getLine((linesOfAPage.get(i + 1))));
//						i++;
//						
//					}
//				} else if (linesOfAPage.get(i).get(j).getTextRect().getX() > 320 && linesOfAPage.get(i).get(j).getTextRect().getX() < 323 ) {
//					finalSb.append("\n");
//					finalSb.append(getLine((linesOfAPage.get(i))));
//					if (linesOfAPage.size() - 1 > i+1) {
//						if (!isInRange((int) linesOfAPage.get(i).get(j).getTextRect().getX(), 322, 1000)) {
//						finalSb.append(getLine((linesOfAPage.get(i + 1))));
//						i++;
//						}
//					}
//				}
//			}
//		}
//		return finalSb.toString();
//
//	}
//	
//	public static boolean isInRange(int val, int min1, int max1) {
//		
//		return (min1 <= val && val <= max1) ;  
//		
//	}
//	
//	public static boolean isInDoubleRange(int min1, int max1, int min2, int max2, int val) {
//		
//		return (min1 <= val && val <= max1) || (min2 <= val && val <= max2);  
//		
//	}
//	
//	static StringBuilder sb;
//	final static String ENTRY_DELIM = "- - - - - - - - - - - - - - - - - - - - - - - - -";
//	
//	static String getLine(List<MetaChar> lmc) {
//		sb = new StringBuilder();
//		// sb.append("\n");
//		// sb.append(ENTRY_DELIM);
//		// sb.append("\n");
//
//		for (MetaChar mc : lmc) {
//			sb.append(mc.getText());
//		}
//		sb.append("\n");
//		return sb.toString();
//	}
//	
}
