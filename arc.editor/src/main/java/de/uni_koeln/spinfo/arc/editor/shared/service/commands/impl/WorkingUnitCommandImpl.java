package de.uni_koeln.spinfo.arc.editor.shared.service.commands.impl;

import java.util.Date;

import de.uni_koeln.spinfo.arc.editor.shared.service.WorkingUnitServiceAsync;


public class WorkingUnitCommandImpl extends CommandImpl{
	WorkingUnitServiceAsync rpcService;
	
	public WorkingUnitCommandImpl(WorkingUnitServiceAsync rpcService, String name, Date date) {
		super(name, date);
		this.rpcService = rpcService;
	}


}
