package de.spinfo.arc.persistance.service.query;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Collections;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import de.spinfo.arc.annotationmodel.annotatable.impl.WordImpl;
import de.spinfo.arc.annotationmodel.annotation.RangeAnnotation;
import de.spinfo.arc.annotationmodel.comparator.WordIndexComparator;
import de.spinfo.arc.persistance.config.MongoConfiguration;
import de.spinfo.arc.persistance.repository.WordImplDaoRepository;

public class WordQueries {
	private final ApplicationContext ctx;
	private final MongoTemplate mongoTemplate;
	private final WordImplDaoRepository wordRepo;
	
	/**
	 * Constructor with settable application context. most likely you want to use
	 * constructor without prams
	 * 
	 * @param ctx if an Spring app context is to be supplied (most likely xou won't
	 */
	public WordQueries(ApplicationContext ctx) {
		super();
		this.ctx = ctx;
		this.mongoTemplate = (MongoTemplate) ctx.getBean("mongoTemplate");
		this.wordRepo = (WordImplDaoRepository) ctx.getBean("wordImplDaoRepository");
	}
	
	public WordQueries() {
		ctx = new AnnotationConfigApplicationContext(MongoConfiguration.class);
		this.mongoTemplate = (MongoTemplate) ctx.getBean("mongoTemplate");
		this.wordRepo = (WordImplDaoRepository) ctx.getBean("wordImplDaoRepository");
	}
	
	/**
	 * 
	 * @param range
	 * @return
	 */
	public List<WordImpl> getWordsByRange(RangeAnnotation range) {
		List<WordImpl> toReturn = mongoTemplate.find(
				Query.query(
						Criteria.where("index")
							.gte(range.getStart())
								.lte(range.getEnd())
						), 
						WordImpl.class
				);
		Collections.sort(toReturn, WordIndexComparator.INSTANCE);
		return toReturn;
	}
	
	/**
	 * This method utilizes the repository
	 * @param range
	 * @return the words in the specific range
	 */
	public List<WordImpl> getWordsByRange2(RangeAnnotation range) {
		List<WordImpl> toReturn = wordRepo.queryByIndexRange
				(range.getStart(), range.getEnd());
		Collections.sort(toReturn, WordIndexComparator.INSTANCE);
		return toReturn;
	}
	
	public List<WordImpl> getWords(int start, int end) {
		List<WordImpl> retrieved = mongoTemplate.
				find(query(where("index").gte(start).lte(end)), WordImpl.class);
		return retrieved;
	}
	
	public WordImpl getWordAt(long index) {
		WordImpl retrieved = mongoTemplate.
				findOne((query(where("index").is(index))), WordImpl.class);
		return retrieved;
	}
	
	List<WordImpl> getWordsByLanguage(String language){
//		List<WordImpl> retrieved = mongoTemplate.
//				find(query(where("index").gte(start).lte(end)), WordImpl.class);
		return null;
	}

}
