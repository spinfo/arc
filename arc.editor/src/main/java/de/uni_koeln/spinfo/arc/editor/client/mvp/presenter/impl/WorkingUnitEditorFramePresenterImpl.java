package de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.impl;
 
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import de.uni_koeln.spinfo.arc.client.pagebrowser.factory.impl.PageBrowserFactoryImpl;
import de.uni_koeln.spinfo.arc.client.pagebrowser.presenter.PageBrowserPresenter;
import de.uni_koeln.spinfo.arc.client.pagebrowser.presenter.impl.PageBrowserPresenterImpl;
import de.uni_koeln.spinfo.arc.client.pagebrowser.view.PageBrowserView;
import de.uni_koeln.spinfo.arc.client.pagebrowser.view.impl.PageBrowserViewImpl;
import de.uni_koeln.spinfo.arc.client.supervisor.Supervisor;
import de.uni_koeln.spinfo.arc.dto.annotatable.WorkingUnitDto;
import de.uni_koeln.spinfo.arc.dto.annotatable.impl.WordDtoImpl;
import de.uni_koeln.spinfo.arc.dto.annotation.AnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.AnnotationDto.AnnotationTypes;
import de.uni_koeln.spinfo.arc.dto.annotation.ChapterRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.FormAnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.LanguageRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.PageRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.PosAnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.PosAnnotationDto.PosTags;
import de.uni_koeln.spinfo.arc.dto.annotation.RectangleAnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.impl.ChapterRangeDtoImpl;
import de.uni_koeln.spinfo.arc.dto.annotation.impl.FormAnnotationDtoImpl;
import de.uni_koeln.spinfo.arc.dto.annotation.impl.LanguageRangeDtoImpl;
import de.uni_koeln.spinfo.arc.dto.annotation.impl.PosAnnotationDtoImpl;
import de.uni_koeln.spinfo.arc.dto.annotation.impl.RectangleAnnotationDtoImpl;
import de.uni_koeln.spinfo.arc.editor.client.mvp.ClientSession;
import de.uni_koeln.spinfo.arc.editor.client.mvp.Editor.PageStates;
import de.uni_koeln.spinfo.arc.editor.client.mvp.events.ui.BusyIndicator;
import de.uni_koeln.spinfo.arc.editor.client.mvp.events.ui.ErrorIndicator;
import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.EditorSupervisor;
import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.PageEditorPresenter;
import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.RangeEditorPresenter;
import de.uni_koeln.spinfo.arc.editor.client.mvp.presenter.WorkingUnitEditorFramePresenter;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.RangeWidget;
import de.uni_koeln.spinfo.arc.editor.client.mvp.views.WorkingUnitEditorFrame;
import de.uni_koeln.spinfo.arc.editor.shared.service.WorkingUnitService;
import de.uni_koeln.spinfo.arc.editor.shared.service.WorkingUnitServiceAsync;
import de.uni_koeln.spinfo.arc.editor.shared.service.workingunit.AnnotationModelService;
import de.uni_koeln.spinfo.arc.editor.shared.service.workingunit.AnnotationModelServiceAsync;
import de.uni_koeln.spinfo.arc.editor.shared.until.DebugHelper;


public class WorkingUnitEditorFramePresenterImpl implements WorkingUnitEditorFramePresenter, EditorSupervisor, Supervisor {

	private WorkingUnitEditorFrame editorFrame;
	private WorkingUnitDto workingUnit;
	private ClientSession clientSession;
	private RangeEditorPresenter rangeEditorPresenter;
	

	private PageEditorPresenter pageEditorPresenter = null;
	private AnnotationTypes annotationType = null;
	
//	private static final CactusFactory FAC = CactusFactoryImpl.INSTANCE;
	
