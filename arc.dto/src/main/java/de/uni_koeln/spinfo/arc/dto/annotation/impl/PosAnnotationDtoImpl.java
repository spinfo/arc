package de.uni_koeln.spinfo.arc.dto.annotation.impl;

import java.io.Serializable;
import java.util.Date;

import de.uni_koeln.spinfo.arc.dto.annotation.PosAnnotationDto;

public class PosAnnotationDtoImpl extends AnnotationDtoImpl implements PosAnnotationDto, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PosTags posTag; 
	
	/**
	 * empty Constructor for GWT - don't use otherwise!
	 */
	public PosAnnotationDtoImpl() {};

	public PosAnnotationDtoImpl(Date date, int score, String userId, PosTags posTag) {
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
		if (obj instanceof PosAnnotationDto) {
			PosAnnotationDto other = (PosAnnotationDto) obj;
			boolean isPos = getPos().equals(other.getPos());
			if (!isPos) 
				return false;
			return super.equals(obj);
		} 
		else 
			return false;
	}

}
