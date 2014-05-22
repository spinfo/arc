package de.spinfo.arc.editor.shared.model3.modifiable;

import de.spinfo.arc.editor.shared.model3.modification.HasParent;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;

/**
 * Classes implementing this embody a container of modifications of type
 * Modification. Each unit which is modifiable and should store a refernece to its Modifiable should implement this.
 * 
 * @author D. Rival
 * 
 */
public interface ModifiableWithParent extends Modifiable, HasParent<Modifiable> {

//	/**
//	 * Sets a modification. Its meant to be set on a Map which holds a
//	 * enum type key for the modification type and a List for the succeeding
//	 * modifications as value.
//	 * 
//	 * @param type  the identifier which acts as key in a map
//	 * @param modification  a Modification typed instance
//	 */
//	void appendModification(Types type, Modification modification);
//
//	/**
//	 * Retrieve a list of modifications of type Modification by a string identifier
//	 * 
//	 * @param type  identifier which gets looked up
//	 * @return the List of modifications of a Modification
//	 */
//	public List<Modification> getModifications(ModelConstants.MODIFICATION.Types type);
//	
//	/**
//	 * Gets the map of all modifications
//	 * 
//	 * @return the complete list of all modifications
//	 */
//	public Map<Types, List<Modification>> getModificationMap();
	
	/**
	 * implementing classes should take care to inform its parents if its being modified
	 */
//	public void onModifiedInformParent(Types type);

}