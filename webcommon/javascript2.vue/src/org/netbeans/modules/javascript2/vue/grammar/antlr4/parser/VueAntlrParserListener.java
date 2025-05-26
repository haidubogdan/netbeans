// Generated from VueAntlrParser.g4 by ANTLR 4.13.1

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

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link VueAntlrParser}.
 */
public interface VueAntlrParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link VueAntlrParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(VueAntlrParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link VueAntlrParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(VueAntlrParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link VueAntlrParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(VueAntlrParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link VueAntlrParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(VueAntlrParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link VueAntlrParser#vueDirective}.
	 * @param ctx the parse tree
	 */
	void enterVueDirective(VueAntlrParser.VueDirectiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link VueAntlrParser#vueDirective}.
	 * @param ctx the parse tree
	 */
	void exitVueDirective(VueAntlrParser.VueDirectiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link VueAntlrParser#vueInterpolation}.
	 * @param ctx the parse tree
	 */
	void enterVueInterpolation(VueAntlrParser.VueInterpolationContext ctx);
	/**
	 * Exit a parse tree produced by {@link VueAntlrParser#vueInterpolation}.
	 * @param ctx the parse tree
	 */
	void exitVueInterpolation(VueAntlrParser.VueInterpolationContext ctx);
}