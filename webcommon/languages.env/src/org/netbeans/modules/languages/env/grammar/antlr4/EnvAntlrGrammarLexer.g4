lexer grammar EnvAntlrGrammarLexer;

@header{package org.netbeans.modules.languages.env.grammar.antlr4;}

tokens { 
    NL,
    WS,
    COMMENT,
    STRING,
    VALUE,
    ASSIGN_OPERATOR
}

fragment Esc
   : '\\'
   ;

fragment SQuoteLiteral
   : SQuote (Esc [btnfr"'\\] | ~ ['\\])* SQuote
   ;
fragment DQuoteLiteral
   : DQuote (Esc [btnfr"'\\] | ~ ["\r\n\\])* DQuote
   ;
    
fragment SQuote
   : '\''
   ;

fragment DQuote
   : '"'
   ;

fragment NewLine
    : [\r\n]
    ;

fragment AnyValue
    : ~('=' | [\r\n])
    ;

fragment NewLineComment
    : '#' ~ [\r\n]* (NL | EOF)
    ;


KEY
    : [a-zA-Z_]+[a-zA-Z0-9_]*
    ;

COMMENT
    : NewLineComment
    ;

ASSIGN_OPERATOR
    : ('=' | ':')->pushMode(VarAssign)
    ;

NL
    : NewLine+
    ;

WS
    : [ \t]+ ->skip
    ;

ERROR
    : .
    ;

mode VarAssign;

STRING : SQuoteLiteral | DQuoteLiteral;

EXIT_COMMENT : ' ' NewLineComment->type(COMMENT), popMode;

EXIT_VAR_ASSING : NewLine->type(NL), popMode;

INLINE_WS : [ \t]+->type(WS),skip;

ANY_VALUE : . ->type(VALUE);