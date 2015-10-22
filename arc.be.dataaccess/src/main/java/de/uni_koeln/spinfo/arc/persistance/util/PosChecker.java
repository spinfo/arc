package de.uni_koeln.spinfo.arc.persistance.util;

import de.uni_koeln.spinfo.arc.annotationmodel.annotation.PosAnnotation;

public class PosChecker {
    /**
     * Checks if the String is a valid pos tagger option. Only those Strings which
     * are equal to the pos enums are valid and can be saved.
     *
     * @param posOption the pos tag string to check
     * @return the String if it is conform to the PosTags constants
     */
    public static String checkIfValid(String posOption) {
        for (PosAnnotation.PosTags posTag : PosAnnotation.PosTags.values()) {
            if (posTag.toString().equals(posOption))
                return posOption;
        }
        return null;//throw new IllegalArgumentException("The Pos String must be equal to the String of the given PosAnnotation.PosTags strings!");
    }
}