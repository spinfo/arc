package de.spinfo.arc.persistance.repository.update;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.spinfo.arc.annotationmodel.annotatable.impl.WorkingUnitImpl;
import de.spinfo.arc.annotationmodel.annotation.LanguageRange;
import de.spinfo.arc.annotationmodel.annotation.impl.LanguageRangeImpl;
import de.spinfo.arc.model2model.ChrestomatieConst;
import de.spinfo.arc.persistance.config.MongoConfiguration;
import de.spinfo.arc.persistance.repository.WordImplDaoRepository;
import de.spinfo.arc.persistance.repository.WorkingUnitDaoRepository;
import de.spinfo.arc.persistance.repository.WorkingUnitImplDaoRepository;
import de.spinfo.arc.persistance.service.update.WorkingUnitUpdater;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes = {MongoConfiguration.class})
public class TestUpdatingLanguages implements ChrestomatieConst{
	
	private static String DB_UNDER_TEST = "arc_working_unit";
	private static String WORKING_UNIT_COLLECTION_UNDER_TEST = "workingUnits";
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
	  
	  WorkingUnitUpdater workingUnitUpdater;
	  
	  @Before
	  public void setup() throws Exception {
		  ApplicationContext ctx = 
				  new AnnotationConfigApplicationContext(MongoConfiguration.class);
		  workingUnitUpdater = new WorkingUnitUpdater(ctx, WORKING_UNIT_COLLECTION_UNDER_TEST);
	  }

	  @After
	  public void teardown() {
	  }

	  @Test
	  public void testSettingLanguage() throws Exception {
		  
		  String WU_TITLE = wuTitles2[0];
		  WorkingUnitImpl wu = workingUnitsImplRepository.queryTitle(WU_TITLE);
		  int languageCountBefore = wu.getLanguages().size();
		  System.out.println("wu: " + wu.getTitle());
		  System.out.println("languageCountBefore" + languageCountBefore);
		  int startIdx = 0;
		  int endIdx = 1;
		  String NEW_ID = "testId5";
		  
		  LanguageRange languageRangetoUpdate = new LanguageRangeImpl(new Date(), -1,
				  NEW_ID, startIdx,
				  endIdx, "testTitle");
		  
		  // First check iof there are any test ranges left..
		  for (LanguageRange range : wu.getLanguages() ) {
			  if (range.getTitle().equals(languageRangetoUpdate.getTitle())
					  && range.getUserId().equals(languageRangetoUpdate.getUserId()) ) 
			  {
				  System.out.println("PULLING OLD..");
				  //.. and delete them
				  System.out.println(workingUnitUpdater.pullLanguage(WU_TITLE, range));
			  }
		  }
		  
		  // Make an assertion that there are no old ones left 
		  for (LanguageRange range : wu.getLanguages() ) {
			  assertFalse("test languages should not exist upfront", 
					  range.getTitle().equals(languageRangetoUpdate.getTitle())
					  && range.getUserId().equals(languageRangetoUpdate.getUserId())
					  );
		  }
		  
		  // push a new range  
		  System.out.println("PUSHING..");
		  System.out.println(workingUnitUpdater.pushLanguage( WU_TITLE, languageRangetoUpdate ));
		  // query the db again and check if the new one is there
		  wu = workingUnitsImplRepository.queryTitle(WU_TITLE);
		  int languageCountAfter = wu.getLanguages().size();
		  System.out.println("languageCountAfter PUSHING" + languageCountAfter);
		  
		  assertTrue("has the amount of languages increased by one?", (languageCountAfter - languageCountBefore) == 1);
		  System.out.println("DONE PUSHING!");
		  
		  // Delete the newly created one
		  System.out.println("PULLING NEW..");
		  System.out.println(workingUnitUpdater.pullLanguage(WU_TITLE, languageRangetoUpdate));
		  
		  wu = workingUnitsImplRepository.queryTitle(WU_TITLE);
		  languageCountAfter = wu.getLanguages().size();
		  
		  System.out.println("languageCountAfter PULLING" + languageCountAfter);
		  // Check if the amount of languages is decreased
		  assertTrue("has the amount of languages decreased by one after removing the last added?", (languageCountAfter - languageCountBefore) == 0);
		  
		  // make further assertions
		  for (LanguageRange range : wu.getLanguages() ) {
			  assertFalse("test language should not exist after pushing", 
					  range.getTitle().equals(languageRangetoUpdate.getTitle())
					  && range.getUserId().equals(languageRangetoUpdate.getUserId())
					  );
		  }
	  }
	 
}
