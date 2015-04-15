package de.spinfo.arc.data;

import de.spinfo.arc.annotationmodel.annotatable.WorkingUnit;
import de.spinfo.arc.annotationmodel.annotatable.impl.WordImpl;
import de.spinfo.arc.annotationmodel.annotation.LanguageRange;
import de.spinfo.arc.annotationmodel.annotation.PageRange;
import de.spinfo.arc.persistance.service.query.WordQueries;
import de.spinfo.arc.persistance.service.query.WorkingUnitQueries;
import de.uni_koeln.spinfo.arc.utils.FileUtils;

import java.io.*;
import java.util.*;

/**
 * Created by franciscomondaca on 24/3/15.
 */
public class IOMongo {

	static WorkingUnitQueries wuQueries = new WorkingUnitQueries();
	static WordQueries wordQueries = new WordQueries();
	private boolean insertedForm;

	public long getPageNumberInWU(String Wu, long index) {

		WorkingUnit workingUnit = wuQueries.getWorkingUnit(Wu);

		List<PageRange> pageRange = workingUnit.getPages();

		List<Range> ranges = new ArrayList<>();

		for (PageRange pr : pageRange) {

			Range range = new Range(pr.getStart(), pr.getEnd());
			ranges.add(range);
		}

		for (Range range : ranges) {

			if (index > range.getStart() && index < range.getEnd()) {

				System.out.println(ranges.indexOf(range));
			}

		}

		return 0;
	}

	public List<Entry> getSursilvanGoldenEntries() {

		WorkingUnit workingUnit = wuQueries.getWorkingUnit("Band II");

		List<LanguageRange> languageRange = workingUnit.getLanguages();
		Set<Long> germanWords = germanWordsInRange(languageRange);
		List<WordImpl> sursilvanTokens = sursilvanTokensInRange(languageRange);

		List<Entry> entries = new ArrayList<>();

		for (WordImpl word : sursilvanTokens) {

			if (germanWords.contains(word.getIndex())) {
				continue;
			}

			if (word.getIndex() > 366350) {
				break;
			}

			printNotTagged(word);

			Entry entry = new Entry();
			entry.setIndex(word.getIndex());
			entry.setForm(word.getLastFormAnnotation().getForm());
			if (word.getLastPosAnnotation() != null) {
				entry.setPos(word.getLastPosAnnotation().getPos().toString());
				entry.setAutor(word.getLastPosAnnotation().getUserId());

			}

			entries.add(entry);
		}

		return entries;
	}

	public List<ForStand> getTokens(String fileName) throws Exception {
		List<ForStand> list = new ArrayList<>();
		Map<Long, Integer> map = new HashMap<>();

		List<Entry> getListOfTokens = readEntries(fileName);
		int i = 0;

		for (Entry e : getListOfTokens) {

			List<Punct> p_list = new ArrayList<>();

			String form = e.getForm();
			StringBuffer buffer = new StringBuffer();

			for (int j = 0; j < form.length(); j++) {

				if (!Character.isLetterOrDigit(form.charAt(j))
						&& !String.valueOf(form.charAt(j)).equals(" ")
						&& !String.valueOf(form.charAt(j)).equals("'")) {

					Punct p = new Punct();
					p.setForm(String.valueOf(form.charAt(j)));
					p.setIndex(j);
					p_list.add(p);

				} else {

					buffer.append(form.charAt(j));

				}

			}

			if (p_list.size() == 0) {
				ForStand entry = new ForStand();
				entry.setIndex(i);
				entry.setForm(buffer.toString());
				entry.setPOS(e.getPos());
				list.add(entry);
				i++;
				map.put(e.getIndex(), i);

			} else {

				if (p_list.size() == 1 && buffer.length() == 0) {
					ForStand p = new ForStand();
					p.setIndex(i);
					p.setForm(form);
					p.setPOS(getPOS(p_list.get(0).getForm()));
					list.add(p);
					i++;
					continue;

				}

				int firstChar = form.indexOf(buffer.charAt(0));
				int lastChar = form
						.lastIndexOf(buffer.charAt(buffer.length() - 1));

				for (int j = 0; j < p_list.size(); j++) {

					if (p_list.get(j).getIndex() < firstChar) {
						ForStand p = new ForStand();
						p.setIndex(i);
						p.setForm(p_list.get(j).getForm());
						p.setPOS(getPOS(p_list.get(j).getForm()));
						list.add(p);
						i++;

					}

				}

				ForStand entry = new ForStand();
				entry.setIndex(i);
				entry.setForm(buffer.toString());
				entry.setPOS(e.getPos());
				list.add(entry);
				i++;
				map.put(e.getIndex(), i);

				for (int j = 0; j < p_list.size(); j++) {

					if (p_list.get(j).getIndex() > lastChar) {
						ForStand p = new ForStand();
						p.setIndex(i);
						p.setForm(p_list.get(j).getForm());
						p.setPOS(getPOS(p_list.get(j).getForm()));
						list.add(p);
						i++;

					}

				}

			}

		}
		FileUtils.writeMap(map, "index_mapping_");
		return list;

	}

