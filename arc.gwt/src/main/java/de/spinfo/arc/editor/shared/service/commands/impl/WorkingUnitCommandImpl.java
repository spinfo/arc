package de.spinfo.arc.editor.shared.service.commands.impl;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.spinfo.arc.editor.shared.model3.workingunit.WorkingUnit;
import de.spinfo.arc.editor.shared.service.WorkingUnitService;
import de.spinfo.arc.editor.shared.service.WorkingUnitServiceAsync;


public class WorkingUnitCommandImpl extends CommandImpl{
	WorkingUnitServiceAsync rpcService;
	
	public WorkingUnitCommandImpl(WorkingUnitServiceAsync rpcService, String name, Date date) {
		super(name, date);
		this.rpcService = rpcService;
	}


}
