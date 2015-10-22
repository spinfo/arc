package de.uni_koeln.spinfo.arc.data;

/**
 * Created by franciscomondaca on 26/3/15.
 */
public class ForStan {

   // private Integer index;
    private String form;
    private String POS;
    private long word_index;
//
//    public Integer getIndex() {
//        return index;
//    }
//
//    public void setIndex(Integer index) {
//        this.index = index;
//    }

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

    public long getWord_index() {
        return word_index;
    }

    public void setWord_index(long word_index) {
        this.word_index = word_index;
    }

    @Override
    public String toString() {

        StringBuffer buffer = new StringBuffer();
        buffer.append(word_index);
        buffer.append("\t");
//        buffer.append(index);
//        buffer.append("\t");
        buffer.append(form);
        buffer.append("\t");
        buffer.append(POS);

        return buffer.toString();
    }
}
