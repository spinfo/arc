package de.uni_koeln.spinfo.arc.dto.annotation.impl;

import java.io.Serializable;
import java.util.Date;

import de.uni_koeln.spinfo.arc.dto.annotation.RectangleAnnotationDto;

public class RectangleAnnotationDtoImpl extends AnnotationDtoImpl implements RectangleAnnotationDto, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private int width;
	private int height;
	
	/**
	 * empty Constructor for GWT - don't use otherwise!
	 */
	public RectangleAnnotationDtoImpl() {}

	public RectangleAnnotationDtoImpl(Date date, int score, String userId, int x, int y, int width, int height) {
		super(date, score, userId);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}


	@Override
	public int getX() {
		return x;
	}

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
	// Below is borrowed from java.awt.Rectangle
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
	public int hashCode() {
		int result = 17;
		result = 31 * result + getX();
		result = 31 * result + getY();
		result = 31 * result + getWidth();
		result = 31 * result + getHeight();
		result = 31 * super.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof RectangleAnnotationDto) {
			RectangleAnnotationDto other = (RectangleAnnotationDto) obj;
			boolean isRect = (
					getX() == other.getX()
					&& getY() == other.getY()
					&& getWidth() == other.getWidth()
					&& getHeight() == other.getHeight()
					);
			if (!isRect) 
				return false;
			return super.equals(obj);
		} 
		else 
			return false;
	}

	@Override
	public String toString() {
		return "RectangleAnnotationImpl [x=" + x + ", y=" + y + ", width="
				+ width + ", height=" + height + "]";
	}
}
