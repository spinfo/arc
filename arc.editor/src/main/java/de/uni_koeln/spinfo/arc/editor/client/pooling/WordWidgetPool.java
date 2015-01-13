package de.uni_koeln.spinfo.arc.editor.client.pooling;

import de.uni_koeln.spinfo.arc.editor.client.mvp.ui.WordWidget;
import de.uni_koeln.spinfo.arc.editor.client.mvp.ui.impl.WordWidgetImpl;


public class WordWidgetPool  extends ArcPool<WordWidget> {

	@Override
	public WordWidget createNewInstance() {
		WordWidget ww = new WordWidgetImpl();
		return ww;
	}

}
