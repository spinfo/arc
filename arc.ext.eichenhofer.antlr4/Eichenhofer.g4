grammar Eichenhofer;

   startSymb : (entry|error)+ EOF;
   entry : OPENINGTAG lemma endEntry;
   error : OPENINGTAG nge endEntry;

   lemma: COMPLEXTOKEN PAR? POS nge ;
   endEntry: CLOSINGTAG LINEBREAK;

   nge: .*? ;

      OPENINGTAG:'<E>';
      CLOSINGTAG:'</E>';

         POS:
                    'm'|
                    'f'|
                    '/'|
                    'fpl'|
                    'mpl'|
                    'm' | 'rn'|
                    'mf'|
                    'tr'|'fr'|
                    'tr/int'|
                    'art'|
                    'adj'|
                    'adv'|
                    'interj'|
                    'conj'|
                    'prep'|
                    'pron'|
                    'int';


         EI: ('➀'|'©'|'➁') ->skip;

         RN :('I'|'II'|'III'|'IV') ->skip ;


         OSP: '[';
         OCP: '(';



         CSP: ']';
         CCP: ')';

         PAR: (OSP|OCP) S? NameStartChar+ (CSP|CCP);

         LINEBREAK : '\r'? '\n' | '\r' ;

         COMPLEXTOKEN: (

         //e.g.: <E>divorziịeu, -ẹada adj geschieden adj (von</E>
         PAR? NameStartChar+ COMMA WS S NameStartChar+ |

         //e.g.: <E>(d)oret m Katzengold n</E> || <E>(di)schmẹter (-matagn) tr (pp -mess)</E> ||
         PAR? (NameStartChar)+ PAR? (NameStartChar)+ EXK? (INT|PAR)?|

         //e.g.: <E>dormulạint2 m Schlafmütze/</E>
         NameStartChar+ EXK? (INT|PAR)?
         ) ;

         INT: [0-9]+ ;



         S: '-' ;

         DOT : '.';
         EXK: '!';
         QM: '?';
         AP: '\'';

         ED: INT DOT-> skip;
         R: '#'->skip;
         COMMA : ',' ;
         AST: '*' ->skip;
         SEMICOLON : ';';
         WS  : (' '|'\t'|'\u000C'|'\u000D')+ -> skip;


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
