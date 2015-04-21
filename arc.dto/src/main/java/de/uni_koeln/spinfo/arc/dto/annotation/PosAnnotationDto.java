package de.uni_koeln.spinfo.arc.dto.annotation;

public interface PosAnnotationDto extends AnnotationDto, HasDetailsDto {
	/** The constants of the eagles part of speech tags */
	public static enum PosTags {
		// Verbs
		V_AVAIR, V_ESSER, V_MOD, V_PP, V_GVRB, V_CLIT,
		// Verbs with Adverbs
		V_ADV,
		// Nouns
		NN, NN_P,
		// Articles
		ART,
		// Articles with Adjectives
		ART_ADJ,
		// Articles with Nouns
		ART_NN,
		// Articles with Pronouns
		ART_PRON,
		// Prepositions
		PREP,
		// Prepositions fusioned with Articles
		PREP_ART,
		// Prepositions fusioned with Pronouns
		PREP_PRON,
		// Prepositions with Adverbs
		PREP_ADV,
		// Prepositions with Verbs
		PREP_GVRB,
		// Adjectives
		ADJ, ADJ_DEM, ADJ_IND, ADJ_IES, ADJ_POS, ADJ_NUM,
		// Adjectives fusioned with Nouns
		ADJ_NN,
		// Conjunctions
		CONJ_C, CONJ_S,
		// Conjunctions fusioned with Pronouns
		CONJ_PRON,
		// Conjunctions with DET
		CONJ_ART,
		// Conjunctions with Verbs
		CONJ_GVRB,
		// Adverbs
		ADV,
		// Adverbs with Prepositions
		ADV_PREP,
		// Interjections
		INT,
		// Numbers
		C_NUM,
		// Pronouns
		PRON_PER, PRON_REL, PRON_DEM, PRON_IND, PRON_IES, PRON_POS, PRON_REF,
		// Pronouns with Verbs
		PRON_GVRB,
		// Particles
		PART,
		// Symbols
		NULL,
		// PunctuationMarks
		P_EOS, P_APO, P_OTH,
		// NOT TAGGED
		NOT_TAGGED

	};

	public PosTags getPos();

	void setPos(PosTags posTag);
}
