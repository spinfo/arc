package de.spinfo.arc.editor.shared.model3.util.factory.impl;

import java.util.Date;

import de.spinfo.arc.editor.shared.model3.description.BaseDescription;
import de.spinfo.arc.editor.shared.model3.description.impl.BaseDescriptionImpl;
import de.spinfo.arc.editor.shared.model3.modifiable.ModifiableWithParent;
import de.spinfo.arc.editor.shared.model3.modifiable.impl.ModifiableWithParentImpl;
import de.spinfo.arc.editor.shared.model3.modification.ModificationRange;
import de.spinfo.arc.editor.shared.model3.modification.ModificationRectangle;
import de.spinfo.arc.editor.shared.model3.modification.ModificationString;
import de.spinfo.arc.editor.shared.model3.modification.impl.ModificationRangeImpl;
import de.spinfo.arc.editor.shared.model3.modification.impl.ModificationRectangleImpl;
import de.spinfo.arc.editor.shared.model3.modification.impl.ModificationStringImpl;
import de.spinfo.arc.editor.shared.model3.modification.payload.RangeUnit;
import de.spinfo.arc.editor.shared.model3.modification.payload.UsableGwtRectangle;
import de.spinfo.arc.editor.shared.model3.modification.payload.impl.RangeUnitImpl;
import de.spinfo.arc.editor.shared.model3.modification.payload.impl.UsableGwtRectangleImpl;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;
import de.spinfo.arc.editor.shared.model3.util.factory.WorkingUnitFactory;
import de.spinfo.arc.editor.shared.model3.workingunit.WorkingUnit;
import de.spinfo.arc.editor.shared.model3.workingunit.WorkingUnitImpl;


public class WorkingUnitFactoryImpl implements WorkingUnitFactory {

	private WorkingUnitFactoryImpl() {};
	
	public static WorkingUnitFactory INSTANCE = new WorkingUnitFactoryImpl();
	
//	@Override
//	public WorkingUnitFactory get() {
//		if (INSTANCE == null)
//			INSTANCE = new WorkingUnitFactoryImpl();
//		
//		return INSTANCE;
//	}
	@Override
	public ModifiableWithParent createWordModifiableContainer() {
		return new ModifiableWithParentImpl();
	}
	@Override
	public BaseDescription createDescription(int ordinal, String title) {
		return new BaseDescriptionImpl(ordinal, title);
	}
	@Override
	public UsableGwtRectangle createRectangle(int x, int y, int width, int height) {
		return new UsableGwtRectangleImpl(x, y, width, height);
	}
	@Override
	public RangeUnit createRange(int start, int end) {
		return new RangeUnitImpl(start, end);
	}
	@Override
	public ModificationRange createModificationRange(int start, int end, BaseDescription description, Date date, int authorId, Types type) {
		RangeUnit rangeUnit = createRange(start, end);
		return new ModificationRangeImpl(rangeUnit, description, checkDate(date), authorId, type);
	}
	
	@Override
	public WorkingUnit createWorkingUnit(BaseDescription description,
			Date date, int authorId) {
		return new WorkingUnitImpl(description, checkDate(date), authorId);
	}

	@Override
	public ModifiableWithParent createAndAppendWordToWorkingUnit(String text,
			UsableGwtRectangle rect, WorkingUnit workingUnitToBeAppendedTo, Date date, int authorId) {
				/* create an instance which suits the word-function*/
				ModifiableWithParent word = new ModifiableWithParentImpl();
				/*
				 * the initial text of a word is the first TEXT_MOD ModificationString of a Modifiable
				 * so the first step is to create a type TEXT_MOD modification and append it 
				 * to the word (aka Modifiable)
				 */
				ModificationString initialTextMod = new ModificationStringImpl(text, checkDate(date), authorId, Types.TEXT_MOD);
				word.appendModification(initialTextMod);
				/*
				 * the initial rectangle of a word is the first RECTANGLE_MOD ModificationRectangle of a Modifiable
				 * so the first step is to create a type RECTANGLE_MOD modification and append it 
				 * to the word (aka Modifiable)
				 */
				ModificationRectangleImpl initialRectMod = new ModificationRectangleImpl(rect, checkDate(date), authorId, Types.RECTANGLE_MOD);
				word.appendModification(initialRectMod);
				/*
				 * Now that the intial vals are all in place append the word Modifiable to the HasModifiable<ModifiableWithParent> instance
				 * aka the WorkingUnit impementation
				 */
				workingUnitToBeAppendedTo.appendModifiable(word);
				/*
				 * finally return the word
				 */
		return word;
	}



	@Override
	public ModifiableWithParent createAndAppendTextModification(String newText,
			ModifiableWithParent wordToBeModified, Date date, int authorId) {
		/* create the StringModification which will be appended to the word */
		ModificationString stringMod = new ModificationStringImpl(newText, checkDate(date), authorId, Types.TEXT_MOD);
		/* append the fresh modification to its Modifiable (aka the word)*/
		wordToBeModified.appendModification(stringMod);
		
		return wordToBeModified;
		
	}
	
