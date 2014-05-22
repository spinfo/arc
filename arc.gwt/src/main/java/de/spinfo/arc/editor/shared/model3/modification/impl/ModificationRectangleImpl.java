package de.spinfo.arc.editor.shared.model3.modification.impl;

import java.io.Serializable;
import java.util.Date;

import de.spinfo.arc.editor.shared.model3.modification.ModificationRange;
import de.spinfo.arc.editor.shared.model3.modification.ModificationRectangle;
import de.spinfo.arc.editor.shared.model3.modification.payload.UsableGwtRectangle;
import de.spinfo.arc.editor.shared.model3.util.DebugHelper;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;



public class ModificationRectangleImpl extends ModificationImpl implements ModificationRectangle, Comparable<ModificationRectangle>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final boolean DEBUG_COMPARABLE = true;
	/* immutables */
	private  UsableGwtRectangle payload;
	
	public ModificationRectangleImpl(UsableGwtRectangle payload, Date date, int authorId, Types type) {
		super(date, authorId, type);
		this.payload = payload;
	}

	@Override
	public UsableGwtRectangle getPayload() {
		return payload;
	}

	/**
	 * Zero arg constructor for GWT serialization. Don't Use!
	 */
	public ModificationRectangleImpl() {}
	/*
	 * convenience methods below to avoid typing too much 
	 */
	
	@Override
	public int getX() {
		return payload.getX();
	}

	@Override
	public void setX(int x) {
		this.payload.setX(x);
	}

	@Override
	public int getY() {
		return payload.getY();
	}

	@Override
	public void setY(int y) {
		this.payload.setY(y);
		
	}

	@Override
	public int getWidth() {
		return payload.getWidth();
	}

	@Override
	public void setWidth(int width) {
		this.payload.setWidth(width);
		
	}

	@Override
	public int getHeight() {
		return payload.getHeight();
	}

	@Override
	public void setHeight(int height) {
		this.payload.setHeight(height);
		
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("\n");
		sb.append(" | payload of mod: ");
		sb.append(payload);
		return sb.toString();
	}
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + payload.hashCode();
		result = result + super.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		
		if (obj instanceof ModificationRectangle) {
			ModificationRectangle other = (ModificationRectangle) obj;
			boolean isEqual = (getX() == other.getX() && getY() == other.getY() && getWidth() == other.getWidth() && getHeight() == other.getHeight());
			boolean isPayloadEqual = payload.equals(other.getPayload());
			// sanity check
			if(isEqual != isPayloadEqual) System.err.println("ModificationRectangleImpl the payload must be equal with the convenience getters/setters");
			
			return isEqual && isPayloadEqual && super.equals(obj);
		}
		return false;
	}

	@Override
	public int compareTo(ModificationRectangle o) {
		
		int superClassCompareTo = super.compareToForSubclasses(o); 
		
		if (DEBUG_COMPARABLE) 
			DebugHelper.print(this.getClass(), "COMPARABLE super.compareToForSubclasses(o): " + superClassCompareTo , true);
		if (superClassCompareTo != 0) return superClassCompareTo;
		// use compareTo from the UsableGwtRectangle
		int resRect = this.getPayload().compareTo(o.getPayload());
		if (DEBUG_COMPARABLE) 
			DebugHelper.print(this.getClass(), "COMPARABLE this.getPayload().compareTo(o.getPayload(): " + resRect , true);
		if (resRect != 0) return resRect;
		
		return 0;
	}
}
