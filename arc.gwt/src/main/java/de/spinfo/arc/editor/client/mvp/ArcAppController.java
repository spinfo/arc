package de.spinfo.arc.editor.client.mvp;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import de.spinfo.arc.editor.client.mvp.presenter.impl.PageEditorPresenterImpl;
import de.spinfo.arc.editor.client.mvp.presenter.impl.WorkingUnitBrowserFramePresenterImpl;
import de.spinfo.arc.editor.client.mvp.ui.impl.WordWidgetImpl;
import de.spinfo.arc.editor.client.mvp.views.impl.WordsContainerImpl;
import de.spinfo.arc.editor.shared.model3.modifiable.ModifiableWithParent;
import de.spinfo.arc.editor.shared.model3.modification.ModificationString;
import de.spinfo.arc.editor.shared.model3.util.WorkingUnitHelper;
import de.spinfo.arc.editor.shared.model3.util.ModelConstants.MODIFICATION.Types;
import de.spinfo.arc.editor.shared.model3.workingunit.WorkingUnit;
import de.spinfo.arc.editor.shared.service.WorkingUnitService;
import de.spinfo.arc.editor.shared.service.WorkingUnitServiceAsync;

public class ArcAppController implements ValueChangeHandler<String> {
	
	ClientFactory clientFactory = new ClientFactoryImpl();
	HasWidgets container;
	
	public ArcAppController () {
		bind();
		
	};
	


	public void go(HasWidgets container) {
		this.container = container;
	}
	
	
	
	
	
	private void bind() {
		History.addValueChangeHandler(this);
		
	}








	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		GWT.log("AppController received ValueChangeEvent from History " + token);

		if (token != null) {
			if (token.equals(Tokens.HOME)) {

				doDisplayWelcomeView();
//				doDisplayPageEditor(0, 0);
//				doDisplayWorkingUnitBrowser();
//				doDisplayWorkingUnitEditor();
			} 
			
			else if (token.equals(Tokens.EDITOR)) {
				doDisplayWorkingUnitEditor();
			} 
			
			else if (token.equals(Tokens.WORKING_UNIT_BROWSER)) {
				doDisplayWorkingUnitBrowser();
			} 
			
			else if (token.startsWith(Tokens.EDIT_PAGE)) {
				String[] bits = token.split("&");
				String workingUnit = "0";
				String page = "0";
				if (bits.length>1) {
					workingUnit = bits[1];
					page = bits[2];
				}
				
				doDisplayPageEditor(Integer.parseInt(workingUnit), Integer.parseInt(page));
			}
			
		}
		
	}


	private final WorkingUnitServiceAsync rpcService = GWT.create(WorkingUnitService.class);
	
	private void doDisplayPageEditor(int workingUnitNum, final int pageNum) {
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable reason) {}

			public void onSuccess() {
				container.clear();
				rpcService.getWorkingUnit(0 , new AsyncCallback<WorkingUnit>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
					@Override
					public void onSuccess(WorkingUnit result) {
						new PageEditorPresenterImpl(pageNum, result, clientFactory).
						go(container);
						
//						List<ModifiableWithParent> words = WorkingUnitHelper.getWordsOfPage(pageNum, result);
//						final WordsContainerImpl wContainer = new WordsContainerImpl(pageNum, words);
//						container.add(wContainer);
						
//						int wordCount = 0;
//						for (Iterator<ModifiableWithParent> iterator = words.iterator(); iterator
//								.hasNext();) {
//							ModifiableWithParent word = (ModifiableWithParent) iterator
//									.next();
//							WordWidgetImpl wWidget = new WordWidgetImpl(wordCount, wContainer);
//							String wordText = ((ModificationString) word.getLastModificationOf(Types.TEXT_MOD)).getText();
//							wWidget.setText(wordText);
//							wContainer.addWord(wWidget);
//							wordCount++;
//						}
					}
				});
				
			}
		});
		
	} 



	private void doDisplayWelcomeView() {
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable reason) {}

			public void onSuccess() {
				container.clear();
				container.add(clientFactory.getInfoView().asWidget());
			}
		});
		
	}



	private void doDisplayWorkingUnitEditor() {
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable reason) {}

			public void onSuccess() {
				container.clear();
				container.add(clientFactory.getWorkingUnitEditor().asWidget());
			}
		});
	}



	private void doDisplayWorkingUnitBrowser() {
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable reason) {}

			public void onSuccess() {
				new WorkingUnitBrowserFramePresenterImpl(clientFactory.getWorkingUnitBrowser()).
				go(container);
//				container.clear();
//				container.add(clientFactory.getWorkingUnitBrowser().asWidget());
			}
		});
		
	}
	
	
	
}
