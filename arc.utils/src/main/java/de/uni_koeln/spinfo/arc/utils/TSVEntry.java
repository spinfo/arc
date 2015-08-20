package de.uni_koeln.spinfo.arc.utils;

/**
 * Created by Andreas on 19.08.2015.
 */
public class TSVEntry {

    String rStichwort;
    String rGenus;
    String rGrammatik;

    public TSVEntry(String rStichwort, String rGenus, String rGrammatik) {
        this.rStichwort = rStichwort;
        this.rGenus = rGenus;
        this.rGrammatik = rGrammatik;
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
        String lemma = reduceStichwort();
        String pos = getPos();

        return lemma+"$"+pos;
    }

    public String getPos() {
        String pos;

        if (!rGrammatik.isEmpty()) {
            pos = rGrammatik;
        } else if (!rGenus.isEmpty()) {
            pos = rGenus;
        } else {
            System.out.println(rGrammatik + ", " + rGenus);
            pos = "n/a";
        }
        return pos;
    }

    public String reduceStichwort() {

        if(rStichwort.contains(",")) {
            String[] splits = rStichwort.split(",");
            rStichwort = splits[0];
        }
        if(rStichwort.contains(" ")) {
            String[] splits = rStichwort.split(" ");
            rStichwort = splits[0];
        }
        return rStichwort;
    }

    public String toString() {
        return rStichwort+":"+rGenus+","+rGrammatik;
    }
}
