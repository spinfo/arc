package de.spinfo.arc.editor.shared.service;

import java.util.Date;
import java.util.List;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.spinfo.arc.editor.shared.model3.description.BaseDescription;
import de.spinfo.arc.editor.shared.model3.modification.ModificationRange;
import de.spinfo.arc.editor.shared.model3.modification.payload.RangeUnit;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;
import de.spinfo.arc.editor.shared.model3.workingunit.WorkingUnit;

@RemoteServiceRelativePath("service")
public interface  WorkingUnitService extends RemoteService{

	WorkingUnitDetails getDetails();
	WorkingUnit getWorkingUnit(int index);
	List<WorkingUnit> getWorkingUnits();
	ModificationRange setNewMidifcationRange(RangeUnit range,
			Types typeOfRangeMod, WorkingUnit workingUnitBeModified,
			BaseDescription description, Date date, int authorId);
}
