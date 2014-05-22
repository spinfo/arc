package de.spinfo.arc.editor.shared.model;

public class Page extends RangeUnit {
	
	private int number;
	
	public Page (int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	private static final String NL = "\n";
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("page nr.");
		sb.append(getNumber());
		sb.append(NL);
		sb.append(super.toString());
		return sb.toString();
	}
}
