// Generated from /Users/franciscomondaca/spinfo/repositories/arc/arc.ext.signorell.gramm/SignorellParser.g4 by ANTLR 4.5
package de.uni_koeln.spinfo.arc.ext.signorell.gramm;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SignorellParser}.
 */
public interface SignorellParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SignorellParser#startSymb}.
	 * @param ctx the parse tree
	 */
	void enterStartSymb(@NotNull SignorellParser.StartSymbContext ctx);
	/**
	 * Exit a parse tree produced by {@link SignorellParser#startSymb}.
	 * @param ctx the parse tree
	 */
	void exitStartSymb(@NotNull SignorellParser.StartSymbContext ctx);
	/**
	 * Enter a parse tree produced by {@link SignorellParser#entry}.
	 * @param ctx the parse tree
	 */
	void enterEntry(@NotNull SignorellParser.EntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link SignorellParser#entry}.
	 * @param ctx the parse tree
	 */
	void exitEntry(@NotNull SignorellParser.EntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link SignorellParser#error}.
	 * @param ctx the parse tree
	 */
	void enterError(@NotNull SignorellParser.ErrorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SignorellParser#error}.
	 * @param ctx the parse tree
	 */
	void exitError(@NotNull SignorellParser.ErrorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SignorellParser#lemma}.
	 * @param ctx the parse tree
	 */
	void enterLemma(@NotNull SignorellParser.LemmaContext ctx);
	/**
	 * Exit a parse tree produced by {@link SignorellParser#lemma}.
	 * @param ctx the parse tree
	 */
	void exitLemma(@NotNull SignorellParser.LemmaContext ctx);
	/**
	 * Enter a parse tree produced by {@link SignorellParser#endEntry}.
	 * @param ctx the parse tree
	 */
	void enterEndEntry(@NotNull SignorellParser.EndEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link SignorellParser#endEntry}.
	 * @param ctx the parse tree
	 */
	void exitEndEntry(@NotNull SignorellParser.EndEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link SignorellParser#nge}.
	 * @param ctx the parse tree
	 */
	void enterNge(@NotNull SignorellParser.NgeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SignorellParser#nge}.
	 * @param ctx the parse tree
	 */
	void exitNge(@NotNull SignorellParser.NgeContext ctx);
}