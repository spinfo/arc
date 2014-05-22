package de.spinfo.arc.editor.shared.model3.modification;

/**
 * Implementing classes hold some kind of payload
 * 
 * @author drival
 *
 * @param <T> the type of payload an Modification is holding (f.i. a String, Rectangle or a RangeUnit)
 */
public interface HasPayload<T>  {
	/**
	 * Returns the payload type <T>
	 * @return the payload
	 */
	T getPayload();
}
