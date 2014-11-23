// Generated from /Users/franciscomondaca/spinfo/repositories/antlr4/eichenhofer/eichenhofer.antlr4/Eichenhofer.g4 by ANTLR 4.4.1-dev
package de.uni_koeln.spinfo.antlr.eichenhofer;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link EichenhoferParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface EichenhoferVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link EichenhoferParser#lemma}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLemma(@NotNull EichenhoferParser.LemmaContext ctx);
	/**
	 * Visit a parse tree produced by {@link EichenhoferParser#startSymb}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStartSymb(@NotNull EichenhoferParser.StartSymbContext ctx);
	/**
	 * Visit a parse tree produced by {@link EichenhoferParser#error}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitError(@NotNull EichenhoferParser.ErrorContext ctx);
	/**
	 * Visit a parse tree produced by {@link EichenhoferParser#endEntry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEndEntry(@NotNull EichenhoferParser.EndEntryContext ctx);
	/**
	 * Visit a parse tree produced by {@link EichenhoferParser#entry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEntry(@NotNull EichenhoferParser.EntryContext ctx);
	/**
	 * Visit a parse tree produced by {@link EichenhoferParser#nge}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNge(@NotNull EichenhoferParser.NgeContext ctx);
}