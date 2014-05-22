package de.spinfo.arc.editor.shared.model3.workingunit;

import java.util.List;

import de.spinfo.arc.editor.shared.model3.modification.Modification;


/**
 * Classes implementing this interface can handle a List of modifiables.
 * 
 * @author drival
 *
 * @param <T>
 */
public interface HasModifables<T> {
	
	public List<T> getModifiables();
	public void appendModifiable (T modifiable);
	
//	public void onChildModified(Modification modification);
}
