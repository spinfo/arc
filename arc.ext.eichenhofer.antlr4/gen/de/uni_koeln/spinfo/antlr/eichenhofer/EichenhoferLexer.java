// Generated from /Users/franciscomondaca/spinfo/repositories/antlr4/eichenhofer/eichenhofer.antlr4/Eichenhofer.g4 by ANTLR 4.4.1-dev
package de.uni_koeln.spinfo.antlr.eichenhofer;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class EichenhoferLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.4.1-dev", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		OPENINGTAG=1, CLOSINGTAG=2, POS=3, EI=4, RN=5, OSP=6, OCP=7, CSP=8, CCP=9, 
		PAR=10, LINEBREAK=11, COMPLEXTOKEN=12, INT=13, S=14, DOT=15, EXK=16, QM=17, 
		AP=18, ED=19, R=20, COMMA=21, AST=22, SEMICOLON=23, WS=24, ErrorChar=25;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'", 
		"'\\u0013'", "'\\u0014'", "'\\u0015'", "'\\u0016'", "'\\u0017'", "'\\u0018'", 
		"'\\u0019'"
	};
	public static final String[] ruleNames = {
		"OPENINGTAG", "CLOSINGTAG", "POS", "EI", "RN", "OSP", "OCP", "CSP", "CCP", 
		"PAR", "LINEBREAK", "COMPLEXTOKEN", "INT", "S", "DOT", "EXK", "QM", "AP", 
		"ED", "R", "COMMA", "AST", "SEMICOLON", "WS", "NameStartChar", "ErrorChar"
	};


	public EichenhoferLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Eichenhofer.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\33\u0105\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4u\n\4"+
		"\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\u0083\n\6\3\6\3\6"+
		"\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\5\13\u0091\n\13\3\13\5\13\u0094"+
		"\n\13\3\13\6\13\u0097\n\13\r\13\16\13\u0098\3\13\3\13\5\13\u009d\n\13"+
		"\3\f\5\f\u00a0\n\f\3\f\3\f\5\f\u00a4\n\f\3\r\5\r\u00a7\n\r\3\r\6\r\u00aa"+
		"\n\r\r\r\16\r\u00ab\3\r\3\r\3\r\3\r\6\r\u00b2\n\r\r\r\16\r\u00b3\3\r\5"+
		"\r\u00b7\n\r\3\r\6\r\u00ba\n\r\r\r\16\r\u00bb\3\r\5\r\u00bf\n\r\3\r\6"+
		"\r\u00c2\n\r\r\r\16\r\u00c3\3\r\5\r\u00c7\n\r\3\r\3\r\5\r\u00cb\n\r\3"+
		"\r\6\r\u00ce\n\r\r\r\16\r\u00cf\3\r\5\r\u00d3\n\r\3\r\3\r\5\r\u00d7\n"+
		"\r\5\r\u00d9\n\r\3\16\6\16\u00dc\n\16\r\16\16\16\u00dd\3\17\3\17\3\20"+
		"\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\25\3\25"+
		"\3\25\3\25\3\26\3\26\3\27\3\27\3\27\3\27\3\30\3\30\3\31\6\31\u00fc\n\31"+
		"\r\31\16\31\u00fd\3\31\3\31\3\32\3\32\3\33\3\33\2\2\34\3\3\5\4\7\5\t\6"+
		"\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24"+
		"\'\25)\26+\27-\30/\31\61\32\63\2\65\33\3\2\7\5\2\61\61hhoo\4\2\u00ab\u00ab"+
		"\u2782\u2783\3\2\62;\5\2\13\13\16\17\"\"\17\2C\\c|\u00c2\u00d8\u00da\u00f8"+
		"\u00fa\u0301\u0372\u037f\u0381\u2001\u200e\u200f\u2072\u2191\u2c02\u2ff1"+
		"\u3003\ud801\uf902\ufdd1\ufdf2\uffff\u012e\2\3\3\2\2\2\2\5\3\2\2\2\2\7"+
		"\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2"+
		"\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2"+
		"\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2"+
		"\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\65\3\2\2\2"+
		"\3\67\3\2\2\2\5;\3\2\2\2\7t\3\2\2\2\tv\3\2\2\2\13\u0082\3\2\2\2\r\u0086"+
		"\3\2\2\2\17\u0088\3\2\2\2\21\u008a\3\2\2\2\23\u008c\3\2\2\2\25\u0090\3"+
		"\2\2\2\27\u00a3\3\2\2\2\31\u00d8\3\2\2\2\33\u00db\3\2\2\2\35\u00df\3\2"+
		"\2\2\37\u00e1\3\2\2\2!\u00e3\3\2\2\2#\u00e5\3\2\2\2%\u00e7\3\2\2\2\'\u00e9"+
		"\3\2\2\2)\u00ee\3\2\2\2+\u00f2\3\2\2\2-\u00f4\3\2\2\2/\u00f8\3\2\2\2\61"+
		"\u00fb\3\2\2\2\63\u0101\3\2\2\2\65\u0103\3\2\2\2\678\7>\2\289\7G\2\29"+
		":\7@\2\2:\4\3\2\2\2;<\7>\2\2<=\7\61\2\2=>\7G\2\2>?\7@\2\2?\6\3\2\2\2@"+
		"u\t\2\2\2AB\7h\2\2BC\7r\2\2Cu\7n\2\2DE\7o\2\2EF\7r\2\2Fu\7n\2\2Gu\7o\2"+
		"\2HI\7t\2\2Iu\7p\2\2JK\7o\2\2Ku\7h\2\2LM\7v\2\2Mu\7t\2\2NO\7h\2\2Ou\7"+
		"t\2\2PQ\7v\2\2QR\7t\2\2RS\7\61\2\2ST\7k\2\2TU\7p\2\2Uu\7v\2\2VW\7c\2\2"+
		"WX\7t\2\2Xu\7v\2\2YZ\7c\2\2Z[\7f\2\2[u\7l\2\2\\]\7c\2\2]^\7f\2\2^u\7x"+
		"\2\2_`\7k\2\2`a\7p\2\2ab\7v\2\2bc\7g\2\2cd\7t\2\2du\7l\2\2ef\7e\2\2fg"+
		"\7q\2\2gh\7p\2\2hu\7l\2\2ij\7r\2\2jk\7t\2\2kl\7g\2\2lu\7r\2\2mn\7r\2\2"+
		"no\7t\2\2op\7q\2\2pu\7p\2\2qr\7k\2\2rs\7p\2\2su\7v\2\2t@\3\2\2\2tA\3\2"+
		"\2\2tD\3\2\2\2tG\3\2\2\2tH\3\2\2\2tJ\3\2\2\2tL\3\2\2\2tN\3\2\2\2tP\3\2"+
		"\2\2tV\3\2\2\2tY\3\2\2\2t\\\3\2\2\2t_\3\2\2\2te\3\2\2\2ti\3\2\2\2tm\3"+
		"\2\2\2tq\3\2\2\2u\b\3\2\2\2vw\t\3\2\2wx\3\2\2\2xy\b\5\2\2y\n\3\2\2\2z"+
		"\u0083\7K\2\2{|\7K\2\2|\u0083\7K\2\2}~\7K\2\2~\177\7K\2\2\177\u0083\7"+
		"K\2\2\u0080\u0081\7K\2\2\u0081\u0083\7X\2\2\u0082z\3\2\2\2\u0082{\3\2"+
		"\2\2\u0082}\3\2\2\2\u0082\u0080\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0085"+
		"\b\6\2\2\u0085\f\3\2\2\2\u0086\u0087\7]\2\2\u0087\16\3\2\2\2\u0088\u0089"+
		"\7*\2\2\u0089\20\3\2\2\2\u008a\u008b\7_\2\2\u008b\22\3\2\2\2\u008c\u008d"+
		"\7+\2\2\u008d\24\3\2\2\2\u008e\u0091\5\r\7\2\u008f\u0091\5\17\b\2\u0090"+
		"\u008e\3\2\2\2\u0090\u008f\3\2\2\2\u0091\u0093\3\2\2\2\u0092\u0094\5\35"+
		"\17\2\u0093\u0092\3\2\2\2\u0093\u0094\3\2\2\2\u0094\u0096\3\2\2\2\u0095"+
		"\u0097\5\63\32\2\u0096\u0095\3\2\2\2\u0097\u0098\3\2\2\2\u0098\u0096\3"+
		"\2\2\2\u0098\u0099\3\2\2\2\u0099\u009c\3\2\2\2\u009a\u009d\5\21\t\2\u009b"+
		"\u009d\5\23\n\2\u009c\u009a\3\2\2\2\u009c\u009b\3\2\2\2\u009d\26\3\2\2"+
		"\2\u009e\u00a0\7\17\2\2\u009f\u009e\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0"+
		"\u00a1\3\2\2\2\u00a1\u00a4\7\f\2\2\u00a2\u00a4\7\17\2\2\u00a3\u009f\3"+
		"\2\2\2\u00a3\u00a2\3\2\2\2\u00a4\30\3\2\2\2\u00a5\u00a7\5\25\13\2\u00a6"+
		"\u00a5\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7\u00a9\3\2\2\2\u00a8\u00aa\5\63"+
		"\32\2\u00a9\u00a8\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00a9\3\2\2\2\u00ab"+
		"\u00ac\3\2\2\2\u00ac\u00ad\3\2\2\2\u00ad\u00ae\5+\26\2\u00ae\u00af\5\61"+
		"\31\2\u00af\u00b1\5\35\17\2\u00b0\u00b2\5\63\32\2\u00b1\u00b0\3\2\2\2"+
		"\u00b2\u00b3\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4\u00d9"+
		"\3\2\2\2\u00b5\u00b7\5\25\13\2\u00b6\u00b5\3\2\2\2\u00b6\u00b7\3\2\2\2"+
		"\u00b7\u00b9\3\2\2\2\u00b8\u00ba\5\63\32\2\u00b9\u00b8\3\2\2\2\u00ba\u00bb"+
		"\3\2\2\2\u00bb\u00b9\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc\u00be\3\2\2\2\u00bd"+
		"\u00bf\5\25\13\2\u00be\u00bd\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf\u00c1\3"+
		"\2\2\2\u00c0\u00c2\5\63\32\2\u00c1\u00c0\3\2\2\2\u00c2\u00c3\3\2\2\2\u00c3"+
		"\u00c1\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4\u00c6\3\2\2\2\u00c5\u00c7\5!"+
		"\21\2\u00c6\u00c5\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7\u00ca\3\2\2\2\u00c8"+
		"\u00cb\5\33\16\2\u00c9\u00cb\5\25\13\2\u00ca\u00c8\3\2\2\2\u00ca\u00c9"+
		"\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb\u00d9\3\2\2\2\u00cc\u00ce\5\63\32\2"+
		"\u00cd\u00cc\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf\u00cd\3\2\2\2\u00cf\u00d0"+
		"\3\2\2\2\u00d0\u00d2\3\2\2\2\u00d1\u00d3\5!\21\2\u00d2\u00d1\3\2\2\2\u00d2"+
		"\u00d3\3\2\2\2\u00d3\u00d6\3\2\2\2\u00d4\u00d7\5\33\16\2\u00d5\u00d7\5"+
		"\25\13\2\u00d6\u00d4\3\2\2\2\u00d6\u00d5\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7"+
		"\u00d9\3\2\2\2\u00d8\u00a6\3\2\2\2\u00d8\u00b6\3\2\2\2\u00d8\u00cd\3\2"+
		"\2\2\u00d9\32\3\2\2\2\u00da\u00dc\t\4\2\2\u00db\u00da\3\2\2\2\u00dc\u00dd"+
		"\3\2\2\2\u00dd\u00db\3\2\2\2\u00dd\u00de\3\2\2\2\u00de\34\3\2\2\2\u00df"+
		"\u00e0\7/\2\2\u00e0\36\3\2\2\2\u00e1\u00e2\7\60\2\2\u00e2 \3\2\2\2\u00e3"+
		"\u00e4\7#\2\2\u00e4\"\3\2\2\2\u00e5\u00e6\7A\2\2\u00e6$\3\2\2\2\u00e7"+
		"\u00e8\7)\2\2\u00e8&\3\2\2\2\u00e9\u00ea\5\33\16\2\u00ea\u00eb\5\37\20"+
		"\2\u00eb\u00ec\3\2\2\2\u00ec\u00ed\b\24\2\2\u00ed(\3\2\2\2\u00ee\u00ef"+
		"\7%\2\2\u00ef\u00f0\3\2\2\2\u00f0\u00f1\b\25\2\2\u00f1*\3\2\2\2\u00f2"+
		"\u00f3\7.\2\2\u00f3,\3\2\2\2\u00f4\u00f5\7,\2\2\u00f5\u00f6\3\2\2\2\u00f6"+
		"\u00f7\b\27\2\2\u00f7.\3\2\2\2\u00f8\u00f9\7=\2\2\u00f9\60\3\2\2\2\u00fa"+
		"\u00fc\t\5\2\2\u00fb\u00fa\3\2\2\2\u00fc\u00fd\3\2\2\2\u00fd\u00fb\3\2"+
		"\2\2\u00fd\u00fe\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ff\u0100\b\31\2\2\u0100"+
		"\62\3\2\2\2\u0101\u0102\t\6\2\2\u0102\64\3\2\2\2\u0103\u0104\13\2\2\2"+
		"\u0104\66\3\2\2\2\32\2t\u0082\u0090\u0093\u0098\u009c\u009f\u00a3\u00a6"+
		"\u00ab\u00b3\u00b6\u00bb\u00be\u00c3\u00c6\u00ca\u00cf\u00d2\u00d6\u00d8"+
		"\u00dd\u00fd\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}