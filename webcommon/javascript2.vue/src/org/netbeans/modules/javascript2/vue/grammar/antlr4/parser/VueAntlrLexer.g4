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
lexer grammar VueAntlrLexer;

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
  package org.netbeans.modules.javascript2.vue.grammar.antlr4.parser;
}

options { 
    superClass = LexerAdaptor;
 }

tokens {
 TEMPLATE_TAG_OPEN,
 VUE_DIRECTIVE,
 JAVASCRIPT,
 JAVASCRIPT_ATTR_VALUE,
 VAR_TAG,
 HTML
}

//fragments

fragment Identifier 
    : [a-zA-Z_\u0080-\ufffe][a-zA-Z0-9_\u0080-\ufffe]*;

fragment ArgumentExtra
    : (Identifier ('.' Identifier)*) 
      | ('[' Identifier ']');

fragment DoubleQuoteStringFragment 
    : '"' ([\\"] | . )*? '"';

fragment SingleQuoteStringFragment 
    : '\'' (~('\'' | '\\') | '\\' . )* '\'';

fragment StringLiteral : DoubleQuoteStringFragment | SingleQuoteStringFragment;

//TOKENS

TEMPLATE_TAG_OPEN : '<template' ->pushMode(INSIDE_TEMPLATE);

OTHER : . ->skip;   
    
mode INSIDE_TEMPLATE;

TEMPLATE_TAG_CLOSE : '</template>'->popMode;
VUE_DIRECTIVE_TEMPLATE : ('v-' Identifier ('-' Identifier)* (':' ArgumentExtra)? | '@' ArgumentExtra | ':' (Identifier | ('[' Identifier ']') )) '=' ->type(VUE_DIRECTIVE),pushMode(INSIDE_SCRIPT_ATTR);
VUE_DIRECTIVE_SIMPLE : 'v-' ( 'once' | 'else' | 'pre' | 'cloak' | 'slot:' Identifier  ) ->type(VUE_DIRECTIVE);

VAR_TAG : '{{' ->pushMode(INSIDE_VAR_INTERPOLATION);
TEMPLATE_OTHER : . ->skip; 
EXIT_TEMPLATE_EOF : EOF->type(HTML),popMode;

mode INSIDE_SCRIPT_ATTR;

SCRIPT_ATTR_STRING_VALUE : StringLiteral->type(JAVASCRIPT_ATTR_VALUE),popMode; 

EXIT_SCRIPT_ATTR_EOF : EOF->type(HTML),popMode;

mode INSIDE_VAR_INTERPOLATION;

VAR_INTERPOLATION_END : '}}' ->type(VAR_TAG), popMode; 
VAR_INTERPOLATION_OTHER : . ->skip; 
EXIT_VAR_INTERPOLATION_EOF : EOF->type(HTML),popMode;