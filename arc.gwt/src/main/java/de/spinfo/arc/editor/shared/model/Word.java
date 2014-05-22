package de.spinfo.arc.editor.shared.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;



public class Word {
	// the original text of the pdf ought not to be changed
	private final String text;
	private Rectangle textRect = new Rectangle(-1,-1, -1,-1);
	
	private List<BaseModification> listOfFormModifications = null;
	private List<BaseModification> listOfPosModifications = null;
	
	/**a reference to its parent working unit in order to tell it when the words been changed
	 */
//	private final WorkingUnit workingUnit;
	
	
//	public Word(String text, Rectangle textRect, WorkingUnit workingUnit) {
	public Word(String text, Rectangle textRect) {
		super();
		this.text = text;
		this.textRect = textRect;
//		this.workingUnit = workingUnit;
		
//		listOfFormModifications = new LinkedList<FormModification>();
//		listOfPosModifications = new LinkedList<PosModification>();
		
	}

	/* (non-Javadoc)
	 * @see de.spinfo.rival.PDFparsing.IMetaChar#setTextRect(java.awt.Rectangle)
	 */
	public Word setTextRect(Rectangle textRect) {
		this.textRect = textRect;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see de.spinfo.rival.PDFparsing.IMetaChar#setText(java.lang.String)
	 */
//	public Word setText(String text) {
//		this.text = text;
//		return this;
//	}
	
	public String getText() {
		return text;
	}
	
	public Rectangle getTextRect() {
		return textRect;
	}
	
	public Word appendFormMod(int authorId, String form, Date date) {
		if (listOfFormModifications== null) 
			listOfFormModifications = new LinkedList<BaseModification>();
		
		FormModification fm = new FormModification(authorId, form, date);
		listOfFormModifications.add(fm);
		
		// call the parent WU to inform that this words been changed
//		workingUnit.onModified(this);
		
		return this;
	}
	
	public Word appendPosMod(int authorId, String posTag, Date date) {
		if (listOfPosModifications== null) 
			listOfPosModifications = new LinkedList<BaseModification>();
		
		PosModification pm = new PosModification(authorId, posTag, date);
		listOfPosModifications.add(pm);
		
		// call the parent WU to inform that this words been changed
//		workingUnit.onModified(this);
		
		return this;
	}
	
	
	public BaseModification getLastFormMod() {
		return listOfFormModifications.get(listOfFormModifications.size()-1);
	}
	
	public BaseModification getLastPosMod() {
			return listOfPosModifications.get(listOfPosModifications.size()-1);
	}
	

	
	public List<BaseModification> getListOfFormModifications() {
		return listOfFormModifications;
	}

	public void setListOfFormModifications(
			List<BaseModification> listOfFormModifications) {
		this.listOfFormModifications = listOfFormModifications;
	}

	public List<BaseModification> getListOfPosModifications() {
		return listOfPosModifications;
	}

	public void setListOfPosModifications(
			List<BaseModification> listOfPosModifications) {
		this.listOfPosModifications = listOfPosModifications;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.text);
		sb.append("\n");
		sb.append(this.textRect);
		sb.append("\n");
		sb.append("Form Mods:");
		sb.append(listOfFormModifications);
		sb.append("\n");
		sb.append("pos Mods:");
		sb.append(listOfPosModifications);
		sb.append("\n");
		return sb.toString();
	}
	
}
