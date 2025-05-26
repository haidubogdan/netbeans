// Generated from VueAntlrLexer.g4 by ANTLR 4.13.1

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

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class VueAntlrLexer extends LexerAdaptor {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		TEMPLATE_TAG_OPEN=1, VUE_DIRECTIVE=2, JAVASCRIPT=3, JAVASCRIPT_ATTR_VALUE=4, 
		VAR_TAG=5, HTML=6, OTHER=7, TEMPLATE_TAG_CLOSE=8, TEMPLATE_OTHER=9, VAR_INTERPOLATION_OTHER=10, 
		VAR_INTERPOLATION_END=11;
	public static final int
		INSIDE_TEMPLATE=1, INSIDE_SCRIPT_ATTR=2, INSIDE_VAR_INTERPOLATION=3;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE", "INSIDE_TEMPLATE", "INSIDE_SCRIPT_ATTR", "INSIDE_VAR_INTERPOLATION"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"Identifier", "ArgumentExtra", "DoubleQuoteStringFragment", "SingleQuoteStringFragment", 
			"StringLiteral", "TEMPLATE_TAG_OPEN", "OTHER", "TEMPLATE_TAG_CLOSE", 
			"VUE_DIRECTIVE_TEMPLATE", "VUE_DIRECTIVE_SIMPLE", "VAR_TAG", "TEMPLATE_OTHER", 
			"EXIT_TEMPLATE_EOF", "SCRIPT_ATTR_STRING_VALUE", "EXIT_SCRIPT_ATTR_EOF", 
			"VAR_INTERPOLATION_END", "VAR_INTERPOLATION_OTHER", "EXIT_VAR_INTERPOLATION_EOF"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'<template'", null, null, null, "'{{'", null, null, "'</template>'", 
			null, null, "'}}'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "TEMPLATE_TAG_OPEN", "VUE_DIRECTIVE", "JAVASCRIPT", "JAVASCRIPT_ATTR_VALUE", 
			"VAR_TAG", "HTML", "OTHER", "TEMPLATE_TAG_CLOSE", "TEMPLATE_OTHER", "VAR_INTERPOLATION_OTHER", 
			"VAR_INTERPOLATION_END"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public VueAntlrLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "VueAntlrLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u000b\u00d9\u0006\uffff\uffff\u0006\uffff\uffff\u0006\uffff"+
		"\uffff\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007"+
		"\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0001\u0000\u0001"+
		"\u0000\u0005\u0000+\b\u0000\n\u0000\f\u0000.\t\u0000\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0005\u00013\b\u0001\n\u0001\f\u00016\t\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001<\b\u0001\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0005\u0002A\b\u0002\n\u0002\f\u0002D\t"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0005\u0003L\b\u0003\n\u0003\f\u0003O\t\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0004\u0001\u0004\u0003\u0004U\b\u0004\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b"+
		"\u0001\b\u0001\b\u0001\b\u0001\b\u0005\b{\b\b\n\b\f\b~\t\b\u0001\b\u0001"+
		"\b\u0003\b\u0082\b\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0003\b\u008c\b\b\u0003\b\u008e\b\b\u0001\b\u0001\b\u0001\b"+
		"\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0003\t\u00af\b\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0001\u0011\u0001B\u0000\u0012\u0004\u0000\u0006\u0000\b\u0000"+
		"\n\u0000\f\u0000\u000e\u0001\u0010\u0007\u0012\b\u0014\u0000\u0016\u0000"+
		"\u0018\u0005\u001a\t\u001c\u0000\u001e\u0000 \u0000\"\u000b$\n&\u0000"+
		"\u0004\u0000\u0001\u0002\u0003\u0004\u0004\u0000AZ__az\u0080\u8000\ufffe"+
		"\u0005\u000009AZ__az\u0080\u8000\ufffe\u0002\u0000\"\"\\\\\u0002\u0000"+
		"\'\'\\\\\u00e1\u0000\u000e\u0001\u0000\u0000\u0000\u0000\u0010\u0001\u0000"+
		"\u0000\u0000\u0001\u0012\u0001\u0000\u0000\u0000\u0001\u0014\u0001\u0000"+
		"\u0000\u0000\u0001\u0016\u0001\u0000\u0000\u0000\u0001\u0018\u0001\u0000"+
		"\u0000\u0000\u0001\u001a\u0001\u0000\u0000\u0000\u0001\u001c\u0001\u0000"+
		"\u0000\u0000\u0002\u001e\u0001\u0000\u0000\u0000\u0002 \u0001\u0000\u0000"+
		"\u0000\u0003\"\u0001\u0000\u0000\u0000\u0003$\u0001\u0000\u0000\u0000"+
		"\u0003&\u0001\u0000\u0000\u0000\u0004(\u0001\u0000\u0000\u0000\u0006;"+
		"\u0001\u0000\u0000\u0000\b=\u0001\u0000\u0000\u0000\nG\u0001\u0000\u0000"+
		"\u0000\fT\u0001\u0000\u0000\u0000\u000eV\u0001\u0000\u0000\u0000\u0010"+
		"b\u0001\u0000\u0000\u0000\u0012f\u0001\u0000\u0000\u0000\u0014\u008d\u0001"+
		"\u0000\u0000\u0000\u0016\u0094\u0001\u0000\u0000\u0000\u0018\u00b2\u0001"+
		"\u0000\u0000\u0000\u001a\u00b7\u0001\u0000\u0000\u0000\u001c\u00bb\u0001"+
		"\u0000\u0000\u0000\u001e\u00c0\u0001\u0000\u0000\u0000 \u00c5\u0001\u0000"+
		"\u0000\u0000\"\u00ca\u0001\u0000\u0000\u0000$\u00d0\u0001\u0000\u0000"+
		"\u0000&\u00d4\u0001\u0000\u0000\u0000(,\u0007\u0000\u0000\u0000)+\u0007"+
		"\u0001\u0000\u0000*)\u0001\u0000\u0000\u0000+.\u0001\u0000\u0000\u0000"+
		",*\u0001\u0000\u0000\u0000,-\u0001\u0000\u0000\u0000-\u0005\u0001\u0000"+
		"\u0000\u0000.,\u0001\u0000\u0000\u0000/4\u0003\u0004\u0000\u000001\u0005"+
		".\u0000\u000013\u0003\u0004\u0000\u000020\u0001\u0000\u0000\u000036\u0001"+
		"\u0000\u0000\u000042\u0001\u0000\u0000\u000045\u0001\u0000\u0000\u0000"+
		"5<\u0001\u0000\u0000\u000064\u0001\u0000\u0000\u000078\u0005[\u0000\u0000"+
		"89\u0003\u0004\u0000\u00009:\u0005]\u0000\u0000:<\u0001\u0000\u0000\u0000"+
		";/\u0001\u0000\u0000\u0000;7\u0001\u0000\u0000\u0000<\u0007\u0001\u0000"+
		"\u0000\u0000=B\u0005\"\u0000\u0000>A\u0007\u0002\u0000\u0000?A\t\u0000"+
		"\u0000\u0000@>\u0001\u0000\u0000\u0000@?\u0001\u0000\u0000\u0000AD\u0001"+
		"\u0000\u0000\u0000BC\u0001\u0000\u0000\u0000B@\u0001\u0000\u0000\u0000"+
		"CE\u0001\u0000\u0000\u0000DB\u0001\u0000\u0000\u0000EF\u0005\"\u0000\u0000"+
		"F\t\u0001\u0000\u0000\u0000GM\u0005\'\u0000\u0000HL\b\u0003\u0000\u0000"+
		"IJ\u0005\\\u0000\u0000JL\t\u0000\u0000\u0000KH\u0001\u0000\u0000\u0000"+
		"KI\u0001\u0000\u0000\u0000LO\u0001\u0000\u0000\u0000MK\u0001\u0000\u0000"+
		"\u0000MN\u0001\u0000\u0000\u0000NP\u0001\u0000\u0000\u0000OM\u0001\u0000"+
		"\u0000\u0000PQ\u0005\'\u0000\u0000Q\u000b\u0001\u0000\u0000\u0000RU\u0003"+
		"\b\u0002\u0000SU\u0003\n\u0003\u0000TR\u0001\u0000\u0000\u0000TS\u0001"+
		"\u0000\u0000\u0000U\r\u0001\u0000\u0000\u0000VW\u0005<\u0000\u0000WX\u0005"+
		"t\u0000\u0000XY\u0005e\u0000\u0000YZ\u0005m\u0000\u0000Z[\u0005p\u0000"+
		"\u0000[\\\u0005l\u0000\u0000\\]\u0005a\u0000\u0000]^\u0005t\u0000\u0000"+
		"^_\u0005e\u0000\u0000_`\u0001\u0000\u0000\u0000`a\u0006\u0005\u0000\u0000"+
		"a\u000f\u0001\u0000\u0000\u0000bc\t\u0000\u0000\u0000cd\u0001\u0000\u0000"+
		"\u0000de\u0006\u0006\u0001\u0000e\u0011\u0001\u0000\u0000\u0000fg\u0005"+
		"<\u0000\u0000gh\u0005/\u0000\u0000hi\u0005t\u0000\u0000ij\u0005e\u0000"+
		"\u0000jk\u0005m\u0000\u0000kl\u0005p\u0000\u0000lm\u0005l\u0000\u0000"+
		"mn\u0005a\u0000\u0000no\u0005t\u0000\u0000op\u0005e\u0000\u0000pq\u0005"+
		">\u0000\u0000qr\u0001\u0000\u0000\u0000rs\u0006\u0007\u0002\u0000s\u0013"+
		"\u0001\u0000\u0000\u0000tu\u0005v\u0000\u0000uv\u0005-\u0000\u0000vw\u0001"+
		"\u0000\u0000\u0000w|\u0003\u0004\u0000\u0000xy\u0005-\u0000\u0000y{\u0003"+
		"\u0004\u0000\u0000zx\u0001\u0000\u0000\u0000{~\u0001\u0000\u0000\u0000"+
		"|z\u0001\u0000\u0000\u0000|}\u0001\u0000\u0000\u0000}\u0081\u0001\u0000"+
		"\u0000\u0000~|\u0001\u0000\u0000\u0000\u007f\u0080\u0005:\u0000\u0000"+
		"\u0080\u0082\u0003\u0006\u0001\u0000\u0081\u007f\u0001\u0000\u0000\u0000"+
		"\u0081\u0082\u0001\u0000\u0000\u0000\u0082\u008e\u0001\u0000\u0000\u0000"+
		"\u0083\u0084\u0005@\u0000\u0000\u0084\u008e\u0003\u0006\u0001\u0000\u0085"+
		"\u008b\u0005:\u0000\u0000\u0086\u008c\u0003\u0004\u0000\u0000\u0087\u0088"+
		"\u0005[\u0000\u0000\u0088\u0089\u0003\u0004\u0000\u0000\u0089\u008a\u0005"+
		"]\u0000\u0000\u008a\u008c\u0001\u0000\u0000\u0000\u008b\u0086\u0001\u0000"+
		"\u0000\u0000\u008b\u0087\u0001\u0000\u0000\u0000\u008c\u008e\u0001\u0000"+
		"\u0000\u0000\u008dt\u0001\u0000\u0000\u0000\u008d\u0083\u0001\u0000\u0000"+
		"\u0000\u008d\u0085\u0001\u0000\u0000\u0000\u008e\u008f\u0001\u0000\u0000"+
		"\u0000\u008f\u0090\u0005=\u0000\u0000\u0090\u0091\u0001\u0000\u0000\u0000"+
		"\u0091\u0092\u0006\b\u0003\u0000\u0092\u0093\u0006\b\u0004\u0000\u0093"+
		"\u0015\u0001\u0000\u0000\u0000\u0094\u0095\u0005v\u0000\u0000\u0095\u0096"+
		"\u0005-\u0000\u0000\u0096\u00ae\u0001\u0000\u0000\u0000\u0097\u0098\u0005"+
		"o\u0000\u0000\u0098\u0099\u0005n\u0000\u0000\u0099\u009a\u0005c\u0000"+
		"\u0000\u009a\u00af\u0005e\u0000\u0000\u009b\u009c\u0005e\u0000\u0000\u009c"+
		"\u009d\u0005l\u0000\u0000\u009d\u009e\u0005s\u0000\u0000\u009e\u00af\u0005"+
		"e\u0000\u0000\u009f\u00a0\u0005p\u0000\u0000\u00a0\u00a1\u0005r\u0000"+
		"\u0000\u00a1\u00af\u0005e\u0000\u0000\u00a2\u00a3\u0005c\u0000\u0000\u00a3"+
		"\u00a4\u0005l\u0000\u0000\u00a4\u00a5\u0005o\u0000\u0000\u00a5\u00a6\u0005"+
		"a\u0000\u0000\u00a6\u00af\u0005k\u0000\u0000\u00a7\u00a8\u0005s\u0000"+
		"\u0000\u00a8\u00a9\u0005l\u0000\u0000\u00a9\u00aa\u0005o\u0000\u0000\u00aa"+
		"\u00ab\u0005t\u0000\u0000\u00ab\u00ac\u0005:\u0000\u0000\u00ac\u00ad\u0001"+
		"\u0000\u0000\u0000\u00ad\u00af\u0003\u0004\u0000\u0000\u00ae\u0097\u0001"+
		"\u0000\u0000\u0000\u00ae\u009b\u0001\u0000\u0000\u0000\u00ae\u009f\u0001"+
		"\u0000\u0000\u0000\u00ae\u00a2\u0001\u0000\u0000\u0000\u00ae\u00a7\u0001"+
		"\u0000\u0000\u0000\u00af\u00b0\u0001\u0000\u0000\u0000\u00b0\u00b1\u0006"+
		"\t\u0003\u0000\u00b1\u0017\u0001\u0000\u0000\u0000\u00b2\u00b3\u0005{"+
		"\u0000\u0000\u00b3\u00b4\u0005{\u0000\u0000\u00b4\u00b5\u0001\u0000\u0000"+
		"\u0000\u00b5\u00b6\u0006\n\u0005\u0000\u00b6\u0019\u0001\u0000\u0000\u0000"+
		"\u00b7\u00b8\t\u0000\u0000\u0000\u00b8\u00b9\u0001\u0000\u0000\u0000\u00b9"+
		"\u00ba\u0006\u000b\u0001\u0000\u00ba\u001b\u0001\u0000\u0000\u0000\u00bb"+
		"\u00bc\u0005\u0000\u0000\u0001\u00bc\u00bd\u0001\u0000\u0000\u0000\u00bd"+
		"\u00be\u0006\f\u0006\u0000\u00be\u00bf\u0006\f\u0002\u0000\u00bf\u001d"+
		"\u0001\u0000\u0000\u0000\u00c0\u00c1\u0003\f\u0004\u0000\u00c1\u00c2\u0001"+
		"\u0000\u0000\u0000\u00c2\u00c3\u0006\r\u0007\u0000\u00c3\u00c4\u0006\r"+
		"\u0002\u0000\u00c4\u001f\u0001\u0000\u0000\u0000\u00c5\u00c6\u0005\u0000"+
		"\u0000\u0001\u00c6\u00c7\u0001\u0000\u0000\u0000\u00c7\u00c8\u0006\u000e"+
		"\u0006\u0000\u00c8\u00c9\u0006\u000e\u0002\u0000\u00c9!\u0001\u0000\u0000"+
		"\u0000\u00ca\u00cb\u0005}\u0000\u0000\u00cb\u00cc\u0005}\u0000\u0000\u00cc"+
		"\u00cd\u0001\u0000\u0000\u0000\u00cd\u00ce\u0006\u000f\b\u0000\u00ce\u00cf"+
		"\u0006\u000f\u0002\u0000\u00cf#\u0001\u0000\u0000\u0000\u00d0\u00d1\t"+
		"\u0000\u0000\u0000\u00d1\u00d2\u0001\u0000\u0000\u0000\u00d2\u00d3\u0006"+
		"\u0010\u0001\u0000\u00d3%\u0001\u0000\u0000\u0000\u00d4\u00d5\u0005\u0000"+
		"\u0000\u0001\u00d5\u00d6\u0001\u0000\u0000\u0000\u00d6\u00d7\u0006\u0011"+
		"\u0006\u0000\u00d7\u00d8\u0006\u0011\u0002\u0000\u00d8\'\u0001\u0000\u0000"+
		"\u0000\u0011\u0000\u0001\u0002\u0003,4;@BKMT|\u0081\u008b\u008d\u00ae"+
		"\t\u0005\u0001\u0000\u0006\u0000\u0000\u0004\u0000\u0000\u0007\u0002\u0000"+
		"\u0005\u0002\u0000\u0005\u0003\u0000\u0007\u0006\u0000\u0007\u0004\u0000"+
		"\u0007\u0005\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}