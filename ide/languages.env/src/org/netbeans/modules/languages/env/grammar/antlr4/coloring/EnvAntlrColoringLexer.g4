lexer grammar EnvAntlrColoringLexer;

@header{package org.netbeans.modules.languages.env.grammar.antlr4.coloring;}

tokens { 
    NL,
    WS,
    COMMENT,
    STRING,
    VALUE,
    KEY,
    KEYWORD,
    OPERATOR,
    ASSIGN_OPERATOR,
    DOLLAR,
    DELIMITATOR
}

options { 
    superClass = LexerAdaptor;
    caseInsensitive = true; 
}    

fragment Esc
   : '\\'
   ;

fragment SQuote
   : '\''
   ;

fragment DQuote
   : '"'
   ;

fragment SQuoteLiteral
   : SQuote (Esc [btnfr"'\\] | ~ ['\\])* SQuote
   ;

fragment NewLine
    : [\r\n]
    ;

fragment NewLineComment
    : '#' ~ [\r\n]* (NL | EOF)
    ;

fragment Identifier 
    : [a-z_\u0080-\ufffe][a-z0-9_.\u0080-\ufffe-]*;

fragment KeyIdentiifier
    : [a-z_]+[a-z0-9_]*
    ;

KEY
    : KeyIdentiifier
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
DB_STRING_OPEN
    : DQuote ->type(STRING),pushMode(DbQuoteString)
    ;
SG_STRING_OPEN
    : SQuoteLiteral ->type(STRING)
    ;
EXIT_COMMENT 
    : (' ')+ NewLineComment->type(COMMENT), popMode
    ;
DELIMITATOR_VAR
    : (',' | '|' | ':') ->type(DELIMITATOR)
    ;
KEYWORD_VAR
    : (
    'true' | 'false' | 'null' | 'on' | '?'+
    | 'prod' | 'production' | 'live'
    | 'development' | 'local' | 'test'
    ) {this._input.LA(1) == '\n'}?->type(KEYWORD)
    ;
INTERPOLATED_VAR 
    : '$' {this._input.LA(1) == '{'}? 
    ->type(DOLLAR),pushMode(StringInterpolation)
;
//greedy identifier matching
IDENTIFIER_VAR
    : Identifier {this._input.LA(1) == '\n'}? 
    ->type(VALUE)
    ;
EXIT_VAR_ASSING : NewLine->type(NL), popMode;
INLINE_WS : [ \t]+->skip;
ANY_VALUE : . ->type(VALUE);

mode DbQuoteString;

DBQ_TEXT : (Esc [btnfr"'\\] | ~ [$"\r\n\\])+->type(STRING);
DBQ_INTERPOLATED_VAR 
    : '$' {this._input.LA(1) == '{'}? 
    ->type(DOLLAR),pushMode(StringInterpolation)
;
DBQ_STRING_CLOSE : DQuote ->type(STRING),popMode;
ANY_DBQ_TEXT : . ->type(STRING);

mode StringInterpolation;
CURLY_OPEN 
    : '{' 
    ;
CURLY_CLOSE
    : '}' ->popMode
    ;
INTERPOLATION_VAR
    : KeyIdentiifier->type(KEY)
    ;
INTERPOLATION_OPERATOR
    : (':' ('+' | '-')? | '?' | '+' | '-')
    ;
VALUE_INTERPOLATION
    : Identifier {this._input.LA(1) == '}'}?
    ;
EXIT_INTERPOLATION
    : . ->type(STRING),popMode
    ;