package de.spinfo.arc.editor.shared.model3.util;

public class DebugHelper {
	private DebugHelper () {}
	public static void print (Class<?> clazz, String text, boolean isType) {
		StringBuilder sb = new StringBuilder();
		sb.append(clazz.getSimpleName());
		sb.append("| hashcode: ");
		sb.append(clazz.hashCode());
		sb.append("\n");
		sb.append(text);
		sb.append("\n");
		sb.append("------------------");
		sb.append("\n");
		if (isType)
			System.out.println(sb.toString());
		else
			System.err.println(sb.toString());
			
	}
}
