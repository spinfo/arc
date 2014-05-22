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

public class WuCellWithTemplate extends AbstractCell<WorkingUnit>{
	
	interface Template extends SafeHtmlTemplates {
		@Template("<table>" +
//				"<tr height=40px>" +
				     "<tr>" +
//				         "<td width=80px rowspan=2 bgcolor=\"{1}\"/>&nbsp</td>" +
						"<td>id: {0}</td>" +
						"<td>title: {1}</td>" +
						"<td>upload date: {2}</td>" +
				         "<td>total mods: {3}</td>" +
//				      "</tr>" +
//				      "<tr>" +
				      "</tr>" +
				   "</table>")
		SafeHtml workingUnit(int id, String title, String date, int totalMods);
//		SafeHtml workingUnit(String title);
	}

	private static Template template;
	
	public WuCellWithTemplate() {
		super(BrowserEvents.CLICK);
		if (template == null) {
			template = GWT.create(Template.class);
		}
	}
	
	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context,
			WorkingUnit value, SafeHtmlBuilder sb) {
		if (value != null)
			sb.append(template.workingUnit(value.getId(), value.getTitle(), 
						CalendarFactory.getDisplayFormatter().format(value.getUploadDate()), value.getTotalMods()));
//			sb.append(template.workingUnit(value.getTitle()));
		
	}
	
	@Override 
	public void onBrowserEvent(Context context, 
            Element parent, 
            WorkingUnit value,
            NativeEvent event, 
            ValueUpdater<WorkingUnit> valueUpdater){
		
	    super.onBrowserEvent(context, parent, value, event, valueUpdater);
	    
	    if (BrowserEvents.CLICK.equals(event.getType())) {
	    	if(valueUpdater!=null)valueUpdater.update(value);
	    	GWT.log("WuCellWithTemplate - You clicked the cell with Index: " + context.getIndex());
	    } 
	}

}
