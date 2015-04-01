package de.spinfo.arc.persistance.service.query;

import de.spinfo.arc.annotationmodel.annotatable.WorkingUnit;
import de.spinfo.arc.annotationmodel.annotatable.impl.WorkingUnitImpl;
import de.spinfo.arc.persistance.config.MongoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

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
	public List<de.spinfo.arc.annotationmodel.annotatable.WorkingUnit> getWorkingUnitsWithLanguage(String language) {
		List<WorkingUnit> retrievedWus  = mongoOperations.
				find(query(where("annotations.LANGUAGE_RANGE.title").is(language)), WorkingUnit.class);
		
		
		return retrievedWus;
	}
	public WorkingUnit getWorkingUnit(String title) {
		WorkingUnit retrieved  = mongoOperations.
				findOne(query(where("title").is(title)), WorkingUnitImpl.class);
		return retrieved;
	}
}
