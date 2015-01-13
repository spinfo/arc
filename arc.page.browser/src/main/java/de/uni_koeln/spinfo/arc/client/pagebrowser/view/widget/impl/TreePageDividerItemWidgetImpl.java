package de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

import de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget.TreePageDividerItemWidget;

public class TreePageDividerItemWidgetImpl extends Composite implements
		TreePageDividerItemWidget {

	private static TreePageDividerItemWidgetImplUiBinder uiBinder = GWT
			.create(TreePageDividerItemWidgetImplUiBinder.class);

	interface TreePageDividerItemWidgetImplUiBinder extends
			UiBinder<Widget, TreePageDividerItemWidgetImpl> {
	}

	public TreePageDividerItemWidgetImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	private int start;
	private int end;
	private final static String DELIMITER = " - ";
	@UiField
	FlexTable table;

	public TreePageDividerItemWidgetImpl(int start, int end) {
		initWidget(uiBinder.createAndBindUi(this));
		setStart(start);
		setEnd(end);
	}

	@Override
	public TreePageDividerItemWidgetImpl setStart(int start) {
		this.start = start;
		table.setText(0, 1, String.valueOf(start));
		table.setText(0, 2, DELIMITER);
		return this;
	}

	@Override
	public TreePageDividerItemWidgetImpl setEnd(int end) {
		this.end = end;
		table.setText(0, 3, String.valueOf(end));
		return this;
	}

	@Override
	public int getStart() {
		return start;
	}

	@Override
	public int getEnd() {
		return end;
	}

}
