package de.uni_koeln.spinfo.arc.editor.client.mvp.login;

/**
 * A non instantiatable class with a bunch of Constants and one important static flag {@link SessionState.IS_LOGGED_IN}
 *  if the user is
 * logged in. 
 * 
 * @author David Rival
 *
 */
public class SessionState {
	
	
	private SessionState() {};
	
	/**Important high security flag which is crucial for using the editor after log in has been confirmed */
	public static boolean IS_LOGGED_IN = false;
	
	/**The username which is used and selected in the dropdwon menu of the login screen*/
	public static String USER_NAME = "guest";
	
	/**The Version number which is displayed top-right */
	public static final String VERSION = "0.4";
	
	/** If the button which enables to skip login is visible  */
	public static final boolean IS_QUICK_LOGIN_BUTTON_WITHOUT_AUTH_VISIBLE = false;
	
}