	@Override
	public ModifiableWithParent createAndAppendPosModification(String newPos,
			ModifiableWithParent wordToBeModified, Date date, int authorId) {
		/* create the StringModification which will be appended to the word */
		ModificationString stringMod = new ModificationStringImpl(newPos, checkDate(date), authorId, Types.POS_MOD);
		/* append the fresh modification to its Modifiable (aka the word)*/
		wordToBeModified.appendModification(stringMod);
		
		return wordToBeModified;
	}
	
	@Override
	public ModifiableWithParent createAndAppendRectangleModification(UsableGwtRectangle rect,
			ModifiableWithParent wordToBeModified, Date date, int authorId) {
		/* create the StringModification which will be appended to the word */
		ModificationRectangle rectMod = new ModificationRectangleImpl(rect, checkDate(date), authorId, Types.RECTANGLE_MOD);
		/* append the fresh modification to its Modifiable (aka the word)*/
		wordToBeModified.appendModification(rectMod);
		
		return wordToBeModified;
	}
 
	@Override
	public WorkingUnit createAndAppendPageRangeModification(RangeUnit range,
			WorkingUnit workingUnitBeModified, BaseDescription description, Date date, int authorId) {
			/*create the range mod*/
			ModificationRange rangeMod = new ModificationRangeImpl(range, description, checkDate(date), authorId, Types.WORKING_UNIT_PAGE_RANGE);
			/* append the fresh modification to its Modifiable (aka the WorkingUnit)*/
			workingUnitBeModified.appendModification(rangeMod);
		return workingUnitBeModified;
	}


@Override
public ModificationRange createAndAppendChapterRangeModification(RangeUnit range,
		WorkingUnit workingUnitBeModified, BaseDescription description,
		Date date, int authorId) {
	/*create the range mod*/
	ModificationRange rangeMod = new ModificationRangeImpl(range, description, checkDate(date), authorId, Types.WORKING_UNIT_CHAPTER_RANGE);
	/* append the fresh modification to its Modifiable (aka the WorkingUnit)*/
	workingUnitBeModified.appendModification(rangeMod);
return rangeMod;
}

@Override
public WorkingUnit createAndAppendLanguageRangeModification(RangeUnit range,
		WorkingUnit workingUnitBeModified, BaseDescription description,
		Date date, int authorId) {
	/*create the range mod*/
	ModificationRange rangeMod = new ModificationRangeImpl(range, description, checkDate(date), authorId, Types.WORKING_UNIT_LANGUAGE_RANGE);
	/* append the fresh modification to its Modifiable (aka the WorkingUnit)*/
	workingUnitBeModified.appendModification(rangeMod);
return workingUnitBeModified;
}

@Override
public WorkingUnit createAndAppendVolumeRangeModification(RangeUnit range,
		WorkingUnit workingUnitBeModified, BaseDescription description,
		Date date, int authorId) {
	/*create the range mod*/
	ModificationRange rangeMod = new ModificationRangeImpl(range, description, checkDate(date), authorId, Types.WORKING_UNIT_VOLUME_RANGE);
	/* append the fresh modification to its Modifiable (aka the WorkingUnit)*/
	workingUnitBeModified.appendModification(rangeMod);
	return workingUnitBeModified;
}

@Override
public WorkingUnit createAndAppendLineRangeModification(RangeUnit range,
		WorkingUnit workingUnitBeModified, BaseDescription description,
		Date date, int authorId) {
	/*create the range mod*/
	ModificationRange rangeMod = new ModificationRangeImpl(range, description, checkDate(date), authorId, Types.WORKING_UNIT_LINE_RANGE);
	/* append the fresh modification to its Modifiable (aka the WorkingUnit)*/
	workingUnitBeModified.appendModification(rangeMod);
	return workingUnitBeModified;
}
@Override
public ModificationRange createAndAppendRangeModification(RangeUnit range, Types typeOfRangeMod,
		WorkingUnit workingUnitBeModified, BaseDescription description,
		Date date, int authorId) { 
	/*create the range mod*/
	ModificationRange rangeMod = new ModificationRangeImpl(range, description, checkDate(date), authorId, typeOfRangeMod);
	/* append the fresh modification to its Modifiable (aka the WorkingUnit)*/
	workingUnitBeModified.appendModification(rangeMod);
	return rangeMod;
}



/**
 * Convenience Method 
 * @param date if null is passed a new date is created
 * @return returns the date passed in or if null a new date is created and returned
 */
	private static Date checkDate(Date date) {
		/* if null is passed as date param a new one is created */
		if (date == null) date = new Date();
		
		return date;
	}

}
