package de.uni_koeln.spinfo.arc.dto.annotatable;

import java.util.ArrayList;
import java.util.List;
public class WorkingUnitsContainer {

	private List<WorkingUnitDto> workingUnits = new ArrayList<>();
	
	/**
	 * empty Constructor for GWT - don't use otherwise!
	 */
	public WorkingUnitsContainer() {}
	
	public List<WorkingUnitDto> getWorkingUnits() {
		return this.workingUnits;
	}

	public void setWorkingUnits(List<WorkingUnitDto> workingUnits) {
		this.workingUnits = workingUnits;
	}

	public void appendWorkingUnit(WorkingUnitDto workingUnit) {
		workingUnits.add(workingUnit);
		
	}

}
