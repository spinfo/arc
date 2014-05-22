package de.spinfo.arc.editor.shared.model3.modification;

import de.spinfo.arc.editor.shared.model3.modifiable.Modifiable;
import de.spinfo.arc.editor.shared.model3.modification.payload.BaseRange;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;

/**
 * Wrapper interface for a Modification of some kind. It denotes that a
 * modification has attached user details like a ID or a score. It also enables
 * to have a "parental" modifiable instance this modification belongs to. The
 * implementing class should also implement the interface HasPayload to denote
 * the type of modification content carried by the modification.
 * 
 * @author d. rival
 * 
 */
public interface Modification extends HasUserDetails, HasParent<Modifiable>, HasComment<String>{
	/**
	 * Get the enum type of this modification
	 * this should be useful for debugging purposes
	 * 
	 * @return the enum type of this modification
	 */
	Types getType();
}
