// Generated from /Users/franciscomondaca/spinfo/repositories/arc/arc.ext.signorell.gramm/SignorellLexer.g4 by ANTLR 4.5
package de.uni_koeln.spinfo.arc.ext.signorell.gramm;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SignorellLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		OPENINGTAG=1, CLOSINGTAG=2, POS=3, RN=4, PAR=5, LINEBREAK=6, TOKEN=7, 
		INT=8, S=9, RP=10, LP=11, DOT=12, EXK=13, QM=14, AP=15, ED=16, R=17, COMMA=18, 
		AST=19, SEMICOLON=20, WS=21, ErrorChar=22;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"OPENINGTAG", "CLOSINGTAG", "POS", "RN", "PAR", "LINEBREAK", "TOKEN", 
		"INT", "S", "RP", "LP", "DOT", "EXK", "QM", "AP", "ED", "R", "COMMA", 
		"AST", "SEMICOLON", "WS", "NameStartChar", "ErrorChar"
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


	public SignorellLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "SignorellLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\30\u00e1\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2"+
		"\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4v\n\4\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\u0080\n\5\3\5\3\5\3\6\3\6\3\6\5\6\u0087"+
		"\n\6\3\6\7\6\u008a\n\6\f\6\16\6\u008d\13\6\3\6\3\6\3\7\5\7\u0092\n\7\3"+
		"\7\3\7\5\7\u0096\n\7\3\b\5\b\u0099\n\b\3\b\3\b\7\b\u009d\n\b\f\b\16\b"+
		"\u00a0\13\b\3\b\3\b\3\b\3\b\3\b\5\b\u00a7\n\b\3\t\6\t\u00aa\n\t\r\t\16"+
		"\t\u00ab\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3"+
		"\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3"+
		"\22\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\26\6"+
		"\26\u00d8\n\26\r\26\16\26\u00d9\3\26\3\26\3\27\3\27\3\30\3\30\2\2\31\3"+
		"\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37"+
		"\21!\22#\23%\24\'\25)\26+\27-\2/\30\3\2\6\4\2hhoo\3\2\62;\5\2\13\13\16"+
		"\17\"\"\17\2C\\c|\u00c2\u00d8\u00da\u00f8\u00fa\u0301\u0372\u037f\u0381"+
		"\u2001\u200e\u200f\u2072\u2191\u2c02\u2ff1\u3003\ud801\uf902\ufdd1\ufdf2"+
		"\uffff\u00ff\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2"+
		"\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2"+
		"\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2"+
		"\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2/\3\2\2"+
		"\2\3\61\3\2\2\2\5\65\3\2\2\2\7u\3\2\2\2\t\177\3\2\2\2\13\u0083\3\2\2\2"+
		"\r\u0095\3\2\2\2\17\u0098\3\2\2\2\21\u00a9\3\2\2\2\23\u00ad\3\2\2\2\25"+
		"\u00af\3\2\2\2\27\u00b3\3\2\2\2\31\u00b7\3\2\2\2\33\u00bb\3\2\2\2\35\u00bd"+
		"\3\2\2\2\37\u00bf\3\2\2\2!\u00c1\3\2\2\2#\u00c6\3\2\2\2%\u00ca\3\2\2\2"+
		"\'\u00ce\3\2\2\2)\u00d2\3\2\2\2+\u00d7\3\2\2\2-\u00dd\3\2\2\2/\u00df\3"+
		"\2\2\2\61\62\7>\2\2\62\63\7G\2\2\63\64\7@\2\2\64\4\3\2\2\2\65\66\7>\2"+
		"\2\66\67\7\61\2\2\678\7G\2\289\7@\2\29\6\3\2\2\2:;\7c\2\2;<\7f\2\2<v\7"+
		"l\2\2=>\7c\2\2>?\7f\2\2?v\7x\2\2@v\t\2\2\2AB\7t\2\2Bv\7p\2\2CD\7h\2\2"+
		"DE\7\61\2\2EF\7r\2\2Fv\7n\2\2GH\7o\2\2HI\7\61\2\2Iv\7h\2\2Jv\7x\2\2KL"+
		"\7x\2\2LM\7\"\2\2MN\7t\2\2NO\7g\2\2OP\7h\2\2Pv\7n\2\2QR\7x\2\2RS\7\"\2"+
		"\2ST\7k\2\2TU\7p\2\2UV\7v\2\2Vv\7t\2\2WX\7x\2\2XY\7\"\2\2YZ\7v\2\2Zv\7"+
		"t\2\2[\\\7t\2\2\\]\7g\2\2]^\7h\2\2^v\7n\2\2_`\7v\2\2`v\7t\2\2ab\7h\2\2"+
		"bv\7t\2\2cd\7k\2\2de\7p\2\2ef\7v\2\2fv\7t\2\2gh\7r\2\2hi\7t\2\2ij\7q\2"+
		"\2jv\7p\2\2kl\7r\2\2lm\7t\2\2mn\7g\2\2nv\7r\2\2op\7k\2\2pq\7p\2\2qr\7"+
		"v\2\2rs\7g\2\2st\7t\2\2tv\7l\2\2u:\3\2\2\2u=\3\2\2\2u@\3\2\2\2uA\3\2\2"+
		"\2uC\3\2\2\2uG\3\2\2\2uJ\3\2\2\2uK\3\2\2\2uQ\3\2\2\2uW\3\2\2\2u[\3\2\2"+
		"\2u_\3\2\2\2ua\3\2\2\2uc\3\2\2\2ug\3\2\2\2uk\3\2\2\2uo\3\2\2\2v\b\3\2"+
		"\2\2w\u0080\7K\2\2xy\7K\2\2y\u0080\7K\2\2z{\7K\2\2{|\7K\2\2|\u0080\7K"+
		"\2\2}~\7K\2\2~\u0080\7X\2\2\177w\3\2\2\2\177x\3\2\2\2\177z\3\2\2\2\177"+
		"}\3\2\2\2\u0080\u0081\3\2\2\2\u0081\u0082\b\5\2\2\u0082\n\3\2\2\2\u0083"+
		"\u0084\5+\26\2\u0084\u0086\7*\2\2\u0085\u0087\5\23\n\2\u0086\u0085\3\2"+
		"\2\2\u0086\u0087\3\2\2\2\u0087\u008b\3\2\2\2\u0088\u008a\5-\27\2\u0089"+
		"\u0088\3\2\2\2\u008a\u008d\3\2\2\2\u008b\u0089\3\2\2\2\u008b\u008c\3\2"+
		"\2\2\u008c\u008e\3\2\2\2\u008d\u008b\3\2\2\2\u008e\u008f\7+\2\2\u008f"+
		"\f\3\2\2\2\u0090\u0092\7\17\2\2\u0091\u0090\3\2\2\2\u0091\u0092\3\2\2"+
		"\2\u0092\u0093\3\2\2\2\u0093\u0096\7\f\2\2\u0094\u0096\7\17\2\2\u0095"+
		"\u0091\3\2\2\2\u0095\u0094\3\2\2\2\u0096\16\3\2\2\2\u0097\u0099\5\23\n"+
		"\2\u0098\u0097\3\2\2\2\u0098\u0099\3\2\2\2\u0099\u009a\3\2\2\2\u009a\u009e"+
		"\5-\27\2\u009b\u009d\5-\27\2\u009c\u009b\3\2\2\2\u009d\u00a0\3\2\2\2\u009e"+
		"\u009c\3\2\2\2\u009e\u009f\3\2\2\2\u009f\u00a6\3\2\2\2\u00a0\u009e\3\2"+
		"\2\2\u00a1\u00a7\5\33\16\2\u00a2\u00a7\5\'\24\2\u00a3\u00a7\5\35\17\2"+
		"\u00a4\u00a7\5\21\t\2\u00a5\u00a7\5\13\6\2\u00a6\u00a1\3\2\2\2\u00a6\u00a2"+
		"\3\2\2\2\u00a6\u00a3\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a6\u00a5\3\2\2\2\u00a6"+
		"\u00a7\3\2\2\2\u00a7\20\3\2\2\2\u00a8\u00aa\t\3\2\2\u00a9\u00a8\3\2\2"+
		"\2\u00aa\u00ab\3\2\2\2\u00ab\u00a9\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\22"+
		"\3\2\2\2\u00ad\u00ae\7/\2\2\u00ae\24\3\2\2\2\u00af\u00b0\7*\2\2\u00b0"+
		"\u00b1\3\2\2\2\u00b1\u00b2\b\13\2\2\u00b2\26\3\2\2\2\u00b3\u00b4\7+\2"+
		"\2\u00b4\u00b5\3\2\2\2\u00b5\u00b6\b\f\2\2\u00b6\30\3\2\2\2\u00b7\u00b8"+
		"\7\60\2\2\u00b8\u00b9\3\2\2\2\u00b9\u00ba\b\r\2\2\u00ba\32\3\2\2\2\u00bb"+
		"\u00bc\7#\2\2\u00bc\34\3\2\2\2\u00bd\u00be\7A\2\2\u00be\36\3\2\2\2\u00bf"+
		"\u00c0\7)\2\2\u00c0 \3\2\2\2\u00c1\u00c2\5\21\t\2\u00c2\u00c3\5\31\r\2"+
		"\u00c3\u00c4\3\2\2\2\u00c4\u00c5\b\21\2\2\u00c5\"\3\2\2\2\u00c6\u00c7"+
		"\7%\2\2\u00c7\u00c8\3\2\2\2\u00c8\u00c9\b\22\2\2\u00c9$\3\2\2\2\u00ca"+
		"\u00cb\7.\2\2\u00cb\u00cc\3\2\2\2\u00cc\u00cd\b\23\2\2\u00cd&\3\2\2\2"+
		"\u00ce\u00cf\7,\2\2\u00cf\u00d0\3\2\2\2\u00d0\u00d1\b\24\2\2\u00d1(\3"+
		"\2\2\2\u00d2\u00d3\7=\2\2\u00d3\u00d4\3\2\2\2\u00d4\u00d5\b\25\2\2\u00d5"+
		"*\3\2\2\2\u00d6\u00d8\t\4\2\2\u00d7\u00d6\3\2\2\2\u00d8\u00d9\3\2\2\2"+
		"\u00d9\u00d7\3\2\2\2\u00d9\u00da\3\2\2\2\u00da\u00db\3\2\2\2\u00db\u00dc"+
		"\b\26\2\2\u00dc,\3\2\2\2\u00dd\u00de\t\5\2\2\u00de.\3\2\2\2\u00df\u00e0"+
		"\13\2\2\2\u00e0\60\3\2\2\2\16\2u\177\u0086\u008b\u0091\u0095\u0098\u009e"+
		"\u00a6\u00ab\u00d9\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}