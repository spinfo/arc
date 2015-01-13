package de.uni_koeln.spinfo.arc.dto.annotatable;

import java.util.List;




public interface HasAnnotatablesDto {
	public List<? extends HasAnnotationsDto> getAnnotatables();
	public void setAnnotatables(List<? extends HasAnnotationsDto> annotatables);
}
 