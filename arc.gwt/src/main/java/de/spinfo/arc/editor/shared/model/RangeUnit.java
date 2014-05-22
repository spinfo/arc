package de.spinfo.arc.editor.shared.model;

public class RangeUnit {
	
	private WordsRange range;


	public RangeUnit() {
	}


	public WordsRange getRange() {
		return range;
	}

	public void setRange(WordsRange range) {
		this.range = range;
	}
	public void setRange(int start, int end) {
		this.range = new WordsRange(start, end);
	}
	
	private static final String NL = "\n";
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("start @");
		sb.append(range.getStart());
		sb.append(NL);
		sb.append("end @");
		sb.append(range.getEnd());
		sb.append(NL);
		return sb.toString();
	}
}
