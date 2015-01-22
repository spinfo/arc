package de.spinfo.arc.persistance.repository.update;

import com.mongodb.WriteResult;
import de.spinfo.arc.annotationmodel.annotatable.Word;
import de.spinfo.arc.annotationmodel.annotatable.impl.WordImpl;
import de.spinfo.arc.annotationmodel.annotation.Annotation;
import de.spinfo.arc.annotationmodel.annotation.Annotation.AnnotationTypes;
import de.spinfo.arc.annotationmodel.annotation.FormAnnotation;
import de.spinfo.arc.annotationmodel.annotation.PosAnnotation;
import de.spinfo.arc.annotationmodel.annotation.RectangleAnnotation;
import de.spinfo.arc.annotationmodel.annotation.impl.FormAnnotationImpl;
import de.spinfo.arc.annotationmodel.annotation.impl.PosAnnotationImpl;
import de.spinfo.arc.annotationmodel.annotation.impl.RectangleAnnotationImpl;
import de.spinfo.arc.persistance.config.MongoConfiguration;
import de.spinfo.arc.persistance.repository.WordImplDaoRepository;
import de.spinfo.arc.persistance.repository.WorkingUnitDaoRepository;
import de.spinfo.arc.persistance.repository.WorkingUnitImplDaoRepository;
import de.spinfo.arc.persistance.service.update.WordUpdater;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes = {MongoConfiguration.class})
public class TestUpdateingAWord {
	
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
	  
	  WordUpdater wordUpdater;
	  
	  @Before
	  public void setup() throws Exception {
		  ApplicationContext ctx = 
				  new AnnotationConfigApplicationContext(MongoConfiguration.class);
		  wordUpdater = new WordUpdater(ctx, WORD_COLLECTION_UNDER_TEST);
	  }

	  @After
	  public void teardown() {
	  }

	  @Test
	  public void testUpdatingAWordsForm() throws Exception {
		  
		  int WORD_IDX_TO_UPDATE = 0;
		  
		  /*Below is an initial form anno in case its been lost while testing popping elements*/
//		  "form" : "1  DANIEL",
//		  "date" : ISODate("2011-10-10T21:49:55.505Z"),
//		  "score" : 0,
//		  "userId" : "badilattim",
//		  "_class" : "de.spinfo.arc.annotationmodel.annotation.impl.FormAnnotationImpl"
//		  FormAnnotation fa = new FormAnnotationImpl(new Date(), 0, "badilattim", "1  DANIEL");
//		  wordUpdater.pushForm(WORD_IDX_TO_UPDATE, fa);
		  
		  
		  String NEW_FORM = "testForm5";
		  String NEW_ID = "testId5";
		  
		  Word w = mongoTemplate.findOne(
				  Query.query(Criteria.where("index").is(WORD_IDX_TO_UPDATE)
						  )
				  , WordImpl.class);
		  
		  List<Annotation> forms =  w.getAnnotationsOfType(AnnotationTypes.FORM);
		  
		  FormAnnotation lastFormAnnotation = w.getLastFormAnnotation();
		  
		  assertEquals("Is convenience casting working?"
				  , ((FormAnnotation)forms.get(forms.size()-1))
				  , w.getLastFormAnnotation());
		  
		  FormAnnotation formAnnotation = new FormAnnotationImpl(new Date(), 1000, NEW_ID, NEW_FORM );
		  
		  WriteResult wr = wordUpdater.pushForm(WORD_IDX_TO_UPDATE, formAnnotation);
		  
		  
		  w = wordRepository.queryIndex(WORD_IDX_TO_UPDATE);
		  
		  
		  assertEquals(" Is the last from annotation retrieved?", NEW_FORM, formAnnotation.getForm());
		  assertEquals(" Is the last id of last annotation is correct?", NEW_ID, formAnnotation.getUserId());
		  
		  wordUpdater.popLastForm(WORD_IDX_TO_UPDATE);
		  
	  }
	  
