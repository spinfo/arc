package de.spinfo.arc.editor.shared.model3.modification.payload.impl;

import java.io.Serializable;

import de.spinfo.arc.editor.shared.model3.modification.payload.BaseRange;
import de.spinfo.arc.editor.shared.model3.modification.payload.RangeUnit;
import de.spinfo.arc.editor.shared.model3.util.DebugHelper;


public class RangeUnitImpl implements RangeUnit , Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final boolean DEBUG_EQUALS = true;
	private static final boolean DEBUG_COMPARABLE = true;
	private BaseRange range;


	public RangeUnitImpl(int start, int end) {
		super();
		if (start > end) throw new IllegalArgumentException("the start index of a range unit must not be greater than the end!");
		this.range = new BaseRangeImpl(start, end);
	}

	/**
	 * Zero arg constructor for GWT serialization. Don't Use!
	 */
	public RangeUnitImpl() {}
	
	@Override
	public BaseRange getRange() {
		return range;
	}

	@Override
	public void setRange(BaseRange range) {
//		if (range.getStart() > range.getEnd()) throw new IllegalArgumentException("the start index of a range unit must not be greater than the end!");
		this.range = range;
		
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(range.toString());
		return sb.toString();
	}
	
	@Override
	public int compareTo(RangeUnit o) {
		int resRangeComparison = this.range.compareTo(o.getRange());
		if (DEBUG_COMPARABLE) 
			DebugHelper.print(this.getClass(), "COMPARABLE this.range.compareTo(o.getRange())" + resRangeComparison , true);
		if (resRangeComparison != 0) return resRangeComparison;
		
		return 0;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + range.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof RangeUnit) {
			RangeUnit other = (RangeUnit) obj;
			boolean isRangeUnit = range.equals(other.getRange());
			if (DEBUG_EQUALS) {
				DebugHelper.print(this.getClass(), "range.equals(other.getRange()", isRangeUnit);
			}
			
			return isRangeUnit;
		}
		return false;
	}
}
