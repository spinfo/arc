package de.uni_koeln.spinfo.arc.matcher;

import java.util.Map;
import java.util.TreeSet;

/**
 * Created by franciscomondaca on 14/8/15.
 */
public class PuterMatcher extends POSMatcher {


    public PuterMatcher(Map<String, TreeSet<String>> fullForms,
                        String collectionName) {
        super(fullForms, collectionName);
    }


    @Override
    public boolean isIndicativeImperfect(String token) {
        if (token.endsWith("iva") || token.endsWith("ivast")
                || token.endsWith("ivan") || token.endsWith("ivat")) {
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
