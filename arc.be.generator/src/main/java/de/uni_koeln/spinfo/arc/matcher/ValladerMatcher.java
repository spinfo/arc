package de.uni_koeln.spinfo.arc.matcher;

import java.util.Map;
import java.util.TreeSet;

/**
 * Created by franciscomondaca on 20/7/15.
 */
public class ValladerMatcher extends POSMatcher {

    public ValladerMatcher(Map<String, TreeSet<String>> fullForms,
                           String collectionName) {
        super(fullForms, collectionName);
    }


    @Override
    public boolean isIndImperfect(String token) {
        if (token.endsWith("a") || token.endsWith("ast")
                || token.endsWith("an") || token.endsWith("at")
                || token.endsWith("a'l") || token.endsWith("'la")
                || token.endsWith("na")) {
            return true;
        }
        return false;
    }



    @Override
    public boolean isAdverb(String token) {
        if (token.endsWith("maing")) {
            return true;
        }
        return false;
    }


}
