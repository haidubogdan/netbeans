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
		TEMPLATE_TAG_OPEN=1, VUE_DIRECTIVE=2, JAVASCRIPT=3, JAVASCRIPT_ATTR_VALUE=4, 
		VAR_TAG=5, HTML=6, OTHER=7, TEMPLATE_TAG_CLOSE=8, TEMPLATE_OTHER=9, VAR_INTERPOLATION_OTHER=10, 
		VAR_INTERPOLATION_END=11;
	public static final int
		RULE_file = 0, RULE_statement = 1, RULE_template = 2, RULE_vueDirective = 3, 
		RULE_vueInterpolation = 4;
	private static String[] makeRuleNames() {
		return new String[] {
			"file", "statement", "template", "vueDirective", "vueInterpolation"
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
			setState(13);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 74L) != 0)) {
				{
				{
				setState(10);
				statement();
				}
				}
				setState(15);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(16);
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
		public TemplateContext template() {
			return getRuleContext(TemplateContext.class,0);
		}
		public TerminalNode JAVASCRIPT() { return getToken(VueAntlrParser.JAVASCRIPT, 0); }
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
			setState(21);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case HTML:
				enterOuterAlt(_localctx, 1);
				{
				setState(18);
				match(HTML);
				}
				break;
			case TEMPLATE_TAG_OPEN:
				enterOuterAlt(_localctx, 2);
				{
				setState(19);
				template();
				}
				break;
			case JAVASCRIPT:
				enterOuterAlt(_localctx, 3);
				{
				setState(20);
				match(JAVASCRIPT);
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
	public static class TemplateContext extends ParserRuleContext {
		public TerminalNode TEMPLATE_TAG_OPEN() { return getToken(VueAntlrParser.TEMPLATE_TAG_OPEN, 0); }
		public TerminalNode TEMPLATE_TAG_CLOSE() { return getToken(VueAntlrParser.TEMPLATE_TAG_CLOSE, 0); }
		public List<TerminalNode> HTML() { return getTokens(VueAntlrParser.HTML); }
		public TerminalNode HTML(int i) {
			return getToken(VueAntlrParser.HTML, i);
		}
		public List<VueDirectiveContext> vueDirective() {
			return getRuleContexts(VueDirectiveContext.class);
		}
		public VueDirectiveContext vueDirective(int i) {
			return getRuleContext(VueDirectiveContext.class,i);
		}
		public List<VueInterpolationContext> vueInterpolation() {
			return getRuleContexts(VueInterpolationContext.class);
		}
		public VueInterpolationContext vueInterpolation(int i) {
			return getRuleContext(VueInterpolationContext.class,i);
		}
		public List<TerminalNode> JAVASCRIPT() { return getTokens(VueAntlrParser.JAVASCRIPT); }
		public TerminalNode JAVASCRIPT(int i) {
			return getToken(VueAntlrParser.JAVASCRIPT, i);
		}
		public List<TerminalNode> JAVASCRIPT_ATTR_VALUE() { return getTokens(VueAntlrParser.JAVASCRIPT_ATTR_VALUE); }
		public TerminalNode JAVASCRIPT_ATTR_VALUE(int i) {
			return getToken(VueAntlrParser.JAVASCRIPT_ATTR_VALUE, i);
		}
		public TemplateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_template; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VueAntlrParserListener ) ((VueAntlrParserListener)listener).enterTemplate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VueAntlrParserListener ) ((VueAntlrParserListener)listener).exitTemplate(this);
		}
	}

	public final TemplateContext template() throws RecognitionException {
		TemplateContext _localctx = new TemplateContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_template);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(23);
			match(TEMPLATE_TAG_OPEN);
			setState(31);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 124L) != 0)) {
				{
				setState(29);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case HTML:
					{
					setState(24);
					match(HTML);
					}
					break;
				case VUE_DIRECTIVE:
					{
					setState(25);
					vueDirective();
					}
					break;
				case VAR_TAG:
					{
					setState(26);
					vueInterpolation();
					}
					break;
				case JAVASCRIPT:
					{
					setState(27);
					match(JAVASCRIPT);
					}
					break;
				case JAVASCRIPT_ATTR_VALUE:
					{
					setState(28);
					match(JAVASCRIPT_ATTR_VALUE);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(33);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(34);
			match(TEMPLATE_TAG_CLOSE);
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
		enterRule(_localctx, 6, RULE_vueDirective);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(36);
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

	@SuppressWarnings("CheckReturnValue")
	public static class VueInterpolationContext extends ParserRuleContext {
		public Token open_tag;
		public Token close_tag;
		public List<TerminalNode> VAR_TAG() { return getTokens(VueAntlrParser.VAR_TAG); }
		public TerminalNode VAR_TAG(int i) {
			return getToken(VueAntlrParser.VAR_TAG, i);
		}
		public VueInterpolationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vueInterpolation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VueAntlrParserListener ) ((VueAntlrParserListener)listener).enterVueInterpolation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VueAntlrParserListener ) ((VueAntlrParserListener)listener).exitVueInterpolation(this);
		}
	}

	public final VueInterpolationContext vueInterpolation() throws RecognitionException {
		VueInterpolationContext _localctx = new VueInterpolationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_vueInterpolation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(38);
			((VueInterpolationContext)_localctx).open_tag = match(VAR_TAG);
			setState(39);
			((VueInterpolationContext)_localctx).close_tag = match(VAR_TAG);
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
		"\u0004\u0001\u000b*\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0001"+
		"\u0000\u0005\u0000\f\b\u0000\n\u0000\f\u0000\u000f\t\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001\u0016\b\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0005\u0002\u001e\b\u0002\n\u0002\f\u0002!\t\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0000\u0000\u0005\u0000\u0002\u0004\u0006\b\u0000\u0000,\u0000"+
		"\r\u0001\u0000\u0000\u0000\u0002\u0015\u0001\u0000\u0000\u0000\u0004\u0017"+
		"\u0001\u0000\u0000\u0000\u0006$\u0001\u0000\u0000\u0000\b&\u0001\u0000"+
		"\u0000\u0000\n\f\u0003\u0002\u0001\u0000\u000b\n\u0001\u0000\u0000\u0000"+
		"\f\u000f\u0001\u0000\u0000\u0000\r\u000b\u0001\u0000\u0000\u0000\r\u000e"+
		"\u0001\u0000\u0000\u0000\u000e\u0010\u0001\u0000\u0000\u0000\u000f\r\u0001"+
		"\u0000\u0000\u0000\u0010\u0011\u0005\u0000\u0000\u0001\u0011\u0001\u0001"+
		"\u0000\u0000\u0000\u0012\u0016\u0005\u0006\u0000\u0000\u0013\u0016\u0003"+
		"\u0004\u0002\u0000\u0014\u0016\u0005\u0003\u0000\u0000\u0015\u0012\u0001"+
		"\u0000\u0000\u0000\u0015\u0013\u0001\u0000\u0000\u0000\u0015\u0014\u0001"+
		"\u0000\u0000\u0000\u0016\u0003\u0001\u0000\u0000\u0000\u0017\u001f\u0005"+
		"\u0001\u0000\u0000\u0018\u001e\u0005\u0006\u0000\u0000\u0019\u001e\u0003"+
		"\u0006\u0003\u0000\u001a\u001e\u0003\b\u0004\u0000\u001b\u001e\u0005\u0003"+
		"\u0000\u0000\u001c\u001e\u0005\u0004\u0000\u0000\u001d\u0018\u0001\u0000"+
		"\u0000\u0000\u001d\u0019\u0001\u0000\u0000\u0000\u001d\u001a\u0001\u0000"+
		"\u0000\u0000\u001d\u001b\u0001\u0000\u0000\u0000\u001d\u001c\u0001\u0000"+
		"\u0000\u0000\u001e!\u0001\u0000\u0000\u0000\u001f\u001d\u0001\u0000\u0000"+
		"\u0000\u001f \u0001\u0000\u0000\u0000 \"\u0001\u0000\u0000\u0000!\u001f"+
		"\u0001\u0000\u0000\u0000\"#\u0005\b\u0000\u0000#\u0005\u0001\u0000\u0000"+
		"\u0000$%\u0005\u0002\u0000\u0000%\u0007\u0001\u0000\u0000\u0000&\'\u0005"+
		"\u0005\u0000\u0000\'(\u0005\u0005\u0000\u0000(\t\u0001\u0000\u0000\u0000"+
		"\u0004\r\u0015\u001d\u001f";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}