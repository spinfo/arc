package de.uni_koeln.spinfo.arc.annotationmodel.annotation;

/**
 * Implementing classes can set and get the basic properties of a rectangle.
 * Implementing classes try to act like a minimal version of the Rectangle class which
 * is currently not compilable in GWT.
 * 
 * @author  D.Rival
 *
 */ 
public interface UsableGwtRectangle extends Comparable<UsableGwtRectangle> {

	public abstract int getX();

	public abstract void setX(int x);

	public abstract int getY();

	public abstract void setY(int y);

	public abstract int getWidth();

	public abstract void setWidth(int width);

	public abstract int getHeight();

	public abstract void setHeight(int height);
	/**
	 * Borrowed from java.awt.Rectangle
	 * Checks if a point is contained in this rectangle
	 */
	public abstract boolean contains(int x, int y);

}