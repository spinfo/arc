package de.spinfo.arc.editor.client.cactus.views;

/**
 * Simple integer constants for the Levels of the special tree called cactus.
 * The tree API needs to have ints so this Interface is implemented for both the 
 * view and the presenter of the specific cactus impementation.
 * 
 * @author D. Rival
 *
 */
public interface CactusChapterViewConstants {
	static final int WORKING_UNIT_LEVEL = 1;
	static final int CHAPTER_LEVEL = 2;
	static final int PAGE_LEVEL = 3;
	
	static final String PATH_TO_PAGES =  WORKING_UNIT_LEVEL + "." + CHAPTER_LEVEL + "." + PAGE_LEVEL;
	static final String PATH_TO_CHAPTERS =  WORKING_UNIT_LEVEL + "." + CHAPTER_LEVEL;
	static final String PATH_TO_WORKING_UNIT =  WORKING_UNIT_LEVEL+"";
}