	public WorkingUnitEditorFramePresenterImpl(
			WorkingUnitEditorFrame editorFrame, WorkingUnitDto workingUnit, ClientSession clientSession) {
		super();
		this.editorFrame = editorFrame;
		this.workingUnit = workingUnit;
		this.clientSession = clientSession;
		bind();
		pageEditorPresenter = new PageEditorPresenterImpl(workingUnit, clientSession, this);
		pageEditorPresenter.go(editorFrame.getCenterPanel());
		
		rangeEditorPresenter = new RangeEditorPresenterImpl(clientSession.getClientFactory().getRangeEditorView(), this, workingUnit);
		
		rangeEditorPresenter.addToChapterRanges(workingUnit.getChapters());
		rangeEditorPresenter.addToLanguageRanges(workingUnit.getLanguages());
		
		rangeEditorPresenter.go(editorFrame.getRangesPanel());
		
		setUpPageBrowser();
		BusyIndicator.free();
	}
	

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(editorFrame.asWidget());
	}

	@Override
	public void bind() {
		editorFrame.setPresenter(this);
//		rangeStartDialog.setPresenter(this);
	}
	
	private void setUpPageBrowser() {
//		HasWidgets centerPanel = editorFrame.getCenterPanel();
		HasWidgets chapterPanel = editorFrame.getChapterPanel();
//	    
	    int PAGE = 0;
	    currentPageNum = PAGE;
//	    pageRanges = new ArrayList<>(workingUnit.getWordPageMap().keySet());
//	    String imageUrl = pageRanges.get(PAGE).getDescription().getImageUrl();
	    String imageUrl = workingUnit.getPages().get(PAGE).getUrl();
	    setPageEditorInMode(PageStates.EDIT_WORD, PAGE,
	    		(int) workingUnit.getPages().get(PAGE).getStart(),
	    		(int) workingUnit.getPages().get(PAGE).getEnd(),
	    		imageUrl, AnnotationTypes.CHAPTER_RANGE,
	    		null);
	    
		
		PageBrowserView view = new PageBrowserViewImpl(PageBrowserFactoryImpl.INSTANCE);
		PageBrowserPresenter presenter = new PageBrowserPresenterImpl(view, this, workingUnit);
		chapterPanel.clear();
		chapterPanel.add(view.asWidget());
		
	}
	
	int secondPageNumForRangeSelection = 0;
	@Override
	public void onAddRangeAnnotation(AnnotationTypes type, int pageNumStart,
			int pageNumEnd) {
		DebugHelper.print(getClass(), "onAddRangeAnnotation start: " + pageNumStart + " end: " + pageNumEnd , true);
		
		PageRangeDto page = workingUnit.getPages().get(pageNumStart);
		secondPageNumForRangeSelection = pageNumEnd;
		setPageEditorInMode(PageStates.SET_RANGE_START_AND_END, pageNumStart,
				(int) page.getStart(), 
				(int) page.getEnd(),
				page.getUrl(),
				type,
				null);
	}


	@Override
	public void onEditRangeAnnotation(RangeWidget rangeWidget) {
		int pageNumStart = Integer.parseInt(rangeWidget.getFromPage());
		int pageNumEnd = Integer.parseInt(rangeWidget.getToPage());
		PageRangeDto page = workingUnit.getPages().get(pageNumStart);
		secondPageNumForRangeSelection = pageNumEnd;
		setPageEditorInMode(PageStates.EDIT_EXISTING_RANGE, pageNumStart,
				(int) page.getStart(), 
				(int) page.getEnd(),
				page.getUrl(),
				rangeWidget.getAnnotationType(),
				rangeWidget
				);
	}

 
	
	@Override
	public void onEditPage(int pageNum) {
		BusyIndicator.busy("Loading page: " , pageNum+"", "this may take a while.. ");
		DebugHelper.print(getClass(), "onEditPage num: " + pageNum , true);
		PageRangeDto page = workingUnit.getPages().get(pageNum);
		
		setPageEditorInMode(PageStates.EDIT_WORD, pageNum,
				(int) page.getStart(), 
				(int) page.getEnd(),
				page.getUrl(),
				AnnotationTypes.CHAPTER_RANGE,
				null);

	}
	
	/*
	 * Gets called from CactusView in order to inform the page editor
	 */
	int currentPageNum = 0;
	private final WorkingUnitServiceAsync rpcService = GWT.create(WorkingUnitService.class);
	
