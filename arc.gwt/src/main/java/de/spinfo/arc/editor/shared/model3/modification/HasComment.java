package de.spinfo.arc.editor.shared.model3.modification;

/**
 * Classes implementing this are meant to be capable of holding a comment for an
 * action/modification
 * 
 * @author drival
 * 
 * @param <T> the type of the comment. A simple case would be String
 */
public interface HasComment<T> {
	/**
	 * Retrieve the comment
	 * 
	 * @return the text of the comment
	 */
	T getComment();

	/**
	 * Set the text of the comment
	 * 
	 * @param comment
	 */
	void setComment(T comment);
}
