package de.spinfo.arc.editor.shared.model3.modifiable;

import de.spinfo.arc.editor.shared.model3.modification.Modification;

/**
 * A Word (curretnly unused) can inform its parent Working unit about changes
 * 
 * @author d. rival
 *
 */
public interface Word extends ModifiableWithParent {
	/**
	 * Implement this to inform the parent Modifiable about changes
	 * 
	 * @param modification
	 */
	public void onWordModified(Modification modification);
}
