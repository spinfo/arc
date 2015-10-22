package de.uni_koeln.spinfo.arc.persistance.service;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.WorkingUnit;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.impl.WordImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.impl.WorkingUnitImpl;
import de.uni_koeln.spinfo.arc.persistance.config.MongoConfiguration;
import de.uni_koeln.spinfo.arc.persistance.repository.WordImplDaoRepository;
import de.uni_koeln.spinfo.arc.persistance.repository.WorkingUnitDaoRepository;
import de.uni_koeln.spinfo.arc.persistance.repository.WorkingUnitImplDaoRepository;

//@ContextConfiguration(classes = {MongoConfiguration.class})
public class WorkingUnitDetails {
	
//	@Autowired
//	WorkingUnitImplDaoRepository workingUnitsRepository;
	WorkingUnitDaoRepository workingUnitsRepository;
	WorkingUnitImplDaoRepository workingUniImplDaoRepository;
	WordImplDaoRepository wordRepository;
	MongoOperations mongoOperations;
	
	ApplicationContext ctx;	
	
	
	public WorkingUnitDetails() {
		ctx = new AnnotationConfigApplicationContext(MongoConfiguration.class);
		workingUniImplDaoRepository = ctx.getBean(WorkingUnitImplDaoRepository.class);	
		workingUnitsRepository = ctx.getBean(WorkingUnitDaoRepository.class);	
		wordRepository = ctx.getBean(WordImplDaoRepository.class);
		mongoOperations = (MongoOperations) ctx.getBean("mongoTemplate");
		
		System.err.println("mongoOperations  " + mongoOperations.count(query(where("index").is(1)), WordImpl.class));
	}
	
	public List<WorkingUnit> getWorkingUnits() {
		List<WorkingUnit> wus = new ArrayList<>();
		if (workingUnitsRepository.findAll() != null)
				wus = workingUnitsRepository.findAll();
		System.err.println("WORKING UNITS  " + wus.toString());
		return wus; 
	}
	 
	public WorkingUnitImpl getWorkingUnit(String title){
		return workingUniImplDaoRepository.queryTitle(title); 
	}
	
	public WorkingUnitImpl getWorkingUnitImpl(String title) {
		return workingUniImplDaoRepository.queryTitle(title); 
	}
	
	public List<WordImpl> getWords(int start, int end) {
		return wordRepository.queryByIndexRange(start, end);
	}

}
