package de.spinfo.arc.editor.shared.model3.modification;

/**
 * Classes implementing this Interface have a refernce to an "parental" instance
 * This is meant to be implemented if f.i. a modification should know about the
 * word it belongs to or if a word should know about its Working unit.
 * 
 * @author D.Rival
 * 
 * @param <T>
 *            The type of the parent this instance is bound to. This is usually
 *            a Modifiable implementation for modifications, or a WorkingUnit
 *            implementation for a Modifiable
 */
public interface HasParent<T> {
	/**
	 * Retrieves the parent instance
	 * 
	 * @return the parent Modifiable of this implementation. Parent in this case
	 *         is the instance which hold a reference to this implementation
	 */
	public T getParent();

	/**
	 * Sets the parent. The parent itself should make sure that it sets itself
	 * as parent while appending an implementation of this to it. After thinking
	 * about that the parent should be a final field which must be set in
	 * constructor i found out that a little more safety gain comes by letting
	 * the parent set itself while appending a f.i. HasParent<Modifiable> to  a
	 * Working Unit implementation. One caveat is, that the Instance this is
	 * bound to as parent has to make sure to set itself by calling this Method
	 * with itself if an implementation of this is appended to it. Thats why the
	 * current implementation of ModifiableWithParentImpl omits a constructor which
	 * requires an instance set.
	 * 
	 * @param parent
	 */
	public void setParent(T parent);

}
