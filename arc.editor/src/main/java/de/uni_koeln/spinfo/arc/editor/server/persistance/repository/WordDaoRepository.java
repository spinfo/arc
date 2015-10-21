package de.uni_koeln.spinfo.arc.editor.server.persistance.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import de.spinfo.arc.annotationmodel.annotatable.Word;

@Transactional
public interface WordDaoRepository extends MongoRepository<Word, String> {
	// @Query ("{ 'index' : ?0 }")
	// public WordImpl queryIndex(long idx);
	//
	// /**
	// * Query for a List of words in a given index-range
	// *
	// * @param start the start word index
	// * @param end the end word Index (the end is inclusive)
	// * @return a List of words by the given range
	// */
	// @Query ("{ 'index' : {'$gte' : ?0, '$lte' : ?1 }}")
	// public List<WordImpl> queryByIndexRange(long start, long end);
	//
	// /**
	// */
	// @Query ("{ 'annotations.FORM.form' : ?0 }")
	// public List<WordImpl> queryByForm(String form);

}