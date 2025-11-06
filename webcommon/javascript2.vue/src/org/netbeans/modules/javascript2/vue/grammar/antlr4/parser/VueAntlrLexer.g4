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
 JAVASCRIPT_ATTR_VALUE,
 VAR_TAG,
 ERROR
}

//fragments

fragment Identifier 
    : [a-zA-Z_\u0080-\ufffe][a-zA-Z0-9_\u0080-\ufffe]*;

fragment PropertyChain
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

I_TEMPLATE_TAG_OPEN : '<template' {this.incrementTemplateTagBalance();}->skip;
TEMPLATE_TAG_CLOSE : {this.getTemplateTagBalance() <= 0}? '</' (' ')* 'template' (' ')* '>'->popMode;
I_TEMPLATE_TAG_CLOSE : '</' (' ')* 'template' (' ')* '>' {this.decreaseTemplateTagBalance();}->skip;
VUE_DIRECTIVE_WITH_VALUE :
     (
        'v-' Identifier ('-' Identifier)* (':' PropertyChain)? //attribute bind
         | '@' PropertyChain //on event listener
         | ':' (Identifier | ('[' Identifier ']') ) //short attribute bind
     ) '=' ->type(VUE_DIRECTIVE), pushMode(ATTR_VALUE);

//directives which don't expect value assignment
VUE_DIRECTIVE_SIMPLE : 
    ('v-' (
        'once' //Render the element and component once only, and skip future updates
       | 'else' //if block
       | 'pre' //Skip compilation for this element and all its children
       | 'cloak' //hide un-compiled template
       | 'slot:' Identifier  //slot reference
    )
    | '@' PropertyChain )->type(VUE_DIRECTIVE);

VAR_TAG : '{{' ->pushMode(INSIDE_VAR_INTERPOLATION);
TEMPLATE_OTHER : . ->skip; 
EXIT_TEMPLATE_EOF : EOF->type(ERROR),popMode;

mode ATTR_VALUE;

SCRIPT_ATTR_STRING_VALUE : StringLiteral->type(JAVASCRIPT_ATTR_VALUE),popMode; 

EXIT_SCRIPT_ATTR_EOF : EOF->type(ERROR),popMode;

mode INSIDE_VAR_INTERPOLATION;

VAR_INTERPOLATION_END : '}}' ->type(VAR_TAG), popMode; 
VAR_INTERPOLATION_OTHER : . ->skip; 
EXIT_VAR_INTERPOLATION_EOF : EOF->type(ERROR),popMode;