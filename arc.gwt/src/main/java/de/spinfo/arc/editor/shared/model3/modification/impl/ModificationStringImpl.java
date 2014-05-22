package de.spinfo.arc.editor.shared.model3.modification.impl;

import java.io.Serializable;
import java.util.Date;

import de.spinfo.arc.editor.shared.model3.modification.ModificationRectangle;
import de.spinfo.arc.editor.shared.model3.modification.ModificationString;
import de.spinfo.arc.editor.shared.model3.util.DebugHelper;

import static de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;

/**
 * This Class can be a word because it has the String Payload
 * 
 * @author drival
 *
 */
public class ModificationStringImpl extends ModificationImpl implements ModificationString, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final boolean DEBUG_EQUALS = true;
	private static final boolean DEBUG_COMPARABLE = true;
	private String payload;
	
	public ModificationStringImpl(String payload, Date date, int authorId, Types type) {
		super(date, authorId, type);
		this.payload = payload;
	}
	/**
	 * Zero arg constructor for GWT serialization. Don't Use!
	 */
	public ModificationStringImpl() {}
	
	@Override
	public String getPayload() {
		return payload;
	}
	
	@Override
	public String getText() {
		return payload;
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
	public int compareTo(ModificationString o) {
		
		int superClassCompareTo = super.compareToForSubclasses(o); 
		
		if (DEBUG_COMPARABLE) 
			DebugHelper.print(this.getClass(), "COMPARABLE super.compareToForSubclasses(o): " + superClassCompareTo , true);
		if (superClassCompareTo != 0) return superClassCompareTo;
		// use compareTo from the UsableGwtRectangle
		int resString = this.getPayload().compareTo(o.getPayload());
		if (DEBUG_COMPARABLE) 
			DebugHelper.print(this.getClass(), "COMPARABLE this.getPayload().compareTo(o.getPayload(): " + resString , true);
		if (resString != 0) return resString;
		
		return 0;
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
		
		if (obj instanceof ModificationString) {
			ModificationString other = (ModificationString) obj;
			boolean isPayload = payload.equals(other.getPayload());
			
			if (DEBUG_EQUALS) DebugHelper.print(this.getClass(),
					"(payload.equals(other.getPayload()" , isPayload);
			
			return isPayload && super.equals(obj);
		}
		return false;
	}
}
