package de.spinfo.arc.editor.shared.model3.modification.payload.impl;

import java.io.Serializable;

import de.spinfo.arc.editor.shared.model3.description.BaseDescription;
import de.spinfo.arc.editor.shared.model3.modification.payload.BaseRange;
import de.spinfo.arc.editor.shared.model3.util.DebugHelper;



public class BaseRangeImpl implements BaseRange, Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final boolean DEBUG_EQUALS = true;
	private static final boolean DEBUG_COMPARABLE = true;
	private int start;
	private int end;

	public BaseRangeImpl(int start, int end) {
		super();
		if (start > end) throw new IllegalArgumentException("the start index of a range unit must not be greater than the end!");
		this.start = start;
		this.end = end;
	}

	/**
	 * Zero arg constructor for GWT serialization. Don't Use!
	 */
	public BaseRangeImpl() {}
	
	@Override
	public int getStart() {
		return start;
	}

	@Override
	public void setStart(int start) {
		if (start > end) throw new IllegalArgumentException("the start index of a range unit must not be greater than the end!");
		this.start = start;
		
	}

	@Override
	public int getEnd() {
		return end;
	}

	@Override
	public void setEnd(int end) {
		if (start > end) throw new IllegalArgumentException("the start index of a range unit must not be greater than the end!");
		this.end = end;
		
	}
	@Override
	public boolean isInRange(int numberToTestIfInRange) {
		return (numberToTestIfInRange >= start && numberToTestIfInRange <= end);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Range:");
		sb.append("[");
		sb.append("start : ");
		sb.append(start);
		sb.append(" | end : ");
		sb.append(end);
		sb.append("]");
		return sb.toString();
	}
	@Override
	public int compareTo(BaseRange o) {
		int resStart = this.start - o.getStart();
		if (DEBUG_COMPARABLE) 
			DebugHelper.print(this.getClass(), "COMPARABLE this.start - o.getStart(): " + resStart , true);
		if (resStart != 0) return resStart;
		
		int resEnd = this.end - o.getEnd();
		if (DEBUG_COMPARABLE) 
			DebugHelper.print(this.getClass(), "COMPARABLE this.end - o.getEnd(): " + resEnd , true);
		if (resEnd != 0) return resEnd;
		
		return 0;
	}
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + start;
		result = 31 * result + end;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof BaseRange) {
			BaseRange other = (BaseRange) obj;
			boolean isRange = (start == other.getStart() && end == other.getEnd());
			if (DEBUG_EQUALS) {
				DebugHelper.print(this.getClass(), "(start == other.getStart() && end == other.getEnd())", isRange);
			}
			return isRange;
		}
					
		return false;
	}
}
