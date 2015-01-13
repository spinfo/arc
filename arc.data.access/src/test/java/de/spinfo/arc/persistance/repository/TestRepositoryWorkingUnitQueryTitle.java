package de.spinfo.arc.persistance.repository;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.spinfo.arc.persistance.config.MongoConfiguration;
import de.spinfo.arc.persistance.fixture.WorkingUnitPersistenceFixture;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.Word;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.impl.WorkingUnitImpl;
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes = {MongoConfiguration.class})
public class TestRepositoryWorkingUnitQueryTitle {
	
	private static String DB_UNDER_TEST = "arc_working_unit";
	private static String WORKING_UNIT_COLLECTION_UNDER_TEST = "workingUnit";
	private static String WORD_COLLECTION_UNDER_TEST = "words";
	
 
	@Autowired
	WorkingUnitDaoRepository workingUnitsRepository;
	  @Autowired
	  WordImplDaoRepository wordRepository;

	  @Autowired
	  MongoOperations mongo;
	  
	  @Autowired
	  private MongoTemplate mongoTemplate;
	  
	  @Before
	  public void setup() throws Exception {
		mongo.dropCollection(WORKING_UNIT_COLLECTION_UNDER_TEST);
	    mongo.dropCollection(WORD_COLLECTION_UNDER_TEST);
	  }

	  @After
	  public void teardown() {
//	    mongo.dropCollection(COLLECTION_UNDER_TEST);
	  }

	  @Test
	  public void thatItemIsInsertedIntoRepoWorks() throws Exception {
//		mongo.dropCollection(WORKING_UNIT_COLLECTION_UNDER_TEST);
//		mongo.dropCollection(WORD_COLLECTION_UNDER_TEST);
//		assertEquals(0, mongo.getCollection(WORKING_UNIT_COLLECTION_UNDER_TEST).count());
//	    assertEquals(0, mongo.getCollection(WORD_COLLECTION_UNDER_TEST).count());
	    WorkingUnitImpl inMemoryWorkingUnit = WorkingUnitPersistenceFixture.standardWorkingUnit();
//	    System.out.println(wu0.getAnnotatables());
	    List<Word> words = inMemoryWorkingUnit.getWords();
//	    for (HasAnnotations hasAnnotations : words) {
//	    	wordRepository.save(hasAnnotations);
//		}
	    /*
	     * Important! Save first the dependencies in order to ensure Id! 
	     */
	    
//	    for (Iterator iterator = words.iterator(); iterator.hasNext();) {
//			HasAnnotations hasAnnotations = (HasAnnotations) iterator.next();
			
//			wordRepository.save(words);
			
//		}
	    
	    /* Than save the parent instance */
//	    workingUnitsRepository.save(inMemoryWorkingUnit);
	    
//	    List<WorkingUnit> retrievedWuFromDb = workingUnitsRepository.findAll();
	    
//	    Query query = new Query(where("workingUnits.score").is(1000));
//	    System.out.println(mongoTemplate.find(query, WorkingUnitImpl.class)); 
	    
//	    System.out.println(retrievedWuFromDb.get(0).getAnnotatables().get(0).getAnnotationsOfType(AnnotationTypes.FORM).get(0).getType());
	    
//	    
//	    assertEquals(1, retrievedWuFromDb.size());
//	    assertEquals(inMemoryWorkingUnit.getWords().size(), retrievedWuFromDb.get(0).getWords().size());
	  }

}
