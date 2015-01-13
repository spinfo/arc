package de.spinfo.arc.persistance.repository.update;

import static org.junit.Assert.assertEquals;

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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.WriteResult;

import de.spinfo.arc.persistance.config.MongoConfiguration;
import de.spinfo.arc.persistance.repository.WordImplDaoRepository;
import de.spinfo.arc.persistance.repository.WorkingUnitDaoRepository;
import de.spinfo.arc.persistance.repository.WorkingUnitImplDaoRepository;
import de.spinfo.arc.persistance.service.update.WordUpdater;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.Word;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.impl.WordImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.Annotation;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.Annotation.AnnotationTypes;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.FormAnnotation;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes = {MongoConfiguration.class})
public class TestPoppingUpdatesOfWord {
	
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
	  public void testPoppingAndPushingLastForm() throws Exception {
		
		  int WORD_IDX_TO_UPDATE = 0;
		  
		  Word w = mongoTemplate.findOne(
					Query.query(Criteria.where("index").is(WORD_IDX_TO_UPDATE))
					, WordImpl.class);
		  
		List<Annotation> forms =  w.getAnnotationsOfType(AnnotationTypes.FORM);
		  
		FormAnnotation firstAnno = w.getAllFormsAnnotations().get(0);
		
		for (int i = 0; i <  w.getAllFormsAnnotations().size(); i++) {
			WriteResult wr = wordUpdater.popLastForm(WORD_IDX_TO_UPDATE);
		}
		 
		w = wordRepository.queryIndex(WORD_IDX_TO_UPDATE);
		
		assertEquals(" Is the last from annotation retrieved?", 0, w.getAllFormsAnnotations().size());
		
		wordUpdater.pushForm(WORD_IDX_TO_UPDATE, firstAnno);
		
		w = wordRepository.queryIndex(WORD_IDX_TO_UPDATE);
		
		assertEquals(" Is the last from annotation retrieved?", 1, w.getAllFormsAnnotations().size());
	  }

}
