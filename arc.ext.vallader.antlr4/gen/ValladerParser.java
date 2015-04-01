// Generated from H:/git/arc/arc.backend/arc.extractions/tscharner_vallader/tscharner_vallader.antlr4\Vallader.g4 by ANTLR 4.4.1-dev
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ValladerParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.4.1-dev", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		OPENINGTAG=1, CLOSINGTAG=2, PAREX=3, ROMAN=4, ARABIC=5, GRAMM=6, QM=7, 
		SL=8, EXK=9, DOT=10, COMPLEXWORD=11, TOKEN=12, CHARSEQUENCE=13, COMMA=14, 
		HYP=15, ENUM=16, SEMICOLON=17, ALT=18, REF=19, RE=20, NEWLINE=21, WS=22, 
		ErrorChar=23;
	public static final String[] tokenNames = {
		"<INVALID>", "'<E>'", "'</E>'", "PAREX", "ROMAN", "ARABIC", "GRAMM", "'?'", 
		"'/'", "'!'", "'.'", "COMPLEXWORD", "TOKEN", "CHARSEQUENCE", "','", "'-'", 
		"ENUM", "';'", "ALT", "'\\u25ba'", "'~'", "NEWLINE", "WS", "ErrorChar"
	};
	public static final int
		RULE_dict = 0, RULE_entry = 1, RULE_error = 2, RULE_lexentry = 3, RULE_endEntry = 4, 
		RULE_keyphrase = 5, RULE_phrase = 6, RULE_infl_info = 7, RULE_grammatical_info = 8, 
		RULE_additional_info = 9, RULE_nge = 10;
	public static final String[] ruleNames = {
		"dict", "entry", "error", "lexentry", "endEntry", "keyphrase", "phrase", 
		"infl_info", "grammatical_info", "additional_info", "nge"
	};

	@Override
	public String getGrammarFileName() { return "Vallader.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ValladerParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class DictContext extends ParserRuleContext {
		public EntryContext entry(int i) {
			return getRuleContext(EntryContext.class,i);
		}
		public ErrorContext error(int i) {
			return getRuleContext(ErrorContext.class,i);
		}
		public TerminalNode EOF() { return getToken(ValladerParser.EOF, 0); }
		public List<ErrorContext> error() {
			return getRuleContexts(ErrorContext.class);
		}
		public List<EntryContext> entry() {
			return getRuleContexts(EntryContext.class);
		}
		public DictContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dict; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ValladerListener ) ((ValladerListener)listener).enterDict(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ValladerListener ) ((ValladerListener)listener).exitDict(this);
		}
	}

	public final DictContext dict() throws RecognitionException {
		DictContext _localctx = new DictContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_dict);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(24);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(22); entry();
					}
					break;
				case 2:
					{
					setState(23); error();
					}
					break;
				}
				}
				setState(26); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==OPENINGTAG );
			setState(28); match(EOF);
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
		public LexentryContext lexentry() {
			return getRuleContext(LexentryContext.class,0);
		}
		public EndEntryContext endEntry() {
			return getRuleContext(EndEntryContext.class,0);
		}
		public TerminalNode OPENINGTAG() { return getToken(ValladerParser.OPENINGTAG, 0); }
		public EntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_entry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ValladerListener ) ((ValladerListener)listener).enterEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ValladerListener ) ((ValladerListener)listener).exitEntry(this);
		}
	}

	public final EntryContext entry() throws RecognitionException {
		EntryContext _localctx = new EntryContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_entry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30); match(OPENINGTAG);
			setState(31); lexentry();
			setState(32); endEntry();
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
		public NgeContext nge() {
			return getRuleContext(NgeContext.class,0);
		}
		public EndEntryContext endEntry() {
			return getRuleContext(EndEntryContext.class,0);
		}
		public TerminalNode OPENINGTAG() { return getToken(ValladerParser.OPENINGTAG, 0); }
		public ErrorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_error; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ValladerListener ) ((ValladerListener)listener).enterError(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ValladerListener ) ((ValladerListener)listener).exitError(this);
		}
	}

	public final ErrorContext error() throws RecognitionException {
		ErrorContext _localctx = new ErrorContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_error);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34); match(OPENINGTAG);
			setState(35); nge();
			setState(36); endEntry();
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
		public NgeContext nge() {
			return getRuleContext(NgeContext.class,0);
		}
		public Grammatical_infoContext grammatical_info() {
			return getRuleContext(Grammatical_infoContext.class,0);
		}
		public Additional_infoContext additional_info() {
			return getRuleContext(Additional_infoContext.class,0);
		}
		public KeyphraseContext keyphrase() {
			return getRuleContext(KeyphraseContext.class,0);
		}
		public LexentryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lexentry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ValladerListener ) ((ValladerListener)listener).enterLexentry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ValladerListener ) ((ValladerListener)listener).exitLexentry(this);
		}
	}

	public final LexentryContext lexentry() throws RecognitionException {
		LexentryContext _localctx = new LexentryContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_lexentry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(38); keyphrase();
			setState(39); grammatical_info();
			setState(41);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				setState(40); additional_info();
				}
				break;
			}
			setState(43); nge();
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
		public TerminalNode NEWLINE() { return getToken(ValladerParser.NEWLINE, 0); }
		public TerminalNode CLOSINGTAG() { return getToken(ValladerParser.CLOSINGTAG, 0); }
		public EndEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_endEntry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ValladerListener ) ((ValladerListener)listener).enterEndEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ValladerListener ) ((ValladerListener)listener).exitEndEntry(this);
		}
	}

	public final EndEntryContext endEntry() throws RecognitionException {
		EndEntryContext _localctx = new EndEntryContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_endEntry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(45); match(CLOSINGTAG);
			setState(46); match(NEWLINE);
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
			if ( listener instanceof ValladerListener ) ((ValladerListener)listener).enterKeyphrase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ValladerListener ) ((ValladerListener)listener).exitKeyphrase(this);
		}
	}

	public final KeyphraseContext keyphrase() throws RecognitionException {
		KeyphraseContext _localctx = new KeyphraseContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_keyphrase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48); phrase();
			setState(50);
			_la = _input.LA(1);
			if (_la==PAREX) {
				{
				setState(49); infl_info();
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
		public TerminalNode PAREX() { return getToken(ValladerParser.PAREX, 0); }
		public List<TerminalNode> COMPLEXWORD() { return getTokens(ValladerParser.COMPLEXWORD); }
		public TerminalNode COMPLEXWORD(int i) {
			return getToken(ValladerParser.COMPLEXWORD, i);
		}
		public PhraseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_phrase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ValladerListener ) ((ValladerListener)listener).enterPhrase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ValladerListener ) ((ValladerListener)listener).exitPhrase(this);
		}
	}

	public final PhraseContext phrase() throws RecognitionException {
		PhraseContext _localctx = new PhraseContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_phrase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(52); match(COMPLEXWORD);
				}
				}
				setState(55); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==COMPLEXWORD );
			setState(58);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				setState(57); match(PAREX);
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
		public TerminalNode PAREX() { return getToken(ValladerParser.PAREX, 0); }
		public Infl_infoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_infl_info; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ValladerListener ) ((ValladerListener)listener).enterInfl_info(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ValladerListener ) ((ValladerListener)listener).exitInfl_info(this);
		}
	}

	public final Infl_infoContext infl_info() throws RecognitionException {
		Infl_infoContext _localctx = new Infl_infoContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_infl_info);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60); match(PAREX);
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
		public TerminalNode GRAMM() { return getToken(ValladerParser.GRAMM, 0); }
		public Grammatical_infoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_grammatical_info; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ValladerListener ) ((ValladerListener)listener).enterGrammatical_info(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ValladerListener ) ((ValladerListener)listener).exitGrammatical_info(this);
		}
	}

	public final Grammatical_infoContext grammatical_info() throws RecognitionException {
		Grammatical_infoContext _localctx = new Grammatical_infoContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_grammatical_info);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62); match(GRAMM);
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

	public static class Additional_infoContext extends ParserRuleContext {
		public TerminalNode PAREX() { return getToken(ValladerParser.PAREX, 0); }
		public Additional_infoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additional_info; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ValladerListener ) ((ValladerListener)listener).enterAdditional_info(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ValladerListener ) ((ValladerListener)listener).exitAdditional_info(this);
		}
	}

	public final Additional_infoContext additional_info() throws RecognitionException {
		Additional_infoContext _localctx = new Additional_infoContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_additional_info);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64); match(PAREX);
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
			if ( listener instanceof ValladerListener ) ((ValladerListener)listener).enterNge(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ValladerListener ) ((ValladerListener)listener).exitNge(this);
		}
	}

	public final NgeContext nge() throws RecognitionException {
		NgeContext _localctx = new NgeContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_nge);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(66);
					matchWildcard();
					}
					} 
				}
				setState(71);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\31K\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\3\2\3\2\6\2\33\n\2\r\2\16\2\34\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4"+
		"\3\4\3\4\3\5\3\5\3\5\5\5,\n\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\5\7\65\n\7\3"+
		"\b\6\b8\n\b\r\b\16\b9\3\b\5\b=\n\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\7\fF"+
		"\n\f\f\f\16\fI\13\f\3\f\3G\2\r\2\4\6\b\n\f\16\20\22\24\26\2\2F\2\32\3"+
		"\2\2\2\4 \3\2\2\2\6$\3\2\2\2\b(\3\2\2\2\n/\3\2\2\2\f\62\3\2\2\2\16\67"+
		"\3\2\2\2\20>\3\2\2\2\22@\3\2\2\2\24B\3\2\2\2\26G\3\2\2\2\30\33\5\4\3\2"+
		"\31\33\5\6\4\2\32\30\3\2\2\2\32\31\3\2\2\2\33\34\3\2\2\2\34\32\3\2\2\2"+
		"\34\35\3\2\2\2\35\36\3\2\2\2\36\37\7\2\2\3\37\3\3\2\2\2 !\7\3\2\2!\"\5"+
		"\b\5\2\"#\5\n\6\2#\5\3\2\2\2$%\7\3\2\2%&\5\26\f\2&\'\5\n\6\2\'\7\3\2\2"+
		"\2()\5\f\7\2)+\5\22\n\2*,\5\24\13\2+*\3\2\2\2+,\3\2\2\2,-\3\2\2\2-.\5"+
		"\26\f\2.\t\3\2\2\2/\60\7\4\2\2\60\61\7\27\2\2\61\13\3\2\2\2\62\64\5\16"+
		"\b\2\63\65\5\20\t\2\64\63\3\2\2\2\64\65\3\2\2\2\65\r\3\2\2\2\668\7\r\2"+
		"\2\67\66\3\2\2\289\3\2\2\29\67\3\2\2\29:\3\2\2\2:<\3\2\2\2;=\7\5\2\2<"+
		";\3\2\2\2<=\3\2\2\2=\17\3\2\2\2>?\7\5\2\2?\21\3\2\2\2@A\7\b\2\2A\23\3"+
		"\2\2\2BC\7\5\2\2C\25\3\2\2\2DF\13\2\2\2ED\3\2\2\2FI\3\2\2\2GH\3\2\2\2"+
		"GE\3\2\2\2H\27\3\2\2\2IG\3\2\2\2\t\32\34+\649<G";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}