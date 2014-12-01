package de.spinfo.arc.persistance.repository.query;

//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

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

import de.spinfo.arc.annotationmodel.annotatable.WorkingUnit;
import de.spinfo.arc.annotationmodel.annotatable.impl.WordImpl;
import de.spinfo.arc.annotationmodel.annotation.PageRange;
import de.spinfo.arc.annotationmodel.annotation.RangeAnnotation;
import de.spinfo.arc.model2model.ChrestomatieConst;
import de.spinfo.arc.persistance.config.MongoConfiguration;
import de.spinfo.arc.persistance.repository.WordImplDaoRepository;
import de.spinfo.arc.persistance.repository.WorkingUnitDaoRepository;
import de.spinfo.arc.persistance.repository.WorkingUnitImplDaoRepository;
import de.spinfo.arc.persistance.service.query.WordQueries;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes = {MongoConfiguration.class})
public class TestExtendedRangeQueries implements ChrestomatieConst{
	
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
	  
	  WordQueries extQueries;
	  
	  @Before
	  public void setup() throws Exception {
//		  ApplicationContext ctx = 
//				  new AnnotationConfigApplicationContext(MongoConfiguration.class);
		  extQueries = new WordQueries();
	  }

	  @After
	  public void teardown() {
	  }

	  
	  @Test
	  public void testRetrievingWordsByRange() throws Exception {
		  WorkingUnit wu = workingUnitsImplRepository.queryTitle(wuTitles2[0]);
		  int RANGE_NUM = 0;
		  RangeAnnotation pageRange = wu.getPages().get(RANGE_NUM);
		  List<WordImpl> words = extQueries.getWordsByRange2(pageRange);
		  System.out.println(words.get(0).getLastFormAnnotation().getForm());
		  System.out.println(words.get(words.size()-1).getLastFormAnnotation().getForm());
		  
		  assertEquals("is first word of a page retrieved correctly?", "1  DANİEL",
				  words.get(0).getLastFormAnnotation().getForm());
	  }
	  
	  @Test
	  public void testRetrievingPagesByRange() throws Exception {
		  WorkingUnit wu = workingUnitsImplRepository.queryTitle(wuTitles2[0]);
		  System.out.println("wu start: " + wu.getStart() + " | wu end: " + wu.getEnd());
		  int RANGE_NUM = 3;
		  RangeAnnotation chapterRange = wu.getChapters().get(RANGE_NUM);
		  System.out.println(chapterRange);
//		  wu.getPages().get(0);
		  List<PageRange> pages = wu.getPageRangesByRange(chapterRange);
		  System.out.println("pages.size(): " + pages.size());
		  System.out.println("pages start: " + pages.get(0).getStart() + " | end: " + pages.get(pages.size()-1).getEnd());
		  System.out.println("last page: " + pages.get(pages.size()-1));
//		  System.out.println("----" + );
		  
		  assertTrue("are retrieved pages from chapter not null?", !pages.isEmpty());
	  }
	  
	  

}
