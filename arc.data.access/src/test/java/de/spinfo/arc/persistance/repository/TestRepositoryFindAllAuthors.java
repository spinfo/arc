package de.spinfo.arc.persistance.repository;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.impl.WordImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.FormAnnotation;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes = {MongoConfiguration.class})
public class TestRepositoryFindAllAuthors {
	
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
	  }

	  @After
	  public void teardown() {
//	    mongo.dropCollection(COLLECTION_UNDER_TEST);
	  }

	  @Test
	  public void testRetrievingDistinctUsersWhoMadeAnnotations() throws Exception {
		
		/*
		 * Query a Range in respect of word index
		 */
		  
		List<WordImpl> words = wordRepository.findAll();
		System.out.println("found total words: " + words.size());
		
		Map<String, Integer> users = new HashMap<String, Integer>();
		for (Iterator<WordImpl> iterator = words.iterator(); iterator.hasNext();) {
			WordImpl word = iterator.next();
			for (Iterator<FormAnnotation> fromIt = word.getAllFormsAnnotations().iterator(); fromIt.hasNext();) {
				FormAnnotation formAnno = fromIt.next();
				Integer freq = users.get(formAnno.getUserId());
				
				if (freq == null) 
				users.put(formAnno.getUserId(), (freq == null) ? 1 : freq + 1);
			}
		}
		
//		System.out.println(users);
		assertEquals("Is the correct amount of modifications by OCR retrieved (one for each word)?", new Integer(words.size()), users.get("OCR"));

	  }

}
