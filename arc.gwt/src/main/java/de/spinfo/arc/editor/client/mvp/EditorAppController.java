package de.spinfo.arc.editor.client.mvp;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;

import de.spinfo.arc.editor.client.mvp.views.MainAppView;


public class EditorAppController implements ValueChangeHandler<String>{

	EventBus eventBus;	
	
	private MainAppView appView;
	
	private void bind() {
		History.addValueChangeHandler(this);
	}
	
	private void doDisplayWorkingUnitBrowser() {
		
	}
	
	
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		GWT.log(token);
		
	}

}
