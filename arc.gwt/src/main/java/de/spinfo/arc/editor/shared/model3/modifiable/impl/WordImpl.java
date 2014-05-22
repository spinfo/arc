package de.spinfo.arc.editor.shared.model3.modifiable.impl;

import de.spinfo.arc.editor.shared.model3.modifiable.Modifiable;
import de.spinfo.arc.editor.shared.model3.modifiable.Word;
import de.spinfo.arc.editor.shared.model3.modification.Modification;
import de.spinfo.arc.editor.shared.model3.modification.ModificationRange;
import de.spinfo.arc.editor.shared.model3.workingunit.WorkingUnit;

public class WordImpl extends ModifiableWithParentImpl implements Word {
	
	@Override
	public void appendModification(Modification modification) {
		super.appendModification(modification);
		onWordModified(modification);
		
	}
	@Override
	public void onWordModified(Modification modification) {
		Modifiable parent = getParent();
		if (parent instanceof WorkingUnit) {
			if (modification instanceof ModificationRange) {
//				ModificationRange rMod = (ModificationRange) modification;
				WorkingUnit parentWorkingUnit = (WorkingUnit) parent;
				parentWorkingUnit.appendModification(modification);
			}
		}
		
	}

}
