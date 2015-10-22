package de.uni_koeln.spinfo.arc.editor.client.mvp.events;

import com.google.gwt.event.shared.GwtEvent;


public class PageEditorSelectedRangeStartEvent extends GwtEvent<PageEditorSelectedRangeStartEventHandler> {
	
	public static final Type<PageEditorSelectedRangeStartEventHandler> TYPE = new Type <PageEditorSelectedRangeStartEventHandler>();
	@Override
	public Type<PageEditorSelectedRangeStartEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PageEditorSelectedRangeStartEventHandler handler) {
		handler.onSelectedRangeStart(this);
	}
	
	protected void fire(HasPageEditorSelectedRangeStartEventHandler source, int pageNum, int wordIndex) {
		source.fireEvent(new PageEditorSelectedRangeStartEvent(pageNum, wordIndex));
	}
	
	private int pageNum;
	private int wordIndex;
	
	
	public PageEditorSelectedRangeStartEvent(int pageNum, int wordIndex) {
		this.pageNum = pageNum;
		this.wordIndex = wordIndex;
	}
	
	public int getPageNum() {
		return pageNum;
	}
	public int getWordIndex() {
		return wordIndex;
	}
}
