package de.uni_koeln.spinfo.arc.dto.annotatable;

public interface HasParentUnitDto<T extends HasAnnotationsDto> {
	public T getParentUnit();
	public void setParentUnit (T parentUnit);
}
