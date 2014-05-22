package de.spinfo.arc.editor.shared.model3.description;

/**
 * Classes implementing this can keep a ordinal number of some kind.
 * It could be the number of succeeding pages, or of chapter.
 * 
 * @author drival
 *
 */
public interface HasOrdinal {
	/**
	 * Get the ordinal number
	 * 
	 * @return ordinal number
	 */
	public int getOrdinal();
	
	/**
	 * Sets the ordinal number
	 * 
	 * @param ordinal
	 */
	public void setOrdinal(int ordinal);
} 
