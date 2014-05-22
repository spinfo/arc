package de.spinfo.arc.editor.shared.service;

import java.util.Date;
import java.util.List;


import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.spinfo.arc.editor.shared.model3.description.BaseDescription;
import de.spinfo.arc.editor.shared.model3.modification.ModificationRange;
import de.spinfo.arc.editor.shared.model3.modification.payload.RangeUnit;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;
import de.spinfo.arc.editor.shared.model3.workingunit.WorkingUnit;

public interface WorkingUnitServiceAsync {
	Request getDetails(AsyncCallback<WorkingUnitDetails> callback);
	Request getWorkingUnit(int index, AsyncCallback<WorkingUnit> callback);
	Request getWorkingUnits(AsyncCallback<List<WorkingUnit>> callback);
	Request setNewMidifcationRange(RangeUnit range,
			Types typeOfRangeMod, WorkingUnit workingUnitBeModified,
			BaseDescription description, Date date, int authorId, AsyncCallback<ModificationRange> callback);
}
