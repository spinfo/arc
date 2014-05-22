package de.spinfo.arc.editor.shared.model3.description;

/**
 * Classes implementing this denote that they have 
 * a description object T.
 * 
 * @author drival
 *
 * @param <T> the type of the description. A simple case would be type String 
 */
public interface HasDescripton<T extends BaseDescription> {
	
	/**
	 * Get the description of Type T extends BaseDescription
	 * 
	 * @return the descripton Type
	 */
	public T getDescription();
	/**
	 * Sets description of Type T extends BaseDescription
	 * 
	 * @param description
	 */
	public void setDescription(T description);
	
}
