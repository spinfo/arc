package de.spinfo.arc.editor.shared.model3.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.spinfo.arc.editor.client.cactus.comparators.ModificationRangeComparator;
import de.spinfo.arc.editor.client.cactus.comparators.ModificationRangeListComparator;
import de.spinfo.arc.editor.shared.model3.modifiable.ModifiableWithParent;
import de.spinfo.arc.editor.shared.model3.modification.Modification;
import de.spinfo.arc.editor.shared.model3.modification.ModificationRange;
import de.spinfo.arc.editor.shared.model3.modification.ModificationString;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;
import de.spinfo.arc.editor.shared.model3.workingunit.WorkingUnit;


public class WorkingUnitHelper {

	private WorkingUnitHelper() {};
	
	public List<Integer> getPagesNumsWordIsContaining (ModifiableWithParent modifiableWithParentToLookFor, WorkingUnit workingUnit, boolean isCaseSensitive) {
		List<Integer> toReturn = new LinkedList<>();
		Types typeToLookFor = Types.TEXT_MOD;
		String textToLookFor = "";
		if (modifiableWithParentToLookFor.getLastModificationOf(typeToLookFor) instanceof ModificationString)
			textToLookFor = ((ModificationString) modifiableWithParentToLookFor.getLastModificationOf(typeToLookFor)).getPayload();
		// return an empty list is better than null
		if (textToLookFor.equals("")) return toReturn;
		else return getPagesNumsWordIsContaining (textToLookFor, workingUnit, true);
	}
	
	public List<Integer> getPagesNumsWordIsContaining (String textToLookFor, WorkingUnit workingUnit, boolean isCaseSensitive) {
		List<Integer> toReturn = new LinkedList<>();
		Types typeToLookFor = Types.TEXT_MOD;
		
		List<ModificationRange> allPageRanges = getAllPageRanges(workingUnit);
			for (int i = 0; i < allPageRanges.size(); i++) {
				ModificationRange pageRange = allPageRanges.get(i);
				for (int j = pageRange.getStart(); j <= pageRange.getEnd(); j++) {
						String currentWordOfWu = "";
					if (workingUnit.getModifiables().get(j).getLastModificationOf(typeToLookFor) instanceof ModificationString) 
						currentWordOfWu = ((ModificationString)workingUnit.getModifiables().get(j).getLastModificationOf(typeToLookFor)).getPayload();
					// just interested in the StringModifications
					else continue;
					if (isCaseSensitive)
						if (textToLookFor.equals(currentWordOfWu)) {
							
							toReturn.add(i);
						}
					else
						if (textToLookFor.equalsIgnoreCase(currentWordOfWu)) {
							
							toReturn.add(i);
						}
				}
			}
			return toReturn;
		}
	
	public static List<ModificationRange> getAllPageRanges(WorkingUnit workingUnit) {
		return  getAllRangesOfType(Types.WORKING_UNIT_PAGE_RANGE, workingUnit);
	}
	
	public static List<ModificationRange> getAllChapterRanges(WorkingUnit workingUnit) {
		return getAllRangesOfType(Types.WORKING_UNIT_CHAPTER_RANGE, workingUnit);
	}
	
	/**
	 * Get all ModificationRanges of a Working unit of a specific type. F.i. if the type
	 * Types.WORKING_UNIT_CHAPTER_RANGE is passed a List with all Modifications of this type of a WorkingUnit are returned
	 * 
	 * 
	 * @param typeToLookFor the enum type which is the key for ModificationRanges
	 * @param workingUnit the working unit where the ModificationRanges are to be retrieved of
	 * @return
	 */
	public static List<ModificationRange> getAllRangesOfType(Types typeToLookFor, WorkingUnit workingUnit) {
		System.out.println("TYPE: " + typeToLookFor.toString());
		List<Modification> mods = workingUnit.getModificationMap().get(typeToLookFor);
		List<ModificationRange> toReturn = castModificationsToRangesOrEmpty(mods);
//		List<ModificationRange> toReturn = new LinkedList<>();
		
;		if (!toReturn.isEmpty())
			Collections.sort(toReturn, ModificationRangeComparator.INSTANCE);
		
		return  toReturn;
	}
	/**
	 * Returns a Modifiable (in most cases a word is meant by this) at a specific index out of
	 * a working unit. 
	 * @param index the index of Modifiable-Element of the the List of Modifiables of a working unit
	 * @param workingUnit the working unit the Modifiable should be retrieved from
	 * @return the Modifiable which is retrieved at a given index
	 */
	public static ModifiableWithParent getWordAt(int index, WorkingUnit workingUnit){
		return workingUnit.getModifiables().get(index);
	}
	
