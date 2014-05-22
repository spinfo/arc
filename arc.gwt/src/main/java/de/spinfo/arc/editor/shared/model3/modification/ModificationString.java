package de.spinfo.arc.editor.shared.model3.modification;

/**
 * Wrapper interface for the type Modification String
 * 
 * @author D. Rival
 *
 */
public interface ModificationString extends Modification, HasPayload<String>, HasText, Comparable<ModificationString> {
//	/**
//	 * returns the textual payload of this modification. In case of a String mod this
//	 * returns the same value as the payload which is defined by the HasPayload interface parameter
//	 * 
//	 * @return the text of this String modification
//	 */
//	public String getText();

}
