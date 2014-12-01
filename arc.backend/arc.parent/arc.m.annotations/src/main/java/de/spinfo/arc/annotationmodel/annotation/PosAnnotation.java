package de.spinfo.arc.annotationmodel.annotation;



public interface PosAnnotation extends Annotation {
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
		// Article
		ART,
		// Prepositions
		PREP,
		ADJ,
		ADJ_DIM,
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
		// numbers
		C_NUM,
		// pronouns
		PRON_PER,
		PRON_REL,
		PRON_DIM,
		PRON_IND,
		PRON_IES,
		PRON_POS,
		PRON_REF,
		// Symbols
		NULL,
//		Punctuation	Marks 
		P_EOS,
		P_APO,
		P_OTH,
		
		// none
		
		NOT_TAGGED
	};
	
	public PosTags getPos();
	void setPos(PosTags posTag); 
}

