package de.spinfo.arc.editor.shared.model;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class WorkingUnit {
	
	// boxed because it has to be used with a comperator, 
	// TODO: set it to final. The id must be created while parsing
	private Integer id;
	private String title ="unnamed";
	private final Date uploadDate;
	private Date lastModificationDate;
	
	private final List<Word> listOfWords;
	private List<Page> listOfPages;
	private List<Chapter> listOfChapters;
	private List<Language> listOfLanguages;
	
	
	public WorkingUnit(Date date) {
		super();
		this.listOfWords = new LinkedList<Word>();
		this.uploadDate = date;
		this.lastModificationDate = date;
	}
	
	public void appendWord(Word word) {
		listOfWords.add(word);
	}
	
	public void appendPage(Page page) {
		if (listOfPages == null) {
			listOfPages = new LinkedList<Page>();
		}
		listOfPages.add(page);
	}

	
	private final static String NL = "\n";
	public String pageToStringAt(int number) {
		List<Word> sublist = getWordListInRange(this.getListOfPages().get(number));
		StringBuilder sb = new StringBuilder();
		for (Word word : sublist) {
			sb.append(word.getText());
			sb.append(NL);
		}
		
		return sb.toString();
	}
	
	public String firstAndLastWordOfPageToStringAt(int number) {
			List<Word> sublist = getWordListInRange(this.getListOfPages().get(number));
			StringBuilder sb = new StringBuilder();
			Word first =sublist.get(0);
			Word last = sublist.get(sublist.size()-1);
				sb.append("First and last word of page: ");
				sb.append(number);
				sb.append(NL);
				sb.append(first.getText());
				sb.append(" | ");
				sb.append(last.getText());
				sb.append(NL);
			
			return sb.toString();
	}
	
	public List<Word> getWordListInRange(RangeUnit range) {
		List<Word> subListOfWords = new LinkedList<Word>();
		if (listOfWords != null) {
			int start = range.getRange().getStart();
			int end = range.getRange().getEnd();
			return listOfWords.subList(start, end);
		} else { 
			System.err.println("Can't print words for range: " + range.toString() + "because no words have been set up in the working unit");
		}
		
		return subListOfWords;
	}
	
	public int getTotalMods() {
		int sum = 0;
		for (Word w : listOfWords) {
			if (w.getListOfFormModifications()!=null)
				sum += w.getListOfFormModifications().size();
			if (w.getListOfPosModifications()!=null)
				sum += w.getListOfPosModifications().size();
		}
		return sum;
	}
	
	// each Word has a reference to its WU and calls it if udateted with itself
	/**
	 * the word calls in with itself if its modified
	 * 
	 * @param word
	 */
//	public void onModified(Word word) {
//		this.lastModificationDate = new Date();
//	}
	
	public List<Word> getListOfWords() {
		return listOfWords;
	}

//	public void setListOfWords(List<Word> listOfWords) {
//		this.listOfWords = listOfWords;
//	}

	public List<Page> getListOfPages() {
		return listOfPages;
	}

	public void setListOfPages(List<Page> listOfPages) {
		this.listOfPages = listOfPages;
	}

	public List<Chapter> getListOfChapters() {
		return listOfChapters;
	}

	public void setListOfChapters(List<Chapter> listOfChapters) {
		this.listOfChapters = listOfChapters;
	}

	public List<Language> getListOfLanguages() {
		return listOfLanguages;
	}

	public void setListOfLanguages(List<Language> listOfLanguages) {
		this.listOfLanguages = listOfLanguages;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getUploadDate() {
		return uploadDate;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("------------ Working Unit ----------");
		sb.append(NL);
		sb.append("Id: ");
		sb.append(getId());
		sb.append(" | Title: ");
		sb.append(getTitle());
		sb.append(" | Upload Date: ");
		sb.append(getUploadDate());
		sb.append(" | total mods: ");
		sb.append(getTotalMods());
		sb.append(NL);
		for (Page p : listOfPages) {
			sb.append(p.toString());
			sb.append(getWordListInRange(p));
			sb.append(NL);
			
		}
		sb.append("-------------- End Of : ");
		sb.append(getTitle());
		sb.append(NL);
		return sb.toString();
	}
}
