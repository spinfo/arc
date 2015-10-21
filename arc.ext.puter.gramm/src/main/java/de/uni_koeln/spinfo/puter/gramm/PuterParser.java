// Generated from /Users/franciscomondaca/spinfo/repositories/arc/arc.ext.puter.gramm/Puter.g4 by ANTLR 4.5
package de.uni_koeln.spinfo.puter.gramm;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PuterParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		OPENINGTAG=1, CLOSINGTAG=2, PAREX=3, ROMAN=4, ARABIC=5, GRAMM=6, QM=7, 
		EXK=8, DOT=9, COMPLEXWORD=10, TOKEN=11, CHARSEQUENCE=12, COMMA=13, HYP=14, 
		ENUM=15, SEMICOLON=16, ALT=17, REF=18, RE=19, TREMA=20, NEWLINE=21, WS=22, 
		ErrorChar=23;
	public static final int
		RULE_dict = 0, RULE_entry = 1, RULE_error = 2, RULE_lexentry = 3, RULE_endEntry = 4, 
		RULE_keyphrase = 5, RULE_phrase = 6, RULE_infl_info = 7, RULE_grammatical_info = 8, 
		RULE_nge = 9;
	public static final String[] ruleNames = {
		"dict", "entry", "error", "lexentry", "endEntry", "keyphrase", "phrase", 
		"infl_info", "grammatical_info", "nge"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'<E>'", "'</E>'", null, null, null, null, "'?'", "'!'", "'.'", 
		null, null, null, "','", "'-'", null, "';'", null, "'\\u25ba'", "'~'", 
		"'\\u0308'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "OPENINGTAG", "CLOSINGTAG", "PAREX", "ROMAN", "ARABIC", "GRAMM", 
		"QM", "EXK", "DOT", "COMPLEXWORD", "TOKEN", "CHARSEQUENCE", "COMMA", "HYP", 
		"ENUM", "SEMICOLON", "ALT", "REF", "RE", "TREMA", "NEWLINE", "WS", "ErrorChar"
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
	public String getGrammarFileName() { return "Puter.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public PuterParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class DictContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(PuterParser.EOF, 0); }
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
		public DictContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dict; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuterListener ) ((PuterListener)listener).enterDict(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuterListener ) ((PuterListener)listener).exitDict(this);
		}
	}

	public final DictContext dict() throws RecognitionException {
		DictContext _localctx = new DictContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_dict);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(22); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(22);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(20); 
					entry();
					}
					break;
				case 2:
					{
					setState(21); 
					error();
					}
					break;
				}
				}
				setState(24); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==OPENINGTAG );
			setState(26); 
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
		public TerminalNode OPENINGTAG() { return getToken(PuterParser.OPENINGTAG, 0); }
		public LexentryContext lexentry() {
			return getRuleContext(LexentryContext.class,0);
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
			if ( listener instanceof PuterListener ) ((PuterListener)listener).enterEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuterListener ) ((PuterListener)listener).exitEntry(this);
		}
	}

	public final EntryContext entry() throws RecognitionException {
		EntryContext _localctx = new EntryContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_entry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28); 
			match(OPENINGTAG);
			setState(29); 
			lexentry();
			setState(30); 
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
		public TerminalNode OPENINGTAG() { return getToken(PuterParser.OPENINGTAG, 0); }
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
			if ( listener instanceof PuterListener ) ((PuterListener)listener).enterError(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuterListener ) ((PuterListener)listener).exitError(this);
		}
	}

	public final ErrorContext error() throws RecognitionException {
		ErrorContext _localctx = new ErrorContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_error);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32); 
			match(OPENINGTAG);
			setState(33); 
			nge();
			setState(34); 
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

	public static class LexentryContext extends ParserRuleContext {
		public KeyphraseContext keyphrase() {
			return getRuleContext(KeyphraseContext.class,0);
		}
		public Grammatical_infoContext grammatical_info() {
			return getRuleContext(Grammatical_infoContext.class,0);
		}
		public NgeContext nge() {
			return getRuleContext(NgeContext.class,0);
		}
		public LexentryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lexentry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuterListener ) ((PuterListener)listener).enterLexentry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuterListener ) ((PuterListener)listener).exitLexentry(this);
		}
	}

	public final LexentryContext lexentry() throws RecognitionException {
		LexentryContext _localctx = new LexentryContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_lexentry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(36); 
			keyphrase();
			setState(37); 
			grammatical_info();
			setState(38); 
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
		public TerminalNode CLOSINGTAG() { return getToken(PuterParser.CLOSINGTAG, 0); }
		public TerminalNode NEWLINE() { return getToken(PuterParser.NEWLINE, 0); }
		public EndEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_endEntry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuterListener ) ((PuterListener)listener).enterEndEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuterListener ) ((PuterListener)listener).exitEndEntry(this);
		}
	}

	public final EndEntryContext endEntry() throws RecognitionException {
		EndEntryContext _localctx = new EndEntryContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_endEntry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40); 
			match(CLOSINGTAG);
			setState(41); 
			match(NEWLINE);
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

	public static class KeyphraseContext extends ParserRuleContext {
		public PhraseContext phrase() {
			return getRuleContext(PhraseContext.class,0);
		}
		public Infl_infoContext infl_info() {
			return getRuleContext(Infl_infoContext.class,0);
		}
		public KeyphraseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyphrase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuterListener ) ((PuterListener)listener).enterKeyphrase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuterListener ) ((PuterListener)listener).exitKeyphrase(this);
		}
	}

	public final KeyphraseContext keyphrase() throws RecognitionException {
		KeyphraseContext _localctx = new KeyphraseContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_keyphrase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43); 
			phrase();
			setState(45);
			_la = _input.LA(1);
			if (_la==PAREX) {
				{
				setState(44); 
				infl_info();
				}
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

	public static class PhraseContext extends ParserRuleContext {
		public List<TerminalNode> COMPLEXWORD() { return getTokens(PuterParser.COMPLEXWORD); }
		public TerminalNode COMPLEXWORD(int i) {
			return getToken(PuterParser.COMPLEXWORD, i);
		}
		public TerminalNode PAREX() { return getToken(PuterParser.PAREX, 0); }
		public PhraseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_phrase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuterListener ) ((PuterListener)listener).enterPhrase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuterListener ) ((PuterListener)listener).exitPhrase(this);
		}
	}

	public final PhraseContext phrase() throws RecognitionException {
		PhraseContext _localctx = new PhraseContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_phrase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(47); 
				match(COMPLEXWORD);
				}
				}
				setState(50); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==COMPLEXWORD );
			setState(53);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				setState(52); 
				match(PAREX);
				}
				break;
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

	public static class Infl_infoContext extends ParserRuleContext {
		public TerminalNode PAREX() { return getToken(PuterParser.PAREX, 0); }
		public Infl_infoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_infl_info; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuterListener ) ((PuterListener)listener).enterInfl_info(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuterListener ) ((PuterListener)listener).exitInfl_info(this);
		}
	}

	public final Infl_infoContext infl_info() throws RecognitionException {
		Infl_infoContext _localctx = new Infl_infoContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_infl_info);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55); 
			match(PAREX);
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

	public static class Grammatical_infoContext extends ParserRuleContext {
		public TerminalNode GRAMM() { return getToken(PuterParser.GRAMM, 0); }
		public Grammatical_infoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_grammatical_info; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuterListener ) ((PuterListener)listener).enterGrammatical_info(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuterListener ) ((PuterListener)listener).exitGrammatical_info(this);
		}
	}

	public final Grammatical_infoContext grammatical_info() throws RecognitionException {
		Grammatical_infoContext _localctx = new Grammatical_infoContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_grammatical_info);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57); 
			match(GRAMM);
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
			if ( listener instanceof PuterListener ) ((PuterListener)listener).enterNge(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuterListener ) ((PuterListener)listener).exitNge(this);
		}
	}

	public final NgeContext nge() throws RecognitionException {
		NgeContext _localctx = new NgeContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_nge);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(59);
					matchWildcard();
					}
					} 
				}
				setState(64);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\31D\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\3"+
		"\2\3\2\6\2\31\n\2\r\2\16\2\32\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4"+
		"\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\5\7\60\n\7\3\b\6\b\63\n\b\r\b\16"+
		"\b\64\3\b\5\b8\n\b\3\t\3\t\3\n\3\n\3\13\7\13?\n\13\f\13\16\13B\13\13\3"+
		"\13\3@\2\f\2\4\6\b\n\f\16\20\22\24\2\2?\2\30\3\2\2\2\4\36\3\2\2\2\6\""+
		"\3\2\2\2\b&\3\2\2\2\n*\3\2\2\2\f-\3\2\2\2\16\62\3\2\2\2\209\3\2\2\2\22"+
		";\3\2\2\2\24@\3\2\2\2\26\31\5\4\3\2\27\31\5\6\4\2\30\26\3\2\2\2\30\27"+
		"\3\2\2\2\31\32\3\2\2\2\32\30\3\2\2\2\32\33\3\2\2\2\33\34\3\2\2\2\34\35"+
		"\7\2\2\3\35\3\3\2\2\2\36\37\7\3\2\2\37 \5\b\5\2 !\5\n\6\2!\5\3\2\2\2\""+
		"#\7\3\2\2#$\5\24\13\2$%\5\n\6\2%\7\3\2\2\2&\'\5\f\7\2\'(\5\22\n\2()\5"+
		"\24\13\2)\t\3\2\2\2*+\7\4\2\2+,\7\27\2\2,\13\3\2\2\2-/\5\16\b\2.\60\5"+
		"\20\t\2/.\3\2\2\2/\60\3\2\2\2\60\r\3\2\2\2\61\63\7\f\2\2\62\61\3\2\2\2"+
		"\63\64\3\2\2\2\64\62\3\2\2\2\64\65\3\2\2\2\65\67\3\2\2\2\668\7\5\2\2\67"+
		"\66\3\2\2\2\678\3\2\2\28\17\3\2\2\29:\7\5\2\2:\21\3\2\2\2;<\7\b\2\2<\23"+
		"\3\2\2\2=?\13\2\2\2>=\3\2\2\2?B\3\2\2\2@A\3\2\2\2@>\3\2\2\2A\25\3\2\2"+
		"\2B@\3\2\2\2\b\30\32/\64\67@";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}