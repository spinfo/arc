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
import com.google.gwt.user.client.ui.HasAnimation;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widget.client.TextButton;

import de.spinfo.arc.editor.client.cactus.views.items.CactusCustomLeafItemWidget;

public class CactusCustomLeafItemWidgetImpl extends Composite implements CactusCustomLeafItemWidget {

	private static CactusCustomLeafItemWidgetImplUiBinder uiBinder = GWT
			.create(CactusCustomLeafItemWidgetImplUiBinder.class);

	interface CactusCustomLeafItemWidgetImplUiBinder extends
			UiBinder<Widget, CactusCustomLeafItemWidgetImpl> {
	}

//	@UiField
//	HasAnimation disclosurePanel;
	
	@UiField
	FlexTable table;
	
	private int ordinal = -1;
	private String title = "no title";
	
	public CactusCustomLeafItemWidgetImpl(int ordinal, String title) {
		initWidget(uiBinder.createAndBindUi(this));
		this.ordinal = ordinal;
		table.setText(0, 0, ordinal+"");
		this.title = title;
		table.setText(0, 1, title);
		
//		TextButton textBtn = new TextButton("view");
//		
//		table.setWidget(0, 2, textBtn);
//		disclosurePanel.setAnimationEnabled(true);
//		table.setText(0, 0, "1");
//	    table.setWidget(1, 1,new CactusCustomItemCategoryWidget());
	}
	
	public CactusCustomLeafItemWidgetImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	};
	
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
