package de.uni_koeln.spinfo.arc.annotationmodel.annotation;

import java.util.Date;

public interface HasDetails {
	public void setDate(Date date);
	public Date getDate();
	public void setScore(int score);
	public int getScore();
	public void setUserId(String userId);
	public String getUserId();
}
