grammar Vallader;

//##############################Parser Rules ######################################

dict :	(entry|error)+ EOF;

entry : OPENINGTAG lexentry endEntry;
error : OPENINGTAG nge endEntry;

lexentry : keyphrase grammatical_info additional_info? nge;
endEntry: CLOSINGTAG NEWLINE;

keyphrase :	phrase infl_info?;

phrase : COMPLEXWORD+ PAREX?;

infl_info: PAREX;

grammatical_info: GRAMM;

additional_info : PAREX;

nge: .*? ;

//##############################Lexer Rules ######################################

OPENINGTAG : '<E>';
CLOSINGTAG : '</E>';

PAREX: '(' ~')'+ ')';

ROMAN : ('I'|'II'|'III'|'IV'|'V'|'VI'|'VII'|'VIII'|'IX'|'X') DOT -> skip ;
ARABIC: NUMBER DOT -> skip;

GRAMM :	'm'	| 'f' | 'm,f'
		| 'adj/adv' | 'adj invar/num' | 'adj invar' | 'adj' | 'adv'
		| 'invar/num'
		| 'prep'
		| 'interj'
		| 'intr' | 'intr/tr' | 'tr ind'	| 'tr'
		| 'mpl'
		| 'fpl'
		| 'cj'
		| 'pron indef' | 'pron pers' | 'pron pers/refl' | 'pron'
		| 'refl'
		| 'fcoll'
		| 'p sg'
;

QM : '?' -> skip;
SL : '/' -> skip;
EXK : '!' -> skip;
DOT : '.';

COMPLEXWORD: TOKEN (COMMA HYP LETTER+)?; //e.g. aciclic,-a

TOKEN  :  (CHARSEQUENCE
			| ALT CHARSEQUENCE
			| CHARSEQUENCE ALT CHARSEQUENCE
			| CHARSEQUENCE ALT)
			((EXK?QM)|EXK)?; //e.g. 'abracadabra!', 'Advent', 'bes-cha', 'bio-'

CHARSEQUENCE: (LETTER|CAPLETTER) LETTER* (HYP LETTER*)* ;
COMMA : ',' -> skip ;
HYP : '-';

ENUM : [a-z] ')' -> skip; //match enumeration 'a)' etc.

SEMICOLON : ';' -> skip;

ALT: '(' LETTER+ ')';

REF : '\u25ba'; //reference symbol |>
RE: '~' -> skip;

fragment CAPLETTER : [A-Z]|'\u00c0'|'\u00c8'|'\u00d2'|'\u00d6'|'\u00dc';
fragment LETTER : [a-z]|'\u00f6'|'\u00fc'|'\u00e0'|'\u00e8'|'\u00f2';
fragment NUMBER: [1-9];

NEWLINE : '\r'? '\n' | 'r'; //return newlines to parser

WS :	[ \t]+ -> skip; // toss out whitespace

ErrorChar : . ;