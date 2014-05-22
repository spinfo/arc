package de.spinfo.arc.editor.shared.model3.modification.impl;

import java.io.Serializable;
import java.util.Date;

import de.spinfo.arc.editor.shared.model3.modifiable.Modifiable;
import de.spinfo.arc.editor.shared.model3.modification.Modification;
import de.spinfo.arc.editor.shared.model3.util.DebugHelper;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;


/**
 * Base implementation of a Modification without adressing the type of modified
 * Data. This class is meant to be extended by another class which implements
 * the HasPayload<T> interface to specify the actual content of the
 * modification. If done this way one doesn't have to write the boilerplate for
 * the user details and comment specification
 * 
 * @author drival
 */
public class ModificationImpl implements Modification, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**set to true to get verbose console outs of single comparisons of the overriden equals method */
	private static final boolean DEBUG_EQUALS = true;
	private static final boolean DEBUG_COMPARABLE = true;
	/* immutables */
	private  Date date;
	private  int authorId;
	private Modifiable parent;
	private  Types type;

	/* mutables */
	private int score = ModelConstants.MODIFICATION.INITIAL_SCORE;
	private String comment = ModelConstants.MODIFICATION.INITAL_COMMENT_TEXT;

	public ModificationImpl(Date date, int authorId,
			Types type) {
		super();
		this.date = date;
		this.authorId = authorId;
//		this.parent = parent;
		this.type = type;
	}
	public ModificationImpl() {};
	
	@Override
	public int getScore() {
		return score;
	}

	@Override
	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public int getAuthorId() {
		return authorId;
	}

	@Override
	public String getComment() {
		return comment;
	}

	@Override
	public void setComment(String comment) {
		this.comment = comment;

	}

	@Override
	public void setParent(Modifiable parent) {
		this.parent = parent;
	}

	@Override
	public Modifiable getParent() {
		return parent;
	}

	@Override
	public Types getType() {
		return type;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("authorId: ");
		sb.append(authorId);
		// if (parent instanceof)
		// sb.append(" | modification target: ");
		// sb.append(getParent().toString());
		sb.append(" | date: ");
		sb.append(date);
		sb.append(" | score: ");
		sb.append(score);
		sb.append(" | comment: ");
		sb.append(comment);
		sb.append(" | modification type: ");
		sb.append(type);
		return sb.toString();
	}

//	@Override
	/**
	 * This can be used by subclasses who can first compare their specifics and
	 * afterwards use this. It just compares to the creation date
	 * 
	 * @param o
	 * @return
	 */
	public int compareToForSubclasses(Modification o) {
		
		int resDate = this.date.compareTo(o.getDate());
		if (DEBUG_COMPARABLE) 
			DebugHelper.print(this.getClass(), "COMPARABLE this.date.compareTo(o.getDate(): " + resDate , true);
		if (resDate != 0) return resDate;
		
		int theAuthorId = (authorId - o.getAuthorId());
		if (DEBUG_COMPARABLE) 
			DebugHelper.print(this.getClass(), "COMPARABLE (authorId - o.getAuthorId()): " + theAuthorId , true);
		if (theAuthorId != 0) return theAuthorId;
		
		int theType = (type.compareTo(o.getType()));
		if (DEBUG_COMPARABLE) 
			DebugHelper.print(this.getClass(), "COMPARABLE type.compareTo(o.getType())): " + theType , true);
		if (theType != 0) return theType;
		
		/*
		 * omitted checks for parents
		 */
		return 0;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + date.hashCode();
		result = 31 * result + authorId;
		result = 31 * result + type.hashCode();
		// not sure if this is good..
		result = 31 * result + ( parent != null ? 1 : 0 );
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		String NL = "\n";
		String POINTER = " ---> ";
		
		if (obj == this) return true;
		
		if (obj instanceof Modification) {
			Modification other = (Modification)obj;
			
			boolean isType = (type == other.getType());
			if (DEBUG_EQUALS) DebugHelper.print(this.getClass(),
					"(type == other.getType())"+ POINTER + isType + NL, isType);
			if (!isType) return false;
			
			boolean isDate = (date.equals(other.getDate()));
			if (DEBUG_EQUALS) DebugHelper.print(this.getClass(),
					"date.equals(other.getDate()"+ POINTER + isDate + NL, isDate);
			if (!isDate) return false;
			
			boolean isAuthorId = (authorId == other.getAuthorId());
			if (DEBUG_EQUALS) DebugHelper.print(this.getClass(),
					"authorId == other.getAuthorId()"+ POINTER + isAuthorId + NL, isAuthorId);
			if (!isAuthorId) return false;
			
			// check first if both implementation have both parents not null
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
								"!(haveBothParents) stop to check the parents if both have none"
										+ POINTER + haveBothParents + NL,
								!haveBothParents);
					return true;
				} 
				// just check the parents if both have one
				else {
					
					// check object references. In case of parent of a mod must have EXACTLY the same parent
//					boolean isObjectReferenceEqualOfParents = (this.parent == other.getParent()); 
//					if (DEBUG_EQUALS)
//						DebugHelper
//								.print(this.getClass(),"this.parent == other.getParent()"												+ POINTER
//										+ isObjectReferenceEqualOfParents
//										+ NL, isObjectReferenceEqualOfParents);
//					if (!isObjectReferenceEqualOfParents) return false;
					
					/*
					 * !!!!!!!!!!!!!!!!!!!!!
					 * No need to check further if obj references are equal! If check above is removed uncomment below!
					 * (Don't know if obj reference comparison is really what I want.. but it makes sense since a modification belongs truly to
					 * just one atomic parent (f.i. Working Unit or a Word)
					 * 
					 * !!!!!!!!!!!!!!!!!!!!!!
					 */			
//					// check parents properties
//					boolean isModificationMapSizeOfParentsEqual = (this.parent
//							.getModificationMap().size() == other.getParent()
//							.getModificationMap().size());
//
//					if (DEBUG_EQUALS)
//						DebugHelper
//								.print(this.getClass(),
//										"this.parent.getModificationMap().size() == other.getParent().getModificationMap().size()"
//												+ POINTER
//												+ isModificationMapSizeOfParentsEqual
//												+ NL,
//										isModificationMapSizeOfParentsEqual);
//					if (!isModificationMapSizeOfParentsEqual)
//						return false;
//
//					boolean isSameParentLastModifiedDate = this.parent
//							.getLastModification()
//							.getDate()
//							.equals(other.getParent().getLastModification()
//									.getDate());
//					if (DEBUG_EQUALS)
//						DebugHelper
//								.print(this.getClass(),
//										"this.parent.getLastModification().getDate() != other.getParent().getLastModification().getDate()"
//												+ POINTER
//												+ isSameParentLastModifiedDate
//												+ NL,
//										isSameParentLastModifiedDate);
//					if (!isSameParentLastModifiedDate)
//						return false;
				}
			return true;

			}
		}
			
		return false;
	}

}
