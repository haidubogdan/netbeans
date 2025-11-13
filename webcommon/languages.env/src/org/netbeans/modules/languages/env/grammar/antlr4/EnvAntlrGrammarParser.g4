parser grammar EnvAntlrGrammarParser;

@header{package org.netbeans.modules.languages.env.grammar.antlr4;}

options { 
    superClass = ParserAdaptor;
    tokenVocab = EnvAntlrGrammarLexer;
 }

expression
    : line* EOF
    ;

line
    : comment | varAssign | newLine
    ;

comment
    : COMMENT
    ;

newLine
    : NL
    ;

varAssign
    : key assignOperator value+ (comment | newLine | EOF)
    ;

assignOperator
    : ASSIGN_OPERATOR
    ;

key
    : KEY
    ;

value:
    (STRING | VALUE)
    ;