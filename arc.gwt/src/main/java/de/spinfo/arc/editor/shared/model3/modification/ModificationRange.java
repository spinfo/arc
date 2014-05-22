package de.spinfo.arc.editor.shared.model3.modification;

import de.spinfo.arc.editor.shared.model3.description.BaseDescription;
import de.spinfo.arc.editor.shared.model3.description.HasDescripton;
import de.spinfo.arc.editor.shared.model3.modification.payload.BaseRange;
import de.spinfo.arc.editor.shared.model3.modification.payload.HasRange;
import de.spinfo.arc.editor.shared.model3.modification.payload.RangeUnit;


/**
 * Wrapper interface for the type Modification Range
 * 
 * @author D. Rival
 *
 */
public interface ModificationRange extends Modification, HasPayload<RangeUnit>, HasDescripton<BaseDescription>,  Comparable<ModificationRange>  {
	
//	/*
//	 * All the setters/getters below are for convenience
//	 * These are also part of the passed type parameter of the interface HasPayload
//	 * 
//	 */
//	
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
	
	/**
	 * Checks another ModificationRange Type if its contained in this instance
	 * This comparison is measured just by the start and end bounds
	 * @param other the instance to check if its containing in this range payload
	 * @return true if the other instances range is contained in this one
	 */
	public boolean containsRange(ModificationRange other);
}
