package de.spinfo.arc.model2model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.bson.BasicBSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.Word;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.WorkingUnit;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.impl.WordImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.impl.WorkingUnitImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.Annotation;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.Annotation.AnnotationTypes;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.ChapterRange;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.FormAnnotation;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.HasDetails;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.LanguageRange;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.PageRange;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.RectangleAnnotation;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.impl.ChapterRangeImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.impl.FormAnnotationImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.impl.LanguageRangeImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.impl.PageRangeImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.impl.RectangleAnnotationImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.comparator.HasDetailsDateComparator;
import de.uni_koeln.spinfo.arc.annotationmodel.comparator.WordIndexComparator;

public class RetriveWorkingUnitAnnotations {
	static final boolean DEBUG_PAGES = false;
	private static final boolean DEBUG_WORKING_UNIT = false;
	private static final boolean DEBUG_WORDS = true;
	String nameOfWorkingUnit = "";
	MongoDao dao = new MongoDao();

	private WorkingUnit setupWorkingUnitDetails(DBObject mongoWorkingUnit,
			long wordStartIndex, long wordEndIndex) {
		BasicDBObject obj = (BasicDBObject) mongoWorkingUnit;
		long milis = Long.valueOf(obj.getString("date"));
		String title = obj.getString("obj_title");
		WorkingUnit workingUnit = new WorkingUnitImpl(new Date(milis), 0,
				"admin", title, wordStartIndex, wordEndIndex);

		// workingUnit.setTitle(title);
		// workingUnit.setDate(new Date(milis));
		return workingUnit;
	}

	private void appendRangesToWorkingUnit(WorkingUnit workingUnit,
			DBCursor rangeCursor, AnnotationTypes type) {
		int rangeCounter = 0;
		int firstStart = -1;
		int lastStart = -1;
		int lastEnd = -1;
		boolean isFirstSet = false;
		String rangePayload = "";
		while (rangeCursor.hasNext()) {
			BasicDBObject obj = (BasicDBObject) rangeCursor.next();
			int start = (int) obj.getLong("obj_start");

			if (!isFirstSet) {
				firstStart = start;
				isFirstSet = true;
			}

			int end = (int) obj.getLong("obj_end");

			/*
			 * normalized means that for each workingUnit the word pointer is
			 * resetted to 0 at beginning
			 */
			// int normalizedStart = start - firstStart;
			// int normalizedEnd = end - firstStart;
			int normalizedStart = start;
			int normalizedEnd = end;

			if (type.equals(AnnotationTypes.PAGE_RANGE)) {
				String imageUrl = obj.getString("obj_id");
				PageRange pageRange = new PageRangeImpl(workingUnit.getDate(),
						0, "admin", normalizedStart, normalizedEnd, imageUrl);
				workingUnit.setAnnotationAsType(AnnotationTypes.PAGE_RANGE,
						pageRange);
				rangePayload = "imageURL: " + imageUrl;
				rangeCounter++;
			} else if (type.equals(AnnotationTypes.CHAPTER_RANGE)) {
				String title = obj.getString("obj_id");
				ChapterRange chapterRange = new ChapterRangeImpl(
						workingUnit.getDate(), 0, "admin", normalizedStart,
						normalizedEnd, title);
				workingUnit.setAnnotationAsType(AnnotationTypes.CHAPTER_RANGE,
						chapterRange);
				rangePayload = "title: " + title;
				rangeCounter++;
			}

			else if (type.equals(AnnotationTypes.LANGUAGE_RANGE)) {
				String title = obj.getString("obj_id");
				LanguageRange languageRange = new LanguageRangeImpl(
						workingUnit.getDate(), 0, "admin", normalizedStart,
						normalizedEnd, title);
				workingUnit.setAnnotationAsType(AnnotationTypes.LANGUAGE_RANGE,
						languageRange);
				rangePayload = "title: " + title;
				rangeCounter++;
			}

			lastStart = start;
			lastEnd = end;
		}
		if (DEBUG_PAGES) {
			System.err.println(getClass() + " \n " + type.toString()
					+ " | rangePayload: " + rangePayload);
			System.err.println(getClass() + " Found ranges: " + rangeCounter);
			System.err.println(getClass() + " firstStart: " + firstStart);
			System.err.println(getClass() + " lastStart: " + lastStart);
			System.err.println(getClass() + " lastStart - firstStart: "
					+ (lastStart - firstStart));
			System.err.println(getClass() + " lastEnd: " + lastEnd);
			System.err.println(getClass() + " lastEnd - firstStart: "
					+ (lastEnd - firstStart));
		}
	}

