package de.spinfo.arc.editor.shared.model3.modification;

import java.util.Date;
/**
 * Classes implementing this Interface are capable of managing user details in respect
 * of a modification of some kind. This is already included in the extending interface Modification
 * which should be used for implementation.
 * 
 * @author D.Rival
 * 
 */
public interface HasUserDetails {
	/**
	 * Get the score of a modification. the score defaults to -1 when not set.
	 * 
	 * @return the current score of the Modification
	 */
	public int getScore();

	/**
	 * Set the score of a modification. the score defaults to -1 when not set.
	 * 
	 * @param score
	 *            for this modification
	 * @return
	 */
	public void setScore(int score);

	/**
	 * Retrieve the Date of this Modification
	 * 
	 * @return the date of the committed modification
	 */
	public Date getDate();

	/**
	 * Retrieve the id of the author of this Modification
	 * 
	 * @return the id of the author
	 */
	public int getAuthorId();

	/**
	 * Retrieve the parent Word of this Modification
	 * 
	 * @return the Word-Instance this modification belongs to
	 */

}
