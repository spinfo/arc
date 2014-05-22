package de.spinfo.arc.editor.server.pdfparser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.apache.pdfbox.util.TextPosition;

import de.spinfo.arc.editor.shared.model3.modifiable.ModifiableWithParent;
import de.spinfo.arc.editor.shared.model3.modification.payload.RangeUnit;
import de.spinfo.arc.editor.shared.model3.modification.payload.UsableGwtRectangle;
import de.spinfo.arc.editor.shared.model3.modification.payload.impl.UsableGwtRectangleImpl;
import de.spinfo.arc.editor.shared.model3.util.WorkingUnitHelper;
import de.spinfo.arc.editor.shared.model3.util.factory.WorkingUnitFactory;
import de.spinfo.arc.editor.shared.model3.util.factory.impl.WorkingUnitFactoryImpl;
import de.spinfo.arc.editor.shared.model3.workingunit.WorkingUnit;


public class PDFParserListener4GWTImpl implements IPDFParserListener {
	private static final boolean DEBUG = false;
	private final static String CLASS_NAME = PDFParserListener4GWTImpl.class
			.getSimpleName();
	private final static String LINE_SEPPERATOR = "\n";
	private final static String WORD_SEPPERATOR = " ";

	int lastWordIdx = 0;
	int currentWordIdx = 0;

	private WorkingUnit workingUnit;

	Date date = new Date();
	WorkingUnitFactory FAC = WorkingUnitFactoryImpl.INSTANCE;
	
	@Override
	public void onStartDocument(PDDocument pdf) {
		if (DEBUG) {
			System.out.println("-- onStartDocument --");
			System.out.println("Document contains num pages:"
					+ pdf.getNumberOfPages());
		}
		workingUnit = FAC.createWorkingUnit(FAC.createDescription(0,
				"parsedOne"), date, -1);
		

	}

	@Override
	public void onEndDocument(PDDocument pdf) {
		if (DEBUG)
			System.out.println("-- onEndDocument --");

//		workingUnit.printPageNum(2);
//		System.out.println(workingUnit);
//		System.out.println(workingUnit.pageToStringAt(1));
//		System.out.println(workingUnit.firstAndLastWordOfPageToStringAt(0));
	}
	
