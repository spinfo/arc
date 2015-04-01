// Generated from /Users/franciscomondaca/spinfo/repositories/arc/arc.ext.puter.gramm/Puter.g4 by ANTLR 4.5
package de.uni_koeln.spinfo.arc.puter.gramm;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PuterLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		OPENINGTAG=1, CLOSINGTAG=2, PAREX=3, ROMAN=4, ARABIC=5, GRAMM=6, QM=7, 
		SL=8, EXK=9, DOT=10, COMPLEXWORD=11, TOKEN=12, CHARSEQUENCE=13, COMMA=14, 
		HYP=15, ENUM=16, SEMICOLON=17, ALT=18, REF=19, RE=20, NEWLINE=21, WS=22, 
		ErrorChar=23;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"OPENINGTAG", "CLOSINGTAG", "PAREX", "ROMAN", "ARABIC", "GRAMM", "QM", 
		"SL", "EXK", "DOT", "COMPLEXWORD", "TOKEN", "CHARSEQUENCE", "COMMA", "HYP", 
		"ENUM", "SEMICOLON", "ALT", "REF", "RE", "CAPLETTER", "LETTER", "NUMBER", 
		"NEWLINE", "WS", "ErrorChar"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'<E>'", "'</E>'", null, null, null, null, "'?'", "'/'", "'!'", 
		"'.'", null, null, null, "','", "'-'", null, "';'", null, "'\\u25ba'", 
		"'~'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "OPENINGTAG", "CLOSINGTAG", "PAREX", "ROMAN", "ARABIC", "GRAMM", 
		"QM", "SL", "EXK", "DOT", "COMPLEXWORD", "TOKEN", "CHARSEQUENCE", "COMMA", 
		"HYP", "ENUM", "SEMICOLON", "ALT", "REF", "RE", "NEWLINE", "WS", "ErrorChar"
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


	public PuterLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Puter.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\31\u0167\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4"+
		"\6\4C\n\4\r\4\16\4D\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5^\n\5\3\5\3\5\3\5\3\5\3"+
		"\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5"+
		"\7\u00f0\n\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13"+
		"\3\f\3\f\3\f\3\f\6\f\u0104\n\f\r\f\16\f\u0105\5\f\u0108\n\f\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u0115\n\r\3\r\5\r\u0118\n\r\3\r"+
		"\3\r\5\r\u011c\n\r\3\16\3\16\5\16\u0120\n\16\3\16\7\16\u0123\n\16\f\16"+
		"\16\16\u0126\13\16\3\16\3\16\7\16\u012a\n\16\f\16\16\16\u012d\13\16\7"+
		"\16\u012f\n\16\f\16\16\16\u0132\13\16\3\17\3\17\3\17\3\17\3\20\3\20\3"+
		"\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\23\3\23\6\23\u0145\n\23"+
		"\r\23\16\23\u0146\3\23\3\23\3\24\3\24\3\25\3\25\3\25\3\25\3\26\3\26\3"+
		"\27\5\27\u0154\n\27\3\30\3\30\3\31\5\31\u0159\n\31\3\31\3\31\5\31\u015d"+
		"\n\31\3\32\6\32\u0160\n\32\r\32\16\32\u0161\3\32\3\32\3\33\3\33\2\2\34"+
		"\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20"+
		"\37\21!\22#\23%\24\'\25)\26+\2-\2/\2\61\27\63\30\65\31\3\2\t\3\2++\4\2"+
		"hhoo\3\2c|\3\2C\\\b\2c|\u00ea\u00ea\u00ec\u00ec\u00f1\u00f1\u00f8\u00f8"+
		"\u00fe\u00fe\3\2\63;\4\2\13\13\"\"\u0194\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3"+
		"\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2"+
		"\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35"+
		"\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)"+
		"\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\3\67\3\2\2\2\5;\3\2\2"+
		"\2\7@\3\2\2\2\t]\3\2\2\2\13c\3\2\2\2\r\u00ef\3\2\2\2\17\u00f1\3\2\2\2"+
		"\21\u00f5\3\2\2\2\23\u00f9\3\2\2\2\25\u00fd\3\2\2\2\27\u00ff\3\2\2\2\31"+
		"\u0114\3\2\2\2\33\u011f\3\2\2\2\35\u0133\3\2\2\2\37\u0137\3\2\2\2!\u0139"+
		"\3\2\2\2#\u013e\3\2\2\2%\u0142\3\2\2\2\'\u014a\3\2\2\2)\u014c\3\2\2\2"+
		"+\u0150\3\2\2\2-\u0153\3\2\2\2/\u0155\3\2\2\2\61\u015c\3\2\2\2\63\u015f"+
		"\3\2\2\2\65\u0165\3\2\2\2\678\7>\2\289\7G\2\29:\7@\2\2:\4\3\2\2\2;<\7"+
		">\2\2<=\7\61\2\2=>\7G\2\2>?\7@\2\2?\6\3\2\2\2@B\7*\2\2AC\n\2\2\2BA\3\2"+
		"\2\2CD\3\2\2\2DB\3\2\2\2DE\3\2\2\2EF\3\2\2\2FG\7+\2\2G\b\3\2\2\2H^\7K"+
		"\2\2IJ\7K\2\2J^\7K\2\2KL\7K\2\2LM\7K\2\2M^\7K\2\2NO\7K\2\2O^\7X\2\2P^"+
		"\7X\2\2QR\7X\2\2R^\7K\2\2ST\7X\2\2TU\7K\2\2U^\7K\2\2VW\7X\2\2WX\7K\2\2"+
		"XY\7K\2\2Y^\7K\2\2Z[\7K\2\2[^\7Z\2\2\\^\7Z\2\2]H\3\2\2\2]I\3\2\2\2]K\3"+
		"\2\2\2]N\3\2\2\2]P\3\2\2\2]Q\3\2\2\2]S\3\2\2\2]V\3\2\2\2]Z\3\2\2\2]\\"+
		"\3\2\2\2^_\3\2\2\2_`\5\25\13\2`a\3\2\2\2ab\b\5\2\2b\n\3\2\2\2cd\5/\30"+
		"\2de\5\25\13\2ef\3\2\2\2fg\b\6\2\2g\f\3\2\2\2h\u00f0\t\3\2\2ij\7o\2\2"+
		"jk\7.\2\2k\u00f0\7h\2\2lm\7c\2\2mn\7f\2\2no\7l\2\2op\7\61\2\2pq\7c\2\2"+
		"qr\7f\2\2r\u00f0\7x\2\2st\7c\2\2tu\7f\2\2uv\7l\2\2vw\7\"\2\2wx\7k\2\2"+
		"xy\7p\2\2yz\7x\2\2z{\7c\2\2{|\7t\2\2|}\7\61\2\2}~\7p\2\2~\177\7w\2\2\177"+
		"\u00f0\7o\2\2\u0080\u0081\7c\2\2\u0081\u0082\7f\2\2\u0082\u0083\7l\2\2"+
		"\u0083\u0084\7\"\2\2\u0084\u0085\7k\2\2\u0085\u0086\7p\2\2\u0086\u0087"+
		"\7x\2\2\u0087\u0088\7c\2\2\u0088\u00f0\7t\2\2\u0089\u008a\7c\2\2\u008a"+
		"\u008b\7f\2\2\u008b\u00f0\7l\2\2\u008c\u008d\7c\2\2\u008d\u008e\7f\2\2"+
		"\u008e\u00f0\7x\2\2\u008f\u0090\7k\2\2\u0090\u0091\7p\2\2\u0091\u0092"+
		"\7x\2\2\u0092\u0093\7c\2\2\u0093\u0094\7t\2\2\u0094\u0095\7\61\2\2\u0095"+
		"\u0096\7p\2\2\u0096\u0097\7w\2\2\u0097\u00f0\7o\2\2\u0098\u0099\7r\2\2"+
		"\u0099\u009a\7t\2\2\u009a\u009b\7g\2\2\u009b\u00f0\7r\2\2\u009c\u009d"+
		"\7k\2\2\u009d\u009e\7p\2\2\u009e\u009f\7v\2\2\u009f\u00a0\7g\2\2\u00a0"+
		"\u00a1\7t\2\2\u00a1\u00f0\7l\2\2\u00a2\u00a3\7k\2\2\u00a3\u00a4\7p\2\2"+
		"\u00a4\u00a5\7v\2\2\u00a5\u00f0\7t\2\2\u00a6\u00a7\7k\2\2\u00a7\u00a8"+
		"\7p\2\2\u00a8\u00a9\7v\2\2\u00a9\u00aa\7t\2\2\u00aa\u00ab\7\61\2\2\u00ab"+
		"\u00ac\7v\2\2\u00ac\u00f0\7t\2\2\u00ad\u00ae\7v\2\2\u00ae\u00af\7t\2\2"+
		"\u00af\u00b0\7\"\2\2\u00b0\u00b1\7k\2\2\u00b1\u00b2\7p\2\2\u00b2\u00f0"+
		"\7f\2\2\u00b3\u00b4\7v\2\2\u00b4\u00f0\7t\2\2\u00b5\u00b6\7o\2\2\u00b6"+
		"\u00b7\7r\2\2\u00b7\u00f0\7n\2\2\u00b8\u00b9\7h\2\2\u00b9\u00ba\7r\2\2"+
		"\u00ba\u00f0\7n\2\2\u00bb\u00bc\7e\2\2\u00bc\u00f0\7l\2\2\u00bd\u00be"+
		"\7r\2\2\u00be\u00bf\7t\2\2\u00bf\u00c0\7q\2\2\u00c0\u00c1\7p\2\2\u00c1"+
		"\u00c2\7\"\2\2\u00c2\u00c3\7k\2\2\u00c3\u00c4\7p\2\2\u00c4\u00c5\7f\2"+
		"\2\u00c5\u00c6\7g\2\2\u00c6\u00f0\7h\2\2\u00c7\u00c8\7r\2\2\u00c8\u00c9"+
		"\7t\2\2\u00c9\u00ca\7q\2\2\u00ca\u00cb\7p\2\2\u00cb\u00cc\7\"\2\2\u00cc"+
		"\u00cd\7r\2\2\u00cd\u00ce\7g\2\2\u00ce\u00cf\7t\2\2\u00cf\u00f0\7u\2\2"+
		"\u00d0\u00d1\7r\2\2\u00d1\u00d2\7t\2\2\u00d2\u00d3\7q\2\2\u00d3\u00d4"+
		"\7p\2\2\u00d4\u00d5\7\"\2\2\u00d5\u00d6\7r\2\2\u00d6\u00d7\7g\2\2\u00d7"+
		"\u00d8\7t\2\2\u00d8\u00d9\7u\2\2\u00d9\u00da\7\61\2\2\u00da\u00db\7t\2"+
		"\2\u00db\u00dc\7g\2\2\u00dc\u00dd\7h\2\2\u00dd\u00f0\7n\2\2\u00de\u00df"+
		"\7r\2\2\u00df\u00e0\7t\2\2\u00e0\u00e1\7q\2\2\u00e1\u00f0\7p\2\2\u00e2"+
		"\u00e3\7t\2\2\u00e3\u00e4\7g\2\2\u00e4\u00e5\7h\2\2\u00e5\u00f0\7n\2\2"+
		"\u00e6\u00e7\7h\2\2\u00e7\u00e8\7e\2\2\u00e8\u00e9\7q\2\2\u00e9\u00ea"+
		"\7n\2\2\u00ea\u00f0\7n\2\2\u00eb\u00ec\7r\2\2\u00ec\u00ed\7\"\2\2\u00ed"+
		"\u00ee\7u\2\2\u00ee\u00f0\7i\2\2\u00efh\3\2\2\2\u00efi\3\2\2\2\u00efl"+
		"\3\2\2\2\u00efs\3\2\2\2\u00ef\u0080\3\2\2\2\u00ef\u0089\3\2\2\2\u00ef"+
		"\u008c\3\2\2\2\u00ef\u008f\3\2\2\2\u00ef\u0098\3\2\2\2\u00ef\u009c\3\2"+
		"\2\2\u00ef\u00a2\3\2\2\2\u00ef\u00a6\3\2\2\2\u00ef\u00ad\3\2\2\2\u00ef"+
		"\u00b3\3\2\2\2\u00ef\u00b5\3\2\2\2\u00ef\u00b8\3\2\2\2\u00ef\u00bb\3\2"+
		"\2\2\u00ef\u00bd\3\2\2\2\u00ef\u00c7\3\2\2\2\u00ef\u00d0\3\2\2\2\u00ef"+
		"\u00de\3\2\2\2\u00ef\u00e2\3\2\2\2\u00ef\u00e6\3\2\2\2\u00ef\u00eb\3\2"+
		"\2\2\u00f0\16\3\2\2\2\u00f1\u00f2\7A\2\2\u00f2\u00f3\3\2\2\2\u00f3\u00f4"+
		"\b\b\2\2\u00f4\20\3\2\2\2\u00f5\u00f6\7\61\2\2\u00f6\u00f7\3\2\2\2\u00f7"+
		"\u00f8\b\t\2\2\u00f8\22\3\2\2\2\u00f9\u00fa\7#\2\2\u00fa\u00fb\3\2\2\2"+
		"\u00fb\u00fc\b\n\2\2\u00fc\24\3\2\2\2\u00fd\u00fe\7\60\2\2\u00fe\26\3"+
		"\2\2\2\u00ff\u0107\5\31\r\2\u0100\u0101\5\35\17\2\u0101\u0103\5\37\20"+
		"\2\u0102\u0104\5-\27\2\u0103\u0102\3\2\2\2\u0104\u0105\3\2\2\2\u0105\u0103"+
		"\3\2\2\2\u0105\u0106\3\2\2\2\u0106\u0108\3\2\2\2\u0107\u0100\3\2\2\2\u0107"+
		"\u0108\3\2\2\2\u0108\30\3\2\2\2\u0109\u0115\5\33\16\2\u010a\u010b\5%\23"+
		"\2\u010b\u010c\5\33\16\2\u010c\u0115\3\2\2\2\u010d\u010e\5\33\16\2\u010e"+
		"\u010f\5%\23\2\u010f\u0110\5\33\16\2\u0110\u0115\3\2\2\2\u0111\u0112\5"+
		"\33\16\2\u0112\u0113\5%\23\2\u0113\u0115\3\2\2\2\u0114\u0109\3\2\2\2\u0114"+
		"\u010a\3\2\2\2\u0114\u010d\3\2\2\2\u0114\u0111\3\2\2\2\u0115\u011b\3\2"+
		"\2\2\u0116\u0118\5\23\n\2\u0117\u0116\3\2\2\2\u0117\u0118\3\2\2\2\u0118"+
		"\u0119\3\2\2\2\u0119\u011c\5\17\b\2\u011a\u011c\5\23\n\2\u011b\u0117\3"+
		"\2\2\2\u011b\u011a\3\2\2\2\u011b\u011c\3\2\2\2\u011c\32\3\2\2\2\u011d"+
		"\u0120\5-\27\2\u011e\u0120\5+\26\2\u011f\u011d\3\2\2\2\u011f\u011e\3\2"+
		"\2\2\u0120\u0124\3\2\2\2\u0121\u0123\5-\27\2\u0122\u0121\3\2\2\2\u0123"+
		"\u0126\3\2\2\2\u0124\u0122\3\2\2\2\u0124\u0125\3\2\2\2\u0125\u0130\3\2"+
		"\2\2\u0126\u0124\3\2\2\2\u0127\u012b\5\37\20\2\u0128\u012a\5-\27\2\u0129"+
		"\u0128\3\2\2\2\u012a\u012d\3\2\2\2\u012b\u0129\3\2\2\2\u012b\u012c\3\2"+
		"\2\2\u012c\u012f\3\2\2\2\u012d\u012b\3\2\2\2\u012e\u0127\3\2\2\2\u012f"+
		"\u0132\3\2\2\2\u0130\u012e\3\2\2\2\u0130\u0131\3\2\2\2\u0131\34\3\2\2"+
		"\2\u0132\u0130\3\2\2\2\u0133\u0134\7.\2\2\u0134\u0135\3\2\2\2\u0135\u0136"+
		"\b\17\2\2\u0136\36\3\2\2\2\u0137\u0138\7/\2\2\u0138 \3\2\2\2\u0139\u013a"+
		"\t\4\2\2\u013a\u013b\7+\2\2\u013b\u013c\3\2\2\2\u013c\u013d\b\21\2\2\u013d"+
		"\"\3\2\2\2\u013e\u013f\7=\2\2\u013f\u0140\3\2\2\2\u0140\u0141\b\22\2\2"+
		"\u0141$\3\2\2\2\u0142\u0144\7*\2\2\u0143\u0145\5-\27\2\u0144\u0143\3\2"+
		"\2\2\u0145\u0146\3\2\2\2\u0146\u0144\3\2\2\2\u0146\u0147\3\2\2\2\u0147"+
		"\u0148\3\2\2\2\u0148\u0149\7+\2\2\u0149&\3\2\2\2\u014a\u014b\7\u25bc\2"+
		"\2\u014b(\3\2\2\2\u014c\u014d\7\u0080\2\2\u014d\u014e\3\2\2\2\u014e\u014f"+
		"\b\25\2\2\u014f*\3\2\2\2\u0150\u0151\t\5\2\2\u0151,\3\2\2\2\u0152\u0154"+
		"\t\6\2\2\u0153\u0152\3\2\2\2\u0154.\3\2\2\2\u0155\u0156\t\7\2\2\u0156"+
		"\60\3\2\2\2\u0157\u0159\7\17\2\2\u0158\u0157\3\2\2\2\u0158\u0159\3\2\2"+
		"\2\u0159\u015a\3\2\2\2\u015a\u015d\7\f\2\2\u015b\u015d\7t\2\2\u015c\u0158"+
		"\3\2\2\2\u015c\u015b\3\2\2\2\u015d\62\3\2\2\2\u015e\u0160\t\b\2\2\u015f"+
		"\u015e\3\2\2\2\u0160\u0161\3\2\2\2\u0161\u015f\3\2\2\2\u0161\u0162\3\2"+
		"\2\2\u0162\u0163\3\2\2\2\u0163\u0164\b\32\2\2\u0164\64\3\2\2\2\u0165\u0166"+
		"\13\2\2\2\u0166\66\3\2\2\2\24\2D]\u00ef\u0105\u0107\u0114\u0117\u011b"+
		"\u011f\u0124\u012b\u0130\u0146\u0153\u0158\u015c\u0161\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}