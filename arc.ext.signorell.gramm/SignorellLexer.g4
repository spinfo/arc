lexer grammar SignorellLexer;

    OPENINGTAG:'<E>';
    CLOSINGTAG:'</E>';

          POS:
                         'adj'
                         |'adv'
                         |'f'
                         |'m'
                         |'rn'
                         |'f/pl'
                         |'m/f'

                         |'v'
                         |'v refl'
                         |'v intr'
                         |'v tr'

                         |'refl'
                         |'tr'
                         |'fr'
                         |'intr'


                         |'pron'

                         |'prep'

                         |'interj';

      RN :('I'|'II'|'III'|'IV') ->skip ;


      PAR: WS '('S? NameStartChar*')';


      LINEBREAK : '\r'? '\n' | '\r' ;
      TOKEN  : S? (NameStartChar) (NameStartChar)* (EXK|AST|QM|INT|PAR)? ;

      INT: [0-9]+ ;



      S: '-' ;


      RP: '('->skip;
      LP: ')'->skip;
      DOT : '.'->skip;
      EXK: '!';
      QM: '?';
      AP: '\'';


      ED: INT DOT-> skip;
      R: '#'->skip;
      COMMA : ',' ->skip ;
      AST: '*' ->skip;
      SEMICOLON : ';' ->skip;

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




