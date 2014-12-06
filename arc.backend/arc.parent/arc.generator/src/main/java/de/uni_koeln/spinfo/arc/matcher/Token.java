package de.uni_koeln.spinfo.arc.matcher;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by franciscomondaca on 28/11/14.
 */
public class Token implements Serializable {


    private static final long serialVersionUID = 7526472295622776147L;

    private String token;
    private long index;
    private Set<String> pos;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<String> getPos() {
        return pos;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }


    public void setPos(Set<String> pos) {
        this.pos = pos;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getIndex());
        buffer.append("\t");
        buffer.append(getToken());
        buffer.append("\t");

        if (getPos() != null) {
            buffer.append(getPos());
            buffer.append("\t");
        }


        return buffer.toString();
    }
}
