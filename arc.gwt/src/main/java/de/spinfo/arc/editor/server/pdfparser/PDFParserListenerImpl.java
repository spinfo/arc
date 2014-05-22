package de.spinfo.arc.editor.server.pdfparser;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.TextPosition;

import de.spinfo.arc.editor.shared.model.Page;
import de.spinfo.arc.editor.shared.model.Rectangle;
import de.spinfo.arc.editor.shared.model.Word;
import de.spinfo.arc.editor.shared.model.WorkingUnit;

public class PDFParserListenerImpl implements IPDFParserListener {
	private static final boolean DEBUG = false;
	private final static String CLASS_NAME = PDFParserListenerImpl.class
			.getSimpleName();
	private final static String LINE_SEPPERATOR = "\n";
	private final static String WORD_SEPPERATOR = " ";

	int lastWordIdx = 0;
	int currentWordIdx = 0;

	private WorkingUnit workingUnit;

	private Page currentPage;

	@Override
	public void onStartDocument(PDDocument pdf) {
		if (DEBUG) {
			System.out.println("-- onStartDocument --");
			System.out.println("Document contains num pages:"
					+ pdf.getNumberOfPages());
		}
		workingUnit = new WorkingUnit(new Date());

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

	@Override
	public void onStartPage(PDPage page, int pageNum) {
		if (DEBUG) {
			System.out.println("-- onStartPage --");
			System.out.println("page num " + pageNum);
		}

		currentPage = new Page(pageNum);

	}

	@Override
	public void onEndPage(PDPage page, int pageNum) {
		if (DEBUG) {
			System.out.println("-- onEndPage --");
			System.out.println("page num " + pageNum);
		}

		currentPage.setRange(lastWordIdx, currentWordIdx);
		workingUnit.appendPage(currentPage);

//		lastWordIdx = currentWordIdx + 1;
		lastWordIdx = currentWordIdx ;

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
	private List<Word> words = new ArrayList<Word>();

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
		// System.out.println(words);
		// System.out.println(words + " || " + numWords);
	}

	private void createAWord(List<TextPosition> tmpWord) {
		Rectangle wordRect = getWordRectangle(tmpWord);
		String wordText = getWordText(tmpWord);

		if (!wordText.isEmpty()) {
//			System.out.println("createAWord!");
			workingUnit.appendWord(new Word(wordText, wordRect));
			// words.add(new Word(wordText, wordRect));
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

	private static Rectangle getWordRectangle(List<TextPosition> tmpWord) {
		if (!tmpWord.isEmpty()) {
			TextPosition firstChar = tmpWord.get(0);
			TextPosition lastChar = tmpWord.get(tmpWord.size() - 1);
			List<Float> heights = new ArrayList<>();

			int x = (int) firstChar.getXDirAdj();
			int y = (int) firstChar.getYDirAdj();
			int width = (int) (lastChar.getXDirAdj() + lastChar
					.getWidthDirAdj());

			for (int h = 0; h < tmpWord.size(); h++) {
				heights.add(tmpWord.get(h).getHeight());
			}

			Collections.sort(heights);
			float height = heights.get(heights.size() - 1);
			// System.err.println(width);
			// System.err.println(heights.get(heights.size()-1));

			// Rectangle wordRect = new Rectangle(x, y, width, height);
			return new Rectangle(x, y, width, (int) height);
		}
		return new Rectangle(-1, -1, -1, -1);
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

	private static Rectangle getTextRect(TextPosition text) {
		// Method[] ms = text.getClass().getDeclaredMethods();
		// System.out.println(text.getClass().getCanonicalName());
		if (text != null) {
			// need to iterate over declared methods, to avoid NullPointer
			// because getYLowerLeftRot(float) is not always defined
			// this is super weird, but its works for now
			// TODO: fix this!
			if (text.getClass().getSimpleName()
					.equals("NormalizedTextPosition")) {
				// System.out.println("x " + text.getXDirAdj());
				int tY = Math.round(text.getYDirAdj());
				int tX = Math.round(text.getX());
				int tW = Math.round(text.getWidthDirAdj());
				int tH = Math.round(text.getHeightDir());
				if (DEBUG) {
					// System.out.println(text.getYDirAdj());
				}
//				Point tP = new Point(tX, tY);
//				Dimension tD = new Dimension(tW, tH);
				
				Rectangle tRect = new Rectangle(tX, tY,tW,tH);
				// System.out.println(tRect);
				return tRect;
			}
		}
		return new Rectangle(-1, -1, -1, -1);
	}

}
