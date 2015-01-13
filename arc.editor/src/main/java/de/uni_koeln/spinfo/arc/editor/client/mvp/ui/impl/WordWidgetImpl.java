package de.uni_koeln.spinfo.arc.editor.client.mvp.ui.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.uni_koeln.spinfo.arc.editor.client.mvp.ui.WordWidget;
import de.uni_koeln.spinfo.arc.editor.client.mvp.ui.WordWidgetPresenter;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.impl.WordsContainerImpl;

public class WordWidgetImpl extends Composite implements HasText, WordWidget {

	private static WordWidgetImplUiBinder uiBinder = GWT
			.create(WordWidgetImplUiBinder.class);

	interface WordWidgetImplUiBinder extends UiBinder<Widget, WordWidgetImpl> {
	}

	private int index;
	private WordsContainerImpl parent;
	
	public WordWidgetImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		
		((Widget) wordPanel).addDomHandler(new ContextMenuHandler() {
			public void onContextMenu(ContextMenuEvent event) {
				System.out.println("On contextEvent");
				presenter.onWordRightClicked(index, this, event);
				event.getNativeEvent().stopPropagation();
				event.getNativeEvent().preventDefault();
//				presenter.onWordRightClicked(this, event);
//				presenter.onWordRightClicked(word, event);
				}
			}
		, ContextMenuEvent.getType());
	}
	

	public WordWidgetImpl(int index, WordsContainerImpl parent) {
		initWidget(uiBinder.createAndBindUi(this));
		this.index = index;
		this.parent = parent;
	}
	
	@UiField HasWidgets wordPanel;
	@UiField HasText textHolder;
	private WordWidgetPresenter presenter;
	private boolean isSelected = false;
	
	
	@UiHandler("wordPanel")
	public void onWordClicked(ClickEvent e) {
		
		if (parent != null)
			parent.onWordClicked(this, e);
//		System.out.println("clicked idx: " + index );
		presenter.onWordClicked(index);
	}
	
	@Override
	public void setIndex(int index) {
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		return index;
	}
	
	@Override
	public void setWordText(String text ) {
		textHolder.setText(text);
	}
	@Override
	public String getWordText() {
		return textHolder.getText();
	}
	
	@Override
	public void setPresenter(WordWidgetPresenter wordWidgetPresenter) {
		this.presenter = wordWidgetPresenter;
	}
	
	@Override
	public void setText(String text) { 
		textHolder.setText(text);
	}

	@Override
	public String getText() {
		return textHolder.getText();
	}


	@Override
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
		if (isSelected)
			this.asWidget().addStyleName("page-editor-selected-word");
		else
			this.asWidget().removeStyleName("page-editor-selected-word");
	}
	@Override
	public boolean getIsSelected() {
		return isSelected ;
	}

}
