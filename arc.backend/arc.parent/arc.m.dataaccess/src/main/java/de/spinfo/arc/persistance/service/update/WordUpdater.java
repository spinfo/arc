package de.spinfo.arc.persistance.service.update;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Update.Position;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import de.spinfo.arc.annotationmodel.annotation.Annotation;
import de.spinfo.arc.annotationmodel.annotation.FormAnnotation;
import de.spinfo.arc.annotationmodel.annotation.PosAnnotation;
import de.spinfo.arc.annotationmodel.annotation.PosAnnotation.PosTags;
import de.spinfo.arc.annotationmodel.annotation.RectangleAnnotation;
import de.spinfo.arc.persistance.config.MongoConfiguration;

public class WordUpdater {
	
	private final ApplicationContext ctx;
	private final MongoTemplate mongoTemplate;
	private final MongoConverter converter;
	private final String wordCollectionName;

	
	public WordUpdater(ApplicationContext ctx, String wordCollectionName) {
		super();
		this.ctx = ctx;
		this.mongoTemplate = (MongoTemplate) ctx.getBean("mongoTemplate");
		this.converter = mongoTemplate.getConverter();
		this.wordCollectionName = wordCollectionName;
	}
	
	public WordUpdater() {
		super();
		ctx = new AnnotationConfigApplicationContext(MongoConfiguration.class);
		this.mongoTemplate = (MongoTemplate) ctx.getBean("mongoTemplate");
		this.converter = mongoTemplate.getConverter();
		this.wordCollectionName = "words";
	}
	
	/*
	 * FORM Specific
	 */
	private static final String WORD_FORM_STACK_FIELD = "annotations.FORM";
	
	public synchronized WriteResult pushForm(long index, FormAnnotation formAnnotation) {
		nullCheck(formAnnotation);
		DBObject fromAnnoToPush = setUpForClassSerialization(formAnnotation);
		return  mongoTemplate.updateFirst(Query.query(
				Criteria.where("index").is(index)), 
				new Update().
				push(WORD_FORM_STACK_FIELD, fromAnnoToPush),
				wordCollectionName);
		}
	
	
	public synchronized WriteResult popLastForm(long index) {
		return  mongoTemplate.updateFirst(Query.query(
				Criteria.where("index").is(index)), 
				new Update().
				pop(WORD_FORM_STACK_FIELD, Position.LAST),
				wordCollectionName);
	}
	
//	public WriteResult popFirstForm(long index) {
//		return  mongoTemplate.updateFirst(Query.query(
//				Criteria.where("index").is(index)), 
//				new Update().
//				pop(WORD_FORM_STACK_FIELD, Position.FIRST),
//				wordCollectionName);
//	}
	
	/*
	 * Rect Specific
	 */
	private static final String WORD_RECT_STACK_FIELD = "annotations.RECTANGLE";
	
	public synchronized WriteResult pushRect(long index, RectangleAnnotation rectangleAnnotation) {
		nullCheck(rectangleAnnotation);
		DBObject rectAnnoToPush = setUpForClassSerialization(rectangleAnnotation);
		return  mongoTemplate.updateFirst(Query.query(
				Criteria.where("index").is(index)), 
				new Update().
				push(WORD_RECT_STACK_FIELD, rectAnnoToPush),
				wordCollectionName);	}
	
	
	public synchronized WriteResult popLastRect(long index) {
		return  mongoTemplate.updateFirst(Query.query(
				Criteria.where("index").is(index)), 
				new Update().
				pop(WORD_RECT_STACK_FIELD, Position.LAST),
				wordCollectionName);
	}
	
	/*
	 * POS Specific
	 */
	private static final String WORD_POS_STACK_FIELD = "annotations.POS";
	
	public synchronized WriteResult pushPos(long index, PosAnnotation posAnnotation) {
		nullCheck(posAnnotation);
		DBObject posAnnoToPush = setUpForClassSerialization(posAnnotation);
		return  mongoTemplate.updateFirst(Query.query(
				Criteria.where("index").is(index)), 
				new Update().
				push(WORD_POS_STACK_FIELD, posAnnoToPush),
				wordCollectionName);	}
	
	
	public synchronized WriteResult popLastPos(long index) {
		return  mongoTemplate.updateFirst(Query.query(
				Criteria.where("index").is(index)), 
				new Update().
				pop(WORD_POS_STACK_FIELD, Position.LAST),
				wordCollectionName);
	}
		
	private static final String WORD_POS_OPTIONS_FIELD = "taggerPosOptions";
	
	public WriteResult pushPosTaggerOptionsAsStrings(long index, Set<String> posOptions) {
		if (index < 0 || posOptions.isEmpty())
			throw new IllegalArgumentException("index must not be less than zero and/or pos option must not be an empty ssettring!");
		return  mongoTemplate.updateFirst(Query.query( 
				Criteria.where("index").is(index)), 
				new Update().
				addToSet(WORD_POS_OPTIONS_FIELD).each(posOptions),
				wordCollectionName);
	}
	
	public WriteResult pushPosTaggerOptions(long index, Set<PosTags> posOptions) {
		if (index < 0 || posOptions.isEmpty())
			throw new IllegalArgumentException("index must not be less than zero and/or pos option must not be an empty ssettring!");
		return  mongoTemplate.updateFirst(Query.query(
				Criteria.where("index").is(index)), 
				new Update().
				addToSet(WORD_POS_OPTIONS_FIELD).each(posOptions),
				wordCollectionName);
	} 
	
	public synchronized WriteResult pushPosTaggerOption(long index, String posOption) {
		if (index < 0 || posOption.isEmpty())
				throw new IllegalArgumentException("index must not be less than zero and/or pos option msut not be an empty string!");
		return  mongoTemplate.updateFirst(Query.query(
				Criteria.where("index").is(index)), 
				new Update().
				addToSet(WORD_POS_OPTIONS_FIELD, posOption),
				wordCollectionName);
	}
	
	/**
	 * 
	 * @param index the total Wordindex the pos tagger options should be removed from
	 * @return The MongoDB Write Result
	 */
	public synchronized WriteResult clearPosTaggerOption(long index) {
		if (index < 0)
				throw new IllegalArgumentException("index must not be less than zero and/or pos option msut not be an empty string!");
		Set<String> emptySet = new HashSet<>();
		
		return  mongoTemplate.updateFirst(Query.query(
				Criteria.where("index").is(index)), 
				new Update().
				set(WORD_POS_OPTIONS_FIELD, emptySet),
				wordCollectionName);
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
