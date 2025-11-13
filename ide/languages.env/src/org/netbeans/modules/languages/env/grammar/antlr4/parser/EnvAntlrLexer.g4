lexer grammar EnvAntlrLexer;

@header{package org.netbeans.modules.languages.env.grammar.antlr4.parser;}

tokens { 
    NL,
    DOLLAR
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
    : [a-z_\u0080-\ufffe][a-z0-9_.\u0080-\ufffe-]*
    ;

fragment KeyIdentiifier
    : [a-z_]+[a-z0-9_]*
    ;

KEY
    : KeyIdentiifier
    ;
COMMENT
    : NewLineComment->skip
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
    : DQuote ->skip,pushMode(DbQuoteString)
    ;

SG_STRING_OPEN
    : SQuoteLiteral ->skip
    ;

EXIT_COMMENT 
    : (' ')+ NewLineComment->skip, popMode
    ;

INTERPOLATED_VAR 
    : '$' {this._input.LA(1) == '{'}? 
    ->type(DOLLAR),pushMode(StringInterpolation)
    ;

//greedy identifier matching
IDENTIFIER_VAR
    : Identifier {this._input.LA(1) == '\n'}? ->skip
    ;

EXIT_VAR_ASSING : NewLine->type(NL), popMode;
INLINE_WS : [ \t]+->skip;
ANY_VALUE : . ->skip;

mode DbQuoteString;

DBQ_TEXT 
    : (Esc [btnfr"'\\] | ~ [$"\r\n\\])+->skip
    ;

DBQ_INTERPOLATED_VAR 
    : '$' {this._input.LA(1) == '{'}? 
    ->type(DOLLAR),pushMode(StringInterpolation)
    ;

DBQ_STRING_CLOSE 
    : DQuote ->skip,popMode
    ;

ANY_DBQ_TEXT : . ->skip;

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
    : . ->skip,popMode
    ;