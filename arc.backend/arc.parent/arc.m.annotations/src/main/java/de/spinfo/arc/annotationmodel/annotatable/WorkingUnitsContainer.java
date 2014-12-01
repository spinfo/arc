package de.spinfo.arc.annotationmodel.annotatable;

import java.util.ArrayList;
import java.util.List;
public class WorkingUnitsContainer {

	private List<WorkingUnit> workingUnits = new ArrayList<>();
	
	/**
	 * empty Constructor for GWT - don't use otherwise!
	 */
	public WorkingUnitsContainer() {}
	
	public List<WorkingUnit> getWorkingUnits() {
		return this.workingUnits;
	}

	public void setWorkingUnits(List<WorkingUnit> workingUnits) {
		this.workingUnits = workingUnits;
	}

	public void appendWorkingUnit(WorkingUnit workingUnit) {
		workingUnits.add(workingUnit);
		
	}

}
