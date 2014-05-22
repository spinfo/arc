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
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import de.spinfo.arc.editor.client.cactus.presenter.impl.CactusViewPresenterImpl;
import de.spinfo.arc.editor.client.cactus.views.CactusView;
import de.spinfo.arc.editor.client.cactus.views.factory.impl.CactusFactoryImpl;
import de.spinfo.arc.editor.client.mvp.views.WorkingUnitEditorFrame;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;

public class WorkingUnitEditorFrameImpl extends Composite implements WorkingUnitEditorFrame  {

	private static WorkingUnitEditorFrameImplUiBinder uiBinder = GWT
			.create(WorkingUnitEditorFrameImplUiBinder.class);

	interface WorkingUnitEditorFrameImplUiBinder extends
			UiBinder<Widget, WorkingUnitEditorFrameImpl> {
	}

	public WorkingUnitEditorFrameImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		CactusView cactusView = CactusFactoryImpl.INSTANCE.createCactusView(Types.WORKING_UNIT_CHAPTER_RANGE);
		new CactusViewPresenterImpl(cactusView, Types.WORKING_UNIT_CHAPTER_RANGE, 0).go(westPanel, centerPanel);
		
//		centerPanel.add(new WorkingUnitBrowserInfoViewImpl());
	}

	@UiField
	HasWidgets westPanel;
	@UiField
	HasWidgets centerPanel;

}
