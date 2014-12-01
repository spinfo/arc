package de.spinfo.arc.annotationmodel.annotatable;

import java.util.Set;

import de.spinfo.arc.annotationmodel.annotation.PosAnnotation.PosTags;

public interface HasTaggerPosOptions {
	public Set<String> getTaggerPosOptions();
	public void appendTaggerPosOption(String posOption);
	void setTaggerPosOptions(Set<String> posOptions);
	
}
