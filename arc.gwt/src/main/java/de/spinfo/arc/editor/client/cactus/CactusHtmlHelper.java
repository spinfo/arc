package de.spinfo.arc.editor.client.cactus;

/**
 * Refactored due to visibility issues. No code Changes. the original is from 
 * package org.cafesip.gwtcomp.client.utils;
 * 
 * Details soo below:
 * 
 * A set of helper methods for generating HTML segments that are commonly used.
 * Whether you use GWT, JSF or any other GUI framework, there is no getting away
 * from HTML coding (I guess that is why GWT also provides a HTML widget). This
 * class contains method that help you generate commly-used HTML segments. For
 * example you can use:
 * 
 * <pre>
 *  String important = HTMLHelper.bold (&quot;Important!&quot;); 
 *  
 *  instead of
 *  
 *  String important = &quot;&lt;b&gt; + &quot;Important&quot; + &quot;&lt;/b&gt;&quot;;
 * </pre>
 * 
 * @author Amit Chatterjee
 * 
 */

public class CactusHtmlHelper {
    /**
     * HTML rendition of bold text.
     * 
     * @param text
     *            text to be rendered in bold.
     * @return the HTMLized string
     */
    public static String bold(String text)
    {
        return "<b>" + text + "</b>";
    }

    /**
     * HTML rendition of italicized text.
     * 
     * @param text
     *            text to be rendered in italicized format.
     * @return the HTMLized string
     */
    public static String italics(String text)
    {
        return "<i>" + text + "</i>";
    }

    /**
     * HTML rendition of newline.
     * 
     * @return the HTMLized string
     */
    public static String newline()
    {
        return "<b/>";
    }

    /**
     * HTML rendition of a horizontal rule.
     * 
     * @param color
     *            Color of the line. Use the HTML color-definition conventions.
     * @return the HTMLized string
     */
    public static String hr(String color)
    {
        StringBuffer buffer = new StringBuffer("<hr");

        if (color != null)
        {
            buffer.append(" style=\"color: " + color + ";\"");
        }

        buffer.append("/>");
        return buffer.toString();
    }

    /**
     * HTML rendition of boxed text. The style is controlled by the style
     * element: <blockquote class="css"> .gwtcomp-BoxedText </blockquote>
     * 
     * @param text
     *            text to be boxed.
     * @return the HTMLized string
     */
    public static String boxedText(String text)
    {
        return "<span class=\"gwtcomp-BoxedText\">" + text + "</span>";
    }

    /**
     * HTML rendition of a image next to a text. You can create image buttons
     * using this method.
     * 
     * @param url
     *            URL of the image
     * @param text
     *            text
     * @return the HTMLized string
     */
    public static String imageWithText(String url, String text)
    {
        return "<div style='white-space: nowrap;'><img border='0' align='top' src='"
                + url + "'/>&nbsp;" + text + "</div>";
    }

    private static String list(String[] items, String listStartTag,
            String listEndTag)
    {
        StringBuffer buffer = new StringBuffer(listStartTag);

        for (int i = 0; i < items.length; i++)
        {
            buffer.append("<li>");
            buffer.append(items[i]);
            buffer.append("</li>");
        }

        buffer.append(listEndTag);
        return buffer.toString();
    }

    /**
     * HTML rendition of a numbered (ordered) list.
     * 
     * @param items
     *            items in the list
     * @return the HTMLized string
     */
    public static String orderedList(String[] items)
    {
        return list(items, "<ol>", "</ol>");
    }

    /**
     * HTML rendition of a bullet (unordered) list.
     * 
     * @param items
     *            items in the list.
     * @return the HTMLized string
     */
    public static String bulletList(String[] items)
    {
        return list(items, "<ul>", "</ul>");
    }

    /**
     * HTML rendition of a header element.
     * 
     * @param i
     *            header level (1, 2, 3, 4, etc.)
     * @param title
     *            header text
     * @return the HTMLized string
     */
    public static String header(int i, String title)
    {
        return "<h" + i + ">" + title + "</h" + i + ">";
    }
}
