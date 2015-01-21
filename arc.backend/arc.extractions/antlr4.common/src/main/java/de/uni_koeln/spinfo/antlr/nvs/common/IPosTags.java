package de.uni_koeln.spinfo.antlr.nvs.common;

/**
 * This interface holds String-Arrays named POS_<NAME_OF_LEXICON> with
 * abbreviations and typical POS-Constants of a lexicon.
 *
 * @author D. Rival
 */

public interface IPosTags {

    public static final String[] POS_TAGS_FINAL = {
            // Verbs
            "V_AVAIR",
            "V_ESSER",
            "V_MOD",
            "V_PP",
            "V_GVRB",
            "V_CLIT",
            // Nouns
            "NN",
            "NN_P",
            // Articles
            "ART",
            // Prepositions
            "PREP",
            // Prepositions fusioned with Articles
            "PREP_ART",
            // Prepositions fusioned with Pronouns
            "PREP_PRON",
            //Adjectives
            "ADJ",
            "ADJ_DEM",
            "ADJ_IND",
            "ADJ_IES",
            "ADJ_POS",
            "ADJ_NUM",
            // Conjunctions
            "CONJ_C",
            "CONJ_S",
            // Adverbs
            "ADV",
            // Interjections
            "INT",
            // Numbers
            "C_NUM",
            // Pronouns
            "PRON_PER",
            "PRON_REL",
            "PRON_DEM",
            "PRON_IND",
            "PRON_IES",
            "PRON_POS",
            "PRON_REF",
            //Particles
            "PART",
            // Symbols
            "NULL",
            // PunctuationMarks
            "P_EOS",
            "P_APO",
            "P_OTH"

    };
    public static final String[] POS_SIGNORELL = {"acad", "acc", "adj",
            "adm", "adv", "adv pron", "agr", "anat", "ant", "arch", "archeol",
            "art", "art def", "art indef", "astrol", "astron", "aux", "av",
            "bibl", "biol", "bot", "caus", "cd", "cf(-->)", "chem", "cid",
            "cin", "cj", "coll", "com", "conj", "conjug", "cons", "cont",
            "cul", "cumpl", "cumpos", "cund", "dat", "def", "dem", "dir",
            "eccl", "econ", "e-e", "e-i", "electr", "elev", "excep", "f",
            "fam", "fig", "fil", "fin", "fis", "fot", "fut", "gastr", "gen",
            "gener", "geogr", "geol", "geom", "giur", "gram", "impers", "impf",
            "impv", "ind", "indef", "indic", "inf", "inform", "inst", "interj",
            "interp", "interr", "intr", "inv", "invers", "irr", "ist", "ling",
            "lit", "loc", "m", "mat", "mec", "med", "met", "meteor", "mil",
            "min", "miner", "mod", "mus", "n", "nav", "neg", "nom", "num",
            "obj", "opt", "p.ex.", "pag", "pass", "peg", "perf", "pers",
            "pict", "pl", "plqperf", "poet", "pop", "poss", "pp", "prep",
            "pres", "pron", "pron dem", "pron indef", "pron interr",
            "pron pers", "pron poss", "pron rel", "psic", "refl", "rel",
            "relig", "resp", "RG", "sal", "scient", "sg", "sp", "spor", "subj",
            "subst", "sviz", "teat", "tecn", "tel", "temp", "tip", "topogr",
            "tr", "tr ind", "traf", "tv", "uff", "v", "v mod", "voc", "vs",
            "vulg",};

    public static final String[] POS_EBNETER = {"adj.", "adj. präd.", "adv.",
            "akk.", "ant:", "art.", "best. art.", "dat.", "dim.:", "DRG", "EN",
            "f.", "fig.", "FN", "Fut.", "ger.", "Imper(ativ)", "Imperf.",
            "Imp(erf). Konj.", "inf.", "interj.", "koll.", "komp.", "konj.",
            "m.", "morph.", "n./ntr.", "neg.", "nom.", "num.", "num. ord.",
            "obl.", "ON", "par:", "pej.", "phon./phonet.", "pl.", "PP-",
            "pp. adj.", "ppräs.", "präd.", "präf.", "pron.", "pron. adj.",
            "pron. subst.", "pron. indef.", "pron. poss.", "pron. pers.",
            "prov:", "ps.", "recipr.", "refl.", "s.", "scil.", "sem.", "sg.",
            "subst.", "sup.", "symp.", "syn:", "unbest. art.", "v.", "v. abs.",
            "v. adv.", "v. adv. itr.", "v. adv. tr.", "v. aux.", "v. cop.",
            "v. def.", "v. fakt.", "v. fakt. refl.", "v. impers.", "v. itr.",
            "v. itr. präp.", "v. itr., tr.", "v. präd.", "v. präp.",
            "v. recipr.", "v. refl.", "v. tr.", "v. tr. (abs.)", "v. tr. ind.",
            "vgl."

    };

