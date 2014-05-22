package de.spinfo.arc.editor.shared.model3.modifiable.impl;


import java.io.Serializable;

import de.spinfo.arc.editor.shared.model3.modifiable.Modifiable;
import de.spinfo.arc.editor.shared.model3.modifiable.ModifiableWithParent;
import de.spinfo.arc.editor.shared.model3.util.DebugHelper;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;


public class ModifiableWithParentImpl extends ModifiableImpl implements ModifiableWithParent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final boolean DEBUG_EQUALS = true;
	private Modifiable parent;

	/**
	 * Zero arg constructor for GWT serialization
	 */
	public ModifiableWithParentImpl() {}

	@Override
	public Modifiable getParent() {
		return parent;
	}

	@Override
	public void setParent(Modifiable parent) {
		this.parent = parent;
	}

//	@Override
//	public void onModifiedInformParent(Types type) {
//		parent.onChildModified(getLastModification()
//	
//	}
	
	@Override
	public int hashCode() {
		int result = 17;
		// not sure if this is good..
		result = 31 * result + ( parent != null ? 1 : 0 );
		result = super.hashCode();
		return result;
	}
	
@Override
public boolean equals(Object obj) {
	
	if (obj == this) return true;
	
	if (obj instanceof ModifiableWithParent) {
		ModifiableWithParent other = (ModifiableWithParent) obj;
		// try to make it efficient and consider just the amount of mods of the parents
//		boolean isModificationMapSizeOfParentsEqual = 
//				(this.parent.getModificationMap().size() == other.getParent().getModificationMap().size());
		
//		if (DEBUG_EQUALS) DebugHelper.print(this.getClass(),
//				"(payload.equals(other.getPayload()" , isPayload);
		boolean areParentsEqual = testParent(other);
		
//		return isModificationMapSizeOfParentsEqual && (super.equals(other));
		return areParentsEqual && (super.equals(other));
		
	}
		
	return false;
}
private static String NL = "\n";
private static String POINTER = "--->";


private boolean testParent(ModifiableWithParent other) {
	boolean hasThisParent = parent != null;
	boolean hasOtherParent = other.getParent() != null;
	boolean haveBothParents = hasThisParent && hasOtherParent;
	boolean justOneHasParent = ( (hasThisParent && !hasOtherParent) || (!hasThisParent && hasOtherParent) );
	if (DEBUG_EQUALS) {
		System.out.println("hasThisParent " + hasThisParent);
		System.out.println("hasOtherParent " + hasOtherParent);
		System.out.println("haveBothParents " + haveBothParents);
		System.out.println("justOneHasParent " + justOneHasParent);
		}
	// If just one has a parent they must be unequal! return false!
	if (justOneHasParent) {
		if (DEBUG_EQUALS) DebugHelper.print(this.getClass(),
				"(justOneHasParent) no equality if just one has a parent"+ POINTER + justOneHasParent + NL, !justOneHasParent);
		return false;
	}
	else {
		if (!haveBothParents) {
			if (DEBUG_EQUALS)
				DebugHelper.print(this.getClass(),
						"!(haveBothParents) THIS ONE MUST HAVE A PARENT!! Stop to check the parents if both have none and RETURN false"
								+ POINTER + haveBothParents + NL,
						!haveBothParents);
			return false;
		} 
		// just check the parents if both have one
		else {
			
			return true;
			// check object references. In case of parent of a mod must have EXACTLY the same parent
//			boolean isObjectReferenceEqualOfParents = (this.parent == other.getParent()); 
//			if (DEBUG_EQUALS)
//				DebugHelper
//						.print(this.getClass(),"this.parent == other.getParent()"												+ POINTER
//								+ isObjectReferenceEqualOfParents
//								+ NL, isObjectReferenceEqualOfParents);
//			if (!isObjectReferenceEqualOfParents) return false;
			
			/*
			 * !!!!!!!!!!!!!!!!!!!!!
			 * No need to check further if obj references are equal! If check above is removed uncomment below!
			 * (Don't know if obj reference comparison is really what I want.. but it makes sense since a modification belongs truly to
			 * just one atomic parent (f.i. Working Unit or a Word)
			 * 
			 * !!!!!!!!!!!!!!!!!!!!!!
			 */			
//			// check parents properties
//			boolean isModificationMapSizeOfParentsEqual = (this.parent
//					.getModificationMap().size() == other.getParent()
//					.getModificationMap().size());
//
//			if (DEBUG_EQUALS)
//				DebugHelper
//						.print(this.getClass(),
//								"this.parent.getModificationMap().size() == other.getParent().getModificationMap().size()"
//										+ POINTER
//										+ isModificationMapSizeOfParentsEqual
//										+ NL,
//								isModificationMapSizeOfParentsEqual);
//			if (!isModificationMapSizeOfParentsEqual)
//				return false;
//
//			boolean isSameParentLastModifiedDate = this.parent
//					.getLastModification()
//					.getDate()
//					.equals(other.getParent().getLastModification()
//							.getDate());
//			if (DEBUG_EQUALS)
//				DebugHelper
//						.print(this.getClass(),
//								"this.parent.getLastModification().getDate() != other.getParent().getLastModification().getDate()"
//										+ POINTER
//										+ isSameParentLastModifiedDate
//										+ NL,
//								isSameParentLastModifiedDate);
//			if (!isSameParentLastModifiedDate)
//				return false;
		}
//	return true;

	}	
}


}
