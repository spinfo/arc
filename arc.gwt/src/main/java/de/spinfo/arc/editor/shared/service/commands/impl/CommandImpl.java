package de.spinfo.arc.editor.shared.service.commands.impl;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.spinfo.arc.editor.shared.model3.workingunit.WorkingUnit;
import de.spinfo.arc.editor.shared.service.WorkingUnitServiceAsync;
import de.spinfo.arc.editor.shared.service.commands.Command;


public class CommandImpl implements Command, Comparable<Command> {
	private String name;
	private Date date;
	private boolean isExecuted = false;
	
	public CommandImpl(String name, Date date) {
		super();
		this.name = name;
		this.date = date;
	}
	
	@Override
	public String getName() {
		return name;
	}
	@Override
	public Date getDate() {
		return date;
	}
	@Override
	public boolean isExecuted() {
		return isExecuted;
	}

	@Override
	public void setIsExecuted(boolean isExecuted) {
		this.isExecuted = isExecuted;
		
	}
	
	@Override
	public int compareTo(Command o) {
		int date = this.getDate().compareTo(o.getDate());
		if (date != 0) return date;
		
		int thisIsExec = this.isExecuted ? 1 : 0;
		int otherIsExec = o.isExecuted() ? 1 : 0;
		int executed = thisIsExec - otherIsExec;
		if (executed!=0) return executed;
		
		return 0;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + date.hashCode();
		result = 31 * result +  (this.isExecuted ? 1 : 0);
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj instanceof Command) {
			Command other = (Command) obj;
			boolean isDate = this.date.equals(other.getDate());
			boolean isExecuted = (this.isExecuted && other.isExecuted()); 
			return isDate && isExecuted;
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Command: ");
		sb.append("\n");
		sb.append(name);
		sb.append(" | date: ");
		sb.append(date);
		sb.append(" | executed? ");
		sb.append(isExecuted);
		sb.append("\n");
		return sb.toString();
	}

}
