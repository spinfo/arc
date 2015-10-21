package de.uni_koeln.spinfo.arc.editor.server.persistance.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import de.spinfo.arc.annotationmodel.annotatable.impl.WorkingUnitImpl;

@Transactional
public interface WorkingUnitImplDaoRepository extends
		MongoRepository<WorkingUnitImpl, String> {

	@Query("{ 'title' : ?0 }")
	public WorkingUnitImpl queryTitle(String title);

	// public PageRange queryAllPages
}
