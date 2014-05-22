package de.spinfo.arc.editor.server;

import java.util.Date;
import java.util.List;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.spinfo.arc.editor.server.pdfparser.WorkingUnitParsedContainer;
import de.spinfo.arc.editor.shared.model3.description.BaseDescription;
import de.spinfo.arc.editor.shared.model3.impl.PseudoWorkinUnitDataSource;
import de.spinfo.arc.editor.shared.model3.modification.ModificationRange;
import de.spinfo.arc.editor.shared.model3.modification.payload.RangeUnit;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;
import de.spinfo.arc.editor.shared.model3.util.factory.impl.WorkingUnitFactoryImpl;
import de.spinfo.arc.editor.shared.model3.workingunit.WorkingUnit;
import de.spinfo.arc.editor.shared.service.WorkingUnitDetails;
import de.spinfo.arc.editor.shared.service.WorkingUnitService;

public class WorkingUnitServiceImpl extends RemoteServiceServlet implements WorkingUnitService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public WorkingUnitDetails getDetails() {
		return new WorkingUnitDetails();
	}

	@Override
	public WorkingUnit getWorkingUnit(int index) {
		System.out.println("called get on server side!");
//		return PseudoWorkinUnitDataSource.INSTANCE.getData().get(index);
		return WorkingUnitParsedContainer.getData();
		
	}

	@Override
	public List<WorkingUnit> getWorkingUnits() {
		return PseudoWorkinUnitDataSource.INSTANCE.getData();
	}

	@Override
	public ModificationRange setNewMidifcationRange(RangeUnit range,
			Types typeOfRangeMod, WorkingUnit workingUnitBeModified,
			BaseDescription description, Date date, int authorId) {
			return WorkingUnitFactoryImpl.INSTANCE.
			createAndAppendRangeModification(range, typeOfRangeMod, workingUnitBeModified, description, date, authorId);
	}

}
