package de.spinfo.arc.editor.shared.model3.modification;

/**
 * This interface should be implemented by Modifications which have 
 * primary String content
 * 
 * @author D. Rival
 *
 */
public interface HasText {

	/**
	 * Retrieve the textual representation of a modification for displaying
	 * purposes f.i. a) in case of a form-modification this method should yield
	 * the modificated form b) in case of a language-modification this method
	 * should yield the name of the language of the regarding word.
	 * 
	 * @return the textual representation of this modification
	 */
	public String getText();
}
