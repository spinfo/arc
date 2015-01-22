package de.spinfo.arc.persistance.repository;

import de.spinfo.arc.annotationmodel.annotatable.impl.WorkingUnitImpl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface WorkingUnitImplDaoRepository extends MongoRepository <WorkingUnitImpl, String>{
	@Query ("{ 'title' : ?0 }")
	public WorkingUnitImpl queryTitle(String title);
	
	
//	public PageRange queryAllPages
}
