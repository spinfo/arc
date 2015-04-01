package de.spinfo.arc.data;

/**
 * Created by franciscomondaca on 26/3/15.
 */
public class Range {


    private Long start;
    private Long end;

    public Range(Long start, Long end) {

        this.start = start;
        this.end = end;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }
}