	public static ModifiableWithParent getFirstWordOfPage(int pageNum, WorkingUnit workingUnit) {
		return getWordsOfPage(pageNum, workingUnit).get(0);
	}
	
	public static ModifiableWithParent getLastWordOfPage(int pageNum, WorkingUnit workingUnit) {
		List<ModifiableWithParent> words = getWordsOfPage( pageNum , workingUnit);
		return words.get(words.size()-1);
	}
	
	/**
	 * Convenience Method which retireves all Modifiables of a given Types.WORKING_UNIT_PAGE_RANGE 
	 * at a given index (pagenum). the Pagenum is the index of the List<ModificationRange> which are saved as Value
	 * for the key Types.WORKING_UNIT_PAGE_RANGE in the WorkingUnits ModificationMap. Out of the Ranges in this list the 
	 * regarding Modifiables (mostly words are meant by this) are retrieved as sublist of all Words of the WorkingUnit
	 * 
	 * All in all this Method is good to retrieve all words by a given Page-index
	 * 
	 * @param pageNum the index of the List of PageRanges aka the PageNum zero-based
	 * @param workingUnit the working unit the page ranges should be retrieved of
	 * @return the List of Modifables which embody the words of a specific Page in a WorkingUnit
	 */
	public static List<ModifiableWithParent> getWordsOfPage(int pageNum, WorkingUnit workingUnit) {
		Types typeToLookFor = Types.WORKING_UNIT_PAGE_RANGE;
		ModificationRange wordRangeOfAPageRange = (ModificationRange) workingUnit.getModificationMap().get(typeToLookFor).get(pageNum);
		return getSublistOfWordsByRangeUnit(wordRangeOfAPageRange, workingUnit);
	}
	
	
	
	/**
	 * takes a modification range (e.g. a chapterRangeModification) and retrieves the the containing Modifications of the given type
	 * (f.i. WORKING_UNIT_PAGE_RANGE). THis is good for retrieving all f.i. all pages of a chapter 
	 * 
	 * @param modificationRange the range which retrieves other Ranges which are contained in it
	 * @param workingUnit
	 * @return the List with the modification ranges contained in the ModificationRanged passed in as param
	 */
	public static List<ModificationRange> getSublistOfModificationRangesByOther(ModificationRange modificationRange, Types type, WorkingUnit workingUnit) {
		List<ModificationRange> toReturn = new LinkedList<>(); 
		List<ModificationRange> retrievedMods = castModificationsToRangesOrEmpty(workingUnit.getModificationMap().get(type));
		
		for (Iterator<ModificationRange> iterator = retrievedMods.iterator(); iterator.hasNext();) {
			ModificationRange mr = (ModificationRange) iterator
					.next();
			if (modificationRange.containsRange(mr))
				toReturn.add(mr);
//			if (modificationRange.equals(mr))
//					toReturn.add(mr);
			
		}
//		for(ModificationRange mr : retrievedMods) {
//			if (modificationRange.containsRange(mr))
//				toReturn.add(mr);
//		}
		
		if (!toReturn.isEmpty())
			Collections.sort(toReturn, ModificationRangeComparator.INSTANCE);
		
		return toReturn;
	}
	
