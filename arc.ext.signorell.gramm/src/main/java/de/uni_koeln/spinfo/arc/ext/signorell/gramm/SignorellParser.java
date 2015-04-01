// Generated from /Users/franciscomondaca/spinfo/repositories/arc/arc.ext.signorell.gramm/SignorellParser.g4 by ANTLR 4.5
package de.uni_koeln.spinfo.arc.ext.signorell.gramm;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SignorellParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		OPENINGTAG=1, CLOSINGTAG=2, POS=3, RN=4, PAR=5, LINEBREAK=6, TOKEN=7, 
		INT=8, S=9, RP=10, LP=11, DOT=12, EXK=13, QM=14, AP=15, ED=16, R=17, COMMA=18, 
		AST=19, SEMICOLON=20, WS=21, ErrorChar=22;
	public static final int
		RULE_startSymb = 0, RULE_entry = 1, RULE_error = 2, RULE_lemma = 3, RULE_endEntry = 4, 
		RULE_nge = 5;
	public static final String[] ruleNames = {
		"startSymb", "entry", "error", "lemma", "endEntry", "nge"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'<E>'", "'</E>'", null, null, null, null, null, null, "'-'", "'('", 
		"')'", "'.'", "'!'", "'?'", "'''", null, "'#'", "','", "'*'", "';'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "OPENINGTAG", "CLOSINGTAG", "POS", "RN", "PAR", "LINEBREAK", "TOKEN", 
		"INT", "S", "RP", "LP", "DOT", "EXK", "QM", "AP", "ED", "R", "COMMA", 
		"AST", "SEMICOLON", "WS", "ErrorChar"
	};
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
	@NotNull
	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "SignorellParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SignorellParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class StartSymbContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(SignorellParser.EOF, 0); }
		public List<EntryContext> entry() {
			return getRuleContexts(EntryContext.class);
		}
		public EntryContext entry(int i) {
			return getRuleContext(EntryContext.class,i);
		}
		public List<ErrorContext> error() {
			return getRuleContexts(ErrorContext.class);
		}
		public ErrorContext error(int i) {
			return getRuleContext(ErrorContext.class,i);
		}
		public StartSymbContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_startSymb; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SignorellParserListener ) ((SignorellParserListener)listener).enterStartSymb(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SignorellParserListener ) ((SignorellParserListener)listener).exitStartSymb(this);
		}
	}

	public final StartSymbContext startSymb() throws RecognitionException {
		StartSymbContext _localctx = new StartSymbContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_startSymb);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(14); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(14);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(12); 
					entry();
					}
					break;
				case 2:
					{
					setState(13); 
					error();
					}
					break;
				}
				}
				setState(16); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==OPENINGTAG );
			setState(18); 
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

	public static class EntryContext extends ParserRuleContext {
		public TerminalNode OPENINGTAG() { return getToken(SignorellParser.OPENINGTAG, 0); }
		public LemmaContext lemma() {
			return getRuleContext(LemmaContext.class,0);
		}
		public EndEntryContext endEntry() {
			return getRuleContext(EndEntryContext.class,0);
		}
		public EntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_entry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SignorellParserListener ) ((SignorellParserListener)listener).enterEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SignorellParserListener ) ((SignorellParserListener)listener).exitEntry(this);
		}
	}

	public final EntryContext entry() throws RecognitionException {
		EntryContext _localctx = new EntryContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_entry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(20); 
			match(OPENINGTAG);
			setState(21); 
			lemma();
			setState(22); 
			endEntry();
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

	public static class ErrorContext extends ParserRuleContext {
		public TerminalNode OPENINGTAG() { return getToken(SignorellParser.OPENINGTAG, 0); }
		public NgeContext nge() {
			return getRuleContext(NgeContext.class,0);
		}
		public EndEntryContext endEntry() {
			return getRuleContext(EndEntryContext.class,0);
		}
		public ErrorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_error; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SignorellParserListener ) ((SignorellParserListener)listener).enterError(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SignorellParserListener ) ((SignorellParserListener)listener).exitError(this);
		}
	}

	public final ErrorContext error() throws RecognitionException {
		ErrorContext _localctx = new ErrorContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_error);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24); 
			match(OPENINGTAG);
			setState(25); 
			nge();
			setState(26); 
			endEntry();
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

	public static class LemmaContext extends ParserRuleContext {
		public List<TerminalNode> TOKEN() { return getTokens(SignorellParser.TOKEN); }
		public TerminalNode TOKEN(int i) {
			return getToken(SignorellParser.TOKEN, i);
		}
		public TerminalNode POS() { return getToken(SignorellParser.POS, 0); }
		public NgeContext nge() {
			return getRuleContext(NgeContext.class,0);
		}
		public LemmaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lemma; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SignorellParserListener ) ((SignorellParserListener)listener).enterLemma(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SignorellParserListener ) ((SignorellParserListener)listener).exitLemma(this);
		}
	}

	public final LemmaContext lemma() throws RecognitionException {
		LemmaContext _localctx = new LemmaContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_lemma);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28); 
			match(TOKEN);
			setState(30);
			_la = _input.LA(1);
			if (_la==TOKEN) {
				{
				setState(29); 
				match(TOKEN);
				}
			}

			setState(32); 
			match(POS);
			setState(33); 
			nge();
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

	public static class EndEntryContext extends ParserRuleContext {
		public TerminalNode CLOSINGTAG() { return getToken(SignorellParser.CLOSINGTAG, 0); }
		public TerminalNode LINEBREAK() { return getToken(SignorellParser.LINEBREAK, 0); }
		public EndEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_endEntry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SignorellParserListener ) ((SignorellParserListener)listener).enterEndEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SignorellParserListener ) ((SignorellParserListener)listener).exitEndEntry(this);
		}
	}

	public final EndEntryContext endEntry() throws RecognitionException {
		EndEntryContext _localctx = new EndEntryContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_endEntry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(35); 
			match(CLOSINGTAG);
			setState(36); 
			match(LINEBREAK);
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

	public static class NgeContext extends ParserRuleContext {
		public NgeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nge; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SignorellParserListener ) ((SignorellParserListener)listener).enterNge(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SignorellParserListener ) ((SignorellParserListener)listener).exitNge(this);
		}
	}

	public final NgeContext nge() throws RecognitionException {
		NgeContext _localctx = new NgeContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_nge);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(38);
					matchWildcard();
					}
					} 
				}
				setState(43);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\30/\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\3\2\3\2\6\2\21\n\2\r\2\16\2\22\3\2"+
		"\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\5\5!\n\5\3\5\3\5\3\5\3\6"+
		"\3\6\3\6\3\7\7\7*\n\7\f\7\16\7-\13\7\3\7\3+\2\b\2\4\6\b\n\f\2\2,\2\20"+
		"\3\2\2\2\4\26\3\2\2\2\6\32\3\2\2\2\b\36\3\2\2\2\n%\3\2\2\2\f+\3\2\2\2"+
		"\16\21\5\4\3\2\17\21\5\6\4\2\20\16\3\2\2\2\20\17\3\2\2\2\21\22\3\2\2\2"+
		"\22\20\3\2\2\2\22\23\3\2\2\2\23\24\3\2\2\2\24\25\7\2\2\3\25\3\3\2\2\2"+
		"\26\27\7\3\2\2\27\30\5\b\5\2\30\31\5\n\6\2\31\5\3\2\2\2\32\33\7\3\2\2"+
		"\33\34\5\f\7\2\34\35\5\n\6\2\35\7\3\2\2\2\36 \7\t\2\2\37!\7\t\2\2 \37"+
		"\3\2\2\2 !\3\2\2\2!\"\3\2\2\2\"#\7\5\2\2#$\5\f\7\2$\t\3\2\2\2%&\7\4\2"+
		"\2&\'\7\b\2\2\'\13\3\2\2\2(*\13\2\2\2)(\3\2\2\2*-\3\2\2\2+,\3\2\2\2+)"+
		"\3\2\2\2,\r\3\2\2\2-+\3\2\2\2\6\20\22 +";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}