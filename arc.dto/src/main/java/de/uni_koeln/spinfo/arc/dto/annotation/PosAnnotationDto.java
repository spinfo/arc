package de.uni_koeln.spinfo.arc.dto.annotation;



public interface PosAnnotationDto extends AnnotationDto, HasDetailsDto {
	/**The constants of the eagles part of speech tags*/
	public static enum PosTags {
		// Verbs
        V_AVAIR,
        V_ESSER,
        V_MOD,
        V_PP,
        V_GVRB,
        V_CLIT,
        // Nouns
        NN,
        NN_P,
        // Articles
        ART,
        // Prepositions
        PREP,
        // Prepositions fusioned with Articles
        PREP_A,
        //Adjectives
        ADJ,
        ADJ_DEM,
        ADJ_IND,
        ADJ_IES,
        ADJ_POS,
        ADJ_NUM,
        // Conjunctions
        CONJ_C,
        CONJ_S,
        // Adverbs
        ADV,
        // Interjections
        INT,
        // Numbers
        C_NUM,
        // Pronouns
        PRON_PER,
        PRON_REL,
        PRON_DEM,
        PRON_IND,
        PRON_IES,
        PRON_POS,
        PRON_REF,
        //Particles
        PART,
        // Symbols
        NULL,
        // PunctuationMarks
        P_EOS,
        P_APO,
        P_OTH,

        // none
        NOT_TAGGED,

	};
	
	public PosTags getPos();
	void setPos(PosTags posTag); 
}

