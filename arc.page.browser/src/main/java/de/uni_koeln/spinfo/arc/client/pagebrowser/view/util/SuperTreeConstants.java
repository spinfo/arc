package de.uni_koeln.spinfo.arc.client.pagebrowser.view.util;



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
public class SuperTreeConstants {
	// prevent initialization
	private SuperTreeConstants() {
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
//			public static final int CATEGORY = 2;
			public static final int DEVISION = 2;
			public static final int LEAF = 3;
			
			public static final String PATH_TO_LEAVES =  ROOT + "."  + DEVISION + "." + LEAF;
			public static final String PATH_TO_DEVISIONS =  ROOT + "." + DEVISION;
//			public static final String PATH_TO_CATEGORIES =  ROOT + "." + CATEGORY;
			public static final String PATH_TO_ROOT =  ROOT+"";
	}
	

}
