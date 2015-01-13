package de.uni_koeln.spinfo.arc.annotationmodel.annotatable;

public interface HasParentUnit<T extends HasAnnotations> {
	public T getParentUnit();
	public void setParentUnit (T parentUnit);
}
