package de.spinfo.arc.editor.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import de.spinfo.arc.editor.client.cactus.presenter.impl.CactusViewPresenterImpl;
import de.spinfo.arc.editor.client.cactus.views.CactusView;
import de.spinfo.arc.editor.client.cactus.views.factory.impl.CactusFactoryImpl;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;

public class CactusPanel extends Composite {

	private static CactusPanelUiBinder uiBinder = GWT
			.create(CactusPanelUiBinder.class);

	interface CactusPanelUiBinder extends UiBinder<Widget, CactusPanel> {
	}

	public CactusPanel() {
		initWidget(uiBinder.createAndBindUi(this));
//		new CactusChapterViewPresenterImpl(new CactusChapterViewImpl()).go(chapterPanel);
		CactusView cactusView = CactusFactoryImpl.INSTANCE.createCactusView(Types.WORKING_UNIT_CHAPTER_RANGE);
		new CactusViewPresenterImpl(cactusView, Types.WORKING_UNIT_CHAPTER_RANGE, 0).go(chapterPanel, contentPanel);
	}

//	@UiField
//	Button button;
	
	@UiField
	SimpleLayoutPanel chapterPanel;
	
	@UiField ScrollPanel contentPanel;

//	@UiHandler("button")
//	void onClick(ClickEvent e) {
//		Window.alert("Hello!");
//	}



}