//	HashMap Map<ModificationRange, List<ModifiableWithParent>> wordsOfPage = new HashMap<>();
	private final AnnotationModelServiceAsync rpcServiceNew = GWT.create(AnnotationModelService.class);
	
	public void setPageEditorInMode(final PageStates state, final int pageNum,
			final int start, final int end,
			final String imageUrl ,final AnnotationTypes type, final RangeWidget rangewidget) {
		BusyIndicator.busy("setting up the page editor for page: " + pageNum+ " with amount pages: " +  (end - start),
				"start index is: " + start + " end: " + end , "loading image: " + imageUrl);
				rpcServiceNew.getWords(start, end, new AsyncCallback<List<WordDtoImpl>>() { 
					@Override 
					public void onFailure(Throwable caught) {
						ErrorIndicator.busy( "Error", "En Error occoured while setting up the page editor for page: " + pageNum, "please contact the admin");
					}  
					@Override
					public void onSuccess(List<WordDtoImpl> result) {
						pageEditorPresenter.initByState(state, pageNum, type, result, imageUrl, rangewidget);
						BusyIndicator.free();
					}
				});
	}
	
	@Override
	public void showRange(final int pageNum, final long selectedWordIdx, final boolean isStart) {
		final PageRangeDto page = workingUnit.getPages().get(pageNum);
		DebugHelper.print(getClass(), "showRange pageNum: " + pageNum + " selectedWordIdx: " 
						+ selectedWordIdx + " isStart: " + isStart + " page.getUrl() " + page.getUrl(), true);
		
		BusyIndicator.busy("showing Range of page number: " +
				pageNum , "selected word index is: "+ selectedWordIdx, "");
		
		rpcServiceNew.getWords((int)page.getStart(), (int)page.getEnd(), new AsyncCallback<List<WordDtoImpl>>() {
			@Override 
			public void onFailure(Throwable caught) {
				ErrorIndicator.busy( "Error", "showing Range of page number: " + pageNum, "please contact the admin");
			} 
			@Override
			public void onSuccess(List<WordDtoImpl> result) { 
				pageEditorPresenter.initForDisplayingRange(pageNum, selectedWordIdx,result, isStart, page.getUrl());
				BusyIndicator.free();
			}
		});
	}
	
	/*
	 * Gets called from page browser presenter in order to inform the PageEditor to show a new page
	 */
	@Override
	public void onNewPageDemanded(int pageNum) {
		List<PageRangeDto> pageRanges = workingUnit.getPages();
		String imageUrl = pageRanges.get(pageNum).getUrl();
		currentPageNum = pageNum;
		DebugHelper.print(getClass(), "onNewPageDemanded", true);
		setPageEditorInMode(PageStates.EDIT_WORD, pageNum, 
				(int) pageRanges.get(pageNum).getStart(),
				(int) pageRanges.get(pageNum).getEnd(), 
				imageUrl, AnnotationTypes.CHAPTER_RANGE, 
				null);
	}

	
	/*
	 * Now it is the range editor view
	 * 
	 */
	
	private static String USER = "USER";
	private static String WORKING_UNIT = "WORKING_UNIT";
	private static String NEW_RANGE = "RANGE";
	private static String REMOVE_RANGE = "REMOVE_RANGE";
	
	private static String WORD_INDEX = "WORD_INDEX";
	private static String NEW_POS = "POS";
	private static String NEW_FORM = "FORM";
	private static String NEW_RECT = "RECT";
	private static String END = ";";
	
	
	@Override
	public void setNewRange(int startIndex, int endIndex,  String title,
			final AnnotationTypes annotationType) {
		
		BusyIndicator.busy("saving new range: " + title, "start: " + startIndex + " end: " +
		endIndex, "Kind of annotation: " + annotationType.toString());
		
		DebugHelper.print(getClass(), 
				USER + " " +
				clientSession.getUserId() +
				" setNewCategory title: " + 
						title + 
						" startIndex: " + 
						startIndex + 
						" endIndex: " + 
						endIndex + 
						" type: " + annotationType.toString(), true);
		StringBuilder sb = new StringBuilder();
		sb.append(USER);
		sb.append(":");
		sb.append(clientSession.getUserId());
		sb.append(END);
		sb.append(" ");
		sb.append(NEW_RANGE);
		sb.append(":");
		sb.append(annotationType.toString());
		sb.append(END);
		sb.append(" ");
		sb.append("title:" + title);
		sb.append(END);
		sb.append(" ");
		sb.append("from:" + startIndex);
		sb.append(END);
		sb.append(" ");
		sb.append("to:" + endIndex );
		sb.append(END);
		writeToCommandLog(sb.toString());
//		
		if (annotationType.equals(AnnotationTypes.CHAPTER_RANGE)) {		 
				final ChapterRangeDto newChapterRange = new ChapterRangeDtoImpl(new Date(), 0,
						clientSession.getUserId(), startIndex,
						endIndex, title);
				rpcServiceNew.pushChapter(workingUnit.getTitle(), newChapterRange, new AsyncCallback<String>() {
					@Override
					public void onFailure(Throwable caught) {
						writeToCommandLog(caught.toString());
						ErrorIndicator.busy( "Error", "saving new range did not work!", "Kind of annotation: " + annotationType.toString());
					}
					@Override
					public void onSuccess(String result) {
						workingUnit.setAnnotationAsType(AnnotationTypes.CHAPTER_RANGE, newChapterRange);
						rangeEditorPresenter.addToChapterRanges(workingUnit.getChapters());
						writeToCommandLog(result);
						
						BusyIndicator.free();
					}
				});
		} 
		else 
			if (annotationType.equals(AnnotationTypes.LANGUAGE_RANGE)) {		
			final LanguageRangeDto newLanguageRange = new LanguageRangeDtoImpl(new Date(), 0,
					clientSession.getUserId(),
					startIndex,
					endIndex, title);
			rpcServiceNew.pushLanguage(workingUnit.getTitle(), newLanguageRange, new AsyncCallback<String>() {
				@Override
				public void onFailure(Throwable caught) {
					writeToCommandLog(caught.toString());
					
					ErrorIndicator.busy( "Error", "saving new range did not work!", "Kind of annotation: " + annotationType.toString());
				}
				@Override
				public void onSuccess(String result) {
					workingUnit.setAnnotationAsType(AnnotationTypes.LANGUAGE_RANGE, newLanguageRange);
					rangeEditorPresenter.addToLanguageRanges(workingUnit.getLanguages());
					writeToCommandLog(result);
					
					BusyIndicator.free();
				}
			});
		}	
	}
	
	@Override	
	public void onUpdateExistingRange(RangeWidget rangeWidget) {
		final AnnotationDto.AnnotationTypes annotationType = rangeWidget.getAnnotationType();
		if (annotationType.equals(AnnotationTypes.CHAPTER_RANGE)) {		 
			
			/* First delete the existing range and push a new one */
			onRemoveChapter((ChapterRangeDto) rangeWidget.getRangeAnnotation());
			
			rpcServiceNew.pushChapter(workingUnit.getTitle(), (ChapterRangeDto) rangeWidget.getRangeAnnotation(), new AsyncCallback<String>() {
				@Override
				public void onFailure(Throwable caught) {
					writeToCommandLog(caught.toString());
					ErrorIndicator.busy( "Error", "saving new range did not work!", "Kind of annotation: " + annotationType.toString());
				}
				@Override
				public void onSuccess(String result) {
//					workingUnit.setAnnotationAsType(AnnotationTypes.CHAPTER_RANGE, newChapterRange);
//					rangeEditorPresenter.addToChapterRanges(workingUnit.getChapters());
					writeToCommandLog(result);
					
					BusyIndicator.free();
				}
			});
	} 
	else 
		if (annotationType.equals(AnnotationTypes.LANGUAGE_RANGE)) {		

			/* First delete the existing range and push a new one */
			onRemoveLanguage((LanguageRangeDto) rangeWidget.getRangeAnnotation());
			
		rpcServiceNew.pushLanguage(workingUnit.getTitle(), (LanguageRangeDto) rangeWidget.getRangeAnnotation(), new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				writeToCommandLog(caught.toString());
				
				ErrorIndicator.busy( "Error", "saving new range did not work!", "Kind of annotation: " + annotationType.toString());
			}
			@Override
			public void onSuccess(String result) {
				rangeEditorPresenter.addToLanguageRanges(workingUnit.getLanguages());
				writeToCommandLog(result);
				
				BusyIndicator.free();
			}
		});
	}	
	}
	
