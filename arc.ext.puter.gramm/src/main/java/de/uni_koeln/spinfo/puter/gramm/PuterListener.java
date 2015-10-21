// Generated from /Users/franciscomondaca/spinfo/repositories/arc/arc.ext.puter.gramm/Puter.g4 by ANTLR 4.5
package de.uni_koeln.spinfo.puter.gramm;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PuterParser}.
 */
public interface PuterListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link PuterParser#dict}.
	 * @param ctx the parse tree
	 */
	void enterDict(@NotNull PuterParser.DictContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuterParser#dict}.
	 * @param ctx the parse tree
	 */
	void exitDict(@NotNull PuterParser.DictContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuterParser#entry}.
	 * @param ctx the parse tree
	 */
	void enterEntry(@NotNull PuterParser.EntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuterParser#entry}.
	 * @param ctx the parse tree
	 */
	void exitEntry(@NotNull PuterParser.EntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuterParser#error}.
	 * @param ctx the parse tree
	 */
	void enterError(@NotNull PuterParser.ErrorContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuterParser#error}.
	 * @param ctx the parse tree
	 */
	void exitError(@NotNull PuterParser.ErrorContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuterParser#lexentry}.
	 * @param ctx the parse tree
	 */
	void enterLexentry(@NotNull PuterParser.LexentryContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuterParser#lexentry}.
	 * @param ctx the parse tree
	 */
	void exitLexentry(@NotNull PuterParser.LexentryContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuterParser#endEntry}.
	 * @param ctx the parse tree
	 */
	void enterEndEntry(@NotNull PuterParser.EndEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuterParser#endEntry}.
	 * @param ctx the parse tree
	 */
	void exitEndEntry(@NotNull PuterParser.EndEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuterParser#keyphrase}.
	 * @param ctx the parse tree
	 */
	void enterKeyphrase(@NotNull PuterParser.KeyphraseContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuterParser#keyphrase}.
	 * @param ctx the parse tree
	 */
	void exitKeyphrase(@NotNull PuterParser.KeyphraseContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuterParser#phrase}.
	 * @param ctx the parse tree
	 */
	void enterPhrase(@NotNull PuterParser.PhraseContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuterParser#phrase}.
	 * @param ctx the parse tree
	 */
	void exitPhrase(@NotNull PuterParser.PhraseContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuterParser#infl_info}.
	 * @param ctx the parse tree
	 */
	void enterInfl_info(@NotNull PuterParser.Infl_infoContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuterParser#infl_info}.
	 * @param ctx the parse tree
	 */
	void exitInfl_info(@NotNull PuterParser.Infl_infoContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuterParser#grammatical_info}.
	 * @param ctx the parse tree
	 */
	void enterGrammatical_info(@NotNull PuterParser.Grammatical_infoContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuterParser#grammatical_info}.
	 * @param ctx the parse tree
	 */
	void exitGrammatical_info(@NotNull PuterParser.Grammatical_infoContext ctx);
	/**
	 * Enter a parse tree produced by {@link PuterParser#nge}.
	 * @param ctx the parse tree
	 */
	void enterNge(@NotNull PuterParser.NgeContext ctx);
	/**
	 * Exit a parse tree produced by {@link PuterParser#nge}.
	 * @param ctx the parse tree
	 */
	void exitNge(@NotNull PuterParser.NgeContext ctx);
}