	WorkingUnit getWorkingUnit(String nameOfWorkingUnit) {
		String WU_ID_TO_QUERY = "_id";
		DBCollection wuColl = dao.connectAndGetCollection("wu");

		BasicDBObject wuQuery = new BasicDBObject(WU_ID_TO_QUERY,
				nameOfWorkingUnit);
		DBObject mongoWorkingUnit = wuColl.findOne(wuQuery);

		// get the ref id out of the wu collections
		String obj_id = (String) mongoWorkingUnit.get(WU_ID_TO_QUERY);
		String VOLUME_ID_REF_TO_QUREY = "obj_id";

		DBCollection volColl = dao.connectAndGetCollection("volume");
		BasicDBObject volumeQuery = new BasicDBObject(VOLUME_ID_REF_TO_QUREY,
				obj_id);
		DBObject volume = volColl.findOne(volumeQuery);

		String VOL_START_KEY_TO_QUERY = "obj_start";
		String VOL_END_KEY_TO_QUERY = "obj_end";
		long obj_start = (Long) volume.get(VOL_START_KEY_TO_QUERY);
		long obj_end = (Long) volume.get(VOL_END_KEY_TO_QUERY);

		if (!areArgsValid(obj_start, obj_end))
			throw new IllegalArgumentException();
		WorkingUnit workingUnit = setupWorkingUnitDetails(mongoWorkingUnit,
				obj_start, obj_end);

		if (DEBUG_WORKING_UNIT) {
			System.out.println(getClass() + " workingUnit: "
					+ workingUnit.getTitle());
			System.out.println(getClass() + " start: " + obj_start);
			System.out.println(getClass() + " end: " + obj_end);
			System.out.println(getClass() + " ende - start : "
					+ (obj_end - obj_start));
		}
		/*
		 * now, based on the start and end of a volume we find out if the page
		 * ranges are contained therefore we have to get a subset of the page
		 * collection
		 */
		DBCollection pageColl = dao.connectAndGetCollection("page");
		DBCursor pagesOfVolumeCursor = getCorsorInRange(pageColl, "obj_start",
				obj_start, obj_end);
		appendRangesToWorkingUnit(workingUnit, pagesOfVolumeCursor,
				AnnotationTypes.PAGE_RANGE);
		pagesOfVolumeCursor.close();

		DBCollection chapterColl = dao.connectAndGetCollection("chapter");
		DBCursor chaptersOfVolumeCursor = getCorsorInRange(chapterColl,
				"obj_start", obj_start, obj_end);
		appendRangesToWorkingUnit(workingUnit, chaptersOfVolumeCursor,
				AnnotationTypes.CHAPTER_RANGE);
		chaptersOfVolumeCursor.close();

		// DBCollection languageColl = dao.connectAndGetCollection("lnguage");
		// DBCursor languageCursor = getCorsorInRange(languageColl, "obj_start",
		// obj_start, obj_end);
		// appendRangesToWorkingUnit(workingUnit, languageCursor,
		// AnnotationTypes.LANGUAGE_RANGE);
		// chaptersOfVolumeCursor.close();

		workingUnit.setWords(getWords((int) obj_start, (int) obj_end,
				workingUnit.getDate()));

		// System.err.println("getWords((int) obj_start, (int) obj_end, workingUnit.getDate()) "
		// + getWords((int) obj_start, (int) obj_end, workingUnit.getDate()) );
		System.err.println("Words of the WorkingUnit: "
				+ workingUnit.getWords().size());

		dao.close();
		return workingUnit;
	}

	private List<FormAnnotation> getFirstFormAnotation(BasicDBList mods) {
		// System.out.println("< No Args for Conversion supplied > Taking just the first Form Modification (HEURISTIC 2)");
		List<FormAnnotation> formAnnotationsOfAWord = new ArrayList<>();
		BasicDBObject currentMod = (BasicDBObject) mods.get(0);
		Date formDate = null;
		int score = 0;
		String form = null;
		formDate = new Date(currentMod.getLong("date"));
		score = castAllToInt(currentMod.get("score"));
		form = currentMod.getString("form");
		String userId = currentMod.getString("author");
		formAnnotationsOfAWord.add(new FormAnnotationImpl(formDate, score,
				userId, form));
		return formAnnotationsOfAWord;
	}

