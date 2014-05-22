package de.spinfo.arc.editor.shared.model3.util;

import de.spinfo.arc.editor.shared.model3.modification.payload.impl.UsableGwtRectangleImpl;


/**
 * Utility Class for constants which are used for the model. This encompasses
 * initialization values of mutable fields of the model like the score or the
 * comment of a modification. It prevents initialization with a private
 * constructor. If used often consider to use static import feature for
 * convenience like "import static ... .ModelConstants.*;" in the client class.
 * 
 * @author D. Rival
 * 
 */
public class ModelConstants {
	// prevent initialization
	private ModelConstants() {
	};
	
	/**
	 * Utility Class for constants which are used for the Modifications
	 * 
	 * @author D. Rival
	 */
	public static class MODIFICATION {
		// prevent initialization
		private MODIFICATION() {};
		public static final int INITIAL_SCORE = -1;
		public static final  String INITAL_COMMENT_TEXT = "no comment set for this modification";
		
		public static enum Types {
			RECTANGLE_MOD, TEXT_MOD, POS_MOD, WORKING_UNIT_PAGE_RANGE, WORKING_UNIT_CHAPTER_RANGE, WORKING_UNIT_LANGUAGE_RANGE, WORKING_UNIT_VOLUME_RANGE, WORKING_UNIT_LINE_RANGE
		}
	}
	/**
	 * Utility Class for constants which are used for Modifiables
	 * e.g. a word in a working unit
	 * 
	 * @author D. Rival
	 */
	public static class MODIFIABLE {
		// prevent initialization
		private MODIFIABLE() {};
		public static final  UsableGwtRectangleImpl INITAL_RECT = new UsableGwtRectangleImpl(-1,-1,-1,-1);
		public static final  String INITAL_COMMENT_TEXT = "no comment set for this word";
	}
	public static class WORKING_UNIT {
		// prevent initialization
		private WORKING_UNIT() {};
	}
	

}
