package de.spinfo.arc.editor.client.mvp;

//import com.google.gwt.core.shared.GWT;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

import de.spinfo.arc.editor.client.mvp.presenter.impl.MainAppViewPresenterImpl;
import de.spinfo.arc.editor.client.mvp.views.MainAppView;
import de.spinfo.arc.editor.client.mvp.views.mainapp.MainAppWidgetController;

import com.google.gwt.core.client.RunAsyncCallback;

public class AppController implements ValueChangeHandler<String> {
	EventBus eventBus;
	ClientFactory clientFactory = new ClientFactoryImpl();
	private HasWidgets container;
//	private MainAppView mainAppView;
	
	public AppController() {
		bind();
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
//				doDisplayFirstView();
				doDisplayMainAppView();
			} 
			
			else if (token.equals(Tokens.EDITOR)) {
				doDisplayMainAppView();
			} 
			

		}
	}
	

	private void doDisplayMainAppView() {

//		GWT.runAsync(new RunAsyncCallback() {
			
//			@Override
//			public void onSuccess() {
//				GWT.log("success run Async!");
				MainAppView mainAppView = clientFactory.getMainAppView();
				new MainAppWidgetController(mainAppView);
				new MainAppViewPresenterImpl(mainAppView).go(container);
//			    History.fireCurrentHistoryState();
//			}
//			
//			@Override
//			public void onFailure(Throwable reason) {
//				GWT.log("failure run Async!");
//			}
//		});
	}
	

	public void go(HasWidgets container) {
		this.container = container;
	}
	
}
