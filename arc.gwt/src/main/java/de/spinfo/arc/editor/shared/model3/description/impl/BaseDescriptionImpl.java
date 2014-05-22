package de.spinfo.arc.editor.shared.model3.description.impl;

import java.io.Serializable;

import de.spinfo.arc.editor.shared.model3.description.BaseDescription;
import de.spinfo.arc.editor.shared.model3.modification.ModificationString;
import de.spinfo.arc.editor.shared.model3.util.DebugHelper;

/**
 * Basic implementation of the BaseDescription interface
 * This can be used as description instance for a Modification
 * 
 * @author drival
 *
 */
public class BaseDescriptionImpl implements BaseDescription, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final boolean DEBUG_EQUALS = true;
	private static final boolean DEBUG_COMPARABLE = true;
	private int ordinal;
	private String title;
	
	
	public BaseDescriptionImpl(int ordinal, String title) {
		super();
		this.ordinal = ordinal;
		this.title = title;
	}
	
	/**
	 * Zero arg constructor for GWT serialization
	 */
	public BaseDescriptionImpl() {};
	
	@Override
	public int getOrdinal() {
		return ordinal;
	}

	@Override
	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
		
	}

	@Override
	public String getTitle() {
		return title;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ordinal: ");
		sb.append(ordinal);
		sb.append(" | title: ");
		sb.append(title);
		sb.append("\n");
		return sb.toString();
	}

	@Override
	public int compareTo(BaseDescription o) {
		int resOrdinal = this.ordinal - o.getOrdinal();
		if (DEBUG_COMPARABLE) 
			DebugHelper.print(this.getClass(), "COMPARABLE this.ordinal - o.getOrdinal(): " + resOrdinal , true);
		if (resOrdinal != 0) return resOrdinal;
		
		int resTitle = this.title.compareTo(o.getTitle());
		if (DEBUG_COMPARABLE) 
			DebugHelper.print(this.getClass(), "COMPARABLE this.title.compareTo(o.getTitle()): " + resTitle , true);
		if (resTitle != 0) return resTitle;
		
		return 0;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + ordinal;
		result = 31 * result + title.hashCode();
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof BaseDescription) {
			BaseDescription other = (BaseDescription) obj;
			boolean isTitle = title.equals(other.getTitle());
			boolean isOrdinal= ordinal == other.getOrdinal();
			if (DEBUG_EQUALS) {
				DebugHelper.print(this.getClass(), "isTitle && isOrdinal", (isTitle && isOrdinal));
			}
			return isTitle && isOrdinal;
		}
		return false;
	}

}