	private List<FormAnnotation> getFormAnotationsByUserIds(
			String[] relevantUserIds, BasicDBList mods) {
		// System.out.println("< Usernames as Args supplied > Taking the order of user ids order to make the conversion of Form Modification (HEURISTIC 1)");
		List<FormAnnotation> formAnnotationsOfAWord = new ArrayList<>();

		for (Iterator<Object> iterator = mods.iterator(); iterator.hasNext();) {
			BasicDBObject currentMod = (BasicDBObject) iterator.next();

			Date formDate = null;
			int score = 0;
			String form = null;
			FormAnnotationImpl formAnno = null;
			boolean isAnnotationFound = false;
			String userId = currentMod.getString("author");
			for (int i = 0; i < relevantUserIds.length; i++) {
				if (relevantUserIds[i].equals(userId)) {
					formDate = new Date(currentMod.getLong("date"));
					score = castAllToInt(currentMod.get("score"));
					form = currentMod.getString("form");
					formAnno = new FormAnnotationImpl(formDate, score, userId,
							form);

					formAnnotationsOfAWord.add(formAnno);
					isAnnotationFound = true;
					break;
				}

			}
			if (isAnnotationFound)
				break;
		}
		Comparator<HasDetails> rev = Collections
				.reverseOrder(HasDetailsDateComparator.INSTANCE);
		Collections.sort(formAnnotationsOfAWord, rev);
		return formAnnotationsOfAWord;

	}

	private String[] userIdsForFormAnnotations = null;

	public void setUserIdsForFormAnnotations(String[] userIdsForFormAnnotations) {
		this.userIdsForFormAnnotations = userIdsForFormAnnotations;
	}

	private List<Word> getWords(int obj_start, int obj_end, Date wuDate) {
		DBCollection wordsColl = dao.connectAndGetCollection("word");
		DBCursor wordsOfWorkingUnitCursor = getCorsorInRange(wordsColl,
				"word_index", obj_start, obj_end);
		List<Word> wordsOfWu = new ArrayList<>();
		int wordCnt = 0;
		while (wordsOfWorkingUnitCursor.hasNext()) {
			BasicDBObject wordObj = (BasicDBObject) wordsOfWorkingUnitCursor
					.next();
			/*
			 * Get each element of the form mod array
			 */
			BasicDBList mods = (BasicDBList) wordObj.get("modifications");
			// List<FormAnnotation> formAnnotationsOfAWord = new ArrayList<>();

			/*
			 * Get the from Annotations. Due to false Dates from the old
			 * XML-Database it depends on the user who made the changes to
			 * decide which annotation to take. If there is no relevant user
			 * found the "OCR" user must be taken, which is the initial userId.
			 */
			// String[] relevantUserIds = {"ltz", "badilattim", "bertherc",
			// "auto", "OCR" };
			//
			// for (Iterator<Object> iterator = mods.iterator();
			// iterator.hasNext();) {
			// BasicDBObject currentMod = (BasicDBObject) iterator.next();
			//
			// Date formDate = null;
			// int score = 0;
			// String form = null;
			// FormAnnotationImpl formAnno = null;
			// boolean isAnnotationFound = false;
			// String userId = currentMod.getString("author");
			// for (int i = 0; i < relevantUserIds.length; i++) {
			// if (relevantUserIds[i].equals(userId)) {
			// formDate = new Date(currentMod.getLong("date"));
			// score = castAllToInt(currentMod.get("score"));
			// form = currentMod.getString("form");
			// formAnno = new FormAnnotationImpl(formDate, score, userId, form);
			//
			// formAnnotationsOfAWord.add(formAnno);
			// isAnnotationFound = true;
			// break;
			// }
			//
			// }
			// if (isAnnotationFound) break;
			// }
			// Comparator<HasDetails> rev =
			// Collections.reverseOrder(HasDetailsDateComparator.INSTANCE);
			// Collections.sort(formAnnotationsOfAWord, rev);
			List<FormAnnotation> formAnnotationsOfAWord;
			if (userIdsForFormAnnotations == null)
				formAnnotationsOfAWord = getFirstFormAnotation(mods);
			else
				formAnnotationsOfAWord = getFormAnotationsByUserIds(
						userIdsForFormAnnotations, mods);

			long wordIndex = wordObj.getLong("word_index");
			wordCnt++;

			BasicBSONObject rect = (BasicBSONObject) wordObj.get("rect");
			int width = castAllToInt(rect.get("width"));
			int height = castAllToInt(rect.get("heigth"));
			int x = castAllToInt(rect.get("x"));
			int y = castAllToInt(rect.get("y"));
			// UsableGwtRectangle wordRect = new UsableGwtRectangleImpl(x, y,
			// width, height);

			// System.out.println(lastMod);

			RectangleAnnotation initialRectAnno = new RectangleAnnotationImpl(
					wuDate, 0, "admin", x, y, width, height);

			Word word = new WordImpl(wordIndex, formAnnotationsOfAWord,
					initialRectAnno);
			wordsOfWu.add(word);
		}

		Collections.sort(wordsOfWu, WordIndexComparator.INSTANCE);
		if (DEBUG_WORDS) {
			System.out.println("NUM_WORDS " + wordsOfWu.size());
			// System.out.println(wordsOfWu.get(0));
			List<Annotation> firstAnnos = wordsOfWu.get(0)
					.getAnnotationsOfType(AnnotationTypes.FORM);
			FormAnnotation firstOfFirst = ((FormAnnotation) firstAnnos.get(0));
			FormAnnotation lastOfFirst = ((FormAnnotation) firstAnnos
					.get(firstAnnos.size() - 1));
			List<Annotation> lastAnnos = wordsOfWu.get(wordsOfWu.size() - 1)
					.getAnnotationsOfType(AnnotationTypes.FORM);
			FormAnnotation firstOflast = (FormAnnotation) lastAnnos.get(0);
			FormAnnotation lastOflast = (FormAnnotation) lastAnnos
					.get(lastAnnos.size() - 1);

			// System.err.println("firstOfFirst.getForm() " +
			// firstOfFirst.getForm());
			// System.err.println("firstOfFirst.getDate().getTime() " +
			// firstOfFirst.getUserId());
			// System.err.println("lastOfFirst.getForm() " +
			// lastOfFirst.getForm());
			// System.err.println("lastOfFirst.getDate().getTime() " +
			// lastOfFirst.getUserId());
			// System.err.println("firstOflast.getForm() " +
			// firstOflast.getForm());
			// System.err.println("firstOflast.getDate().getTime() " +
			// firstOflast.getUserId());
			// System.err.println("lastOflast.getForm() " +
			// lastOflast.getForm());
			// System.err.println("lastOflast.getDate().getTime() " +
			// lastOflast.getUserId());

			System.out
					.println("First Annotation of the first word of workingUnit: "
							+ firstAnnos.get(0));
			System.out
					.println("LAST Annotation of the first word of workingUnit: "
							+ firstAnnos.get(firstAnnos.size() - 1));
			System.out
					.println("First Annotation of the Last word of workingUnit: "
							+ lastAnnos.get(0));
			System.out
					.println("LAST Annotation of the Last word of workingUnit: "
							+ lastAnnos.get(lastAnnos.size() - 1));
			// System.out.println(firstAnnos);
			// System.out.println(wordsOfWu.get(wordsOfWu.size()-1));
		}

		return wordsOfWu;
	}

