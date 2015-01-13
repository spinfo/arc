package de.uni_koeln.spinfo.arc.editor.client.mvp.events;


import com.google.gwt.event.shared.GwtEvent;

import de.uni_koeln.spinfo.arc.editor.client.mvp.Editor;

public class PageEditorModeChangeEvent extends GwtEvent<PageEditorModeChangeEventHandler> {

	public static final Type<PageEditorModeChangeEventHandler> TYPE = new Type <PageEditorModeChangeEventHandler>();
	
	@Override
	public Type<PageEditorModeChangeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PageEditorModeChangeEventHandler handler) {
		handler.onPageEditorModeChange(this);
	}
	
	protected void fire(HasPageEditorModeChangeEventHandlers source, Editor.PageStates state, int pageNum) {
		source.fireEvent(new PageEditorModeChangeEvent(state, pageNum));
	}
	
	private Editor.PageStates state;
	private int pageNum;
//	private Types type;
	
	public PageEditorModeChangeEvent(Editor.PageStates state, int pageNum) {
		this.state = state;
		this.pageNum = pageNum;
//		this.type = type;
	}

	public Editor.PageStates getState() {
		return state;
	}
	public int getPageNum() {
		return pageNum;
	}
}
