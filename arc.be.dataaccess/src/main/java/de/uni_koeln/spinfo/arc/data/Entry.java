package de.uni_koeln.spinfo.arc.data;

import java.io.Serializable;

/**
 * Created by franciscomondaca on 24/3/15.
 */
public class Entry implements Serializable {

    private static final long serialVersionUID = 1L;

    private long index;
    private String form;
    private String pos;
    private String autor;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }


    @Override
    public String toString() {

        StringBuffer buffer = new StringBuffer();
        buffer.append(index);
        buffer.append("\t");
        buffer.append(form);
        buffer.append("\t");

        if (pos != null) {

            buffer.append(pos);
            buffer.append("\t");
            buffer.append(autor);
        }

        return buffer.toString();
    }
}
