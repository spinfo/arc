package de.spinfo.arc.editor.shared.model3.modifiable.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.spinfo.arc.editor.client.cactus.comparators.ModificationRangeComparator;
import de.spinfo.arc.editor.shared.model3.modifiable.Modifiable;
import de.spinfo.arc.editor.shared.model3.modification.Modification;
import de.spinfo.arc.editor.shared.model3.util.DebugHelper;
import de.spinfo.arc.editor.shared.model3.util.WorkingUnitHelper;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;


public class ModifiableImpl implements Modifiable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Modifiable parent;
	/**set to true to get verbose console outs of single comparisons of the overriden equals method */
	private static final boolean DEBUG_EQUALS = true;
	private static final boolean DEBUG_COMPARABLE = true;
	private static final boolean IS_EQUALS_CONSIDER_DATE_LAST_MODIFIED = false;
	private static final boolean DEBUG_APPEND_MODIFICATION = false;

	/**
	 * Zero arg constructor for GWT serialization
	 */
	public ModifiableImpl() {}
	/**
	 * All modifications are stored in this map for better performance.
	 */
	private Map<Types, List<Modification>> heterogenousModifications = new HashMap<Types, List<Modification>>();
	/**
	 * All Modifictions are stored in this List as well in a Map
	 */
	private List<Modification> heterogenousModificationsList = new LinkedList<Modification>();
	
	@Override
	public void appendModification(Modification modification) {
//		if (!type.equals(modification.getType())) {
//			StringBuilder sb = new StringBuilder();
//			sb.append("The type of the modification passed to appendModification must match the type specified in the modification itself \n");
//			sb.append("is: ");
//			sb.append(type.toString());
//			sb.append("| but in the modification instance specified as: ");
//			sb.append(modification.getType());
//			sb.append("\n");
//			throw new IllegalArgumentException(sb.toString());
//		}
//		else if (!this.equals(modification.getParent())) {
//			StringBuilder sb = new StringBuilder();
//			sb.append("The parent of the modification passed to appendModification must match the parent specified in the modification itself \n");
//			sb.append("is: ");
//			sb.append(this.toString());
//			sb.append("| but in the modification instance specified as: ");
//			sb.append(modification.getParent());
//			sb.append("\n");
//			throw new IllegalArgumentException(sb.toString());
//		}
		/*
		 * IMPORTANT
		 * set the parent of this modification  here
		 */
		modification.setParent(this);
		/* IMPORTANT
		 * set the last modification here
		 */
		heterogenousModificationsList.add(modification);
		
		if (heterogenousModifications.get(modification.getType()) == null) {
			List<Modification> genericModList = new LinkedList<Modification>();
			genericModList.add(modification);
			heterogenousModifications.put(modification.getType(), genericModList);
		} else {
			heterogenousModifications.get(modification.getType()).add(modification);
//			Collections.sort(WorkingUnitHelper.castModificationsToRangesOrEmpty(heterogenousModifications.get(modification.getType())), ModificationRangeComparator.INSTANCE);
		}
		
		if (DEBUG_APPEND_MODIFICATION)
			DebugHelper.print(ModifiableImpl.class, "appended modification: " + modification.toString(), true);
	}
	
	@Override
	public List<Modification> getModifications(Types type) {
		List<Modification> toReturn = new LinkedList<>();
		if (heterogenousModifications.get(type) == null)
			return toReturn;
		else
			return heterogenousModifications.get(type);
	}

	@Override
	public Map<Types, List<Modification>> getModificationMap() {
		return heterogenousModifications;
	}
	
	@Override
	public List<Modification> getHeterogenousModificationList() {
		return this.heterogenousModificationsList ;
	}
	
	@Override
	public Modification getLastModification() {
		return heterogenousModificationsList.get(heterogenousModificationsList.size()-1) ;
	}
	
	public Modification getLastModificationOf(Types type) {
		if (heterogenousModifications.get(type) == null) {
			return null;
		} else {
			List<Modification> resOfType = heterogenousModifications.get(type);
			return resOfType.get(resOfType.size()-1);
		}
	}
	
	public Modification getfirstModificationOf(Types type){
		List<Modification> resOfType = heterogenousModifications.get(type);
		return resOfType.get(0);
	}
	
	public String allModificationsToString() {
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<Types, List<Modification>>> it = heterogenousModifications
				.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Types, List<Modification>> pairs = (Map.Entry<Types, List<Modification>>) it
					.next();
			sb.append(pairs.getKey());
			sb.append(" = ");
			sb.append(pairs.getValue());
			sb.append("\n");
			// it.remove(); // avoids a ConcurrentModificationException
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(allModificationsToString());
		return sb.toString();
	}
	
	@Override
	public int compareTo(Modifiable o) {
		int resDate = this.getLastModification().getDate().compareTo(o.getLastModification().getDate());
		if (DEBUG_COMPARABLE) 
			DebugHelper.print(this.getClass(), "COMPARABLE this.getLastModification().getDate().compareTo(o.getLastModification().getDate()): " + resDate , true);
		if (resDate != 0) return resDate;
		
		int resMapSize = (heterogenousModifications.size() - o.getModificationMap().size());
		if (DEBUG_COMPARABLE) 
			DebugHelper.print(this.getClass(), "COMPARABLE heterogenousModifications.size() - o.getModificationMap().size(): " + resMapSize , true);
		if (resDate != 0) return resMapSize;
		/*
		 * Omitted one by one modifiation checks..
		 */
		return 0;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		if (IS_EQUALS_CONSIDER_DATE_LAST_MODIFIED) {
			result = 31 * result + getLastModification().getDate().hashCode();
		}
		result = 31 * result + heterogenousModifications.size();
		
		Iterator<Entry<Types, List<Modification>>> itThis = heterogenousModifications
				.entrySet().iterator();
		while (itThis.hasNext()) {
				Map.Entry<Types, List<Modification>> pairsThis = (Map.Entry<Types, List<Modification>>) itThis
						.next();
				result = 31 * result + pairsThis.getKey().hashCode();
				for (Modification m : pairsThis.getValue()) {
					result = 31 * result + m.hashCode();
				}
		}

		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;

		if (obj instanceof Modifiable) {
			Modifiable other = (Modifiable) obj;
			// this is implementing has parent so the other has to do it as well
			// if (!(other instanceof HasParent<?>)) return false;
			// they're inequal if one has no modifications
			// if (heterogenousModifications.isEmpty() ||
			// other.getModificationMap().isEmpty()) return false;
			// they're inequal if one has different sized entries
			if (IS_EQUALS_CONSIDER_DATE_LAST_MODIFIED) {
			boolean isLastModified = getLastModification().getDate().equals(other.getLastModification().getDate());
			if (DEBUG_EQUALS)
				DebugHelper.print(this.getClass(), "getLastModification().getDate().equals(other.getLastModification().getDate()", isLastModified);
			if(!isLastModified) return false;
			}
			boolean isModificationsMapSizeAlike = heterogenousModifications.size() == other.getModificationMap()
					.size();
			if (DEBUG_EQUALS)
				DebugHelper.print(this.getClass(), "heterogenousModifications.size() != other.getModificationMap().size() this: " +heterogenousModifications.size() + " other: " + other.getModificationMap()
						.size() , isModificationsMapSizeAlike);
			if (!isModificationsMapSizeAlike) return false;
			
			
			
//			// check first the key set and afterwards the entry set for being
//			// more efficient
//			for (Types keyThis : heterogenousModifications.keySet()) {
//				for (Types keyother : other.getModificationMap().keySet()) {
//					if (!(keyThis.equals(keyother))) {
//						DebugHelper.print(this.getClass(), "failed keyset comparison \n keyThis: " + keyThis + " keyOther " + keyother);
//						return false; 
//					}
//				}
//			}
//			
//			DebugHelper.print(this.getClass(), "passed keyset comparison");
//			
//			//keys are equal. check now the value in entry sets
//			for (List<Modification> valThis : heterogenousModifications.values()) {
//				for (List<Modification> valOther : other.getModificationMap().values()) {
//					for(Modification mThis : valThis)
//						for(Modification mOther : valOther)
//								if (!(mThis.equals(mOther)))
//										return false;
//				}
//			}
//			
//			DebugHelper.print(this.getClass(), "passed entryset comparison");
			/*
			 * Below the checks with iterators on the both key/val pairs
			 * unbcommented it for now because I think it is better performance to
			 * iterate first on the keys 
			 */
			Iterator<Entry<Types, List<Modification>>> itThis = heterogenousModifications
					.entrySet().iterator();
			Iterator<Entry<Types, List<Modification>>> itOther = other
					.getModificationMap().entrySet().iterator();
			// do a one by one entryset comparison, first the enum keys
			while (itThis.hasNext()) {
				while (itOther.hasNext()) {
					Map.Entry<Types, List<Modification>> pairsThis = (Map.Entry<Types, List<Modification>>) itThis
							.next();
					Map.Entry<Types, List<Modification>> pairsOther = (Map.Entry<Types, List<Modification>>) itOther
							.next();
					// retrun false if the entrysets don't match - the keys are enums so they're equals method should work instant
					if (!(pairsThis.getKey().equals(pairsOther.getKey()))) {
						if (DEBUG_EQUALS) {
							DebugHelper.print(this.getClass(), "failed key comparison \n pairsThis: " + pairsThis.getKey() + "\n pairsOther: " + pairsOther.getKey(), false);
						}
						return false;
					}
					// check if the size of each value-modification list equal the other
					if (pairsThis.getValue().size() != pairsOther.getValue().size()) {
						if (DEBUG_EQUALS) {
							DebugHelper.print(this.getClass(), "failed pairsOther.getValue().size() comparison: " + pairsThis.getValue().size() + "\n pairsOther.getValue().size(): " + pairsOther.getValue().size(), false);
						}
						return false;
					}
					// now that's for sure that the lists have same size, check the elements
					List<Modification> thisList = pairsThis.getValue(); 
					List<Modification> otherList = pairsOther.getValue(); 
					// this way each element gets compared symmetrically
					for (int i = 0; i < thisList.size(); i++) {
						if (!(thisList.get(i).equals(otherList.get(i)))) {
							if (DEBUG_EQUALS) {
								DebugHelper.print(this.getClass(), "failed thisList.get(i).equals(otherList.get(i)) comparison: " + thisList.get(i) + "\n otherList.get(i): " + otherList.get(i) + "| of class: " + otherList.get(i).getClass().getSimpleName(), false);
							}
							return false;
						}
						
					}
					
				}
			} 
			if (DEBUG_EQUALS)
			DebugHelper.print(this.getClass(), "passed entryset comparison", true);
			// if passed key/val pair checks return true
			return true;
		} else
			// if the other obj is of wrong instance return false
			return false;
	}


}
