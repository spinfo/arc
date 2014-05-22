package de.spinfo.arc.editor.shared.model3.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.spinfo.arc.editor.shared.model3.description.BaseDescription;
import de.spinfo.arc.editor.shared.model3.modifiable.ModifiableWithParent;
import de.spinfo.arc.editor.shared.model3.modification.payload.RangeUnit;
import de.spinfo.arc.editor.shared.model3.modification.payload.UsableGwtRectangle;
import de.spinfo.arc.editor.shared.model3.modification.payload.impl.UsableGwtRectangleImpl;
import de.spinfo.arc.editor.shared.model3.util.factory.WorkingUnitFactory;
import de.spinfo.arc.editor.shared.model3.util.factory.impl.WorkingUnitFactoryImpl;
import de.spinfo.arc.editor.shared.model3.workingunit.WorkingUnit;


public class PseudoWorkinUnitDataSource {

	private PseudoWorkinUnitDataSource() {}
	
	public static PseudoWorkinUnitDataSource INSTANCE = new PseudoWorkinUnitDataSource();
	
	private static int WORKING_UNIT_DATA_SIZE = 3;
	private static int PAGE_DATA_SIZE = 11;
	private static int WORDS_PER_PAGE_DATA_SIZE = 501;
	private static final String[] DUMMY_POS_MODS = {"V","N","ADJ","F","M","N"};
	private static final String[] DUMMY_FORM_MODS = {"pseudo_form_mod_bla", "pseudo_form_mod_blub", "another_pseudo_form_mod", "pseudo_form_mod_here!"};
	private static UsableGwtRectangle DUMMY_RECT = new UsableGwtRectangleImpl(-1, -1, -1, -1);
	
	private static boolean isCreatingPseudoPosTags = false;
	private static boolean isCreatingPseudoFormMods = false;
	
	private static List<WorkingUnit> workingUnits = new ArrayList<WorkingUnit>();;
	
	
	public List<WorkingUnit> getData() {
		
		if (!workingUnits.isEmpty()) return workingUnits;
		
		System.out.println("creating pseudo data first time...");
		WorkingUnitFactory FAC = WorkingUnitFactoryImpl.INSTANCE;
		
//		WorkingUnit currentWorkingUnit;
		
				for (int wuNum = 0; wuNum < WORKING_UNIT_DATA_SIZE; wuNum++) {
					Date date = new Date();
					StringBuilder titleBuilder = new StringBuilder();
					titleBuilder.append(100-wuNum);
					titleBuilder.append("pseudoWorkingUnit#");
					int ordinal = wuNum;
					int authorId = wuNum;
					WorkingUnit currentWorkingUnit = FAC.createWorkingUnit(FAC.createDescription(ordinal,
							titleBuilder.toString()), date, authorId);
					int lastWordIdx = 0;
					int currentWordIdx = 0;
					// simulates onStartPage
					for (int pageNum = 0; pageNum < PAGE_DATA_SIZE; pageNum++) {

						for (int wordNum = 0; wordNum < WORDS_PER_PAGE_DATA_SIZE; wordNum++) {
							StringBuilder wordTextBuilder = new StringBuilder();
							wordTextBuilder.append("unit<"+ wuNum + ">" + "page<" + pageNum + ">" + "word<" + wordNum + ">");
							
//							WordImpl w = new WordImpl("unit<"+ i + ">" + "page<" + j + ">" + "word<" + k + ">", DUMMY_RECT);
							ModifiableWithParent word = FAC
									.createAndAppendWordToWorkingUnit(
											wordTextBuilder.toString(), DUMMY_RECT, currentWorkingUnit, date,
											authorId);
							
							if (isCreatingPseudoPosTags) {
								String dummyPos = DUMMY_POS_MODS[wordNum % DUMMY_POS_MODS.length];
								FAC.createAndAppendPosModification(dummyPos, word, date, authorId);
//								w.appendPosMod(-1000, DUMMY_POS_MODS[wordNum % DUMMY_POS_MODS.length], new Date());
							}
							if (isCreatingPseudoFormMods) {
								String dummyTextMod = DUMMY_FORM_MODS[wordNum % DUMMY_FORM_MODS.length];
								FAC.createAndAppendTextModification(dummyTextMod, word, date, authorId);
//								w.appendFormMod(-1000, DUMMY_FORM_MODS[wordNum % DUMMY_FORM_MODS.length], new Date());
							}
//							currentWorkingUnit.appendWord(w);
							currentWordIdx++;
						}
//						create a page
						RangeUnit ru = FAC.createRange(lastWordIdx, currentWordIdx-1);
						FAC.createAndAppendPageRangeModification( ru , currentWorkingUnit, FAC.createDescription(pageNum, "page"), date, authorId);
						lastWordIdx = currentWordIdx;
					}
					
//					int endChapter1 = PAGE_DATA_SIZE / 2 * (WORDS_PER_PAGE_DATA_SIZE ) ;
//					
//					RangeUnit chapterRange2 = FAC.createRange(endChapter1, PAGE_DATA_SIZE * WORDS_PER_PAGE_DATA_SIZE - 2000 );
//					BaseDescription bd2 = FAC.createDescription(1, "chapter2");
//					FAC.createAndAppendChapterRangeModification(chapterRange2, currentWorkingUnit, bd2, null, 0);
//					
//					RangeUnit chapterRange = FAC.createRange(0,endChapter1 );
//					BaseDescription bd = FAC.createDescription(0, "chapter1");
//					FAC.createAndAppendChapterRangeModification(chapterRange, currentWorkingUnit, bd, null, 0);
//					
//					RangeUnit chapterRange3 = FAC.createRange(0 + (endChapter1 / 2),(endChapter1 / 2) +  (PAGE_DATA_SIZE * WORDS_PER_PAGE_DATA_SIZE) /2);
//					BaseDescription bd3 = FAC.createDescription(0, "chapter1.5");
//					FAC.createAndAppendChapterRangeModification(chapterRange3, currentWorkingUnit, bd3, null, 0);
					
					RangeUnit chapterRange = FAC.createRange(0,WORDS_PER_PAGE_DATA_SIZE * 2 );
					BaseDescription bd = FAC.createDescription(0, "chapter1");
					FAC.createAndAppendChapterRangeModification(chapterRange, currentWorkingUnit, bd, null, 0);
					
					workingUnits.add(currentWorkingUnit);
				}
		
		return workingUnits;
	}
	
}
