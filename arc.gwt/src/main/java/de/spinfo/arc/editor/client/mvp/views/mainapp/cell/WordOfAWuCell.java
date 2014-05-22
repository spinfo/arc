package de.spinfo.arc.editor.client.mvp.views.mainapp.cell;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.client.SafeHtmlTemplates.Template;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;

import de.spinfo.arc.editor.shared.model.CalendarFactory;
import de.spinfo.arc.editor.shared.model.WorkingUnit;

public class WordOfAWuCell extends AbstractCell<String>{
	
	interface Template extends SafeHtmlTemplates {
		@Template("{0}")
		SafeHtml setWord(String textOfWord);
//		SafeHtml workingUnit(String title);
	}

	private static Template template;
	
	public WordOfAWuCell() {
		super(BrowserEvents.CLICK);
		if (template == null) {
			template = GWT.create(Template.class);
		}
	}
	
	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context,
			String value, SafeHtmlBuilder sb) {
		if (value != null)
			sb.append(template.setWord(value));
//			sb.append(template.workingUnit(value.getTitle()));
		
	}
	
	@Override 
	public void onBrowserEvent(Context context, 
            Element parent, 
            String value,
            NativeEvent event, 
            ValueUpdater<String> valueUpdater){
		
	    super.onBrowserEvent(context, parent, value, event, valueUpdater);
	    
	    if (BrowserEvents.CLICK.equals(event.getType())) {
	    	if(valueUpdater!=null)valueUpdater.update(value);
	    	GWT.log("WORd IN A WU - You clicked the cell with Index: " + context.getIndex());
	    	GWT.log("WORd IN A WU - Value: " + value);
	    } 
	}

}
