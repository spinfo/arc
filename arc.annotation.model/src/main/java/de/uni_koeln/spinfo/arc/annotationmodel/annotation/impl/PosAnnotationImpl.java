package de.uni_koeln.spinfo.arc.annotationmodel.annotation.impl;

import java.io.Serializable;
import java.util.Date;

import de.uni_koeln.spinfo.arc.annotationmodel.annotation.PosAnnotation;

public class PosAnnotationImpl extends AnnotationImpl implements PosAnnotation, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PosTags posTag; 
	
	/**
	 * empty Constructor for GWT - don't use otherwise!
	 */
	public PosAnnotationImpl() {};

	public PosAnnotationImpl(Date date, int score, String userId, PosTags posTag) {
		super(date, score, userId);
		this.posTag = posTag;
	}

	@Override
	public PosTags getPos() {
		return posTag;
	}

	@Override
	public void setPos(PosTags posTag) {
		this.posTag = posTag;
	}


	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + getPos().hashCode();
		result = 31 * super.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof PosAnnotation) {
			PosAnnotation other = (PosAnnotation) obj;
			boolean isPos = getPos().equals(other.getPos());
			if (!isPos) 
				return false;
			return super.equals(obj);
		} 
		else 
			return false;
	}

}
