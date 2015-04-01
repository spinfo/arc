package de.spinfo.arc.data;

/**
 * Created by franciscomondaca on 26/3/15.
 */
public class ForStand {

    private Integer index;
    private String form;
    private String POS;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getPOS() {
        return POS;
    }

    public void setPOS(String POS) {
        this.POS = POS;
    }

    @Override
    public String toString() {

        StringBuffer buffer = new StringBuffer();
        buffer.append(getIndex());
        buffer.append("\t");
        buffer.append(getForm());
        buffer.append("\t");
        buffer.append(getPOS());

        return buffer.toString();
    }
}
