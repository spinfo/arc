package de.spinfo.arc.editor.shared.service.commands.impl;

import java.util.LinkedList;
import java.util.List;

import de.spinfo.arc.editor.shared.model3.util.DebugHelper;
import de.spinfo.arc.editor.shared.service.commands.Command;



public class CommandHandler {
	private static final int CAPACITY = 30;

	private static final boolean DEBUG_APPEND_COMMAND = true;
	// prevent initialization
	private CommandHandler() {
	};
	
	public static List<Command> commands = new LinkedList<Command>();
	
	public static CommandHandler INSTANCE = new CommandHandler();
	
	public static Command appendCommand(Command command) {
		if (commands.size() <= CAPACITY) {
			if (DEBUG_APPEND_COMMAND) 
				DebugHelper.print(CommandHandler.class, "added command: " +command.toString() , true);
			
		}
		else {
			if (DEBUG_APPEND_COMMAND) 
				DebugHelper.print(CommandHandler.class, "refused command! capacity reached! " +command.toString() , true);
				commands.clear();
			
		}
		commands.add(command);
		return command;
	}
	
	public static List<Command> getcommands() {
		return commands;
	}
}