	  @Test
	  public void testUpdatingAWordsRect() throws Exception {
		  
		  int WORD_IDX_TO_UPDATE = 0;
		  int X = -1;
		  int Y = -1;
		  int WIDTH = -10;
		  int HEIGHT = -10;
		  String NEW_ID = "testId";
		  
		  Word w = wordRepository.queryIndex(WORD_IDX_TO_UPDATE);
		  
		  List<Annotation> rects =  w.getAnnotationsOfType(AnnotationTypes.RECTANGLE);
		  
		  RectangleAnnotation lastRectAnnotation = w.getLastRectangleAnnotation();
		  
		  assertEquals("Is convenience casting working?"
				  , ((RectangleAnnotation)rects.get(rects.size()-1))
				  , w.getLastRectangleAnnotation());
		  
		  RectangleAnnotation rectAnnotation = new RectangleAnnotationImpl(new Date(), 1000, NEW_ID, X, Y, WIDTH, HEIGHT);
		  
		  WriteResult wr = wordUpdater.pushRect(WORD_IDX_TO_UPDATE, rectAnnotation);
		  
		  w = wordRepository.queryIndex(WORD_IDX_TO_UPDATE);
		  
		  assertEquals(" Does the last rect annotation equals the retrieved one?", rectAnnotation, w.getLastRectangleAnnotation());
		  assertEquals(" Is the last id of last annotation is correct?", NEW_ID, w.getLastRectangleAnnotation().getUserId());
		  
		  wordUpdater.popLastRect(WORD_IDX_TO_UPDATE);
		  
		  w = wordRepository.queryIndex(WORD_IDX_TO_UPDATE);
		  
		  assertEquals(" Is after the popping the last one the prior one still correct?", lastRectAnnotation, w.getLastRectangleAnnotation());
		  
	  }
	  
	  @Test
	  public void testUpdatingAWordsPOS() throws Exception {
		  int WORD_IDX_TO_UPDATE = 1;
		  String NEW_ID = "testId";
		  PosAnnotation.PosTags POS_TO_SAVE = PosAnnotation.PosTags.ADJ;
		  Word w = wordRepository.queryIndex(WORD_IDX_TO_UPDATE);
		  
		  PosAnnotation posAnnotation = new PosAnnotationImpl(new Date(), 1000, NEW_ID, POS_TO_SAVE);
		  
		  WriteResult wr = wordUpdater.pushPos(WORD_IDX_TO_UPDATE, posAnnotation);
		  w = wordRepository.queryIndex(WORD_IDX_TO_UPDATE);
		  
		  assertEquals(" Does the last rect annotation equals the retrieved one?", posAnnotation, w.getLastPosAnnotation());
		  assertEquals(" Is the last id of last annotation is correct?", NEW_ID, w.getLastPosAnnotation().getUserId());
		  
		  w = wordRepository.queryIndex(WORD_IDX_TO_UPDATE);
		  wordUpdater.popLastPos(WORD_IDX_TO_UPDATE);
		  
		  System.out.println(w.getLastPosAnnotation().getPos());
		  System.out.println(w.getTaggerPosOptions());
		  
		  assertEquals(" Is after the popping the last one the prior one still correct?", posAnnotation, w.getLastPosAnnotation());
	  }
	  
	  @Test
	  public void testSettingPosTaggerOptions() throws Exception {
		int WORD_IDX_TO_UPDATE = 1;
		String NEW_ID = "testId";
		PosAnnotation.PosTags[] POS_TAGS_TO_SAVE = { 
				PosAnnotation.PosTags.ADJ,
				PosAnnotation.PosTags.ART,
				PosAnnotation.PosTags.ADJ_POS,
				PosAnnotation.PosTags.C_NUM,
		};
		
		Set<PosAnnotation.PosTags> tagSet = new HashSet<PosAnnotation.PosTags>(Arrays.asList(POS_TAGS_TO_SAVE));
		
		WriteResult wr = wordUpdater.pushPosTaggerOptions(WORD_IDX_TO_UPDATE, tagSet);
		Word w = wordRepository.queryIndex(WORD_IDX_TO_UPDATE);
		
		assertEquals(" Does the last rect annotation equals the retrieved one?", tagSet.size(), w.getTaggerPosOptions().size());
//		assertEquals(" Is the last id of last annotation is correct?", NEW_ID, w.getLastPosAnnotation().getUserId());
		
		w = wordRepository.queryIndex(WORD_IDX_TO_UPDATE);
	  }

}
