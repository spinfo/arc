package de.spinfo.arc.editor.shared.model3.workingunit;

import java.util.List;

import de.spinfo.arc.editor.shared.model3.description.BaseDescription;
import de.spinfo.arc.editor.shared.model3.description.HasDescripton;
import de.spinfo.arc.editor.shared.model3.modifiable.Modifiable;
import de.spinfo.arc.editor.shared.model3.modifiable.ModifiableWithParent;
import de.spinfo.arc.editor.shared.model3.modification.HasUserDetails;
import de.spinfo.arc.editor.shared.model3.modification.ModificationRange;


/**
 * A Working unit is of type Modifiable but is not implementing the HasParent
 * interface but has a Description carried by the HasDescription interface.
 * It also implements the HasModifiables Interface which prescribes the implementation
 * of a List of modifiables which are meant to be the Words
 * 
 * @author drival
 * 
 */
public interface WorkingUnit extends 
		Modifiable,
		HasDescripton<BaseDescription>,
		HasUserDetails,
		HasModifables<ModifiableWithParent> {
		
		/**
		 * convenience method to retrieve a certain page.
		 * Note that in the underlying model a Page is just a Range of 
		 * Modifiables which have certain Modification like TEXT_MOD,
		 * RRECTANGLE_MOD. This Method should take care of retrieving
		 * the correct Modifiables by checking the WorkinUnits Modifications Map
		 * and retrieving List of the type WORKING_UNIT_PAGE_RANGE
		 * 
		 * @param pageNum
		 * @return the words (aka ModifiableWithParent)
		 */
	public List<ModifiableWithParent> getWordsOfPage(int pageNum);
	public ModifiableWithParent getFirstWordOfPage(int pageNum);
	public ModifiableWithParent getLastWordOfPage(int pageNum);
	public ModifiableWithParent getWordAt(int index);
	/**
	 * retrieves all pages this word is in without using the equals comparison. it just uses the string content
	 * @param wordToLookFor
	 * @return
	 */
	public List<Integer> getPagesNumsWordIsContaining (ModifiableWithParent modifiableWithParentToLookFor, boolean isCaseSensitive);
	public List<Integer> getPagesNumsWordIsContaining (String textToLookFor, boolean isCaseSensitive);
	
	public List<ModificationRange> getAllPageRanges();
	public List<ModificationRange> getAllChapterRanges();
	
//		public List<ModifiableWithParent> getWordsOfPage(int pageNum);
			
}
