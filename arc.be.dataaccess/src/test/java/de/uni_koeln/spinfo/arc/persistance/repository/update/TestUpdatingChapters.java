package de.uni_koeln.spinfo.arc.persistance.repository.update;

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

import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.impl.WorkingUnitImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.ChapterRange;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.impl.ChapterRangeImpl;
import de.uni_koeln.spinfo.arc.model2model.ChrestomatieConst;
import de.uni_koeln.spinfo.arc.persistance.config.MongoConfiguration;
import de.uni_koeln.spinfo.arc.persistance.repository.WordImplDaoRepository;
import de.uni_koeln.spinfo.arc.persistance.repository.WorkingUnitDaoRepository;
import de.uni_koeln.spinfo.arc.persistance.repository.WorkingUnitImplDaoRepository;
import de.uni_koeln.spinfo.arc.persistance.service.update.WorkingUnitUpdater;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes = {MongoConfiguration.class})
public class TestUpdatingChapters implements ChrestomatieConst{
	
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
	  public void testSettingChapter() throws Exception {
		  
		  String WU_TITLE = wuTitles2[0];
		  WorkingUnitImpl wu = workingUnitsImplRepository.queryTitle(WU_TITLE);
		  int chapterCountBefore = wu.getChapters().size();
		  System.out.println("wu: " + wu.getTitle());
		  System.out.println("chapterCountBefore" + chapterCountBefore);
		  int chapterStartIdx = 0;
		  int chapterEndIdx = 1;
		  String NEW_ID = "testId5";
		  
		  ChapterRange chapterRangetoUpdate = new ChapterRangeImpl(new Date(), -1,
				  NEW_ID, chapterStartIdx,
				  chapterEndIdx, "testTitle");
		  
		  // First check iof there are any test ranges left..
		  for (ChapterRange cr : wu.getChapters() ) {
			  if (cr.getTitle().equals(chapterRangetoUpdate.getTitle())
					  && cr.getUserId().equals(chapterRangetoUpdate.getUserId()) ) 
			  {
				  System.out.println("PULLING OLD..");
				  //.. and delete them
				  System.out.println(workingUnitUpdater.pullChapter(WU_TITLE, cr));
			  }
		  }
		  
		  // Make an assertion that there are no old ones left
		  for (ChapterRange cr : wu.getChapters() ) {
			  assertFalse("test chapter should not exist upfront", 
					  cr.getTitle().equals(chapterRangetoUpdate.getTitle())
					  && cr.getUserId().equals(chapterRangetoUpdate.getUserId())
					  );
		  }

		 // push a new chapter	  
		  System.out.println("PUSHING..");
		  System.out.println(workingUnitUpdater.pushChapter( WU_TITLE, chapterRangetoUpdate ));
		  // query the db again and check if the new one is there
		  wu = workingUnitsImplRepository.queryTitle(WU_TITLE);
		  int chapterCountAfter = wu.getChapters().size();
		  System.out.println("chapterCountAfter PUSHING" + chapterCountAfter);
		  
		  assertTrue("has the amount of chapters increased by one?", (chapterCountAfter - chapterCountBefore) == 1);
		  System.out.println("DONE PUSHING!");
		  
		  // Delete the newly created one
		  System.out.println("PULLING NEW..");
		  System.out.println(workingUnitUpdater.pullChapter(WU_TITLE, chapterRangetoUpdate));
		  
		  wu = workingUnitsImplRepository.queryTitle(WU_TITLE);
		  chapterCountAfter = wu.getChapters().size();
		  
		  System.out.println("chapterCountAfter PULLING" + chapterCountAfter);
		  // Check if the amount of chapters is decreased
		  assertTrue("has the amount of chapters decreased by one after removing the last added?", (chapterCountAfter - chapterCountBefore) == 0);
		  
		  // make further assertions
		  for (ChapterRange cr : wu.getChapters() ) {
			  assertFalse("test chapter should not exist after pushing", 
					  cr.getTitle().equals(chapterRangetoUpdate.getTitle())
					  && cr.getUserId().equals(chapterRangetoUpdate.getUserId())
					  );
		  }
	  }
	  
	 
}
