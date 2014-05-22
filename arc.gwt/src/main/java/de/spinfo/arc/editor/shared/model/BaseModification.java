package de.spinfo.arc.editor.shared.model;

import java.util.Date;

public class BaseModification {

	private String modTextContent = "";
	private int score = 0;
	private Date date;
	private int authorId;
	
	public BaseModification( int authorId, String modTextContent, Date date) {
		super();
		this.modTextContent = modTextContent;
		this.date = date;
		this.authorId = authorId;
	}

	public String getModTextContent() {
		return modTextContent;
	}

	public BaseModification setModTextContent(String modTextContent) {
		this.modTextContent = modTextContent;
		return this;
	}

	public int getScore() {
		return score;
	}

	public BaseModification setScore(int score) {
		this.score = score;
		return this;
	}

	public Date getDate() {
		return date;
	}

	public BaseModification setDate(Date date) {
		this.date = date;
		return this;
	}

	public int getAuthorId() {
		return authorId;
	}

	public BaseModification setAuthorId(int authorId) {
		this.authorId = authorId;
		return this;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("authorId: ");
		sb.append(this.authorId);
		sb.append(" | mod: ");
		sb.append(this.modTextContent);
		sb.append(" | date: ");
		sb.append(this.date);
		sb.append(" | score: ");
		sb.append(this.score);
		return sb.toString();
	}
	
}
