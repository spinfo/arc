package de.spinfo.arc.editor.shared.model3.modification.payload;


/**
 * Wrapper interface for a basic range unit for e.g. a chapter word range 
 * or a page word range. A range unitis meant to be the payload of a modification
 * 
 * @author drival
 *
 */
public interface RangeUnit extends HasRange<BaseRange>, Comparable<RangeUnit> {

}
