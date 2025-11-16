lexer grammar EnvAntlrGrammarLexer;

@header{package org.netbeans.modules.languages.env.grammar.antlr4;}

tokens { 
    NL,
    WS,
    COMMENT,
    STRING,
    VALUE,
    NUMBER,
    KEYWORD,
    OPERATOR,
    EMAIL,
    ASSIGN_OPERATOR
}

options { caseInsensitive = true; }    
    
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

fragment Identifier 
    : [a-z_\u0080-\ufffe][a-z0-9_.\u0080-\ufffe-]*; 

fragment Numeric
    : [0-9]+ ('.' [0-9]+ (',' [0-9])? )?
    | [0-9]+ (',' [0-9]+ ('.' [0-9])? )?
    ;
KEY
    : [a-z_]+[a-z0-9_]*
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

OPERATOR_VAR
    : ','->type(OPERATOR)
    ;

EMAIL_VAR
    : [a-z0–9._%+-]+ '@' [a-z0–9.-]+ '.' [a-z]+ ->type(EMAIL)
    ;

PATH
    : ('http' 's'? '://')? Identifier ('/' Identifier)+ '/'?
    ;
KEYWORD_VAR
    : (
    'true'
    | 'false'
    | 'null'
    | 'prod' | 'production' | 'live'
    | 'development' | 'local'
    )->type(KEYWORD)
    ;

IP_VAR :
    [1-9][0-9][0-9]?('.' [0-9][0-9][0-9]?)('.' [0-9][0-9]?[0-9]?)('.' [0-9][0-9]?[0-9]?)->type(NUMBER)
    ;

NUMERIC_VAR 
    : Numeric->type(NUMBER)
    ;

IDENTIFIER_VAR
    : Identifier->type(VALUE)
    ;

EXIT_VAR_ASSING : NewLine->type(NL), popMode;

INLINE_WS : [ \t]+->type(WS),skip;

ANY_VALUE : . ->type(VALUE);