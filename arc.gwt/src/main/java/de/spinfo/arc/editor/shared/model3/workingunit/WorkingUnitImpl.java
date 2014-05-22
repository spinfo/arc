package de.spinfo.arc.editor.shared.model3.workingunit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.spinfo.arc.editor.shared.model3.description.BaseDescription;
import de.spinfo.arc.editor.shared.model3.modifiable.ModifiableWithParent;
import de.spinfo.arc.editor.shared.model3.modifiable.impl.ModifiableImpl;
import de.spinfo.arc.editor.shared.model3.modification.HasPayload;
import de.spinfo.arc.editor.shared.model3.modification.HasText;
import de.spinfo.arc.editor.shared.model3.modification.Modification;
import de.spinfo.arc.editor.shared.model3.modification.ModificationRange;
import de.spinfo.arc.editor.shared.model3.modification.ModificationString;
import de.spinfo.arc.editor.shared.model3.modification.payload.BaseRange;
import de.spinfo.arc.editor.shared.model3.modification.payload.RangeUnit;
import de.spinfo.arc.editor.shared.model3.util.DebugHelper;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;

//import test.shared.model3.impl.Page;

public class WorkingUnitImpl extends ModifiableImpl implements WorkingUnit, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final boolean DEBUG_EQUALS = true;

	/* immutables */
	private  Date date;
	private  int authorId;

	/* mutables */
	private int score = ModelConstants.MODIFICATION.INITIAL_SCORE;
	private BaseDescription description;


	/** The List of all words of a working unit */
	private  List<ModifiableWithParent> modifiables = new LinkedList<ModifiableWithParent>();

	/**
	 * @param description
	 *            an implementation of the BaseDescription interface (e.g.
	 *            BaseDescriptionImpl)
	 * @param date
	 *            the date this working unit is fist time uploaded/instantiated
	 * @param authorId
	 *            the id of the creator/uploader e.g. the admin which supplied
	 *            this unit or a user
	 */
	public WorkingUnitImpl(BaseDescription description, Date date, int authorId) {
		super();
		this.description = description;
		this.authorId = authorId;
		this.date = date;
	}
	/**
	 * Zero arg constructor for GWT serialization. Don't Use!
	 */
	public WorkingUnitImpl() {}

	@Override
	public List<ModifiableWithParent> getModifiables() {
		return modifiables;
	}

	@Override
	public void appendModifiable(ModifiableWithParent modifiable) {
		/*
		 * important!!
		 * sets this instane as parent
		 */
		modifiable.setParent(this);
		
		modifiables.add(modifiable);

	}

	@Override
	public BaseDescription getDescription() {
		return description;
	}

	@Override
	public void setDescription(BaseDescription description) {
		this.description = description;

	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public int getAuthorId() {
		return authorId;
	}
	
	private static final String NL = "\n";

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("------------ Working Unit ----------");
		sb.append(NL);
		sb.append(" | description: ");
		sb.append(description.toString());
		sb.append(" | authorId: ");
		sb.append(authorId);
		sb.append(" | date: ");
		sb.append(date);
		sb.append(NL);
		sb.append("--- MODIFIABLES of this Modifiable (e.g. words of a working unit): ---");
		sb.append(NL);
		for (ModifiableWithParent m : modifiables) {
			sb.append(m.toString());
			// sb.append(getWordListInRange(p));
			sb.append(NL);
		}
		sb.append("--- END OF MODIFIABLES(e.g. words): ---");
		sb.append(NL);
		sb.append(NL);
		// print out all modifications from the map
		sb.append("--- MODIFICATIONS of this Modifiable (e.g. chapter range, Language Range of a working unit): ---");
		sb.append(NL);
		sb.append(super.allModificationsToString());
		sb.append("--- END OF MODIFICATIONS(e.g. chapter range, Language Range): ---");
		sb.append(NL);
		sb.append("-------------- End Of Working Unit : ");
		sb.append(description.getTitle());
		sb.append(NL);
		return sb.toString();
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + date.hashCode();
		result = 31 * result + authorId;
		result = 31 * result + description.hashCode();
		for (ModifiableWithParent mwp : modifiables) {
			result = 31 * result + mwp.hashCode();
		}
		result = 31 * result + super.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj == this) return true;
		
		if (obj instanceof WorkingUnit) {
			WorkingUnit other = (WorkingUnit) obj;
			
			// both must have the same modifiable content
			boolean isDescription = (description.equals(other.getDescription()));
			if (DEBUG_EQUALS)
				DebugHelper.print(this.getClass(),"description.equals(other.getDescription()", isDescription);
			if (!isDescription) return false;		
//			if (! (description.equals(other.getDescription()))) return false;
			
			boolean isDate = ((date.equals(other.getDate())));
			if (DEBUG_EQUALS)
				DebugHelper.print(this.getClass(),"(date.equals(other.getDate()))", isDate);
			if (!isDate) return false;				
//			if (! (date.equals(other.getDate()))) return false;
			boolean isAuthorId = ((authorId == other.getAuthorId()));
			if (DEBUG_EQUALS)
				DebugHelper.print(this.getClass(),"(authorId == other.getAuthorId())", isAuthorId);
			if (!isAuthorId) return false;	
//			if (! (authorId == other.getAuthorId())) return false;
			
			boolean isModifiablesSize  = other.getModifiables().size() == modifiables.size();
			// check if the wu modifiables (aka words) size is equal
			if (DEBUG_EQUALS)
				DebugHelper.print(this.getClass(),"(other.getModifiables().size())", isModifiablesSize);
			if (!isModifiablesSize) return false;	
//			if (other.getModifiables().size() != modifiables.size()) return false;
			/*
			 *  the size is equal, now check element by elem
			 */
			for (int i = 0; i < modifiables.size(); i++) {
				if (!(modifiables.get(i).equals(other.getModifiables().get(i)))) {
					if (DEBUG_EQUALS)
						DebugHelper.print(this.getClass(),"!(modifiables.get(i).equals(other.getModifiables().get(i)))", false);
				}
				return false;
			}
			// superclasses
			if (!(super.equals(other))) return false;
			return true;
		}
		return false;
	}


