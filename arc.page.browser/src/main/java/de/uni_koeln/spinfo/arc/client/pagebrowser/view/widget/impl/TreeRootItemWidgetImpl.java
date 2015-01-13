package de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

import de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget.TreeRootItemWidget;

public class TreeRootItemWidgetImpl extends Composite implements
		TreeRootItemWidget {

	private static TreeRootItemWidgetImplUiBinder uiBinder = GWT
			.create(TreeRootItemWidgetImplUiBinder.class);

	interface TreeRootItemWidgetImplUiBinder extends
			UiBinder<Widget, TreeRootItemWidgetImpl> {
	}

	public TreeRootItemWidgetImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	FlexTable table;

	private String title = "no title";

	public TreeRootItemWidgetImpl(String title) {
		initWidget(uiBinder.createAndBindUi(this));
		this.title = title;
		table.setText(0, 0, title);
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
