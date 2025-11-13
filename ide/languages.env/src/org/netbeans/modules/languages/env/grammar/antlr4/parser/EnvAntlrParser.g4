parser grammar EnvAntlrParser;

@header{package org.netbeans.modules.languages.env.grammar.antlr4.parser;}

options { 
    superClass = ParserAdaptor;
    tokenVocab = EnvAntlrLexer;
 }

envFile
    : line* EOF
    ;

line
    : varAssign | newLine
    ;

newLine
    : NL
    ;

varAssign
    : KEY assignOperator interpolatedKey* (newLine | EOF)
    ;

assignOperator
    : ASSIGN_OPERATOR
    ;

interpolatedKey
    : DOLLAR 
    CURLY_OPEN KEY (INTERPOLATION_OPERATOR VALUE_INTERPOLATION)? CURLY_CLOSE
    ;