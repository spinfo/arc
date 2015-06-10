package de.spinfo.arc.data;

import java.io.Serializable;

/**
 * Created by franciscomondaca on 20/4/15.
 */
public class MongoWord implements Serializable {

    private static final long serialVersionUID = 1L;

    private long index;
    private String word;
    private String language;

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
