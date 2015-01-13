package de.uni_koeln.spinfo.arc.editor.client.mvp;



/**
 * Utility Class for constants which are used for the Editor. This encompasses
 * If used often consider to use static import feature for
 * convenience like "import static ... .ModelConstants.*;" in the client class.
 * 
 * @author D. Rival
 * 
 */
public class Editor {
	// prevent initialization
	private Editor() {};
	public static enum PageStates {
		EDIT_WORD, EDIT_COORDINATES, SET_RANGE_START_AND_END,
		EDIT_EXISTING_RANGE
	}
	public static enum RangeStates {
		SELECT_PAGES, WAITING_FOR_RANGE_START
	}

	

}
