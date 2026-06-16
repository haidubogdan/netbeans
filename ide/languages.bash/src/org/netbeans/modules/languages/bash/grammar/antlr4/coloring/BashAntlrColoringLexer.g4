/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
lexer grammar BashAntlrColoringLexer;

@header{
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.netbeans.modules.languages.bash.grammar.antlr4.coloring;}

tokens { 
    NL,
    WS,
    COMMENT,
    STRING,
    KEYWORD,
    IDENTIFIER,
    VARIABLE,
    NUMBER,
    DELIMITER,
    OPERATOR,
    SEPARATOR,
    ASSIGN_OPERATOR,
    DOLLAR,
    DELIMITER
}

options { 
    superClass = LexerAdaptor;
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

fragment BackTickQuote
   : '`'
   ;

fragment SQuoteLiteral
   : SQuote (Esc [btnfr"'\\] | ~ ['\\])* SQuote
   ;

fragment NewLine
    : [\r\n]
    ;

fragment WhiteSpace
    : [ \t]
    ;

fragment NewLineComment
    : '#' ~ [\r\n]* (NL | EOF)
    ;

fragment Identifier 
    : [a-zA-Z_\u0080-\ufffe][a-zA-Z0-9_.\u0080-\ufffe-]*;

fragment VarName
    : [a-zA-Z_][a-zA-Z0-9_]*
;

fragment AppKeywords
    : 'npm' | 'php' | 'mysql' | 'python'
    ;

fragment BashKeyword
    : 'if' | 'elif' | 'else' | 'fi'
    | 'for' | 'then' | 'do' | 'while'
    | 'in' | 'done'
    ;

fragment BashCommands
    : 'sleep' | 'exit' | 'read' | 'echo'
    | 'mkdir' | 'touch'
    | 'cp' | 'mv' | 'rm'
    | 'cd' | 'ls' | 'pwd'
    | 'grep' | 'awk' | 'sed'
    | 'cat' | 'tail' | 'head'
    | 'ps' | 'top' | 'kill'
    | 'df' | 'du'
    | 'curl' | 'wget'
    | 'ssh'
    | 'rsync'
    | 'zip' | 'unzip'
    | 'chmod' | 'chown' | 'chgrp'
    | 'cron' | 'exec' | 'ln'
    ;

fragment Delimiter
    : '(' | ')' | '[' | ']' | '{' | '}'
    ;

fragment Operator
    : ('+' | '-' | '*' | '/' | '%' | '<' | '>' | '&&' | '||' | '!' | '$#')
    ;

fragment Separator
    : (',')
    ;

fragment Number
    : '-'? [0-9]+
    ;

BASH_KEYWORD : AppKeywords | BashKeyword | BashCommands;

COMMAND_OPTION
    : '-' ('-')? VarName
    ;

NUMBER
    : Number
    ;

STRING
    : SQuoteLiteral;

COMMENT
    : NewLineComment
    ;

DB_STRING_OPEN
    : DQuote ->type(STRING),pushMode(DbQuoteString)
    ;

B_STRING_OPEN
    : BackTickQuote ->type(STRING),pushMode(BackQuotedString)
    ;

SG_STRING_OPEN
    : SQuoteLiteral ->type(STRING)
    ;

IDENTIFIER 
    : '.'? Identifier
    ;

HEREDOC_OPEN : '<<' ('-' | WhiteSpace) 
    (Identifier | SQuote Identifier SQuote) {setHeredocDelimiter();} 
    ->pushMode(HereDoc)
    ;

VARIABLE
    : '$' VarName
    ;

DELIMITER
    : Delimiter
    ;

ASSIGN_OPERATOR
    : ('=' | ':')->pushMode(VarAssign)
    ;

OPERATOR
    : Operator
    ;

SEPARATOR
    : Separator
    ;

BREAK :
    '\\'
    ;

SEMICOLON
    : ';'
    ;

NL
    : NewLine+
    ;
WS
    : WhiteSpace+ ->skip
    ;

ERROR
    : .
    ;


mode VarAssign;
VAR_DB_STRING_OPEN
    : DQuote ->type(STRING),pushMode(DbQuoteString)
    ;

VAR_B_STRING_OPEN
    : BackTickQuote ->type(STRING),pushMode(BackQuotedString)
    ;

VAR_SG_STRING_OPEN
    : SQuoteLiteral ->type(STRING)
    ;
EXIT_COMMENT 
    : (' ')+ NewLineComment->type(COMMENT), popMode
    ;

DELIMITER_VAR
    : Delimiter->type(DELIMITER)
    ;

SEPARATOR_VAR
    : Separator ->type(SEPARATOR)
    ;

NUMBER_VAR
    : Number->type(NUMBER)
    ;

INTERPOLATED_VAR 
    : '$' {this._input.LA(1) == '{'}?
    ->type(DOLLAR),pushMode(StringInterpolation)
;

IDENTIFIER_VAR
    : '$' VarName
    ->type(IDENTIFIER)
    ;
EXIT_VAR_ASSING : NewLine->type(NL), popMode;
INLINE_WS : [ \t]+->skip;
ANY_VALUE : . ->type(IDENTIFIER);

mode DbQuoteString;

DBQ_TEXT : (Esc [btnfr"'\\] | ~ [$"\r\n\\])+->type(STRING);

DBQ_INTERPOLATED_VAR 
    : '$' {this._input.LA(1) == '{'}?
    ->type(DOLLAR),pushMode(StringInterpolation)
;

DBQ_IDENTIFIER
    : '$' VarName
    ->type(VARIABLE)
    ;

DBQ_STRING_CLOSE : DQuote ->type(STRING),popMode;
ANY_DBQ_TEXT : . ->type(STRING);

mode BackQuotedString;

BQ_TEXT : (Esc [btnfr"'`\\] | ~ [$`\r\n\\])+->type(STRING);
BQ_INTERPOLATED_VAR 
    : '$' '{'?
    ->type(DOLLAR),pushMode(StringInterpolation)
;
BQ_STRING_CLOSE : BackTickQuote ->type(STRING),popMode;
ANY_BQ_TEXT : . ->type(STRING);

mode StringInterpolation;

CURLY_OPEN 
    : ('{' {this.resetInterpolationKeyAdded();})->type(DELIMITER)
    ;
CURLY_CLOSE
    : '}' ->type(DELIMITER),popMode
    ;
INTERPOLATION_VAR
    : {!this.keyTokenAdded()}? Identifier {this.consumeKeyToken();}->type(IDENTIFIER)
    ;

/*
from https://dotenvx.com/docs/env-file
${VAR:-default} -> value of VAR if set and non-empty, otherwise default
${VAR-default} -> value of VAR if set, otherwise default

${VAR:+alternate} -> value of alternate if VAR is set and non-empty, otherwise empty ''
${VAR+alternate} -> value of alternate if VAR is set and non-empty, otherwise empty ''
*/
INTERPOLATION_OPERATOR
    : (':' ('+' | '-')? | '?' | '+' | '-')->type(OPERATOR)
    ;

VALUE_INTERPOLATION
    : . ->type(IDENTIFIER)
    ;

mode HereDoc;

HEREDOC_CLOSE : NewLine Identifier {validateHeredocDelimiter()}? ->popMode;

HEREDOC_IDENTIFIER : Identifier->type(STRING);

OTHER_HEREDOC 
    : . ->type(STRING)
    ;