	/**
	 * Takes a List of ModificationRanges and retrieves a 2-dimensional list where the first dimension is the 
	 * ModificationRange which are are the other Ranges are retrieved for. The second dimension are the respective
	 * 'sub-ranges' which are encapsulated by this range. F.i. is a List od ModificationRanges is passed in which are all
	 * are of type Types.WORKING_UNIT_CHAPTER_RANGE the returned List<List<ModificationRange>> contain for each chapter the
	 * the type looked for (f.i. Types.WORKING_UNIT_PAGE_RANGE returns all the page ranges for the given chapterRange-Range)
	 * 
	 * @param modificationRanges the list of Ranges other containing ranges should be looked for
	 * @param type the type of ModificationRanges which are to be retrieved as containing in the ranges
	 * @param workingUnit
	 * @return
	 */
	public static List<List<ModificationRange>> getSublistsOfModificationRangesByOther(List<ModificationRange> modificationRanges, Types type, WorkingUnit workingUnit) {
		List<List<ModificationRange>> toReturn = new LinkedList<List<ModificationRange>>();
		
		for (ModificationRange modificationRange : modificationRanges) {
			List<ModificationRange> retrievedOthers = getSublistOfModificationRangesByOther(modificationRange, type, workingUnit);
			
			if (!retrievedOthers.isEmpty()) {
				
				toReturn.add(retrievedOthers); 
			}
		}
		
		if (!toReturn.isEmpty())
			Collections.sort(toReturn, ModificationRangeListComparator.INSTANCE);
		
		return toReturn;
	}
	
	/**
	 * Takes a list of the base Type Modification and if they are instances of ModificationRange they'll get
	 * casted to a List of Type ModificationRange if not an empty list is returned
	 * 
	 * @param modsToCast
	 * @return
	 */
	public static List<ModificationRange> castModificationsToRangesOrEmpty(List<Modification> modsToCast) {
		List<ModificationRange> toReturn = new LinkedList<>(); 
		if(modsToCast != null) {
		for (Iterator<Modification> iterator = modsToCast.iterator(); iterator.hasNext();) {
			ModificationRange modificationRange = (ModificationRange) iterator.next();
			if (modificationRange instanceof ModificationRange)
				toReturn.add((ModificationRange) modificationRange);
		}
		}
		return toReturn;
	}
	/**
	 * Gets all Modifiables which are in the range of the passed in ModificationRange. F.i. if the type is 
	 * Types.WORKING_UNIT_CHAPTER_RANGE all words which are in this range are returned. This is done by taking the start and end properties of
	 * the passed in range to get a sublist of the words (aka Modifiables) out of the passed in WorkingUnit
	 * 
	 * @param modificationRange The range which is used to get a list of containing Words (aka Modifiables)
	 * @param workingUnit the WrkinUnit the Words are be to retrieved of
	 * @return the list of words (aka Modifiables)
	 */
	public static List<ModifiableWithParent> getSublistOfWordsByRangeUnit(ModificationRange modificationRange,WorkingUnit workingUnit) {
		List<ModifiableWithParent> words = workingUnit.getModifiables(); 
		List<ModifiableWithParent> sub = words.subList(modificationRange.getStart(), modificationRange.getEnd() + 1);
		return sub;
	}
	
//	public static void  removeModificationRangeFromOther(ModificationRange toBeRemoved,ModificationRange toBeRemovedFrom, WorkingUnit workingUnit) {
//		List<ModificationRange> rangesContaining = getSublistOfModificationRangesByOther(toBeRemovedFrom, toBeRemovedFrom.getType(), workingUnit);
//		rangesContaining.removeAll(Collections.singleton(toBeRemoved));
//		System.out.println(" ###################### ");
//		System.out.println(rangesContaining);
//	}
	
	public static void  removeModificationRangeFromWorkingUnit(ModificationRange toBeRemoved, WorkingUnit workingUnit) {
		workingUnit.getModificationMap().get(toBeRemoved.getType()).remove(toBeRemoved);
		System.out.println(" ###################### ");
		System.out.println(workingUnit.getModificationMap().get(toBeRemoved.getType()));
	}
	
}
