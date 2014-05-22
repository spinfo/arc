package de.spinfo.arc.editor.client.mvp.views.mainapp;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.cellview.client.CellWidget;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;

import de.spinfo.arc.editor.client.mvp.Tokens;
import de.spinfo.arc.editor.client.mvp.views.MainAppView;
import de.spinfo.arc.editor.client.mvp.views.mainapp.cell.WordOfAWuCell;
import de.spinfo.arc.editor.client.mvp.views.mainapp.cell.WuCellWithTemplate;
import de.spinfo.arc.editor.client.mvp.views.mainapp.composite.WorkingUnitBrowserWidget;
import de.spinfo.arc.editor.client.mvp.views.mainapp.composite.impl.CenterWidgetImpl;
import de.spinfo.arc.editor.client.mvp.views.mainapp.composite.presenter.impl.WorkingUnitBrowserPresenterImpl;
import de.spinfo.arc.editor.shared.model.PseudoDataSource;
import de.spinfo.arc.editor.shared.model.Word;
import de.spinfo.arc.editor.shared.model.WorkingUnit;

public class MainAppWidgetController implements ValueChangeHandler<String> {

	MainAppView mainAppView;

	public MainAppWidgetController(MainAppView mainAppView) {
		this.mainAppView = mainAppView;

		bind();
		doDisplayWorkingUnitBrowser();
		// History.newItem(Tokens.EDITOR + "&" + Tokens.WORKING_UNIT_BROWSER);
	}

	private void bind() {
		History.addValueChangeHandler(this);
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		GWT.log("MainAppWidgetController received ValueChangeEvent from History "
				+ token);

		if (token != null) {
			if (token.startsWith(Tokens.EDITOR)) {
				String[] bits = token.split("&");

				if (bits.length > 1) {
					String currentLocation = bits[1];
//					String currentId = currentLocation.substring(currentLocation.length()-1);
					
//					GWT.log("currentId " + currentId);
//					if (currentLocation.startsWith("selected")){
						
						doDisplayWordsOfSelectedWorkingUnit(currentLocation);
//						char currentId = currentLocation.charAt(currentLocation.length() - 1);
//					}
					
					 }

			}
		}
	}


	private void doDisplayWordsOfSelectedWorkingUnit(String currentId) {
		mainAppView.getCenterPanel().clear();
//		mainAppView.setCenterContent(new WuCellWithTemplate());
		
//		WuCellWithTemplate test = new WuCellWithTemplate();
//		CellWidget<WorkingUnit> testWidget = new CellWidget<WorkingUnit>(test);
//		testWidget.setValue(PseudoDataSource.getWorkingUnits().get(Integer.parseInt(currentId)));
		
//		WordOfAWuCell test = new WordOfAWuCell();
//		CellWidget<String> testWidget = new CellWidget<String>(test);
		
		FlowPanel fp = new FlowPanel();
		
		int id = Integer.parseInt(currentId);
		WorkingUnit wu = PseudoDataSource.getWorkingUnits().get(id);
		
		for (Word w : wu.getListOfWords() ) {
//			WordOfAWuCell wordCell = new WordOfAWuCell();
			CellWidget<String> testWidget = new CellWidget<String>( new WordOfAWuCell());
			testWidget.setValue(w.getText());
			fp.add(testWidget);
		}
		
//		testWidget.setValue(PseudoDataSource.getWorkingUnits().get(id).get);
		
		
		
		mainAppView.setCenterContent(fp);
	}

	private void doDisplayWorkingUnitBrowser() {

		new WorkingUnitBrowserPresenterImpl(
				MainAppCompositeFactory.getWestWorkingUnitBrowserWidget())
				.go(mainAppView.getWestPanel());
		
			mainAppView.setCenterContent(new CenterWidgetImpl());
		// mainAppView.setWestContent(MainAppCompositeFactory.getWestWorkingUnitBrowserWidget());

	}

	// private void doDisplayWorkingUnitbrowser() {
	// new
	// WorkingUnitBrowserPresenterImpl(MainAppCompositeFactory.getWestWorkingUnitBrowserWidget());
	// }

}
