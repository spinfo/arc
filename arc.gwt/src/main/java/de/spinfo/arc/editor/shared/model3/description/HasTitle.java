package de.spinfo.arc.editor.shared.model3.description;

/**
 * Classes implementing this can keep a title of some kind.
 * It could be the title of a chapter.
 * 
 * @author drival
 *
 */
public interface HasTitle {
	/**
	 * sets the title
	 * 
	 * @param title
	 */
	public void setTitle(String title);
	/**
	 * get the title
	 * 
	 * @return
	 */
	public String getTitle();
	
}
