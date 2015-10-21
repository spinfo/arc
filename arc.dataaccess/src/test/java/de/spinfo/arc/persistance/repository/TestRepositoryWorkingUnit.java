package de.spinfo.arc.persistance.repository;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.spinfo.arc.annotationmodel.annotatable.WorkingUnit;
import de.spinfo.arc.model2model.ChrestomatieConst;
import de.spinfo.arc.persistance.config.MongoConfiguration;
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes = {MongoConfiguration.class})
public class TestRepositoryWorkingUnit implements ChrestomatieConst {
	
	private static String DB_UNDER_TEST = "arc_working_unit";
	private static String WORKING_UNIT_COLLECTION_UNDER_TEST = "workingUnit";
	private static String WORD_COLLECTION_UNDER_TEST = "words";
	
 
	@Autowired
	WorkingUnitImplDaoRepository workingUnitsRepository;
	  @Autowired
	  WordImplDaoRepository wordRepository;

	  @Autowired
	  MongoOperations mongo;
	  
	  @Autowired
	  private MongoTemplate mongoTemplate;
	  
	  @Before
	  public void setup() throws Exception {
	  }

	  @After
	  public void teardown() {
//	    mongo.dropCollection(COLLECTION_UNDER_TEST);
	  }

	  @Test
	  public void wuTitleQueryTest() throws Exception {
		  for (int i = 0; i < wuTitles2.length; i++) {
			  WorkingUnit wuFromMongo = workingUnitsRepository.queryTitle(wuTitles2[i]);
//			  System.out.println(wuFromMongo.getTitle());
//			  long currentWuSize = wuFromMongo.getEnd() - wuFromMongo.getStart();
			  
//			  assertEquals("Do the workingUnits have expected titles? ", wuTitles2[i], wuFromMongo.getTitle());
		  }
	  }
	  
	  @Test
	  public void queryPagesFromWu() {
		  int WU_IDX = 1;
		  WorkingUnit wuFromMongo = workingUnitsRepository.queryTitle(wuTitles2[WU_IDX]);
//		  System.out.println(wuFromMongo.getPages());
		  assertEquals("Do the workingUnits pages have the expected size? ", wuFromMongo.getPages().size(), wuNumPages[WU_IDX]);
	  }
	  @Test
	  public void queryChaptersFromWu() {
		  	  int WU_IDX = 0;
			  WorkingUnit wuFromMongo = workingUnitsRepository.queryTitle(wuTitles2[WU_IDX]);
			  System.out.println(wuFromMongo.getChapters().size());
			  assertEquals("Do the workingUnits chapters have the expected size? ", wuFromMongo.getPages().size(), wuNumPages[WU_IDX]);
	  }

}
