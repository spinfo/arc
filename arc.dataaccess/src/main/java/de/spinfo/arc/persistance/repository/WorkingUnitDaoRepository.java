package de.spinfo.arc.persistance.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import de.spinfo.arc.annotationmodel.annotatable.WorkingUnit;

@Transactional
public interface WorkingUnitDaoRepository extends MongoRepository <WorkingUnit, String>{
	@Query ("{ 'title' : ?0 }")
	public WorkingUnit queryTitle(String title);
	
	
//	public PageRange queryAllPages
}
