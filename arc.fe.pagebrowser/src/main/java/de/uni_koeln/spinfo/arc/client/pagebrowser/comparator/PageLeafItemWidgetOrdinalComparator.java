package de.uni_koeln.spinfo.arc.client.pagebrowser.comparator;

import java.util.Comparator;

import de.uni_koeln.spinfo.arc.client.pagebrowser.view.widget.PageLeafItemWidget;

public class PageLeafItemWidgetOrdinalComparator implements Comparator<PageLeafItemWidget>{
	
	private PageLeafItemWidgetOrdinalComparator() {}; 
	
	public static PageLeafItemWidgetOrdinalComparator INSTANCE = new PageLeafItemWidgetOrdinalComparator();
	
	public int compare(PageLeafItemWidget o1, PageLeafItemWidget o2) {
		if (o1 == o2) {
			return 0;
		}
 
		// Compare the id columns.
		if (o1 != null) {
			return (o2 != null) ? o1.getOrdinal() - o2.getOrdinal(): 1;
		}
		return -1;
	}
	
}
