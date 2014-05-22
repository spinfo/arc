package de.spinfo.arc.editor.shared.model3.util.factory;

import java.util.Date;

import de.spinfo.arc.editor.shared.model3.description.BaseDescription;
import de.spinfo.arc.editor.shared.model3.modifiable.ModifiableWithParent;
import de.spinfo.arc.editor.shared.model3.modification.ModificationRange;
import de.spinfo.arc.editor.shared.model3.modification.ModificationRectangle;
import de.spinfo.arc.editor.shared.model3.modification.ModificationString;
import de.spinfo.arc.editor.shared.model3.modification.payload.RangeUnit;
import de.spinfo.arc.editor.shared.model3.modification.payload.UsableGwtRectangle;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;
import de.spinfo.arc.editor.shared.model3.workingunit.WorkingUnit;


/**
 * Classes impementing this act as factory and help clients to
 * not get confused by the underlying model.
 * 
 * @author drival
 *
 */
public interface WorkingUnitFactory {
//	
//	/**
//	 * This is meant to be implemented as a static fatory so
//	 * get should return this instance
//	 * @return an instance of this class
//	 */
//	public WorkingUnitFactory get();
	public WorkingUnit createWorkingUnit(BaseDescription description, Date date, int authorId);
	
	/**
	 * creates an instance which is suitable as word
	 * @return a word which is in the model an implementation of ModfifiableWithParent
	 */
	public ModifiableWithParent createWordModifiableContainer();
	
	public BaseDescription createDescription(int ordinal, String title );
	public UsableGwtRectangle createRectangle(int x, int y, int width, int height);
	public RangeUnit createRange(int start, int end);
	
	/**
	 * Appends a word (aka ModifiableWithParent-type) to a working unit
	 * 
	 * @param text the text content of the word
	 * @param rect the bounding rect of the word
	 * @param workingUnitToBeAppendedTo theWorkingUnit instance this word is part of
	 * @param date the date the word is appended to the Working unit may be null than a new Date with current time is created
	 * @return
	 */
	public ModifiableWithParent createAndAppendWordToWorkingUnit(String text, UsableGwtRectangle rect, WorkingUnit workingUnitToBeAppendedTo, Date date, int authorId);
	
	/**
	 * create the StringModification which will be appended to a word (aka Modifiable with parent where the parent is the owning WorkingUnit) 
	 * 
	 * @param newText the text of the new Modification
	 * @param wordToBeModified the instance this Modification should be appended to
	 * @return the word (aka ModifiableWithParent) this StringModification is created and appended to  
	 */
	public ModifiableWithParent createAndAppendTextModification(String newText, ModifiableWithParent wordToBeModified, Date date, int authorId);
	
	public ModifiableWithParent createAndAppendPosModification(String newPos, ModifiableWithParent wordToBeModified, Date date, int authorId);
	
	public ModifiableWithParent createAndAppendRectangleModification(UsableGwtRectangle rect, ModifiableWithParent wordToBeModified, Date date, int authorId);
	
	public WorkingUnit createAndAppendPageRangeModification(RangeUnit range, WorkingUnit workingUnitBeModified, BaseDescription description, Date date, int authorId);
	public ModificationRange createAndAppendChapterRangeModification(RangeUnit range, WorkingUnit workingUnitBeModified, BaseDescription description, Date date, int authorId);
	public WorkingUnit createAndAppendLanguageRangeModification(RangeUnit range, WorkingUnit workingUnitBeModified, BaseDescription description, Date date, int authorId);
	public WorkingUnit createAndAppendVolumeRangeModification(RangeUnit range, WorkingUnit workingUnitBeModified, BaseDescription description, Date date, int authorId);
	public WorkingUnit createAndAppendLineRangeModification(RangeUnit range, WorkingUnit workingUnitBeModified, BaseDescription description, Date date, int authorId);

	ModificationRange createAndAppendRangeModification(RangeUnit range,
			Types typeOfRangeMod, WorkingUnit workingUnitBeModified,
			BaseDescription description, Date date, int authorId);

	ModificationRange createModificationRange(int start, int end,
			BaseDescription description, Date date, int authorId, Types type);
}
