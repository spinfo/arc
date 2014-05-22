package de.spinfo.arc.editor.shared.model3.modifiable;

import java.util.List;
import java.util.Map;

import de.spinfo.arc.editor.shared.model3.modification.Modification;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;


/**
 * Classes implementing this embody a container of modifications of type
 * Modification. Each unit which is modifiable should implement this.
 * 
 * @author D. Rival
 * 
 */
public interface Modifiable extends Comparable<Modifiable> {

	/**
	 * Sets a modification. Its meant to be set on a Map which holds a
	 * enum type key for the modification type and a List for the succeeding
	 * modifications as value.
	 * 
//	 * @param type  the identifier which acts as key in a map
	 * @param modification  a Modification typed instance
	 * @throws IllegalArgumentException if the type of the modification passed to appendModification doesn't match the type specified in the modification itself
	 */
	void appendModification(Modification modification) throws IllegalArgumentException;

	/**
	 * Retrieve a list of modifications of type Modification by an enum
	 * 
	 * @param type  identifier which gets looked up
	 * @return the List of modifications of a Modification
	 */
	public List<Modification> getModifications(ModelConstants.MODIFICATION.Types type);
	
	/**
	 * Gets the map of all modifications which are identified by an enum key
	 * 
	 * @return the complete Map of all modifications
	 */
	public Map<Types, List<Modification>> getModificationMap();
	
	/**
	 * Get the las modification of this modifiable. If this f.i. imposes a word
	 * the last modification could be a corrected orthography/form
	 * 
	 * @return the current modification 
	 */
	public Modification getLastModification();
	
	public Modification getLastModificationOf(Types type);
	
	public Modification getfirstModificationOf(Types type);
	
	/**
	 * Returns the List of Modifications associated with this Modifiable. This List keeps the references in order
	 * while the Modification Map offers to retrieve sublists of modifications identified by an enum key
	 * The list is heterogenous because it can hold all kind of implementations of the modificatin interface
	 * ( f.i. ModificationRange, ModificationRectangle, ModificationString)
	 * 
	 * @return the List of the sequence of the different modification type (e.g. WOKING_UNIT_CHAPTER_RANGE)
	 * where getHeterogenousModificationList().get(0) returns the first Modification of this Modifiable ever done.
	 * getHeterogenousModificationList().get(getHeterogenousModificationList().size()-1) returns the most current modification
	 * The latter is returned by calling this types method getLastModification() as well
	 */
	public List<Modification> getHeterogenousModificationList();
}