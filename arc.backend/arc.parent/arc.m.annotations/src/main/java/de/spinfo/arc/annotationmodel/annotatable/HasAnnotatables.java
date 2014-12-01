package de.spinfo.arc.annotationmodel.annotatable;

import java.util.List;




public interface HasAnnotatables {
	public List<? extends HasAnnotations> getAnnotatables();
	public void setAnnotatables(List<? extends HasAnnotations> annotatables);
}
 