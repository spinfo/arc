package de.uni_koeln.spinfo.arc.dto.annotatable;

import java.util.Set;

public interface HasTaggerPosOptionsDto {
	public Set<String> getTaggerPosOptions();
	public void appendTaggerPosOption(String posOption);
	void setTaggerPosOptions(Set<String> posOptions);
	
}
 