	private String getPOS(String s) {
		String pos = null;

		switch (s) {

		case "!":
		case "?":
		case ".":
			pos = "P_EOS";
			break;

		case ",":
		case ";":
		case ":":
		case "“":
		case "„":

			pos = "P_OTH";
			break;

		case "…":
		case "-":
			pos = "NULL";
			break;

		}

		return pos;

	}

	public Set<Long> germanWordsInRange(List<LanguageRange> languageRange) {
		Set<Long> germanWords = new HashSet<>();

		for (LanguageRange lr : languageRange) {
			if (lr.getTitle().equals("Deutsch/Tudesg")) {
				List<WordImpl> wordsOfLang = wordQueries.getWordsByRange(lr);
				for (WordImpl german : wordsOfLang) {

					germanWords.add(german.getIndex());
				}

			}

		}

		return germanWords;
	}

	public List<WordImpl> sursilvanTokensInRange(
			List<LanguageRange> languageRange) {

		List<WordImpl> sursilvanTokens = new ArrayList<>();

		for (LanguageRange lr : languageRange) {

			if (lr.getTitle().equals("Sursilvan")) {

				List<WordImpl> wordsOfLang = wordQueries.getWordsByRange(lr);
				for (WordImpl sur : wordsOfLang) {

					sursilvanTokens.add(sur);
				}

			}
		}

		return sursilvanTokens;
	}

	public void getModel(Map<Integer, String> goldenMap) throws IOException {

		File file = new File(FileUtils.outputPath + "model"
				+ FileUtils.getISO8601StringForCurrentDate() + ".txt");
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file), "UTF8"));

		Map<Integer, String> treeMap = new TreeMap<>(goldenMap);

		for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {

			Integer index = entry.getKey();
			String form = entry.getValue();

			if (form.equals("!") || form.equals(".") || form.equals("?")
					|| form.equals(",")) {
				out.append(form);
				out.append("_");
				out.append(form);
				out.append("\t");
			}

		}

		out.close();
	}

	private void printNotTagged(WordImpl word) {

		StringBuffer buffer = new StringBuffer();

		if (word.getLastPosAnnotation() != null) {

			if (word.getLastPosAnnotation().getPos().toString().equals("NULL")
					|| word.getLastPosAnnotation().getPos().toString()
							.equals("NOT_TAGGED")) {

				// if
				// (word.getLastPosAnnotation().getUserId().toString().equals("lutzf")
				// ||word.getLastPosAnnotation().getUserId().toString().equals("rivald"))
				// {

				buffer.append(word.getIndex());
				buffer.append("\t");
				buffer.append(word.getLastFormAnnotation().getForm());
				buffer.append("\t");
				buffer.append(word.getLastPosAnnotation().getPos());
				buffer.append("\t");
				buffer.append(word.getLastPosAnnotation().getUserId());

				System.out.println(buffer.toString());
				// }
			}
		}

	}

	// Temporary solution in order top avoid mutual dependence in maven
	private static List<Entry> readEntries(String fileName) throws Exception {

		ObjectInputStream inputStream = new ObjectInputStream(
				new FileInputStream(FileUtils.outputPath + fileName));

		List<Entry> tokens = (List<Entry>) inputStream.readObject();

		inputStream.close();

		return tokens;

	}

}