package de.uni_koeln.spinfo.arc.editor.client.mvp.workingunitbrowser.view.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import de.uni_koeln.spinfo.arc.editor.client.mvp.views.WorkingUnitBrowserInfoView;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.impl.WorkingUnitBrowserInfoViewImpl;
import de.uni_koeln.spinfo.arc.editor.client.mvp.workingunitbrowser.view.WorkingUnitBrowserFrame;

public class WorkingUnitBrowserFrameImpl extends Composite implements
		WorkingUnitBrowserFrame {

	private static WorkingUnitBrowserFrameViewImplUiBinder uiBinder = GWT
			.create(WorkingUnitBrowserFrameViewImplUiBinder.class);

	interface WorkingUnitBrowserFrameViewImplUiBinder extends
			UiBinder<Widget, WorkingUnitBrowserFrameImpl> {
	}

	WorkingUnitBrowserInfoView infoView;

	public WorkingUnitBrowserFrameImpl() {
		initWidget(uiBinder.createAndBindUi(this));

		infoView = new WorkingUnitBrowserInfoViewImpl();
		// infoView.setHeaderText("Hello, " + SessionState.USER_NAME);
		infoView.setContentText("The first step in annotating the documents is to select one of the available working units to work with. "
				+ "Please click on one of the documents!");
		// Button backButton = new Button("- back to welcome screen -");
		// backButton.addClickHandler(new ClickHandler() {
		// @Override
		// public void onClick(ClickEvent event) {
		// History.newItem(Tokens.HOME, true);
		// }
		// });

		// infoView.getContentPanel().add(backButton);
		centerPanel.add(infoView.asWidget());

	}

	@Override
	public void setGreetingName(String name) {
		infoView.setHeaderText("Hello, " + name);
	}

	@UiField
	SplitLayoutPanel splitPanel;

	@UiField
	SimpleLayoutPanel westPanel;

	@UiField
	SimpleLayoutPanel centerPanel;

	// private WorkingUnitBrowserFramePresenter presenter;

	@Override
	public SimpleLayoutPanel getWestPanel() {
		return westPanel;
	}

	@Override
	public SimpleLayoutPanel getCenterPanel() {
		return centerPanel;
	}

	// @Override
	// public void setPresenter(WorkingUnitBrowserFramePresenter presenter) {
	// thi.pressenter = presenter;
	// }

}
