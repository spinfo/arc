// Generated from /Users/franciscomondaca/spinfo/repositories/extractions/nvs/nvs.antlr4/NVS.g4 by ANTLR 4.4.1-dev
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link NVSParser}.
 */
public interface NVSListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link NVSParser#lemma}.
	 * @param ctx the parse tree
	 */
	void enterLemma(@NotNull NVSParser.LemmaContext ctx);
	/**
	 * Exit a parse tree produced by {@link NVSParser#lemma}.
	 * @param ctx the parse tree
	 */
	void exitLemma(@NotNull NVSParser.LemmaContext ctx);
	/**
	 * Enter a parse tree produced by {@link NVSParser#startSymb}.
	 * @param ctx the parse tree
	 */
	void enterStartSymb(@NotNull NVSParser.StartSymbContext ctx);
	/**
	 * Exit a parse tree produced by {@link NVSParser#startSymb}.
	 * @param ctx the parse tree
	 */
	void exitStartSymb(@NotNull NVSParser.StartSymbContext ctx);
	/**
	 * Enter a parse tree produced by {@link NVSParser#error}.
	 * @param ctx the parse tree
	 */
	void enterError(@NotNull NVSParser.ErrorContext ctx);
	/**
	 * Exit a parse tree produced by {@link NVSParser#error}.
	 * @param ctx the parse tree
	 */
	void exitError(@NotNull NVSParser.ErrorContext ctx);
	/**
	 * Enter a parse tree produced by {@link NVSParser#endEntry}.
	 * @param ctx the parse tree
	 */
	void enterEndEntry(@NotNull NVSParser.EndEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link NVSParser#endEntry}.
	 * @param ctx the parse tree
	 */
	void exitEndEntry(@NotNull NVSParser.EndEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link NVSParser#entry}.
	 * @param ctx the parse tree
	 */
	void enterEntry(@NotNull NVSParser.EntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link NVSParser#entry}.
	 * @param ctx the parse tree
	 */
	void exitEntry(@NotNull NVSParser.EntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link NVSParser#nge}.
	 * @param ctx the parse tree
	 */
	void enterNge(@NotNull NVSParser.NgeContext ctx);
	/**
	 * Exit a parse tree produced by {@link NVSParser#nge}.
	 * @param ctx the parse tree
	 */
	void exitNge(@NotNull NVSParser.NgeContext ctx);
}