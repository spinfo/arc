package de.spinfo.arc.editor.shared.model3.modification.payload;

/**
 * Classes implementing this can set and get a range type T that must 
 * extend/implement the BaseRange
 * 
 * @author drival
 *
 * @param <T> the Type of a HasRange - type 
 */
public interface HasRange<T extends BaseRange> {
	/**
	 * 
	 * @return the Range Object
	 */
	public T getRange();
	/**
	 * sets a new range Object which must implement /extend the
	 * HasRange Interface
	 * 
	 * @param range
	 */
	public void setRange(T range); 
}
