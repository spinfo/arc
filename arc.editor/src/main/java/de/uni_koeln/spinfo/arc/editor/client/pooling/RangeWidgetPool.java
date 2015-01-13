package de.uni_koeln.spinfo.arc.editor.client.pooling;

import de.uni_koeln.spinfo.arc.editor.client.mvp.views.RangeWidget;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.impl.RangeWidgetImpl;


public class RangeWidgetPool  extends ArcPool<RangeWidget> {

	@Override
	public RangeWidget createNewInstance() {
		RangeWidget rw = new RangeWidgetImpl();
		return rw;
	}

}
