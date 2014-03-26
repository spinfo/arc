package de.uni_koeln.spinfo.arc.editor.client;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import de.uni_koeln.spinfo.arc.editor.shared.WelcomeService;
import de.uni_koeln.spinfo.arc.editor.shared.WelcomeServiceAsync;

public class Editor implements EntryPoint {
	
	private WelcomeServiceAsync service = GWT.create(WelcomeService.class);

	@Override
	public void onModuleLoad() {
		removeChildren();
		addTestWidgets();
	}

	private void addTestWidgets() {
		final HTML html = new HTML();
		Button button = new Button("Test GWT Widget");
		button.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				service.welcome(new AsyncCallback<String>() {
					
					@Override
					public void onSuccess(String result) {
						html.setText(result);
					}
					
					@Override
					public void onFailure(Throwable caught) {
					}
				});
			}
		});
		
		RootPanel.get("editor").add(button);
		RootPanel.get("editor").add(html);
	}

	private void removeChildren() {
		Element element = DOM.getElementById("editor");
		if (element != null) {
			for (int i = 0; i < element.getChildCount(); i++) {
				element.removeChild(element.getChild(0));
			}
		}
	}

}
