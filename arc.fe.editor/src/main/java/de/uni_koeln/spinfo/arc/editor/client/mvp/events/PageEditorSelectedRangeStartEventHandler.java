package de.uni_koeln.spinfo.arc.editor.client.mvp.events;

import com.google.gwt.event.shared.EventHandler;

public interface PageEditorSelectedRangeStartEventHandler extends EventHandler {
	public void onSelectedRangeStart(PageEditorSelectedRangeStartEvent event);
}