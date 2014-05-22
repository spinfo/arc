package de.spinfo.arc.editor.client.cactus.views.items.impl;


import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

import de.spinfo.arc.editor.client.cactus.views.items.CactusCustomNodeItemWidget;

public class CactusCustomNodeItemWidgetImpl extends Composite implements CactusCustomNodeItemWidget  {

	private static CactusCustomNodeItemWidgetImplUiBinder uiBinder = GWT
			.create(CactusCustomNodeItemWidgetImplUiBinder.class);

	interface CactusCustomNodeItemWidgetImplUiBinder extends
			UiBinder<Widget, CactusCustomNodeItemWidgetImpl> {
	}

	private int ordinal = -1;
	private String title = "no title";

	public CactusCustomNodeItemWidgetImpl() {
		initWidget(uiBinder.createAndBindUi(this)); 
	}
	
	@UiField FlexTable table;

	public CactusCustomNodeItemWidgetImpl(int ordinal, String title) {
		initWidget(uiBinder.createAndBindUi(this));
		this.ordinal = ordinal;
		table.setText(0, 0, ordinal+"");
		table.setText(0, 1, title);
		this.title = title;
	}

	@Override
	public void setOrdinal(int ordinal) {
		table.setText(0, 0, ordinal+"");
		this.ordinal = ordinal;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
		table.setText(0, 1, title);
	}

	@Override
	public int getOrdinal() {
		return ordinal;
	}

	@Override
	public String getTitle() {
		return title;
	}

}
