package de.spinfo.arc.editor.client.cactus.presenter.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;




import com.google.gwt.user.client.ui.TreeItem;

import de.spinfo.arc.editor.client.cactus.comparators.ModificationRangeComparator;
import de.spinfo.arc.editor.client.cactus.comparators.TreeItemModificationRangeUserObjectComparator;
import de.spinfo.arc.editor.shared.model3.modification.ModificationRange;
import de.spinfo.arc.editor.shared.model3.util.DebugHelper;

public class CactusViewPresenterHelper {
	private CactusViewPresenterHelper() {};
	
	public static CactusViewPresenterHelper INSTANCE = new CactusViewPresenterHelper();
	
	/**
	 * this method is for reminding me to think of multiselection of different Ranges
	 * and how to determin if the elements are succeeding or not. If in the cactus are some multi items selected
	 * and there are some blocks and some isolated selection can mean that for each block there must be a different Category-node
	 * in this case its got to be good to retrieve such kind of list 
	 * 
	 * @param selectedItems of a Cactus which has ModificationRange as UserObject
	 */
	public static List<List<ModificationRange>>  findSucceedingAndDistincSelectedRanges(TreeItem[] selectedItems) {
		// find out, if selected items are in order or if they encompass different ranges with gaps
		List<ModificationRange> selectedItemsRanges = new LinkedList<>();
		List<TreeItem> selectedItemsList = Arrays.asList(selectedItems);
		Collections.sort(selectedItemsList, TreeItemModificationRangeUserObjectComparator.INSTANCE);
		
		List<List<ModificationRange>> rangeBlocksToReturn = new LinkedList<List<ModificationRange>>();
		
		/*
		 * Copy the userobjects from the tree items to a new list (selectedItemsRanges)
		 */
		for (Iterator iterator = selectedItemsList.iterator(); iterator
				.hasNext();) {
			TreeItem treeItem = (TreeItem) iterator.next();
			ModificationRange itemsUserObject = (ModificationRange) treeItem.getUserObject();
			selectedItemsRanges.add(itemsUserObject);
		}
		
		/*
		 * Do some comparison to fill 2 distinct lists. one for
		 * distinct, isolated items which have been selected and one for succeding items 
		 */
		if (!selectedItemsRanges.isEmpty()) {
			List<ModificationRange> succeedingRanges = new LinkedList<>();
			List<ModificationRange> distinctRanges = new LinkedList<>();
			boolean isFirstSwapped = false;
			ModificationRange lastPageRange = null;
			
			for (Iterator iterator = selectedItemsRanges.iterator(); iterator
					.hasNext();) {
				ModificationRange itemRange = (ModificationRange) iterator
						.next();
				
				
				if (lastPageRange == null) {
					lastPageRange = itemRange;
					itemRange = (ModificationRange) iterator
							.next();
				}
				
				if ( lastPageRange.getEnd() - itemRange.getStart() == -1) {
//					System.out.println("range in order: last: \n" + lastPageRange + "\n next: " + itemRange);
					if (!isFirstSwapped) {
						succeedingRanges.add(lastPageRange);
						succeedingRanges.add(itemRange);
						lastPageRange = itemRange;
						isFirstSwapped = true;
					} else {
						succeedingRanges.add(itemRange);
						lastPageRange = itemRange;
					}
				} else {
					if (!isFirstSwapped) {
						distinctRanges.add(lastPageRange);
						distinctRanges.add(itemRange);
						lastPageRange = itemRange;
						isFirstSwapped = true;
					} else {
						distinctRanges.add(itemRange);
						lastPageRange = itemRange;
					}
					
					
				}
			}
			/*
			 * Check the list of distinct items against the succeeding items in order to
			 * append from the distinct items to the succeeding if one is missed.
			 * Thats the case becaUSE IN THE ABOVE BACKWARD COMPARISON the beginning of a 
			 * new succeeding block is recognized as a distinct selected item. Below the first loop saves
			 * the ModificationRanges wich are to be appended to the succeeding list 
			 */
			List<ModificationRange> toBeSwappedDistincts = new LinkedList<>();
			for (int i = 0; i < distinctRanges.size(); i++) {
				for (int j = 0; j < succeedingRanges.size(); j++) {
				if (distinctRanges.get(i).getEnd() - succeedingRanges.get(j).getStart() == -1) {
					String str = " the range in distinct ranges: " + distinctRanges.get(i) +
							" is a preceding range of succeedingRange: " + succeedingRanges.get(j);
					System.err.println(str);
					toBeSwappedDistincts.add(distinctRanges.get(i));
					}
					
				}
			}
			/*
			 * The distinct selected ranges which are to be appended to the succeeding list are 
			 * copied over. All occourences of the copied over ranges are removed from the list
			 * of distinct slelected ranges 
			 */
			succeedingRanges.addAll(toBeSwappedDistincts);
			for (Iterator iterator2 = toBeSwappedDistincts.iterator(); iterator2
					.hasNext();) {
						ModificationRange swapDistRange = (ModificationRange) iterator2.next();
						while (distinctRanges.remove(swapDistRange)) {}
			}
			/*
			 * Both ranges are sorted descending from by their Ranges starts and ends
			 */
			Collections.sort(succeedingRanges, ModificationRangeComparator.INSTANCE);
			Collections.sort(distinctRanges, ModificationRangeComparator.INSTANCE);
			
			DebugHelper.print(CactusViewPresenterHelper.class, "selected items succeeding ranges:\n" + 
					succeedingRanges + "\n distinct ranges:\n "+ distinctRanges, true);	
			
			/*
			 * As last stap the list of succeeding Ranges are checked for blocks. A block is f.i. 3,4,5 and 6,7,8
			 * all these blocks are copied to new lists which are put in succesion on a 2-dimensional List, where ich index 
			 * is a block of succeeding selected items. This is good for later on create new Categories out of each block 
			 */
			
			if (!succeedingRanges.isEmpty()) {
//				List<List<ModificationRange>> rangeBlocksToReturn = new LinkedList<List<ModificationRange>>();
				
				List<ModificationRange> block = new LinkedList<>();
				ModificationRange lastRange = null;
				
				boolean isSwapped = false;
				for (Iterator iterator = succeedingRanges.iterator(); iterator
						.hasNext();) {
					ModificationRange range =  (ModificationRange) iterator.next();
					if (lastRange == null) {
						lastRange = range;
					} 
					else { 
						if ( lastRange.getEnd() - range.getStart() == -1) {
							if (!isSwapped){
								block.add(lastRange);
								isSwapped = true;
							}
							block.add(range);
							System.out.println("added to a block: \n" + lastRange + "\n" + range);
							lastRange = range;
						} 
						else {
							if (!isSwapped){
								block.add(lastRange);
								isSwapped = true;
							}
							rangeBlocksToReturn.add(block);	
							System.out.println("added to new block: \n" + range);
							System.out.println("in the OLD BLOCK is: \n" + block);
							block = new LinkedList<>();
							block.add(range);
							lastRange = range;
						}
					}
				}
				if (!block.isEmpty()) {
					rangeBlocksToReturn.add(block);
				}
				DebugHelper.print(CactusViewPresenterHelper.class, "succeeding ranges AS BLOCKS:\n" + 
						rangeBlocksToReturn , true);
			}
		}
		/*
		 * Finally return the list of List of ModificationRange where each index is a list of 
		 * succeeding PageRanges. These are good for building up distinct categories for each block
		 */
		return rangeBlocksToReturn;
	}
}
