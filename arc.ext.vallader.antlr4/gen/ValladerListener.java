// Generated from H:/git/arc/arc.backend/arc.extractions/tscharner_vallader/tscharner_vallader.antlr4\Vallader.g4 by ANTLR 4.4.1-dev
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ValladerParser}.
 */
public interface ValladerListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ValladerParser#entry}.
	 * @param ctx the parse tree
	 */
	void enterEntry(@NotNull ValladerParser.EntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link ValladerParser#entry}.
	 * @param ctx the parse tree
	 */
	void exitEntry(@NotNull ValladerParser.EntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link ValladerParser#phrase}.
	 * @param ctx the parse tree
	 */
	void enterPhrase(@NotNull ValladerParser.PhraseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ValladerParser#phrase}.
	 * @param ctx the parse tree
	 */
	void exitPhrase(@NotNull ValladerParser.PhraseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ValladerParser#additional_info}.
	 * @param ctx the parse tree
	 */
	void enterAdditional_info(@NotNull ValladerParser.Additional_infoContext ctx);
	/**
	 * Exit a parse tree produced by {@link ValladerParser#additional_info}.
	 * @param ctx the parse tree
	 */
	void exitAdditional_info(@NotNull ValladerParser.Additional_infoContext ctx);
	/**
	 * Enter a parse tree produced by {@link ValladerParser#infl_info}.
	 * @param ctx the parse tree
	 */
	void enterInfl_info(@NotNull ValladerParser.Infl_infoContext ctx);
	/**
	 * Exit a parse tree produced by {@link ValladerParser#infl_info}.
	 * @param ctx the parse tree
	 */
	void exitInfl_info(@NotNull ValladerParser.Infl_infoContext ctx);
	/**
	 * Enter a parse tree produced by {@link ValladerParser#keyphrase}.
	 * @param ctx the parse tree
	 */
	void enterKeyphrase(@NotNull ValladerParser.KeyphraseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ValladerParser#keyphrase}.
	 * @param ctx the parse tree
	 */
	void exitKeyphrase(@NotNull ValladerParser.KeyphraseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ValladerParser#dict}.
	 * @param ctx the parse tree
	 */
	void enterDict(@NotNull ValladerParser.DictContext ctx);
	/**
	 * Exit a parse tree produced by {@link ValladerParser#dict}.
	 * @param ctx the parse tree
	 */
	void exitDict(@NotNull ValladerParser.DictContext ctx);
	/**
	 * Enter a parse tree produced by {@link ValladerParser#grammatical_info}.
	 * @param ctx the parse tree
	 */
	void enterGrammatical_info(@NotNull ValladerParser.Grammatical_infoContext ctx);
	/**
	 * Exit a parse tree produced by {@link ValladerParser#grammatical_info}.
	 * @param ctx the parse tree
	 */
	void exitGrammatical_info(@NotNull ValladerParser.Grammatical_infoContext ctx);
	/**
	 * Enter a parse tree produced by {@link ValladerParser#error}.
	 * @param ctx the parse tree
	 */
	void enterError(@NotNull ValladerParser.ErrorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ValladerParser#error}.
	 * @param ctx the parse tree
	 */
	void exitError(@NotNull ValladerParser.ErrorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ValladerParser#nge}.
	 * @param ctx the parse tree
	 */
	void enterNge(@NotNull ValladerParser.NgeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ValladerParser#nge}.
	 * @param ctx the parse tree
	 */
	void exitNge(@NotNull ValladerParser.NgeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ValladerParser#lexentry}.
	 * @param ctx the parse tree
	 */
	void enterLexentry(@NotNull ValladerParser.LexentryContext ctx);
	/**
	 * Exit a parse tree produced by {@link ValladerParser#lexentry}.
	 * @param ctx the parse tree
	 */
	void exitLexentry(@NotNull ValladerParser.LexentryContext ctx);
	/**
	 * Enter a parse tree produced by {@link ValladerParser#endEntry}.
	 * @param ctx the parse tree
	 */
	void enterEndEntry(@NotNull ValladerParser.EndEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link ValladerParser#endEntry}.
	 * @param ctx the parse tree
	 */
	void exitEndEntry(@NotNull ValladerParser.EndEntryContext ctx);
}