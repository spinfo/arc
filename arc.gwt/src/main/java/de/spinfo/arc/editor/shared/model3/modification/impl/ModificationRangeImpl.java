package de.spinfo.arc.editor.shared.model3.modification.impl;

import java.io.Serializable;
import java.util.Date;

import de.spinfo.arc.editor.shared.model3.description.BaseDescription;
import de.spinfo.arc.editor.shared.model3.modification.Modification;
import de.spinfo.arc.editor.shared.model3.modification.ModificationRange;
import de.spinfo.arc.editor.shared.model3.modification.payload.BaseRange;
import de.spinfo.arc.editor.shared.model3.modification.payload.RangeUnit;
import de.spinfo.arc.editor.shared.model3.util.DebugHelper;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;


public class ModificationRangeImpl extends ModificationImpl implements ModificationRange , Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final boolean DEBUG_COMPARABLE = true;

	private static final boolean DEBUG_CONTAINS_RANGE = false;
	private static final boolean DEBUG_EQUALS = true;
	/* immutables */
	private  RangeUnit payload;
	/* mutables */
	private BaseDescription description;
	
	public ModificationRangeImpl(RangeUnit payload,BaseDescription description, Date date, int authorId, Types type) {
		super(date, authorId, type);
		this.payload = payload;
		this.description = description;
	}
	

	/**
	 * Zero arg constructor for GWT serialization. Don't Use!
	 */
	public ModificationRangeImpl() {};
	
	@Override
	public RangeUnit getPayload() {
		return payload;
	}

	@Override
	public int getStart() {
		return payload.getRange().getStart();
	}

	@Override
	public void setStart(int start) {
		this.payload.getRange().setStart(start);
		
	}

	@Override
	public int getEnd() {
		return payload.getRange().getEnd();
	}

	@Override
	public void setEnd(int end) {
		this.payload.getRange().setEnd(end);
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
//		sb.append(super.toString());
		sb.append("\n");
		sb.append(" | Description: ");
		sb.append(description.toString());
		sb.append(" | payload of mod: ");
		sb.append(payload);
		return sb.toString();
	}

	@Override
	public BaseDescription getDescription() {
		return description;
	}

	@Override
	public void setDescription(BaseDescription description) {
		this.description = description;
	}
	
	@Override
	public int compareTo(ModificationRange o) {
		
		int superClassCompareTo = super.compareToForSubclasses(o); 
		
		if (DEBUG_COMPARABLE) 
			DebugHelper.print(this.getClass(), "COMPARABLE super.compareToForSubclasses(o): " + superClassCompareTo , true);
		if (superClassCompareTo != 0) return superClassCompareTo;
		
		// use the compare to from the RangeUnit
		int resPayloadRange = this.getPayload().compareTo(o.getPayload());
		if (DEBUG_COMPARABLE) 
			DebugHelper.print(this.getClass(), "COMPARABLE this.getPayload().compareTo(o.getPayload(): " + resPayloadRange , true);
		if (resPayloadRange != 0) return resPayloadRange;
		
		int resDescription = this.getDescription().compareTo(o.getDescription()); 
		if (DEBUG_COMPARABLE) 
			DebugHelper.print(this.getClass(), "COMPARABLE this.getDescription().compareTo(o.getDescription(): " + resDescription , true);
		if (resDescription != 0) return resDescription;
		
		return 0;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + payload.hashCode();
		result = 31 * result + description.hashCode();
		result = 31 * result + super.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj == this) return true;
		
		if (obj instanceof ModificationRange) {
			ModificationRange other = (ModificationRange) obj;
			boolean isPayload = payload.equals(other.getPayload());
			DebugHelper.print(this.getClass(), "is Payload equals? this: " + this.payload + " other: "+ other.getPayload(), isPayload);
		
			boolean isDescription = description.equals(other.getDescription());
			DebugHelper.print(this.getClass(), "is description equals? this: " + this.description + " other: "+ other.getDescription(), isDescription);
			return  isPayload &&
					isDescription &&
					super.equals(obj);
		}
		return false;
	}


	@Override
	public boolean containsRange(ModificationRange other) {
		BaseRange otherRange = other.getPayload().getRange();
		BaseRange thisRange = payload.getRange();
		int otherStart = otherRange.getStart();
		int otherEnd = otherRange.getEnd();
		int thisStart = thisRange.getStart();
		int thisEnd = thisRange.getEnd();
		boolean isOthersStartSmaller = otherStart <= thisStart;
		boolean isOthersStartGreater = otherStart >= thisStart;
		boolean isContaining = (isOthersStartSmaller && (otherEnd >= thisStart)) || (isOthersStartGreater && (otherStart <= thisEnd));
		if (DEBUG_CONTAINS_RANGE)
			DebugHelper.print(ModificationRangeImpl.class, 
					"--->" 
					+"(isOthersStartSmaller && (otherEnd >= thisStart)) || (isOthersStartGreater && (otherStart <= thisEnd))" + isContaining + "\n" 
					+"isOthersStartGreater: " + isOthersStartGreater + "\n" +
					" | thisStart "	+ thisStart+ " thisEnd: " + thisEnd + "\n" 
							+ " | otherStart: " + otherStart + " otherEnd: " + otherEnd, isContaining);
		return isContaining;
	}



}
