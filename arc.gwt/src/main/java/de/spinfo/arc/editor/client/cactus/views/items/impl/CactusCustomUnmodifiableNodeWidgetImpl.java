package de.spinfo.arc.editor.client.cactus.views.items.impl;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import de.spinfo.arc.editor.client.cactus.views.items.CactusCustomUnmodifiableNodeWidget;

public class CactusCustomUnmodifiableNodeWidgetImpl extends Composite implements CactusCustomUnmodifiableNodeWidget {

	private static CactusCustomUnmodifiableNodeWidgetImplUiBinder uiBinder = GWT
			.create(CactusCustomUnmodifiableNodeWidgetImplUiBinder.class);

	interface CactusCustomUnmodifiableNodeWidgetImplUiBinder extends
			UiBinder<Widget, CactusCustomUnmodifiableNodeWidgetImpl> {
	}

	public CactusCustomUnmodifiableNodeWidgetImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	private String title = "no title";
	
	@UiField
	FlexTable table;
	
	public CactusCustomUnmodifiableNodeWidgetImpl(String title) {
		initWidget(uiBinder.createAndBindUi(this));
		this.title = title;
		table.setText(0, 1, title);
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
		table.setText(0, 1, title);
	}
	@Override
	public String getTitle() {
		return title;
	}
}
