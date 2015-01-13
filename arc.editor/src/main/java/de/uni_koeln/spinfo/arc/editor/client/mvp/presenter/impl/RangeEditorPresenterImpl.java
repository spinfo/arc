package de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.impl;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.ui.HasWidgets;

import de.uni_koeln.spinfo.arc.dto.annotatable.WorkingUnitDto;
import de.uni_koeln.spinfo.arc.dto.annotation.AnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.ChapterRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.LanguageRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.PageRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.RangeAnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.AnnotationDto.AnnotationTypes;
import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.EditorSupervisor;
import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.RangeEditorPresenter;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.RangeEditorView;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.RangeWidget;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.impl.RangeWidgetImpl;
import de.uni_koeln.spinfo.arc.editor.shared.until.DebugHelper;

public class RangeEditorPresenterImpl implements RangeEditorPresenter {
	
//	ClientSession clientSession;
	private RangeEditorView rangeEditorView;
	private EditorSupervisor supervisor; 
	private WorkingUnitDto workingUnit;
	public RangeEditorPresenterImpl(RangeEditorView rangeEditorview,
			EditorSupervisor supervisor, WorkingUnitDto workingUnit) {
		super();
		this.rangeEditorView = rangeEditorview;
		this.supervisor = supervisor;
		this.workingUnit = workingUnit;
		bind();
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(rangeEditorView.asWidget());
	}

	@Override
	public void bind() {
		rangeEditorView.setPresenter(this);
	}

	private void setupChapterRanges(List<ChapterRangeDto> chapters) {
		rangeEditorView.getChapterPanel().clear();
		int ordinalIdx = 0;
		for (Iterator<ChapterRangeDto> iterator = chapters.iterator(); iterator.hasNext();) {
			ChapterRangeDto chapterRange = iterator.next();
			long currentPageNumStart = getContainingPageNum(chapterRange.getStart(),
					workingUnit.getPages());
			long currentPageNumEnd = getContainingPageNum(chapterRange. getEnd(),
					workingUnit.getPages());
			RangeWidget rangeWidget = createRangeWidget(
					ordinalIdx, 
					chapterRange.getTitle(),
					chapterRange.getStart(),
					chapterRange.getEnd(),
					currentPageNumStart,
					currentPageNumEnd,
					chapterRange,
					AnnotationDto.AnnotationTypes.CHAPTER_RANGE);
			rangeEditorView.getChapterPanel().add(rangeWidget.asWidget());
			ordinalIdx++;
		}
	}
	private void setupLanguageRanges(List<LanguageRangeDto> languages) {
		rangeEditorView.getLanguagePanel().clear();
		int ordinalIdx = 0;
		for (Iterator<LanguageRangeDto> iterator = languages.iterator(); iterator.hasNext();) {
			LanguageRangeDto languageRange = iterator.next();
			
			long currentPageNumStart = getContainingPageNum(languageRange.getStart(),
					workingUnit.getPages());
			long currentPageNumEnd = getContainingPageNum(languageRange. getEnd(),
					workingUnit.getPages());
			
			RangeWidget rangeWidget = createRangeWidget(
					ordinalIdx,
					languageRange.getTitle(),
					languageRange.getStart(),
					languageRange.getEnd(),
					currentPageNumStart,
					currentPageNumEnd,
					languageRange,
					AnnotationDto.AnnotationTypes.LANGUAGE_RANGE);
			rangeEditorView.getLanguagePanel().add(rangeWidget.asWidget());
			ordinalIdx++;
		}
	}
	
	private long getContainingPageNum(long rangeIndex, List<PageRangeDto> pages){
		long pageNumCounter = 0;
		for (Iterator<PageRangeDto> iterator = pages.iterator(); iterator.hasNext();) {
			PageRangeDto pageRange = iterator.next();
			
			if (pageRange.getStart() <= rangeIndex && pageRange.getEnd() >= rangeIndex)
				return pageNumCounter;
			
			pageNumCounter++;
		}
		return -1;
	}
	
	private RangeWidget createRangeWidget(int ordinalIdx,
				String title, long startIndex, long endIndex, 
				long pageNumStart, long pageNumEnd ,
				RangeAnnotationDto rangeAnnotation, 
				AnnotationDto.AnnotationTypes annotationType) {
		
		RangeWidget rangeWidget = new RangeWidgetImpl(rangeAnnotation, annotationType);
		rangeWidget.setOrdinal(ordinalIdx+"");
		rangeWidget.setRangeTitle(title);
		rangeWidget.setFromPage(pageNumStart+"");
		rangeWidget.setToPage(pageNumEnd+"");
		rangeWidget.setFromIndex(startIndex);
		rangeWidget.setToIndex(endIndex);
		rangeWidget.setPresenter(this);
		if (ordinalIdx % 2 != 0)
			rangeWidget.asWidget().addStyleName("page-editor-selected-word");
		return rangeWidget;
	}

	
	@Override
	public void onRemoveRangeClicked(RangeWidget rangeWidget) {
		if(rangeWidget.getRangeAnnotation() instanceof ChapterRangeDto)
			supervisor.onRemoveChapter((ChapterRangeDto) rangeWidget.getRangeAnnotation());
		else if (rangeWidget.getRangeAnnotation() instanceof LanguageRangeDto)
			supervisor.onRemoveLanguage((LanguageRangeDto) rangeWidget.getRangeAnnotation());
			
		rangeWidget.asWidget().removeFromParent(); 
	}
	  

	@Override
	public void onEditRangeClicked(RangeWidget rangeWidget) {
		AnnotationTypes type = rangeWidget.getAnnotationType();
		int pageNumStart = (int) rangeWidget.getRangeAnnotation().getStart();
		int pageNumEnd = (int) rangeWidget.getRangeAnnotation().getEnd();
		supervisor.onEditRangeAnnotation(rangeWidget);
	}


	@Override
	public void addToChapterRanges(List<ChapterRangeDto> chapterRanges) {
		DebugHelper.print(getClass(), "< ADD TO CHAPTER RANGE> \n" + chapterRanges.toString(), true); 
		setupChapterRanges(chapterRanges);
	}
	
	@Override
	public void addToLanguageRanges(List<LanguageRangeDto> languageRanges) {
		DebugHelper.print(getClass(), "< ADD TO LANGUAGE RANGE> \n" + languageRanges.toString(), true);
		setupLanguageRanges(languageRanges);
	}

	@Override
	public void onFromPageBtnClicked(RangeWidget rangeWidget) {
		supervisor.showRange(Integer.parseInt(rangeWidget.getFromPage()), 
				rangeWidget.getRangeAnnotation().getStart(), true);
	}

	@Override
	public void onToPageBtnClicked(RangeWidget rangeWidget) {
		supervisor.showRange(Integer.parseInt(rangeWidget.getToPage()), 
				rangeWidget.getRangeAnnotation().getEnd(), false);
	}
	
}
