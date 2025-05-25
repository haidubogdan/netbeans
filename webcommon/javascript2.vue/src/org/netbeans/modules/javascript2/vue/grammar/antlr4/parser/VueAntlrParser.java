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

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class VueAntlrParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		TEMPLATE_TAG_OPEN=1, VUE_DIRECTIVE=2, QUOTE_ATTR=3, JAVASCRIPT=4, JAVASCRIPT_ATTR=5, 
		JAVASCRIPT_INTERP=6, HTML=7, CSS=8, TEMPLATE_TAG_CLOSE=9, VAR_TAG=10;
	public static final int
		RULE_file = 0, RULE_statement = 1, RULE_vueDirective = 2;
	private static String[] makeRuleNames() {
		return new String[] {
			"file", "statement", "vueDirective"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'<template'", null, null, null, null, null, null, null, "'</template>'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "TEMPLATE_TAG_OPEN", "VUE_DIRECTIVE", "QUOTE_ATTR", "JAVASCRIPT", 
			"JAVASCRIPT_ATTR", "JAVASCRIPT_INTERP", "HTML", "CSS", "TEMPLATE_TAG_CLOSE", 
			"VAR_TAG"
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

	@Override
	public String getGrammarFileName() { return "VueAntlrParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public VueAntlrParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FileContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(VueAntlrParser.EOF, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public FileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_file; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VueAntlrParserListener ) ((VueAntlrParserListener)listener).enterFile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VueAntlrParserListener ) ((VueAntlrParserListener)listener).exitFile(this);
		}
	}

	public final FileContext file() throws RecognitionException {
		FileContext _localctx = new FileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_file);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(9);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2046L) != 0)) {
				{
				{
				setState(6);
				statement();
				}
				}
				setState(11);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(12);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public TerminalNode HTML() { return getToken(VueAntlrParser.HTML, 0); }
		public TerminalNode TEMPLATE_TAG_OPEN() { return getToken(VueAntlrParser.TEMPLATE_TAG_OPEN, 0); }
		public TerminalNode TEMPLATE_TAG_CLOSE() { return getToken(VueAntlrParser.TEMPLATE_TAG_CLOSE, 0); }
		public VueDirectiveContext vueDirective() {
			return getRuleContext(VueDirectiveContext.class,0);
		}
		public TerminalNode QUOTE_ATTR() { return getToken(VueAntlrParser.QUOTE_ATTR, 0); }
		public TerminalNode VAR_TAG() { return getToken(VueAntlrParser.VAR_TAG, 0); }
		public TerminalNode JAVASCRIPT_ATTR() { return getToken(VueAntlrParser.JAVASCRIPT_ATTR, 0); }
		public TerminalNode JAVASCRIPT_INTERP() { return getToken(VueAntlrParser.JAVASCRIPT_INTERP, 0); }
		public TerminalNode JAVASCRIPT() { return getToken(VueAntlrParser.JAVASCRIPT, 0); }
		public TerminalNode CSS() { return getToken(VueAntlrParser.CSS, 0); }
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VueAntlrParserListener ) ((VueAntlrParserListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VueAntlrParserListener ) ((VueAntlrParserListener)listener).exitStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(24);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case HTML:
				enterOuterAlt(_localctx, 1);
				{
				setState(14);
				match(HTML);
				}
				break;
			case TEMPLATE_TAG_OPEN:
				enterOuterAlt(_localctx, 2);
				{
				setState(15);
				match(TEMPLATE_TAG_OPEN);
				}
				break;
			case TEMPLATE_TAG_CLOSE:
				enterOuterAlt(_localctx, 3);
				{
				setState(16);
				match(TEMPLATE_TAG_CLOSE);
				}
				break;
			case VUE_DIRECTIVE:
				enterOuterAlt(_localctx, 4);
				{
				setState(17);
				vueDirective();
				}
				break;
			case QUOTE_ATTR:
				enterOuterAlt(_localctx, 5);
				{
				setState(18);
				match(QUOTE_ATTR);
				}
				break;
			case VAR_TAG:
				enterOuterAlt(_localctx, 6);
				{
				setState(19);
				match(VAR_TAG);
				}
				break;
			case JAVASCRIPT_ATTR:
				enterOuterAlt(_localctx, 7);
				{
				setState(20);
				match(JAVASCRIPT_ATTR);
				}
				break;
			case JAVASCRIPT_INTERP:
				enterOuterAlt(_localctx, 8);
				{
				setState(21);
				match(JAVASCRIPT_INTERP);
				}
				break;
			case JAVASCRIPT:
				enterOuterAlt(_localctx, 9);
				{
				setState(22);
				match(JAVASCRIPT);
				}
				break;
			case CSS:
				enterOuterAlt(_localctx, 10);
				{
				setState(23);
				match(CSS);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VueDirectiveContext extends ParserRuleContext {
		public TerminalNode VUE_DIRECTIVE() { return getToken(VueAntlrParser.VUE_DIRECTIVE, 0); }
		public VueDirectiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vueDirective; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VueAntlrParserListener ) ((VueAntlrParserListener)listener).enterVueDirective(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VueAntlrParserListener ) ((VueAntlrParserListener)listener).exitVueDirective(this);
		}
	}

	public final VueDirectiveContext vueDirective() throws RecognitionException {
		VueDirectiveContext _localctx = new VueDirectiveContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_vueDirective);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(26);
			match(VUE_DIRECTIVE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\n\u001d\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0001\u0000\u0005\u0000\b\b\u0000\n\u0000\f\u0000\u000b"+
		"\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0003\u0001\u0019\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0000"+
		"\u0000\u0003\u0000\u0002\u0004\u0000\u0000#\u0000\t\u0001\u0000\u0000"+
		"\u0000\u0002\u0018\u0001\u0000\u0000\u0000\u0004\u001a\u0001\u0000\u0000"+
		"\u0000\u0006\b\u0003\u0002\u0001\u0000\u0007\u0006\u0001\u0000\u0000\u0000"+
		"\b\u000b\u0001\u0000\u0000\u0000\t\u0007\u0001\u0000\u0000\u0000\t\n\u0001"+
		"\u0000\u0000\u0000\n\f\u0001\u0000\u0000\u0000\u000b\t\u0001\u0000\u0000"+
		"\u0000\f\r\u0005\u0000\u0000\u0001\r\u0001\u0001\u0000\u0000\u0000\u000e"+
		"\u0019\u0005\u0007\u0000\u0000\u000f\u0019\u0005\u0001\u0000\u0000\u0010"+
		"\u0019\u0005\t\u0000\u0000\u0011\u0019\u0003\u0004\u0002\u0000\u0012\u0019"+
		"\u0005\u0003\u0000\u0000\u0013\u0019\u0005\n\u0000\u0000\u0014\u0019\u0005"+
		"\u0005\u0000\u0000\u0015\u0019\u0005\u0006\u0000\u0000\u0016\u0019\u0005"+
		"\u0004\u0000\u0000\u0017\u0019\u0005\b\u0000\u0000\u0018\u000e\u0001\u0000"+
		"\u0000\u0000\u0018\u000f\u0001\u0000\u0000\u0000\u0018\u0010\u0001\u0000"+
		"\u0000\u0000\u0018\u0011\u0001\u0000\u0000\u0000\u0018\u0012\u0001\u0000"+
		"\u0000\u0000\u0018\u0013\u0001\u0000\u0000\u0000\u0018\u0014\u0001\u0000"+
		"\u0000\u0000\u0018\u0015\u0001\u0000\u0000\u0000\u0018\u0016\u0001\u0000"+
		"\u0000\u0000\u0018\u0017\u0001\u0000\u0000\u0000\u0019\u0003\u0001\u0000"+
		"\u0000\u0000\u001a\u001b\u0005\u0002\u0000\u0000\u001b\u0005\u0001\u0000"+
		"\u0000\u0000\u0002\t\u0018";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}