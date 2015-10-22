package de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

import de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget.PageLeafItemWidget;

public class PageLeafItemWidgetImpl extends Composite implements
		PageLeafItemWidget {

	private static PageLeafItemWidgetImplUiBinder uiBinder = GWT
			.create(PageLeafItemWidgetImplUiBinder.class);

	interface PageLeafItemWidgetImplUiBinder extends
			UiBinder<Widget, PageLeafItemWidgetImpl> {
	}

	// @UiField
	// HasAnimation disclosurePanel;

	@UiField
	FlexTable table;

	private int ordinal = -1;
	private String title = "no title";

	public PageLeafItemWidgetImpl(int ordinal, String title) {
		initWidget(uiBinder.createAndBindUi(this));
		this.ordinal = ordinal;
		table.setText(0, 0, ordinal + "");
		this.title = title;
		table.setText(0, 1, title);

		// TextButton textBtn = new TextButton("view");
		//
		// table.setWidget(0, 2, textBtn);
		// disclosurePanel.setAnimationEnabled(true);
		// table.setText(0, 0, "1");
		// table.setWidget(1, 1,new CactusCustomItemCategoryWidget());
	}

	public PageLeafItemWidgetImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	};

	@Override
	public void setOrdinal(int ordinal) {
		table.setText(0, 0, ordinal + "");
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
