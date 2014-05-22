package de.spinfo.arc.editor.shared.model3.modification;

import de.spinfo.arc.editor.shared.model3.modification.payload.UsableGwtRectangle;

/**
 * Wrapper interface for the type Modification UsableGwtRectangle
 * 
 * @author D. Rival
 * 
 */
public interface ModificationRectangle extends Modification, HasPayload<UsableGwtRectangle>,  Comparable<ModificationRectangle>  {
	/*
	 * All the setters/getters below are for convenience
	 * These are also part of the passed type parameter of the interface HasPayload
	 * 
	 */
	public abstract int getX();

	public abstract void setX(int x);

	public abstract int getY();

	public abstract void setY(int y);

	public abstract int getWidth();

	public abstract void setWidth(int width);

	public abstract int getHeight();

	public abstract void setHeight(int height);
}
