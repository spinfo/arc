package de.uni_koeln.spinfo.arc.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Andreas on 19.08.2015.
 */
public class TSVEntry {

    String rStichwort;
    String rGenus;
    String rGrammatik;

    private static Pattern romanPattern = Pattern.compile("(^I*V?I{0,3}\\.[ ]+)(.*)");
    private static Pattern bracketPattern = Pattern.compile("([\\(\\[])(.*)([\\)\\]])");

    public TSVEntry(String rStichwort, String rGenus, String rGrammatik) {

        this.rStichwort = rStichwort;
        this.rGenus = rGenus;
        this.rGrammatik = processPos(rGrammatik);

    }

    public String getrStichwort() {
        return rStichwort;
    }

    public String getrGenus() {
        return rGenus;
    }

    public String getrGrammatik() {
        return rGrammatik;
    }

    public String print() {
        String pos = getPos();

        return rStichwort+"$"+pos;
    }

    public String getPos() {
        String pos;

        if (!rGrammatik.isEmpty()) {
            pos = rGrammatik;
        } else if (!rGenus.isEmpty()) {
            pos = rGenus;
        } else {
            //System.out.println(rGrammatik + ", " + rGenus);
            pos = "n/a";
        }
        return pos;
    }

    private String processPos(String pos) {

        // Create matcher object
        Matcher matcher = romanPattern.matcher(pos);

        if (matcher.lookingAt()) {
            pos = matcher.group(2);
        }

        return pos;
    }

    public String reduceStichwort() {

        Matcher matcher = bracketPattern.matcher(rStichwort);
        rStichwort = matcher.replaceAll("");

        if(rStichwort.contains(",")) {
            String[] splits = rStichwort.split(",");
            rStichwort = splits[0];
        }

        if(rStichwort.contains(" ")) {
            return null;
        } else if (rStichwort.isEmpty()) {
            return null;
        }
        return rStichwort;
    }

    public String toString() {
        return rStichwort+":"+rGenus+","+rGrammatik;
    }
}
