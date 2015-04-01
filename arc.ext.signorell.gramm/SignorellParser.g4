parser grammar SignorellParser;

options { tokenVocab=SignorellLexer;}

   startSymb : (entry|error)+ EOF;
   entry : OPENINGTAG lemma endEntry;
   error : OPENINGTAG nge endEntry;

   lemma: TOKEN TOKEN? POS nge ;
   endEntry: CLOSINGTAG LINEBREAK;

   nge: .*? ;

