package de.spinfo.arc.editor.client.mvp.views.impl;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.spinfo.arc.editor.client.mvp.presenter.WorkingUnitBrowserInfoViewPresenter;
import de.spinfo.arc.editor.client.mvp.views.WorkingUnitBrowserInfoView;

public class WorkingUnitBrowserInfoViewImpl extends Composite implements WorkingUnitBrowserInfoView {

	private static WorkingUnitBrowserInfoViewImplUiBinder uiBinder = GWT
			.create(WorkingUnitBrowserInfoViewImplUiBinder.class);

	interface WorkingUnitBrowserInfoViewImplUiBinder extends
			UiBinder<Widget, WorkingUnitBrowserInfoViewImpl> {
	}

	public WorkingUnitBrowserInfoViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		/*
		 * below are methods for testing functionality 
		 */
//		addToHeaderPanel(new Button("gsdfsdfsd"));
//		setContentText("blaaaaaaaaaaaaaaaaa");
//		
//		HorizontalPanel hp = new HorizontalPanel();
//		hp.add(new Button("gsdfddddd"));
//		hp.add(new Button("gsdfddddd"));
//		hp.add(new Button("gsdfddddd"));
//		hp.add(new Button("gsdfddddd"));
//		hp.add(new Button("gsdfddddd"));
//		hp.add(new Button("gsdfddddd"));
//		hp.add(new Button("gsdfddddd"));
//		hp.add(new Button("gsdfddddd"));
//		hp.add(new Button("gsdfddddd"));
//		addToContentPanel(hp);
//		setContentText("dssdsds");
//		clearHeaderPanelWidgets();
//		clearContentPanelWidgets();
	}

	@UiField
	HasWidgets horizontalHeaderPanel;
	@UiField
	HasText headerLabel;
	@UiField
	HasWidgets verticalContentPanel;
	@UiField 
	HasText contentLabel;
	
	private WorkingUnitBrowserInfoViewPresenter presenter;

	@Override
	public void addToHeaderPanel(Widget widget) {
		this.horizontalHeaderPanel.add(widget);
	}
	@Override	
	public void addToContentPanel(Widget widget) {
		this.verticalContentPanel.add(widget);
	}
	@Override	
	public void setHeaderText(String headerText) {
		this.headerLabel.setText(headerText);
	}
	@Override
	public void setContentText(String contentText) {
		this.contentLabel.setText(contentText);
	}
	@Override
	public void clearHeaderPanelWidgets() {
		for (Iterator<Widget> iterator = horizontalHeaderPanel.iterator(); iterator.hasNext();) {
			Widget widget = (Widget) iterator.next();
			if (!(widget instanceof Label)) {
				widget.removeFromParent();
			}
		}
	}

	@Override
	public void clearContentPanelWidgets() {
		verticalContentPanel.clear();
//		int widgetCount = 0;
//		for (Iterator<Widget> iterator = verticalContentPanel.iterator(); iterator.hasNext();) {
//			Widget widget = (Widget) iterator.next();
//			widgetCount++;
//			if (!(widget instanceof Label)) {
//				widget.removeFromParent();
//			} else {
////				if (widgetCount > 1)
//					widget.removeFromParent();
//			}
//		}
	}

	@Override
	public HasWidgets getHeaderPanel() {
		return horizontalHeaderPanel;
	}
	@Override
	public HasWidgets getContentPanel() {
		return verticalContentPanel;
	}
	@Override
	public HasText getHeaderTextHolder() {
		return headerLabel;
	}
	@Override
	public HasText getContentTextHolder() {
		return contentLabel;
	}
	@Override
	public void setPresenter(WorkingUnitBrowserInfoViewPresenter presenter) {
		this.presenter = presenter;
		
	}
	@Override
	public void clearAllContent() {
		setContentText("");
		setHeaderText("");
		clearHeaderPanelWidgets();
		clearContentPanelWidgets();
		
	}	
	
}