	int currentPageNum = 0;
	@Override
	public void onStartPage(PDPage page, int pageNum) {
		if (DEBUG) {
			System.out.println("-- onStartPage --");
			System.out.println("page num " + pageNum);
		}
		currentPageNum = pageNum;
		
		int i =1;
		PDResources resources = page.getResources();
		Map pageImages = null;
		try {
			pageImages = resources.getImages();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (pageImages != null) { 
            Iterator imageIter = pageImages.keySet().iterator();
            while (imageIter.hasNext()) {
                String key = (String) imageIter.next();
                PDXObjectImage image = (PDXObjectImage) pageImages.get(key);
                System.out.println("WIDTH in Parser: " + image.getWidth());
                System.out.println("HEIGHT in Parser: " + image.getHeight());
                image.getHeight();
                System.out.println(this.getClass().getSimpleName()+ " -- found image on Page: " + pageNum + " -> " +image.toString());
//                image.write2file("C:\\Users\\564864\\Desktop\\Java\\helloworld\\images\\" + i+"");
                i ++;
            }
        }
//		currentPage = new Page(pageNum);

	}

	@Override
	public void onEndPage(PDPage page, int pageNum) {
		if (DEBUG) {
			System.out.println("-- onEndPage --");
			System.out.println("page num " + pageNum);
		}

//		currentPage.setRange(lastWordIdx, currentWordIdx);
//		workingUnit.appendPage(currentPage);

		RangeUnit ru = FAC.createRange(lastWordIdx, currentWordIdx - 1);
		FAC.createAndAppendPageRangeModification( ru , workingUnit, FAC.createDescription(pageNum - 1, "page"), date, -1);
		lastWordIdx = currentWordIdx;

	}

	int articleCount = 0;

	@Override
	public void onStartArticle(boolean isltr, int numArticles) {
		if (DEBUG)
			System.out.println("-- onStartArticle num: " + articleCount
					+ " of total: " + numArticles);

	}

	@Override
	public void onEndArticle() {
		if (DEBUG)
			System.out.println("-- onEndArticle --");
	}

	@Override
	public void onLineWritten(List<TextPosition> line) {
		if (DEBUG)
			System.out.println("-- onLineWritten --");

		if (!line.isEmpty()) {
			setUpWords(line);
		}

	}

	private static String WS = " ";
	private static String NL = "\n";
//	private List<Word> words = new ArrayList<Word>();
	
	int currentLineNum = 0;
	int currentLineLastWordIdx = 0;
	private void setUpWords(List<TextPosition> line) {
		List<TextPosition> tmpWord = new ArrayList<TextPosition>();
		String lastChar = "";
		for (int i = 0; i < line.size(); i++) {

			if (line.get(i).getCharacter() != null) {
				if (!line.get(i).getCharacter().equals(WS)) {
					tmpWord.add(line.get(i));
					lastChar = line.get(i).getCharacter();
				} else {
					if (!lastChar.equals(WS)) {

						createAWord(tmpWord);

						tmpWord = new ArrayList<TextPosition>();
						lastChar = WS;
					}
				}
				// Wenn es sich um das Ende einer Zeile handelt
				if (line.size() - 1 == i) {
					createAWord(tmpWord);

					tmpWord = new ArrayList<TextPosition>();
				}
			}
		}
		currentLineNum++;
		RangeUnit ru = FAC.createRange(currentLineLastWordIdx, currentWordIdx - 1);
		FAC.createAndAppendLineRangeModification( ru , workingUnit, FAC.createDescription(currentLineNum, "line"), date, -1);
		currentLineLastWordIdx = currentWordIdx;
		// System.out.println(words);
		// System.out.println(words + " || " + numWords);
	}

	private void createAWord(List<TextPosition> tmpWord) {
		UsableGwtRectangle wordRect = getWordRectangle(tmpWord);
		String wordText = getWordText(tmpWord);

		if (!wordText.isEmpty()) {
//			System.out.println("createAWord!");
//			workingUnit.appendWord(new Word(wordText, wordRect));
			ModifiableWithParent word = FAC
					.createAndAppendWordToWorkingUnit(
							wordText, wordRect, workingUnit, date,
							-1);
			WorkingUnitFactoryImpl.INSTANCE.createAndAppendPosModification("V", word, date, -1);
			currentWordIdx++;
		}
	}

	private static String getWordText(List<TextPosition> tmpWord) {
		StringBuilder wordBuilder = new StringBuilder();

		for (TextPosition tp : tmpWord) {
			wordBuilder.append(tp.getCharacter());
		}

		return wordBuilder.toString();
	}

	private static UsableGwtRectangle getWordRectangle(List<TextPosition> tmpWord) {
		if (!tmpWord.isEmpty()) {
			TextPosition firstChar = tmpWord.get(0);
			TextPosition lastChar = tmpWord.get(tmpWord.size() - 1);
			List<Float> heights = new ArrayList<>();
			
//			System.out.println("firstChar.getXScale() " + firstChar.getXScale());
//			System.out.println("firstChar.getYScale() " + firstChar.getYScale());
			
			int x = (int) firstChar.getXDirAdj();
			int y = (int) firstChar.getYDirAdj();
			int width = x + (int) (lastChar.getXDirAdj() + lastChar
					.getWidthDirAdj());

			for (int h = 0; h < tmpWord.size(); h++) {
				heights.add(tmpWord.get(h).getHeight());
			}

			Collections.sort(heights);
			float height = heights.get(heights.size() - 1);
			// System.err.println(width);
			// System.err.println(heights.get(heights.size()-1));

			// Rectangle wordRect = new Rectangle(x, y, width, height);
			return new UsableGwtRectangleImpl(x, y, width, (int) height);
		}
		return new UsableGwtRectangleImpl(-1, -1, -1, -1);
	}

	
	public WorkingUnit getWorkingUnit() {
		return workingUnit;
	}


	private static boolean isBold(String baseFontName) {
		String name = baseFontName.toLowerCase();
		if (name.indexOf("bold") > -1) {
			return true;
		}

		return false;
	}

	private static boolean isItalic(String baseFontName) {
		String name = baseFontName.toLowerCase();
		// oblique is the same as italic
		if (name.indexOf("italic") > -1 || name.indexOf("oblique") > -1) {
			return true;
		}
		return false;
	}

	
}
