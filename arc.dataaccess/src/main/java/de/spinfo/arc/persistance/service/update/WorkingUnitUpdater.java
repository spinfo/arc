package de.spinfo.arc.persistance.service.update;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import de.spinfo.arc.annotationmodel.annotatable.impl.WorkingUnitImpl;
import de.spinfo.arc.annotationmodel.annotation.Annotation;
import de.spinfo.arc.annotationmodel.annotation.ChapterRange;
import de.spinfo.arc.annotationmodel.annotation.LanguageRange;
import de.spinfo.arc.persistance.config.MongoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class WorkingUnitUpdater {
	
	private final ApplicationContext ctx;
	private final MongoTemplate mongoTemplate;
	private final MongoConverter converter;
	private final String workingUnitCollectionName;

	
	public WorkingUnitUpdater(ApplicationContext ctx, String workingUnitCollectionName) {
		super();
		this.ctx = ctx;
		this.mongoTemplate = (MongoTemplate) ctx.getBean("mongoTemplate");
		this.converter = mongoTemplate.getConverter();
		this.workingUnitCollectionName = workingUnitCollectionName;
	}
	
	
	public WorkingUnitUpdater() {
		super();
		this.ctx = new AnnotationConfigApplicationContext(MongoConfiguration.class);
		this.mongoTemplate = (MongoTemplate) ctx.getBean("mongoTemplate");
		this.converter = mongoTemplate.getConverter();
		this.workingUnitCollectionName = "workingUnits";
	}
	
	/*
	 * CHAPTER Specific
	 */
	private static final String CHAPTER_STACK_FIELD = "annotations.CHAPTER_RANGE";
	
	public WriteResult pushChapter(String workingUnitTitle, ChapterRange chapterRange) {
		nullCheck(chapterRange);
		DBObject chapterRangeToPush = setUpForClassSerialization(chapterRange);
		return  mongoTemplate.updateFirst(Query.query(
				Criteria.where("title").is(workingUnitTitle)), 
				new Update().
				push(CHAPTER_STACK_FIELD, chapterRangeToPush),
				workingUnitCollectionName);
	}
// below is the mongoDB cmd for pulling a chapter
// db.workingUnits.update( { }, { $pull: {"annotations.CHAPTER_RANGE" : {"title":"testTitle"} }  }, {multi: true}  )
	
	public synchronized WriteResult pullChapter(String workingUnitTitle, ChapterRange chapterRange) {
		nullCheck(chapterRange);
		DBObject chapterRangeToPull = setUpForClassSerialization(chapterRange);
//		Below is how its done more less without spring
//		DBCollection coll = mongoTemplate.getCollection(workingUnitCollectionName);
//		BasicDBObject match = new BasicDBObject("title", workingUnitTitle);
//		BasicDBObject update = new BasicDBObject("annotations.CHAPTER_RANGE",  chapterRangeToPull);
//		return coll.update(match, new BasicDBObject("$pull", update), false, false);
		return  mongoTemplate.updateFirst(Query.query(
				Criteria.where("title").is(workingUnitTitle))
					, new Update().
						pull(CHAPTER_STACK_FIELD, chapterRangeToPull)
						, WorkingUnitImpl.class);
	}
	
	
	/*
	 * LANGUAGE Specific
	 */
	
	private static final String LANGUAGE_STACK_FIELD = "annotations.LANGUAGE_RANGE";
	
	public synchronized WriteResult pushLanguage(String workingUnitTitle, LanguageRange languageRange) {
		nullCheck(languageRange);
		DBObject languageRangeToPush = setUpForClassSerialization(languageRange);
		return  mongoTemplate.updateFirst(Query.query(
				Criteria.where("title").is(workingUnitTitle)), 
				new Update()
					.push(LANGUAGE_STACK_FIELD, languageRangeToPush),
						workingUnitCollectionName);
	}
	
	public synchronized WriteResult pullLanguage(String workingUnitTitle, LanguageRange languageRange) {
		nullCheck(languageRange);
		DBObject chapterRangeToPull = setUpForClassSerialization(languageRange);
		return  mongoTemplate.updateFirst(Query.query(
				Criteria.where("title").is(workingUnitTitle))
					, new Update()
						.pull(LANGUAGE_STACK_FIELD, chapterRangeToPull)
							, WorkingUnitImpl.class);
	}
	
	
	
	/**
	 * In order to fill the "_class"-Field of the object to serialized
	 * this method needs to be called before serializing the object. This is 
	 * a workaround otherwise queries with springdata against mongoDb wouldn't work
	 * 
	 * @param annotation
	 * @return a typed ready-to-persist DBObject
	 */
	private DBObject setUpForClassSerialization(Annotation annotation) {
		DBObject dbObjToReturn = new BasicDBObject();
		mongoTemplate.getConverter().write(annotation, dbObjToReturn);
		return dbObjToReturn;
	}

	private void nullCheck(Annotation annotation) {
		if (annotation == null)
			throw new IllegalArgumentException("Annotation may not be null!");
	}
	
}