//	// below is for the old range
//	// @Todo delete this
//	@Override
//	public void setNewRange(int selectedIndexInRespectOfTotalWordIndex,  String title,
//			final AnnotationTypes annotationType) {
//		setNewRange(selectedIndexInRespectOfTotalWordIndex, selectedIndexInRespectOfTotalWordIndex+1, title, annotationType);
//	}
	/*
	 * Gets called from range editor  in order to update DB
	 * 
	 */
	@Override
	public void onRemoveChapter(final ChapterRangeDto chapterRange) {
//		System.err.println("onRemoveRange: " + chapterRange ); 
		
		BusyIndicator.busy("Removing a chapter: " + chapterRange.getTitle(), " start: " + chapterRange.getStart() + " end: " +
				chapterRange.getEnd(), "");
		
		StringBuilder sb = new StringBuilder();
		sb.append(USER);
		sb.append(":");
		sb.append(clientSession.getUserId());
		sb.append(END);
		sb.append(" ");
		sb.append(REMOVE_RANGE);
		sb.append(":");
		sb.append(AnnotationTypes.CHAPTER_RANGE.toString());
		sb.append(END);
		sb.append(" ");
		sb.append("from:" + chapterRange.getStart());
		sb.append(END);
		sb.append(" ");
		sb.append("to:" + chapterRange.getEnd());
		sb.append(END);
		writeToCommandLog(sb.toString());
		
		rpcServiceNew.pullChapter(workingUnit.getTitle(), chapterRange,
				new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				writeToCommandLog(caught.toString());
				ErrorIndicator.busy( "Error! removing a chapter did not work!",  "title: " + chapterRange.getTitle() + " start: " + chapterRange.getStart() + " end: " +
						chapterRange.getEnd(), "");
			}
			@Override
			public void onSuccess(String result) {
				workingUnit.removeAnnotation(AnnotationTypes.CHAPTER_RANGE, chapterRange);
				writeToCommandLog(result);
				
				BusyIndicator.free();
			}
		});
	}
	@Override
	public void onRemoveLanguage(final LanguageRangeDto languageRange) {
//		System.err.println("onRemoveRange: " + languageRange ); 
		
		BusyIndicator.busy("Removing a language range: " + languageRange.getTitle(), "start: " + languageRange.getStart() + " end: " +
				languageRange.getEnd(), "");
		
		StringBuilder sb = new StringBuilder();
		sb.append(USER);
		sb.append(":");
		sb.append(clientSession.getUserId());
		sb.append(END);
		sb.append(" ");
		sb.append(REMOVE_RANGE);
		sb.append(":");
		sb.append(AnnotationTypes.LANGUAGE_RANGE.toString());
		sb.append(END);
		sb.append(" ");
		sb.append("from:" + languageRange.getStart());
		sb.append(END);
		sb.append(" ");
		sb.append("to:" + languageRange.getEnd());
		sb.append(END);
		writeToCommandLog(sb.toString());
		
		rpcServiceNew.pullLanguage(workingUnit.getTitle(), languageRange,
				new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				writeToCommandLog(caught.toString());
				ErrorIndicator.busy( "Error! removing a language range did not work!",  "title: " + languageRange.getTitle() + " start: " + languageRange.getStart() + " end: " +
						languageRange.getEnd(), "");
			}
			@Override
			public void onSuccess(String result) {
				workingUnit.removeAnnotation(AnnotationTypes.LANGUAGE_RANGE, languageRange);
				pageEditorPresenter.unselectAll();
				writeToCommandLog(result);
				
				BusyIndicator.free();
			}
		});
	}
	/*
	 * Gets called from page editor  in order to update DB
	 * 
	 * 
	 */
	@Override
	public void updateCoordinates(int x, int y, int width, int height, int absoluteWordIndex) {
//		System.err.println("updateCoordinates: \n" + x + " y:" + y + " w: " + width + " h: " + height + "\n " + absoluteWordIndex + "\n ");
		
		BusyIndicator.busy("updating coordinates of word with index: " + absoluteWordIndex, + x + " y:" + y + " w: " + width + " h: " 
		+ height , "");
		
		StringBuilder sb = new StringBuilder();
		sb.append(USER);
		sb.append(":");
		sb.append(clientSession.getUserId());
		sb.append(END);
		sb.append(" ");
		sb.append(WORD_INDEX);
		sb.append(":");
		sb.append(absoluteWordIndex);
		sb.append(END);
		sb.append(" ");
		sb.append(NEW_RECT);
		sb.append(" ");
		sb.append("x:");
		sb.append(x);
		sb.append(" ");
		sb.append("y:");
		sb.append(y);
		sb.append(" ");
		sb.append("width:");
		sb.append(width);
		sb.append(" ");
		sb.append("height:");
		sb.append(height);
		sb.append(END);
		writeToCommandLog(sb.toString());
		
		RectangleAnnotationDto rectAnno = new RectangleAnnotationDtoImpl(new Date(),
				0, 
				clientSession.getUserId(),
				x,y, width, height);
		rpcServiceNew.updateRectangle(absoluteWordIndex, rectAnno, new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				writeToCommandLog(caught.toString());
				ErrorIndicator.busy( "Error! updating coordinates of a word!", caught.getMessage(), "");
			}

			@Override
			public void onSuccess(String result) {
				writeToCommandLog(result);
				BusyIndicator.free();
			}
		});
	}
	
	@Override
	public void updateForm(String text, int absoluteSelectedWordIdx) {
//		System.err.println("updateForm: \n" + text + "\n " + absoluteSelectedWordIdx + "\n "); 
		
		BusyIndicator.busy("updating form of word with index: " + absoluteSelectedWordIdx, " new form: " + text , "");
		
		StringBuilder sb = new StringBuilder();
		sb.append(USER);
		sb.append(":");
		sb.append(clientSession.getUserId());
		sb.append(END);
		sb.append(" ");
		sb.append(WORD_INDEX);
		sb.append(":");
		sb.append(absoluteSelectedWordIdx);
		sb.append(END);
		sb.append(" ");
		sb.append(NEW_FORM);
		sb.append(":");
		sb.append(text);
		sb.append(END);
		writeToCommandLog(sb.toString());
		FormAnnotationDto formAnno = new FormAnnotationDtoImpl(new Date(),
				0, clientSession.getUserId(), text);
		rpcServiceNew.updateForm(absoluteSelectedWordIdx, formAnno, new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				writeToCommandLog(caught.toString());
				ErrorIndicator.busy( "Error! updating a form of a word", caught.getMessage(), "");
			}

			@Override
			public void onSuccess(String result) {
				writeToCommandLog(result);
				BusyIndicator.free();
			}
		});
	}

	@Override
	public void updatePos(String itemText, int absoluteSelectedWordIdx) {
//		System.err.println("updatePos: \n" + itemText + "\n " + absoluteSelectedWordIdx + "\n "); 
		BusyIndicator.busy("updating the part of speech of word with index: " + absoluteSelectedWordIdx, " new pos: " + itemText , "");
		
		StringBuilder sb = new StringBuilder();
		sb.append(USER);
		sb.append(":");
		sb.append(clientSession.getUserId());
		sb.append(END);
		sb.append(" ");
		sb.append(WORD_INDEX);
		sb.append(":");
		sb.append(absoluteSelectedWordIdx);
		sb.append(END);
		sb.append(" ");
		sb.append(NEW_POS);
		sb.append(":");
		sb.append(itemText);
		sb.append(END);
		writeToCommandLog(sb.toString());
		
		PosTags posToSave = PosTags.NOT_TAGGED;
		for (PosTags pos : PosTags.values()) {
			if (pos.toString().equals(itemText)) {
				posToSave = pos;
			}
		}
		 
		PosAnnotationDto posAnno = new PosAnnotationDtoImpl(new Date(), 0, clientSession.getUserId(), posToSave);
		rpcServiceNew.updatePos(absoluteSelectedWordIdx, posAnno, new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				writeToCommandLog(caught.toString());
				ErrorIndicator.busy( "Error! updating cordinates of a word", caught.getMessage(), "Click next to this popup in order to close this message");
			}

			@Override
			public void onSuccess(String result) {
				writeToCommandLog(result);
				
				BusyIndicator.free();
			}
		});
	}

	private void writeToCommandLog(String text) {
		Date d = new Date();
		StringBuilder sb = new StringBuilder();
		sb.append("Date:");
		sb.append(d.getTime());
		sb.append(END);
		sb.append(" ");
		sb.append(WORKING_UNIT);
		sb.append(":");
		sb.append(workingUnit.getTitle());
		sb.append(END);
		sb.append(" ");
		sb.append(text);
		String cmdTxt = sb.toString();
		rpcService.writeToCommandLog(cmdTxt, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Void result) {
			}
		});
	}

	
	int newRangeStart = 0;
	int newRangeEnd = 0;
	String rangeTitle = "no title";
	@Override
	public void onRangeStartSelected(int absoluteSelectedWordIdx) {
		DebugHelper.print(getClass(), "onRangeStartSelected "+ absoluteSelectedWordIdx, true);
		newRangeStart = absoluteSelectedWordIdx;
		final PageRangeDto pageRange = workingUnit.getPages().get(secondPageNumForRangeSelection);
		rpcServiceNew.getWords((int) pageRange.getStart(), (int) pageRange.getEnd(), new AsyncCallback<List<WordDtoImpl>>() {
			@Override 
			public void onFailure(Throwable caught) {
				ErrorIndicator.busy( "Error! Selecting start of a range!", caught.getMessage(), "");
			} 
			@Override
			public void onSuccess(List<WordDtoImpl> result) {
				pageEditorPresenter.initForRangeEndSelection(secondPageNumForRangeSelection,  result, pageRange.getUrl());
			}
		});
	}


	@Override
	public void onRangeEndSelected(int absoluteSelectedWordIdx) {
		DebugHelper.print(getClass(), "onRangeEndSelected " + absoluteSelectedWordIdx, true);
		newRangeEnd = absoluteSelectedWordIdx;
	}


	@Override
	public void onSaveRangeClicked(String title, AnnotationTypes annotationType) {
		DebugHelper.print(getClass(), "onSaveRangeClicked " + title, true);
		rangeTitle = title;
		setNewRange(newRangeStart, newRangeEnd, title, annotationType);
		onCancelRangeClicked();
	}


	@Override
	public void onCancelRangeClicked() {
		DebugHelper.print(getClass(), "onCancelRangeClicked", true);
		 newRangeStart = 0;
		 newRangeEnd = 0;
		 rangeTitle = "no title";
	}

}
