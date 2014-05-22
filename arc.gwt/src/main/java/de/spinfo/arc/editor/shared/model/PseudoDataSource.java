package de.spinfo.arc.editor.shared.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.view.client.ListDataProvider;


public class PseudoDataSource {

	private static int WORKING_UNIT_DATA_SIZE = 3;
	private static int PAGE_DATA_SIZE = 3;
	private static int WORDS_PER_PAGE_DATA_SIZE = 3;
	private static Rectangle DUMMY_RECT = new Rectangle(-1, -1, -1, -1);

	private static final String[] DUMMY_POS_MODS = {"V","N","ADJ","F","M","N"};
	private static final String[] DUMMY_FORM_MODS = {"pseudo_form_mod_bla", "pseudo_form_mod_blub", "another_pseudo_form_mod", "pseudo_form_mod_here!"};
	private static List<WorkingUnit> workingUnits;

	private static final boolean DEBUG = false;
	private static boolean isCreatingPseudoPosTags = true;
	private static boolean isCreatingPseudoFormMods = true;

	
	private static PseudoDataSource instance;
	
	
	public static List<WorkingUnit> createData(int sizeOfWorkingUnits, int sizeOfPagesPerUnit, int sizeOfWordsPerPage) {
		if (instance == null)
			instance = new PseudoDataSource();
		
		WORKING_UNIT_DATA_SIZE = sizeOfWorkingUnits;
		PAGE_DATA_SIZE = sizeOfPagesPerUnit;
		WORDS_PER_PAGE_DATA_SIZE = sizeOfWordsPerPage;
		
		if (workingUnits == null) {
			
			workingUnits = new ArrayList<WorkingUnit>(WORKING_UNIT_DATA_SIZE);
			GWT.log("creating pseudoData!");
		
		//only create the data if it hasn't been set up yet
//		if (workingUnits.size() < WORKING_UNIT_DATA_SIZE) {
			
			WorkingUnit currentWorkingUnit;
			Page currentPage;

			// simulates onStartDocument
			for (int i = 0; i < WORKING_UNIT_DATA_SIZE; i++) {
				currentWorkingUnit = new WorkingUnit(new Date());
				currentWorkingUnit.setTitle(100-i+"pseudoWorkingUnit#" + i);
				currentWorkingUnit.setId(i);
				int lastWordIdx = 0;
				int currentWordIdx = 0;
				// simulates onStartPage
				for (int j = 0; j < PAGE_DATA_SIZE; j++) {
					currentPage = new Page(j);

					for (int k = 0; k < WORDS_PER_PAGE_DATA_SIZE; k++) {
						Word w = new Word("unit<"+ i + ">" + "page<" + j + ">" + "word<" + k + ">", DUMMY_RECT);
						
						if (isCreatingPseudoPosTags)
							w.appendPosMod(-1000, DUMMY_POS_MODS[k % DUMMY_POS_MODS.length], new Date());
						if (isCreatingPseudoFormMods)
							w.appendFormMod(-1000, DUMMY_FORM_MODS[k % DUMMY_FORM_MODS.length], new Date());
						
						currentWorkingUnit.appendWord(w);
						currentWordIdx++;

					}
					currentPage.setRange(lastWordIdx, currentWordIdx);
//					System.out.println("currentWordIdx " + currentWordIdx);
//					System.out.println("lastWordIdx " + lastWordIdx);
//					System.err.println("set a page from " + lastWordIdx + " to " + (currentWordIdx));
					currentWorkingUnit.appendPage(currentPage);
					lastWordIdx = currentWordIdx;
				}

				workingUnits.add(currentWorkingUnit);
			}
			if (DEBUG)
				System.out.println(workingUnits);
		}
		return workingUnits;
	}
	
	static final int maxData = 2000;
	static final int chunks = 40;
	
	public static void populate(final List<WorkingUnit> theList, final ListDataProvider<WorkingUnit> dataListProvider){
		
//		Scheduler.get().scheduleIncremental(new RepeatingCommand(){
//			int counter = 0;
			
//			@Override
//			public boolean execute() {
//		List<WorkingUnit> li = PseudoDataSource.createData(11, 11, 11);
//		System.out.println(li);
//				theList.addAll(PseudoDataSource.createData(111, 11, 11));
				theList.addAll(workingUnits);
				dataListProvider.refresh();
//				counter += chunks;
//				return (counter<maxData);
//			}
//		});
	}
	
	public static List<WorkingUnit> getWorkingUnits() {
		return workingUnits;
	}

	public static PseudoDataSource getInstance() {
		return instance;
	}
	
	public static void setCreatingPseudoPosTags(boolean isCreatingPseudoPosTags) {
		PseudoDataSource.isCreatingPseudoPosTags = isCreatingPseudoPosTags;
	}

	public static void setCreatingPseudoFormMods(boolean isCreatingPseudoFormMods) {
		PseudoDataSource.isCreatingPseudoFormMods = isCreatingPseudoFormMods;
	}

}