    public static final String[] POS_NVS = {"adj", // A d j e k t i v , a d j e
            // k t
            // i v i s c h
            "adv", // A d v e r b , a d v e r b i a l
            "art", // A r t i k e l
            "bibl", // b i b l i s c h , B i b e l
            "coll", // k o l l e k t i v , S a m m e l n a m e
            "dat", // D a t i v
            "def", // d e f i n i t , b e s t i m m t
            "dem", // d e m o n s t r a t i v
            "elektr", // e l e k t r i s c h , E l e k t r i z i t ä t

            "etw", // etwas
            "etw.", // etwas

            "euphem", // e u p h e m i s t i s c h
            "f", // f e m i n i n , w e i b l i c h
            "fam", // f a m i l i ä r
            "f/coll", // w e i b l i c h , k o l l e k t i v
            "fig", // f i g u r a t i v , b i l d l i c h
            "fig.", // f i g u r a t i v , b i l d l i c h
            "gen", // G e n i t i v
            "geol", // g e o l o g i s c h
            "grammat", // g r a m m a t i k a l i s c h
            "hist", // h i s t o r i s c h
            "impers", // u n p e r s ö n l i c h
            "indef", // i n d e f i n i t , u n b e s t i m m t
            "interj", // I n t e r j e k t i o n
            "interrog", // i n t e r r o g a t i v , f r a g e n d

            "intr", "itr", "intr.", // i n t r a n s i t i v

            "invar", // u n v e r ä n d e r l i c h
            "iron", // i r o n i s c h

            "jdm", // j e m a n d e m ( D a t i v )
            "jdm.", // j e m a n d e m ( D a t i v )

            "jds", // j e m a n d e s ( G e n i t i v )
            "jds.", // j e m a n d e s ( G e n i t i v )
            "jem", // j e m a n d ( N o m i n a t i v ) , j e m a n d e n ( A k
            // k u s a t i v )
            "koll", // j e m a n d ( N o m i n a t i v ) , j e m a n d e n ( A k
            // k u s a t i v )
            "lit", // l i t e r a r i s c h
            "m", // m a s k u l i n , m ä n n l i c h
            "med", // m e d i z i n i s c h , M e d i z i n
            "n", // s ä c h l i c h , N e u t r u m
            "neutr", // n e u t r a l , G r u n d f o r m
            "num", // N u m e r a l e , Z a h l w o r t
            "od", // o d e r
            // "p", //Seite
            "pejor", // p e j o r a t i v
            "pers", // p e r s ö n l i c h
            "pl", // P l u r a l , M e h r z a h l
            "poet", // p o e t i s c h
            "pol", // p o l i t i s c h
            "poss", // possessiv, b e s i t z a n z e i g e n d
            "PP", // P a r t i z i p P e r f e k t
            "prep", // P r ä p o s i t i o n
            "präp", // P r ä p o s i t i o n
            "pron", // P r o n o m e n , F ü r w o r t
            "prop", "refl", // r e f l e x i v , r ü c k b e z ü g l i c h
            "rel", // r e l a t i v , b e z ü g l i c h
            "schwd", // s c h w e i z e r d e u t s c h
            "sing", // S i n g u l a r , E i n z a h l
            "subst", // S u b s t a n t i v
            "techn", // t e c h n i s c h , T e c h n i k
            "tr", // t r a n s i t i v
            "v", // V e r b , Z e i t w o r t
            "vgl", // v e r g l e i c h e
            "vulg", // v u l g ä r
            // "nach"
    };

}
