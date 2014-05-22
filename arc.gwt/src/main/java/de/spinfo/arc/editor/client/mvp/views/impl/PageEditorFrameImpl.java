package de.spinfo.arc.editor.client.mvp.views.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class PageEditorFrameImpl extends Composite  {

	private static PageEditorFrameImplUiBinder uiBinder = GWT
			.create(PageEditorFrameImplUiBinder.class);

	interface PageEditorFrameImplUiBinder extends
			UiBinder<Widget, PageEditorFrameImpl> {
	}

	public PageEditorFrameImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}


}