//	@Override
//	public void onChildModified(Modification modification) {
//		if (modification instanceof ModificationRange) {
////			ModificationRange rangeMod =(ModificationRange) modification;
//			super.appendModification(modification);
//		}
//		
//	}
	public List<Integer> getPagesNumsWordIsContaining (ModifiableWithParent modifiableWithParentToLookFor, boolean isCaseSensitive) {
		List<Integer> toReturn = new LinkedList<>();
		Types typeToLookFor = Types.TEXT_MOD;
		String textToLookFor = "";
		if (modifiableWithParentToLookFor.getLastModificationOf(typeToLookFor) instanceof ModificationString)
			textToLookFor = ((ModificationString) modifiableWithParentToLookFor.getLastModificationOf(typeToLookFor)).getPayload();
		// return an empty list is better than null
		if (textToLookFor.equals("")) return toReturn;
		else return getPagesNumsWordIsContaining (textToLookFor, true);
	}
	
	@Override
	public List<Integer> getPagesNumsWordIsContaining (String textToLookFor, boolean isCaseSensitive) {
		List<Integer> toReturn = new LinkedList<>();
		Types typeToLookFor = Types.TEXT_MOD;
		
		List<ModificationRange> allPageRanges = getAllPageRanges();
			for (int i = 0; i < allPageRanges.size(); i++) {
				ModificationRange pageRange = allPageRanges.get(i);
				for (int j = pageRange.getStart(); j <= pageRange.getEnd(); j++) {
						String currentWordOfWu = "";
					if (modifiables.get(j).getLastModificationOf(typeToLookFor) instanceof ModificationString) 
						currentWordOfWu = ((ModificationString)modifiables.get(j).getLastModificationOf(typeToLookFor)).getPayload();
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
	
	@Override
	public List<ModificationRange> getAllPageRanges() {
		return  getAllRangesOfType(Types.WORKING_UNIT_PAGE_RANGE);
	}
	
	@Override
	public List<ModificationRange> getAllChapterRanges() {
		return getAllRangesOfType(Types.WORKING_UNIT_CHAPTER_RANGE);
	}
	
	private List<ModificationRange> getAllRangesOfType(Types typeToLookFor) {
		List<Modification> mods = getModificationMap().get(typeToLookFor);
		List<ModificationRange> toReturn = new LinkedList<>();
		for (Modification m : mods) {
			if (m instanceof ModificationRange) {
				toReturn.add((ModificationRange) m);
			}
		}
		return  toReturn;
	}
	
	@Override
	public ModifiableWithParent getWordAt(int index){
		return modifiables.get(index);
	}
	
	@Override
	public ModifiableWithParent getFirstWordOfPage(int pageNum) {
		return getWordsOfPage(pageNum).get(0);
	}
	@Override
	public ModifiableWithParent getLastWordOfPage(int pageNum) {
		List<ModifiableWithParent> words = getWordsOfPage(pageNum);
		return words.get(words.size()-1);
	}
	
	@Override
	public List<ModifiableWithParent> getWordsOfPage(int pageNum) {
		Types typeToLookFor = Types.WORKING_UNIT_PAGE_RANGE;
		ModificationRange wordRangeOfAPageRange = (ModificationRange) getModificationMap().get(typeToLookFor).get(pageNum);
		return getSublistOfWordsByRangeUnit(wordRangeOfAPageRange);
	}
	
	
	private List<ModifiableWithParent> getSublistOfWordsByRangeUnit(ModificationRange modificationRange) {
		List<ModifiableWithParent> sub = modifiables.subList(modificationRange.getStart(), modificationRange.getEnd());
		return sub;
	}

}
