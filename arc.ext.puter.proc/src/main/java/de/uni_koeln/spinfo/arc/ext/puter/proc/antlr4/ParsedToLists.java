package de.uni_koeln.spinfo.arc.ext.puter.proc.antlr4;

import java.util.List;

/**
 * Created by Andreas on 28.04.2015.
 */
public class ParsedToLists {

    private List<String> entries;
    private List<String> errors;
    private List<String> complexEntries;

    public ParsedToLists(List<String> entries, List<String> errors, List<String> complexEntries) {
        this.entries = entries;
        this.errors = errors;
        this.complexEntries = complexEntries;
    }

    public List<String> getEntries() {
        return entries;
    }

    public List<String> getErrors() {
        return errors;
    }

    public List<String> getComplexEntries() { return complexEntries; }

}
