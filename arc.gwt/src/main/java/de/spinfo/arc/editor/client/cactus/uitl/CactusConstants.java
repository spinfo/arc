package de.spinfo.arc.editor.client.cactus.uitl;

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
public class CactusConstants {
	// prevent initialization
	private CactusConstants() {
	};
	
	/**
	 * Utility Class for constants which are used for the Cactus
	 * 
	 * @author D. Rival
	 */
	public static class PATH {
		// prevent initialization
		private PATH() {};
		
			public static final int ROOT = 1;
			public static final int CATEGORY = 2;
			public static final int LEAF = 3;
			
			public static final String PATH_TO_LEAVES =  ROOT + "." + CATEGORY + "." + LEAF;
			public static final String PATH_TO_CATEGORIES =  ROOT + "." + CATEGORY;
			public static final String PATH_TO_ROOT =  ROOT+"";
	}
	

}
