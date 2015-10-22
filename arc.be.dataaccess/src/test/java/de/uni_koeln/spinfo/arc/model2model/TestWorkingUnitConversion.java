package de.uni_koeln.spinfo.arc.model2model;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.Word;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.WorkingUnit;
import de.uni_koeln.spinfo.arc.model2model.ChrestomatieConst;
import de.uni_koeln.spinfo.arc.model2model.RetriveWorkingUnitAnnotations;
import de.uni_koeln.spinfo.arc.persistance.config.MongoConfiguration;
import de.uni_koeln.spinfo.arc.persistance.repository.WordDaoRepository;
import de.uni_koeln.spinfo.arc.persistance.repository.WordImplDaoRepository;
import de.uni_koeln.spinfo.arc.persistance.repository.WorkingUnitDaoRepository;
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes = {MongoConfiguration.class})
public class TestWorkingUnitConversion implements ChrestomatieConst {
	
	private static String DB_UNDER_TEST = "arc_working_unit";
	private static String WORKING_UNIT_COLLECTION_UNDER_TEST = "workingUnits";
	private static String WORD_COLLECTION_UNDER_TEST = "words";
	private static String WORD_CONTAINER_COLLECTION_UNDER_TEST = "wordContaier";
	
 
	@Autowired
	WorkingUnitDaoRepository workingUnitsRepository;
	@Autowired
	WordImplDaoRepository wordImplRepository;
	@Autowired
	WordDaoRepository wordRepository;
	  @Autowired
	  WordImplDaoRepository wordContaierRepository;

	  @Autowired
	  MongoOperations mongo;
	  
	  @Autowired
	  private MongoTemplate mongoTemplate;
	  
	  @Before
	  public void setup() throws Exception {
//		mongo.dropCollection(WORKING_UNIT_COLLECTION_UNDER_TEST);
//		mongo.dropCollection(WORD_COLLECTION_UNDER_TEST);
//	    mongo.dropCollection(WORD_CONTAINER_COLLECTION_UNDER_TEST);
	  }

	  @After
	  public void teardown() {
//	    mongo.dropCollection(COLLECTION_UNDER_TEST);
	  }
 
//	  @Test
	  public void IsConversionWorking() throws Exception {
		  /*
		   * Uncomment to delete previous conversion
		   */
		mongo.dropCollection(WORKING_UNIT_COLLECTION_UNDER_TEST);
		mongo.dropCollection(WORD_COLLECTION_UNDER_TEST);
		mongo.dropCollection(WORD_CONTAINER_COLLECTION_UNDER_TEST);
		assertEquals(0, mongo.getCollection(WORKING_UNIT_COLLECTION_UNDER_TEST).count());
		assertEquals(0, mongo.getCollection(WORD_COLLECTION_UNDER_TEST).count());
	    assertEquals(0, mongo.getCollection(WORD_CONTAINER_COLLECTION_UNDER_TEST).count());
	    
		  RetriveWorkingUnitAnnotations rwua = new RetriveWorkingUnitAnnotations();
		  for (int i = 0; i < wuTitles.length; i++) {
//		 for (int i = 0; i < 1; i++) {
		  	int WU_IDX = i;
			System.err.println("converting WU: " + wuTitles[WU_IDX]);
			 String WU_VAL_TO_QUERY = wuTitles[WU_IDX]; // 2722117 - 2775612
			 WorkingUnit inMemoryWorkingUnit = rwua.getWorkingUnit(WU_VAL_TO_QUERY);
			 
			 List<Word> words = inMemoryWorkingUnit.getWords();	
			 
			 System.err.println("Saving workingUnit collection... ");
			 workingUnitsRepository.save(inMemoryWorkingUnit);
			 
			 System.err.println("Saving words collection... ");
			 // the worddaorepo must use the interface Word
			 wordRepository.save(words);
		}
//	    
//		List<WorkingUnit> retrievedWuFromDb = workingUnitsRepository.findAll();
//	    List<WordImpl> retrievedWordsFromDb = wordRepository.findAll();
	    
	    
//	    assertEquals(wuSizes[WU_IDX]+1, retrievedWordsFromDb.size());
//	    assertEquals(1, retrievedWuFromDb.size());
	  }

}
