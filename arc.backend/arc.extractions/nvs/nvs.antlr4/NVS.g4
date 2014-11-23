grammar NVS;

   startSymb : (entry|error)+ EOF;
   entry : OPENINGTAG lemma endEntry;
   error : OPENINGTAG nge endEntry;


   lemma: TOKEN TOKEN? POS nge ;
   endEntry: CLOSINGTAG LINEBREAK;

   nge: .*? ;



//LEXER RULES

   OPENINGTAG:'<E>';
   CLOSINGTAG:'</E>';

   POS:
    'art'DOT?| 'art.indef'DOT?| 'art.def'DOT?|
    'adj'DOT?| 'adj'DOT?'invar'DOT?|'adj'DOT?'attrib' DOT?|'adj'DOT?'indef'DOT?|'num'DOT?|'num'DOT?'ord'DOT?|
    'adv' |
    'conj' DOT?|
    'coll'|
    'dem'|
    'grammat'|
    'impers'|
    'indef'|
    'interj'|
    'f'| 'f/coll'|
    'm'| 'm/pl'|  'f/pl'| 'sm'| 'ON' | 'm/f'| 'n'| 'neutr'| 'pl'| 'PN'| 'sm/sf'|  'sm'|
    'poss'  |
    'prep'|
    'präp'|
    'prop'|
    'sing'|
    'subst'|
    'tr'DOT?| 'v'|  'intr'DOT?  |  'itr'|    'refl'|
    'onomat'DOT? |
    'pron'DOT?|
    'pron.detem'DOT?|
    'pron'DOT?'pers'DOT? |   'pron'DOT?'pers'DOT?'obj'DOT? |
    'pron'DOT?'impers'DOT? |
    'pron'DOT?'indef'DOT? |
    'pron'DOT?'rel'DOT? |
    'pron'DOT?'interrog'DOT? |
    'pron'DOT?'ord'DOT? |
    'pron'DOT?'refl'DOT? ;


   RN :('I'|'II'|'III'|'IV') ->skip ;
   PAR: '(' .*? ')' ->skip;

   LINEBREAK : '\r'? '\n' | '\r' ;
   TOKEN  : ('a'..'z'|'A'..'Z') AP? (NameStartChar)*  (S (NameStartChar)*)? (EXK|AST|QM)?;

   INT: [0-9]+ ;

   WS  : (' '|'\t'|'\u000C'|'\u000D')+ -> skip;

   COMMA : ',' ->skip ;
   AST: '*' ->skip;
   SEMICOLON : ';' ->skip;
   S: '-' ;
   ED: INT DOT-> skip;
   R: '#'->skip;
   DOT : '.';
   EXK: '!';
   QM: '?';
   AP: '\'';


   fragment
      NameStartChar
      	:   'A'..'Z'
      	|   'a'..'z'
      	|   '\u00C0'..'\u00D6'
      	|   '\u00D8'..'\u00F6'
      	|   '\u00F8'..'\u02FF'
      	|   '\u0370'..'\u037D'
      	|   '\u037F'..'\u1FFF'
      	|   '\u200C'..'\u200D'
      	|   '\u2070'..'\u218F'
      	|   '\u2C00'..'\u2FEF'
      	|   '\u3001'..'\uD7FF'
      	|   '\uF900'..'\uFDCF'
      	|   '\uFDF0'..'\uFFFD'
      	; // ignores | ['\u10000-'\uEFFFF] ;

    ErrorChar : . ;



