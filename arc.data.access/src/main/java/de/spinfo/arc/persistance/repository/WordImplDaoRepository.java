package de.spinfo.arc.persistance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.impl.WordImpl;

@Transactional
public interface WordImplDaoRepository extends
		MongoRepository<WordImpl, String> {

	@Query("{ 'index' : ?0 }")
	public WordImpl queryIndex(long idx);

	/**
	 * Query for a List of words in a given index-range
	 * 
	 * @param start
	 *            the start word index
	 * @param end
	 *            the end word Index (the end is inclusive)
	 * @return a List of words by the given range
	 */
	@Query("{ 'index' : {'$gte' : ?0, '$lte' : ?1 }}")
	public List<WordImpl> queryByIndexRange(long start, long end);

	/**
	 */
	@Query("{ 'annotations.FORM.form' : ?0 }")
	public List<WordImpl> queryByForm(String form);

}
