package de.spinfo.arc.editor.shared.model3.modification.payload;

/**
 * Classes implement this interface are capable of 
 * managing a specific range. This is should be passed as Class Parameter
 * to the implementation of the HasRange interface
 * 
 * @author drival
 *
 */
public interface BaseRange extends Comparable<BaseRange> {
	
	/**
	 * get the start index
	 */
	public int getStart();
	
	/**
	 * Set the start index
	 * 
	 * @param start
	 */
	public void setStart(int start);
	
	/**
	 * gets the end index
	 * 
	 * @return
	 */
	public int getEnd();
	
	/**
	 * Sets the end index
	 * 
	 * @param end
	 */
	public void setEnd(int end);
	
	public boolean isInRange(int numberToTestIfInRange);

}