package de.spinfo.arc.persistance.service.query;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import de.spinfo.arc.persistance.config.MongoConfiguration;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.WorkingUnit;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.impl.WorkingUnitImpl;

public class WorkingUnitQueries {
	
	ApplicationContext ctx ;
	MongoTemplate mongoOperations;
	
	public WorkingUnitQueries() {
		ctx  = new AnnotationConfigApplicationContext(MongoConfiguration.class);
		mongoOperations = (MongoTemplate) ctx.getBean("mongoTemplate");
	}
	
	public List<WorkingUnit> getAllWorkingUnits() {
		List<WorkingUnit> retrieved  = mongoOperations.
				findAll(WorkingUnit.class);
		return retrieved;
	}
	public List<WorkingUnit> getWorkingUnitsWithLanguage(String language) {
		List<WorkingUnit> retrievedWus  = mongoOperations.
				find(Query.query(Criteria.where("annotations.LANGUAGE_RANGE.title").is(language)), WorkingUnit.class);
		
		
		return retrievedWus;
	}
	public WorkingUnit getWorkingUnit(String title) {
		WorkingUnit retrieved  = mongoOperations.
				findOne(Query.query(Criteria.where("title").is(title)), WorkingUnitImpl.class);
		return retrieved;
	}
}
