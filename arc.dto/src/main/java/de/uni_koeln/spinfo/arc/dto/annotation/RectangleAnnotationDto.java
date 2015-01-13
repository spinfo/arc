package de.uni_koeln.spinfo.arc.dto.annotation;


public interface RectangleAnnotationDto extends AnnotationDto, HasDetailsDto {
	
	public abstract int getX();

	public abstract void setX(int x);

	public abstract int getY();

	public abstract void setY(int y);

	public abstract int getWidth();

	public abstract void setWidth(int width);

	public abstract int getHeight();

	public abstract void setHeight(int height);
	
	public boolean contains(int X, int Y);
}

