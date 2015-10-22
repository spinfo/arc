package de.uni_koeln.spinfo.arc.editor.client.mvp;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import de.uni_koeln.spinfo.arc.dto.annotatable.WorkingUnitDto;
//import de.spinfo.arc.editor.client.mvp.views.WorkingUnitBrowserFrame;
import de.uni_koeln.spinfo.arc.editor.client.mvp.events.ui.BusyIndicator;
import de.uni_koeln.spinfo.arc.editor.client.mvp.login.LoginController;
import de.uni_koeln.spinfo.arc.editor.client.mvp.login.SessionState;
import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.impl.WorkingUnitEditorFramePresenterImpl;
import de.uni_koeln.spinfo.arc.editor.client.mvp.workingunitbrowser.presenter.impl.WorkingUnitBrowserWidgetPresenterImpl;
import de.uni_koeln.spinfo.arc.editor.client.mvp.workingunitbrowser.view.WorkingUnitBrowserFrame;
import de.uni_koeln.spinfo.arc.editor.shared.service.workingunit.AnnotationModelService;
import de.uni_koeln.spinfo.arc.editor.shared.service.workingunit.AnnotationModelServiceAsync;


public class ArcAppController implements ValueChangeHandler<String> {
	
	private HasWidgets container;
	private ClientSession clientSession;

	
	public ArcAppController (ClientSession clientSession) {
		this.clientSession = clientSession;

		bind();
	};
	
	public void go(HasWidgets container) {
		this.container = container;
	}
	
	private void bind() {
		History.addValueChangeHandler(this);
//		
//		// Listen for AppBusy events on the event bus
//		clientSession.getEventBus().addHandler(AppBusyEvent.getType(), 
//				new AppBusyHandler(){
//					public void onAppBusyEvent(AppBusyEvent event) {
//						BusyIndicator.busy();
//					}
//		});
//		
//		// Listen for AppFree events on the event bus
//		clientSession.getEventBus().addHandler(AppFreeEvent.getType(), 
//				new AppFreeHandler(){
//					public void onAppFreeEvent(AppFreeEvent event) {
//						BusyIndicator.free();
//					}
//		});
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
//		GWT.log("AppController received ValueChangeEvent from History " + token);

		if (token != null && SessionState.IS_LOGGED_IN) {
			if (token.equals(Tokens.HOME)) {
//				doDisplayWelcomeView();
			} 
			else 
				if (token.startsWith(Tokens.EDITOR)) {
				System.out.println(token);
				String[] bits = token.split("&");
				if (bits.length>1) {
					String textualId = bits[1];
					doDisplayWorkingUnitEditor(textualId);
				}
			} 
			
			else if (token.equals(Tokens.WORKING_UNIT_BROWSER)) {
				doDisplayWorkingUnitBrowser();
			} 
		} 
		else {
			History.newItem(Tokens.HOME, true);
		}
			
	}


	private final AnnotationModelServiceAsync rpcService = GWT.create(AnnotationModelService.class);
	


	private void doDisplayWorkingUnitEditor(final String textualId) {
//		System.err.println("doDisplayWorkingUnitEditor: " + textualId);
		
//		clientSession.getEventBus().fireEvent(new AppBusyEvent());
		BusyIndicator.busy("loading pages of working unit " , textualId, "this may take a while.. ");
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable reason) {}

			public void onSuccess() {
				rpcService.getWorkingUnit(textualId , new AsyncCallback<WorkingUnitDto>() {

					@Override
					public void onFailure(Throwable caught) {
					}
					@Override
					public void onSuccess(WorkingUnitDto workingUnit) {
						BusyIndicator.free();
						new WorkingUnitEditorFramePresenterImpl(clientSession.getClientFactory().getWorkingUnitEditorFrame(), workingUnit, clientSession).
						go(container);
					}
				});
			}
		});
	}


	private void doDisplayWelcomeView() {
//		GWT.runAsync(new RunAsyncCallback() {
//			public void onFailure(Throwable reason) {}
//
//			public void onSuccess() {
				container.clear();
				/*
				 * The loginController below is just for handling the 
				 * login screen in the welcome area. Afterwards the appcontroller is started after login 
				 * currentlz from the LoginView / this must be changed
				 */
//			    MainFrameView mainFrame = new MainFrameViewImpl();
//			    RootLayoutPanel.get().add(mainFrame);
			    LoginController loginController = new LoginController();
			    loginController.go(container);
			    History.fireCurrentHistoryState();
				container.add(clientSession.getClientFactory().getWelcomeView().asWidget());
//			}
//		});
		
	}


	private void doDisplayWorkingUnitBrowser() {
		
		GWT.runAsync(new RunAsyncCallback() {
			public void onFailure(Throwable reason) {}

			public void onSuccess() {
				WorkingUnitBrowserFrame browserFrame = clientSession.getClientFactory().getWorkingUnitBrowserFrame();
				browserFrame.setGreetingName(SessionState.USER_NAME);
				new WorkingUnitBrowserWidgetPresenterImpl(browserFrame, clientSession.getClientFactory()).
				go(container);
//				container.clear();
//				container.add(clientSession.getClientFactory().getWorkingUnitBrowserFrame().asWidget());
			}
		});
		
	}
	
	
	
}
