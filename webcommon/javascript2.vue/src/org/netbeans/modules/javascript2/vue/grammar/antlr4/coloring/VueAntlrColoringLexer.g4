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
lexer grammar VueAntlrColoringLexer;

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
  package org.netbeans.modules.javascript2.vue.grammar.antlr4.coloring;
}

options { 
    superClass = ColoringLexerAdaptor;
 }

tokens {
 JAVASCRIPT,
 HTML,
 CSS
}

//fragments

fragment DoubleQuoteStringFragment 
    : '"' ([\\"] | . )*? '"';

fragment SingleQuoteStringFragment 
    : '\'' (~('\'' | '\\') | '\\' . )* '\'';

fragment StringLiteral : DoubleQuoteStringFragment | SingleQuoteStringFragment;

//TOKENS

SCRIPT_TAG_START : '<script' (' ')* ->type(HTML),pushMode(INSIDE_SCRIPT_TAG_START);
STYLE_TAG_OPEN : '<style' (' ')* ->type(HTML),pushMode(INSIDE_STYLE_TAG_START);

OTHER : . ->type(HTML);   

mode INSIDE_STYLE_TAG_START;

STYLE_LANG_ATTR : 'lang=' StringLiteral {this.setStyleLanguage();}->type(HTML);
STYLE_TAG_START_END : '>' ->type(HTML),pushMode(INSIDE_STYLE);
STYLE_TAG_START_OTHER : . ->type(HTML); 
EXIT_STYLE_TAG_START_EOF : EOF->type(HTML),popMode;

mode INSIDE_STYLE;
    
STYLE_TAG_CLOSE : '</style>'->type(HTML),mode(DEFAULT_MODE);
STYLE_OTHER : . ->type(CSS); 
EXIT_STYLE_EOF : EOF->type(HTML),popMode;

mode INSIDE_SCRIPT_TAG_START;

SCRIPT_LANG_ATTR : 'lang=' StringLiteral {this.setScriptLanguage();}->type(HTML);
SCRIPT_TAG_START_END : '>' ->type(HTML),pushMode(INSIDE_SCRIPT);
SCRIPT_TAG_START_OTHER : . ->type(HTML); 
EXIT_SCRIPT_TAG_START_EOF : EOF->type(HTML),popMode;

mode INSIDE_SCRIPT;

SCRIPT_TAG_CLOSE : '</script>'->type(HTML),mode(DEFAULT_MODE);
SCRIPT_TAG_OTHER : . ->type(JAVASCRIPT); 
EXIT_SCRIPT_TAG_EOF : EOF->type(HTML),popMode;    
