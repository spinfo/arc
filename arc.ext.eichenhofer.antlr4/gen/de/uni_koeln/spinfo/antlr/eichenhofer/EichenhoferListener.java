// Generated from /Users/franciscomondaca/spinfo/repositories/antlr4/eichenhofer/eichenhofer.antlr4/Eichenhofer.g4 by ANTLR 4.4.1-dev
package de.uni_koeln.spinfo.antlr.eichenhofer;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link EichenhoferParser}.
 */
public interface EichenhoferListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link EichenhoferParser#lemma}.
	 * @param ctx the parse tree
	 */
	void enterLemma(@NotNull EichenhoferParser.LemmaContext ctx);
	/**
	 * Exit a parse tree produced by {@link EichenhoferParser#lemma}.
	 * @param ctx the parse tree
	 */
	void exitLemma(@NotNull EichenhoferParser.LemmaContext ctx);
	/**
	 * Enter a parse tree produced by {@link EichenhoferParser#startSymb}.
	 * @param ctx the parse tree
	 */
	void enterStartSymb(@NotNull EichenhoferParser.StartSymbContext ctx);
	/**
	 * Exit a parse tree produced by {@link EichenhoferParser#startSymb}.
	 * @param ctx the parse tree
	 */
	void exitStartSymb(@NotNull EichenhoferParser.StartSymbContext ctx);
	/**
	 * Enter a parse tree produced by {@link EichenhoferParser#error}.
	 * @param ctx the parse tree
	 */
	void enterError(@NotNull EichenhoferParser.ErrorContext ctx);
	/**
	 * Exit a parse tree produced by {@link EichenhoferParser#error}.
	 * @param ctx the parse tree
	 */
	void exitError(@NotNull EichenhoferParser.ErrorContext ctx);
	/**
	 * Enter a parse tree produced by {@link EichenhoferParser#endEntry}.
	 * @param ctx the parse tree
	 */
	void enterEndEntry(@NotNull EichenhoferParser.EndEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link EichenhoferParser#endEntry}.
	 * @param ctx the parse tree
	 */
	void exitEndEntry(@NotNull EichenhoferParser.EndEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link EichenhoferParser#entry}.
	 * @param ctx the parse tree
	 */
	void enterEntry(@NotNull EichenhoferParser.EntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link EichenhoferParser#entry}.
	 * @param ctx the parse tree
	 */
	void exitEntry(@NotNull EichenhoferParser.EntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link EichenhoferParser#nge}.
	 * @param ctx the parse tree
	 */
	void enterNge(@NotNull EichenhoferParser.NgeContext ctx);
	/**
	 * Exit a parse tree produced by {@link EichenhoferParser#nge}.
	 * @param ctx the parse tree
	 */
	void exitNge(@NotNull EichenhoferParser.NgeContext ctx);
}