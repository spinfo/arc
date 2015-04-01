package de.spinfo.arc.persistance.repository;

import de.spinfo.arc.annotationmodel.annotation.impl.PageRangeImpl;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PageRangeDaoRepository extends MongoRepository <PageRangeImpl, String> {
	
}
