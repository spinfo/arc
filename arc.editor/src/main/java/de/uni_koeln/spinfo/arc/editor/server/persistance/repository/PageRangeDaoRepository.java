package de.uni_koeln.spinfo.arc.editor.server.persistance.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.spinfo.arc.annotationmodel.annotation.impl.PageRangeImpl;


public interface PageRangeDaoRepository extends MongoRepository <PageRangeImpl, String> {
	
}
