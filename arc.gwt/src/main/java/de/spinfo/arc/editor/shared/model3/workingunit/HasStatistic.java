package de.spinfo.arc.editor.shared.model3.workingunit;

import de.spinfo.arc.editor.shared.model3.modification.Modification;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;

public interface HasStatistic {
	
	/**
	 * Update the stats cotainer with the modification
	 * 
	 * @param modification
	 */
	public void updateStat(Modification modification);
	
	/**
	 * Return the frquency of Modifications for the type specified
	 * 
	 * @param type
	 * @return the frequency of this modification
	 */
	public int getFrequencyOfMod(Types type);
}
