package de.uni_koeln.spinfo.arc.data;

/**
 * Created by franciscomondaca on 20/4/15.
 */
public class LangRange {

    private static final long serialVersionUID = 1L;

    private String language;
    private long start;
    private long end;
    private int band;

    public int getBand() {
        return band;
    }

    public void setBand(int band) {
        this.band = band;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }


    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(band);
        buffer.append("\t");
        buffer.append(language);
        buffer.append("\t");
        buffer.append(start);
        buffer.append("\t");
        buffer.append(end);

        return buffer.toString();
    }

}
