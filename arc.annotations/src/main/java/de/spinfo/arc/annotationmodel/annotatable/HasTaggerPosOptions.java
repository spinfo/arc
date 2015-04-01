package de.spinfo.arc.annotationmodel.annotatable;

import java.util.Set;

public interface HasTaggerPosOptions {
	public Set<String> getTaggerPosOptions();
	public void appendTaggerPosOption(String posOption);
	void setTaggerPosOptions(Set<String> posOptions);
	
}
