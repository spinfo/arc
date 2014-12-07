package de.spinfo.arc.persistance.repository;

import de.spinfo.arc.annotationmodel.annotatable.Word;
import de.spinfo.arc.annotationmodel.annotatable.impl.WordImpl;
import de.spinfo.arc.annotationmodel.annotatable.impl.WorkingUnitImpl;
import de.spinfo.arc.annotationmodel.annotation.Annotation;
import de.spinfo.arc.annotationmodel.annotation.Annotation.AnnotationTypes;
import de.spinfo.arc.annotationmodel.annotation.FormAnnotation;
import de.spinfo.arc.annotationmodel.comparator.HasDetailsDateComparator;
import de.spinfo.arc.persistance.config.MongoConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes = {MongoConfiguration.class})
public class TestRepositoryWordQueryIndex {
	
	private static String DB_UNDER_TEST = "arc_working_unit";
	private static String WORKING_UNIT_COLLECTION_UNDER_TEST = "workingUnit";
	private static String WORD_COLLECTION_UNDER_TEST = "words";
	
 
	@Autowired
	WorkingUnitDaoRepository workingUnitsRepository;
	
	@Autowired
	WorkingUnitImplDaoRepository workingUnitsImplRepository;
	
	
	  @Autowired
	  WordImplDaoRepository wordRepository;

	  @Autowired
	  MongoOperations mongo;
	  
	  @Autowired
	  private MongoTemplate mongoTemplate;
	  
	  @Before
	  public void setup() throws Exception {
//		mongo.dropCollection(WORKING_UNIT_COLLECTION_UNDER_TEST);
//	    mongo.dropCollection(WORD_COLLECTION_UNDER_TEST);
	  }

	  @After
	  public void teardown() {
//	    mongo.dropCollection(COLLECTION_UNDER_TEST);
	  }

	  @Test
	  public void testIfQueryByIdxWorks() throws Exception {
//		
		Word w = wordRepository.queryIndex(1);
		List<Annotation> forms =  w.getAnnotationsOfType(AnnotationTypes.FORM);
		
//		System.out.println(w.getAllForms());
//		System.out.println(" ------------------------ ");
//		System.out.println(w.getLastForm());
//		
		
		assertEquals("are the correct amount of word annotations retrieved by repo? ", 1, forms.size());
		
		/*
		 * Query a Range in respect of word index
		 */
		List<WordImpl> words = wordRepository.queryByIndexRange(0, 9);
		assertEquals(" Is the correct range of words retrieved by the repo?", 10, words.size());
		
		/*
		 * Query by a form
		 */
//		List<WordImpl> formWords = wordRepository.queryByForm("esser");
//		for (int i = 0; i < formWords.size(); i++) {
//			System.out.println(formWords.get(i).getIndex());
//		}
		
		/*
		 * Query all words containing esser
		 */
		WordImpl esserWord = mongo.findOne(query(where("annotations.FORM.form").is("esser")), WordImpl.class);
		
		List<FormAnnotation> formAnnos = esserWord.getAllFormsAnnotations();
		System.out.println(formAnnos);
		System.out.println("-------------------");
		Collections.sort(formAnnos, HasDetailsDateComparator.INSTANCE);
		System.out.println(formAnnos);
//		System.out.println(esserWord);
//		assertEquals(" Is the correct range of words retrieved by the repo?", 10, words.size());
		
		List<WorkingUnitImpl> wuAlt = workingUnitsImplRepository.findAll();

		System.err.println("Title: " + wuAlt.get(0).getTitle());
		System.err.println("num pages: " + wuAlt.get(0).getPages().size());
		System.err.println("start: " + wuAlt.get(0).getStart());
		System.err.println("end: " + wuAlt.get(0).getEnd());
//		System.err.println("xxxxxxxxxxxxxxxx Pages: " + wu.getPages());
		
	  }

}