	public static DBCursor getCorsorInRange(DBCollection collection,
			String keyToLookFor, long start, long end) {
		if (!areArgsValid(start, end))
			throw new IllegalArgumentException();
		BasicDBObject query = new BasicDBObject();
		query.put(keyToLookFor,
				BasicDBObjectBuilder.start("$gte", start).add("$lte", end)
						.get());

		DBCursor cursor = collection.find(query);
		return cursor;
	}

	private static boolean areArgsValid(long start, long end) {
		return start < end;
	}

	private static int castAllToInt(Object toBeCasted) {
		if (toBeCasted instanceof Integer) {
			return (int) toBeCasted;
		}
		if (toBeCasted instanceof Long) {
			return new Long((long) toBeCasted).intValue();
		}
		if (toBeCasted instanceof Double) {
			return (int) ((double) toBeCasted);
		}
		if (toBeCasted instanceof Float) {
			return new Float((float) toBeCasted).intValue();
		}
		if (toBeCasted instanceof String) {
			return Integer.parseInt((String) toBeCasted);
		}
		return (int) toBeCasted;
	}
	// private DBCursor getCorsorInRange(DBCollection collection, long start,
	// long end) {
	// if (!areArgsValid(start, end)) throw new IllegalArgumentException();
	// BasicDBObject query = new BasicDBObject(
	// "obj_start", new BasicDBObject( "$gte", start )
	// )// defining a "lower than or equals" end
	// .append("obj_end", new BasicDBObject( "$lte", end ));
	//
	//
	//
	// DBCursor cursor = collection.find(query);
	// return cursor;
	// }
}
