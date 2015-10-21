package de.spinfo.arc.model2model;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import de.spinfo.arc.annotationmodel.annotatable.Word;
import de.spinfo.arc.annotationmodel.annotatable.WorkingUnit;
import de.spinfo.arc.persistance.config.MongoConfiguration;
import de.spinfo.arc.persistance.repository.WordDaoRepository;
import de.spinfo.arc.persistance.repository.WorkingUnitDaoRepository;

public class ConvertToNewModel implements ChrestomatieConst {
	
	private static String WORKING_UNIT_COLLECTION_UNDER_TEST = "workingUnits";
	private static String WORD_COLLECTION_UNDER_TEST = "words";
	
	static WorkingUnitDaoRepository workingUnitsRepository;
//	static WorkingUnitImplDaoRepository workingUniImplDaoRepository;
	static WordDaoRepository wordRepository;
	static MongoOperations mongoOperations;
	static ApplicationContext ctx;	
	static String SPRING_MSG = "------------------------------------- Creating SpringData Beans -------------------------------------\n";
	static String DROP_MSG = "------------------------------------- Dropping previous converted DB Model -------------------------------------\n";
	static String CONVERT_MSG = "------------------------------------- Converting old to new DB Model again -------------------------------------\n";
	static String DONE = "------------------------------------- DONE -------------------------------------\n";
	public static void main(String[] args) throws Exception {
		if (args != null)
			 if(args.length > 0) {
				 StringBuilder sb = new StringBuilder();
				 sb.append("< Usernames as Args supplied > Taking the order of user ids order to make the conversion of Form Modification (HEURISTIC 1)\n");
				 sb.append("Supplied user ids for conversion! The order is relevant for conversion:\n");
				 for (int i = 0; i < args.length; i++) {
					 sb.append("#"+i+ " args[i]\n");
				}
				 System.out.println(sb.toString());
			 }
		else
			System.out.println("< No Args for Conversion supplied > Taking just the first Form Modification (HEURISTIC 2)");
		
		System.out.println(SPRING_MSG);
		setUpSpring();
		System.out.println(DONE);
		System.out.println(DROP_MSG);
		dropPrevious(); 
		System.out.println(DONE);
		System.out.println(CONVERT_MSG);
		convert(args);
		System.out.println(DONE);
	}
	
	private static void setUpSpring() {
		ctx = new AnnotationConfigApplicationContext(MongoConfiguration.class);
//		workingUniImplDaoRepository = ctx.getBean(WorkingUnitImplDaoRepository.class);	
		workingUnitsRepository = ctx.getBean(WorkingUnitDaoRepository.class);	
		wordRepository = ctx.getBean(WordDaoRepository.class);
		mongoOperations = (MongoOperations) ctx.getBean("mongoTemplate");
	}
	private static void convert(String[] relevantUserIds) {
		 // some code to be measured
		System.out.println("Start converting... This may take some time.. ");
		System.out.println("You'll get spare progress infos... consider grapping a coffee.. ");
		 RetriveWorkingUnitAnnotations rwua = new RetriveWorkingUnitAnnotations();
		 
		 if (relevantUserIds !=null)
		 if(relevantUserIds.length > 0) 
			 rwua.setUserIdsForFormAnnotations(relevantUserIds);	 
		 
		  for (int i = 0; i < wuTitles.length; i++) {
//		 for (int i = 0; i < 1; i++) {
		  	int WU_IDX = i;
			System.out.println("converting Working Unit #" + WU_IDX + " / " + wuTitles.length + "\n Title: " + wuTitles[WU_IDX]);
			 String WU_VAL_TO_QUERY = wuTitles[WU_IDX]; // 2722117 - 2775612
			 WorkingUnit inMemoryWorkingUnit = rwua.getWorkingUnit(WU_VAL_TO_QUERY);
			 
			 List<Word> words = inMemoryWorkingUnit.getWords();	
			 
			 System.out.println("Saving workingUnit collection... ");
			 workingUnitsRepository.save(inMemoryWorkingUnit);
			 
			 System.out.println("Saving words collection... ");
			 // the worddaorepo must use the interface Word
			 wordRepository.save(words);
			 System.out.println("-------------------------------------  Done with " + wuTitles[WU_IDX] + "! ------------------------------------- ");
		}
		
		  System.out.println("=====================================================================");
		  System.out.println("DONE WITH FULL CONVERSION! Fire up the arc editor and behold the changes!");
		  System.out.println("---------------------------------------------------------------------");
	}

	private static void dropPrevious() throws Exception {
		mongoOperations.dropCollection(WORKING_UNIT_COLLECTION_UNDER_TEST);
		if (mongoOperations.getCollection(WORKING_UNIT_COLLECTION_UNDER_TEST).count() > 0) {
			System.err.println("Collection: " +  WORKING_UNIT_COLLECTION_UNDER_TEST +" couldn't be droped before converting! Please drop it and convert again!");
			throw new Exception();
		} 
		else
			System.out.println("Dropped Collection: " +  WORKING_UNIT_COLLECTION_UNDER_TEST );
		
		mongoOperations.dropCollection(WORD_COLLECTION_UNDER_TEST);
		if (mongoOperations.getCollection(WORD_COLLECTION_UNDER_TEST).count() > 0) {
			System.err.println("Collection: " + WORD_COLLECTION_UNDER_TEST + " couldn't be droped before converting! Please drop it and convert again!");
			throw new Exception();
		} 
		else 
		System.out.println("Dropped Collection: " +  WORD_COLLECTION_UNDER_TEST );
		
	}
	
}
