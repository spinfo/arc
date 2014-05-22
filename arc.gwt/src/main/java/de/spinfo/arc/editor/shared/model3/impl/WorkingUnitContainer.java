package de.spinfo.arc.editor.shared.model3.impl;

import java.util.ArrayList;
import java.util.List;

import de.spinfo.arc.editor.shared.model3.workingunit.WorkingUnit;


public class WorkingUnitContainer {
	private WorkingUnitContainer() {}
	public static WorkingUnitContainer INSTANCE = new WorkingUnitContainer() {};
	
	private static List<WorkingUnit> allWorkingUnits = new ArrayList<WorkingUnit>();
	
	public WorkingUnit appendWorkingUnit(WorkingUnit workingUnit) {
		allWorkingUnits.add(workingUnit);
		return workingUnit;
	}
	
	public List<WorkingUnit> getWorkingUnits() {
		return allWorkingUnits;
	}
}
