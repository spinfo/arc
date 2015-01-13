package de.spinfo.arc.persistance.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.uni_koeln.spinfo.arc.annotationmodel.annotation.impl.PageRangeImpl;

public interface PageRangeDaoRepository extends
		MongoRepository<PageRangeImpl, String> {

}
