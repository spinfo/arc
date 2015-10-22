package de.uni_koeln.spinfo.arc.annotationmodel.annotation.impl;

import de.uni_koeln.spinfo.arc.annotationmodel.annotation.UsableGwtRectangle;
import de.uni_koeln.spinfo.arc.annotationmodel.util.DebugHelper;

import java.io.Serializable;




/**
 * A simple Rectangle class which can just hold the base values of a rect 
 * without any  mathematical methods like calculation intersection etc.
 * This is meant to be used in conjunction with the GWT compiler for compability
 * 
 * @author drival
 *
 */
public class UsableGwtRectangleImpl implements UsableGwtRectangle, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final boolean DEBUG_EQUALS = true;
	private static final boolean DEBUG_COMPARABLE = true;
	int x;
	int y;
	int width;
	int height;
	
	public UsableGwtRectangleImpl(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	/**
	 * Zero arg constructor for GWT serialization. Don't Use!
	 */
	public UsableGwtRectangleImpl() {}
	/* (non-Javadoc)
	 * @see test.shared.model3.impl.HasRectangle#getX()
	 */
	@Override
	public int getX() {
		return x;
	}

	/* (non-Javadoc)
	 * @see test.shared.model3.impl.HasRectangle#setX(int)
	 */
	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}
	

	@Override
	public boolean contains(int X, int Y) {
	      	int w = this.width;
	        int h = this.height;
	        if ((w | h) < 0) {
	            // At least one of the dimensions is negative...
	            return false;
	        }
	        // Note: if either dimension is zero, tests below must return false...
	        int x = this.x;
	        int y = this.y;
	        if (X < x || Y < y) {
	            return false;
	        }
	        w += x;
	        h += y;
	        //    overflow || intersect
	        return ((w < x || w > X) &&
	                (h < y || h > Y));
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append("x : ");
		sb.append(x);
		sb.append(" | y : ");
		sb.append(y);
		sb.append(" | width : ");
		sb.append(width);
		sb.append(" | height : ");
		sb.append(height);
		sb.append("]");
		return sb.toString();
	}
	
	@Override
	public int compareTo(UsableGwtRectangle o) {
		int resX = this.x - o.getX();
		if (DEBUG_COMPARABLE)
			DebugHelper
					.print(this.getClass(), "this.x - o.getX()" + resX, true);
		if (resX != 0)
			return resX;
		
		int resY = this.y - o.getY();
		if (DEBUG_COMPARABLE)
			DebugHelper
			.print(this.getClass(), "this.y - o.getY()" + resY, true);
		if (resY != 0)
			return resY;

		int resWidth = this.width - o.getWidth();
		if (DEBUG_COMPARABLE)
			DebugHelper
			.print(this.getClass(), "this.width - o.getWidth()" + resWidth, true);
		if (resWidth != 0)
			return resWidth;
		
		int resHeight = this.height - o.getHeight();
		if (DEBUG_COMPARABLE)
			DebugHelper
					.print(this.getClass(), "this.width - o.getWidth()" + resHeight, true);
		if (resHeight != 0)
			return resHeight;

		return 0;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + x;
		result = 31 * result + y;
		result = 31 * result + width;
		result = 31 * result + height;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof UsableGwtRectangle) {
			UsableGwtRectangle other = (UsableGwtRectangle) obj;
			boolean isRect = (x == other.getX() && y == other.getY() && width == other.getWidth() && height == other.getHeight());
			if (DEBUG_EQUALS) {
				DebugHelper.print(this.getClass(), "equals: (x == other.getX() && y == other.getY() && width == other.getWidth() && height == other.getHeight()) ", isRect);
			}
			return isRect;
		}
		return false;
	}
}
